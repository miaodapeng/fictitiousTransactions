$(document).ready(function () {
	if ($("input.orderNo").val().startsWith('EL')){
		$(".table").find("input").attr("disabled","disabled");
		$(".table").find("select").attr("disabled","disabled");
	}
})

$(document).ready(function(){
	$("input[name='oneSelect']").click(function(){
		var falg = false;
		$.each($("input[name='oneSelect']"),function(i,n){
			if(!$(this).prop("checked")){
				falg = true;
			}
		});
		if(falg){
			$("#allSelect").prop("checked", "");
		}else{
			$("#allSelect").prop("checked", "checked");
		}
	})
	
	$("#allSelect").click(function(){
		if($(this).prop("checked")){
			$.each($("input[name='oneSelect']"),function(i,n){
				$(this).prop("checked", "checked");
			});
		}else{
			$.each($("input[name='oneSelect']"),function(i,n){
				$(this).prop("checked", "");
			});
		}
		
	})
	
	
	
	$('#submit').click(function() {
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		$("textarea").removeClass("errorbor");
		$("input[name='afterSaleNums']").val('');
		$("input[name='sku']").val('');

		if($("#shtype").val()=='th'||$("#shtype").val()=='hh'||$("#shtype").val()=='tp'){
			var reason=$('input:radio[name="reason"]:checked').val();
			if(reason==undefined||reason==""){
				$("#reasonError").css("display","");
				return false;
			}else{
				$("#reasonError").css("display","none");
			}
		}
		
		if($("#shtype").val()=='th' || $("#shtype").val()=='hh' || $("#shtype").val()=='aw' || $("#shtype").val()=='jz' || $("#shtype").val()=='qt'){
			
			var len = $("input:checkbox:checked").length;
			if($("#allSelect") && $("#allSelect").is(':checked')){
				len--;
			}
			
			if(!(len > 0)){
				layer.alert("未选择售后商品，不允许提交！");
				return false;
			}
			var falg = false;
			var numReg = /^([1]?\d{1,10})$/;
			var refundAmount = Number(0);
			var num=0;
			var sum=0;
			var skus="";
			var price=0;
			$.each($("input[name='oneSelect']"),function(i,n){
				if($(this).prop("checked")){
					var saleorderGoodsId = $(this).attr("alt");
					 num = $("input[alt1='"+saleorderGoodsId+"']").val();
					 sum = $("span[alt1='"+saleorderGoodsId+"']").html();
					var goodsId = $(this).siblings("input[name='goodsIds']").val();
					 price = parseFloat($(this).siblings("input[name='prices']").val());
					var deliveryNum = $("#deliveryNums_"+saleorderGoodsId).val();
					 skus = $(this).siblings("input[name='skus']").val();
					var isActionGoods=$(this).siblings("input[name='isActionGoodses']").val();
					
					if($("#shtype").val() != 'jz' && $("#shtype").val()!='qt'){
						if(num == '' || num == undefined || num == 0){
							layer.alert("售后数量为空，不允许提交！")
							falg = true;
							return false;
						}else if(!numReg.test(num)){
							$("input[alt1='"+saleorderGoodsId+"']").addClass("errorbor");
							layer.alert("售后数量必须为大于等于0的正整数");
							falg = true;
							return false;
						}else if( Number(num) > Number(deliveryNum) && ($("#shtype").val()=='hh' || $("#shtype-aw").val()=='at'|| $("#shtype-wx").val()=='wx')){
							$("input[alt1='"+saleorderGoodsId+"']").addClass("errorbor");
							layer.alert("售后数量不允许超过已发货上限！");
							falg = true;
							return false;
						}
						else if( Number(num) > Number(sum) && $("#shtype").val()=='th'){
							$("input[alt1='"+saleorderGoodsId+"']").addClass("errorbor");
							layer.alert("售后数量不允许超过可退货上限！");
							falg = true;
							return false;
						}
					}

				refundAmount += Number(num)*Number(price);
				$(this).siblings("input[name='afterSaleNums']").val(saleorderGoodsId+"|"+num+"|"+$("#"+saleorderGoodsId).val()+"|"+goodsId+"|"+isActionGoods);//销售产品id+售后数量+直发普发
				$(this).siblings("input[name='sku']").val(skus);
			}
				$.each($("input[name='oneSelect']"),function(i,n) {
					var num2;
					var saleorderGoodsId2 = $(this).attr("alt");
					if($(this).prop("checked")){
						num2= $("input[alt1='"+saleorderGoodsId2+"']").val();
					}else{
						num2=0;
					}

					var sum2 = $("span[alt1='"+saleorderGoodsId2+"']").html();
					var price2 = parseFloat($(this).siblings("input[name='prices']").val());
					var skus2 = $(this).siblings("input[name='skus']").val();
					if(skus2===skus&&price!==price2){
						if(price<price2){
							if(num2!==sum2){
								layer.alert("请优先选择单价高的商品进行退货");
								falg = true;
								return false;
							}
						}
						if(price>price2) {
							if (num < sum && num2 > 0) {
								layer.alert("请优先选择单价高的商品进行退货");
								falg = true;
								return false;
							}
						}
					}

				});
			});
			$("#refundAmount").val(refundAmount);
			if(falg){
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
		}
		if($("#shtype").val()=='hh'){
			if($("#traderAddressId").val()==''){
				warnErrorTips("traderAddressId","traderAddressIdError","收货地址不允许为空");//文本框ID和提示用语
				return false;
			}
			var traderAddressId = $("#traderAddressId").val().split("|")[0];
			var areaIds = $("#"+traderAddressId).val();
			if(areaIds != '' && areaIds.split(",").length != 3){
				warnErrorTips("traderAddressId","traderAddressIdError","收货地址请补充完省市区");//文本框ID和提示用语
				return false;
			}
			
			
		}

		/*
		if($("#shtype").val()=='th'){
			var refund=$('input:radio[name="refund"]:checked').val();
			if(refund==undefined||refund==""){
				warnErrorTips("refund","refundError","款项退还不允许为空");
				return false;
			}
			
			if($("#traderSubject").val()==''){
				warnErrorTips("traderSubject","traderSubjectError","交易主体不允许为空");//文本框ID和提示用语
				return false;
			}
			var refund=$('input:radio[name="refund"]:checked').val();
			if(refund == '2'){
				if($("#payee").val()==''){
					warnErrorTips("payee","payeeError","收款名称不允许为空");//文本框ID和提示用语交易主体不允许为空
					return false;
				}
				if($("#payee").val().length > 128){
					warnErrorTips("payee","payeeError","收款名称不允许超过128个字符");//文本框ID和提示用语
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
			}
		}*/
		if($("#shtype").val()=='aw'){
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
		if($("#shtype").val()=='tp'){
			var len = $("input:checkbox:checked").length; 
			if(len == 0){
				layer.alert("未选择发票，不允许提交！");
				return false;
			}
			$.each($("input[name='oneSelect']"),function(i,n){
				if($(this).prop("checked")){
					var invoiceId = $(this).attr("alt");
					$(this).siblings("input[name='invoiceIds']").val(invoiceId);
				}
			});
			if($("#comments").val()==''){
				warnErrorTips("comments","commentsError","详情说明不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#comments").val().length > 512){
				warnErrorTips("comments","commentsError","详情说明不允许超过512个字符");//文本框ID和提示用语
				return false;
			}
		}
		if($("#shtype").val()=='jz' || $("#shtype").val()=='tk'){
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
		}

		if($("#shtype").val()=='tk'){
			//退款金额不得大于（客户实付金额-订单实际金额)
			var realAmount = $("#realAmount").val();
			var payedAmount = $("#payedAmount").val();
			var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			var refundAmount = $("#refundAmount").val();
			if(refundAmount ==''){
				warnErrorTips("refundAmount","refundAmountError","退款金额不允许为空");//文本框ID和提示用语
				return false;
			}
			if(refundAmount ==0){
				warnErrorTips("refundAmount","refundAmountError","退款金额不允许为0");//文本框ID和提示用语
				return false;
			}
			if(!priceReg.test(refundAmount)){
				warnErrorTips("refundAmount","refundAmountError","退款金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
			if(Number(refundAmount)>(Number(payedAmount)-Number(realAmount)).toFixed(2)){
				warnErrorTips("refundAmount","refundAmountError","退款金额不得大于（客户实付金额-订单实际金额)");//文本框ID和提示用语
				return false;
			}
			var traderMode=$('input:radio[name="traderMode"]:checked').val();
			var traderSubject=$('input:radio[name="traderSubject"]:checked').val();
			if($("#payee").val()==''){
				warnErrorTips("payee","payeeError","收款名称不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#payee").val().length > 128){
				warnErrorTips("payee","payeeError","收款名称不允许超过128个字符");//文本框ID和提示用语
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
			var msg = '';
			if(traderMode == '521'){
				msg = '银行帐号';
			}else if(traderMode == '520'){
				msg = '支付宝帐号';
			}
			if($("#bankAccount").val()==''){
				warnErrorTips("bankAccount","bankAccountError",msg+"不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#bankAccount").val().length > 32){
				warnErrorTips("bankAccount","bankAccountError",msg+"不允许超过32个字符");//文本框ID和提示用语
				return false;
			}
			if(traderMode == '521' && $("#bankCode").val()!='' && $("#bankCode").val().length > 32){
				warnErrorTips("bankCode","bankCodeError","开户行支付联行号不允许超过32个字符");//文本框ID和提示用语
				return false;
			}
			if(traderMode == '520'){
				$("#bankCode").val('');
			}
/*
			if(Number(refundAmount)>(Number(realAmount)-Number(payedAmount))){
				layer.alert("退款金额不得大于（客户实付金额-订单实际金额");
				return false;
			}*/
			
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
	});
	
	$('input:radio[name="refund"]').click(function(){
		var refund=$('input:radio[name="refund"]:checked').val();
		if(refund ==1){
			$("#li0").hide();
			$("#li1").hide();
			$("#li2").hide();
			$("#li3").hide();
			$("#li4").hide();
			$("#li5").hide();
		}else if(refund ==2){
			$("#li0").show();
			$("#li1").show();
			$("#li2").show();
			$("#li3").show();
			$("#li4").show();
			$("#li5").show();
		}
	})
	
	$('input:radio[name="traderMode"]').click(function(){
		var traderMode=$('input:radio[name="traderMode"]:checked').val();
		if(traderMode == '519'){
			$("#bank").val("");
			$("#bankAccount").val("");
			$("#bankCode").val("");
		}else if(traderMode == '520'){
			$("#bank").val("支付宝");
			$("#bankAccount").val('');
			$("#bankCode").val('');
		}
	})
	
	$('input:radio[name="traderSubject"]').click(function(){
		var traderSubject=$('input:radio[name="traderSubject"]:checked').val();
		if(traderSubject == '2'){
			$("#payee").val("");
			$("#bank").val("");
			$("#bankAccount").val("");
			$("#bankCode").val("");
			
		}else if(traderSubject == '1'){
			$("#payee").val($("#payee1").val());
			$("#bank").val($("#bank1").val());
			$("#bankAccount").val($("#bankAccount1").val());
			$("#bankCode").val($("#bankCode1").val());
		}
	})
	
	$("#sub").click(function(){
		checkLogin();
		$(".warning").remove();
		$("#myformChild input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		var refund=$('input:radio[name="refund"]:checked').val();
		if(refund==undefined||refund==""){
			warnErrorTips("refund","refundError","款项退还不允许为空");
			return false;
		}
		if(refund == '2'){
			if($("#myformChild input:radio[name='traderMode']:checked").val()=='' || $("#myformChild input:radio[name='traderMode']:checked").val()== undefined){
				warnErrorTips("traderMode","traderModeError","交易方式不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#myformChild input:radio[name='traderSubject']:checked").val()=='' || $("#myformChild input:radio[name='traderSubject']:checked").val()== undefined){
				warnErrorTips("traderSubject","traderSubjectError","交易主体不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#myformChild #payee").val()==''){
				warnErrorTips("payee","payeeError","收款名称不允许为空");//文本框ID和提示用语交易主体不允许为空
				return false;
			}
			if($("#myformChild #payee").val().length > 128){
				warnErrorTips("payee","payeeError","收款名称不允许超过128个字符");//文本框ID和提示用语
				return false;
			}
			if($("#myformChild #bank").val()==''){
				warnErrorTips("bank","bankError","开户银行不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#myformChild #bank").val().length > 256){
				warnErrorTips("bank","bankError","开户银行不允许超过256个字符");//文本框ID和提示用语
				return false;
			}
			var traderMode=$('#myformChild input:radio[name="traderMode"]:checked').val();
			var msg = '';
			if(traderMode == '521'){
				msg = '银行帐号';
			}else if(traderMode == '520'){
				msg = '支付宝帐号';
			}
			if($("#myformChild #bankAccount").val()==''){
				warnErrorTips("bankAccount","bankAccountError",msg+"不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#myformChild #bankAccount").val().length > 32){
				warnErrorTips("bankAccount","bankAccountError",msg+"不允许超过32个字符");//文本框ID和提示用语
				return false;
			}
			if(traderMode == '521' && $("#bankCode").val()!='' && $("#bankCode").val().length > 32){
				warnErrorTips("bankCode","bankCodeError","开户行支付联行号不允许超过32个字符");//文本框ID和提示用语
				return false;
			}
			if(traderMode == '520'){
				$("#bankCode").val('');
			}
			debugger;
		}
		$.ajax({
			url:page_url+'/aftersales/order/saveAfterSalesApply.do',
			data:$('#myformChild').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					window.location.reload();
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
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' 
			&& strExtension != 'pdf' && strExtension != 'doc' && strExtension != 'docx') {
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

function closeLayer() {
	layer.close($('#layerIndex').val());
}
