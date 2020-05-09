$(function(){
	$("#buyorder").click(function(){
		checkLogin();
		var directs ="";
		var saleorderType="";
		var saleorderGoodsIds="";
		var orderType = "";
		var goodsIds = "";
		$.each($("input[name='oneSelect']"),function(i,n){
			if($(this).prop("checked")){
				saleorderType += $(this).siblings("input[name='saleorderNo']").val()+",";			
				directs += $(this).siblings("input[name='deliveryDirect']").val()+",";
				saleorderId = $(this).siblings("input[name='saleorderId']").val();
				saleorderGoodsIds += $(this).siblings("input[name='saleorderGoodId']").val()+",";
				if(goodsIds.indexOf($(this).siblings("input[name='goodsId']").val())<0){
					goodsIds += $(this).siblings("input[name='goodsId']").val()+",";
				}
			}
		});
		if(goodsIds == "" || saleorderGoodsIds == ""){
			layer.alert("未选择任何信息");
			return false;
		}
		if(saleorderType.indexOf("BH")>=0&&(saleorderType.indexOf("VS")>=0 || saleorderType.indexOf("DH")>=0 || saleorderType.indexOf("JX")>=0)){
			layer.alert("备货单、销售订单不允许同时被选择");
			return false;
		}
		if(directs.indexOf("1")>=0&&directs.indexOf("0")>=0){
			layer.alert("直发与普发不允许同时被选择");
			return false;
		}
		if(directs.indexOf("1")>=0&&directs.indexOf("0")<0){
			var saleorderNos = saleorderType.split(",");
			var flag =false;
			if(saleorderNos.length>1){
				for(var i=1; i<saleorderNos.length;i++){
					if(saleorderNos[i] != '' && saleorderNos[0] != saleorderNos[i]){
						flag=true;
						return false;
					}
				}
			}
			if(flag){
				layer.alert("直发仅允许选择同订单直发产品");
				return false;
			}
		}
		if(saleorderType.indexOf("BH")>=0){
			orderType = 1;
		}else{
			orderType = 0;
		}
		var direct= '';
		if(directs.indexOf("1")>=0&&directs.indexOf("0")<0){
			direct = 1;
		}else{
			direct = 0;
		}
		index = layer.confirm("您是否确认该操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
			var timestamp = Date.parse(new Date());
			var	url = page_url+"/order/buyorder/saveAddBuyorder.do?orderType="+orderType+"&saleorderGoodsIds="
						+escape(saleorderGoodsIds)+"&deliveryDirect="+direct+"&goodsIds="+goodsIds+"&saleorderId="+saleorderId+"&isIgnore="+0;
			$("#addpf").attr('tabTitle','{"num":"view'+timestamp+'","title":"新增采购订单","link":"'+url+'"}');
			$("#addpf").click();
			layer.close(index);
			});
	})
	
	$("#addbuy").click(function(){
		checkLogin();
		var direct ="";
		var saleorderType="";
		var saleorderGoodsIds="";
		var goodsIds = "";
		var saleorderId = "";
		$.each($("input[name='oneSelect']"),function(i,n){
			if($(this).prop("checked")){
				saleorderType += $(this).siblings("input[name='saleorderNo']").val()+",";			
				direct += $(this).siblings("input[name='deliveryDirect']").val()+",";
				saleorderId = $(this).siblings("input[name='saleorderId']").val();
				saleorderGoodsIds += $(this).siblings("input[name='saleorderGoodId']").val()+",";
				if(goodsIds.indexOf($(this).siblings("input[name='goodsId']").val())<0){
					goodsIds += $(this).siblings("input[name='goodsId']").val()+",";
				}
			}
		});
		if(goodsIds==""||saleorderGoodsIds==""){
			layer.alert("未选择任何信息");
			return false;
		}
		if(saleorderType.indexOf("BH")>=0&&(saleorderType.indexOf("VS")>=0 || saleorderType.indexOf("DH")>=0 || saleorderType.indexOf("JX")>=0)){
			layer.alert("备货单、销售订单不允许同时被选择");
			return false;
		}
		if(direct.indexOf("1")>=0&&direct.indexOf("0")>=0){
			layer.alert("直发与普发不允许同时被选择");
			return false;
		}
		if(direct.indexOf("1")>=0&&direct.indexOf("0")<0){
			var saleorderNos = saleorderType.split(",");
			var flag =false;
			if(saleorderNos.length>1){
				for(var i=1; i<saleorderNos.length;i++){
					if(saleorderNos[i] != '' && saleorderNos[0] != saleorderNos[i]){
						flag=true;
						return false;
					}
				}
			}
			if(flag){
				layer.alert("直发仅允许选择同订单直发产品");
				return false;
			}
		}
		
		index = layer.confirm("您是否确认该操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
			var timestamp = Date.parse(new Date()); 
			var	url = page_url+"/order/buyorder/addSaleorderToBuyorderPage.do?saleorderGoodsIds="
						+escape(saleorderGoodsIds)+"&goodsIds="+goodsIds;
			$("#addbuyorder").attr('tabTitle','{"num":"view'+timestamp+'","title":"加入采购订单","link":"'+url+'"}');
			$("#addbuyorder").click();
			layer.close(index);
		layer.close(index);
			}, function(){
			});
	})
	
});

