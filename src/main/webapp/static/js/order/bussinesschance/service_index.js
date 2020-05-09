$(function() {
	
	if($("#isRest").val()==1){
		reset();
	}
	//单个checkbox 联动全选
	$("input[name='bussinessChanceId']").click(function(){
		checkLogin();
		if(!$(this).prop("checked")){
			$("#selectAll").prop("checked",false);
		}else{
			var all = true;
			$.each($("input[name='bussinessChanceId']"),function(i,n){
				if(!$(n).prop("checked")){
					all = false;
				}
			});
			if(all){
				$("#selectAll").prop("checked",true);
			}
		}
	});
});


//全选
function selectAll(obj){
	checkLogin();
	if($(obj).prop("checked")){
		$.each($("input[name='bussinessChanceId']"),function(){
			$(this).prop("checked",true);
		});
	}else{
		$.each($("input[name='bussinessChanceId']"),function(){
			$(this).prop("checked",false);
		});
	}
}
//批量分配
function assignAll(){
	checkLogin();
	if($("input[name='bussinessChanceId']:checked").length == 0){
		layer.alert("选择分配商机的条数不能为0");
		return false;
	}
	var userId = $("#toSaler").val();
	if(userId == 0){
		layer.alert("分配的销售不能为空");
		return false;
	}
	var ids = "";var nos = "";
	$.each($("input[name='bussinessChanceId']:checked"),function(i,n){
		if(i+1 != $("input[name='bussinessChanceId']:checked").length){
			ids += $(this).val() + ",";
			nos += $(this).attr("id") + ",";
		}else{
			ids += $(this).val();
			nos += $(this).attr("id");
		}
	});
	
	var index=layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/order/bussinesschance/assignbussinesschance.do",
				data: {'userId':userId,'ids':ids,'nos':nos},
				dataType:'json',
				success: function(data){
					$.each($("input[name='bussinessChanceId']:checked"),function(i,n){
						  $(this).parents("tr").find(".saleUser").html($("#toSaler option:selected").text());
						$(this).remove()
					});
					//refreshPageList(data)
					layer.close(index)
				},
				error:function(){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}else{
						alert('操作失败');
					}
				}	
			});
		}, function(){
	});
}

function exportList(){
	checkLogin();
	location.href = page_url + '/report/service/exportbussinesschancelist.do?' + $("#search").serialize();
}