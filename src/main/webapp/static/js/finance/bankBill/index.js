function exportBankBillList(param){
	checkLogin();
	location.href = page_url + '/report/finance/exportBankBillList.do?' + $.param({bankTag : param}) + '&' + $("#search").serialize();
}

function searchReset() {
	$("form").find("input[type='text']").val('');
	$.each($("form select"),function(i,n){
		$(this).children("option:first").prop("selected",true);
	});
	//交易时间重置默认
	var nowDate = $("form").find("input[name='nowDate']").val();
	$("form").find("input[name='beginTime']").val(nowDate);
	$("form").find("input[name='endTime']").val(nowDate);
}

//银行流水
function sendBankBillList(param){
	
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			layer.close(index);
			checkLogin();
			var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
			$("body").prepend(div); //jq获取上一页框架的父级框架；
			var searchBegintime = $(param).parents("div").siblings("ul").find("input[name='beginTime']").val();
			var searchEndtime = $(param).parents("div").siblings("ul").find("input[name='endTime']").val();
			var bankBill = {
					'searchBegintime' : searchBegintime,
					'searchEndtime' : searchEndtime
			}
			$.ajax({
				type: "POST",
				url: page_url + "/finance/bankbill/sendbankbilllist.do",
				dataType:'json',
				data : bankBill,
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

//南京银行
function sendPayBillToKindlee(param){
	
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			layer.close(index);
			checkLogin();
			var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
			$("body").prepend(div); //jq获取上一页框架的父级框架；
			
			var beginTime = $(param).parents("div").siblings("ul").find("input[name='beginTime']").val();
			var endTime = $(param).parents("div").siblings("ul").find("input[name='endTime']").val();
			
			$.ajax({
				type: "POST",
				url: page_url + "/finance/bankbill/sendpaybilltokindlee.do",
				dataType:'json',
				data : {
					'searchBegintime' : beginTime,
					'searchEndtime' : endTime
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
						layer.alert("当前操作无权限");
					}
				}
			});
		layer.close(index);
	}, function(){
		
	});
}


//中国银行
function sendChPayBillToKindlee(param){
	
	index = layer.confirm("您是否确认该操作？", {
		btn: ['确定','取消'] //按钮
	}, function(){
		layer.close(index);
		checkLogin();
		var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
		$("body").prepend(div); //jq获取上一页框架的父级框架；
		
		var beginTime = $(param).parents("div").siblings("ul").find("input[name='beginTime']").val();
		var endTime = $(param).parents("div").siblings("ul").find("input[name='endTime']").val();
		
		$.ajax({
			type: "POST",
			url: page_url + "/finance/bankbill/sendchpaybilltokindlee.do",
			dataType:'json',
			data : {
				'searchBegintime' : beginTime,
				'searchEndtime' : endTime
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
					layer.alert("当前操作无权限");
				}
			}
		});
		layer.close(index);
	}, function(){
		
	});
}





