function search() {
	checkLogin();
	if($("#searchTraderName").val()==undefined || $("#searchTraderName").val()==""){
		warnTips("searchError","查询条件不允许为空");//文本框ID和提示用语
		return false;
	}
	$("#search").submit();
}

function selectCustomer(indexId,traderId,traderName,traderCustomerId,customerType,customerNature){
	checkLogin();
	if ($("#customer_type_nature_div", window.parent.document).length > 0) {
		var customerTypeStr = '';
		if (customerType == '426') {
			customerTypeStr = '科研医疗';
		} else if (customerType == '427') {
			customerTypeStr = '临床医疗';
		}
		
		var customerNatureStr = '';
		if (customerNature == '465') {
			customerNatureStr = '分销';
		} else if (customerNature == '466') {
			customerNatureStr = '终端';
		}
		if (customerTypeStr == '' || customerNatureStr == '') {
			layer.alert("客户类型和客户性质不能为空");
			return false;
		}
		$("#customer_type_nature_div", window.parent.document).html(customerTypeStr + ' ' + customerNatureStr);
	}
	
	$("#trader_name_span_" + indexId, window.parent.document).html(traderName);
	$("#trader_id_" + indexId, window.parent.document).val(traderId);
	$("#trader_name_" + indexId, window.parent.document).val(traderName);
	$("#add_contact_" + indexId, window.parent.document).attr("layerParams", '{"width":"430px","height":"220px","title":"添加联系人","link":"./addContact.do?traderId='+traderId+'&traderCustomerId='+traderCustomerId+'"}');
	$("#add_address_" + indexId, window.parent.document).attr("layerParams",'{"width":"600px","height":"200px","title":"添加地址","link":"./addAddress.do?traderId='+traderId+'"}');
	

	if ($("#desc_div", window.parent.document).length > 0) {
		$("#desc_div", window.parent.document).show();
		$("#updateTerminalInfo", window.parent.document).hide();
		$("#customer_type_" + indexId, window.parent.document).val(customerType);
		$("#customer_nature_" + indexId, window.parent.document).val(customerNature);
	}
	//根据客户ID获取联系人列表&地址列表
	$.ajax({
		async:false,
		url: page_url + '/order/saleorder/getCustomerContactAndAddress.do',
		data:{"traderId":traderId},
		type:"POST",
		dataType : "json",
		success:function(data){
			//jsonStr = JSON.stringify(data.param);
			var contactStr = '<option value="0">请选择</option>';
			for(var i = 0; i< data.param.contactList.length; i++) {
				var isSelected = data.param.contactList[i].isDefault == 1 ? 'selected = "selected"' : '';
				contactStr += '<option value="' + data.param.contactList[i].traderContactId + '" ' + isSelected + '>' + data.param.contactList[i].name + '/' + data.param.contactList[i].telephone + '/' + data.param.contactList[i].mobile + '</option>';
			}
			$("#contact_" + indexId, window.parent.document).html(contactStr);
			
			var addressStr = '<option value="0">请选择</option>';
			for(var i = 0; i< data.param.addressList.length; i++) {
				var isSelected = data.param.addressList[i].traderAddress.isDefault == 1 ? 'selected = "selected"' : '';
				addressStr += '<option value="' + data.param.addressList[i].traderAddress.traderAddressId + '" ' + isSelected + '>' + data.param.addressList[i].area + '/' + data.param.addressList[i].traderAddress.address + '</option>';
			}
			$("#address_" + indexId, window.parent.document).html(addressStr);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
	
	if(parent.layer!=undefined){
		parent.layer.close(index);
	}
}

function switchInput(){
	checkLogin();
	$(".searchfunc").hide();
	$("#selectTerminalInfo").hide();
	$("#InputTerminalInfo").show();
}

function switchSelect(){
	checkLogin();
	$("#selectTerminalInfo").show();
	$(".searchfunc").show();
	$("#InputTerminalInfo").hide();
}
$(function(){
	var checkType = function(){
		if($('.J-type:checked').val() == '1'){
			$('.J-block1').show();
			$('.J-block2').hide();
		}else{
			$('.J-block2').show();
			$('.J-block1').hide();
		}
	}
	
	checkType();
	
	$('.J-type').change(function(){
		checkType();
	})
})




function selectlendOut(traderId, traderName,traderType){
	window.parent && window.parent.hanlderSelect && window.parent.hanlderSelect(traderId, traderName,traderType); 
	if(parent.layer!=undefined){
		parent.layer.close(index);
	}
}

$(function(){
	if($('#block_2').val()=='2') $('#radio_2').attr("checked",true);

	$('.J-search-btn').click(function(){
		if(!$.trim($(".J-searchTraderName").val())){
			$('.J-error').show();//文本框ID和提示用语
			return false;
		}
		
		var searchURL = '';
		
		if($('.J-type:checked').val() == 1){
			searchURL = '/trader/customer/searchCustomerList.do';
		}else{
			searchURL = '/order/buyorder/getSupplierByName.do';
		}
		
		$("#search").submit();
		
		var link = page_url + searchURL + '?&searchTraderName=' + $('.J-searchTraderName').val() + '&lendOut=1'; 
		window.location.href = link;
//		$.ajax({
//			async:false,
//			url: page_url + '/trader/customer/searchCustomerList.do',
//			data:{
//				"searchTraderName":$('.J-searchTraderName').val(),
//				"lendOut":1
//			},
//			type:"POST",
//			dataType : "json",
//			success:function(data){
//				
//			},
//			error:function(data){
//				if(data.status ==1001){
//					layer.alert("当前操作无权限")
//				}
//			}
//		});
	})
	
	$('.J-searchTraderName').on('keydown', function(e){
		if(e.keyCode == 13||e.keyCode == 108){
			e.preventDefault();
			$('.J-search-btn').click();
			return false;
		}
	})
	
	$('.J-add-search_customer-search').submit(function(e){
		e.preventDefault();
		return false;
	})
})





