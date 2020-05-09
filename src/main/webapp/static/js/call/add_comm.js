$(function() {
	//更改订单类型
	$("#communicateType").change(function(){
		checkLogin();
		var communicateType = $("#communicateType").val();
		var traderId = $("input[name='traderId']").val();
		if(communicateType > 0 && traderId > 0){
			//异步查询订单
			$.ajax({
				type : "POST",
				url : page_url+"/system/call/getorderlist.do",
				data :{'communicateType':communicateType,"traderId" : traderId},
				dataType : 'json',
				success : function(data) {
					$option = "<option value='0'>请选择</option>";
					$.each(data.listData,function(i,n){
						if(communicateType == 244){
							$option += "<option value='"+data.listData[i]['bussinessChanceId']+"'>"+data.listData[i]['bussinessChanceNo']+"</option>";
						}
						if(communicateType == 245){
							$option += "<option value='"+data.listData[i]['quoteorderId']+"'>"+data.listData[i]['quoteorderNo']+"</option>";
						}
						if(communicateType == 246){
							$option += "<option value='"+data.listData[i]['saleorderId']+"'>"+data.listData[i]['saleorderNo']+"</option>";
						}
						if(communicateType == 247){
							$option += "<option value='"+data.listData[i]['buyorderId']+"'>"+data.listData[i]['buyorderNo']+"</option>";
						}
						if(communicateType == 248){
						}
					});
					$("select[name='relatedId'] option:gt(0)").remove();
					$("select[name='relatedId']").html($option);
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}else{
			$("select[name='relatedId'] option:gt(0)").remove();
		}
	});
	
	$("#submit").click(function(){
		$(".warning").remove();
		$("input").removeClass("errorbor");
		if($("#traderContactId").val() == 0){
			warnTips("traderContactId","联系人不允许为空");
			return false;
		}
		
		if($("#communicateType").val() != 0 && $("#relatedId").val() == 0){
			warnTips("relatedId","已存在订单类型，订单不允许为空");
			return false;
		}
		
		if($("#begin").val() == '' || $("#end").val() == ''){
			warnTips("endDiv"," 沟通时间不允许为空");
			return false;
		}
		
		
		
		if($("input[name='tagId']").length == 0 && $("input[name='tagName']").length == 0){
			warnTips("tag_show_ul"," 沟通内容不允许为空");
			return false;
		}
		
		if($("#nextContactContent").val().length > 256){
			warnTips("nextContactContent"," 下次沟通内容长度不允许超过256个字符");
			return false;
		}
		if($("#comments").val().length > 128){
			warnTips("comments"," 备注长度不允许超过128个字符");
			return false;
		}
		$.ajax({
			url:page_url+'/system/call/saveaddcommunicate.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code != 0){
					layer.alert(data.message);
				}else{
					window.parent.closeComm();
				}
				
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		return false;
	});
});