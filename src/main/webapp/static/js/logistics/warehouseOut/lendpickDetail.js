$(function(){
	/*var pn = $("#_pickNums").val();
	if($(window.parent.document).find('.active').eq(1).children('iframe').attr('src').indexOf("_flag")<0){
	$(window.parent.document).find('.active').eq(1).children('iframe').attr('src',"warehouse/warehousesout/viewPickingDetail.do?saleorderId=19433&flag=2&_flag=0");
	}*/
})
//全选
function selectall(obj,name){
	checkLogin();
	if($(obj).is(":checked")){
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop("checked",true);
		$("input[id='"+name+"']").not(':disabled').prop("checked",true);
		}else{
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop('checked',false);
		$("input[id='"+name+"']").not(':disabled').prop('checked',false);
			}
}
//根据日期选择
function checkbarcode(m,f,id){
	checkLogin();
	var flag=0;
	if(!m)	return false;
	$("input[name='"+id+"']").each(function(){
		if($(this).attr("alt") == m){
			$(this).prop('checked',f);
		}
		if(!$(this).is(":checked")){
			flag=1;
		}
	});
	if(flag==1){
		$("input[name='all_"+id+"']").prop('checked',false);
	}else{
		$("input[name='all_"+id+"']").prop('checked',true);
	}
}
//批量撤销
function cancelWlogAll(id){
	checkLogin();
	var wlogIds = "";
	var wds="";
	if(id=="b_checknox"){
		$.each($("input[name='"+id+"']:checked"),function(){
			var rs = $(this).val().split(",");
			wlogIds +=  $(this).val()+"#";
		});
	}else{
		$.each($("input[name='"+id+"']:checked"),function(){
			var rs = $(this).val().split(",");
			wlogIds +=  rs[0]+"_";
		});
	}
	
	if(wlogIds == ""){
		layer.alert('请选择需要撤销的记录');
		return false;
	}
	var URL="";
	if(id=="b_checknox"){
		URL="/warehouse/warehousesout/editIsEnablePicking.do";
	}else if(id=="c_checknox"){
		URL="/warehouse/warehousesout/editIsEnableWlog.do";
	}else if(id=="d_checknox"){
		URL="/warehouse/warehousesout/editIsEnableWlog.do";
	}
	layer.confirm('确定撤销？', function(index){
		$.ajax({
			async : false,
			url : page_url + URL,
			data : {
				"wlogIds" : wlogIds
				
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0)
				{
					if(id=="b_checknox")
					{
						layer.alert("撤销成功");
						
						// 拣货记录--删除
						$.each($("input[name='"+id+"']:checked"),function()
						{
							var n = $(this).parents("tr").index();
							$("#jh_table").find("tr:eq("+(n+1)+")").remove();
							
							// 打印拣货单--删除
							$("#print_jh_id").find("tr:eq("+n+")").remove();
						});
						
						if($("#jh_table").find("tr").length==1)
						{
							// 拣货记录--展示
							var htm = "<tr><td colspan='9'>暂无记录</td></tr>";
							$("#jh_table").append(htm);
							$(".table-style4").remove();
							// 无拣货记录,隐藏 打印拣货单;拣货完成,扫码打包发货 按钮
							$('#dy_jhd_span').addClass('none');
							$("#sm_db_span").addClass('none');
							// 展示拣货按钮
							$("#jh_span").removeClass('none');
						}
						else
						{
							// 拣货记录--重新排序
							var inx = 0;
							$.each($("#jh_table tbody tr"),function()
							{
								inx++;
								$(this).find("td:eq(1)").text(inx);
							});
						}
					}
					else
					{						
						layer.alert("撤销成功", { icon : 1}, function(index){
											
								location.reload();
						});
					}
				}
				else
				{
					layer.alert(data.message, { icon : 2});
				}
					
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});  
	
}
//批量撤销(不刷新页面)
function cancelWlogAllNoF(id){
	var wlogIds = "";
	$.each($("input[name='"+id+"']:checked"),function(){
		var rs = $(this).val().split(",");
		wlogIds += rs[0]+"_";
	});
	if(wlogIds == ""){
		layer.alert('请选择需要撤销的记录');
		return false;
	}
	var URL="";
	if(id=="b_checknox"){
		URL="/warehouse/warehousesout/editIsEnablePicking.do";
	}else if(id=="c_checknox"){
		URL="/logistics/warehousein/editIsEnableWlog.do";
	}else if(id=="d_checknox"){
		URL="/logistics/warehousein/editIsEnableWlog.do";
	}
	layer.confirm('确定撤销？', function(index){
		$.ajax({
			async : false,
			url : page_url + URL,
			data : {
				"wlogIds" : wlogIds,
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					layer.alert("撤销成功");
					$.each($("input[name='"+id+"']:checked"),function(){
						var n= $(this).parents("tr").index();
						$("#jh_table").find("tr:eq("+(n+1)+")").remove();
					});
					if($("#jh_table").find("tr").length==1){
						var htm = "<tr><td colspan='7'>暂无记录</td></tr>";
						$("#jh_table").append(htm);
						$(".table-style4").remove();
					}
				}else{
					layer.alert(data.message, { icon : 2});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});  
	
}

//打印拣货单
function preview(printId) {
	checkLogin();
	$("#"+printId).removeClass('none');
	$("#"+printId).printArea();
	$("#"+printId).addClass('none');
	
};
//打印捡货单
function printPick(id){
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='"+id+"']:checked"),function(){
		var rs = $(this).val().split(",");
		wlogIds += rs[1]+"_";
	});
	if(wlogIds == ""){
		layer.alert('请选择需要打印的拣货记录');
		return false;
	}
	$("#salegoodsIds").val(wlogIds);
	$("#searchs").submit();
}
//打印出货单
function printOutOrder(id,type){
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='"+id+"']:checked"),function(){
		wlogIds += $(this).val()+"_";
	});
	if(wlogIds == ""){
		layer.alert('请选择需要打印的拣货记录');
		return false;
	}
	$("#wdlIds").val(wlogIds+"#"+type);
	if(type=="0"){
		$("type_f").val("0");
	}else if(type=="1"){//带效期
		$("type_f").val("1");
	}
	$("#searchc").submit();
}
//打印物流单
function printview(wlName,type,saleorderId){
	checkLogin();
	var url ="./printExpress.do?saleorderId="+saleorderId;
	var name="";
	var w=800;
	var h=600;
	if(wlName=="EMS"){
		url +="&logisticsId=1&type="+type;
		name="EMS快递单";
	}else if(wlName=="中通快递"){
		url +="&logisticsId=2&type="+type;
		name="中通快递单";
	}else if(wlName=="顺丰速运"){
		url +="&logisticsId=3&type="+type;
		name="顺丰快递单";
	}
	window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}

function canelAll(obj,name) {
	checkLogin();
	var status = 0;
	$.each($("input[name='"+name+"']"), function() {
		var datestatus = 0;
		if ($(this).is(":checked") == false) {
			status = 1;
		}
		// 日期选择
		var alt = $(this).attr("alt");
		$.each($("input[alt='" + alt + "']"), function() {
			if ($(this).is(":checked") == false) {
				datestatus = 1;
			}
		})
		if (datestatus == 1) {
			$("input[name='" + alt + "']").prop('checked', false);
		} else {
			$("input[name='" + alt + "']").prop("checked", true);
		}

	});
	if (status == 1) {
		$("input[name='all_"+name+"']").prop('checked', false);
	} else {
		$("input[name='all_"+name+"']").prop("checked", true);
	}

}
