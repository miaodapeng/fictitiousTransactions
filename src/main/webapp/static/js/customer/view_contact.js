
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
			
				var traderCustomerId=$("#traderCustomerId").val();
				$.ajax({
					type: "POST",
					url: page_url+"/trader/customer/saveCustomerDefaultContact.do",
					data: {'traderContactId':id,'isDefault':status,'traderId':traderId,'traderType':traderType,'traderCustomerId':traderCustomerId},
					dataType:'json',
					async:false,
					success: function(data){
						if(data.code==0){
							layer.alert(data.message,{
								closeBtn: 0,
								btn: ['确定'] //按钮
							}, function(){
								var st=data.data.split(",");
								var str=page_url+"/trader/customer/getContactsAddress.do?traderId="+st[0]+"&traderCustomerId="+st[1];
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
			
				var traderCustomerId=$("#traderCustomerId").val();
				$.ajax({
					type: "POST",
					url: page_url+"/trader/customer/isDefaultAddress.do",
					data: {'traderAddressId':id,'isDefault':status,'traderId':traderId,'areaId':areaId,'traderType':traderType,'traderCustomerId':traderCustomerId},
					dataType:'json',
					async:false,
					success: function(data){
						if(data.code==0){
							layer.alert(data.message,{
								closeBtn: 0,
								btn: ['确定'] //按钮
							}, function(){
								var st=data.data.split(",");
								var str=page_url+"/trader/customer/getContactsAddress.do?traderId="+st[0]+"&traderCustomerId="+st[1];
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

function setForbidden(userId,status,type){
	checkLogin();
	var msg="";
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
			url: page_url+"/trader/customer/isDisabled.do",
			data: {'traderContactId':userId,'isEnable':status,'traderType':$("#traderType").val()},
			dataType:'json',
			success: function(data){
				var st=data.data.split(",");
				var str=page_url+"/trader/customer/getContactsAddress.do?traderId="+st[0]+"&traderCustomerId="+st[1];
				$("#contact").attr('href',str);
				self.location.href=str;
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
				url: page_url+"/trader/customer/isDisabledContact.do",
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
				url: page_url+"/trader/customer/isDisabledAddress.do",
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

function del(id,traderContactId){
	checkLogin();
	layer.confirm("是否删除当前联系人行业背景？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+"/trader/customer/delContactExperience.do",
				data:{'traderContactExperienceId':id,'traderId':$("#traderId").val(),'traderCustomerId':$("#traderCustomerId").val(),'traderContactId':traderContactId},
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						var st=data.data.split(",");
						var str=page_url+"/trader/customer/getContactInfo.do?traderId="+st[1]+"&traderCustomerId="+st[0]+"&traderContactId="+st[2];
						$("#contact").attr('href',str);
						self.location.href=str;
					}else{
						layer.alert(message);
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

	return false;
}

