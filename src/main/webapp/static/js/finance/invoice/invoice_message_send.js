
function checkInput(obj){
	if($(obj).is(':checked')){
		var num = 0;
		$("input:checkbox[name='invoiceId']:checked").each(function(i){
			num ++;
		});
		if(num == $("input:checkbox[name='invoiceId']").length){
			$("#checkOptAll").prop("checked",true);
		}
	}else{
		$("#checkOptAll").prop("checked",false);
	}
}
function checkAllOpt(obj){
	if($(obj).is(':checked')){
		$("input:checkbox[name='invoiceId']").each(function(i){
			$(this).prop("checked",true);
		});
	}else{
		$("input:checkbox[name='invoiceId']").each(function(i){
			$(this).prop("checked",false);
		});
	}
}

function sendInvoiceSms(){
	var invoiceIdArr = [];
	$("input:checkbox[name='invoiceId']:checked").each(function(i){
		invoiceIdArr.push($(this).val());
	});
	if(invoiceIdArr.length == 0){
		layer.alert("请选择要推送的发票！");
		return false;
	}
	checkLogin();
	layer.confirm("确认推送电子发票邮件吗？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			/*var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
			$("body").prepend(div);*/
			$.ajax({
				type: "POST",
				url: page_url + "/finance/invoice/sendInvoiceSmsAndMail.do?invoiceIdArr="+JSON.stringify(invoiceIdArr)+"&formToken="+$("#formToken").val(),
				dataType:'json',
				success: function(data){
					/*$(".tip-loadingNewData").remove();*/
					if(data.code==0){
						layer.alert(data.message, {icon: 1},
								function () {
							$('#close-layer').click();
						});
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					/*$(".tip-loadingNewData").remove();*/
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	);
}