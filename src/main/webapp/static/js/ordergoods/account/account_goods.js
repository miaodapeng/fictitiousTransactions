/**
 * 删除产品
 * 
 * @param ordergoodsGoodsId
 * @returns
 */
function delAccountGoods(ordergoodsGoodsAccountId,goodsId,webAccountId,ordergoodsStoreId) {
	checkLogin();
	if (ordergoodsGoodsAccountId > 0 
			&& goodsId> 0
			&& webAccountId> 0
			&& ordergoodsStoreId> 0
			) {
		layer.confirm("确认删除产品", {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function() {
			$.ajax({
				type : "POST",
				url : "./deleteaccountgoods.do",
				data : {
					'ordergoodsGoodsAccountId' : ordergoodsGoodsAccountId,
					'goodsId' : goodsId,
					'webAccountId' : webAccountId,
					'ordergoodsStoreId' : ordergoodsStoreId,
				},
				dataType : 'json',
				success : function(data) {
					refreshNowPageList(data)
				},
				error : function(data) {
					if (data.status == 1001) {
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function() {
		});
	}
}
