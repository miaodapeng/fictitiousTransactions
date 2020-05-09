
function updateSaleGoodsSave(saleorderId,saleorderGoodsIdArr){
	var deliveryDirect = 0;
	if($("input:radio[id='deliveryDirect_y']").is(":checked")){
		deliveryDirect = 1;
	}else{
		deliveryDirect = 0;
	}
	 var deliveryDirectComments = $("#deliveryDirectComments").val();
	if(deliveryDirect == '1'){
		if(deliveryDirectComments == null || deliveryDirectComments.length == 0){
			warnTips("deliveryDirectComments","选择直发时，不允许为空");//文本框ID和提示用语
			return false;
		}else if(deliveryDirectComments.length > 64){
			warnTips("deliveryDirectComments","直发备注不允许超出64位字符");//文本框ID和提示用语
			return false;
		}
	}
	checkLogin();
	$.ajax({
		type: "POST",
		url: page_url+"/order/saleorder/updateSaleGoodsSave.do",
		data : {"saleorderId":saleorderId,"saleorderGoodsIdArr":saleorderGoodsIdArr,"deliveryDirect":deliveryDirect,"deliveryDirectComments":deliveryDirectComments},
		dataType:'json',
		success: function(data){
//			refreshPageList(data);
			if(data.code==0){
				layer.alert(data.message, {icon: 1},
					function () {
						parent.location.reload();
						$('#close-layer').click();
					}
				);
			}else{
				layer.alert(data.message);
			}
		},
		error:function(data){
			if(data.status == 1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}