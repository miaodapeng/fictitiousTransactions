$(function(){
	var saleorderId = $("input[name='saleorderId']").val();
	var	url = page_url + '/order/saleorder/editBhSaleorder.do?saleorderId='+saleorderId;
	var frameObj = window.frameElement;
	if(frameObj.src.indexOf("saleorderId")<0){
		frameObj.src = url;
	}
});


function delSaleorderGoods(saleorderGoodsId, saleorderId){
	checkLogin();
	layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./delSaleorderGoodsById.do",
				data: {'saleorderGoodsId':saleorderGoodsId,'saleorderId':saleorderId,'isDelete':1},
				dataType:'json',
				success: function(data){
					/*layer.alert(data.message,{
						closeBtn: 0,
						btn: ['确定'] //按钮
					}, function(){
						window.location.reload();
					});*/
					if (data.code == 0) {
						window.location.reload();
					} else {
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
	});
}