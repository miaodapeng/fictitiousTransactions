function delPackage(id){
	checkLogin();
	layer.confirm("您是否确认操作？", 
		{ btn: ['确定','取消']}, 
		function(){
			$.ajax({
				async:false,
				url:'../opt/delGoodsPackageById.do',
				data:{"goodsPackageId":id},
				type:"POST",
				dataType : "json",
				success:function(data){
					if(data.code==0){
						layer.alert(data.message, 
								{ icon: 1 },
								function () {
									location.reload();
								}
						);
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}
	);
}

function delRecommend(id){
	checkLogin();
	layer.confirm("您是否确认操作？", 
			{ btn: ['确定','取消']}, 
			function(){
				$.ajax({
					async:false,
					url:'../opt/delGoodsRecommendById.do',
					data:{"goodsRecommendId":id},
					type:"POST",
					dataType : "json",
					success:function(data){
						if(data.code==0){
							layer.alert(data.message, 
									{ icon: 1 },
									function () {
										//$('#cancle').click();
										location.reload();
									}
							);
						}else{
							layer.alert(data.message);
						}
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				})
			}
		);
}

function delMainSupply(goodsId, traderSupplierId){
	checkLogin();
	layer.confirm("您是否确认操作？", 
		{ btn: ['确定','取消']}, 
		function(){
			$.ajax({
				async:false,
				url:'./delMainSupply.do',
				data:{"goodsId":goodsId, "traderSupplierId":traderSupplierId},
				type:"POST",
				dataType : "json",
				success:function(data){
					if(data.code==0){
						layer.alert(data.message, 
							{ icon: 1 },
							function () {
								location.reload();
							}
						);
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}
	);
}


function delGoodsChannelPrice(goodsId, goodsChannelPriceIdList){
	checkLogin();
	layer.confirm("您是否确认操作？", 
		{ btn: ['确定','取消']}, 
		function(){
			$.ajax({
				async:false,
				url:'./delGoodsChannelPrice.do',
				data:{'goodsId':goodsId, 'goodsChannelPriceIdList':goodsChannelPriceIdList},
				type:"POST",
				dataType : "json",
				success:function(data){
					if(data.code==0){
						layer.alert(data.message, 
							{ icon: 1 },
							function () {
								location.reload();
							}
						);
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}
	);
}
function showPriceList(goodsChannelPriceId,type,unitName){
	checkLogin();
	$.ajax({
		type: "POST",
		url: page_url+"/goods/goods/showPriceList.do",
		data: {'goodsChannelPriceId':goodsChannelPriceId,'priceType':type},
		dataType:'json',
		success: function(data){
			$("#showPrice").remove();
			var list = data.data;
			var typeName = "";
			var dimension = "";
			if(type==1){
				typeName = "成本价";
				dimension ="时间";
			}else if(type==2){
				typeName = "批量价";
				dimension = "数量";
			}
		    var div = '<div class="parts content1" style="" id="showPrice">'+
				        '<div class="title-container">'+
				            '<div class="table-title nobor">'+typeName+'</div>'+
				        '</div>'+
				        '<table class="table table-bordered table-striped table-condensed table-centered">'+
				            '<thead>'+
				                '<tr>'+
				                    '<th>'+dimension+'</th>'+
				                    '<th>价格</th>'+
				                '</tr>'+
				            '</thead>'+
				            '<tbody>';
				            for (var i = 0; i < list.length; i++) {
				            	// 批量
				            	if(type==2)
				            	{
					            	div += '<tr>';
		            	           if(list[i].maxNum == 0)
		            	           {
		            	        	   div+= '<td>>'+list[i].minNum+unitName+'</td>';
		            	           }
		            	           else 
	            	        	   {
		            	        	   if(list[i].maxNum > list[i].minNum)
	            	        		   {	            	        		   
		            	        		   div+= '<td>'+list[i].minNum+'-'+list[i].maxNum+unitName+'</td>';
	            	        		   }
		            	        	   else
		            	        	   {
		            	        		   div+= '<td>>'+list[i].minNum+unitName+'</td>';
		            	        	   }
	            	        	   }
		            	           
		            	           div+='<td>'+list[i].batchPrice.toFixed(2)+'</td>';
		            	           div+= '</tr>';
				            	}
				            	// 成本
				            	else if(type==1)
				            	{
				            		div += '<tr>';
					            	           div+= '<td>'+getDate(list[i].startTime)+'-'+getDate(list[i].endTime)+'</td>'+
					            	                 '<td>'+list[i].batchPrice.toFixed(2)+'</td>'+
							                '</tr>';
				            	}
				            };
				          div+=  '</tbody>'+
				        '</table>'+
				    '</div>';
		    $("#authorization").after(div);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}
function getDate(date){
	var time = new Date(date);
	var y = time.getFullYear();//年
	var m = time.getMonth() + 1;//月
	var d = time.getDate();//日
	var h = time.getHours();//时
	var mm = time.getMinutes();//分
	var s = time.getSeconds();//秒
	return y+"年"+m+"月"+d+"日 ";
}
function delGoodsChannelPriceAll(goodsChannelPriceId){
	checkLogin();
	layer.confirm("您是否确认操作？", 
		{ btn: ['确定','取消']}, 
		function(){
			$.ajax({
				async:false,
				url:'./delGoodsChannelPriceAll.do',
				data:{'goodsChannelPriceId':goodsChannelPriceId},
				type:"POST",
				dataType : "json",
				success:function(data){
					if(data.code==0){
						layer.alert(data.message, 
							{ icon: 1 },
							function () {
								location.reload();
							}
						);
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}
	);
}