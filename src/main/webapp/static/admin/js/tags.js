$(function(){
	$('.table-border tbody .td .tagList').each(function(){
		var self = $(this);
		var tluh = self.find('ul').height();
		if(parseInt(tluh) > 22){
			self.prepend('<em class="expand">展开</em>')
			self.height(22);
		}
		self.delegate('em.expand','click',function(){
			self.removeAttr('style');
			$(this).removeAttr('class').addClass('retract').text('收起');
		});
		self.delegate('em.retract','click',function(){
			self.height(22);
			$(this).removeAttr('class').addClass('expand').text('展开');
		});
	});
})