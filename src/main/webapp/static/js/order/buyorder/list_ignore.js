

$(function(){
	$("#buyorder").click(function(){
		checkLogin();
		var isDirects ="";
		var saleorderType="";
		var saleorderGoodsIds="";
		var orderType = "";
		var goodsIds = "";
		var saleorderId = "";
		var saleorderNos ="";
		var allnos="";
		$.each($("input[name='oneSelect']"),function(i,n){
			if($(this).prop("checked")){	
				saleorderId = $(this).siblings("input[name='saleorderId']").val();
				saleorderGoodsIds += $(this).siblings("input[name='saleorderGoodId']").val()+",";
				if(goodsIds.indexOf($(this).siblings("input[name='goodsId']").val())<0){
					goodsIds += $(this).siblings("input[name='goodsId']").val()+",";
				}
				var oneno = $(this).siblings("input[name='saleorderNo']").val().substring(0,2)+",";			
				var direct = $(this).siblings("input[name='deliveryDirect']").val()+",";
				var saleorderNo = $(this).siblings("input[name='saleorderNo']").val()+",";
				
				allnos +=oneno;
				isDirects +=direct;
				saleorderNos +=saleorderNo;
			}
		});
		if(goodsIds==""||saleorderGoodsIds==""){
			layer.alert("未选择任何信息");
			return false;
		}
		if(isDirects!=''&&isDirects.indexOf("0")>=0&&isDirects.indexOf("1")>=0){
			layer.alert("直发与普发不允许同时被选择");
			return false;
		}
		if(isDirects!=''&&isDirects.indexOf("0")<0&&isDirects.indexOf("1")>=0){
			if(saleorderNos!=''){
				var flag = false;
				var sn = saleorderNos.split(",");
				for(var i =1; i<sn.length; i++){
					if(sn[0] != sn[i] && sn[i] != ''){
						flag = true;
					}
				}
			}
			if(flag && saleorderNos.split(",").length >2){
				layer.alert("直发仅允许选择同订单直发产品");
				return false;
			}
		}
		if(allnos!=''&&allnos.indexOf("BH")>=0&&(allnos.indexOf("VS")>=0||allnos.indexOf("DH")>=0)){
			layer.alert("备货单、销售订单不允许同时被选择");
			return false;
		}
		
		if(allnos.indexOf("BH")>=0){
			orderType = 1;
		}else{
			orderType = 0;
		}
		var dir ='';
		if(isDirects.indexOf("1")>=0){
			dir=1;
		}else{
			dir=0
		}
		index = layer.confirm("您是否确认重新采购？", {
			  btn: ['是','否'] //按钮
			}, function(){
				var timestamp = Date.parse(new Date());
				var	url = page_url+"/order/buyorder/saveAddBuyorder.do?orderType="+orderType+"&saleorderGoodsIds="
							+escape(saleorderGoodsIds)+"&deliveryDirect="+dir+"&goodsIds="+goodsIds+"&saleorderId="+saleorderId+"&isIgnore="+1;
				$("#addpf").attr('tabTitle','{"num":"view'+timestamp+'","title":"新增采购订单","link":"'+url+'"}');
				$("#addpf").click();
				//self.location.reload();
				//pagesContrlpages(false,false,true);
		layer.close(index);
			}, function(){
			});
	})
	
	$("#addbuy").click(function(){
		checkLogin();
		var isDirects ="";
		var saleorderType="";
		var saleorderGoodsIds="";
		var orderType = "";
		var goodsIds = "";
		var saleorderId = "";
		var saleorderNos ="";
		var allnos="";
		$.each($("input[name='oneSelect']"),function(i,n){
			if($(this).prop("checked")){	
				saleorderId = $(this).siblings("input[name='saleorderId']").val();
				saleorderGoodsIds += $(this).siblings("input[name='saleorderGoodId']").val()+",";
				if(goodsIds.indexOf($(this).siblings("input[name='goodsId']").val())<0){
					goodsIds += $(this).siblings("input[name='goodsId']").val()+",";
				}
				var oneno = $(this).siblings("input[name='saleorderNo']").val().substring(0,2)+",";			
				var direct = $(this).siblings("input[name='deliveryDirect']").val()+",";
				var saleorderNo = $(this).siblings("input[name='saleorderNo']").val()+",";
				
				allnos +=oneno;
				isDirects +=direct;
				saleorderNos +=saleorderNo;
			}
		});
		if(goodsIds==""||saleorderGoodsIds==""){
			layer.alert("未选择任何信息");
			return false;
		}
		if(isDirects!=''&&isDirects.indexOf("0")>=0&&isDirects.indexOf("1")>=0){
			layer.alert("直发与普发不允许同时被选择");
			return false;
		}
		if(isDirects!=''&&isDirects.indexOf("0")<0&&isDirects.indexOf("1")>=0){
			if(saleorderNos!=''){
				var flag = false;
				var sn = saleorderNos.split(",");
				for(var i =1; i<sn.length; i++){
					if(sn[0] != sn[i] && sn[i] != ''){
						flag = true;
					}
				}
			}
			if(flag && saleorderNos.split(",").length >2){
				layer.alert("直发仅允许选择同订单直发产品");
				return false;
			}
		}
		if(allnos!=''&&allnos.indexOf("BH")>=0&&(allnos.indexOf("VS")>=0||allnos.indexOf("DH")>=0)){
			layer.alert("备货单、销售订单不允许同时被选择");
			return false;
		}
		index = layer.confirm("您是否确认重新采购？", {
			  btn: ['是','否'] //按钮
		}, function(){
			var timestamp = Date.parse(new Date());
			var	url = page_url+"/order/buyorder/addSaleorderToBuyorderPage.do?saleorderGoodsIds="
						+escape(saleorderGoodsIds)+"&goodsIds="+goodsIds;
			$("#addbuyorder").attr('tabTitle','{"num":"view'+timestamp+'","title":"加入采购订单","link":"'+url+'"}');
			$("#addbuyorder").click();
			self.location.reload();
		layer.close(index);
			}, function(){
			});
	})
	
})