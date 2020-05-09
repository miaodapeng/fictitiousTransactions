$(function() {
	
	$("#search").click(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		var name=$("#searchName").val();
		if(name == ''){
			//warnTips("name","名称为空不能搜索");
			warnErrorTips("searchName","supplierNameError","查询条件不允许为空");
			return  false;
		}
		$("#myform").submit();
	})
	


	
	$("#submit").click(function(){
		checkLogin();
		var traderId=$("input[name='traderId']").val();
		if(traderId==''){
			layer.msg("请选择客户");
			return  false;
		}
		var contactId=$("#contactId").val();
		var traderType=$("#traderType").val();
		var url ='';
		if(traderType==1){
			url = page_url+'/trader/customer/transferContact.do';
		}else{
			url = page_url+'/trader/supplier/transferContact.do';
		}
		$.ajax({
			url:url,
			data:{'traderContactId':contactId,'traderId':traderId,'traderType':traderType,'name':$("input[name='name']").val(),'mobile':$("input[name='mobile']").val()},
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
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

function selectObj(traderId,supplierName){
	checkLogin();
	$("#cus").addClass("none");
	$("#searchName").addClass("none");
	$("#search").addClass("none");
	$("#research").removeClass("none");
	$("input[name='traderId']").val(traderId);
	$("#supplierName").removeClass("none").html(supplierName);
}

function research(){
	checkLogin();
	$("#cus").removeClass("none");
	$("#searchName").removeClass("none");
	$("#search").removeClass("none");
	$("#research").addClass("none");
	$("#supplierName").addClass("none")
}
