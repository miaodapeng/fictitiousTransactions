$(function() {
	$("#add_submit").click(function(){
		checkLogin();
		var traderId = $("#traderId").val();
		var indexId = $("#indexId").val();
		
		$(".warning").remove();
		
		//姓名
		var realName = $("#name").val();
		var realNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.]{2,16}$/;
		var realMessage = "员工姓名不允许使用特殊字符";
		if(realName == ''){
			warnTips("name","员工姓名不允许为空");
			return  false;
		}
		if(realName.length < 2 || realName.length > 16){
			warnTips("name","员工姓名长度应该在2-16个字符之间");
			return false;
		}
		if(!realNameReg.test(realName)){
			warnTips("name",realMessage);
			return  false;
		}
		//手机
		var mobile = $("#mobile").val();
		var mobileReg = /^1\d{10}$|^$/;
		var mobileMessage = "手机格式错误";
		if(mobile == ''){
			warnTips("mobile","手机号不允许为空");
			return  false;
		}
		if(!mobileReg.test(mobile)){
			warnTips("mobile",mobileMessage);
			return  false;
		}
		//邮箱
		/*
		var email = $("#email").val();
		var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$|^$/;
		var emailMessage = "邮箱格式错误";
		if(email == ''){
			warnTips("email","邮箱不允许为空");
			return  false;
		}
		if(!emailReg.test(email)){
			warnTips("email",emailMessage);
			return  false;
		}*/
		
		$.ajax({
			async:false,
			url:page_url+'/trader/customer/addSaveContact.do',
			data:$("#confirmForm").serialize(),
			type:"POST",
			dataType : "json",
			success:function(data2){
				if (data2.code == 0) {
					var dataArr = data2.data.split(',');
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
								var contactStr = '<option value="0">请选择</option>';
								/*for(var i = 0; i< data.param.contactList.length; i++) {
									var isSelected = data.param.contactList[i].traderContactId == dataArr[2] ? 'selected = "selected"' : '';
									contactStr += '<option value="' + data.param.contactList[i].traderContactId + '" ' + isSelected + '>' + data.param.contactList[i].name + '/' + data.param.contactList[i].telephone + '/' + data.param.contactList[i].mobile + '</option>';
								}*/
								if(indexId == 'webId'){
									for(var i = 0; i< data.param.contactList.length; i++) {
										var isSelected = data.param.contactList[i].traderContactId == dataArr[2] ? 'selected = "selected"' : '';
										contactStr += '<option value="' + data.param.contactList[i].traderContactId + '" ' + isSelected + '>' + data.param.contactList[i].name + '|' + data.param.contactList[i].mobile + '</option>';
									}
									$("#traderContactId", window.parent.document).html(contactStr);
								} else {
									for(var i = 0; i< data.param.contactList.length; i++) {
										var isSelected = data.param.contactList[i].traderContactId == dataArr[2] ? 'selected = "selected"' : '';
										contactStr += '<option value="' + data.param.contactList[i].traderContactId + '" ' + isSelected + '>' + data.param.contactList[i].name + '/' + data.param.contactList[i].telephone + '/' + data.param.contactList[i].mobile + '</option>';
									}
									$("#contact_" + indexId, window.parent.document).html(contactStr);
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
				//refreshPageList(data);
			},
			error:function(data2){
				if(data2.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
	})
});
