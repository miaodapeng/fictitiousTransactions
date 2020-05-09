$(function(){
	var uri = $("input[name='uri']").val();
	var buyorderId = $("input[name='buyorderId']").val();
	var	url = uri+"?&buyorderId="+buyorderId+'&viewType=3';
//	var frameObj = window.frameElement;
//	if(frameObj.src.indexOf("viewBuyorder")<0){
//		frameObj.src = url;
//	}
	if($(window.frameElement).attr('src').indexOf("viewType=3")<0){
		$(window.frameElement).attr('data-url',url);
	}

	$("#sub").click(function(){
		//$("#sub").removeAttr("layerparams");
		$("#sub").attr("disabled","true").attr("class","bt-bg-style bg-light-greybe bt-small mr10 pop-new-data"); 
	})

		
})

function addNum(obj,num,buyorderGoodsId,saleorderGoodsId){
	checkLogin();
	var srnum = $(obj).val();
	var numReg = /^([1]?\d{1,10})$/;
	if(srnum==''){
		layer.alert("数量不能为空");
		$(obj).val(0);
		return false;
	}
	if(srnum !='' && !numReg.test(srnum)){
		layer.alert("数量必须为正整数且小于11位数字");
		$(obj).val(0);
		return false;
	}
	if(Number(srnum)<1 || Number(srnum) > Number(num)){
		layer.alert("数量必须大于1小于"+num);
		$(obj).val(0);
		return false;
	}
	$(obj).siblings("input").val(buyorderGoodsId+"|"+saleorderGoodsId+"|"+srnum);
	var sum =Number(0);
	var goodsId = $(obj).attr("alt1");	
	$.each($("input[alt1='"+goodsId+"']"),function(i,n){
		sum += Number($(this).val());
	});
	$("span[alt='"+goodsId+"']").html(sum);
	$("span[alt='"+goodsId+"']").siblings("input").val(buyorderGoodsId+"|"+sum);
	//计算总件数
	var zSum = Number(0);
	$.each($("span .buyNum"),function(i,n){
		alert($(this).html());
		zSum += Number($(this).html());
	});
	$("#zSum").html(sum);
	//计算单个总额
	var price = $("input[alt='"+goodsId+"']").val();
	if(price != undefined && price !=""){
		$("span[alt='"+buyorderGoodsId+"']").html(Number(sum)*Number(price));
	}
	
	//计算总金额
	var allMoney = Number(0);
	$.each($(".oneAllMoney"),function(i,n){
		allMoney += Number($(this).html());
	});
	$("#zMoney").html(allMoney);
	
	
}

function changPrice(obj,buyorderGoodsId,goodsId){
	checkLogin();
	var price = $(obj).val();
	var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if(price ==''){
		layer.alert("单价不允许为空");
		return false;
	}
	if(price !='' && price.length > 10 && !priceReg.test(price)){
		layer.alert("单价输入错误！仅允许使用数字，最多精确到小数点后两位");
		return false;
	}
	if(Number(price)>300000000){
		layer.alert("单价不允许超过3亿");
		return false;
	}
	
	$(obj).siblings().val(buyorderGoodsId+"|"+price);
	var num = $("span[alt='"+goodsId+"']").html();

	$("span[alt='"+buyorderGoodsId+"']").html(Number(num)*Number(price));
	//计算总金额
	var allMoney = Number(0);
	$.each($(".oneAllMoney"),function(i,n){
		allMoney += Number($(this).html());
	});
	$("#zMoney").html(allMoney);
}

function changComments(obj,buyorderGoodsId){
	checkLogin();
	var insideComments = $(obj).val();
	if(insideComments != ''&& insideComments != undefined){
		$(obj).siblings().val(buyorderGoodsId+"|"+insideComments);
	}
	
}

function applyReview(){
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$("#myform").attr("action", page_url + "/order/buyorder/saveApplyReview.do");
			$("#myform").submit();
		layer.close(index);
		}, function(){
		});
}

function applyValidBuyorder(){
	checkLogin();
	var flag = false
	$.each($(".onePrice"),function(i,n){
		if(Number($(this).html()) == 0){
			flag = true;
			return false;
		}
		 
	});
	var zMoney = $("#zMoney").html();
	
	if(Number(zMoney) != Number($("#prepaidAmount").val()) + Number($("#accountPeriodAmount").val()) + Number($("#retainageAmount").val())){
		layer.alert("付款信息与订单总金额不相符，请确认");
		return false;
	}
	var msg = '';
	if(flag){
		msg = "订单内有商品采购价为0，是否确认提交审核？";
	}else{
		msg = "是否确认提交审核？";
	}
	layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./editApplyValidBuyorder.do",
				data: $('#myform').serialize(),
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

function editBuyorder(){
	checkLogin();
	$("#myform").attr("action", page_url + "/order/buyorder/editAddBuyorderPage.do");
	$("#myform").submit();
}

function closeBuyorder(buyorderId){
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/order/buyorder/saveColseBuyorder.do",
				data: {'buyorderId':buyorderId,'status':3},
				dataType:'json',
				success: function(data){
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			layer.close(index);
		}, function(){
		});
}

