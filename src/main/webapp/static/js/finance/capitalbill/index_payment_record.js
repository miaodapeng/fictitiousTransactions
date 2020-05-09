
function searchReset() {
	$("form").find("input[type='text']").val('');
	$.each($("form select"),function(i,n){
		$(this).children("option:first").prop("selected",true);
	});
	//交易时间重置默认
	var pre1MonthDate = $("form").find("input[name='pre1MonthDate']").val();
	var nowDate = $("form").find("input[name='nowDate']").val();
	$("form").find("input[name='searchBegintimeStr']").val(pre1MonthDate);
	$("form").find("input[name='searchEndtimeStr']").val(nowDate);
}


function exportPayCapitalBillList(){
	checkLogin();
	location.href = page_url + '/report/finance/exportPayCapitalBillList.do?' + $("#search").serialize();
}

function sendPayBillToKindlee(param){
	checkLogin();
	var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
	$("body").prepend(div); //jq获取上一页框架的父级框架；
	var searchBegintime = $(param).parents("div").siblings("ul").find("input[name='searchBegintimeStr']").val();
	var searchEndtime = $(param).parents("div").siblings("ul").find("input[name='searchEndtimeStr']").val();
	$.ajax({
		type: "POST",
		url: page_url + "/finance/capitalbill/sendpaybilltokindlee.do",
		dataType:'json',
		data : {
			'searchBegintime' : searchBegintime,
			'searchEndtime' : searchEndtime
		},
		success: function(data){
			$(".tip-loadingNewData").remove();
		},
		error:function(data){
			$(".tip-loadingNewData").remove();
			if(data.status ==1001){
				layer.alert("当前操作无权限");
			}
		}
	});
}