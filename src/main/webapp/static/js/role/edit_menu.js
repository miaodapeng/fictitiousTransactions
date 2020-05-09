function btnSubmit(){
	checkLogin();
	var $form = $("#editMenuForm");
	var groupIdArr = [];
	$form.find("input[id^='group'][type='checkbox']:checked").each(function(i) {
		groupIdArr.push($(this).val());
	});

	var actionIdArr = [];
	$form.find("input[id^='action'][type='checkbox']:checked").each(function() {
		if ($(this).is(":checked")) {
			actionIdArr.push($(this).val());
		}
	});
	/*
	 * $.ajax({ async:false, url : './savemenu.do', data:
	 * {"groupIdArr":JSON.stringify(groupIdArr),"actionIdArr":JSON.stringify(actionIdArr),"roleId":$form.find("input[name='roleId']").val()},
	 * type: "POST", dataType : "json", beforeSend:function(){ if(!(groupIdArr &&
	 * groupIdArr.length > 0)){ warnTips("showInptId","请选择功能节点");//文本框ID和提示用语
	 * return false; } }, success:function(data){ layer.msg(data.message); if
	 * (data.code == 0) { refreshList(); } } })
	 */
	$form.append("<input type='hidden' id='groupIdArr' name='groupIdArr' value='" + JSON.stringify(groupIdArr) + "'>");
	$form.append("<input type='hidden' id='actionIdArr' name='actionIdArr' value='" + JSON.stringify(actionIdArr) + "'>");
	$form.submit();
	return false;
}

/**
 * 分组复选框点击事件
 * @param num
 * @param obj
 */
function chooseGroup(num,obj){
	checkLogin();
	if($("#group"+num).is(":checked")){
		$("#editMenuForm").find(".warning").remove();
		sel(num,true);
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

/**
 * 适用多级分组，后期如果扩展此处依然可以沿用
 * @param num
 * @param obj
 */
function chooseMutiGroup(obj){
	var boxid = $(obj).attr('id');
	var arr = boxid.split('-');
	//选定操作
	if ($(obj).prop("checked")) {
		//当前group下的所有选中
		$("input[id^=" + boxid + "-]").prop("checked", true);
		//当前group下的action所有选中
		var aBoxid = boxid.replace('group','action');
		$("input[id^=" + aBoxid + "-]").prop("checked", true);
		var id = arr[0];
		for (var i = 1; i < arr.length; i++) {
			id += "-" + arr[i];
			$("#" + id).prop("checked", true);
		}
	} else {
		//当前group下所有取消选中
		$("input[id^=" + boxid + "-]").prop("checked", false);
		//当前group下的action所有选中取消
		var aBoxid = boxid.replace('group','action');
		$("input[id^=" + aBoxid + "-]").prop("checked", false);
		for (var j = 1; j <= arr.length; j++) {
			arr.pop();
			id = arr.join("-");
			if ($("input[id^=" + id + "-]:checked").length == 0) {
				$("#" + id).prop("checked", false);
			}
		}
	}
}

/**
 * 适用多级分组，后期如果扩展此处依然可以沿用
 * 对于action而言，只需要关注选中哪些父节点，不需要关注子节点
 * @param num
 * @param obj
 */
function chooseMutiAction(obj){
	var sBoxid = $(obj).attr('id');
	//替换action成group
	var boxid = sBoxid.replace('action','group');
	var sArr = sBoxid.split('-');
	var arr = boxid.split('-');
	//选定操作
	if ($(obj).prop("checked")) {
		//向上找父节点勾选
		var id = arr[0];
		for (var i = 1; i < arr.length; i++) {
			id += "-" + arr[i];
			$("#" + id).prop("checked", true);
		}
	} else {
		sArr.pop();
		var action = sArr.join("-");
		//当前action全部被取消
		if($("input[id^=" + action + "-]:checked").length == 0){
			//判段是否存在其他选中的复选框
			//取消选定
			for (var j = 1; j <= arr.length; j++) {
				arr.pop();
				id = arr.join("-");
				if($("input[id^=" + id + "-]:checked").length == 0){
					$("#" + id).prop("checked", false);
				}
			}
		}
	}
}

//循环选中或取消分组
function selMuti(count,flag){
	checkLogin();
	$("input[id='group"+count+"']").each(function(){
		$(this).prop("checked",flag);
		if($("#action"+count)!=undefined){
			$("input[id='action"+count+"']").prop("checked",flag);
		}
		if($(this).attr("name")!=undefined && $(this).attr("name")!=null && $(this).attr("name")!=""){
			sel(this,flag);
		}
	});
}


/**
 * 节点点击事件
 * @param num
 * @param obj
 */
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
		$("#editMenuForm").find(".warning").remove();
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
//取消或选中节点，操作对应的分组
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
		if(name!=undefined){
			cancel(name,flag);
		}
	}
}
