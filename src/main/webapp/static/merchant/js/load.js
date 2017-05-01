(function($) {
    $.showLoad = function(msg) {

        $("<div class=\"load-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
        $("<div class=\"load-mask-msg\"></div>").html(msg ? msg : "正在处理，请稍候。。。").appendTo("body").css({display:"block",left: ($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

    };

    $.hideLoad = function() {
        $("div.load-mask").remove();
        $("div.load-mask-msg").remove();
    }

})(jQuery);