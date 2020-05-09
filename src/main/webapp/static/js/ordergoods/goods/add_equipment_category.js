function search2() {
	checkLogin();
	if($("#searchContent").val()==undefined || $("#searchContent").val()==""){
		layer.alert("查询条件不能为空");//文本框ID和提示用语
		return false;
	}
	$("#searchContent").val($("#searchContent").val().trim());
	$("#search").submit();
}

function selectGoods(goodsId,sku,goodsName,brandName,model,unitName){
	checkLogin();
	//验证该报价下是否存在重复产品
	$.ajax({
		async:false,
		url:'./isexitgoodscategroy.do',
		data:{"ordergoodsStoreId":$("#ordergoodsStoreId").val(),"goodsId":goodsId},
		type:"GET",
		dataType : "json",
		success:function(data){
			if(data.code == 0){
				//操作显示/隐藏区域
				$("#goodsListDiv").hide();
				$("#confirmGoodsDiv").show();
				
				//赋值
				$("#confirmGoodsDiv").find("#confirmGoodsName").html(goodsName);
				$("#confirmGoodsDiv").find("#confirmGoodsName").attr("tabTitle",'{"num":"viewgoods'+goodsId+'","link":"'+page_url+'/goods/goods/viewbaseinfo.do?goodsId='+goodsId+'","title":"产品信息"}');
				$("#confirmGoodsDiv").find("#confirmGoodsBrandNameModel").html(brandName+"/"+model);
				
				$("#confirmGoodsDiv").find("#confirmGoodsContent").html(sku);
				$("#confirmGoodsDiv").find("#confirmUnitName").html(unitName);
				
				$("#confirmGoodsDiv").find("#goodsId").val(goodsId);
			}else{
				layer.alert("设备已经存在，不允许重复添加", 
					{ icon: 2 },
					function (index) {
						layer.close(index);
					}
				);
			}
		}
	})
	
}

function againSearch(){
	checkLogin();
	$(".formpublic").find("input[type=text]").val("");
	$("#goodsListDiv").show();
	$("#confirmGoodsDiv").hide();
}

function confirmSubmit(){
	checkLogin();
	var ordergoodsStoreId = $("#ordergoodsStoreId").val();
	var goodsId = $("#goodsId").val();
	
	if(ordergoodsStoreId == '' && goodsId == ""){
		layer.alert("操作失败，请刷新后重试！");
		return false;
	}
	if($("input[name='categoryId']:checked").length == 0){
		layer.alert("对应的试剂分类不允许为空");
		return false;
	}
	
	var ordergoodsCategoryIds = "";
	$.each($("input[name='categoryId']:checked"),function(i,n){
		ordergoodsCategoryIds += $(this).val()+",";
	});
	$.ajax({
		async:false,
		url:'./saveaddequipmentcategory.do',
		data:{ordergoodsStoreId:ordergoodsStoreId,goodsId:goodsId,ordergoodsCategoryIds:ordergoodsCategoryIds},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				window.parent.location.reload();
			}else{
				layer.alert(data.message);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}

