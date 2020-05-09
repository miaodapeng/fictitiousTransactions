$(function(){

	$("#searchSpan").click(function(){
		checkLogin();
		$("#search").submit();
	})
	
	
});

/*
 * 置顶
 */
function stick(userId,status){
	checkLogin();
	if(userId > 0){
		var msg = "";
		if(status == 1){
			msg = "是否置顶该供应商？";
		}
		if(status == 0){
			msg = "是否取消置顶？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/supplier/isStick.do",
				data: {'id':userId,'isTop':status},
				dataType:'json',
				success: function(data){
					var str=page_url+"/trader/supplier/index.do";
					self.location.href=str;
					//refreshPageList(data)
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
 * 禁用
 */
function setDisabled(userId,status){
	checkLogin();
	if(userId > 0&&status == 1){
		layer.confirm("是否启用该供应商？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/supplier/isDisabledSupplier.do",
				data: {'id':userId,'isDisabled':status},
				dataType:'json',
				success: function(data){
					var str=page_url+"/trader/supplier/index.do";
					self.location.href=str;
					//refreshPageList(data);
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
	checkLogin();
	location.href = page_url + '/report/buy/exportsupplierlist.do?' + $("#search").serialize();
}