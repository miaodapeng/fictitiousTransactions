$(function(){
	$("#myform").submit(function(){
		checkLogin();
		$("input").removeClass("errorbor");
		if($("#rOrdergoodsLaunchGoodsJCategoryId").val() ==""){
			layer.alert("请添加设备");
			return false;
		}

		var st = true;
		$("input[name='startdate']").each(function(i,n){
			if($(n).val() == ''){
				st = false;
				$(n).addClass("errorbor");
			}
		});
		if(!st){
			layer.alert("开始时间必须填全");
			return false;
		}
		var ed = true;
		$("input[name='enddate']").each(function(i,n){
			if($(n).val() == ''){
				ed = false;
				$(n).addClass("errorbor");
			}
		});
		if(!ed){
			layer.alert("结束时间必须填全");
			return false;
		}

		var go = true;
		var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		$("input[name='goal']").each(function(i,n){
			if($(n).val() == '' || !reg.test($(n).val()) || Number($(n).val())>10000000){
				go = false;
				$(n).addClass("errorbor");
			}
		});
		if(!go){
			layer.alert("指标额仅允许使用数字，最多精确到小数后两位，不允许超过一千万 ");
			return false;
		}
		
		return true;
	});
});