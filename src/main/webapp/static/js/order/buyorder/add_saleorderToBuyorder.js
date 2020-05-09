$(function(){
	$("#sub").click(function(){
		checkLogin();
		var flag1 = false;
		var searchNo = $("#searchNo").val()==undefined?"":$("#searchNo").val();
		if(searchNo==""){
			warnErrorTips("searchNo","searchNoError","采购单号不允许为空");//文本框ID和提示用语
			return false;
		}
		$.ajax({
			type: "POST",
			url: page_url+"/order/buyorder/getBuyorderByBuyorderNo.do",
			data: {'buyorderNo':searchNo},
			dataType:'json',
			async: false,
			success: function(data){
				
				if(data.code==0){
					$("#validStatus").val(data.data.validStatus);
					$("#status").val(data.data.status);
					$("#lockedStatus").val(data.data.lockedStatus);
					$("#verifyStatus").val(data.data.verifyStatus);
					$("#deliveryDirect").val(data.data.deliveryDirect);
					$("#saleorderNoType").val(data.data.saleorderNo);
					$("#buyorderId").val(data.data.buyorderId);
					$("input[name='beforeParams']").val(data.message);
				}else{
					flag1= true;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		if(flag1){
			warnErrorTips("searchNo","searchNoError","采购单号不存在，请检验");
			return false;
		}
		if($("#validStatus").val()==1 && $("#status").val()!=2 && $("#status").val()!=3){
			warnErrorTips("searchNo","searchNoError","不允许添加到已生效订单");
			return false;
		}
		if( $("#status").val()==2 ){
			warnErrorTips("searchNo","searchNoError","不允许添加到已完结订单");
			return false;
		}
		if( $("#status").val()==3 ){
			warnErrorTips("searchNo","searchNoError","不允许添加到已关闭订单");
			return false;
		}
		if($("#lockedStatus").val()==1 ){
			warnErrorTips("searchNo","searchNoError","不允许添加到已锁定订单");
			return false;
		}
		if($("#verifyStatus").val()==0 ){
			warnErrorTips("searchNo","searchNoError","不允许添加到审核中订单");
			return false;
		}
		var flag2 = false;
		var flag3 = false;
		var numReg = /^([1]?\d{1,10})$/;
		var sum = Number(0);
		
		$.each($("input[name='saleorderGoodsNum']"),function(i,n){
			var num = $(this).val();
			if(num ==''||num == undefined){
				$(this).val(0);
				flag2 = true;
				return false;
			}
			if(!numReg.test(num)){
				$(this).val(0);
				flag3 = true;
				return false;
			}
			sum += Number(num);
		});
		if(flag2){
			layer.alert("数量不允许为空");
			return false;
		}
		if(flag3){
			layer.alert("数量必须为大于等于1的正整数");
			return false;
		}
		if(sum==Number(0)){
			layer.alert("订单中采购商品数量必须大于等于1");
			return false;
		}
//		if(flag && $("#validStatus").val()==1){
//			layer.alert("订单已生效，禁止添加");
//			flag = false;
//		}
//		if(flag && $("#status").val()==2){
//			layer.alert("订单已完结，禁止添加");
//			flag = false;
//		}
//		if(flag && $("#status").val()==3){
//			layer.alert("订单已关闭，禁止添加");
//			flag = false;
//		}
		var saleorderNoType = $("#saleorderNoType").val();
		var saleorderNos = $("input[name='saleorderNo']").val();
		
		if((saleorderNoType.indexOf("BH")>=0 && (saleorderNos.indexOf("VS")>=0||saleorderNos.indexOf("DH")>=0))||
				(saleorderNos.indexOf("BH")>=0 && (saleorderNoType.indexOf("VS")>=0||saleorderNoType.indexOf("DH")>=0))){
			warnErrorTips("searchNo","searchNoError","不允许将备货单、销售订单的商品添加到同一采购单");
			return false;
		}
		var searchdeliveryDirect = $("#deliveryDirect").val();
		var deliveryDirects = $("input[name='deliveryDirect']").val();
		
		if( (searchdeliveryDirect==0 && deliveryDirects.indexOf("1")>=0) || (searchdeliveryDirect==1 && deliveryDirects.indexOf("0")>=0)){
			warnErrorTips("searchNo","searchNoError","不允许将直发与普发的商品添加到同一采购单");
			return false;
		}
		
		if(searchdeliveryDirect==1 && deliveryDirects.indexOf("1")>=0 && saleorderNos !=saleorderNoType){
			warnErrorTips("searchNo","searchNoError","不允许将非同一销售订单的直发商品添加到同一采购单");
			return false;
		}
		
		$("#myform").attr("action", page_url + "/order/buyorder/saveAddHavedBuyorder.do");
		$("#myform").submit();
		
	})
	
})


function addNum(obj,num,saleorderGoodsId,sku,saleorderNo){
	checkLogin();
	var srnum = $(obj).val();
	var goodsId = $(obj).attr("alt1");	
	var numReg = /^([1]?\d{1,10})$/;
	if(srnum==''){
		layer.alert("数量不允许为空");
		$(obj).val(1);
		$("span[alt='"+goodsId+"']").html(1);
		var zSum = Number(0);
		$.each($(".buyNum"),function(i,n){
			zSum += Number($(this).html());
		});
		$("#zSum").html(zSum);
		return false;
	}
	if(srnum !='' && !numReg.test(srnum)){
		layer.alert("数量必须为正整数且小于11位数字");
		$("span[alt='"+goodsId+"']").html(1);
		$(obj).val(1);
		var zSum = Number(0);
		$.each($(".buyNum"),function(i,n){
			zSum += Number($(this).html());
		});
		$("#zSum").html(zSum);
		return false;
	}
	if(Number(srnum)<1 || Number(srnum) > Number(num)){
		layer.alert("数量必须大于等于1且小于等于"+num);
		$("span[alt='"+goodsId+"']").html(1);
		$(obj).val(1);
		var zSum = Number(0);
		$.each($(".buyNum"),function(i,n){
			zSum += Number($(this).html());
		});
		$("#zSum").html(zSum);
		return false;
	}
	$(obj).siblings("input").val(sku+"|"+saleorderGoodsId+"|"+srnum+"|"+saleorderNo);
	var sum =Number(0);
	
	$.each($("input[alt1='"+goodsId+"']"),function(i,n){
		sum += Number($(this).val());
	});

	$("span[alt='"+goodsId+"']").html(sum);
	$("span[alt='"+goodsId+"']").siblings("input").val(sku+"|"+goodsId+"|"+sum);
	//计算总件数
	var zSum = Number(0);
	$.each($(".buyNum"),function(i,n){
		zSum += Number($(this).html());
	});
	$("#zSum").html(zSum);
	
}


