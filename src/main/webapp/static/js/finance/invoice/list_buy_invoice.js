var startTime = "",endTime = "";
$(function(){
	startTime = $("#de_startTime").val();
	endTime = $("#de_endTime").val();
})
function resetPage(){
	reset();
	$("#startTime").val(startTime);
	$("#endTime").val(endTime);
}
function search(){
	var colorTypeEnable = $("#colorTypeEnable").val();
	var arr = colorTypeEnable.split("-");
	$("#colorType").val(arr[0]);
	$("#isEnable").val(arr[1]);
	$("#search").submit();
}

function exportIncomeInvoiceList(){
	var colorTypeEnable = $("#colorTypeEnable").val();
	var arr = colorTypeEnable.split("-");
	$("#colorType").val(arr[0]);
	$("#isEnable").val(arr[1]);
	checkLogin();
	location.href = page_url + '/report/finance/exportIncomeInvoiceList.do?' + $("#search").serialize();
}

function exportIncomeInvoiceDetailList(){
	var colorTypeEnable = $("#colorTypeEnable").val();
	var arr = colorTypeEnable.split("-");
	$("#colorType").val(arr[0]);
	$("#isEnable").val(arr[1]);
	checkLogin();
	location.href = page_url + '/report/finance/exportIncomeInvoiceDetailList.do?' + $("#search").serialize();
}

function sendIncomeInvoiceList(param){

	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			layer.close(index);
			checkLogin();
			var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
			$("body").prepend(div); //jq获取上一页框架的父级框架；
			var startDate = $(param).parents("div").siblings("ul").find("input[name='startTime']").val();
			var endDate = $(param).parents("div").siblings("ul").find("input[name='endTime']").val();
			$.ajax({
				type: "POST",
				url: page_url + "/finance/invoice/sendincomeinvoicelist.do",
				dataType:'json',
				data : {
					'startDate' : startDate,
					'endDate' : endDate
				},
				success: function(data){
					$(".tip-loadingNewData").remove();
					if(data && data.code == 0){
						if(data.page && data.page.totalRecord){
							layer.alert('推送成功：'+ data.page.totalRecord + '条！');
						}else{
							layer.alert('推送成功：'+ 0 + '条！');
						}
					}
					if(data && data.code == -1){//如果存在traderId为null的
						layer.alert(data.message);
					}
				},
				error:function(data){
					$(".tip-loadingNewData").remove();
					if(data.status ==1001){
						layer.alert("当前操作无权限");
					}
				}
			});
			
			layer.close(index);
		}, function(){
			
		});
}


