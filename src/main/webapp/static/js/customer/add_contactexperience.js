$(function() {
	
	$("#posi span").click(function(){
		checkLogin();
		$("#position").val($(this).text());
	})
	
	$("#myform").submit(function(){
		checkLogin();
		if($("#company").val() != '' && $("#company").val().length > 128){
			warnTips("company","企业名称长度不能超过128个字符");
			return  false;
		}else{
			delWarnTips("company");
		}
		if($("#position").val() != '' && $("#position").val().length > 64){
			warnTips("position","职位名称长度不能超过64个字符");
			return  false;
		}else{
			delWarnTips("position");
		}
		if($("input[name='start']").val()==''&&$("input[name='end']").val()==''&&$("input[name='company']").val()==''&&$("input[name='position']").val()==''
			&&$("input[name='bussinessBrandId']").val() == undefined&&$("input[name='bussinessAreaId']").html() == undefined){
			layer.alert("请至少填写/选择一项数据");
			return false;
		}
		var opturl='';
		if($("opt").val()=='add'){
			opturl=page_url+'/trader/customer/saveAddContactExperience.do';
		}else{
			opturl=page_url+'/trader/customer/saveEditContactExperience.do';
		}
		$.ajax({
			url:opturl,
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					var st=data.data.split(",");
					var str=page_url+"/trader/customer/getContactInfo.do?traderId="+st[1]+"&traderCustomerId="+st[0]+"&traderContactId"+st[2];
					$("#contact").attr('href',str);
					window.parent.location.reload();
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

	})
	
});
