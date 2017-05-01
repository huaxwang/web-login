package com.soga.code.core.util;

/**
 * 版权：融贯资讯 <br/>
 * 作者：yong.chen@rogrand.com <br/>
 * 生成日期：2013年11月6日 <br/>
 * 描述：调用服务接口后返回的操作码 和 信息
 */
public enum SysCode {
    // 成功代码：000000
    // 系统代码：200000
    // 业务逻辑代码：
    // 登录：100100
    //      注册：100200
    //      短信：100300
    //      商户：100400
    //      用户：100500
    //      服务：100600
    //      活动：100700
    //      对话：100800
    //      消息：100900
    //      订单：101000
    // 优惠券验证：101100
    //   家庭药箱：101200
	//  搜索：101300
	//  订单：101400
    
    SUCCESS("000000", "操作成功"),
    FAIL("111111", "操作失败,请联系客服人员"),
    SYS_ERR("SYS_ERR", "系统错误，请联系客服人员"),
    PARAM_IS_ERROR("333333", "输入错误，请仔细检查"),
    BIZ_ERR("BIZ_ERR", "操作失败,请重试")
	;
    
    private SysCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    private String code;
    private String message;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
