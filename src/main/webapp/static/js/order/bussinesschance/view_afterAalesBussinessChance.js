$(function(){
	
	$("#submit").click(function(){
		checkLogin();
		if($("#receiveTime").val()==''){
			warnTips("receiveTime","商机时间不允许为空");
			return  false;
		}
		var source=$('input:radio[name="source"]:checked').val();
		
		if(source==undefined||source==""){
			//$('input:radio[name="source"]').parent("ul").siblings("div").css("display","");
			$("#source").css("display","");
			return false;
		}else{
			$("#source").css("display","none");
		}
		var communication=$('input:radio[name="communication"]:checked').val();
		if(communication==undefined||communication==""){
			//$('input:radio[name="communication"]').parent("ul").siblings("div").css("display","");
			$("#communication").css("display","");
			return false;
		}else{
			$("#communication").css("display","none");
		}
		if($("#content").val()==""){
			$('#content1').css("display","");
			return false;
		}else{
			$('#content1').css("display","none");
		}
		if($("#content").val().length>512){
			$('#content2').css("display","");
			return false;
		}else{
			$('#content2').css("display","none");
		}
		if($("#goodsCategory").val()==''){
			$('#goodsCategory1').css("display","");
			return false;
		}
		if($('#goodsBrand').val()!=''&&$('#goodsBrand').val().length>32){
			warnTips("goodsBrand","产品品牌不能大于32字符");
			return false;
		}
		if($('#goodsName').val()!=''&&$('#goodsName').val().length>100){
			warnTips("goodsName","产品名称不能大于100字符");
			return false;
		}
		if($('#traderName').val()!=''&&$('#traderName').val().length>256){
			warnTips("traderName","客户名称不能大于256字符");
			return false;
		}
		if($('#traderContactName').val()!=''&&$('#traderContactName').val().length>256){
			warnTips("traderContactName","联系人名称不能大于256字符");
			return false;
		}
		var mobileReg = /^1\d{10}$|^$/;
		if($('#mobile').val()!=''&&!mobileReg.test($('#mobile').val())){
			warnTips("mobile","手机号格式错误");
			return  false;
		}
		if($('#mobile').val()!=''&&$('#mobile').val().length>16){
			warnTips("mobile","手机号不能大于16字符");
			return false;
		}
		var telephoneReg = /^(\d{3,4}-?)?\d{7,9}(-?\d{2,6})?$|^$/;
		if($('#telephone').val()!=''&&!telephoneReg.test($('#telephone').val())){
			warnTips("telephone","电话格式错误");
			return  false;
		}
		if($('#telephone').val()!=''&&$('#telephone').val().length>32){
			warnTips("telephone","电话不能大于32字符");
			return false;
		}
		if($('#otherContact').val()!=''&&$('#otherContact').val().length>64){
			warnTips("otherContact","其他联系方式不能大于64字符");
			return false;
		}
		if($('#userId').val()==''){
			warnTips("userId","分配销售不允许为空");
			return false;
		}
		if($("#comments").val()!='' && $("#comments").val().length > 128){
			warnTips("comments","备注长度不能超过128字符");
			return  false;
		}
		
	})
});
function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg'&& strExtension != 'gif' && strExtension != 'png' && strExtension != 'pdf' && strExtension != 'doc' && strExtension != 'docx' && strExtension != 'xls' && strExtension != 'xlsx') {
		warnTips("uri_1"," 请选择正确文件格式");
		return false;
	}
	  var fileSize = 0;
	  var isIE = /msie/i.test(navigator.userAgent) && !window.opera;      
	  if (isIE && !obj.files) {     
	     var filePath = obj.value;      
	     var fileSystem = new ActiveXObject("Scripting.FileSystemObject");  
	     var file = fileSystem.GetFile (filePath);        
	     fileSize = file.Size;     
	  }else { 
	     fileSize = obj.files[0].size;   
	  } 
	  fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
	  if(fileSize>2048){
		  warnTips("uri_1"," 上传内容不能大于2MB");
	    return false;
	  }
	$.ajaxFileUpload({
		url : page_url + '/fileUpload/ajaxFileUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {
				$("#name_"+num ).val(oldName);
				$("#uri_"+num ).val(data.filePath+"/"+data.fileName);
				$("#uri_"+num ).siblings("i").css("display","");
			} else {
				layer.alert(data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}else{
				layer.alert(data.responseText);
			}
			
		}
	});
	
}

