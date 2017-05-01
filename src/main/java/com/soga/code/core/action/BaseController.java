package com.soga.code.core.action;

import com.alibaba.fastjson.JSON;
import com.soga.code.api.service.error.BaseException;
import com.soga.code.api.service.error.BizException;
import com.soga.code.api.service.error.RequestParamException;
import com.soga.code.api.service.error.SysErrorException;
import com.soga.code.api.service.error.enu.ParamErrorMessageCode;
import com.soga.code.api.service.system.api.base.SysErrorLogService;
import com.soga.code.api.service.system.model.SysErrorLog;
import com.soga.code.api.service.system.model.enu.SysErrorLogCode;
import com.soga.code.common.redis.RedisClient;
import com.soga.code.core.model.*;
import com.soga.code.core.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class BaseController {
	protected static Logger logger = Logger.getLogger(BaseController.class);

	protected UrlPathHelper urlPathHelper = new UrlPathHelper();

	protected SysCode returnCode;
	protected String returnResult;

	private String defaultPath = PropertiesUtil.getValue("defaultPath");


	/**
	 * 构建输出到终端JSON 串HEAD <br/>
	 * 构建输出到终端JSON 串HEAD <br/>
	 *
	 * @param method-[后台调用方法名] <br/>
	 * @param sNumber-[序列号]    <br/>
	 * @param version-[版本号]    <br/>
	 * @return [Head] 输出到终端JSON 串 HEAD<br/>
	 */
	protected Head getResponseHead(String method, String sNumber, String version) {
		return new Head(method, sNumber, version);
	}

	/**
	 * 构建输出到终端JSON 串BODY <br/>
	 * 构建输出到终端JSON 串BODY <br/>
	 *
	 * @param code-[返回消息消息码 和消息] <br/>
	 * @param result-[返回JSON   结果] <br/>
	 * @return [Body] 输出到终端JSON串 BODY<br/>
	 */
	protected Body getResponseBody(SysCode code, Object result) {
		Body body = new Body();
		body.setMessage(code.getMessage());
		body.setCode(code.getCode());
		body.setResult(result);
		return body;
	}

	/**
	 * 构建返回给前端的JSON对象 <br/>
	 * 构建返回给前端的JSON对象 <br/>
	 *
	 * @param mac-[加密信息]        <br/>
	 * @param method-[后台调用方法]   <br/>
	 * @param sNumber-[序列号]     <br/>
	 * @param version-[版本号]     <br/>
	 * @param code-[返回消息消息码和消息] <br/>
	 * @param result-[返回结果]     <br/>
	 * @return [ResponseJSON] 返回给前段JSON对象<br/>
	 */
	protected ResponseJSON getResponseJSON(String mac, String method, String sNumber, String version, SysCode code,
										   Object result) {
		ResponseJSON responseJSON = new ResponseJSON();
		responseJSON.setMac(mac);
		responseJSON.setHead(getResponseHead(method, sNumber, version));
		responseJSON.setBody(getResponseBody(code, result));
		return responseJSON;
	}

	protected ParamInfo parseRequest(HttpServletRequest request, String method, String[] paramKey) {
		String jsonParam = request.getParameter("dataJson");
		Object jsonStr = "";
		String mac = UUID.randomUUID().toString();
		String sNumber = UUID.randomUUID().toString();
		String version = "V2.0";

		logger.info("parseRequest method:" + method + "; jsonParam:" + jsonParam);

		ParamInfo info = new ParamInfo();

		// 如果传入字符串为空,则提示参数不合法
		if (RoUtil.isEmpty(jsonParam)) {
			logger.info("parameter error !");
			info.setState(SysCode.PARAM_IS_ERROR);
			info.setResponse(getResponseJSON(mac, method, sNumber, version, SysCode.PARAM_IS_ERROR, jsonStr));
			return info;
		}

		RequestJSON requestJSON = JSON.parseObject(jsonParam, RequestJSON.class);
		info.setRequestJSON(requestJSON);

		if (paramKey != null && paramKey.length > 0) {
			for (int i = 0; i < paramKey.length; i++) {
				String jp = requestJSON.getBody().getString(paramKey[i]);
				if (!RoUtil.isEmpty(jp)) {
					info.getParams().put(paramKey[i], jp);
					logger.info("【text-- the key:" + paramKey[i] + " ,value :" + jp + "】");
				} else {
					logger.info("【warn-- the key:" + paramKey[i] + " is empty or is null!】");
				}
			}
		}

		info.setResponse(getResponseJSON(mac, method, sNumber, version, SysCode.SUCCESS, jsonStr));
		info.setState(SysCode.SUCCESS);
		logger.info("parameter SUCCESS !");
		return info;
	}

	/**
	 * 描述：〈响应成功输出内容，包含跨域处理〉 <br/>
	 * 作者：xuan.zhou@rogrand.com <br/>
	 * 生成日期：2014-03-13 <br/>
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param content  JSON对象
	 */
	protected void renderJson(HttpServletRequest request, HttpServletResponse response,
							  Object content) {
		logger.info(getRequestUrl(request));
		renderJson(request, response, SysCode.SUCCESS, content, null);
	}

	/**
	 * 描述：〈响应输出内容，包含跨域处理〉 <br/>
	 * 作者：xuan.zhou@rogrand.com <br/>
	 * 生成日期：2014-03-13 <br/>
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param sysCode  系统代码
	 * @param content  JSON对象
	 */
	protected void renderJson(HttpServletRequest request, HttpServletResponse response, SysCode sysCode,
							  Object content) {
		renderJson(request, response, sysCode, content, null);
		return;
	}

	/**
	 * 响应文本
	 *
	 * @param response 响应对象
	 * @param content     输出对象
	 * @return null
	 * @throws IOException 异常
	 */
	protected void responseText(HttpServletRequest request,HttpServletResponse response, Object content){
		String jsonResult = "";
		String callback = request.getParameter("callback");
		if (StringUtils.isNotEmpty(callback)) {
			jsonResult = JSON.toJSON(content).toString();
			logger.info(jsonResult);
			ResponseUtils.renderText(response, callback + "(" + JSON.toJSON(content) + ");");
		} else {
			logger.info(jsonResult);
			ResponseUtils.renderJson(response, JsonUtils.toJsonString(content, false));
		}
	}

	protected void renderJson(HttpServletRequest request, HttpServletResponse response, SysCode sysCode,
							  Object content, Map<Class<?>, String[]> includes) {
		renderJson(request, response, sysCode, content, includes, null);
	}

	protected void renderJson(HttpServletRequest request, HttpServletResponse response, SysCode sysCode,
							  Object content, Map<Class<?>, String[]> includes, Map<Class<?>, String[]> excludes) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", sysCode.getCode());
		result.put("message", sysCode.getMessage());
		result.put("result", content);

		String jsonResult = "";
		String callback = request.getParameter("callback");
		if (StringUtils.isNotEmpty(callback)) {
			jsonResult = JSON.toJSON(result).toString();
			logger.info(jsonResult);
			ResponseUtils.renderText(response, callback + "(" + JSON.toJSON(result) + ");");
		} else {
			jsonResult = JsonUtils.toJsonString(result, includes, excludes, false);
			logger.info(jsonResult);
			ResponseUtils.renderJson(response, JsonUtils.toJsonString(result, includes, excludes, false));
		}
	}


	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	protected ModelAndView getView(HttpServletRequest request, Map map) {
		return getView(request, map, true);
	}

	/**
	 * 描述：〈描述〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2017年2月24日 <br/>
	 *
	 * @param request 请求
	 * @param map     参数
	 * @param flag    flag true-主页面 false-子页面
	 * @return
	 */
	protected ModelAndView getView(HttpServletRequest request, Map map, boolean flag) {
		String currentUser = (String) request.getSession().getAttribute("currentUser");
		logger.debug("当前登录的用户为[" + currentUser + "]");
		request.setAttribute("currUser", currentUser);

		RedisClient redisClient = SpringContextHolder.getBean("redisClient");
		String path = urlPathHelper.getContextPath(request);
		logger.debug("项目路径[" + path + "]");
		map.put("path", urlPathHelper.getContextPath(request));

		request.setAttribute("defaultPath", this.defaultPath);

		List<String> menus = new ArrayList<String>();
		menus.add("我的速卖通");
		menus.add("产品管理");
		menus.add("交易");
		menus.add("消息中心");
		menus.add("店铺");
		menus.add("账号及认证");
		menus.add("营销活动");
		menus.add("数据纵横");
		menus.add("经营表现");
		map.put("menus", menus);

		List<String> lefts = new ArrayList<String>();
		lefts.add("17年招商入驻流程全介绍");
		lefts.add("成交分析");
		lefts.add("发布产品");
		lefts.add("管理产品");
		lefts.add("淘宝产品代销");
		lefts.add("诊断中心");
		lefts.add("参加活动");
		lefts.add("管理直通车");
		lefts.add("查看违规记录");
		lefts.add("速卖通贷款");
		map.put("lefts", lefts);

		map.put("merchantHost", PropertiesUtil.getValue("merchantHost"));


		String vw = takeSubView(request, flag);
		return new ModelAndView(vw, map);
	}

	/**
	 * 重定向一个请求动作
	 *
	 * @param url 动作
	 * @return View
	 */
	protected ModelAndView getRedirect(String url) {
		String vw = UrlBasedViewResolver.REDIRECT_URL_PREFIX + url;
		return new ModelAndView("redirect:" + vw);
	}

	/**
	 * 通过请求url得到页面路径
	 *
	 * @param request 请求对象
	 * @return String
	 */
	private String takeView(HttpServletRequest request) {
		String path = urlPathHelper.getPathWithinApplication(request);
		int index = path.lastIndexOf("."); //后缀
		if (index != -1) {
			path = path.substring(0, index);
		}
		return path;
	}

	/**
	 * 通过请求url得到页面路径
	 *
	 * @param request 请求对象
	 * @param flag    true-主页面 false-子页面
	 * @return String
	 */
	private String takeSubView(HttpServletRequest request, boolean flag) {
		String path = urlPathHelper.getPathWithinApplication(request);
		if (flag == true) {
			return path;
		}
		int index = path.lastIndexOf("/"); //后缀
		if (index != -1) {
			String fistPath = path.substring(0, index);
			String lastPath = path.substring(index + 1);
			path = fistPath + "_" + lastPath;
		}
		return path;
	}

	protected Map<String, Object> getMap() {
		return new HashMap<String, Object>();
	}

	protected boolean hasText(String str) {
		if (str == null || str.trim().equals("") || !NumberUtils.isNumber(str)) {
			return false;
		}
		return true;
	}

	/**
	 * 描述：〈获取字符串参数值〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2017年3月2日 <br/>
	 *
	 * @param key      参数key
	 * @param request  请求
	 * @return
	 */
	protected String getStringParam(String key, HttpServletRequest request) {
		String result = null;

		if (StringUtils.isEmpty(key)) {
			return null;
		}

		result = request.getParameter(key);
		return result;
	}

	/**
	 * 描述：〈获取字符串参数值〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2017年3月2日 <br/>
	 *
	 * @param paramName      参数key
	 * @param request  请求
	 * @param response 响应
	 * @return
	 */
	protected Integer getIntegerParam(String paramName, HttpServletRequest request, HttpServletResponse response) throws RequestParamException {
		String result = null;
		if (StringUtils.isEmpty(paramName)) {
			return null;
		}

		result = request.getParameter(paramName);
		if (NumberUtils.isNumber(result)) {
			throw  new RequestParamException(ParamErrorMessageCode.NUMBER_FORMAT_ERROR);
		}

		return Integer.valueOf(result);
	}


	/**
	 * 描述：〈处理服务层异常〉 <br/>
	 * 〈调用服务层方法，捕捉异常〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2017年3月2日 <br/>
	 *
	 * @param e 异常
	 * @param c 控制层类
	 * @param request
	 */
	protected void dealBaseException(Exception e, Class c, HttpServletRequest request, HttpServletResponse response) {
		//系统错误
		String errorMsg = null;
		SysErrorLog sysErrorLog = new SysErrorLog();
		SysCode sysCode = null;
		if (e instanceof SysErrorException) {
            sysCode = SysCode.SYS_ERR;
            sysErrorLog.setErrorType(SysErrorLogCode.ERROR_TYPE_ONE.getType());
            errorMsg = getErrorMessage(e, c, request);
            sysErrorLog.setErrorMsg(errorMsg);
		} else if (e instanceof BizException) {
            sysCode = SysCode.BIZ_ERR;
            sysErrorLog.setErrorType(SysErrorLogCode.ERROR_TYPE_TWO.getType());
			errorMsg = getErrorMessage(e, c, request);
			sysErrorLog.setErrorMsg(errorMsg);
		} else {
			sysCode = SysCode.SYS_ERR;
			sysErrorLog.setErrorType(SysErrorLogCode.ERROR_TYPE_ONE.getType());
			errorMsg = getErrorMessage(e, c, request);
			sysErrorLog.setErrorMsg(errorMsg);
		}
		sysErrorLog.setSysName("");
		sysErrorLog.setUserId("");
		sysErrorLog.setCreateDate(new Date());
		sysErrorLog.setStatue(0);

		SysErrorLogService sysErrorLogService =SpringContextHolder.getBean("sysErrorLogService");
		Integer num = sysErrorLogService.insertSelective(sysErrorLog);
		renderJson(request, response, sysCode, "错误编号["+num+"]");
	}

	/**
	 * 描述：〈获取服务层异常信息〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2017年3月2日 <br/>
	 *
	 * @param e       服务层异常
	 * @param c       调用控制层class
	 * @param request
	 */
	private String getErrorMessage(Exception e, Class c,
								   HttpServletRequest request) {
		BaseException sysEx = null;
		if (e instanceof  BaseException){
			sysEx = (BaseException) e;
			String url = getRequestUrl(request);
			sysEx.putUrl(c.getName(), "用户请求URL[" + url + "]");
		}

		StackTraceElement stack[] = e.getStackTrace();
		StackTraceElement ste = null;
		String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		StringBuffer sbr = new StringBuffer();
		for (int i = 0; i < stack.length; i++) {
			ste = stack[i];
			if (ste.getClassName().contains("com.soga.code")) {
				sbr.append(now + " " + ste.getClassName() + "{[" + ste.getFileName() + "][" + ste.getMethodName() + "][" + ste.getLineNumber() + "]} ");
				if (e instanceof  BaseException){
					sbr.append( sysEx.getBizMessage(ste.getClassName()));
				}
				sbr.append("\r\n");
			}
		}

		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
		sbr.append("br");
		sbr.append(sw.toString());
		return sbr.toString();
	}

	/**
	 * 描述：〈返回请求全路径〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2017年3月2日 <br/>
	 *
	 * @param request
	 * @return
	 */
	private String getRequestUrl(HttpServletRequest request) {
		String url = request.getRequestURI().toString();

		//获取参数
		Enumeration enu = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		String paraName = null;
		if (!enu.hasMoreElements()) {
			return url;
		}

		while (enu.hasMoreElements()) {
			paraName = (String) enu.nextElement();
			sb.append(paraName + ":" + request.getParameter(paraName) + "&");
		}
		url = url + "?" + sb.toString().substring(0, sb.toString().lastIndexOf("&"));
		return url;
	}
}

/**
 *
 * 版权：soga <br/>
 * 作者：huaxing.wang@rogrand.com <br/>
 * 生成日期：2017年3月2日 <br/>
 * 描述：〈内部类，result错误消息对象〉
 */
class ParamErrorMessage{
	private String key;//错误参数key值
	private String msg;//错误消息内容

	public static String PARAM_NOT_INPUT_BLANK_MSG = "不能输入空格";
	public static String PARAM_INPUT_DIGIT_MSG = "请输入数字";

	ParamErrorMessage(String key,String msg){
		this.key=key;
		this.msg=msg;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
