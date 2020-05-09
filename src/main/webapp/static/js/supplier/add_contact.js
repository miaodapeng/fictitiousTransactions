$(function() {
	$("#dept span").click(function(){
		checkLogin();
		$("#department").val($(this).text());
	})
	
	$("#posi span").click(function(){
		checkLogin();
		$("#position").val($(this).text());
	})
	
	$("#myform").submit(function(){
		checkLogin();
		//姓名
		var realName = $("#name").val();
		var realNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.]{2,16}$/;
		var realMessage = "员工姓名不允许使用特殊字符";
		if(realName == ''){
			warnTips("name","员工姓名不允许为空");
			return  false;
		}else{
			delWarnTips("name");
		}
		if(realName.length < 2 || realName.length > 16){
			warnTips("name","员工姓名长度应该在2-16个字符之间");
			return false;
		}else{
			delWarnTips("name");
		}
		if(!realNameReg.test(realName)){
			warnTips("name",realMessage);
			return  false;
		}else{
			delWarnTips("name");
		}
		if($("#department").val().length > 64){
			warnTips("departmentError","部门名称长度不能超过64个字符");
			return  false;
		}else{
			delWarnTips("departmentError");
		}
		if($("#position").val().length > 64){
			warnTips("positionError","职位名称长度不能超过64个字符");
			return  false;
		}else{
			delWarnTips("positionError");
		}
		
		//电话
		var telephone = $("#telephone").val();
		var telephoneReg = /^(\d{3,4}-?)?\d{7,9}(-?\d{2,6})?$|^$/;
		var telephoneMessage = "电话格式错误";
		
		if(telephone != '' && !telephoneReg.test(telephone)){
			warnTips("telephone",telephoneMessage);
			return  false;
		}else{
			delWarnTips("telephone");
		}
		

		//手机
		var mobile = $("#mobile").val();
		var mobileReg = /^1\d{10}$|^$/;
		var mobileMessage = "手机格式错误";
		if(mobile == ''){
			warnTips("mobile","手机号不允许为空");
			return  false;
		}else{
			delWarnTips("mobile");
		}
		if(!mobileReg.test(mobile)){
			warnTips("mobile",mobileMessage);
			return  false;
		}else{
			delWarnTips("mobile");
		}
		//手机2
		var mobile2 = $("#mobile2").val();
		if(!mobileReg.test(mobile2)){
			warnTips("mobile2",mobileMessage);
			return  false;
		}else{
			delWarnTips("mobile2");
		}
		//邮箱
		var email = $("#email").val();
		var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$|^$/;
		var emailMessage = "邮箱格式错误";
		if(email != '' && !emailReg.test(email)){
			warnTips("email",emailMessage);
			return  false;
		}else{
			delWarnTips("email");
		}
		var qqReg=/^\d{5,16}$/; 
		if($("#qq").val()!='' && !qqReg.test($("#qq").val())){
			warnTips("qq","qq格式不正确");
			return  false;
		}else{
			delWarnTips("qq");
		}
		if($("#comments").val()!='' && $("#comments").val().length > 128){
			warnTips("comments","备注长度不能超过128字符");
			return  false;
		}else{
			delWarnTips("comments");
		}
		if($("#mod").val()!="mod"){
			$.ajax({
				url:page_url+'/trader/supplier/addSaveContact.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code ==0){
						var st=data.data.split(",");
						var str=page_url+"/trader/supplier/getContactsAddress.do?traderId="+st[1]+"&traderSupplierId="+st[0];
						$("#contact").attr('href',str);
						$(window.parent.document).find(".j-iframe").attr('src','').attr('src',str);
						window.parent.closeLayer();
						// window.parent.location.reload();
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
		}
	})
	
});
