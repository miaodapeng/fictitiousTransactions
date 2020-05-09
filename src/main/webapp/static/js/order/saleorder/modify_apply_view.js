$(function(){
	var	url = page_url + '/order/saleorder/viewModifyApply.do?saleorderModifyApplyId='+$("#saleorderModifyApplyId").val()+'&saleorderId='+$("#saleorderId").val();
	$(window.frameElement).attr('data-url',url);
})
