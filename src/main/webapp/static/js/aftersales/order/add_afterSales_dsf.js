$(document).ready(function(){
	$('#submit').click(function() {
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		$("textarea").removeClass("errorbor");
		
		if($("#shtype").val()=='aw'){
			if($("#traderId").val()==''){
				warnErrorTips("searchName","searchNameError","客户不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#comments").val()==''){
				warnErrorTips("comments","commentsError","详情说明不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#comments").val().length > 512){
				warnErrorTips("comments","commentsError","详情说明不允许超过512个字符");//文本框ID和提示用语
				return false;
			}
			if($("#traderContactId").val()==''){
				warnErrorTips("traderContactId","traderContactIdError","联系人不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#province").val()==0){
				warnErrorTips("province","areaError","省份不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#city").val()==0){
				warnErrorTips("city","areaError","地市不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#zone").val()==0 && $("#zone option").length > 1){
				warnErrorTips("zone","areaError","区县不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#address").val()==''){
				warnErrorTips("address","addressError","详细地址不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#address").val().length>256){
				warnErrorTips("address","addressError","详细地址不允许超过256个字符");//文本框ID和提示用语
				return false;
			}
			
		}
		if($("#shtype").val()=='jz'){
			if($("#comments").val()==''){
				warnErrorTips("comments","commentsError","详情说明不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#comments").val().length > 512){
				warnErrorTips("comments","commentsError","详情说明不允许超过512个字符");//文本框ID和提示用语
				return false;
			}
			if($("#traderContactName").val()==''){
				warnErrorTips("traderContactName","traderContactNameError","联系人姓名不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#traderContactName").val().length > 64){
				warnErrorTips("traderContactName","traderContactNameError","联系人姓名不允许超过64个字符");//文本框ID和提示用语
				return false;
			}
			if($("#traderContactMobile").val()==''){
				warnErrorTips("traderContactMobile","traderContactMobileError","联系人手机不允许为空");//文本框ID和提示用语
				return false;
			}
			var mobileReg = /^1\d{10}$|^$/;
			if(!mobileReg.test($("#traderContactMobile").val())){
				warnErrorTips("traderContactMobile","traderContactMobileError","联系人手机格式错误");//文本框ID和提示用语
				return false;
			}
			
		}
		if($("#shtype").val()=='qt'){
			if($("#comments").val()==''){
				warnErrorTips("comments","commentsError","详情说明不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#comments").val().length > 512){
				warnErrorTips("comments","commentsError","详情说明不允许超过512个字符");//文本框ID和提示用语
				return false;
			}
		}
		if($("#shtype").val()=='tk'){
			if($("#traderId").val()==''){
				warnErrorTips("searchName","searchNameError","客户不允许为空");//文本框ID和提示用语
				return false;
			}
			var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			var refundAmount = $("#refundAmount").val();
			if(refundAmount ==''){
				warnErrorTips("refundAmount","refundAmountError","退款金额不允许为空");//文本框ID和提示用语
				return false;
			}
			if(!priceReg.test(refundAmount)){
				warnErrorTips("refundAmount","refundAmountError","退款金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
			if(Number(refundAmount) > 300000000){
				warnErrorTips("refundAmount","refundAmountError","退款金额不允许超过3亿");//文本框ID和提示用语
				return false;
			}
			if($("#traderContactId").val()==''){
				warnErrorTips("traderContactId","traderContactIdError","联系人不允许为空");//文本框ID和提示用语
				return false;
			}
			var traderSubject=$('input:radio[name="traderSubject"]:checked').val();

				if($("#payee").val()==''){
					warnErrorTips("payee","payeeError","交易名称不允许为空");//文本框ID和提示用语
					return false;
				}
				if($("#payee").val().length > 128){
					warnErrorTips("payee","payeeError","交易名称不允许超过128个字符");//文本框ID和提示用语
					return false;
				}
				if($("#bank").val()==''){
					warnErrorTips("bank","bankError","开户银行不允许为空");//文本框ID和提示用语
					return false;
				}
				if($("#bank").val().length > 256){
					warnErrorTips("bank","bankError","开户银行不允许超过256个字符");//文本框ID和提示用语
					return false;
				}
				if($("#bankAccount").val()==''){
					warnErrorTips("bankAccount","bankAccountError","银行帐号不允许为空");//文本框ID和提示用语
					return false;
				}
				if($("#bankAccount").val().length > 32){
					warnErrorTips("bankAccount","bankAccountError","银行帐号不允许超过32个字符");//文本框ID和提示用语
					return false;
				}
				if($("#bankCode").val()!='' && $("#bankCode").val().length > 32){
					warnErrorTips("bankCode","bankCodeError","开户行支付联行号不允许超过32个字符");//文本框ID和提示用语
					return false;
				}
				
			if($("#comments").val()==''){
				warnErrorTips("comments","commentsError","详情说明不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#comments").val().length > 512){
				warnErrorTips("comments","commentsError","详情说明不允许超过512个字符");//文本框ID和提示用语
				return false;
			}
			
		}
	});

	
	
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
	console.log(strExtension + "1111");
	if (strExtension != 'jpg' && strExtension != 'JPG' && strExtension != 'gif' && strExtension != 'GIF' && strExtension != 'png' && strExtension != 'PNG' && strExtension != 'bmp' && strExtension != 'BMP'
			&& strExtension != 'pdf' && strExtension != 'PDF' && strExtension != 'doc' && strExtension != 'DOC' && strExtension != 'docx' && strExtension != 'DOCX') {
		layer.alert("文件格式不正确");
		// 清空url
		$(obj).val("");
		return false;
	}
	  var fileSize = 0;
	  console.log(fileSize + "222");
	  var isIE = /msie/i.test(navigator.userAgent) && !window.opera;      
	  if (isIE && !obj.files) {
		 console.log(isIE && !obj.files);
		 console.log(333);
	     var filePath = obj.value;      
	     var fileSystem = new ActiveXObject("Scripting.FileSystemObject");  
	     var file = fileSystem.GetFile (filePath);        
	     fileSize = file.Size;     
	  }else { 
		 console.log(isIE && !obj.files + "444");
		 console.log(444);
	     fileSize = obj.files[0].size;   
	  } 
	  fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
	  console.log(fileSize + "555");
	  if(fileSize>2048){
		  layer.alert("上传附件不得超过2M");
	    return false;
	  }
	  console.log(page_url + "666");
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

function conadd(){
	var num = Number($("#conadd").siblings(".form-blanks").length)+Number(1);
	var div = '<div class="form-blanks mt10">'+
					'<div class="pos_rel f_left">'+
				    '<input type="file" class="upload_file" id="file_'+num+'" name="lwfile" style="display: none;" onchange="uploadFile(this,'+num+');">'+
				    '<input type="text" class="input-largest" id="name_'+num+'" readonly="readonly"'+
				    	'placeholder="请上传附件" name="fileName" onclick="file_'+num+'.click();" >'+
				    '<input type="hidden" id="uri_'+num+'" name="fileUri" >'+
				'</div>'+
				'<label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $(\'#file_'+num+'\').click();">浏览</label>'+
				'<div class="f_left"><i class="iconsuccesss mt3 none" id="img_icon_'+num+'"></i>'+
				'<a href="" target="_blank" class="font-blue cursor-pointer mt3 none" id="img_view_'+num+'">查看</a>'+
				'<span class="font-red cursor-pointer mt3" onclick="del('+num+')" id="img_del_'+num+'">删除</span></div><div class="clear"></div>'+
				'</div>';
	$("#conadd").before(div);
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
				layer.close(index);
			}, function(){
			});
	}
}

function search(){
	checkLogin();
	var searchName = $("#searchName").val()==undefined?"":$("#searchName").val();
	if($("#searchName").val()==''){
		warnErrorTips("searchName","searchNameError","搜索内容不允许为空");//文本框ID和提示用语
		return false;
	}
	var searchUrl = page_url+"/aftersales/order/getCustomerPage.do?searchName="+searchName;
	$("#popEngineer").attr('layerParams','{"width":"900px","height":"500px","title":"搜索客户","link":"'+searchUrl+'"}');
	$("#popEngineer").click();
	
}

function research(){
	checkLogin();
	$("#cus").show();
	$("#search1").show();
	$("#selname").hide().html("");
	$("#search2").hide();
}

function research1(){
	checkLogin();
	$("#inp").show();
	$("#search1").show();
	$("#selname").hide();
	$("#search2").hide();
	$("#selname").val("");
	$("#engineerId").val("");
	$("#name").val("");
}
