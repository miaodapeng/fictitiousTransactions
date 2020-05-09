$(document).ready(function(){
	$("input[name='owner']").change(function(){
		if($(this).val() == 2){//外部
			$("#inputName").hide();
			$("#inputDiv").show();
			$("#company").val("");
		}else{
			$("#inputName").show();
			$("#inputDiv").hide();
			$("#company").val($("#companyName").val());
		}
	});
	$('#myform').submit(function() {
		jQuery.ajax({
			url:'./saveadd.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				checkLogin();
				$(".warning").remove();
				$("select").removeClass("errorbor");
				$("input").removeClass("errorbor");
				var name = $("#name").val();
				var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{2,32}$/;
				if(name == ""){
					warnTips("name","姓名不允许为空");
					return false;
				}
				if(name.length < 2 || name.length > 32){
					warnTips("name","姓名长度为2-32个字符长度");
					return false;
				}
//				if(!nameReg.test(name)){
//					warnTips("name","姓名不允许使用特殊字符");
//					return false;
//				}
				
				var mobile = $("#mobile").val();
				var mobileReg = /^1\d{10}$|^$/;
				if(mobile == ""){
					warnTips("mobile","手机号不允许为空");
					return false;
				}
				if(!mobileReg.test(mobile)){
					warnTips("mobile","手机格式错误");
					return  false;
				}
				
				if($("select[name='province']").val() == 0){
					$("select[name='province']").addClass("errorbor");
					$("#zone").siblings('.warning').remove();
					$("#zone").after('<div class="warning">省份不允许为空</div>');
					return false;
				}
				if($("select[name='city']").val() == 0){
					$("select[name='city']").addClass("errorbor");
					$("#zone").siblings('.warning').remove();
					$("#zone").after('<div class="warning">地市不允许为空</div>');
					return false;
				}
				if($("select[name='zone']").val() == 0 && $("select[name='zone'] option").length > 1){
					$("select[name='zone']").addClass("errorbor");
					$("#zone").siblings('.warning').remove();
					$("#zone").after('<div class="warning">区县不允许为空</div>');
					return false;
				}
				
				var company = $("#company").val();
				if(company.length > 128){
					warnTips("company","公司名称长度不允许超过128个字符");
					return false;
				}
				var serviceProducts = $("#serviceProducts").val();
				if(serviceProducts.length > 512){
					warnTips("serviceProducts","维修产品长度不允许超过128个字符");
					return false;
				}
				var comments = $("#comments").val();
				if(comments.length > 1024){
					warnTips("comments","备注长度不允许超过1024个字符");
					return false;
				}
			},
			success:function(data)
			{
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
			
		});
		return false;
	});
});
