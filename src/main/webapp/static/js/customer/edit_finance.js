$(function() {
	
	$("#upload_file").change(function(){
		checkLogin();
		$("#upload_file_tmp").val($("#upload_file").val());
	})

	$("#submit").click(function(){
		checkLogin();
		var taxNum=$("#taxNum").val();
		var preTaxNum=$("#preTaxNum").val();
		var checkStatus=$("#checkStatus").val();
		if($("#traderType").val()=='1'&&(taxNum==''||taxNum==undefined)&&!((preTaxNum==undefined||preTaxNum=='')&&(checkStatus!=undefined&&checkStatus==1))){
			index = layer.confirm("税务登记号为空，需财务部进行审核？", {
				btn: ['确定','取消'] //按钮
			}, function(){
				save();
				layer.close(index);
			}, function(){
				layer.close(index)
			});
			return false;
		}


		save();
		return false;
	})
	
});



function save(){
	if($("#traderType").val()=='1'&&$("#regAddress").val()!=''&&$("#regAddress").val().length>256){
		warnTips("regAddress","注册地址长度不能超过256字符");
		return false;
	}
	if($("#traderType").val()=='1'&&$("#regTel").val()!=''&&$("#regTel").val().length>32){
		warnTips("regTel","注册电话长度不能超过32字符");
		return false;
	}

	if($("#traderType").val()=='1'&&$("#taxNum").val()!=''&&$("#taxNum").val().length>32){
		warnTips("taxNum","税务登记号长度不能超过32字符");
		return false;
	}
	if($("#bank").val()!=''&&$("#bank").val().length>256){
		warnTips("bank","开户银行长度不能超过256字符");
		return false;
	}
	if($("#traderType").val() == '2'){
		if($("#bankCode").val()==''){
			warnTips("bankCode","开户行支付联行号不允许为空");
			return false;
		}
		if($("#bankCode").val().length > 32){
			warnTips("bankCode","开户行支付联行号长度不能超过32字符");
			return false;
		}
	}else{
		if($("#bankCode").val()!=''&&$("#bankCode").val().length>32){
			warnTips("bankCode","开户行支付联行号长度不能超过32字符");
			return false;
		}
	}

	if($("#bankAccount").val()!=''&&$("#bankAccount").val().length>32){
		warnTips("bankAccount","银行帐号长度不能超过32字符");
		return false;
	}
	if($("#traderType").val()!='1'&&$("#comments").val()!=''&&$("#comments").val().length>128){
		warnTips("comments","备注长度不能超过128字符");
		return false;
	}
	var url='';
	if($("#traderType").val()=='1'){
		url='/trader/customer/saveCustomerFinance.do';
	}else{
		if($("#bank").val()=='' ){
			warnTips("bank","开户银行不允许为空");
			return false;
		}
		if($("#bankAccount").val()==''){
			warnTips("bankAccount","银行帐号不允许为空");
			return false;
		}
		url='/trader/supplier/saveCustomerFinance.do';
	}
	$.ajax({
		url:page_url+url,
		data:$('#myform').serialize(),
		type:"POST",
		dataType : "json",
		async: false,
		success:function(data)
		{
			if(data.code==0){
				if($("#traderType").val()=='1'){
					var st=data.data.split(",");
					var str=page_url+"/trader/customer/getFinanceAndAptitude.do?traderId="+st[0]+"&traderCustomerId="+st[1];
					$("#finace").attr('href',str);
				}
				window.parent.location.reload();
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
	return false;
}
function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	var domain = $("input[name='domain']").val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' && strExtension != 'pdf') {
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
	  layer.alert("图片不能大于2MB");
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
				//$("#name_"+num ).val(oldName);
				$("#uri_"+num ).val(data.filePath+"/"+data.fileName);
				$("#img_icon_" + num).attr("class", "iconsuccesss ml7").show();
				$("#img_view_" + num).attr("href", 'http://' + domain + data.filePath+"/"+data.fileName).show();
				$("#img_del_" + num).show();
				//$("#up").addClass("none");
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
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$("#img_view_" + num).hide();
			$("#img_del_" + num).hide();
			$("#file_"+ num).val("");
			$("#uri_"+ num).val("");
			$("#img_icon_" + num).hide();
			//$("#up").removeClass("none");
			//$("#uri_"+num ).removeClass("none");
			layer.close(index);
		}, function(){
		});
}