/*$(function() {
	var $form = $("#submit");
	$form.submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./addCategorySave.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){  
				var weight = $(".weight");
				alert(wight);
				return false;
				var count=0;
				for(var i=0;i<weight.length;i++){
					count=count+$("#configVoList["+i+"].weight").val()
				}
				
				if (level>=3) {
					warnTips("category_div","分类层级不允许超过三级");//文本框ID和提示用语
					return false;
				}

			},
			success:function(data){
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
	});
});*/

function submitData(){
		checkLogin();
		var $form = $("#submit");

        var deptName = $("#deptName").val();

        var ids = $("#ids").val();

        deptName   =   deptName.replace(/\s+/g,"")

        if(deptName =="" || deptName == null || deptName.length == 0){
            warnTips("deptName","请填写小组名称");
            return false;
        }

        if(ids =="" || ids == null || ids == 0 ){
            warnTips("idsName","请选择负责人!");
            return false;
        }

		$.ajax({
			async:false,
			url:'./insertTeam.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			success:function(data){
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
}

function submitDatas(){
    checkLogin();
    var $form = $("#submit");
    var weight = $(".weight");
    var count=0;


    var ids = $("#ids").val();


    var groupName = $("#groupName").val();

    groupName   =   groupName.replace(/\s+/g,"");


    if(groupName =="" || groupName == null || groupName.length == 0 ){
        warnTips("groupName","请填写团队名称!");
        return false;
    }


    for(var i=0;i<weight.length;i++){
        if(!$("#configVoList-"+i).val()){
            $('#warn').html("各项考核项占比不可为空，请检查后提交！");
            return false;
        }
        if($("#configVoList-"+i).val().indexOf(".")>-1){
            $('#warn').html("各项考核项占比不可为小数，请检查后提交！");
            return false;
        }
        count=count+parseFloat($("#configVoList-"+i).val());
    }
    if(count!=100){
        /*warnTips("warn","各项考核项占比总和不为100%，请检查后提交！");//文本框ID和提示用语*/
        $('#warn').html("各项考核项占比总和不为100%，请检查后提交！");
        return false;
    }else{
        $('#warn').html("");
    }

    if(ids =="" || ids == null || ids == 0 ){
        warnTips("idsName","请选择负责人!");
        return false;
    }


    $.ajax({
        async:false,
        url:'./insertGroup.do',
        data:$form.serialize(),
        type:"POST",
        dataType : "json",
        success:function(data){
            if (data.code == 0){
                parent.window.location.reload();
                layer.close(index);
            } else {
                idex =  layer.confirm("编辑失败！", {
                    btn: ['知道了'] //按钮
                }, function () {
                    layer.close(idex);
                });
            }
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    })
    return false;
}

/**
 * 小组编辑提交
 * @returns {boolean}
 */
function submitDatass(){
    checkLogin();
    var $form = $("#submits");
    $.ajax({
        async:false,
        url:'./editDeptUser.do',
        data:$form.serialize(),
        type:"POST",
        dataType : "json",
        success:function(data){
            refreshPageList(data);
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    })
    return false;
}


function submitDataGroup(){
    checkLogin();
    var $form = $("#submit");
    var weight = $(".weight");
    var count=0;

    var ids = $("#ids").val();

    var groupName = $("#groupName").val();

    groupName   =   groupName.replace(/\s+/g,"");

    if(groupName =="" || groupName == null || groupName.length == 0 ){
        warnTips("groupName","请填写团队名称");
        return false;
    }

    for(var i=0;i<weight.length;i++){
        if(!$("#configVoList-"+i).val()){
            $('#warn').html("各项考核项占比不可为空，请检查后提交！");
            return false;
        }
        if($("#configVoList-"+i).val().indexOf(".")>-1){
            $('#warn').html("各项考核项占比不可为小数，请检查后提交！");
            return false;
        }
        count=count+parseFloat($("#configVoList-"+i).val());
    }
    if(count!=100){
        /*warnTips("warn","各项考核项占比总和不为100%，请检查后提交！");//文本框ID和提示用语*/
        $('#warn').html("各项考核项占比总和不为100%，请检查后提交！");
        return false;
    }else{
        $('#warn').html("");
    }


    if(ids =="" || ids == 0 ){
        warnTips("idsName","选择负责人!");
        return false;
    }

    $.ajax({
        async:false,
        url:'./saveOrUpdateConfigSetData.do',
        data:$form.serialize(),
        type:"POST",
        dataType : "json",
        success:function(data){
            refreshPageList(data);
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    })
    return false;
}

function addOrgUser(obj) {
    var org = '<div class="form-blanks">';
    org += '</div>';
    $(obj).parent("div").after(org);
    $(obj).parent("div").find("select").eq(0).clone().appendTo($(obj).parent("div").next('div'));
    var posit = '<div class="f_left bt-bg-style bt-middle bg-light-red" onclick="delOrgUser(this);">删除</div>';
    $(obj).parent("div").next('div').append(posit);
    $(obj).parent("div").next('div').find("select").val(0);
}

/**
 * 删除小组负责人
 * @param obj
 * @returns
 */
function delOrgUsers(obj)
{
    // 获取上一个兄弟节点
    var delUserId = $(obj).prev().val();
    var  salesPerformanceDeptId = $("#salesPerformanceDeptId").val();


    if ( delUserId != null &&salesPerformanceDeptId != null ) {
        $.ajax({
            async:false,
            url:'./delDeptUser.do',
            data:{'userId':delUserId,'salesPerformanceDeptId':salesPerformanceDeptId},
            type:"POST",
            dataType : "json",
            success:function(data){

                $(obj).parent("div").remove();
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        })
    }
}

function delOrgUser(obj)
{
    $(obj).parent("div").remove();
}

/**
* 删除团队负责人
* @param obj
* @returns
*/
function delGroupUsers(obj)
{
    // 获取上一个兄弟节点
    var delUserId = $(obj).prev().val();
    var  salesPerformanceGroupId = $("#salesPerformanceGroupId").val();
    /*console.log("删除userId:" + delUserId+"小组的ID"+salesPerformanceGroupId);*/

    if ( delUserId != null &&salesPerformanceGroupId != null ) {
        $.ajax({
            async:false,
            url:'./delGroupUser.do',
            data:{'userId':delUserId,'salesPerformanceGroupId':salesPerformanceGroupId},
            type:"POST",
            dataType : "json",
            success:function(data){
                $(obj).parent("div").remove();
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        })
    }
}