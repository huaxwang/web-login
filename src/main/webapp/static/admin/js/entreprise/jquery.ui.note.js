(function( $ ) {
$.widget( "ui.note", {
	version: "1.10.2",
	counter: 0,
	options: {
		isFloat: true,
		delay: 6000,
		speed: 500
	},
	
	_create: function(){
		this.note = $("#ui-note");
		if(!this.note[0]){
			this.note = $("<div id='ui-note'></div>");
			this.note.addClass("ui-note");
							
			$("body").append(this.note);
		}
			
		if(this.options.isFloat){
			this.note.addClass("ui-note-float");
		}
	},
	
	add: function(msg){
		if(msg.type || msg.message)
			this._addItem(msg);
		if(msg.length){
			for(var i = 0; i < msg.length; i++){
				if(msg[i].type || msg[i].message)
					this._addItem(msg[i]);
			}
		}
	},
	
	clear: function() {
		var items = this.note.children();
		for(var i=0;i<items.length;i++){
			setTimeout('$("#'+items[i].id+'").slideUp('+this.options.speed+');',0*this.options.speed);
		}
	},
	
	_addItem: function(msg){
		var noteitem = $("<div id='ui-note-item-"+this.counter+"'></div>");
		this.counter++;
		noteitem.addClass("ui-note-item ui-note-" + msg.type).text(msg.message);
		this.uiDialogTitlebarClose = $("<button></button>")
			.button({
				label: "close",
				icons: {
					primary: "ui-icon-closethick"
				},
				text: false
			})
			.addClass("ui-dialog-titlebar-close")
			.appendTo( noteitem );
		var cmd = '$(document).note("close","#'+noteitem.attr("id")+'");';
		this.uiDialogTitlebarClose.click(function(event){
			event.preventDefault();
			eval('$(document).note("close","#'+noteitem.attr("id")+'");');
		});
			
		this.note.append(noteitem);
		noteitem.hide().slideDown(this.options.speed);
		// console.log(cmd);
		var delay;
		if(typeof(msg.delay)!=="undefined")
			delay=msg.delay;
		else
			delay = this.options.delay;
		if(delay > 0)
			setTimeout(cmd,delay);
	},
	
	close: function(item){
		this._close(item);
	},
	
	_close: function(item) {
		me = $("#ui-note "+item);
		me.slideUp(this.options.speed,function(){ me.remove(); });
	},
	
	/*_setOptions: function(options) {
		$.each(options, function(key, value) {
			this._setOption(key, value);
		});
	},*/
	
	_setOption: function(key, value) {
		this._super( key, value );
		
		if( key === "isFloat") {
			this.note.removeClass("ui-note-float");
			if(value){
				this.note.addClass("ui-note-float");
			}
		}
	}
});
}( jQuery ) );
