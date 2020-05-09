/**
 * 查询备货单号下商品关联的销售订单
 * @returns
 */
function queryOrderAndProductConnectOrderNo(saleorderId)
{
	$.ajax
	({
		type: "POST",
		url: page_url + "/order/productConnect/queryOrderAndProductConnectOrderNo.do",
		data: 
		{
			'searchOrderId': saleorderId
		},
		dataType: 'json',
		success: function(result)
		{
			// 成功
			if(result.code == 0)
			{
				
				var list = result.data;
				
				if(null == list || 'null' == list || undefined == list || list.length == 0)
				{
					return;
				}
				
				for(var i = 0; i < list.length; i++)
				{
					var mod = list[i];
					if(null == mod || undefined == mod || 'null' == mod)
						continue;
					
					var orderConnectNo = mod.orderConnectNo;
					var orderConnectId = mod.orderConnectId;
					var creatorName = mod.creatorName;
					var creatorId = mod.creatorId;
					var bugNum = mod.bugNum;
					var orderNum = mod.orderNum;
					var orderPrice = mod.orderPrice;
					var deliveryCycle = mod.deliveryCycle;
					var orderComments = mod.orderComments;
					var goodComments = mod.goodComments;
					var traderName = mod.traderName;
					var traderId = mod.traderId;
					var sku = mod.sku;
					var saleorderGoodsId = mod.saleorderGoodsId;
					
					if(null == orderConnectNo || 'null' == orderConnectNo || null == orderConnectId || 'null' == orderConnectId || null == sku || 'null' == sku)
						continue;
					
					if(null == creatorName || 'null' == creatorName)
					{
						creatorName = '';
					}
					if(null == bugNum || 'null' == bugNum)
					{
						bugNum = 0;
					}
					if(null == orderNum || 'null' == orderNum)
					{
						orderNum = 0;
					}
					if(null == orderPrice || 'null' == orderPrice)
					{
						orderPrice = 0;
					}
					if(null == deliveryCycle || 'null' == deliveryCycle)
					{
						deliveryCycle = '';
					}
					if(null == orderComments || 'null' == orderComments)
					{
						orderComments = '';
					}
					if(null == goodComments || 'null' == goodComments)
					{
						goodComments = '';
					}
					if(null == traderName || 'null' == traderName)
					{
						traderName = '';
					}
					
					
					var ht = '<tr>';
					ht += '<td><a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewsaleorder' + orderConnectId + '","link":"./order/saleorder/view.do?saleorderId=' +  orderConnectId + '","title":"订单信息"}\'>' + orderConnectNo + '</a></td>';
//					ht += '<td>' + orderConnectNo + '</td>';
					ht += '<td>' + creatorName + '</td>';
					ht += '<td>' + bugNum + '/' + orderNum + '</td>';
					ht += '<td>' + orderPrice + '</td>';
					ht += '<td>' + deliveryCycle + '</td>';
					ht += '<td>' + orderComments + '</td>';
					ht += '<td>' + goodComments + '</td>';
					ht += '<td>' + traderName + '</td>';
					ht += '</tr>';
					// 展示 
					$('#bh_product_tr_' + sku).removeClass('none');
					
					// 追加
					$('#bh_product_apend_order_' + sku).append(ht);
					
				}
				loadMoreAddTitle();
			}
			// 失败或者无数据
			else
			{
				
			}
		},
		error:function(data)
		{
			if(data.status ==1001)
			{
				layer.alert("当前操作无权限")
			}
		}
	});
}


function closeBhSaleorder(saleorderId){
	checkLogin();
	layer.confirm("备货订单被关闭后无法修改和跟进，是否确认？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./closeSaleorder.do",
				data: {'saleorderId':saleorderId,'status':3},
				dataType:'json',
				success: function(data){
					/*layer.alert(data.message,{
						closeBtn: 0,
						btn: ['确定'] //按钮
					}, function(){
						window.location.reload();
					});*/
					window.location.reload();
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

function applyValidSaleorder(saleorderId,taskId){
	checkLogin();
	var formToken = $("input[name='formToken']").val();
	layer.confirm("您是否确认申请审核该订单？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./editApplyValidBH.do",
				data: {'saleorderId':saleorderId,'taskId':taskId,'formToken':formToken},
				dataType:'json',
				success: function(data){
					if (data.code == 0) {
						window.location.reload();
					} else {
						layer.alert(data.message);
					}
					
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
