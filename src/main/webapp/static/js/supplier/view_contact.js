
//	var ajaxBack=null;
	function setDefault(id,status,traderId,type,areaId){
		checkLogin();
		if(status==0){
			status=1;
		}else{
			status=0;
		}//type=0联系人
		var traderType=$("#traderType").val();
		if(type==0){
				var traderSupplierId=$("#traderSupplierId").val();
				$.ajax({
					type: "POST",
					url: page_url+"/trader/supplier/saveSupplierDefaultContact.do",
					data: {'traderContactId':id,'isDefault':status,'traderId':traderId,'traderType':traderType,'traderSupplierId':traderSupplierId},
					dataType:'json',
					async:false,
					success: function(data){
						if(data.code==0){
							layer.alert(data.message,{
								closeBtn: 0,
								btn: ['确定'] //按钮
							}, function(){
								var st=data.data.split(",");
								var str=page_url+"/trader/supplier/getContactsAddress.do?traderId="+st[0]+"&traderSupplierId="+st[1];
								//$("#finace").attr('href',str);
								self.location.href=str;
							});
						}else{
							layer.msg(data.message);
						}
						
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				})
				return false;
			
			
		}else{//type=1地址
			
				var traderSupplierId=$("#traderSupplierId").val();
				$.ajax({
					type: "POST",
					url: page_url+"/trader/supplier/isDefaultAddress.do",
					data: {'traderAddressId':id,'isDefault':status,'traderId':traderId,'areaId':areaId,'traderType':traderType,'traderSupplierId':traderSupplierId},
					dataType:'json',
					async:false,
					success: function(data){
						if(data.code==0){
							layer.alert(data.message,{
								closeBtn: 0,
								btn: ['确定'] //按钮
							}, function(){
								var st=data.data.split(",");
								var str=page_url+"/trader/supplier/getContactsAddress.do?traderId="+st[0]+"&traderSupplierId="+st[1];
								//$("#finace").attr('href',str);
								self.location.href=str;
							});
						}else{
							layer.msg(data.message);
						}
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				})
				return false;
			
			
		}

	}

	
/*
 * 禁用
 */
function setDisabled(userId,status,type){
	checkLogin();
	var msg="";
	if(type==0){
		if(status==1){
			msg="是否启用该联系人？";
		}else{
			msg="是否禁用该联系人？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/supplier/isDisabledContact.do",
				data: {'traderContactId':userId,'isEnable':status,'traderType':$("#traderType").val()},
				dataType:'json',
				success: function(data){
					self.location.reload();
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
		});
	}else{
		if(status==1){
			msg="是否启用该地址？";
		}else{
			msg="是否禁用该地址？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/supplier/isDisabledAddress.do",
				data: {'traderAddressId':userId,'isEnable':status,'traderType':$("#traderType").val()},
				dataType:'json',
				success: function(data){
					self.location.reload();
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
}

