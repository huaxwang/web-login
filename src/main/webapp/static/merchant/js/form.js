$.extend($.fn.datebox.defaults, {
    formatter:function(date) {
        return date.format("yyyy-MM-dd");
    },
    parser:function(value) {
        var d = $.parseDate(value);
        return d != null ? d : new Date();
    }
});
/*
$.fn.datebox.defaults.formatter = function(date) {
    return date.format("yyyy-MM-dd");
};

$.fn.datebox.defaults.parser = function(value) {
    var d = $.parseDate(value);
    return d != null ? d : new Date();
};
*/

(function($) {
    var focusElement=function(element){
        var input;
        if(element.constructor==String){
            $("#"+$.trimChar(element,"#")).focus();
        }
        else if(element.constructor==Object){
            $(element).focus();
        }
    };
    /**
     * 序列化表单对象为json对象,主表数据转换
     */
    $.fn.extend({
        serializeJson:function() {

            var inputs = $(this).find(":input[name]");
            if (!inputs || inputs.length == 0) return null;
            var json = {};

            for (var i = 0; i < inputs.length; i++) {
                var elem = $(inputs[i]);
                if (elem.is(':checkbox[checked=false]')) continue;
                if (elem.is(':radio[checked=false]')) continue;
                if (json.hasOwnProperty(elem.attr('name'))) {
                    if ((json[elem.attr('name')]).constructor != Array) {
                        json[elem.attr('name')] = [json[elem.attr('name')]];
                    }
                    if (elem.hasClass("easyui-numberbox")){
                    	json[elem.attr('name')].push(elem.floatValue()!=null?elem.floatValue():"");
                    }
                    else if (elem.hasClass("easyui-datebox")) {
                        json[elem.attr('name')].push(elem.dateValue()!=null?elem.dateValue().format("yyyy-MM-dd") : "");
                    }
                    else {
                        json[elem.attr('name')].push(elem.val());
                    }
                } else {
                	if (elem.hasClass("easyui-numberbox")) {
                        json[elem.attr('name')] = elem.floatValue()!=null?elem.floatValue():"";
                    }
                    else if (elem.hasClass("easyui-datebox")) {
                        json[elem.attr('name')] = elem.dateValue()!=null ? elem.dateValue().format("yyyy-MM-dd") : "";
                    }
                    else {
                        json[elem.attr('name')] = elem.val();//this.value;
                    }
                }
            }

            for (var k in json)  return json;
            return null;
        },

        /**
         * 序列化表单对象为JsonArray对象,从表数据的转换
         * @param tagName 对象集合中的元素的tagname （通常是tr）
         */
        serializeJsonArray : function(tagName) {
            var tag = (tagName) ? tagName : "tr";
            var elementArray = $(this).find(tag);
            var jsonArray = [];
            for(var i=0;i<elementArray.length;i++){
                var json = $(elementArray[i]).serializeJson();
                if (json) jsonArray.push(json);
            }
            return jsonArray;
        },

        //        serializeJsonArrayTd : function(tagName) {
        //            var tag = (tagName) ? tagName : "tr";
        //            var elem = $(this);
        //            var elementArray;
        //            elementArray = elem.find(tag);
        //            var jsonArray = [];
        //            $.each(elementArray, function() {
        //                alert(1)
        //                var tdArray = $(this).find('td')
        //                alert(2)
        //                $.each(tdArray, function() {
        //                    var json = $(this).serializeJson();
        //                    alert($.toJSON(json))
        //                    if (json) {
        //                        jsonArray.push(json);
        //                    }
        //                })
        //            });
        //            return jsonArray;
        //        },

        /**
         * 将json对象转换成字符串设置到隐藏域中
         * @param json
         */
        setJson :function(json) {
            $(this).val($.toJSON(json));
        } ,

        dateValue:function() {
            return $.parseDate($(this).val());
        },
        
        floatValue:function(){
        	return $.isFloat($(this).val())?parseFloat($(this).val()):null;
        },
        
        integerValue:function(){
        	return $.isInteger($(this).val())?parseInt($(this).val()):null;
        },
        

        checkEmpty:function(msg, focusInput) {
            if ($.isEmpty($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return true;
            }
            else {
                return false;
            }
        },

        checkNotEmpty:function(msg, focusInput){
            if ($.isEmpty($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return false;
            }
            else{
                return true;
            }
        },

        checkDigit:function(msg, focusInput) {
            if (!$.isDigit($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return false;
            }
            else {
                return true;
            }
        },

        checkInteger:function(msg, focusInput) {
            if (!$.isInteger($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return false;
            }
            else {
                return true;
            }
        },

        checkFloat:function(msg, focusInput) {
            if (!$.isFloat($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return false;
            }
            else {
                return true;
            }
        },


        checkDate:function(msg, focusInput) {
            if (!$.isDate($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return false;
            }
            else {
                return true;
            }
        },

        checkEmail:function(msg, focusInput) {
            if (!$.isEmail($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return false;
            }
            else {
                return true;
            }
        },

        checkUrl:function(msg, focusInput) {
            if (!$.isUrl($(this).val())) {
                alert(msg);
                focusElement(focusInput!=null?focusInput:$(this));
                return false;
            }
            else {
                return true;
            }
        },
        /**
         * 复选框的状态选择
         * IDName:checkbox框id或name
         * status:选中状态 true or false
         */
        statusChecked:function (IDName, status) {
            var objCheckBoxs = this.getObjects(IDName);
            if (objCheckBoxs != null) {
                for (var i = 0; i < objCheckBoxs.length; i++) {
                    objCheckBoxs[i].checked = (status != null) ? status : (!objCheckBoxs[i].checked);
                }
            }
        },


        /**
         * 获得对象数组
         * elementName:对象数组的名字
         * objWindow:window对象
         * 梅剑
         */
        getObjects: function (elementName, objWindow) {
            var objs;
            if (objWindow == null) {
                objs = window.document.getElementsByName(elementName);
            }
            else {
                objs = objWindow.document.getElementsByName(elementName);
            }
            return objs;
        },
        /**
         * 获得对象
         * elementID:对象的id
         * objWindow:window对象
         * 梅剑
         */
        getObject: function (elementID, objWindow) {
            var obj;
            if (objWindow == null) {
                obj = window.document.getElementById(elementID);
            }
            else {
                obj = objWindow.document.getElementById(elementID);
            }
            return obj;
        },
        /**
         * 所有子复选框被选中后，父复选框自动被选中；反之，则未选中
         * cellsBoxName 子复选框名称
         * mainBoxName全选父复选框名称
         * written by Clark Fan
         */
        superStatusAutoChecked:function(cellsBoxName, mainBoxName) {
            var objLen = 0;
            var objs = this.getObjects(cellsBoxName);
            for (var i = 0; i < objs.length; i++) {
                if (objs[i].checked) {
                    objLen += 1;
                }
            }
            this.getObject(mainBoxName).checked = objs.length == objLen;
        }

    });


})(jQuery);
