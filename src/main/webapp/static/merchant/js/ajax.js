var showError = function(req) {
    $("body").css("overflow","auto");
    $("body").html(req.responseText);
};

(function($) {

    $.convertData = function(params) {
        var castString = function(param) {
            if (param == null) {
                return null;
            }
            else if (param.constructor == String) {
                return param;
            }
            else if (param.constructor == Boolean) {
                return param;
            }
            else if (param.constructor == Date) {
                return $.formatDate(param);
            }
            else if (param.constructor == Number) {
                return param;
            }
            else if (param.constructor == Array) {
                return $.toJSON(param);
            }
            else if (param.constructor == Object) {
                return $.toJSON(param);
            }
        };

        if (params == null) return null;
        var obj = {};
        if (params.constructor == Array) {
            for (var i = 0; i < params.length; i++) {
                obj["arg" + i] = castString(params[i]);
            }
        }
        else {
            for (var k in params) {
                obj[k] = castString(params[k]);
            }
        }
        return $.param(obj);
    };

    $.ajaxGet = function(action, params, successCallback, errorCallback) {
        var ajaxData = $.convertData(params);
        var url = action + (action.indexOf("?") == -1 ? "?timeSerialize=" : "&timeSerialize=");
        url += (new Date()).getTime();
        $.ajax({
            type: "GET",
            url: url,
            dataType : "text",
            data: ajaxData,
            complete:function(request, textStatus) {
                if (request.readyState == 4 && request.status == 200) {
                    var result = request.responseText;
                    if ($.isException(result)) {
                        if (errorCallback) errorCallback($.getException(result));
                        else $("body").html($.getException(result));
                        return;
                    }
                    if (successCallback != null && $.isFunction(successCallback)) {
                        successCallback(result, textStatus);
                    }
                }
                else {
                    //alert("请求出错:readyState="+request.readyState+";status="+request.status);
                }
            }
        });
    };


    $.ajaxPost = function(action, params, successCallback, errorCallback) {
        var ajaxData = $.convertData(params);
        var url = action + (action.indexOf("?") == -1 ? "?timeSerialize=" : "&timeSerialize=");
        url += (new Date()).getTime();
        $.ajax({
            type: "POST",
            url: url,
            dataType : "text",
            data: ajaxData,
            complete:function(request, textStatus) {
                if (request.readyState == 4 && request.status == 200) {
                    var result = request.responseText;
                    if ($.isException(result)) {
                        if (errorCallback) errorCallback($.getException(result));
                        else $("body").html($.getException(result));
                        return;
                    }
                    if (successCallback != null && $.isFunction(successCallback)) {
                        successCallback(result, textStatus);
                    }
                }
                else {
                    //alert("请求出错:readyState="+request.readyState+";status="+request.status);
                }
            }
        });
    };


    $.isException = function(result) {
        return result != null && result.indexOf("<title>exception</title>") >= 0;
    };

    $.getException = function(result) {
        var inx1 = result.indexOf("<body>") + 6;
        var inx2 = result.indexOf("</body>");
        return result.substring(inx1, inx2);
    };


})(jQuery);
