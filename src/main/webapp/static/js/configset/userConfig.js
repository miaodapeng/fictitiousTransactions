//移除人员
function delUserConfig(groupId,userId){
	checkLogin();
	layer.confirm('确认移除该人员吗，确认移除后，该人员将不计入五行剑法的计算统计中！确认后立即生效', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./delUserConfig.do",
				data: {'groupId':groupId,'userId':userId},
				dataType:'json',
				success: function(data){
					refreshNowPageList(data)
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
	});
}
//新增人员
function addUserConfig(){
	checkLogin();
	var obj=$("input[name='checkOne']:checked");
	var userIds="";
	for(var i=0; i<obj.length; i++){
		if(obj[i]!=null) {
			userIds+=obj[i].value+","; //如果选中，将value添加到变量s中
		}
	}
	if(userIds==''){
		layer.alert("未选择任何数据！");
		return false;
	}else{
		userIds=userIds.substring(0,userIds.length-1);
	}
	var groupId=$("#groupIds").val();
	$.ajax({
		type: "POST",
		url: "./addUserConfig.do",
		data: {'userIds':userIds,'groupId':groupId},
		dataType:'json',
		success: function(data){
			parent.layer.close(index);
			parent.location.reload();
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}
function submitData(){
	checkLogin();
	var $form = $("#submit");
	var goalYear=$("#goalYear").val();
	var goalMonth=$("#goalMonth").val();
	if(!goalYear){
		$("#warn").html("年度目标不可为空！");
		return false;
	}
	if(parseInt(goalYear)==0){
		$("#warn").html("年度目标不可为0！");
		return false;
	}
	if(!goalMonth){
		$("#warn").html("本月目标不可为空！");
		return false;
	}
	if(parseInt(goalMonth)==0){
		$("#warn").html("本月目标不可为0！");
		return false;
	}
	if(parseInt(goalYear)<parseInt(goalMonth)){
		$("#warn").html("年度目标不可 小于 本月目标！");
		return false;
	}
	if(goalYear==goalMonth){
		$("#warn").html("年度目标不可 等于 本月目标！");
		return false;
	}
	$.ajax({
		async:false,
		url:'./saveOrUpdateOneUserConfigData.do',
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

function batchEditGoal()
{
	
	var goalYear = $("#goalYear").val();
	
	if(goalYear.trim() == '')
	{
		layer.alert("年度目标不可为空");
		return false;
	}
	if(!/^\d+\.?\d{0,2}$/.test(goalYear))
	{
		layer.alert("年度目标：请输入数字，可精确到小数点后2位");
		return false;
	}
	
	var nowMonth = $("#nowMonth").val();
	var nowYear = $("#nowYear").val();
	var t_year = $("#t_year").val();
	
	var goalMonth = $("#goalMonth").val();
	var months = '';
	// 获取月度目标
	for(var i = 1; i < 13; i++)
	{
		if(i >= nowMonth)
		{			
			var gm = $("#goalMonth_" + i).val();
			if(gm.trim() == '')
			{
				alert("可输入的月度目标不可为空");
				return false;
			}
			if(!/^\d+\.?\d{0,2}$/.test(gm))
			{
				layer.alert(i + "月目标：请输入数字，可精确到小数点后2位");
				return false;
			}
			months += i + '_' + gm + ',';
		}
	}
	
	var userId = $("#userId").val();
	var yearId = $("#yearId").val();
	$.ajax
	({
		async:false,
		url:'./batchSaveOrUpdate.do',
		data: 
		{
			'reqType' : 1,
			'year' : t_year,
			'salesPerformanceGoalYearId': yearId,
			'goalYear' : goalYear,
			'userId' : userId,
			'extendStr' : months
		},
		type:"POST",
		dataType : "json",
		success:function(data)
		{
			refreshPageList(data);
		},
		error:function(data)
		{
			if(data.status ==1001)
			{
				layer.alert("当前操作无权限")
			}
		}
	})
	
}


function notIeOnChange(event)
{
	var id = $("#id").val();
	var dicno = event.target.value;// 获取input框实时输入的数据
	console.log(dicno);
	if(!/^\d+\.?\d{0,2}$/.test(dicno))
	{
		layer.alert("请输入数字，可精确到小数点后2位");
		return;
	}
}
//ie兼容模式
function ieOnChange(event)
{
	var id = $("#id").val();
	// 获取input框实时输入的数据
	var dicno = event.srcElement.value;
	console.log(dicno);
	if(!/^\d+\.?\d{0,2}$/.test(dicno))
	{
		layer.alert("请输入数字，可精确到小数点后2位");
		return;
	}
}


//新增小组人员
function addUserConfigs(salesPerformanceDeptId){
    checkLogin();
    var obj=$("input[name='checkOne']:checked");
    var  formToken = $("#formToken").val();
    var userIds="";
    for(var i=0; i<obj.length; i++){
        if(obj[i]!=null) {
            userIds+=obj[i].value+","; //如果选中，将value添加到变量s中
        }
    }
    if(userIds==''){
        layer.alert("未选择任何数据！");
        return false;
    }else{
        userIds=userIds.substring(0,userIds.length-1);
    }
    $.ajax({
        type: "POST",
        url: "./addDeptUserConfig.do",
        data: {'userIds':userIds,'salesPerformanceDeptId':salesPerformanceDeptId,'formToken':formToken},
        dataType:'json',
        success: function(data){
            parent.layer.close(index);
            parent.location.reload();
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}