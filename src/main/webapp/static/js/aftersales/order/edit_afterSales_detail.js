$(function(){
	$("#sub").click(function(){
		checkLogin();
		$(".warning").remove();
			if($("input:radio[name='traderMode']:checked").val()=='' || $("input:radio[name='traderMode']:checked").val()== undefined){
				warnErrorTips("traderMode","traderModeError","交易方式不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("input:radio[name='traderSubject']:checked").val()=='' || $("input:radio[name='traderSubject']:checked").val()== undefined){
				warnErrorTips("traderSubject","traderSubjectError","交易主体不允许为空");//文本框ID和提示用语
				return false;
			}
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
			var traderMode=$('input:radio[name="traderMode"]:checked').val();
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

function closeLayer() {
	layer.close($('#layerIndex').val());
}