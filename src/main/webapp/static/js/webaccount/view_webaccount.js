function saveWebAccount(){
	checkLogin();
	if($("#traderCustomerId").val() == '' || $("#traderCustomerId").val() <= 0){
		layer.alert("请添加客户");
		return false;
	}
	if($("#traderContactId").val() == 0){
		layer.alert("联系人不允许为空");
		return false;
	}

	if($("#traderAddressId").val() == 0){
		layer.alert("联系地址不允许为空");
		return false;
	}
	// 验证选择的客户归属销售  与  当前销售是否是同一人
	var traderCustomerId = $("#traderCustomerId").val();
	if(traderCustomerId != ''){
		$.ajax({
			async : false,
			type : "POST",
			url : page_url + "/trader/accountweb/vailTraderUser.do",
			data :{'traderCustomerId': traderCustomerId,'owner':'sale'},
			dataType : 'json',
			success : function(data) {
				if(data.code == -1){
					layer.confirm(data.message, {
						btn: ['确定', '取消'] //按钮
					}, function () {
						$("input[name='optType']").val("saleWebConfirm");
						$('#myform').submit();
						return true;// 确认
					}, function () {
						// 取消
					});
				} else {
					$('#myform').submit();
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});

	}
};

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

function addContacts() {
	$('#addCustomerContacts').attr('layerparams','{"width":"430px","height":"220px","title":"添加联系人","link":"/order/saleorder/addContact.do?traderId='+$('#traderId').val()+'&traderCustomerId='+$('#traderCustomerId').val()+'&indexId=webId"}');
	$('#addCustomerContacts').click();
}
function addAddress() {
	$('#add_address').attr('layerparams','{"width":"600px","height":"200px","title":"添加地址","link":"/order/saleorder/addAddress.do?traderId='+$('#traderId').val()+'&indexId=webId"}');
	$('#add_address').click();
}

function transferCertificate(erpAccountId,traderId,traderCutomerId,type) {
	var _self=self;
	var index1=layer.confirm("确认覆盖原有资质信息吗？", {
		btn: ['确定','取消'] //按钮
	}, function(){
		$.ajax({
			type: "POST",
			url: page_url+"/aftersales/webaccount/transferCertificate.do",
			data: {'erpAccountId':erpAccountId,'traderId':traderId,'type':type},
			dataType:'json',
			success: function(data){
				if (data.code == 0) {
					layer.close(index1)
					layer.confirm("操作成功", {
						btn: ['返回','维护公司资质'] //按钮
					}, function(){
						window.location.reload();
					}, function(){
						window.location.reload();
						var frontPageId = $(window.parent.parent.document).find('.active').eq(1).attr('id');
						var url=page_url+"/trader/customer/getFinanceAndAptitude.do?traderId="+traderId+"&traderCustomerId="+traderCutomerId;
						var item = { 'id': traderCutomerId, 'name':"财务与资质信息", 'url': url, 'closable': true };
						_self.parent.parent.closableTab.addTab(item);
						_self.parent.parent.closableTab.resizeMove();
						$(window.parent.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
					});

				} else {
					layer.close(index1)
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

function searchCustomer(registerPlatform) {
	if(registerPlatform==2){
		layer.alert("医械购平台注册的用户，无法更改关联公司")
	}else{
		$("#searchTrader").click();
	}
}