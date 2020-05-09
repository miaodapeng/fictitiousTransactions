function search(){
	var colorTypeEnable = $("#colorTypeEnable").val();
	var arr = colorTypeEnable.split("-");
	$("#colorType").val(arr[0]);
	$("#isEnable").val(arr[1]);
	$("#search2").submit();
}
var startTime = "",endTime = "";
$(function(){
	$("#sInvoiceNo").focus();
	startTime = $("#de_startTime").val();
	endTime = $("#de_endTime").val();
})
function resetPage(){
	reset();
	$("#startTime").val(startTime);
	$("#endTime").val(endTime);

}
$(document).ready(function(){
	 //为列表页搜索框添加回车事件
    	$("#search2 :input").keydown(function (e) {
    	      if (e.which == 13) {
    	    	  var sno = $("input[name='invoiceNo']").val();
    	    	  if(sno!='' && sno.length>10){
    	    		sno = sno.replace(new RegExp("，","gm"),",");
  	            	var arr = sno.split(',');
  	            	$("input[name='invoiceNo']").val(arr[3]);
    	    	  } 
    	        $("#search2").submit();
    	        return false;
    	      }
    	})
	
	$("input[name='sInvoiceNo']").focus();
	document.onkeydown = function(e){
	    if(!e){
	        e = window.event;
	    }
	    if((e.keyCode || e.which) == 13){
	        var no = $("input[name='sInvoiceNo']").val();
	        if(no!='' && no.length>10){
	        	no = no.replace(new RegExp("，","gm"),",");
            	var arr = no.split(',');
            	$("input[name='sInvoiceNo']").val(arr[3]);
            	$("#logisticsNo").focus();
            	if(arr[3].length == 8){
            		$("#jsInvoiceNo").val(arr[3]);
            		
            		var st=$("#startTime").val();
            		var et=$("#endTime").val();
            		
            		$("#jsStartTime").val(st);
            		$("#jsEndTime").val(et);
            		$("#invoiceTrader").submit();
            	}
	        } 
	    }

	}
});


function optCheck(obj){
	var flag = true;
	if($(obj).is(":checked")){
		var str = $(obj).val();
		var str1 = $(obj).siblings('input').val();
		$("input[type='checkbox'][name='checkName']").each(function(){	
			if($(this).is(":checked") && str != $(this).val()){
				$(obj).prop("checked",false);
				layer.alert("当前客户与已选择客户不一致！", 
					{ icon: 2 },
					function (index) {
						$("#list_table").find("input[type='checkbox'][name='checkOptAll']").prop("checked",false);
						$(obj).prop("checked",false);
						layer.close(index);
						flag = false;
						return flag;
					}
				);
			}
		});
			
		//客户信息
		$("input[type='hidden'][name='traderDetalis']").each(function(){
			if($(this).prev().is(":checked") && str1 != $(this).val()){		
				$(obj).prop("checked",false);
				layer.alert("当前客户信息与已选择的客户信息不一致！", 
					{ icon: 2 },
					function (index) {
						$("#list_table").find("input[type='checkbox'][name='checkOptAll']").prop("checked",false);
						$(obj).prop("checked",false);
						layer.close(index);
						flag = false;
						return flag;
					}
				);
			}
			
		});

		if(!flag){
			return false;
		}
		if($(obj).attr("class").length > 0 && $(obj).attr("class") != 0){
			layer.alert("当前记录已寄送发票！", 
				{ icon: 2 },
				function (index) {
					$("#list_table").find("input[type='checkbox'][name='checkOptAll']").prop("checked",false);
					$(obj).prop("checked",false);
					layer.close(index);
					return false;
				}
			);
		}
		
		var n = 0;
		$("#list_table").find("input[type='checkbox'][name='checkName']").each(function(){
			if($(this).is(":checked")){
				n++;
			}else{
				return false;
			}
		});
		if($("#list_table").find("input[type='checkbox'][name='checkName']").length == n){
			$("#list_table").find("input[type='checkbox'][name='checkOptAll']").prop("checked",true);
		}
	}else{
		$("#list_table").find("input[type='checkbox'][name='checkOptAll']").prop("checked",false);
	}
}

