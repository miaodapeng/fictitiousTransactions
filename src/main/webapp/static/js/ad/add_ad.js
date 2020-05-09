$(document).ready(function(){

	$("#sub").click(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("textarea").removeClass("errorbor");
		if($("#title").val()==''){
			warnErrorTips("title","titleError","名称不允许为空");//文本框ID和提示用语
			return false;
		}
		if(strlen($("#title").val()) > 40){
			warnErrorTips("title","titleError","广告名称不允许超过40个字符");//文本框ID和提示用语
			return false;
		}
		if($("#url").val()==''){
			warnErrorTips("name_1","urlError","图片不允许为空");//文本框ID和提示用语
			return false;
		}
		var sort = $("#sort").val().trim();
		if (sort.length==0) {
			warnTips("sort","排序编号不能为空");//文本框ID和提示用语
			return false;
		}else{
			var re = /^[0-9]+$/ ;
			if(!re.test(sort)){
				warnTips("sort","排序编号必须为正整数");//文本框ID和提示用语
				return false;
			}
		}
		if($("#comments").val().length > 256){
			warnErrorTips("comments","commentsError","备注不允许超过512个字符");//文本框ID和提示用语
			return false;
		}
		var url = page_url;
		if($("#subType").val()=='add'){
			url = url + '/system/ad/saveAddAd.do';
		}else{
			url = url + '/system/ad/saveEditAd.do';
		}
		$.ajax({
			url:url,
			data:$('#myform').serialize(),
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

function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	var domain = $("#domain").val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg' && strExtension != 'JPG' && strExtension != 'gif' && strExtension != 'GIF' && strExtension != 'png' && strExtension != 'PNG') {
		layer.alert("图片格式不正确");
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
	  if(fileSize>1024*5){
		  layer.alert("上传附件不得超过5M");
		  return false;
	  }
	$.ajaxFileUpload({
		url : page_url + '/system/ad/ajaxFileUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {
				$("#urlError").siblings('.warning').remove();
				$("#name_1").removeClass("errorbor");
				$("#name_"+num ).val(oldName);
				$("#uri_"+num ).val(data.filePath+"/"+data.fileName);
				var url = 'http://' + domain + data.filePath+"/"+data.fileName;
				$("#url").val(url);
				$("#img_icon_" + num).attr("class", "iconsuccesss ml7").show();
				$("#img_view_" + num).attr("href", 'http://' + domain + data.filePath+"/"+data.fileName).show();
				$("#img_del_" + num).show();
			} else {
				warnErrorTips("name_1","urlError",data.message);//文本框ID和提示用语
				
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
	var sum = Number($("#conadd").siblings(".form-blanks").length);
	var uri = $("#uri_"+num).val();
	if(uri == '' && sum > 1){
		$("#img_del_"+num).parent().parent(".form-blanks").remove();
	}else{
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
				$("#url").val("");
				layer.close(index);
			}, function(){
			});
	}
}
