$(function(){
	$("#myform").submit(function(){
		checkLogin();
		if($("#ordergoodsStoreId").val() == 0){
			layer.alert("操作失败");
			return false;
		}
		if($("#traderCustomerId").val() == '' || $("#traderCustomerId").val() <= 0){
			layer.alert("请添加客户");
			return false;
		}
		if($("#traderContactId").val() == 0){
			layer.alert("联系人不允许为空");
			return false;
		}
		
		var mobile = $("#traderContactId").find("option:selected").attr("title");
		var mobileReg = /^1\d{10}$|^$/;
		if(mobile == ""){
			layer.alert("联系人手机号码不允许为空");
			return false;
		}
		if(!mobileReg.test(mobile)){
			layer.alert("联系人手机格式错误");
			return false;
		}
		if($("#traderAddressId").val() == 0){
			layer.alert("联系地址不允许为空");
			return false;
		}
	});
});

//客户所在地，联系人
function init(id,traderId,area){
	checkLogin();
	if(id > 0){
		$("#area").html(area);
		$.ajax({
			type : "POST",
			url : page_url+"/order/buyorder/getContactsAddress.do",
			data :{'traderId':traderId,'traderType':1},
			dataType : 'json',
			success : function(data) {
				if(data.code == 0){
					$option = "<option value='0' title='请选择'>请选择</option>";
					$.each(data.data,function(i,n){
						$option += "<option value='"+data.data[i]['traderContactId']+"' title='"+data.data[i]['mobile']+"'>"+data.data[i]['name']+"|"+data.data[i]['mobile']+"</option>";
					});
					$("select[name='traderContactId']").html($option);
					
					$option = "<option value='0'>请选择</option>";
					$.each(data.listData,function(i,n){
						$option += "<option value='"+data.listData[i]['traderAddress']['traderAddressId']+"'>"+data.listData[i]['area']+" "+data.listData[i]['traderAddress']['address']+"</option>";
					});
					$("select[name='traderAddressId']").html($option);
				}else{
					layer.alert("该客户暂无联系人/地址，请先维护客户联系人/地址");
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}else{
		layer.alert("操作失败");
	}
}