function checkAllOpt(obj){
	if($(obj).is(":checked")){
		var str = "";
		$("#listInfo").find("input[type='checkbox'][name='checkName']").each(function(i){
			if($(this).attr("class").length > 0 && $(this).attr("class") != 0){
				$(this).prop("checked",false);
				layer.alert("第【"+(i+1)+"】记录已寄送发票，请验证！", 
					{ icon: 2 },
					function (index) {
						$(obj).prop("checked",false);
						layer.close(index);
					}
				);
				return false;
			}
			if(i == 0){
				str = $(this).val();
			}else{
				if(str != $(this).val()){
					layer.alert("记录中客户不一致，不允许全选！", 
						{ icon: 2 },
						function (index) {
							$(obj).prop("checked",false);
							$("input[type='checkbox'][name='checkName']").each(function(j){
								$(this).prop("checked",false);
							});
							layer.close(index);
						}
					);
					return false;
				}else{
					str = $(this).val();
				}
			}
			$(this).prop("checked",true);
		});
		
		$("#listInfo").find("input[type='hidden'][name='traderDetalis']").each(function(i){
			if(i == 0){
				str = $(this).val();
			}else{
				if(str != $(this).val()){
					layer.alert("第【"+(i+1)+"】条记录中客户信息不一致，不允许全选！", 
						{ icon: 2 },
						function (index) {
							$(obj).prop("checked",false);
							$("input[type='checkbox'][name='checkName']").each(function(j){
								if(j != 0){
									$(this).prop("checked",false);
								}
							});
							layer.close(index);
						}
					);
					return false;
				}else{
					str = $(this).val();
				}
			}
		});
		
	}else{
		$("input[type='checkbox'][name='checkName']").each(function(j){
			$(this).prop("checked",false);
		});
	}
	return false;
}

