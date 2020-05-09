function uploadFileFtp(obj){
	var imgPath = $(obj).val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp') {
		alert("请选择图片文件");
		return;
	}
	$.ajaxFileUpload({
		url : './ajaxFileUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {
				$("#filePath").val(data.filePath+"/"+data.fileName);
				layer.alert("OK");
			} else {
				layer.alert(data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {
			layer.alert(data.responseText);
		}
	})
}

function downFile(){
	alert($("#filePath").val());
	$.ajax({
		async:false,
		url:'./downFile.do',
		data:{filePath:$("#filePath").val()},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				window.location.href=data.filePath;
			}
		}
	})
}

function deleteFile() {
	layer.confirm("您是否确认删除？", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			async : false,
			url : './deleteFile.do',
			data : {
				filePath : $("#filePath").val()
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					layer.alert(data.message);
				} else {
					layer.alert(data.message);
				}
			}
		})
	});
}

function uploadFileServe(obj){
	var imgPath = $(obj).val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
/*	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp') {
		alert("请选择图片文件");
		return;
	}*/
	$.ajaxFileUpload({
		url : './ajaxFileUploadServe.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {
				$("#filePath").val(data.filePath+"/"+data.fileName);
				layer.alert("OK");
			} else {
				layer.alert(data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {
			layer.alert(data.responseText);
		}
	})
}