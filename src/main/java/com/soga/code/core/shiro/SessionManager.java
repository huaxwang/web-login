package com.soga.code.core.shiro;

import com.soga.code.core.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class SessionManager extends DefaultWebSessionManager {

	public SessionManager() {
		super();
	}
	
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		// 如果参数中包含“sid”参数，则使用此sid会话。 例如：http://localhost/project?__sid=xxx&__cookie=true
		// 其实这里还可以使用如下参数：cookie中的session名称：如：JSESSIONID=xxx,路径中的 ;JESSIONID=xxx，但建议还是使用 __sid参数。
		String sid = request.getParameter("sid");
		if (StringUtils.isNotBlank(sid)) {
			// 是否将sid保存到cookie，浏览器模式下使用此参数。
			if (WebUtils.isTrue(request, "cooktrue")){
				HttpServletRequest rq = (HttpServletRequest)request;
		        HttpServletResponse rs = (HttpServletResponse)response;
				Cookie template = getSessionIdCookie();
		        Cookie cookie = new SimpleCookie(template);
				cookie.setValue(sid); cookie.saveTo(rq, rs);
			}
			// 设置当前session状态
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        	return sid;
		}else{
			//查询cookie有sessionId直接返回sessionID
			HttpServletRequest rq = (HttpServletRequest)request;
			javax.servlet.http.Cookie[] cookies = rq.getCookies();
			String value = CookieUtil.getUid(rq, "sid");
			if(StringUtils.isNotBlank(value)){
				return value;
			}
			
			//查询cookie没有sessionID重新生成一个sessionID
			return super.getSessionId(request, response);
		}
	}
 
}

