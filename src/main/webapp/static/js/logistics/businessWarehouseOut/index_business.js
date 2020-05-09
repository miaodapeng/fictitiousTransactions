$(document).ready(function(e) {
	var ids = $("#Ids").val();
	var strs=ids.split("_"); //字符分割
	for (var i=0;i<strs.length ;i++ )
	{
	    if(strs[i]!=""){
	    	//订单下的产品
	    	$.ajax({
	    		url : page_url + '/warehouse/businessWarehouseOut/queryShGoodsList.do',
	    		data : {
	    			"afterSalesId":strs[i]
	    		},
	    		type : "POST",
	    		dataType : "json",
	    		success : function(data) {
	    			if(data.data!=null){
	    				var list = data.data;
	    				for(var i=0;i<list.length;i++){ 
	    					// 避免页面出现null
	    					var model = (list[i].model == 'null' || null == list[i].model) ? '' : list[i].model;
	    					if (list[i].isActionGoods == 1){
								var html ='<tr><td class="text-left">'+
									'<a class="addtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewgoods'+list[i].goodsId+'","link":"./goods/goods/viewbaseinfo.do?goodsId='+list[i].goodsId+'","title":"产品信息"}\'><font color="red">[活动]</font>'+list[i].goodsName+'</a>'+
									'</td>'+
									'<td>'+list[i].sku+'</td>'+
									'<td>'+list[i].brandName+'</td>'+
									'<td>'+model+'</td>'+
									'<td>'+list[i].materialCode+'</td>'+
									'<td>'+list[i].unitName+
									'</td>'+
									'<td>'+list[i].num+'</td>'+
									'<td>'+(parseInt(list[i].num) -parseInt(list[i].deliveryNum)) +'</td>'+
									'<td>'+list[i].goodsStock+'</td>';
								if(list[i].deliveryStatus==0){
									html+='<td class="warning-color1">未发货</td>';
								}else if(list[i].deliveryStatus==1){
									html+='<td class="warning-color1">部分发货</td>';
								}else if(list[i].deliveryStatus==2){
									html+='<td class="">全部发货</td>';
								}
							} else {
								var html ='<tr><td class="text-left">'+
									'<a class="addtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewgoods'+list[i].goodsId+'","link":"./goods/goods/viewbaseinfo.do?goodsId='+list[i].goodsId+'","title":"产品信息"}\'>'+list[i].goodsName+'</a>'+
									'</td>'+
									'<td>'+list[i].sku+'</td>'+
									'<td>'+list[i].brandName+'</td>'+
									'<td>'+model+'</td>'+
									'<td>'+list[i].materialCode+'</td>'+
									'<td>'+list[i].unitName+
									'</td>'+
									'<td>'+list[i].num+'</td>'+
									'<td>'+(parseInt(list[i].num) -parseInt(list[i].deliveryNum)) +'</td>'+
									'<td>'+list[i].goodsStock+'</td>';
								if(list[i].deliveryStatus==0){
									html+='<td class="warning-color1">未发货</td>';
								}else if(list[i].deliveryStatus==1){
									html+='<td class="warning-color1">部分发货</td>';
								}else if(list[i].deliveryStatus==2){
									html+='<td class="">全部发货</td>';
								}
							}
						   html+='<td></td></tr>';
	    				   $("#"+data.param+"_goods").append(html);
	    				}
	    			}else{
	    			}
	    		},
	    		error: function(data){
	    			if(data.status ==1001){
	    				layer.alert("当前操作无权限")
	    			}else
	    			layer.alert("操作失败")}
	    	});
	    }
	} 
})

function search() {
	checkLogin();
	$("#search").submit();
}