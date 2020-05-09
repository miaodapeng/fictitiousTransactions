$(function(){
})
function searchTyc(){
		checkLogin();
		var bType = 0;
		var traderName = $("input[type='radio']:checked").val();
		if(typeof(traderName)=="undefined"){
			/*traderName = $("#customerTraderName").val();
			bType = 1;*/
			layer.alert("请选择客户！");
			return false;
		}
		$.ajax({
			async : false,
			url : page_url + '/trader/customer/eyeCheckInfo.do',
			data : {
				"traderName" : traderName
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code==-1){
					layer.alert("接口错误！");
				}else if(data.code==2){
					layer.alert("没有查询到"+data.data+"的信息！");
				}else if(data.code==3){
					layer.alert("余额不足！");
				}else{
					var index = 0;
					var flag = -1;
					var url ="";
					/*if(bType==1){
						url = $(window.frameElement).attr('src');
						alert(url);
						$(window.frameElement).attr('src',url+"&traderName="+data.data);
					}else{
						url = $(window.parent.frameElement).attr('src');
						alert(url);
						$(window.parent.frameElement).attr('src',url);
					}*/
					url = $(window.parent.frameElement).attr('src');
					var param = "";
					if(url.indexOf("?")==-1) {
						param = "?traderName="+traderName;
					}
					$(window.parent.frameElement).attr('src',url+param);
					
					/*if(url.indexOf("traderId") > 0){
						index = url.indexOf("&");
						flag = 1;
					}else{
						index = url.indexOf("?");
					}
					alert(url);
					if(index>0){
						url  = url.substring(0,index);
						if(flag==1){
							
						}else{
							
						}
					}else{
						$(window.frameElement).attr('src',url+"?traderName="+data.data);
					}*/
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
}
function hideDiv(){
	$("#eyeDiv").hide();
}

function getAllCustomers(){
    checkLogin();
    location.href = page_url + '/order/miannian/getAllCustomers.do?' + $("#search").serialize();
}