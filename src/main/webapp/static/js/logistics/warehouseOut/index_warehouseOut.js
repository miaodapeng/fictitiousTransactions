
$(document).ready(function(e) {
	var ids = $("#Ids").val();
	var strs=ids.split("_"); //字符分割
	var num =-1;
	var n=-1;
	for (var i=0;i<strs.length ;i++ )
	{
	    if(strs[i]!=""){
	    	//订单状态
	    	$.ajax({
	    		url : page_url + '/warehouse/warehousesout/checkState.do',
	    		data : {
	    			"saleorderId":strs[i]
	    		},
	    		type : "POST",
	    		dataType : "json",
	    		success : function(data) {
	    			if(data.data!=null){
		    			var da = data.data;
		    			if(da!=null){
		    				$("#"+data.param+"_flag"+" a").after('<font color="red">[可出库]</font>');
		    			}
	    			}else{
	    			}
	    		},
	    		error: function(data){
	    			if(data.status ==1001){
	    				layer.alert("当前操作无权限")
	    			}
	    			//layer.alert("操作失败")
	    			}
	    	});
	    	//订单下的产品
	    	$.ajax({
	    		url : page_url + '/warehouse/warehousesout/queryGoodsList.do',
	    		data : {
	    			"saleorderId":strs[i]
	    		},
	    		type : "POST",
	    		dataType : "json",
	    		success : function(data) {
	    			n++;
	    			if(data.data!=null){
	    				var list = data.data;
	    				for(var i=0;i<list.length;i++){
	    					// 避免页面出现null
	    					var model = (list[i].model == 'null' || null == list[i].model) ? '' : list[i].model;
	    					var storageAddress = (null == list[i].storageAddress || 'null' == list[i].storageAddress) ? '' : list[i].storageAddress;
	    					var materialCode = (null == list[i].materialCode || 'null' == list[i].materialCode) ? '' : list[i].materialCode;
							if (list[i].isActionGoods > 0){
								var html ='<tr><td class="text-left">'+'<font color="red">[活动]</font>' +
									'<a class="addtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewgoods'+list[i].goodsId+'","link":"./goods/goods/viewbaseinfo.do?goodsId='+list[i].goodsId+'","title":"产品信息"}\'>'+list[i].goodsName+'</a>'+
									'</td>'+
									'<td>'+list[i].sku+'</td>'+
									'<td>'+list[i].brandName+'</td>'+
									'<td>'+model+'</td>'+
									'<td>'+materialCode+'</td>'+
									'<td>'+list[i].unitName+
									'</td>'+
									'<td>'+list[i].num+'</td>'+
									'<td>'+(parseInt(list[i].num) -parseInt(list[i].deliveryNum)) +'</td>'+
                                    '<td>'+list[i].goods.stockNum+'</td>';
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
									'<td>'+materialCode+'</td>'+
									'<td>'+list[i].unitName+
									'</td>'+
									'<td>'+list[i].num+'</td>'+
									'<td>'+(parseInt(list[i].num) -parseInt(list[i].deliveryNum))+'</td>' +
                                    '<td>'+list[i].goods.stockNum+'</td>';
								if(list[i].deliveryStatus==0){
									html+='<td class="warning-color1">未发货</td>';
								}else if(list[i].deliveryStatus==1){
									html+='<td class="warning-color1">部分发货</td>';
								}else if(list[i].deliveryStatus==2){
									html+='<td class="">全部发货</td>';
								}
							}

						   html+='<td>' + storageAddress + '</td></tr>';
	    				   $("#"+data.param+"_goods").append(html);
	    				}
	    				$(".addtitle").bind("click",function(){
							var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
					        var newPageId;
					        var tabTitle = $(this).attr('tabTitle');
					        if (typeof(tabTitle) == 'undefined') {
					            alert('参数错误');
					        } else {
					            tabTitle = $.parseJSON(tabTitle);
					        }
					        var id = tabTitle.num;
					        var name = tabTitle.title;
					        var uri = tabTitle.link;
					        var closable = 1;
					        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
					        self.parent.closableTab.addTab(item);
					        self.parent.closableTab.resizeMove();
					        $(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
						});
	    				
	    			}else{
	    			}
	    		},
	    		error: function(data){
	    			if(data.status ==1001){
	    				layer.alert("当前操作无权限")
	    			}
	    			//layer.alert("操作失败")}
	    		}
	    			
	    	});
	    	
	    }
	} 
	
})