function sendInvoice(){
	checkLogin();
	if($("#listInfo").find("input[type='checkbox'][name='checkName']:checked").length == 0){
		layer.alert("请选择需要寄送发票的记录！", 
			{ icon: 2 },
			function (index) {
				layer.close(index);
			}
		);
		return false;
	}
	var invoiceIdArr = [];var flag = true;
	$("#listInfo").find("input[type='checkbox'][name='checkName']:checked").each(function(i){
		if($(this).attr("class").length > 0 && $(this).attr("class") != 0){
			flag = false;
			layer.alert("第【"+(i+1)+"】记录已寄送发票，请验证！", 
				{ icon: 2 },
				function (index) {
					layer.close(index);
					return flag;
				}
			);
		}
		invoiceIdArr.push($(this).attr("id"));
	});
	if(!flag){
		return false;
	}
	$("#relatedIdArr").attr('layerParams','{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceIdArr='+invoiceIdArr+'"}');
	$("#relatedIdArr").click();
	
	/*$.ajax({
		type: "POST",
		url: "./batchSaveExpress.do",
		data: {"invoiceIdArr":invoiceIdArr},
		dataType:'json',
		success: function(data){
			refreshPageList(data);//刷新父页面列表数据
		}
	});*/
}
function printview(){
	checkLogin();
	var companyId = $("#companyId").val();
	var flag = true;
	if($("#listInfo").find("input[type='checkbox'][name='checkName']:checked").length == 0 && $("#sInvoiceNo").val().trim()==""){
		layer.alert("请选择或填写需要寄送的发票！", 
			{ icon: 2 },
			function (index) {
				layer.close(index);
				return false;
			}
		);
		return false;
	}
	var invoiceIdArr = [];var invoiceNo = "";var printInvoiceNo = "";var type = "";var invoicNos=[];var types=[];
	if($("#listInfo").find("input[type='checkbox'][name='checkName']:checked").length > 0){
		$("#listInfo").find("input[type='checkbox'][name='checkName']:checked").each(function(i){
			if($(this).attr("class").length > 0 && $(this).attr("class") != 0){
				layer.alert("第【"+(i+1)+"】记录已寄送发票，请验证！", 
						{ icon: 2 },
						function (index) {
							layer.close(index);
							flag = false;
							return flag;
						}
				);
			}
			invoiceIdArr.push($(this).attr("id"));
			invoicNos.push($(this).attr("alt"));
			types.push($(this).attr("placeholder"));
			printInvoiceNo = $(this).attr("alt");
			type = $(this).attr("placeholder");
		});
		if(!flag){
			return false;
		}
	}else if($("#listInfo").find("input[type='checkbox'][name='checkName']:checked").length == 0 && $("#sInvoiceNo").val().trim() != ""){
		invoiceNo = $("#sInvoiceNo").val().trim();
		printInvoiceNo = invoiceNo;
	}
	if($("#logisticsName").val().trim().length == 0){
		layer.alert("快递公司不允许为空！", 
			{ icon: 2 },
			function (index) {
				layer.close(index);
				$("#logisticsName").focus();
				return false;
			}
		);
		return false;
	}
	if(companyId=="1"){
		var lName = $("#logisticsName").val();
		if(lName!="顺丰速运" && lName!="中通快递"){
			if($("#logisticsNo").val().trim().length == 0){
				layer.alert("快递单号不允许为空！", 
					{ icon: 2 },
					function (index) {
						layer.close(index);
						$("#logisticsNo").focus();
						return false;
					}
				);
				return false;
			}
		}
	}else{
		if($("#logisticsNo").val().trim().length == 0){
			layer.alert("快递单号不允许为空！", 
				{ icon: 2 },
				function (index) {
					layer.close(index);
					$("#logisticsNo").focus();
					return false;
				}
			);
			return false;
		}
	}
	
	$.ajax({
		type: "POST",
		url: "./saveExpress.do",
		data: {"invoiceNo":invoiceNo,"invoiceIdArr":JSON.stringify(invoiceIdArr),"logisticsNo":$("#logisticsNo").val().trim(),"logisticsId":$('#logisticsName>option:selected').attr("id")},
		dataType:'json',
		success: function(data){
			var msg = "";
			var flag =0;
			if(data.code == 0){
//				var invoiceNo = $("#sInvoiceNo").val();
				var logisticsName = $("#logisticsName").val();
				var url = page_url+ "/finance/invoice/printExpress.do?type="+type+"&invoiceNo="+printInvoiceNo+"&expressId="+data.data.expressId;
				var name="";
				var w=800;
				var h=600;
				if(logisticsName=="EMS"){
					url +="&logisticsId=4";
					name="EMS快递单";
				}else if(logisticsName=="中通快递"){
					url +="&logisticsId=2";
					name="中通快递单";
				}else if(logisticsName=="顺丰速运"){
					url +="&logisticsId=1";
					name="顺丰快递单";
				}else if(logisticsName=="中通快运"){
					url +="&logisticsId=18";
					name="中通快运单";
				}else if(logisticsName=="德邦快递"){
					url +="&logisticsId=7";
					name="德邦快递单";
				}
				if(companyId=="1"){
					if(logisticsName!="顺丰速运" && logisticsName!="中通快递"){
						refreshNowPageList(data);
						window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
					}else if(logisticsName=="顺丰速运" || logisticsName=="中通快递"){
						$.ajax({
							type: "POST",
							url: "./printExpressSf.do",
							data: {
								"invoiceNo":printInvoiceNo,
								"invoiceNos":JSON.stringify(invoicNos),
								"type":type,
								"types":JSON.stringify(types),
								"expressId":data.data.expressId,
								"logisticsName":logisticsName,
								"invoiceIdArr":JSON.stringify(invoiceIdArr),
								"logisticsId":$('#logisticsName>option:selected').attr("id")},
							dataType:'json',
							success: function(data){
								if(data.code!=0){
									layer.alert(data.message+"无法下单打印！");
									flag = 1;
								}else{
                                    if(data.data == null || data.data==''){
                                        layer.alert("快递单号为空，请检查");
                                        return false;
									}
                                    refreshNowPageList(data);//刷新列表数据
								}
							}
						})
					}
				}else{
					refreshNowPageList(data);
					window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
				}
				
			}else{
				layer.alert(data.message,  { icon: 2 },
					function (index) {
						layer.close(index);
						return false;
					}
				);
			}
			
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}

function openprintview(logisticsName,invoiceNo,logisticsNo,expressId,type){
	var companyId = $("#companyId").val();
	var url = page_url+ "/finance/invoice/printExpress.do?type="+type+"&invoiceNo="+invoiceNo;
	var name="";
	var w=800;
	var h=600;
	if(logisticsName=="EMS"){
		url +="&logisticsId=4";
		name="EMS快递单";
	}else if(logisticsName=="中通快递"){
		url +="&logisticsId=2";
		name="中通快递单";
	}else if(logisticsName=="顺丰速运"){
		url +="&logisticsId=1";
		name="顺丰快递单";
	}else if(logisticsName=="中通快运"){
		url +="&logisticsId=18";
		name="中通快运单";
	}else if(logisticsName=="德邦快递"){
		url +="&logisticsId=7";
		name="德邦快递单";
	}
	if(companyId=="1"){
		if(logisticsName!="顺丰速运" && logisticsName!="中通快递"){
			url += "&expressId="+expressId
			window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			$.ajax({
				type: "POST",
				url: "./printExpressSf.do",
				data: {
					"invoiceNo":invoiceNo,
					"type":type,
					"logisticsName":logisticsName,
					"logisticsNo":logisticsNo,
					"expressId":expressId},
				dataType:'json',
				success: function(data){
					if(data.code!=0){
						layer.alert(data.message+"无法下单打印！");
						flag = 1;
					}else{
						//refreshNowPageList(data);//刷新列表数据
					}
				}
			})
		}
	}else{
		
		url += "&expressId="+expressId
		window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	//window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}
function exportOpenInvoiceList(){
	checkLogin();
	location.href = page_url + '/report/finance/exportOpenInvoiceList.do?' + $("#search2").serialize();
}

function exportOpenInvoiceDetail(){
	checkLogin();
	location.href = page_url + '/report/finance/exportOpenInvoiceDetailList.do?' + $("#search2").serialize();
}

function sendOpenInvoicelist(param){
	
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			layer.close(index);
			checkLogin();
			var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
			$("body").prepend(div); //jq获取上一页框架的父级框架；
			var startDate = $(param).parents("div").siblings("ul").find("input[name='startTime']").val();
			var endDate = $(param).parents("div").siblings("ul").find("input[name='endTime']").val();
			$.ajax({
				type: "POST",
				url: page_url + "/finance/invoice/sendopeninvoicelist.do",
				dataType:'json',
				data : {
					'startDate' : startDate,
					'endDate' : endDate
				},
				success: function(data){
					$(".tip-loadingNewData").remove();
					if(data && data.code == 0){
						if(data.page && data.page.totalRecord){
							layer.alert('推送成功：'+ data.page.totalRecord + '条！');
						}else{
							layer.alert('推送成功：'+ 0 + '条！');
						}
					}
					if(data && data.code == -1){//如果存在traderId为null的
						layer.alert(data.message);
					}
				},
				error:function(data){
					$(".tip-loadingNewData").remove();
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			
			layer.close(index);
		}, function(){
			
		});
}

function batchDownEInvoice(){
	checkLogin();
	var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
	$("body").prepend(div); //jq获取上一页框架的父级框架；
	$.ajax({
		type: "POST",
		url: page_url + "/finance/invoice/batchDownEInvoice.do",
		dataType:'json',
		success: function(data){
			$(".tip-loadingNewData").remove();
			refreshNowPageList(data);//刷新列表数据
		},
		error:function(data){
			$(".tip-loadingNewData").remove();
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}