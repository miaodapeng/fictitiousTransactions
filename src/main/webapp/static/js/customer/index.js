$(function(){
	$("#customerType").change(function(){
		checkLogin();
		var customerType=$("#customerType").val();
		var cus=$("#cusProperty").val();
		
		cus= 0;
		if(customerType!=0&&customerType==1){
			$("#customerPropertys").addClass("none");
			$("#customerProperty").removeClass("none");
			$("#customerProperty").empty();
			var option = "<option value='0'>全部</option>";
			if(cus==3){
				option+="<option value='3' selected='selected'>分销</option>"+
				 		"<option value='4' >终端</option>";
			}else if(cus==4){
				option+="<option value='3'>分销</option>"+
		 		"<option value='4' selected='selected'>终端</option>";
			}else{
				option+="<option value='3'>分销</option>"+
		 		"<option value='4'>终端</option>";
			}
			$("#customerProperty").append(option);
		}else if(customerType!=0&&customerType==2){
			$("#customerPropertys").addClass("none");
			$("#customerProperty").removeClass("none");
			$("#customerProperty").empty();
			var option = "<option value='0'>全部</option>";
			if(cus==5){
				option+="<option value='3' selected='selected'>分销</option>"+
				 		"<option value='4' >终端</option>";
			}else if(cus==6){
				option+="<option value='5'>分销</option>"+
		 		"<option value='6' selected='selected'>终端</option>";
			}else{
				option+="<option value='5'>分销</option>"+
		 		"<option value='6'>终端</option>";
			}
			$("#customerProperty").append(option);
		}else{
			$("#customerPropertys").removeClass("none");
			$("#customerProperty").addClass("none");
		}
	})
	
	
});

/*
 * 置顶
 */
function stick(traderCustomerId,status){
	checkLogin();
	if(traderCustomerId > 0){
		var msg = "";
		if(status == 1){
			msg = "是否置顶该客户？";
		}
		if(status == 0){
			msg = "是否取消置顶？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			
			$.ajax({
				type: "POST",
				url: page_url+"/trader/customer/isStick.do",
				data: {'traderCustomerId':traderCustomerId,'isTop':status},
				dataType:'json',
				success: function(data)
				{
//					var pageType = $("#pageType").val();
//					var str = page_url+"/trader/customer/index.do";
//					self.location.href=str;
					$("#search").submit();
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
	
}

/*
 * 启用客户
 */
function setDisabled(traderCustomerId,status){
	checkLogin();
	if(traderCustomerId > 0&&status==1){
		layer.confirm("是否启用该客户？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/customer/isDisabledCustomer.do",
				data: {'traderCustomerId':traderCustomerId,'isEnable':status},
				dataType:'json',
				success: function(data){
//					var str=page_url+"/trader/customer/index.do";
//					self.location.href=str;
					//refreshPageList(data)
					$("#search").submit();
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
	
}

function exportList(){
	location.href = page_url + '/report/sale/exportcustomerlist.do?' + $("#search").serialize();
}