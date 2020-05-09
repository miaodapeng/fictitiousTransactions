function addBarcode(Id,goodsId,totalNum,enableNum,type){
	checkLogin();
	var buyorderGoodsId ="";
	var afterSalesGoodsId ="";
	if(type==1){
		buyorderGoodsId = Id;
	}else if(type==2){
		afterSalesGoodsId = Id;
	}else if(type==4){
		afterSalesGoodsId = Id;
	}
	var addBarcodeNum = $("input[name='addBarcodeNum']").val();
	var re = /^[0-9]+$/;
	if(!re.test(addBarcodeNum)){
		$("input[name='addBarcodeNum']").val(totalNum-enableNum);
		layer.alert('二维码个数必须为正整数');
		return false;
	}
	if(addBarcodeNum == 0){
		$("input[name='addBarcodeNum']").val(totalNum-enableNum);
		layer.alert('二维码个数必须为正整数');
		return false;
	}
	if((totalNum-enableNum)<addBarcodeNum){
		$("input[name='addBarcodeNum']").val(totalNum-enableNum);
		layer.alert('超出可生成的二维码个数');
		return false;
	}
	$.ajax({
		async : false,
		url : page_url + '/logistics/warehousein/addBarcodeImg.do',
		data : {
			"type" : type,
			"num" : addBarcodeNum,
			"buyorderGoodsId" : buyorderGoodsId,
			"afterSalesGoodsId" : afterSalesGoodsId,
			"goodsId" : goodsId
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
				layer.alert("条形码生成成功", { icon : 1},function(index){
					location.reload();
				});
			}else{
				layer.alert(data.message, { icon : 2});
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}
//全选
function selectall(obj){
	checkLogin();
	if($(obj).is(":checked")){
		$("input[type='checkbox']").not(':disabled').prop("checked",true);
		}else{
		$("input[type='checkbox']").not(':disabled').prop('checked',false);
			}
}
//根据日期选择
function checkbarcode(m,f){
	checkLogin();
	if(!m)	return false;
	$("input[name='b_checknox']").each(function(){
		if($(this).attr("alt") == m){
			$(this).prop('checked',f);
		}
	});
}
//验证生成数量
function checkNum(totalNum,enableNum,obj){
	checkLogin();
	var re = /^[0-9]+$/;
	if(!re.test($(obj).val())){
		layer.alert('二维码个数必须为正整数');
		$(obj).val(totalNum-enableNum);
		return false;
	}
	if((totalNum-enableNum)<$(obj).val()){
		$(obj).val(totalNum-enableNum);
		layer.alert('超出可生成的二维码个数');
		return false;
	}
}
//单个打印
function preview(myDiv) {
	checkLogin();
	$("#"+myDiv).printArea();
	
};
//批量打印
function  printall(){
	checkLogin();
	if($("input[name='b_checknox']:checked").length <= 0){
		alert('请选择要打印的条码');
		return false;
	}
	var print = '';
	$.each($("input[name='b_checknox']:checked"),function(){
		var id = $(this).val();
		print+=$.trim($(this).parent().parent().find('.pr_'+id).html());
	});
	$(print).printArea();
}
//单个作废
function cancelBarcode(barcodeIds){
	checkLogin();
	console.log(barcodeIds);
	if(barcodeIds > 0){
		layer.confirm('确定作废？', function(index){
			$.ajax({
				async : false,
	    		url : page_url + '/logistics/warehousein/checkBarcode.do',
	    		data : {
	    			"barcodeId" : barcodeIds,
	    			"beforeParams":barcodeIds
	    		},
	    		type : "POST",
	    		dataType : "json",
	    		success : function(data) {
	    			if(data.code == -1){
	    				$.ajax({
	    		    		async : false,
	    		    		url : page_url + '/logistics/warehousein/editBarcode.do',
	    		    		data : {
	    		    			"barcodeIds" : barcodeIds,
	    		    			"beforeParams":barcodeIds
	    		    		},
	    		    		type : "POST",
	    		    		dataType : "json",
	    		    		success : function(data) {
	    		    			if(data.code == 0){
	    		    				layer.alert("作废成功", { icon : 1},function(index){
	    		    					location.reload();
	    		    				});
	    		    			}else{
	    		    				layer.alert(data.message, { icon : 2});
	    		    			}
	    		    		},
	    		    		error:function(data){
	    		    			if(data.status ==1001){
	    		    				layer.alert("当前操作无权限")
	    		    			}
	    		    		}
	    		    	});
	    			}else{
	    				layer.alert(data.message, { icon : 2});
	    			}
	    		},
	    		error:function(data){
	    			if(data.status ==1001){
	    				layer.alert("当前操作无权限")
	    			}
	    		}
			});
		})
	}
}

function cancelBarcodeAll(){
	checkLogin();
	var barcodeIds = "";
	var isIn = 0;
	$.each($("input[name='b_checknox']:checked"),function(){
		barcodeIds += $(this).val()+"_";
		if($(this).attr("class")==1){
			isIn =1 ;
		}
	});
	if(barcodeIds == ""){
		layer.alert('请选择需要废弃的二维码');
		return false;
	}
	//判断该批量作废的条码中是否存在已经入库
	if(isIn==1){
		layer.alert('已入库的二维码不允许作废！');
		return false;
	}
	layer.confirm('确定批量作废？', function(index){
		$.ajax({
			async : false,
			url : page_url + '/logistics/warehousein/editBarcode.do',
			data : {
				"barcodeIds" : barcodeIds,
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					layer.alert("作废成功", { icon : 1},function(index){
						location.reload();
					});
				}else{
					layer.alert(data.message, { icon : 2});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}

		});
	});  
	
}
/*******退换货入库*********/
//全选
function selectallsh(obj,name){
	checkLogin();
	if($(obj).is(":checked")){
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop("checked",true);
		$("input[id='"+name+"']").not(':disabled').prop("checked",true);
		}else{
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop('checked',false);
		$("input[id='"+name+"']").not(':disabled').prop('checked',false);
			}
}
//根据日期选择
function checkbarcodesh(m,f,id){
	checkLogin();
	var flag=0;
	if(!m)	return false;
	$("input[name='"+id+"']").each(function(){
		if($(this).attr("alt") == m){
			$(this).prop('checked',f);
		}
		if(!$(this).is(":checked")){
			flag=1;
		}
	});
	if(flag==1){
		$("input[name='all_"+id+"']").prop('checked',false);
	}else{
		$("input[name='all_"+id+"']").prop('checked',true);
	}
}
function canelAllsh(obj,name) {
	checkLogin();
	var status = 0;
	$.each($("input[name='"+name+"']"), function() {
		var datestatus = 0;
		if ($(this).is(":checked") == false) {
			status = 1;
		}
		// 日期选择
		var alt = $(this).attr("alt");
		$.each($("input[alt='" + alt + "']"), function() {
			if ($(this).is(":checked") == false) {
				datestatus = 1;
			}
		})
		if (datestatus == 1) {
			$("input[name='" + alt + "']").prop('checked', false);
		} else {
			$("input[name='" + alt + "']").prop("checked", true);
		}

	});
	if (status == 1) {
		$("input[name='all_"+name+"']").prop('checked', false);
	} else {
		$("input[name='all_"+name+"']").prop("checked", true);
	}

}

function hideTh(el){
	var needHide = true;
	$('.'+el).each(function(i, ele){
		var html = $.trim($(this).html());
		if(!!html && (html != '-'  && html != '\\')){
			needHide = false;
		}
	});
	if(needHide){
		$('[cz-tab=' + el + ']').hide();
		$('.'+el).hide();
	}
};

var checkHideList = ['J-number', 'J-manufacturer','J-productCompanyLicence',
	'J-temperaTure','J-price','J-maxSkuRefundAmount','J-materialCode',
	'J-batchNumber','J-expirationDate','J-productDate','J-amount','J-model'];

$.each(checkHideList, function(i, item){
	hideTh(item);
})


function addSkuBarcode(goodsId) {
	checkLogin();
	$.ajax({
		async : false,
		url : page_url + '/logistics/warehousein/saveSkuBarcode.do',
		data : {
			goodsIds: goodsId
		},
		type : "GET",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
				layer.alert("生成成功", { icon : 1},function(index){
					location.reload();
				});
			}else{
				layer.alert(data.message, { icon : 2});
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})

}
function addAllSkuBarcode() {
	checkLogin();
	if($("input[name='b_checknox']:checked").length <= 0){
		alert('目前未选择订货号');
		return false;
	}
	var goodsIds=$("#goodsIds").val();
	$.each($("input[name='b_checknox']:checked"),function(){
		goodsIds += $(this).val()+",";
	});
	$.ajax({
		async : false,
		url : page_url + '/logistics/warehousein/saveSkuBarcode.do',
		data : {
			goodsIds: goodsIds
		},
		type : "GET",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
				layer.alert("生成成功", { icon : 1},function(index){
					location.reload();
				});
			}else{
				layer.alert(data.message, { icon : 2});
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}
function printSkuBarcode(logisticeFlag) {
	checkLogin();
	if($("input[name='b_checknox']:checked").length <= 0){
		alert('目前未选择订货号');
		return false;
	}
	if(!logisticeFlag){
		alert('非物流账户不可打印');
		return false;
	}
	var goodsIds=$("#goodsIds").val();
	$.each($("input[name='b_checknox']:checked"),function(){
			goodsIds += $(this).val()+",";
	});
	$("#goodsIds").val(goodsIds);
	$("#printSkuBarcode").submit();

}