//忽略
function ignore(goodsId){
	checkLogin();
	var saleorderGoodsIDs = "";
	$.each($("input[alt='"+goodsId+"']"),function(i,n){
		if($(this).prop("checked")){
			saleorderGoodsIDs += $(this).siblings("input[name='saleorderGoodId']").val()+",";
		}
	});
	if(saleorderGoodsIDs ==''){
		layer.alert("请选择要忽略数据");
		return false;
	}
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/order/buyorder/saveIgnore.do",
				data: {'saleorderGoodsIDs':saleorderGoodsIDs},
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

function oneSelect(obj){
	checkLogin();
	if($(obj).prop("checked")){
		//计算所有已选择的，排除刚点击的
		var allSelect = 0;
		var allnos = '';
		var isDirects = '';
		var saleorderNoss = '';
		var objsaleorderGoodsId = $(obj).siblings("input[name='saleorderGoodId']").val();
		$.each($("input[name='oneSelect']"),function(i,n){
			var goodsId = $(this).siblings("input[name='saleorderId']").val();
			var thissaleorderGoodsId = $(this).siblings("input[name='saleorderGoodId']").val();
			if($(this).prop("checked") && objsaleorderGoodsId != thissaleorderGoodsId){
				allnos += $(this).siblings("input[name='saleorderNo']").val().substring(0,2)+",";			
				isDirects += $(this).siblings("input[name='deliveryDirect']").val()+",";
				saleorderNoss += $(this).siblings("input[name='saleorderNo']").val()+",";
				allSelect += Number($(this).siblings("input[name='noBuyNum']").val());
			}
		});
		$("#selected").html(allSelect);
		//本次点击数据
		var direct = $(obj).siblings("input[name='deliveryDirect']").val();
		var oneno = $(obj).siblings("input[name='saleorderNo']").val().substring(0,2);
		var saleorderNo = $(obj).siblings("input[name='saleorderNo']").val();
		if(isDirects!=''&&((isDirects.indexOf("0")>=0&&direct.indexOf("1")>=0)||(isDirects.indexOf("1")>=0&&direct.indexOf("0")>=0))){
			layer.alert("直发与普发不允许同时被选择");
			$(obj).prop("checked", false);
			return false;
		}else if(isDirects!=''&&isDirects.indexOf("0")<0&&direct.indexOf("1")>=0&&saleorderNoss!=''&&saleorderNoss.indexOf(saleorderNo)<0 ){
			layer.alert("直发仅允许选择同订单直发产品");
			$(obj).prop("checked", false);
			return false;
		}
		if(allnos!=''&&(allnos.indexOf("BH")>=0&&(oneno.indexOf("VS")>=0||oneno.indexOf("DH")>=0 || oneno.indexOf("JX")>=0)
				||((allnos.indexOf("VS")>=0||allnos.indexOf("DH")>=0 || allnos.indexOf("JX")>=0 ) && oneno.indexOf("BH")>=0))){
			layer.alert("备货单、销售订单不允许同时被选择");
			$(obj).prop("checked", false);
			return false;
		}
		var goodsid = $(obj).siblings("input[name='saleorderId']").val();
		allSelect += Number($(obj).siblings("input[name='noBuyNum']").val());
		$("#selected").html(allSelect);
		//判断本次表格是否全选
		if(!$("input[alt2='"+goodsid+"']").prop("checked")){
			var falg =false;
			var oneTableNum =Number(0);
			$.each($("input[alt='"+goodsid+"']"),function(i,n){
				if($(this).prop("checked")){
					oneTableNum += Number($(this).siblings("input[name='noBuyNum']").val());
				}else{
					falg = true;
				}
			});
			$("span[alt1='"+goodsid+"']").html(oneTableNum);
			if(!falg){
				$("input[alt2='"+goodsid+"']").prop("checked", "checked");
			}
		}
		//判断当前页面是否已经全选
		var allSelect = true;
		$.each($("input[name='oneAllSelect']"),function(i,n){
			if(!$(this).prop("checked")){
				allSelect = false;
				return false;
			}
		});
		if(allSelect){
			$("input[name='allSelect']").prop("checked", "checked");
		}
	}else{
		var goodsId = $(obj).siblings("input[name='saleorderId']").val();
		var oneTableNum =Number(0);
		$.each($("input[alt='"+goodsId+"']"),function(i,n){
			if($(this).prop("checked")){
				oneTableNum += Number($(this).siblings("input[name='noBuyNum']").val());
			}
		});
		$("span[alt1='"+goodsId+"']").html(oneTableNum);
		$("input[alt2='"+goodsId+"']").prop("checked", false);
		$("input[name='allSelect']").prop("checked", false);
		var allSelect = 0;
		$.each($("input[name='oneSelect']"),function(i,n){
			if($(this).prop("checked")){
				allSelect += Number($(this).siblings("input[name='noBuyNum']").val());
			}
		});
		$("#selected").html(allSelect);
	}
}
//单个表格全选
function oneAllSelect(obj){
	checkLogin();
	if($(obj).prop("checked")){
		//计算所有已选择的，排除刚点击的
		var allSelect = 0;
		var allnos = '';
		var isDirects = '';
		var saleorderNoss = '';
		var objgoodsId = $(obj).attr("alt2");
		$.each($("input[name='oneSelect']"),function(i,n){
			var goodsId = $(this).siblings("input[name='saleorderId']").val();
			if($(this).prop("checked") && objgoodsId != goodsId){
				allnos += $(this).siblings("input[name='saleorderNo']").val().substring(0,2)+",";			
				isDirects += $(this).siblings("input[name='deliveryDirect']").val()+",";
				saleorderNoss += $(this).siblings("input[name='saleorderNo']").val()+",";
				allSelect += Number($(this).siblings("input[name='noBuyNum']").val());
			}
		});
		$("#selected").html(allSelect);
		
		//本次全选的表格
		var flag = false;
		var oneTableNum = Number(0);
		
		$.each($("input[alt='"+objgoodsId+"']"),function(i,n){
			var direct = $(this).siblings("input[name='deliveryDirect']").val();
			var oneno = $(this).siblings("input[name='saleorderNo']").val().substring(0,2);
			var saleorderNo = $(this).siblings("input[name='saleorderNo']").val();
			if(isDirects!=''&&((isDirects.indexOf("0")>=0&&direct.indexOf("1")>=0)||(isDirects.indexOf("1")>=0&&direct.indexOf("0")>=0))){
				layer.alert("直发与普发不允许同时被选择");
				flag = true;
			}else if(isDirects!=''&&isDirects.indexOf("0")<0&&direct.indexOf("1")>=0&&saleorderNoss!=''&&saleorderNoss.indexOf(saleorderNo)<0 ){
				layer.alert("直发仅允许选择同订单直发产品");
				flag = true;
			}
			if(allnos!=''&&(allnos.indexOf("BH")>=0&&(oneno.indexOf("VS")>=0||oneno.indexOf("DH")>=0||oneno.indexOf("JX")>=0)
					||((allnos.indexOf("VS")>=0||allnos.indexOf("DH")>=0||allnos.indexOf("JX")>=0)&&oneno.indexOf("BH")>=0))){
				layer.alert("备货单、销售订单不允许同时被选择");
				flag = true;
			}
			isDirects += $(this).siblings("input[name='deliveryDirect']").val()+",";
			allnos += $(this).siblings("input[name='saleorderNo']").val().substring(0,2)+",";
			saleorderNoss += $(this).siblings("input[name='saleorderNo']").val()+",";
			oneTableNum += Number($(this).siblings("input[name='noBuyNum']").val());
		});
		if(!flag){
			$.each($("input[alt='"+objgoodsId+"']"),function(i,n){
				$(this).prop("checked","checked");
			})
			$("span[alt1='"+objgoodsId+"']").html(oneTableNum);
			allSelect += Number(oneTableNum);
			$("#selected").html(allSelect);
		}else{
			$(obj).prop("checked",false);
		}
		//判断当前页面是否已经全选
		var allSelect = true;
		$.each($("input[name='oneAllSelect']"),function(i,n){
			if(!$(this).prop("checked")){
				allSelect = false;
				return false;
			}
		});
		if(allSelect){
			$("input[name='allSelect']").prop("checked", "checked");
		}
		
	}else{
		var allSelect = 0;
		var objgoodsId = $(obj).attr("alt2");
		$.each($("input[alt='"+objgoodsId+"']"),function(i,n){
			$(this).prop("checked",false);
		})
		$("span[alt1='"+objgoodsId+"']").html(0);
		$.each($("input[name='oneSelect']"),function(i,n){
			if($(this).prop("checked")){
				allSelect += Number($(this).siblings("input[name='noBuyNum']").val());
			}
		});
		$("#selected").html(allSelect);
		$("input[name='allSelect']").prop("checked", false);
	}
}

function allSelect(obj){
	checkLogin();
	if($(obj).prop("checked")){
		var allSelect = 0;
		var allnos = '';
		var isDirects = '';
		var saleorderNoss = '';
		var flag = false;
		$.each($("input[name='oneSelect']"),function(i,n){
			var oneno = $(this).siblings("input[name='saleorderNo']").val().substring(0,2)+",";			
			var direct = $(this).siblings("input[name='deliveryDirect']").val()+",";
			var saleorderNo = $(this).siblings("input[name='saleorderNo']").val()+",";
			var buynum = Number($(this).siblings("input[name='noBuyNum']").val());
			var goodsId = $(this).siblings("input[name='saleorderId']").val();
			if(isDirects!=''&&((isDirects.indexOf("0")>=0&&direct.indexOf("1")>=0)||(isDirects.indexOf("1")>=0&&direct.indexOf("0")>=0))){
				layer.alert("直发与普发不允许同时被选择");
				$(this).prop("checked", false);
				
				buynum = Number(0);
				flag = true;
				return false;
			}else if(isDirects!=''&&isDirects.indexOf("0")<0&&isDirects.indexOf("1")>=0&&saleorderNoss!=''&&saleorderNoss.indexOf(saleorderNo)<0 ){
				layer.alert("直发仅允许选择同订单直发产品");
				$(this).prop("checked", false);
				buynum = Number(0);
				flag = true;
				return false;
			}
			if(allnos!=''&&(allnos.indexOf("BH")>=0&&(oneno.indexOf("VS")>=0||oneno.indexOf("DH")>=0||oneno.indexOf("JX")>=0)
					||((allnos.indexOf("VS")>=0||allnos.indexOf("DH")>=0||allnos.indexOf("JX")>=0)&&oneno.indexOf("BH")>=0))){
				layer.alert("备货单、销售订单不允许同时被选择");
				$(this).prop("checked", false);
				buynum = Number(0);
				flag = true;
				return false;
			}
			$(this).prop("checked",true);
			if(!$("input[alt2='"+goodsId+"']").prop("checked")){
				//判断单个表是否全选
				var falg =false;
				var oneTableNum =Number(0);
				$.each($("input[alt='"+goodsId+"']"),function(i,n){
					oneTableNum += Number($(this).siblings("input[name='noBuyNum']").val());
					if(!$(this).prop("checked")){
						falg = true;
					}
				});
				$("span[alt1='"+goodsId+"']").html(oneTableNum);
				if(!falg){
					$("input[alt2='"+goodsId+"']").prop("checked", "checked");
				}
			}
			allnos +=oneno;
			isDirects +=direct;
			saleorderNoss +=saleorderNo;
			allSelect += buynum;
			
		});
		if(flag){
			$.each($("input[name='oneSelect']"),function(i,n){
				if($(this).prop("checked")){
					$(this).prop("checked", false);
				}
			});
			$.each($("input[name='oneAllSelect']"),function(i,n){
				if($(this).prop("checked")){
					$(this).prop("checked", false);
				}
			});
			$(obj).prop("checked", false);
		}else{
			$("#selected").html(allSelect);
		}
		
	}else{
		$.each($("input[name='oneSelect']"),function(i,n){
			$(this).prop("checked", false);
		});
		$.each($("input[name='oneAllSelect']"),function(i,n){
			$(this).prop("checked", false);
			var goodsId = $(this).attr("alt2");
			$("span[alt1='"+goodsId+"']").html(0);
		});
		$("#selected").html(0);
	}	
}

function changeOrg(obj){
	checkLogin();
	var orgId = $(obj).val();

	if(orgId != ''){
		$.ajax({
			type: "POST",
			url: page_url+"/order/buyorder/getProductorUserList.do",
			data: {'orgId':orgId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#proUserId").empty();
					var option = '<option value="">全部</option>';
					for(var i=0; i<data.listData.length;i++){
						option +='<option value="'+data.listData[i].userId+'">'+data.listData[i].username+'</option>'
					}
					$("#proUserId").append(option);
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
	}
	//选择全部显示全部
	if (orgId == '') {
		$.ajax({
			type: "POST",
			url: page_url+"/order/buyorder/getProductorUserListCount.do",
			/*data: {'orgId':orgId},*/
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#proUserId").empty();
					var option = '<option value="">全部</option>';
					for(var i=0; i<data.listData.length;i++){
						option +='<option value="'+data.listData[i].userId+'">'+data.listData[i].username+'</option>'
					}
					$("#proUserId").append(option);
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
	}

	
	
}