function payApplyPass(payApplyId){
	checkLogin();
	index = layer.confirm("您是否确认通过？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/finance/buyorder/payApplyPass.do",
				data: {'payApplyId':payApplyId},
				dataType:'json',
				success: function(data){
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			layer.close(index);
		}, function(){
		});
}
function formatDate(time,format){
	if(time!=null){
		if(format==null){
			format='YYYY-MM-DD';
		}
		var date = new Date(time);
	    var year = date.getFullYear(),
	        month = date.getMonth()+1,//月份是从0开始的
	        day = date.getDate(),
	        hour = date.getHours(),
	        min = date.getMinutes(),
	        sec = date.getSeconds();
	    var preArr = Array.apply(null,Array(10)).map(function(elem, index) {
	        return '0'+index;
	    });////开个长度为10的数组 格式为 00 01 02 03

	    var newTime = format.replace(/YYYY/g,year)
	                        .replace(/MM/g,preArr[month]||month)
	                        .replace(/DD/g,preArr[day]||day)
	                        .replace(/hh/g,preArr[hour]||hour)
	                        .replace(/mm/g,preArr[min]||min)
	                        .replace(/ss/g,preArr[sec]||sec);

	    return newTime;
	}
}
$(document).ready(function() {
	var buyorderId =$("input[name='buyorderId']").val();
	var deliveryDirect=$("input[name='deliveryDirect']").val();
	var validStatus=$("input[name='validStatus']").val();
	var lockedStatus=$("input[name='lockedStatus']").val();
	var status=$("input[name='status']").val();
	var userId=$("input[name='userId']").val();
	var user_id=$("#user_id").val();
	$.ajax({
		type: "POST",
		url: page_url+"/order/buyorder/getSaleBuyNumByAjax.do",
		data: {'buyorderId':buyorderId,'deliveryDirect':deliveryDirect,'validStatus':validStatus,'lockedStatus':lockedStatus,'status':status,'userId':userId},
		dataType:'json',
		success: function(data){
			//物流管理
			var wlhtml="";
			//入库记录
			var rkhtml="";
			var myDate = new Date();
			var buyorderVo=data.data;
			var voList=buyorderVo.buyorderGoodsVoList;
			var expressList=buyorderVo.expressList;
			for(var i=0;i<voList.length;i++){
				var bgv=voList[i];
				if(bgv.saleorderGoodsVoList!=null&&bgv.saleorderGoodsVoList.length>0){
					for(var x=0;x<bgv.saleorderGoodsVoList.length;x++){
						var saleorderGoods=bgv.saleorderGoodsVoList[x];
						var numHtml="";
						if(buyorderVo.deliveryDirect == 1){
							var number=Number(saleorderGoods.num)-Number(saleorderGoods.buyNum)+Number(saleorderGoods.saleBuyNum);
							numHtml=numHtml+((saleorderGoods.saleBuyNum==null||saleorderGoods.saleBuyNum=='')? 0 : saleorderGoods.saleBuyNum)+"/"+number;
						}else if(buyorderVo.deliveryDirect == 0){
							var number=Number(saleorderGoods.needBuyNum)+Number(saleorderGoods.saleBuyNum);
							numHtml=numHtml+((saleorderGoods.saleBuyNum==null||saleorderGoods.saleBuyNum=='')? 0 : saleorderGoods.saleBuyNum)+"/"+number;
						}
						$("#"+(i+1)+"_"+saleorderGoods.saleorderId+"_"+x).html(numHtml);
					}
				}
			}
			/************************************************************************************/
			for(var i=0;i<expressList.length;i++){
				 var express=expressList[i];
				 wlhtml=wlhtml+"<tr><td>"+express.logisticsNo+"</td><td>"+express.logisticsName+"</td>" +
				 		"<td>"+formatDate(express.deliveryTime)+"</td><td>";
				 		var amount=0.00;
				 		for(var j=0;j<express.expressDetail.length;j++){
				 			var expressDetails=express.expressDetail[j];
				 			///////////
				 			amount=Number(amount)+Number(expressDetails.amount);
				 		}
				 		/////////
				 		wlhtml=wlhtml+amount.toFixed(2)+"</td><td class='text-left'>";
				 		for(var z=0;z<express.expressDetail.length;z++){
				 			var expressDetails=express.expressDetail[z];
				 			wlhtml=wlhtml+"<div>"+expressDetails.goodName+"&nbsp;&nbsp;&nbsp;&nbsp;"+expressDetails.num+expressDetails.unitName+"</div><br/>";
				 		}
				 		wlhtml=wlhtml+"</td><td>"+express.logisticsComments+"</td><td>"+express.updaterUsername+"</td><td>";
				 		/////
				 		if(parseInt(buyorderVo.validStatus)==1&&parseInt(buyorderVo.lockedStatus)!=1&&parseInt(buyorderVo.status)!=3&& buyorderVo.userId == user_id){
				 			wlhtml=wlhtml+"<span class='bt-smaller bt-border-style border-blue loadMoreAddtitle' " +
				 					"tabTitle='{\"num\":\"editExpress\",\"link\":\"./order/buyorder/editExpress.do?" +
				 					"expressId="+express.expressId+"&buyorderId="+buyorderVo.buyorderId+"\"," +
				 					"\"title\":\" 编辑快递\"}'>编辑</span>";
				 		}
				 		wlhtml=wlhtml+"</td></tr>";
			}
			if(buyorderVo.expressList!=null&&buyorderVo.expressList.length>0){
				wlhtml=wlhtml+"<tr> <td colspan='8' class='allchosetr text-left'>";
				var allamount=0.00;
				var allarrivalnum=0;
				for(var i=0;i<buyorderVo.expressList.length;i++){
					amount=0.00;
					var arrivalnum=0;
					for(var j=0;j<express.expressDetail.length;j++){
						amount=Number(amount)+Number(expressDetails.amount);
						arrivalnum=Number(arrivalnum)+Number(expressDetails.num);
					}
					allamount=(Number(allamount)+Number(amount)).toFixed(2);
					allarrivalnum=Number(allarrivalnum)+Number(arrivalnum);
				}
				var allnum=0;
				for(var i=0;i<buyorderVo.buyorderGoodsVoList.length;i++){
					allnum=Number(allnum)+Number(bgv.num);
				}
				wlhtml=wlhtml+" 运费总额：<span class='warning-color1 mr10'>"+allamount+"</span>" +
						"已发/商品总数:  <span class='warning-color1'>"+allarrivalnum+"/"+allnum+"</span>" +
						"</td></tr>";
			}else{
				wlhtml=wlhtml+"<tr><td colspan='8'>暂无记录！</td></tr>";
			}
			/************************************************************************************/
			var logVoList=buyorderVo.warehouseGoodsOperateLogVoList;
			if(logVoList!=null&&logVoList.length>0){
				num=0;	
				for(var i=0;i<logVoList.length;i++){
					var agolv=logVoList[i];
					rkhtml=rkhtml+"<tr><td>"+(num*1+1)+"</td>" +
							"<td>"+formatDate(agolv.addTime)+"</td><td class='text-left'>" +
							"<span class='font-blue cursor-pointer loadMoreAddtitle' tabTitle='{\"num\":" +
							"\"viewsaleorder"+myDate.getMilliseconds() + parseInt(Math.random()*10000)+"\"" +
							",\"link\":\"./goods/goods/viewbaseinfo.do?goodsId="+agolv.goodsId+"\"," +
							"\"title\":\"产品信息\"}'>"+agolv.goodsName+"</span></br>"+agolv.sku+"</br>"+agolv.materialCode+"</td>" +
							"<td>"+agolv.brandName+"</td><td>"+agolv.model+"</td><td>"+agolv.num+"</td><td>"+agolv.unitName+"</td>" +
							"<td><date:date value ='"+agolv.expirationDate+"'/></td><td>"+(agolv.batchNumber == null ? '' : agolv.batchNumber)+"</td>" +
							"<td>"+agolv.operaterName+"</td></tr>";
					num++;
				}
			}else{
				rkhtml=rkhtml+"<tr><td colspan='10'>暂无记录！</td></tr>";
			}
			/************************************************************************************/
			if(wlhtml!=""){
				$("#wulb").find("tr:last").after(wlhtml);
			}
			if(rkhtml!=""){
				$("#rk").find("tr:last").after(rkhtml);
			}
			loadMoreAddTitle();
			loadMoreBlueKuang();
		},
		error:function(data){
			loadMoreClose();
			if(data.status ==1001){
				layer.alert("当前操作无权限");
			}
		}
	});
});