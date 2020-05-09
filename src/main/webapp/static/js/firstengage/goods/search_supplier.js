$(function() {
	$("#search").click(function(){
		checkLogin();
		var name=$("#supplierName").val();
		if(name == ''){
			$("#supplierName").addClass("errorbor")
			$("#none").removeClass("none");
			return  false;
		}else{
			$("#supplierName").removeClass("errorbor")
			$("#none").addClass("none");
		}
		$("#myform1").submit();
	});

});

function addSelectSupplier(goodsId, traderSupplierId){
	checkLogin();
	layer.confirm("您是否确认选择？", 
			{ btn: ['确定','取消']}, 
			function(){
				$.ajax({
					async:false,
					url:'./saveMainSupply.do',
					data:{"goodsId":goodsId,"traderSupplierId":traderSupplierId},
					type:"POST",
					dataType : "json",
					success:function(data){
						if(data.code==0){
							layer.alert(data.message, 
									{ icon: 1 },
									function () {
										parent.location.reload();
									}
							);
						}else{
							layer.alert(data.message);
						}
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				})
			});
}

