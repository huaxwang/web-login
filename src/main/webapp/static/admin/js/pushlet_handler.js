// ********************************************************************************************************************
/**
 * 描述：〈Pushlet数据处理-入口方法〉 <br/>
 * 作者：xuan.zhou@rogrand.com <br/>
 * 生成日期：2014-8-23 <br/>
 * 
 * @param subject 推送主题
 * @param sessionId 会话ID
 * @param pushType 推送类型
 * @param data 推送内容（JSON数据）
 */
function pushletHandler(subject, sessionId, pushType, data) {
	if(subject.toLowerCase() == "listen_service_process_notice"){
		doNoResponseServiceNotice(subject, sessionId, pushType, data);
    }
}
// ********************************************************************************************************************



// ********************************************************************************************************************
function _1(el, _2, _3, _4) {
    var _5 = $(el).window("window");
    if (!_5) {
        return;
    }
    switch (_2) {
    case null:
        _5.show();
        break;
    case "slide":
        _5.slideDown(_3);
        break;
    case "fade":
        _5.fadeIn(_3);
        break;
    case "show":
        _5.show(_3);
        break;
    }
    var _6 = null;
    if (_4 > 0) {
        _6 = setTimeout(function() {
            _7(el, _2, _3);
        },
        _4);
    }
    _5.hover(function() {
        if (_6) {
            clearTimeout(_6);
        }
    },
    function() {
        if (_4 > 0) {
            _6 = setTimeout(function() {
                _7(el, _2, _3);
            },
            _4);
        }
    });
};
function _7(el, _8, _9) {
    if (el.locked == true) {
        return;
    }
    el.locked = true;
    var _a = $(el).window("window");
    if (!_a) {
        return;
    }
    switch (_8) {
    case null:
        _a.hide();
        break;
    case "slide":
        _a.slideUp(_9);
        break;
    case "fade":
        _a.fadeOut(_9);
        break;
    case "show":
        _a.hide(_9);
        break;
    }
    setTimeout(function() {
        $(el).window("destroy");
    },
    _9);
};
// ********************************************************************************************************************



/**
 * 描述：〈处理未响应的服务通知事件〉 <br/> 
 * 作者：xuan.zhou@rogrand.com <br/> 
 * 生成日期：2014-8-23 <br/>
 */
var buyServiceNum = 0;
var askServiceNum = 0;
function doNoResponseServiceNotice(subject, sessionId, pushType, data) {
	// alert("serviceType:" + data.serviceType + ", serviceId:" + data.serviceId);
	
	if(data.serviceType == "1"){
		buyServiceNum = buyServiceNum + 1;
	}else{
		askServiceNum = askServiceNum + 1;
	}
	
	var contentHtml = "";
	if(buyServiceNum > 0&&data.tip!="") {
		contentHtml += "有来自<font color='red'>" + data.tip + "</font>的新订单!<br/>";
	}
	if(askServiceNum > 0) {
		contentHtml += "您有<font color='red'>" + askServiceNum + "</font>个问诊服务需要处理!<br/>";
	}
    
	$(".messager-body").window('close');
    $.messager.show({
        title:"消息提醒",
        msg:contentHtml,
        timeout:0,
        showType:"slide"
    });
}
