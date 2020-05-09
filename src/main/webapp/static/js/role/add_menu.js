$(function(i){
	var $form = $("#addMenuForm");
	$form.submit(function() {
		checkLogin();
		var groupIdArr = [];
		$form.find("div[id='group']").children("input").each(function(){
			if($(this).is(":checked")){
				groupIdArr.push($(this).val());
			}
		});
		var actionIdArr = [];
		$form.find("li[id='action']").children("input").each(function(){
			if($(this).is(":checked")){
				actionIdArr.push($(this).val());
			}
		});
		$.ajax({
			async:false,
			url : './savemenu.do',
			data: {"groupIdArr":JSON.stringify(groupIdArr),"actionIdArr":JSON.stringify(actionIdArr),"roleId":$form.find("input[name='roleId']").val()},
			type: "POST",
			dataType : "json",
			beforeSend:function(){
				if(!(groupIdArr && groupIdArr.length > 0)){
					warnTips("showInptId","请选择功能节点");//文本框ID和提示用语
					return false;
				}
			},
			success:function(data){
				layer.msg(data.message);
				if (data.code == 0) {
					refreshList();
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
	});
})

function chooseGroup(num,obj){
	checkLogin();
	if($("#group"+num).is(":checked")){
		$("#addMenuForm").find(".warning").remove();
		sel(obj,true);
		if($("#action"+num)!=undefined){
			$("input[id='action"+num+"']").prop("checked",true);
		}
		cancel($("#group"+num).attr("name"),true);
	}else{
		sel(obj,false);
		if($("#action"+num)!=undefined){
			$("input[id='action"+num+"']").prop("checked",false);
		}
		cancel($("#group"+num).attr("name"),false);
	}
}
function sel(obj,flag){
	checkLogin();
	$("input[name='"+$(obj).val()+"']").each(function(){
		$(this).prop("checked",flag);
		var id = $(this).attr("id");
		var count = id.substring(5,id.length);
		if($("#action"+count)!=undefined){
			$("input[id='action"+count+"']").prop("checked",flag);
		}
		if($(this).attr("name")!=undefined && $(this).attr("name")!=null && $(this).attr("name")!=""){
			sel(this,flag);
		}
	});
}

function chooseAction(num,obj){
	checkLogin();
	var flag = false;
	$("input[id='action"+num+"']").each(function(n){
		if($(this).is(":checked")){
			flag = true;return;
		}
	});
	var parent_id = $("#group"+num).attr("name");
	if(flag){
		$("#addMenuForm").find(".warning").remove();
		$("#group"+num).prop("checked",true);
		cancel(parent_id,flag);
	}else{
		$("input[id='action"+num+"']").each(function(i){
			if($(this).is(":checked")){
				flag = true;return;
			}
		});
		if(!flag){
			$("#group"+num).prop("checked",false);
			cancel(parent_id,flag);
		}
	}
}
function cancel(parent_id,flag){
	checkLogin();
	if($("input[value='"+parent_id+"']")!=undefined){
		if(!flag){//如果是取消
			var gFlag = false;
			$("input[name='"+parent_id+"']").each(function(){
				if($(this).is(":checked")){
					gFlag=true;return;
				}
			});
			if(!gFlag){
				$("input[value='"+parent_id+"']").prop("checked",flag);
			}
		}else{
			$("input[value='"+parent_id+"']").prop("checked",flag);
		}
		var name = $("input[value='"+parent_id+"']").attr("name");
		cancel(name,flag);
	}
}
