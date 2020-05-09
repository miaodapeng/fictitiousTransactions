function afterBuyReturnInvoice(afterSalesInvoiceId){
	layer.confirm("您是否确认该操作？", {
		btn : [ '确定', '取消' ]
	}, function() {
		checkLogin();
		$.ajax({
			type : "POST",
			url : page_url + "/finance/after/editAfterInvoice.do",
			data : {
				'afterSalesInvoiceId' : afterSalesInvoiceId,
				'status' : 1
			},
			dataType : 'json',
			success : function(data) {
				if (data.code == 0) {
					layer.alert(data.message, {
						icon : 1
					}, function() {
						location.reload();
					});
				} else {
					layer.alert(data.message, {
						icon : 2
					});
				}
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});
}
//物料编码、注册证号（对应spu的“注册证号”）、管理类别、产品负责人（字段内容改为：该商品对应spu的归属产品经理&归属产品助理）、采购提醒、包装清单、服务条款、审核状态
$(function(){
	$(".J-skuInfo-tr").each(function(){
		var tr=$(this)
		$.getJSON("/goods/vgoods/static/skuTip.do?skuId="+$(this).attr("skuId"),function(result){
			console.log(result.data)
			var data=result.data
			var goodsInfo  ='物料编号：'+data.MATERIAL_CODE;
			goodsInfo +='<br>注册证号：'+data.REGISTRATION_NUMBER;
			goodsInfo +='<br>管理类别：'+data.MANAGE_CATEGORY_LEVEL;
			goodsInfo +='<br>产品负责人：'+data.PRODUCTMANAGER;
			goodsInfo +='<br>包装清单：'+data.PACKING_LIST;
			goodsInfo +='<br>质保年限：'+(data.QA_YEARS==undefined?'':data.QA_YEARS+'年') ;

			goodsInfo +='<br>库存：'+data.STOCKNUM;
			goodsInfo +='<br>可用库存：'+data.AVAILABLESTOCKNUM;
			goodsInfo +='<br>订单占用：'+data.OCCUPYNUM;
			goodsInfo +='<br>审核状态：'+data.CHECK_STATUS;
			tr.find(".JskuCode").html(data.SKU_NO);
			tr.find(".JmaterialCode").html(data.MATERIAL_CODE);
			tr.find(".JbrandName").html(data.BRAND_NAME);
			tr.find(".JskuName").html(data.SHOW_NAME);
			tr.find(".JskuModel").html(data.MODEL);
			tr.find(".JskuInfo").html(goodsInfo);
			tr.find(".JskuUnit").html(data.UNIT_NAME);
		})
	})
})