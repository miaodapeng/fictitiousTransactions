$(function() {
	$("#sub").click(function(){
		var brandNature="";
		if($("#source").val()==1){
			brandNature=$('input[name="brandNature"]').val();
		}else{
			brandNature=$('input:radio[name="brandNature"]:checked').val();
		}
		if(brandNature=='' || brandNature == undefined){
			warnErrorTips("brandNature","brandNatureError","商品品牌不允许为空");//文本框ID和提示用语
			return false;
		}
		var brandName = $("#brandName").val();
		if (brandName.length==0) {
			warnTips("brandName","品牌名称不允许为空");//文本框ID和提示用语
			return false;
		}
		if (brandName.length<1 || brandName.length >32) {
			warnTips("brandName","品牌名称不允许超过32个字符");//文本框ID和提示用语
			return false;
		}
		if ($("#brandWebsite").val() != '' && $("#brandWebsite").val().length > 256) {
			warnErrorTips("brandWebsite","brandWebsiteError","品牌网址不允许超过256个字符");//文本框ID和提示用语
			return false;
		}
		if(UE.getEditor('content').getContentTxt() == ""){
			warnTips("content","公告内容不允许为空");
			return  false;
		}
		if(UE.getEditor('content').getContentTxt().length > 10000){
			warnTips("content","公告标题长度不允许超过10000个字符");
			return  false;
		}
	});
	
	
/**	
	var $form = $("#addBrandForm");
	$form.submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./saveBrand.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){
				var brandNature=$('input:radio[name="brandNature"]:checked').val();
				if(brandNature=='' || brandNature == undefined){
					warnErrorTips("brandNature","brandNatureError","商品品牌不允许为空");//文本框ID和提示用语
					return false;
				}
				var brandName = $("#brandName").val();
				if (brandName.length==0) {
					warnTips("brandName","品牌名称不能为空");//文本框ID和提示用语
					return false;
				}
				if (brandName.length<1 || brandName.length >32) {
					warnTips("brandName","品牌名称长度应该在1-32个字符之间");//文本框ID和提示用语
					return false;
				}
				/* 测试重复提交
				 * window.setTimeout(function(){
					$.ajax({
						async:false,
						url:'./saveBrand.do',
						data:$form.serialize(),
						type:"POST",
						dataType : "json",
						success:function(data){
							if(data.code == 0 && data.status == -1){
								warnTips("brandName",data.message);
								return false;
							}else{
								refreshPageList(data);
							}
						}
					},5); 
				});*/
//			},
//			success:function(data){
//				if(data.code == 0 && data.status == -1){
//					warnTips("brandName",data.message);
//					return false;
//				}else{
//					refreshPageList(data);
//				}
//			},
//			error:function(data){
//				if(data.status ==1001){
//					layer.alert("当前操作无权限")
//				}
//			}
//		})
//		return false;
//	});
});

function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	var domain = $("#domain").val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1).toLowerCase();
	if (strExtension != 'jpg' && strExtension != 'png' && strExtension != 'jpeg') {
		layer.alert("文件格式不正确");
		return;
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
		  layer.alert("上传附件不得超过2M");
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
				$("#img_icon_" + num).attr("class", "iconsuccesss ml7").show();
				$("#img_view_" + num).attr("href", 'http://' + domain + data.filePath+"/"+data.fileName).show();
				$("#img_del_" + num).show();
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

function del(num){
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$("#img_icon_" + num).hide();
			$("#img_view_" + num).hide();
			if(num == 1){
				$("#img_del_" + num).hide();
			}
			$("#name_"+ num).val("");
			$("#uri_"+ num).val("");
			$("#file_"+ num).val("");
			layer.close(index);
		}, function(){
		});
	
}