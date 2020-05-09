$(function() {
	
	$("#search").submit(function(){
		var warrantyPeriod = $("#warrantyPeriod").val();
		if(warrantyPeriod!=""){
			if (warrantyPeriod.length > 16) {
				warnTipss("warrantyPeriod","zb_nx","质保年限不允许超过16个字符");
				return false;
			}
		}
		if ($("#warrantyPeriodRule").val()!="" && $("#warrantyPeriodRule").val().length > 256) {
			warnTips("warrantyPeriodRule","质保期限规则长度不允许超过256个字符");
			return false;
		}
		var warrantyRepairFee = $("#warrantyRepairFee").val();
		if(warrantyRepairFee!=""){
			if (warrantyRepairFee.length > 8) {
				warnTipss("warrantyRepairFee","bw_wx","保外维修长度不允许超过8个字符");
				return false;
			}
			if( ! /^[0-9]+(.[0-9]{0,2})?$/.test(warrantyRepairFee)){
				warnTipss("warrantyRepairFee","bw_wx","只能输入数字，小数点后只能保留两位");
				return false;
			}
		}
		var responseTime = $("#responseTime").val();
		if(responseTime!=""){
			if (responseTime.length > 16) {
				warnTipss("time_xy","xy_sj","响应时间长度不允许超过16个字符");
				return false;
			}
			if(!/^[0-9]*[1-9][0-9]*$/.test(responseTime)){
				warnTipss("responseTime","xy_sj","响应时间不允许输入非正整数");
				return false;
			}
		}
		if ($("#conditions").val()!="" && $("#conditions").val().length > 256) {
			warnTips("conditions","使用条件长度不允许超过256个字符");
			return false;
		}
		var extendedWarrantyFee = $("#extendedWarrantyFee").val();
		if(extendedWarrantyFee!=""){
			if (extendedWarrantyFee.length > 16) {
				warnTipss("extendedWarrantyFee","yb_jg","供应商延保价格不允许超过16个字符");
				return false;
			}
			if( ! /^[0-9]+(.[0-9]{0,2})?$/.test(extendedWarrantyFee)){
				warnTipss("extendedWarrantyFee","yb_jg","只能输入数字，小数点后只能保留两位");
				return false;
			}
		}
		if ($("#exchangeConditions").val()!="" && $("#exchangeConditions").val().length > 256) {
			warnTips("exchangeConditions","换货条件长度不允许超过256个字符");
			return false;
		}
		if ($("#exchangeMode").val()!="" && $("#exchangeMode").val().length > 256) {
			warnTips("exchangeMode","换货方式长度不允许超过256个字符");
			return false;
		}
		if ($("#freightDescription").val()!="" && $("#freightDescription").val().length > 256) {
			warnTips("freightDescription","运费说明长度不允许超过256个字符");
			return false;
		}
	})
	
	//初始化售后内容
	var a = $("input[name='655']").val();
	if(typeof(a) != "undefined"){
		$("input[id='655']").attr("checked", true);
	}
	var b = $("input[name='656']").val();
	if(typeof(b) != "undefined"){
		$("input[id='656']").attr("checked", true);
	}
	var c = $("input[name='657']").val();
	if(typeof(c) != "undefined"){
		$("input[id='657']").attr("checked", true);
	}
	
	//是否有备用机 
	var haveStandbyMachine = $("#haveStandbyMachine").val();
	if(haveStandbyMachine==-1){
		$("#haveStandbyMachine").val(1);
	}
	if(haveStandbyMachine==0){
		$("#isby").prop("checked", false);
	    $("#noby").prop("checked", true);
	    $("#isbyj").hide();
	}else{
		$("#isby").prop("checked", true);
	    $("#noby").prop("checked", false);
	}
	$("#isby").click(function() {
	    $("#haveStandbyMachine").val(1);
	    $("#isby").prop("checked", true);
	    $("#noby").prop("checked", false);
	    $("#isbyj").show();
	});
	$("#noby").click(function() {
	   $("#haveStandbyMachine").val(0);
	   $("#noby").prop("checked", true);
	   $("#isby").prop("checked", false);
	   $("#isbyj").hide();
	});
	
	//是否允许退货
	var isRefund = $("#isRefund").val();
	if(isRefund!=""){
		if(isRefund==0){
			 $("#noth").prop("checked", true);
			 $("#isth").prop("checked", false);
			 $("#hhtj").hide();
		     $("#hhfs").hide();
		     $("#yfsm").hide();
		}else if(isRefund==1){
			$("#isth").prop("checked", true);
		    $("#noth").prop("checked", false);
		    $("#hhtj").show();
		    $("#hhfs").show();
		    $("#yfsm").show();
		}
	}else{
		$("#isth").prop("checked", false);
	    $("#noth").prop("checked", false);
	}
	$("#isth").click(function() {
	    $("#isRefund").val(1);
	    $("#isth").prop("checked", true);
	    $("#noth").prop("checked", false);
	    $("#hhtj").show();
	    $("#hhfs").show();
	    $("#yfsm").show();
	});
	$("#noth").click(function() {
	   $("#isRefund").val(0);
	   $("#noth").prop("checked", true);
	   $("#isth").prop("checked", false);
	   $("#hhtj").hide();
	   $("#hhfs").hide();
	   $("#yfsm").hide();
	});
});

