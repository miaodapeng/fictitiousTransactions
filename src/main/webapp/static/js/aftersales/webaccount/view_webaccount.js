$(function(){
	$("#myform").submit(function(){
		checkLogin();
		if($("#userId").val() == 0){
			layer.alert("分配销售不允许为空");
			return false;
		}



		// 验证选择的客户归属销售  与  分配的销售是否是同一人
		var traderCustomerId = $("#traderCustomerId").val();
		if(traderCustomerId != ''){
			if($("#traderContactId").val() == 0){
				layer.alert("客户联系人不允许为空");
				return false;
			}
			//客户与关联公司
			var mobile=$("#traderContactId option:selected").attr("title");
			var mobilePhone=$("#mobilePhone").text();
			if (mobile!=mobilePhone) {
				layer.alert("注册手机号码和要关联公司的手机号码不一致");
				return false;
			}

			if($("#traderAddressId").val() == 0){
				layer.alert("客户联系地址不允许为空");
				return false;
			}
			var flag = true;
			$.ajax({
				async : false,
				type : "POST",
				url : page_url + "/aftersales/webaccount/vailTraderUser.do",
				data :{'traderCustomerId': traderCustomerId,'userId': $("#userId").val(),'owner':'after'},
				dataType : 'json',
				success : function(data) {
					if(data.code == -1){
						layer.alert(data.message);
						flag = false;return false;
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
						flag = false;return false;
					}
				}
			});
			return flag;
		}
	});
});

function transferCertificate(erpAccountId,traderId,traderCutomerId,type) {
	var _self=self;
	var index1=layer.confirm("确认覆盖原有资质信息吗？", {
		btn: ['确定','取消'] //按钮
	}, function(){
		$.ajax({
			type: "POST",
			url: "./transferCertificate.do",
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

function searchCustomer(registerPlatform) {
	if(registerPlatform==2){
		layer.alert("医械购平台注册的用户，无法更改关联公司")
	}else{
		$("#searchTrader").click();
	}
}