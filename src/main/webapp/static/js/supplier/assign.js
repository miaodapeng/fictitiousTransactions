$(function() {
	$("input[name='type']").click(function(){
		var type = $(this).val();
		
		if(type == 1){//单个
			$(".single_assign").show();
			$(".batch_assign").hide();
			
		}
		if(type == 2){//批量
			$(".single_assign").hide();
			$(".batch_assign").show();
			
		}
	});
	
});

/**
 * 搜索
 * @returns
 */
function search(obj){
	checkLogin();
	$(".font-red").remove();
	$("select[name='from_user']").removeClass("errorbor");
	$("input[name='traderName']").removeClass("errorbor");
	var type = $("input[name='type']:checked").val();
	if(type == 1 && $("input[name='traderName']").val() == ''){
		var msg = '<div class="font-red">供应商名称不能为空</div>';
		$(obj).parent().parent().after(msg);
		$("input[name='traderName']").addClass("errorbor");
		return false;
	}
	
	if(type == 2 && $("select[name='from_user']").val() == 0){
		$("select[name='from_user']").addClass("errorbor");
		var msg = '<div class="font-red mt2">请选择采购人员</div>';
		$(obj).parent().parent().after(msg);
		return false;
	}
	
	$("#form").attr("action",page_url+"/trader/supplier/assign.do");
	var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
    $("body").prepend(div); //jq获取上一页框架的父级框架；;
	$("#form").submit();
}

function formSub(){
	checkLogin();
	$("select[name='single_to_user']").removeClass("errorbor");
	$("select[name='batch_to_user']").removeClass("errorbor");
	$(".font-red").remove();
	var type = $("input[name='type']:checked").val();
	var traderId = 0;
	var single_to_user = 0;
	var from_user = 0;
	var batch_to_user = 0;
	if(type == 1 && $("input[name='tarderId']:checked").length == 0){
		var msg = '<div class="font-red" style="margin-top: -5px; margin-bottom: 10px;">分配供应商数量不能小于1</div>';
		
		$(".single_to_user").before(msg);
		return false;
	}
	if(type == 1 && $("select[name='single_to_user']").val() == 0){
		var msg = '<div class="font-red mt2">请选择采购人员</div>';
		$("select[name='single_to_user']").addClass("errorbor");
		$("select[name='single_to_user']").after(msg);
		return false;
	}
	if(type == 2 && $("select[name='batch_to_user']").val() == 0){
		var msg = '<div class="font-red mt2">请选择采购人员</div>';
		$("select[name='batch_to_user']").addClass("errorbor");
		$("select[name='batch_to_user']").after(msg);
		return false;
	}
	
	if(type == 1){
		traderId = $("input[name='tarderId']:checked").val();
		single_to_user = $("select[name='single_to_user']").val();
	}
	
	if(type == 2){
		from_user = $("select[name='from_user']").val();
		batch_to_user = $("select[name='batch_to_user']").val();
	}
	
	$.ajax({
		type : "POST",
		url : page_url + '/trader/supplier/saveassign.do',
		data : {type:type,traderId:traderId,single_to_user:single_to_user,from_user:from_user,batch_to_user:batch_to_user},
		dataType : 'json',
		success : function(data) {
			layer.alert(data.message, {
				  closeBtn: 0,
				  btn: ['确定'] //按钮
				}, function(){
					search();
				});
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
	
}