$(function() {
	$("#add_submit").click(function(){
		checkLogin();
		var traderId = $("#traderId").val();
		var indexId = $("#indexId").val();
		
		$(".warning").remove();
		
		if($("select[name='province']").val() == 0){
			warnTips("area_div","地区不允许为空");
			return false;
		}
		if(($("select[name='zone']").val() == 0 && $("select[name='zone'] option").length > 1)
				|| $("select[name='city']").val() == 0 ){
			warnTips("area_div","地区需选择最小级");
			return false;
		}

		var pattern = new RegExp("[`~!@$^&*=|{}':;'\\[\\].<>/?~！@￥……&*——|{}【】‘；：”“'。、？]");
		if($("#address").val()==''){
			warnTips("address","地址不允许为空");
			return  false;
		}
		if(pattern.test($("#address").val())){
			warnTips("address","地址不能包含特殊字符");
			return  false;
		}
		if($("#address").val().length >128){
			warnTips("address","地址长度不能超过128个字符");
			return  false;
		}
		$.ajax({
			url:page_url+'/trader/customer/saveAddress.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data2)
			{
				if (data2.code == 0) {
					//var dataArr = data2.data.split(',');
					//根据客户ID获取联系人列表&地址列表
					$.ajax({
						async:false,
						url: page_url + '/order/saleorder/getCustomerContactAndAddress.do',
						data:{"traderId":traderId},
						type:"POST",
						dataType : "json",
						success:function(data){
							//jsonStr = JSON.stringify(data.param);
							if (data.code == 0) {
								var addressStr = '<option value="0">请选择</option>';
								if(indexId == 'webId'){
									for(var i = 0; i< data.param.addressList.length; i++) {
										var isSelected = data.param.addressList[i].traderAddress.traderAddressId == data2.data ? 'selected = "selected"' : '';
										addressStr += '<option value="' + data.param.addressList[i].traderAddress.traderAddressId + '" ' + isSelected + '>' + data.param.addressList[i].area + '&nbsp;' + data.param.addressList[i].traderAddress.address + '</option>';
									}
									$("#traderAddressId", window.parent.document).html(addressStr);
								} else {
									for(var i = 0; i< data.param.addressList.length; i++) {
										var isSelected = data.param.addressList[i].traderAddress.traderAddressId == data2.data ? 'selected = "selected"' : '';
										addressStr += '<option value="' + data.param.addressList[i].traderAddress.traderAddressId + '" ' + isSelected + '>' + data.param.addressList[i].area + '/' + data.param.addressList[i].traderAddress.address + '</option>';
									}
									$("#address_" + indexId, window.parent.document).html(addressStr);
								}

								if(parent.layer!=undefined){
									parent.layer.close(index);
								}
							} else {
								layer.alert(data.message,{ icon: 2 });
							}
						},
						error:function(data){
							if(data.status ==1001){
								layer.alert("当前操作无权限")
							}
						}
					});
				} else {
					layer.alert(data2.message,{ icon: 2 });
				}
				
				/*layer.alert(data.message,{
					closeBtn: 0,
					btn: ['确定'] //按钮
				}, function(){
					window.parent.location.reload();
				});
				if (data.code == 0) {
					window.parent.location.reload();
				} else {
					layer.alert(data.message,{ icon: 2 });
				}*/
			},
			error:function(data2){
				if(data2.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		return false;
	})
	
});
