$(function(){
	$("input[name='realName']").focus();
	
	//用户名检测
	$("input[name='username']").change(function(){
		checkLogin();
		if($(this).val() != ''){
			$.ajax({
				type: "POST",
				url: page_url+'/system/user/getuser.do',
				data: {'username':$(this).val(),'userId':$("input[name='userId']").val()},
				dataType:'json',
				success: function(data){
					if(data.code != 0){
						warnTips("username",data.message);
					}else{
						$("#username").removeClass("errorbor");
						$("#username").parents('li').find('.warning').remove();
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	});
	
	//工号检测
	$("input[name='number']").change(function(){
		checkLogin();
		if($(this).val() != ''){
			var numberReg = /^[a-zA-Z0-9]{1,16}$|^$/;
			if($(this).val() != '' && $(this).val().length > 16){
				warnTips("number","工号长度不允许超过16个字符");
				return  false;
			}else if(!numberReg.test($(this).val())){
				warnTips("number","工号格式错误");
				return  false;
			}else{
				$.ajax({
					type: "POST",
					url: page_url+'/system/user/getuser.do',
					data: {'number':$(this).val(),'userId':$("input[name='userId']").val()},
					dataType:'json',
					success: function(data){
						if(data.code != 0){
							warnTips("number",data.message);
						}else{
								$("#number").removeClass("errorbor");
								$("#number").parents('li').find('.warning').remove();
						}
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
			}
		}
	});
	
	$("#userform").submit(function(){
		checkLogin();
		$(".warning").remove();
		$("select").removeClass("errorbor");
		//员工姓名
		var realName = $("input[name='realName']").val();
		var realNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.]{2,16}$/;
		var realMessage = "员工姓名不允许使用特殊字符";
		if(realName == ''){
			warnTips("realName","员工姓名不允许为空");
			return  false;
		}
		if(realName.length < 2 || realName.length > 16){
			warnTips("realName","员工姓名长度应该在2-16个字符之间");
			return false;
		}
		if(!realNameReg.test(realName)){
			warnTips("realName",realMessage);
			return  false;
		}
		if($("input[name='staff']:checked").length <= 0){
			warnTips("staff","请选择是否为贝登员工");
			return  false;
		}
		var value = $("input[name='staff']:checked").val();
		var belongCompanyName = $("input[name='belongCompanyName']").val();
		if((value == 0 || value == '0') && belongCompanyName == ''){
			//选择了非贝登员工但是没填所属公司
			warnTips("showBelongCompanyName","请填写所属公司");
			return  false;
		}

		if($("input[name='systemId']:checked").length <= 0){
			warnTips("system","请选择可登录系统");
			return  false;
		}
		//可登录系统
		var systems = '';
		$.each($("input[name='systemId']:checked"),function(i,n){
			systems += $(this).val()+',';
		});
		$("input[name='systems']").val(systems.substring(0, systems.lastIndexOf(',')));

		//部门职位
		var org = true;
		var posit = true;
		var orgMessage = "请选择部门";
		var positMessage = "请选择职位";
		var orgIds = '';
		var positionIds = '';
		$.each($("select[name='orgId']"),function(i,n){
			if($(this).val() == 0){
				$(this).addClass("errorbor");
				org = false;
			}else{
				orgIds += $(this).val() + ",";
			}
		});
		if(!org){
			$("div[class='career-one']:last").after('<div class="warning" style=" margin: -8px 0 4px;">请选择部门</div>');
			//warnTips("orgPositWarn",orgMessage);
			$("#firstOrg").focus();
			return  false;
		}
		$.each($("select[name='positionId']"),function(i,n){
			if($(this).val() == 0){
				posit = false;
				$(this).addClass("errorbor");
			}else{
				positionIds += $(this).val() + ",";
			}
		});
		
		if(!posit){
			$("div[class='career-one']:last").after('<div class="warning" style=" margin: -8px 0 4px;">请选择职位</div>');
			//warnTips("orgPositWarn",positMessage);
			$("#firstOrg").focus();
			return  false;
		}
		//直接上级
		
		//用户名
		var username = $("input[name='username']").val();
		var usernameReg = /^[a-zA-Z0-9\.]{2,32}$/;
		var usernameMessage = "用户名不允许使用汉字和特殊字符";
		if(username == ''){
			warnTips("username","用户名不允许为空");
			return  false;
		}
		if(username.length < 2 || username.length > 32){
			warnTips("username","用户名长度应该在2-32个字符之间");
			return false;
		}
		if(!usernameReg.test(username)){
			warnTips("username",usernameMessage);
			return  false;
		}
		//密码
		var userId = $("input[name='userId']").val();
		var password = $("input[name='password']").val();
		var repassword = $("input[name='repassword']").val();
		var passwordReg = /^[a-zA-Z0-9]{6,16}$/;
		var passwordMessage = "密码由6-16位英文或数字组成";
		var repasswordMessage = "密码不一致";
		if(userId != ""){
			//编辑
			if(password != "" || repassword != ''){
				if(!passwordReg.test(password)){
					warnTips("password",passwordMessage);
					return  false;
				}
				
				if(password != repassword){
					warnTips("repassword",repasswordMessage);
					return  false;
				}
			}
			
		}else{
			//新增
			if(!passwordReg.test(password)){
				warnTips("password",passwordMessage);
				return  false;
			}
			if(password != repassword){
				warnTips("repassword",repasswordMessage);
				return  false;
			}
			
		}
		//邮箱
		var email = $("input[name='email']").val();
		var emailReg = /^[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)?@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$|^$/;
		var emailMessage = "邮箱格式错误";
		if(!emailReg.test(email)){
			warnTips("email",emailMessage);
			return  false;
		}
		//手机
		var mobile = $("input[name='mobile']").val();
		var mobileReg = /^1\d{10}$|^$/;
		var mobileMessage = "手机格式错误";
		if(!mobileReg.test(mobile)){
			warnTips("mobile",mobileMessage);
			return  false;
		}
		//电话
		var telephone = $("input[name='telephone']").val();
		var telephoneReg = /^(\d{3,4}-?)?\d{7,9}(-?\d{2,6})?$|^$/;
		var telephoneMessage = "电话格式错误";
		if(!telephoneReg.test(telephone)){
			warnTips("telephone",telephoneMessage);
			return  false;
		}
		
		//工号
		var number = $("input[name='number']").val();
		var numberReg = /^[a-zA-Z0-9]{1,16}$|^$/;
		if(number != '' && number.length > 16){
			warnTips("number","工号长度不允许超过16个字符");
			return  false;
		}
		if(!numberReg.test(number)){
			warnTips("number","工号格式错误");
			return  false;
		}
		
		//分机号
		var ccNumber = $("input[name='ccNumber']").val();
		var ccNumberReg = /^\d{4}$|^$/;
		if(!ccNumberReg.test(ccNumber)){
			warnTips("ccNumber","分机号为4位数字");
			return  false;
		}
		
		//传真
		var fax = $("input[name='fax']").val();
		var faxMessage = "传值格式错误";
		if(!telephoneReg.test(fax)){
			warnTips("fax",faxMessage);
			return  false;
		}
		//地区
		//地址
		//设置角色
		
		//#################数据处理#########################
		//部门集合
		$("input[name='orgIds']").val(orgIds);
		//职位集合
		$("input[name='positionIds']").val(positionIds);

		if($("input[name='roleId']:checked").length <= 0){
			warnTips("errorRole","请选择角色");
			return  false;
		}

		//角色集合
		var roleIds = '';
		$.each($("input[name='roleId']:checked"),function(i,n){
			roleIds += $(this).val()+',';
		});
		$("input[name='roleIds']").val(roleIds);

		var number = $("input[name='number']").val();
		var flag = false;
		$.ajax({
			type: "POST",
			url: page_url+'/system/user/getuser.do',
			data: {'username':username,'number':number,'userId':$("input[name='userId']").val()},
			dataType:'json',
			async : false,
			success: function(data){
				if(data.code != 0){
					layer.alert(data.message);
					return false;
				}else{
					flag = true;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		if($("input[name='userId']").val() > 0){
			$("#userform").attr("action",page_url+'/system/user/saveedituser.do')
		}else{
			$("#userform").attr("action",page_url+'/system/user/saveuser.do')
		}
		return flag;
	});
});

//初始化职位
function initPosit(obj){
	checkLogin();
	var orgId = $(obj).val();
	if(orgId > 0){
		$.ajax({
			type: "POST",
			url: page_url+'/system/posit/getposit.do',
			data: {'orgId':orgId},
			dataType:'json',
			success: function(data){
				if(data.code == 0){
					var posit = data.listData;
					var option = "";
					$.each(posit,function(i,n){
						option += "<option value='"+posit[i]['positionId']+"'>"+posit[i]['positionName']+"</option>";
					});
					$(obj).next("select[name='positionId']").find("option:gt(0)").remove();
					$(obj).next("select[name='positionId']").append(option);
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}else{
		$(obj).next("select[name='positionId']").find("option:gt(0)").remove();
	}
	
}

/**
 * 添加一行
 * @param obj
 * @returns
 */
function addOrgPosit(obj){
	var org = '<div class="career-one">';
	org += '</div>';
	$(obj).parent("div").after(org);
	$(obj).parent("div").find("select").eq(0).clone().appendTo($(obj).parent("div").next('div'));
	var posit = '<select class="career_right" name="positionId"><option selected="selected" value="0">请选择</option></select><div class="f_left bt-bg-style bt-middle bg-light-red" onclick="delOrgPosit(this);">删除</div>';
	$(obj).parent("div").next('div').append(posit);
	$(obj).parent("div").next('div').find("select").val(0);
}

/**
 * 删除一行
 * @param obj
 * @returns
 */
function delOrgPosit(obj){
	$(obj).parent("div").remove();
}
function checkNumber(){
	var number=$("input[name='number']").val();
	var numberReg = /^[a-zA-Z0-9]{1,16}$|^$/;
	if(number != '' && number.length > 16){
		warnTips("number","工号长度不允许超过16个字符");
		return  false;
	}else if(!numberReg.test(number)){
		warnTips("number","工号格式错误");
		return  false;
	}else{
		$("#number").removeClass("errorbor");
		$("#number").parents('li').find('.warning').remove();
	}
}

/**
 * 是否贝登员工处的可登录系统的显示和隐藏
 * @param obj
 */
function update(obj){
	var staff = $("input[name=staff]:checked").val();
	if(staff == '0'){
		$("#systemId1").prop("checked",false);
		$("#systemId2").prop("checked",false);
		$("#systemId1").attr('disabled','disabled');
		$("#systemId2").attr('disabled','disabled');
		$('#belongCompanyName').show();
	}else {
		$('#systemId1').attr("disabled",false);
		$('#systemId2').attr("disabled",false);
		$('#belongCompanyName').hide();
	}
}
