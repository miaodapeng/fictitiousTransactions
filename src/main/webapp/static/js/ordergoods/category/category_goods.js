/**
 * 删除产品
 * 
 * @param ordergoodsGoodsId
 * @returns
 */
function delCateGoods(rOrdergoodsGoodsJCategoryId,goodsId,ordergoodsGoodsId,ordergoodsStoreId) {
	checkLogin();
	var lock = false; //默认未锁定
	if (rOrdergoodsGoodsJCategoryId > 0 && goodsId >　0 && ordergoodsGoodsId > 0 && ordergoodsStoreId>0) {
		layer.confirm("确认删除产品", {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function() {
			if(!lock) {
                lock = true; // 锁定
				$.ajax({
					type : "POST",
					url : "./deletecategoods.do",
					data : {
						'rOrdergoodsGoodsJCategoryId' : rOrdergoodsGoodsJCategoryId,
						'goodsId':goodsId,
						'ordergoodsGoodsId':ordergoodsGoodsId,
						'ordergoodsStoreId' : ordergoodsStoreId
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
			}
		}, function() {
		});
	}
}