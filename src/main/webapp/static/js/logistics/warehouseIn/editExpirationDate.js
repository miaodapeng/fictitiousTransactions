function submitEdit() {
	checkLogin();
	 var total=0;
	 var batchNumber = $("input[name='batchNumber']").val();
	 var beforeParams = $("input[name='beforeParams']").val();
	 if(batchNumber == ""){
		 	warn2Tips("batchNumber", "批次号不允许为空");
			return false;
	 }
	 if(batchNumber.length > 64){
			warn2Tips("batchNumber","批次号不允许超过1024个字符");
			return false;
	 }
	
	 var expirationDate = $("input[name='expirationDate']").val();
	 var warehouseGoodsOperateLogId = $("input[name='warehouseGoodsOperateLogId']").val();
		$.ajax({
			async : false,
			url : page_url + '/logistics/warehousein/saveExpirationDate.do',
			data : {
				"batchNumber" : batchNumber,
				"expirationDate" : expirationDate,
				"warehouseGoodsOperateLogId" : warehouseGoodsOperateLogId,
				"beforeParams":beforeParams
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				refreshPageList(data)
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
}