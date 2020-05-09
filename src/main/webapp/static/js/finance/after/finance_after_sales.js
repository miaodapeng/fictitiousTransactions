function afterSalePayApplyAudit(payApplyId,relatedId,status){
	checkLogin();
	layer.confirm("您是否确认审核通过该付款申请？", {
		btn : [ '确定', '取消' ]// 按钮
	}, function() {
		$.ajax({
			async : false,
			url : './editAfterPayApplyAudit.do',
			data : {"payApplyId":payApplyId,"relatedId":relatedId,"status":status},
			type : "POST",
			dataType : "json",
			success : function(data) {
				layer.alert(data.message, {
					icon : (data.code == 0 ? 1 : 2)
				}, function() {
					/*if (parent.layer != undefined) {
						parent.layer.close(index);
					}*/
					location.reload();
				});
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
//		$("#auditAccountPeriodForm").submit();
	});
}

function confirmRefundAmount(afterSalesId,subjectType,type,status,atferSalesStatus){
	checkLogin();
	layer.confirm("您是否确认退款？", {
		btn : [ '确定', '取消' ]// 按钮
	}, function() {
		$.ajax({
			async : false,
			url : './afterThRefundAmountBalance.do',
			data : {"afterSalesId":afterSalesId,"subjectType":subjectType,"type":type,"status":status,"atferSalesStatus":atferSalesStatus},
			type : "POST",
			dataType : "json",
			success : function(data) {
				layer.alert(data.message, {
					icon : (data.code == 0 ? 1 : 2)
				}, function(index) {
					/*if (parent.layer != undefined) {
						parent.layer.close(index);
					}*/
					if(data.code == 0){
						location.reload();
					}else{
						layer.close(index);
					}
				});
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	});
}
var flag = true;//js防止重复提交
function cancelEInvoicePush(invoiceId,afterSalesId){
	checkLogin();
	layer.confirm('您是否确认退票？', {
		btn : [ '确定', '取消' ] ,// 按钮
		cancel : function(index, layero) { // 取消操作，点击右上角的X
			flag = true;
		}
	}, function() { // 是
		if(flag){
			flag = false;
			$.ajax({
				async : false,
				url : page_url + '/finance/invoice/cancelEInvoicePush.do',
				data : {"invoiceId":invoiceId,"afterSalesId":afterSalesId},
				type : "POST",
				dataType : "json",
				success : function(data) {
					layer.alert(data.message, {
						icon : (data.code == 0 ? 1 : 2)
					}, function(index) {
						if(data.code == 0){
							location.reload();
						}else{
							flag = true;
							layer.close(index);
						}
					});
				},error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}
	}, function() { //否
		flag = true;
	});
	
	
	/*checkLogin();
	layer.confirm("您是否确认退票？", {
		btn : [ '确定', '取消' ]// 按钮
	}, function() {
		$.ajax({
			async : false,
			url : page_url + '/finance/invoice/cancelEInvoicePush.do',
			data : {"invoiceId":invoiceId,"afterSalesId":afterSalesId},
			type : "POST",
			dataType : "json",
			success : function(data) {
				layer.alert(data.message, {
					icon : (data.code == 0 ? 1 : 2)
				}, function(index) {
					if(data.code == 0){
						location.reload();
					}else{
						layer.close(index);
					}
				});
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	});*/

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
			goodsInfo +='<br>质保年限：'+(data.QA_YEARS==''?'':data.QA_YEARS+'年') ;

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
