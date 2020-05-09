$(function(){
	$("#myform").submit(function() {
		checkLogin();
		jQuery.ajax({
			url:page_url+'/system/call/savebussinesschance.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				$(".warning").remove();
				$("input").removeClass("errorbor");
				$("textarea").removeClass("errorbor");
				if($("#receiveTime").val()==''){
					warnTips("receiveTime","商机时间不允许为空");
					return  false;
				}
				
				if($('input[name="source"]:checked').length == 0){
					warnTips("sourceUl","商机来源不允许为空");
					return  false;
				}
				if($("#content").val()==""){
					warnTips("content","询价产品不允许为空");
					return false;
				}
				if($("#content").val().length>512){
					warnTips("content","询价产品内容不能大于512字符");
					return false;
				}
				if($("#goodsCategory").val()==''){
					warnTips("goodsCategory","产品分类不允许为空");
					return false;
				}
				var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
				if($('#goodsBrand').val()!='' && !nameReg.test($('#goodsBrand').val()!='')){
					warnTips("goodsBrand","产品品牌不允许使用特殊字符");
					return false;
				}else{
					delWarnTips("goodsBrand");
				}
				if($('#goodsBrand').val()!=''&&$('#goodsBrand').val().length>32){
					warnTips("goodsBrand","产品品牌不能大于32字符");
					return false;
				}else{
					delWarnTips("goodsBrand");
				}
				if($('#goodsName').val()!=''&&$('#goodsName').val().length>100){
					warnTips("goodsName","产品名称不能大于100字符");
					return false;
				}else{
					delWarnTips("goodsName");
				}
				if($('#traderName').val()!=''&&$('#traderName').val().length>256){
					warnTips("traderName","客户名称不能大于256字符");
					return false;
				}else{
					delWarnTips("traderName");
				}
				if($('#traderContactName').val()!=''&&$('#traderContactName').val().length>256){
					warnTips("traderContactName","联系人名称不能大于256字符");
					return false;
				}else{
					delWarnTips("traderContactName");
				}
				var mobileReg = /^1\d{10}$|^$/;
				if($('#mobile').val()!=''&&!mobileReg.test($('#mobile').val())){
					warnTips("mobile","手机号格式错误");
					return  false;
				}else{
					delWarnTips("mobile");
				}
				if($('#mobile').val()!=''&&$('#mobile').val().length>16){
					warnTips("mobile","手机号不能大于16字符");
					return false;
				}else{
					delWarnTips("mobile");
				}
				var telephoneReg = /^(\d{3,4}-?)?\d{7,9}(-?\d{2,6})?$|^$/;
				if($('#telephone').val()!=''&&!telephoneReg.test($('#telephone').val())){
					warnTips("telephone","电话格式错误");
					return  false;
				}else{
					delWarnTips("telephone");
				}
				if($('#telephone').val()!=''&&$('#telephone').val().length>32){
					warnTips("telephone","电话不能大于32字符");
					return false;
				}else{
					delWarnTips("telephone");
				}
				if($('#otherContact').val()!=''&&$('#otherContact').val().length>64){
					warnTips("otherContact","其他联系方式不能大于64字符");
					return false;
				}else{
					delWarnTips("otherContact");
				}
				if($('#userId').val()==''){
					warnTips("userId","分配销售不允许为空");
					return false;
				}else{
					delWarnTips("userId");
				}
				if($("#comments").val()!='' && $("#comments").val().length > 128){
					warnTips("comments","备注长度不能超过128字符");
					return  false;
				}else{
					delWarnTips("comments");
				}
			},
			success:function(data)
			{
				if(data.code == 0){
					window.parent.closeComm();
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
	});
});
