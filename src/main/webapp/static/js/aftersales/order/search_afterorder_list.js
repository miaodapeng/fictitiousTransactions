$(function(){
	$('.J-search-btn').click(function(){
		if(!$.trim($(".J-searchAfterSalesNo").val())){
			$('.J-error').show();//文本框ID和提示用语
			return false;
		}
		
		var searchURL = '/aftersales/order/searchAfterOrder.do';
		
		$("#search").submit();
		
		var link = page_url + searchURL + '?afterSalesNo=' + $('.J-searchAfterSalesNo').val() + '&lendOut=1'; 
		window.location.href = link;
		

	})
})
function selectlendOut(afterSalesNo, goodsName,afterGoodsNum,exchangeDeliverNum,goodsStockNum,goodsId){
	window.parent && window.parent.hanlderSelect && window.parent.hanlderSelectAfterSales(afterSalesNo, goodsName,afterGoodsNum,exchangeDeliverNum,goodsStockNum,goodsId); 
	if(parent.layer!=undefined){
		parent.layer.close(index);
	}
}