function con_add(i){
	var num = Number($("#conadd"+i).siblings(".form-blanks").length)+Number(1);
	var div = '<div class="form-blanks mt10">'+
					'<div class="pos_rel f_left">'+
				    '<input type="file" class="upload_file" id="file_'+i+num+'" name="lwfile" style="display: none;" onchange="uploadFile(this,'+i+num+');"  >'+
				    '<input type="text" class="input-largest" id="name_'+i+num+'" readonly="readonly"'+
				    	'placeholder="请上传附件" name="fileName'+i+'" onclick="file_'+i+num+'.click();" >'+
				    '<input type="hidden" id="uri_'+i+num+'" name="fileUri'+i+'" >'+
				'</div>'+
				'<label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $(\'#file_'+i+num+'\').click();">浏览</label>'+
				'<div class="f_left"><i class="iconsuccesss mt3 none" id="img_icon_'+i+num+'"></i>'+
				'<a href="" target="_blank" class="font-blue cursor-pointer mt3 none" id="img_view_'+i+num+'">查看</a>'+
				'<span class="font-red cursor-pointer mt3" onclick="del('+i+num+','+num+')" id="img_del_'+i+num+'">删除</span></div><div class="clear"></div>'+
				'</div>';
	$("#conadd"+i).before(div);
}
function con_add_vedio(){
	$("#img_del_41").show();
	var num = Number($("#conadd41").siblings(".form-blanks").length)+Number(1);
	var div = '<div class="form-blanks">'+
					'<div class="pos_rel f_left">'+
				    '<input type="text" class="input-largest" id="name_4'+num+'" '+' name="fileName41" " >'+
				    '<input type="hidden" id="uri_4'+num+'" name="fileUri41" >'+
				    '</div><div class="f_left">'+
				'<span class="font-red cursor-pointer mt3" onclick="del_vedio(4'+num+','+num+')" id="img_del_4'+num+'">删除</span></div><div class="clear"></div>'+
				'</div>';
	$("#conadd41").before(div);
}

function del_vedio(num,i){
	var sum = Number($("#conadd41").siblings(".form-blanks").length);
	var uri = $("#uri_"+num).val();
	if( sum > 1){
		$("#img_del_"+num).parent().parent(".form-blanks").remove();
	}else{
		if(i == 1){
			$("#img_del_" + num).hide();
		}
		$("#name_"+ num).val("");
		$("#uri_"+ num).val("");
		$("#file_"+ num).val("");
	}
}


function del(num,i){
	var n=0;
	if((num>200 && num<300)|| (num>20 && num<30)){
		n= 21;
	}else if(num>300 || (num >30 && num < 100) ){
		n = 31;
	}else if((num>100 && num<200) || (num>10 && num<20)){
		n = 11; 
	}
	var sum = Number($("#conadd"+n).siblings(".form-blanks").length);
	var uri = $("#uri_"+num).val();
	if(uri == '' && sum > 1){
		$("#img_del_"+num).parent().parent(".form-blanks").remove();
	}else{
		index = layer.confirm("您是否确认该操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				if(i == 1){
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

function search() {
	checkLogin();
	//售后内容
	var aftersaleContent ="_";
	$.each($('input:checkbox'),function(){
         if(this.checked){
        	 aftersaleContent+=$(this).val()+"_";
         }
     });
    $("#aftersaleContent").val(aftersaleContent);
    $("#search").submit();
}

//文本框验证--定位光标
function warnTipss(id,warnid,txt){
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	$("#"+id).siblings('.warning').remove();
	$("#"+warnid).after('<div class="warning">'+txt+'</div>');
	$("#"+id).focus();
	$("#"+id).addClass("errorbor");
	return false;
}