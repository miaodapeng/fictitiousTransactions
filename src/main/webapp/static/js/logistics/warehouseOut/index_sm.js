$(document).ready(function(e) {
	console.log($("#googsListDiv"))
	$("input[id='smgoods']").focus();

	window.totalNum = {};

	$('#googsListDiv input').each(function () {
		if(totalNum[$(this).attr('id')]){
			totalNum[$(this).attr('id')] += parseInt($(this).val());
		}else{
			totalNum[$(this).attr('id')] = parseInt($(this).val());
		}
	});

	var getNum = function(id){
		var num = 0;
		var type = 0;
		if($('[id='+ id +'][data-flag=1]').length){
			var readyNum = $('.J-active-' + id).length;
			var activeNum = parseInt($('[id='+ id +'][data-flag=1]').val());

			if(activeNum <= readyNum){
				num = $('[id='+ id +'][data-flag=0]').val();
			}else{
				num = $('[id='+ id +'][data-flag=1]').val();
				type = 1;
			}

		}else{
			num = $('[id='+ id +']').val() || 0;
		}

		return {
			num: num,
			type: type
		};
	};

	document.onkeydown = function(e){
	    if(!e){
	        e = window.event;
	    }
	    if((e.keyCode || e.which) == 13){
        	var sNo ="";
        	sNo = $("#smgoods").val();
        	if(sNo==""){
        		layer.alert("条码不允许为空！");
	        	return false;
        	}
        	var reg=/^[1-9]\d*$|^0$/; 
        	if(reg.test(sNo)==false)
    		{
        		layer.alert("条码格式不正确！");
        		return false;
    		}
        	var flag =0;
        	$ ('#outRecode tr ').each (function (){
	            if($(this).find("td:eq(0)").text() == sNo){
	            	flag=1;
	            }
	        })
	        if(flag==1){
	        	layer.alert("条码重复录入！");
	        	return false;
	        }
        	var orderId = $("#saleorderId").val();
        	if(orderId==undefined){
        		orderId = $("#afterSalesId").val();
        	}
        	var bussinessType = $("#bussinessType").val();
    		$.ajax({
    			url : page_url + '/warehouse/warehousesout/getSMGoods.do',
    			data : {
    				"barcode" : sNo,
    				"orderId" : orderId,
    				"bussinessType" : bussinessType
    			},
    			type : "POST",
    			dataType : "json",
    			success : function(data) {
    				if(data.code == -1){
    					layer.alert("该条码无可出库商品");
    				}else if(data.code == -2){
    					layer.alert("该产品已被锁定，无法出库");
    				}else{
    					if(data.data!=null){
    	    				var whLog = data.data;
    	    				$("#blank").remove();
    	    				var num=1;
    	    	        	$ ('#outRecode tr').each (function (){
    	    	        		num++;
    	    	        	  })
    	    	        	for(var i=0;i<whLog.length;i++){
								var numItem = getNum(whLog[i].goodsId);
    	    	        		//给活动商品做标记
								var action = '';
								if (numItem.type == 1){
									action = '<font color="red">[活动]</font>'
								}
    	    	        		// 避免页面厂商条码出现null
    	    	        		var barcodeFactory = (null == whLog[i].barcodeFactory || 'null' == whLog[i].barcodeFactory) ? '' : whLog[i].barcodeFactory;
    	    	        		var materialCode = (null == whLog[i].materialCode || 'null' == whLog[i].materialCode) ? '' : whLog[i].materialCode;
    	    	        		// 对于采购单价,销售单价 保留2位有效数字
    	    	        		var cgPrice = (null == whLog[i].cgprice || 'null' == whLog[i].cgprice || undefined == whLog[i].cgprice) ? 0.00 : whLog[i].cgprice;
    	    	        		var xsPrice = (null == whLog[i].xsprice || 'null' == whLog[i].xsprice || undefined == whLog[i].xsprice) ? 0.00 : whLog[i].xsprice;
    	    	        		cgPrice = cgPrice.toFixed(2);
    	    	        		xsPrice = xsPrice.toFixed(2);
    	    	        		
    	    	        		var htm = "<tr><td style='display:none;'>"+whLog[i].barcode+"</td>"+
    					          "<td style='display:none;'><input  type='hidden' value='"+whLog[i].warehouseGoodsOperateLogId+"'></input></td>"+
    							  "<td>"+(num+i)+"</td>"+
    					          "<td class='text-left'>"+
    	                          " <div >"+
    	                          "<a class='addtitle' href='javascript:void(0);' tabTitle='{'num':'viewgoods"+whLog[i].warehouseGoodsOperateLogId+"','link':'./goods/goods/viewbaseinfo.do?goodsId="+whLog[i].goodsId+"','title':'产品信息'}'>"+action+whLog[i].goodsName+"</a>"+
    	                          "</div>"+
    	                          "<div>"+whLog[i].sku+"</div>"+
    	                          "<div>"+materialCode+"</div>"+
    			                  " </td>"+
    			                  "<td>"+whLog[i].brandName+"/"+whLog[i].model+"</td>"+
    			                  "<td>"+whLog[i].unitName+"</td>"+
    			                  "<td>"+whLog[i].barcode+"</td>"+
    			                  "<td>"+barcodeFactory+"</td>"+
    			                  "<td class='J-num'><input name='outNum' id = '"+whLog[i].num+"' style='width:50px;text-align:right;' value='"+whLog[i].num+"' onblur='outNums(this)'  /></td>";
    			                  // $("#googsListDiv").find("input").each(function () {
    			                	//   if(whLog[i].goodsId == $(this).attr('id')){
    			                	// 	  htm+="<td>"+$(this).val()+"</td>";
    			             		// 	 }
    			                  // })

								  // 还需出库数量验证
                                    if (numItem.num == null){
                                        numItem.num = 0;
                                    }
								  htm +='<td class="' +  (numItem.type == 1 ? 'J-active-' + whLog[i].goodsId: '') + '">' + numItem.num + '</td>';
    			                  htm+="<td>"+cgPrice+"</td>"+
    			                  "<td>"+xsPrice+"</td>"+
    			                  "<td ><span onclick='delR(this)' style='color: red;    cursor: pointer;'>删除</span></td>"+
    			                  "<td class='J-id' style='display:none;'>"+whLog[i].goodsId+"</td>"+
    			                  "</tr>";
    					          $("#outRecode").append(htm);
    					          $("#smgoods").val("");
    	    	        	}
    	    				
    	    				}else{
    	    					layer.alert("该条码无可出库商品");
    	    				}
    				}
    			},
    			error: function(data){
    				if(data.status ==1001){
    					layer.alert("当前操作无权限")
    				}else
    				layer.alert("无此条码")}
    		});
        }
	}
})
function outNums(obj){
	checkLogin();
	var v = $(obj).val();
	var re =/^\+?(0|[1-9][0-9]*)$/;
    if (!re.test(v)) {
    	layer.alert("出库数量为非负整数！");
    	$(obj).val($(obj).attr("id"));
    	return false;
    } else {
    	var cnt = parseInt($(obj).val());
    	var num = parseInt($(obj).attr("id"));
    	if(cnt>num){
    		layer.alert("出库数量不允许大于在库数量！")
    		$(obj).val($(obj).attr("id"));
    		return false;
    	}
    }
}
function delR(now){
	checkLogin();
		$(now).parent().parent().remove();
		var num =0;
		$ ('#outRecode tr td:eq(0)').each (function (){
            num++;
        })
        if(num==0){
        	var htm ="<tr id='blank'>"+
						"<td colspan='10' >暂无出库记录</td>"+
					"</tr>";
        	$("#outRecode").append(htm);
        }
}
//条码出库
function search_sm() {
	//checkLogin();
	$("#searchSpan").removeAttr("onclick");
	var sm = $("#smgoods").val();
	/*if(sm==""){
		layer.alert("条码不能为空！");
		return false;
	}*/
	var cnt =0;
	var num = 0;
	$('#outRecode tr ').each (function (){
		var tdArr = $(this).children();
		cnt += parseInt(tdArr.eq(8).find('input').val());
	})
	if(cnt==0 && sm==""){
		layer.alert("请至少出库一件商品！");
		$("#searchSpan").attr("onclick","search_sm();");
		return false;
	}
	var point=0;
	for(var item in totalNum){
		var outNum = 0;
		$('#outRecode tr ').each (function (){
			var tdArr = $(this).find('.J-id');
			if(tdArr.text()==item){
				outNum+=parseInt($(this).find('.J-num input').val());
			}
		});

		if(totalNum[item]<outNum){
			layer.alert("订货号为V"+item+"的商品出库数不允许大于商品需出库总数"+totalNum[item]+"！");
			point = -1 ;
			$("#searchSpan").attr("onclick","search_sm();");
			return false;
		}
	}

     if(point==-1){
    	 return false;
     }
	if($("#blank").text()!=""){
		layer.alert("条码不存在！");
		$("#searchSpan").attr("onclick","search_sm();");
		return false;
	}
	var idCnt ="";
	var flag ="0";
	$('#outRecode tr ').each (function (){
		var tdArr = $(this).children();
		/*//出库前判断，商品是否属于拣货
		var flag=-1;
		var smGoodsId = $('#outRecode tr td:eq(12)').text();
		$("#pickTable tr td").each (function (){
			if(smGoodsId==$("#pickTable tr td:eq(6)").text()){
				flag=1;
			}
		})
		if(flag==-1){
			layer.alert("序号"+$('#outRecode tr td:eq(2)').text()+"商品不是订单需要发货的商品，请核实！");
			return false;
		}*/
		//再判断出库商品数>0
		if(parseInt(tdArr.eq(8).find('input').val())>0){
			idCnt+=tdArr.eq(1).find('input').val()+","+tdArr.eq(8).find('input').val()/*+","+tdArr.eq(9).find('input').val()*/;
			idCnt+="#";
		}
    })
	 $("#idCnt").val(idCnt);
     $("#search").submit();
}