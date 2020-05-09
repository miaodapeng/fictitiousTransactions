$(document).ready(function(){
	$('#submit').click(function() {
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		var flag = false;
		$.each($("input:text"),function(i,n){
			var val = $(this).val();
			var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			if(val != '' && val != undefined){	
				var id = $(this).attr("id");
				if(!priceReg.test(val)){
					$("#"+id).addClass("errorbor");
					$(this).val(0);
					flag = true;
					layer.alert("目标金额输入错误！仅允许使用数字，最多精确到小数后两位");
					return false;
				}
			}else{
				var orgId = $(this).attr("alt");
				var orgLeaderId = $(this).attr("alt1");
				$(this).siblings("input").val(0+"|"+orgId+"|"+orgLeaderId);
			}
		});
		if(flag){
			return false;
		}
		if($("input[name='positLevel']").val() == '443' || $("input[name='positLevel']").val() == '444'){
			var flag2 = false;
			for(var i =1; i<13;i++){
				if(Number($("#total_"+i).html())<Number($("#gogal_"+i).html())){
					flag2 = true;
					layer.alert("设定总值与目标不符！");
					return false;
				}
			}
			if(flag2){
				return false;
			}
		}
		

	});
});

function saveTotal(obj,orgId,orgLeaderId){
	if(orgLeaderId == undefined){
		layer.alert("请先设定当前部门的负责人！");
		return false;
	}
	checkLogin();
	$(".warning").remove();
	$("input").removeClass("errorbor");
	var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var goal = $(obj).val();
	var id = $(obj).attr("id");

	if(goal != '' && !priceReg.test(goal)){
		$("#"+id).addClass("errorbor");
		layer.alert("目标金额输入错误！仅允许使用数字，最多精确到小数后两位");
		return false;
	}
	var deptsum = $("#deptSum_"+orgId).html();
	if(deptsum == ''){
		$("#deptSum_"+orgId).html(Number(goal));
	}else{
		$("#deptSum_"+orgId).html(Number(goal)+Number(deptsum));
	}
	$(obj).siblings("input").val(goal+"|"+orgId+"|"+orgLeaderId);
	var num = id.substring(id.indexOf("_")+1,id.lastIndexOf("_"));
	var total = Number(0);
	$.each($("input[alt2='mon_"+num+"']"),function(i,n){
		if($(this).val() == ''){
			total += Number(0);
		}else{
			total += Number($(this).val());
		}
		
	});
	$("#total_"+num).html(total);
	var totalGoal = Number(0);
	$.each($(".total"),function(i,n){
		if($(this).html() == '' || $(this).html() == undefined){
			totalGoal +=Number(0);
		}else{
			totalGoal +=Number($(this).html());
		}
		
	})
	$("#totalGoal").html(totalGoal);
}


function updateTotal(obj,orgId,orgLeaderId,salesGoalSettingId){
	if(orgLeaderId == undefined){
		layer.alert("请先设定当前部门的负责人！");
		return false;
	}
	var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var goal = $(obj).val();
	var id = $(obj).attr("id");

	if(!priceReg.test(goal)){
		$("#"+id).addClass("errorbor");
		layer.alert("目标金额输入错误！仅允许使用数字，最多精确到小数后两位");
		return false;
	}
	$(obj).siblings("input").val(goal+"|"+orgId+"|"+orgLeaderId+"|"+salesGoalSettingId);
	var num = id.substring(id.indexOf("_")+1,id.lastIndexOf("_"));
	var total = Number(0);
	$.each($("input[alt2='mon_"+num+"']"),function(i,n){
		if($(this).val() == ''){
			total += Number(0);
		}else{
			total += Number($(this).val());
		}
		
	});
	$("#total_"+num).html(total);
	var totalGoal = Number(0);
	$.each($(".total"),function(i,n){
		if($(this).html() == '' || $(this).html() == undefined){
			totalGoal +=Number(0);
		}else{
			totalGoal +=Number($(this).html());
		}
		
	})
	$("#totalGoal").html(totalGoal);
}

function setDefault(logisticsId){
	checkLogin();
	layer.alert("请确认继续当前操作",{
		closeBtn: 0,
		btn: ['确定'] //按钮
	}, function(){
		$.ajax({
			type: "POST",
			url: page_url+"/home/page/saveSetDefaultLogistics.do",
			data: {'logisticsId':logisticsId,'isDefault':1},
			dataType:'json',
			async:false,
			success: function(data){
				if(data.code==0){
					self.location.reload();
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	});
	return false;
}

function setBuyDefault(addressId){
	checkLogin();
	layer.alert("请确认继续当前操作",{
		closeBtn: 0,
		btn: ['确定'] //按钮
	}, function(){
		$.ajax({
			type: "POST",
			url: page_url+"/home/page/saveSetDefaultBuyAddress.do",
			data: {'addressId':addressId},
			dataType:'json',
			async:false,
			success: function(data){
				if(data.code==0){
					self.location.reload();
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	});
	return false;
}
