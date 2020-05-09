function contractReturnSubmit () {
	checkLogin();
	if($("#uri_514").val() == ''){
		layer.alert("请选择上传文件");
		return false;
	}
	$.ajax({
		async : false,
		url : './contractReturnSave.do',
		data : $("#contract_return").serialize(),
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.code==0){
				layerPFF();
				//window.parent.location.reload();
			}else{
				layer.alert(data.message);
			}
			
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			layer.alert("操作失败");
		}
	});
}

function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' && strExtension != 'pdf') {
		alert("请选择图片文件");
		return;
	}
	if($(obj)[0].files[0].size > 2*1024*1024){//字节
		layer.alert("图片文件大小应为2MB以内",{ icon: 2 });
		return;
	}
	$("#img_icon_" + num).attr("class", "iconloading mt5").show();
	$("#img_view_" + num).hide();
	$("#img_del_" + num).hide();
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
				$("#uri_" + num).val(data.filePath+"/"+data.fileName);
				$("#domain_" + num).val(data.httpUrl);
				$("#file_name_" + num).val(data.fileName);
				$("#img_view_" + num).attr("href", 'http://' + data.httpUrl + data.filePath+"/"+data.fileName).show();
				$("#img_icon_" + num).attr("class", "iconsuccesss mt5").show();
				$("#img_del_" + num).show();
				//layer.msg('上传成功');
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
	})
}

function delProductImg(num) {
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$("#img_icon_" + num).hide();
			$("#img_view_" + num).hide();
			$("#img_del_" + num).hide();
			$("#uri_" + num).val('');
			$("#domain_" + num).val('');
			$("#file_name_" + num).val('');
			layer.close(index);
		}, function(){
		});
}
