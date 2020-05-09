function search() {
	checkLogin();
	if($("#searchTraderName").val()==undefined || $("#searchTraderName").val()==""){
		warn2Tips("searchTraderName","查询条件不允许为空");//文本框ID和提示用语
		if (!$('#searchTraderName').hasClass('errorbor')) {
			$('#searchTraderName').addClass('errorbor');
		}
		return false;
	}
	$("#search").submit();
}

function selectTrader(traderId,traderType,traderName){
	checkLogin();
	if(traderId != ''){
		$.ajax({
			async:false,
			url: page_url + '/logistics/filedelivery/getContactsAddress.do',
			data:{"traderId":traderId,"traderType":traderType},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code == 0){
					//清空客户联系人Select
					parent.$("#showTraderInfo").find("#traderContactId").empty();
					//如果有查到客户联系人，插入查到的联系人，没有查到则提示
					if(data.data.contact.length>0){
						$.each(data.data.contact,function(i,v){
							if(v.isEnable == 1){
								if(i == 0){
									parent.$("#showTraderInfo").find("#traderContactName").val(v.name);
									parent.$("#showTraderInfo").find("#mobile").val(v.mobile);
									parent.$("#showTraderInfo").find("#telephone").val(v.telephone);
								}
								if(v.isDefault == 1){
									parent.$("#showTraderInfo").find("#traderContactName").val(v.name);
									parent.$("#showTraderInfo").find("#mobile").val(v.mobile);
									parent.$("#showTraderInfo").find("#telephone").val(v.telephone);
									
									parent.$("#showTraderInfo").find("#traderContactId").append("<option value='"+v.traderContactId+"' selected>"+v.name+" | "+v.mobile+" | "+v.telephone+"</option>");
								}else{
									parent.$("#showTraderInfo").find("#traderContactId").append("<option value='"+v.traderContactId+"'>"+v.name+" | "+v.mobile+" | "+v.telephone+"</option>");
								}
							}
						});
					}else{
						parent.$("#showTraderInfo").find("#traderContactId").append("<option value='Value'>请维护客户联系人</option>");
					}
					//清空客户地址Select
					parent.$("#showTraderInfo").find("#traderAddressId").empty();
					//如果有查到客户地址，插入查到的地址，没有查到则提示
					if(data.data.address.length>0){
						var enableNum = 0;
						$.each(data.data.address,function(i,v){
							if(v.traderAddress.isEnable == 1){
								if(i == 0 || enableNum == i){
									parent.$("#showTraderInfo").find("#address").val(v.traderAddress.address);
									parent.$("#showTraderInfo").find("#area").val(v.area);
								}
								if(v.traderAddress.isDefault == 1){
									parent.$("#showTraderInfo").find("#address").val(v.traderAddress.address);
									parent.$("#showTraderInfo").find("#area").val(v.area);
									
									parent.$("#showTraderInfo").find("#traderAddressId").append("<option value='"+v.traderAddress.traderAddressId+"' selected>"+v.area+" | "+v.traderAddress.address+"</option>");
								}else{
									parent.$("#showTraderInfo").find("#traderAddressId").append("<option value='"+v.traderAddress.traderAddressId+"'>"+v.area+" | "+v.traderAddress.address+"</option>");
								}
							}else{
								enableNum = i+1;
							}
						});
					}else{
						parent.$("#showTraderInfo").find("#traderAddressId").append("<option value='Value'>请维护客户地址</option>");
					}
					parent.$("#traderId").val(traderId);
					parent.$("#traderName").val(traderName);
					parent.$("#TraderNameDiv").text(traderName);
					parent.$("#showTraderInfo").show();
					parent.$("#traderNameLi").hide();
					parent.layer.close(index);
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

function switchInput(){
	$(".searchfunc").hide();
	$("#selectTerminalInfo").hide();
	$("#InputTerminalInfo").show();
}

function switchSelect(){
	$("#selectTerminalInfo").show();
	$(".searchfunc").show();
	$("#InputTerminalInfo").hide();
}

