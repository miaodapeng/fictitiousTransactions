function subRolex() {

	var roleName = $("#roleName").val();

	if (roleName.length == 0) {
		warnTips("roleName","角色名称不能为空");//文本框ID和提示用语

		return false;
	}
	if (roleName.length<1 || roleName.length >32) {
		warnTips("roleName","角色名称为1-32个汉字长度");//文本框ID和提示用语
		return false;
	}
	var roleNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
	if(!roleNameReg.test(roleName)){
		warnTips("roleName","角色名称不允许使用特殊字符");
		return false;
	}
	if($("input[name='platformId']:checked").length <= 0){
		warnTips("platform","应用系统为空，无法提交。");
		return  false;
	}

	var m = show();
	if(m != ''){
		layer.confirm(m, {
			btn: ['确定'] //按钮
		}, function (index) {
			layer.close(index);
			submit();
		});
		return false;
	}

	submit();
}

function submit() {
	$.ajax({
		url:'./updateRole.do',
		data:$('#editRoleForm').serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			refreshNowPageList(data);//刷新父页面列表数据（保留在当前页）
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}

function show() {
	var chk_value =[];
	$("input[name='platformId']:checked").each(function(){
		chk_value.push($(this).val());
	});
	var old_value =[];
	$("input[name='choosedId']").each(function(){
		old_value.push($(this).val());
	});
	var temp =[];
	for(var i = 0; i < old_value.length; i++){
		if($.inArray(old_value[i], chk_value) == -1){
			//不存在
			temp.push(old_value[i]);
		}
	}
	var erp = $("input[name='erp']").val();
	var biz = $("input[name='biz']").val();
	var yxg = $("input[name='yxg']").val();

	var msg = '';
	var flag = 0;
	var middle = '';
	if(temp.length > 0){
		msg += "该角色已在";
		for(var j = 0; j < temp.length; j++){
			if(temp[j] == 1){
				//erp平台
				if(erp == '1'){
					//erp平台已应用
					if(middle != ''){
						middle += "和贝登ERP平台";
					}else {
						middle += "贝登ERP平台"
					}
					flag = 1;
				}
			}else if(temp[j] == 2){
				//运营后台
				if(biz == '1'){
					//运营后台平台已应用
					if(middle != ''){
						middle += "和运营后台平台"
					}else {
						middle += "运营后台平台"
					}
					flag = 1;
				}
			}else if(temp[j] == 3){
				//医械购
				if(yxg == '1'){
					//医械购平台已应用
					if(middle != ''){
						middle += "和经销商平台"
					}else {
						middle += "经销商平台"
					}
					flag = 1;
				}
			}
		}
		msg += middle;
		msg	+="设置权限，提交后将清空";
	}
	if(temp.length > 0 && flag !=0){
		return msg;
	}else {
		return '';
	}
}




