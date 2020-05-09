$(function(){
	// 1
	$('ul li ul li').each(function(){
		$(this).click(function(){
			if($(this).children('input').attr('type')=='radio'){
				$(this).siblings('li').children('input').removeAttr('checked','true');
				$(this).children('input').attr('checked','true');
			}
		})
	})

})