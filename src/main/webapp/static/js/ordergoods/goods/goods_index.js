/**
 * 删除产品
 * 
 * @param ordergoodsGoodsId
 * @returns
 */
function delGoods(ordergoodsGoodsId,goodsId,ordergoodsStoreId) {
	checkLogin();
	var lock = false; //默认未锁定
	if (ordergoodsGoodsId > 0 && goodsId> 0 && ordergoodsStoreId) {
		layer.confirm("确认删除产品", {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function() {
			if(!lock) {
                lock = true; // 锁定
				$.ajax({
					type : "POST",
					url : "./deletegoods.do",
					data : {
						'ordergoodsGoodsId' : ordergoodsGoodsId,
						'goodsId' : goodsId,
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

//更改排序
function changeSort(obj,rOrdergoodsGoodsJCategoryId){
	checkLogin();
	var sort = $(obj).val();
	if(rOrdergoodsGoodsJCategoryId > 0 && sort > 0){
		layer.confirm("确认修改排序", {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function() {
			$.ajax({
				type : "POST",
				url : "./changsort.do",
				data : {
					'rOrdergoodsGoodsJCategoryId' : rOrdergoodsGoodsJCategoryId,
					'sort' : sort,
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