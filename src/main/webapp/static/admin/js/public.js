/**
 * @describe 通过压缩的url浏览原始图片
 * @param imgUrl 压缩图片的url
 */
function picImgLink(imgUrl) {
	console.info(imgUrl);
	window.open(imgUrl.replace('_MIN', ''), '图片预览', '');
};

/**
 *  验证输入的只能是正整数（包括零）
 */
$.extend($.fn.validatebox.defaults.rules, {   
    positiveInteger: {   
        validator: function(value, param){
        	var reg =  /^\d+$/;
            return reg.test(value);
        },   
        message: '请输入正整数.'  
    }   
}); 

/**
 *  输入框不为空
 */
$.extend($.fn.validatebox.defaults.rules, {   
	notNull: {   
        validator: function(value, param){
            return value.length > 0;
        },   
        message: '该项不为空.'
    }   
}); 
