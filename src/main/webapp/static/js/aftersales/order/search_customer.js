function search(){
	checkLogin();
	$(".warning").remove();
	$("input").removeClass("errorbor");
	if($("#searchName").val()==''){
		warnErrorTips("searchName","searchNameError","搜索内容不允许为空");//文本框ID和提示用语
		return false;
	}
	$("#myform").submit();
	return false;
}

function selectObj(traderId,name){
	checkLogin();
	$("#cus",window.parent.document).hide();
	$("#search1",window.parent.document).hide();
	$("#search2",window.parent.document).show();
	$("#selname",window.parent.document).show().html(name);
	$("#payee",window.parent.document).val(name);
	$("#payee1",window.parent.document).val(name);
	$("#traderId",window.parent.document).val(traderId);
	$.ajax({
		url:page_url+'/aftersales/order/getCustomerContactAndFinace.do',
		data:{"traderId":traderId,"traderType":1},
		type:"POST",
		dataType : "json",
		async: false,
		success:function(data)
		{
			if(data.code ==0){
				var contactSelect = $("select[name='traderContactId']",window.parent.document);
				contactSelect.empty();
				var contact = "<option value=''>请选择</option>";
				for(var i=0; i<data.listData.length;i++){
					if(data.listData[i].isDefault==1){
						if(data.listData[i].telephone != null && data.listData[i].telephone != ''){
							contact += "<option selected='selected' value='"+data.listData[i].traderContactId+"'>"+data.listData[i].name+"/"+
										data.listData[i].mobile+"/"+data.listData[i].telephone +"</option>";
						}else{
							contact += "<option selected='selected' value='"+data.listData[i].traderContactId+"'>"+data.listData[i].name+"/"+
											data.listData[i].mobile+"</option>";
						}
						
					}else{
						if(data.listData[i].telephone != null && data.listData[i].telephone != ''){
							contact += "<option value='"+data.listData[i].traderContactId+"'>"+data.listData[i].name+"/"+
										data.listData[i].mobile+"/"+data.listData[i].telephone+"</option>";
						}else{
							contact += "<option value='"+data.listData[i].traderContactId+"'>"+data.listData[i].name+"/"+
										data.listData[i].mobile+"</option>";
						}
					}
				}
				contactSelect.append(contact);
				//$("#amount",window.parent.document).html(data.data.amount);
				//$("#amount1",window.parent.document).val(data.data.amount);
				$("#bank",window.parent.document).val(data.data.bank);
				$("#bank1",window.parent.document).val(data.data.bank);
				$("#bankAccount",window.parent.document).val(data.data.bankAccount);
				$("#bankAccount1",window.parent.document).val(data.data.bankAccount);
				$("#bankCode",window.parent.document).val(data.data.bankCode);
				$("#bankCode1",window.parent.document).val(data.data.bankCode);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    parent.layer.close(index);
}

