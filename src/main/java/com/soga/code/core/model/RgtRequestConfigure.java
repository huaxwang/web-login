package com.soga.code.core.model;

/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：huaxing.wang@rogrand.com <br/>
 * 生成日期：2016年5月18日 <br/>
 * 描述：〈融通请求url〉
 */
public class RgtRequestConfigure {
	
	/**
	 * 工商注册号验证
	 */
	public static String CHECK_ELICENSENO = "/biz/checkELicenseNo";
	/**
	 * 企业信息新增
	 */
	public static String ADD_ENTERPRISE = "/biz/addEnterprise";
	/**
	 * 企业信息认证
	 */
	public static String AUDIT_ENTERPRISE = "/biz/auditEnterprise";
	/**
	 * 企业信息修改
	 */
	public static String UPDATE_ENTERPRISE = "/biz/updateEnterprise";
	/**
	 * 根据ID获取企业信息
	 */
	public static String QUERY_ENTERPRISE = "/biz/queryEnterprise";
	/**
	 * 企业资质新增
	 */
	public static String ADD_ENTERPRISE_PIC = "/biz/addEnterprisePic";
	/**
	 * 企业资质修改
	 */
	public static String UPDATE_ENTERPRISE_PIC = "/biz/updateEnterprisePic";
	/**
	 * 企业资质修改
	 */
	public static String DELETE_ENTERPRISE_PIC = "/biz/deleteEnterprisePic";
	/**
	 * 根据企业ID获取企业资质
	 */
	public static String QUERY_ENTERPRISE_PICS = "/biz/queryEnterprisePics";

	
	/**
	 * 用户注册
	 */
	public static String USER_REGISTER = "/userInfos/register";
	
	/**
	 * 用户登录
	 */
	public static String USER_LOGIN = "/userInfos/login";
	
	/**
	 * 修改用户
	 */
	public static String UPDATE_USER= "/userInfos/updateUser";
	
	
	/**
	 * 查询用户
	 */
	public static String QUERY_USER= "/userInfos/queryUser";
	
	
	/**
	 * 验证邮箱是否重复
	 */
	public static String VALIDATE_EMAIL= "/userInfos/validateEmail";
	
	/**
	 * 验证用户名是否重复
	 */
	public static String VALIDATE_USERNAME= "/userInfos/validateUsername";
	
	
	/**
	 * 验证手机号是否重复
	 */
	public static String VALIDATE_MOBILE= "/userInfos/validateMobile";
	
	/**
	 * 用户注销
	 */
	public static String USER_LOGOUT= "/userInfos/logout";
	
	/**
	 * 验证ticket
	 */
	public static String AUTHORIZE_TICKET= "/userInfos/authorizeTicket";
	
	/**
	 * 验证授权码
	 */
	public static String AUTHORIZE_AUTHCODE= "/userInfos/authorizeAuthCode";
	
	/**
	 * 创建子账号
	 */
	public static String CREATE_CHILD_ACCOUNT= "/userInfos/createChildAccount";
	
	/**
	 * 修改子账号
	 */
	public static String UPDATE_CHILD_ACCOUNT= "/userInfos/updateChildAccount";
	
	/**
	 * 禁用启用账号
	 */
	public static String CHANGE_USER_STATUS= "/userInfos/changeUserStatus";
	
	/**
	 * 店员绑定
	 */
	public static String BIND_STAFF= "/staff/bindStaff";
	
	/**
	 * 店员解绑
	 */
	public static String UNBIND_STAFF= "/staff/unbindStaff";
	
	/**
	 * 店员审核
	 */
	public static String AUDIT_STAFF= "/staff/auditStaff";
	
	/**
	 * 菜单列表
	 */
	public static String MENU_LIST= "/permission/queryPermByUId";
}
