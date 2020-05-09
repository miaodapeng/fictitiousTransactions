var status = 0;
if($("input[name='b_checknox']").length > 0){
	$.each($("input[name='b_checknox']").not(':disabled'),function(){
		if($(this).is(":checked")==false){
			status = 1;
		}
	});
	if(status == 1){
		
		$("input[name='allcheck']").prop('checked',false);
	}else{
		$("input[name='allcheck']").prop("checked",true);
	}
}

function addExpress(){
	checkLogin();
	var regA = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var logisticsNo = $("input[name='logisticsNo']").val();
	var logisticsId = $("#logisticsId").val();
	var deliveryTimes = $("input[name='deliveryTimes']").val();
	var amount = $("input[name='amount']").val();
	var logisticsComments = $("input[name='logisticsComments']").val();
	var expressId = $("input[name='expressId']").val();
	var buyorderId = $("input[name='buyorderId']").val();
	var buyorderNo = $("input[name='buyorderNo']").val();
	if (logisticsNo == undefined || logisticsNo == ""){
		warn2Tips("logisticsNo", "快递单号不允许为空");
		return false;
	}
	var reg = /^[A-Za-z0-9]+$/;
	if(!reg.test(logisticsNo)){
		warn2Tips("logisticsNo", "快递单号只允许数字和字母组合");// 文本框ID和提示用语
		return false;
	}

	if (logisticsNo.length > 64 ){
		warn2Tips("logisticsNo", "快递单号不允许超过64个字符");
		return false;
	}
	/**
	if (amount == undefined || amount == "") {
		warn2Tips("amount", "费用不允许为空");
		return false;
	}**/
	if(Number(amount)>10000000){
		warn2Tips("amount","运费不允许超过一千万");//文本框ID和提示用语
		return false;
	}
	if(amount.length>0 && !regA.test(amount)){
		warn2Tips("amount","运费输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
		return false;
	}
	if (logisticsComments.length > 1024 ){
		warn2Tips("logisticsComments", "备注不允许超过1024个字符");
		return false;
	}
	clear2ErrorMsg();	
	var id_num_price = "";
	$.each($("input[name='b_checknox']:checked"),function(){
		id_num_price += $(this).val()+"|"+$(this).parent().nextAll().find("input[name='num']").val()+"|"+$(this).parent().nextAll().find("input[name='price']").val()+"_";
	});
	if(id_num_price == ""){
		layer.alert("请选择产品");
		return false;
	}
	var formToken = $("input[name='formToken']").val();
	$.ajax({
		async : false,
		url : page_url + '/order/buyorder/saveAddExpress.do',
		data : {
			"logisticsNo" : logisticsNo,
			"logisticsId" : logisticsId,
			"logisticsComments" : logisticsComments,
			"deliveryTimes" : deliveryTimes,
			"amount" : amount,
			"id_num_price" : id_num_price,
			"expressId" : expressId,
			"buyorderId" : buyorderId,
			"buyorderNo" : buyorderNo,
			"formToken" : formToken
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
				layer.alert("操作成功", { icon : 1},function(index){
					pagesContrlpages(true,false,true)
				});
			}else{
				layer.alert(data.message, { icon : 2});
			}
			
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
		
	})
}

function checkNum(obj,allnum,addnum,nownum){
	checkLogin();
	var re = /^[0-9]+$/;
	var num = $(obj).val();
	if (num == undefined || num == "") {
		layer.alert("计重数量不允许为空");
		$(obj).val(0);
		return false;
	}
	if(Number(num)>10000){
		layer.alert("计重数量不允许超过一万");//文本框ID和提示用语
		$(obj).val(0);
		return false;
	}
	if(!re.test(num)){
		layer.alert("产品数量必须为正整数");//文本框ID和提示用语
		$(obj).val(0);
		return false;
	}
	if(num>(allnum-addnum+nownum)){
		layer.alert("超出可发货数量");//文本框ID和提示用语
		$(obj).val(nownum);
		return false;
	}
}

function canelAll(obj){
	checkLogin();
	var status = 0;
	$.each($("input[name='b_checknox']").not(':disabled'),function(){
		if($(this).is(":checked")==false){
			status = 1;
		}
	});
	if(status == 1){
		
		$("input[name='allcheck']").prop('checked',false);
	}else{
		$("input[name='allcheck']").prop("checked",true);
	}
}
//全选
function selectall(obj){
	checkLogin();
	if($(obj).is(":checked")){
			$("input[type='checkbox']").not(':disabled').prop("checked",true);
		}else{
			$("input[type='checkbox']").not(':disabled').prop('checked',false);
	}
}
