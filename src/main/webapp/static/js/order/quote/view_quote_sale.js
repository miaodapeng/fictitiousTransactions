$(function(){
	var	url = page_url + '/order/quote/getQuoteDetail.do?quoteorderId='+$("#quoteorderId").val()+'&viewType=3';
	if($(window.frameElement).attr('src').indexOf("viewType=3")<0){
		$(window.frameElement).attr('data-url',url);
	}
});
function revokeValId(){
	checkLogin();
	layer.confirm("您是否确认撤销该报价生效状态？", {
		btn : [ '确定', '取消' ]// 按钮
	}, function() {
		$(window.frameElement).attr('data-url','');
		$("#viewQuoteOperationForm").submit();
		/*$.ajax({
			async : false,
			url : './revokeValIdStatus.do',
			data : {"validStatus":0,"quoteorderId":quoteorderId},
			type : "POST",
			dataType : "json",
			success : function(data) {
				layer.alert(data.message, {
					icon : (data.code == 0 ? 1 : 2)
				}, function() {
					if (parent.layer != undefined) {
						parent.layer.close(index);
					}
					parent.location.reload();
				});
			}
		})*/
	});
}
function printBj(obj){
	
	var url =page_url+"/order/quote/printOrder.do?quoteorderId="+obj;
	var name="打印报价单";
	var w=800;
	var h=600;
	window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}
function quoteorderToSaleorder(quoteorderId) {
	checkLogin();
	index = layer.confirm("是否确认转为订单?", {
		btn : [ '是', '否' ]// 按钮
	}, function() {
		var formToken = $("input[name='formToken']").val();
		$.ajax({
			async : false,
			url : page_url + '/order/saleorder/preQuoteorderToSaleorder.do',
			data : {"quoteorderId":quoteorderId,"formToken":formToken},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					layer.close(index);
					window.location.href = page_url + "/order/saleorder/quoteordertosaleorder.do?quoteorderId="+quoteorderId;
				}else if(data.code == -1){
					layer.alert(data.message);
					location.reload();
				}
				else {
					if (data.status == 2) {
						layer.close(index);
						index1 = layer.confirm(data.message, {
							btn : [ '是', '否' ]// 按钮
						}, function() {
							layer.close(index1);
							window.location.href = page_url + "/order/saleorder/quoteordertosaleorder.do?quoteorderId="+quoteorderId;
						});
					} else if (data.status == 1) {
						layer.alert(data.message);
					}
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});
}
