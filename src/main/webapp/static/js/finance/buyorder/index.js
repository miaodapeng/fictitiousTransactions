$(function(){
	
	
});

function searchReset() {
	$("form").find("input[type='text']").val('');
	$.each($("form select"),function(i,n){
		$(this).children("option:first").prop("selected",true);
	});
	//时间重置默认
	var pre1MonthDate = $("form").find("input[name='pre1MonthDate']").val();
	var nowDate = $("form").find("input[name='nowDate']").val();
	$("form").find("input[name='searchBegintimeStr']").val(pre1MonthDate);
	$("form").find("input[name='searchEndtimeStr']").val(nowDate);
}


