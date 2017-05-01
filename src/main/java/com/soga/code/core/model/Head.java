package com.soga.code.core.model;

/**
 * 版权：融贯资讯 <br/>
 * 作者：kai.gao@rogrand.com <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：前后端JSON对象公用Head部分
 */
public class Head {
	/**
	 * 应用ID
	 */
	private Integer appId;
	/**
	 * Secret
	 */
	private String appSecret;
    /**
     * serialNumber : <交易序列号>
     */
    private String serialNumber;

    /**
     * method : <交易方法>
     */
    private String method;

    /**
     * version : <交易方法版本号>
     */
    private String version;

    /**
     * terminalstate : <终端类型>
     * 
     * 0-安卓 10-用户版appstore(个人) 11-用户版inhouse(企业) 12-商户版appstore(个人)
     * 13-商户版inhouse(企业)
     */

    private int terminalstate;

    /**
     * sysVersion : <终端系统版本号>
     */
    private String sysVersion;

    /**
     * imei : <设备IME码>
     */
    private String imei;

    /**
     * appType : <APP类型>
     * 
     * 1-用户版 2-商户版
     */
    private int appType;

    /**
     * appVersion : <APP版本号>
     */
    private String appVersion;

    /**
     * appSys : <IOS-1 Andriod-2>
     */
    private int appSys;
    
    /**
     * channel : <渠道>
     */
    private String channel;
    
    public Head() {
    	this.serialNumber="";
    	this.method="";
    	this.version="";
    	this.terminalstate=0;
    	this.sysVersion="";
    	this.imei="";
    	this.appVersion="";
    	this.appType=0;
    	this.appSys=0;
    	this.channel="";
    }
    
    public Head(String method, String serialNumber, String version) {
        this.method = method;
        this.serialNumber = serialNumber;
        this.version = version;
    }

    public Head(String method, String serialNumber, String version, int terminalstate, String sysVersion, String imei,
            int appType,String channel) {
        this.method = method;
        this.serialNumber = serialNumber;
        this.version = version;
        this.terminalstate = terminalstate;
        this.sysVersion = sysVersion;
        this.imei = imei;
        this.appType = appType;
        this.channel = channel;
    }

    public Head(String method, String serialNumber, String version, int terminalstate, String sysVersion, String imei,
            int appType, String appVersion,String channel) {
        this.method = method;
        this.serialNumber = serialNumber;
        this.version = version;
        this.terminalstate = terminalstate;
        this.sysVersion = sysVersion;
        this.imei = imei;
        this.appType = appType;
        this.appVersion = appVersion;
        this.channel = channel;
    }

    public String toString() {
        return "serialNumber:" + serialNumber + ",method:" + method + ",version:" + version + ",terminalstate:"
                + terminalstate + ",sysVersion:" + sysVersion + ",imei:" + imei + ",appType:" + appType
                + ",appVersion:" + appVersion;
    }

    public int getAppSys() {
        return appSys;
    }

    public void setAppSys(int appSys) {
        this.appSys = appSys;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getTerminalstate() {
        return terminalstate;
    }

    public void setTerminalstate(int terminalstate) {
        this.terminalstate = terminalstate;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
