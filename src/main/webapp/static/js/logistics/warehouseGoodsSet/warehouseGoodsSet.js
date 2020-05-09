$(function(){
	
	$("#wareHouseId").change(function(){
		var wareHouseId=$("#wareHouseId").val();
		var storageroomId = $("#storageroomId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/warehouses/getWarehouseByWId.do",
			data: {'warehouseId':wareHouseId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageroomId").empty();
					$("#storageAreaId").empty();
					$("#storageRackId").empty();
					$("#storageLocationId").empty();
					var storagerooms=data.data;
					var option = "<option value='0'>全部</option>";
					for(var i=0;i<storagerooms.length;i++){
						if(storageroomId !=""&&storageroomId==storagerooms[i].storageroomId){
							option +="<option value='"+storagerooms[i].storageroomId+"' selected='selected'>"+storagerooms[i].storageRoomName+"</option>";
						}else{
							option +="<option value='"+storagerooms[i].storageroomId+"'>"+storagerooms[i].storageRoomName+"</option>";
						}
					}
					$("#storageroomId").append(option);
					$("#storageAreaId").append("<option value='0'>全部</option>");
					$("#storageRackId").append("<option value='0'>全部</option>");
					$("#storageLocationId").append("<option value='0'>全部</option>");
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	})
	
	$("#storageroomId").change(function(){
		var storageroomId=$("#storageroomId").val();
		var storageareaId = $("#storageAreaId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/storageRoomMag/getStorageroomBySId.do",
			data: {'storageroomId':storageroomId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageAreaId").empty();
					$("#storageRackId").empty();
					$("#storageLocationId").empty();
					var storageareas=data.data;
					var option = "<option value='0'>全部</option>";
					for(var i=0;i<storageareas.length;i++){
						if(storageareaId !=""&&storageareaId==storageareas[i].storageareaId){
							option +="<option value='"+storageareas[i].storageAreaId+"' selected='selected'>"+storageareas[i].storageAreaName+"</option>";
						}else{
							option +="<option value='"+storageareas[i].storageAreaId+"'>"+storageareas[i].storageAreaName+"</option>";
						}
					}
					$("#storageAreaId").append(option);
					$("#storageRackId").append("<option value='0'>全部</option>");
					$("#storageLocationId").append("<option value='0'>全部</option>");
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	})
	
	$("#storageAreaId").change(function(){
		var storageAreaId=$("#storageAreaId").val();
		var storageRackId = $("#storageRackId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/storageAreaMag/getStorageRackByAId.do",
			data: {'storageAreaId':storageAreaId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageRackId").empty();
					$("#storageLocationId").empty();
					var storageRacks=data.data;
					var option = "<option value='0'>全部</option>";
					for(var i=0;i<storageRacks.length;i++){
						if(storageRackId !=""&&storageRackId==storageRacks[i].storageRackId){
							option +="<option value='"+storageRacks[i].storageRackId+"' selected='selected'>"+storageRacks[i].storageRackName+"</option>";
						}else{
							option +="<option value='"+storageRacks[i].storageRackId+"' >"+storageRacks[i].storageRackName+"</option>";
						}
					}
					$("#storageRackId").append(option);
					$("#storageLocationId").append("<option value='0'>全部</option>");
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	})
	
	$("#storageRackId").change(function(){
		var storageRackId=$("#storageRackId").val();
		var storageLocationId = $("#storageLocationId").val();
		$.ajax({
			type: "POST",
			url: page_url+"/warehouse/storageRackMag/getStorageroomByRId.do",
			data: {'storageRackId':storageRackId},
			dataType:'json',
			success: function(data){
				if(data.code==0){
					$("#storageLocationId").empty();
					var storageLocations=data.data;
					var option = "<option value='0'>全部</option>";
					for(var i=0;i<storageLocations.length;i++){
						if(storageLocationId !=""&&storageLocationId==storageLocations[i].storageLocationId){
							option +="<option value='"+storageLocations[i].storageLocationId+"' selected='selected'>"+storageLocations[i].storageLocationName+"</option>";
						}else{
							option +="<option value='"+storageLocations[i].storageLocationId+"' >"+storageLocations[i].storageLocationName+"</option>";
						}
					}
					$("#storageLocationId").append(option);
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	})
})
//单个删除
function delRecode(goodsName,levelAndId){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./delWarehouseGoods.do",
					data: {'goodsName':goodsName, 'levelAndId':levelAndId},
					dataType:'json',
					success: function(data){
						refreshNowPageList(data);
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
//批量删除
function delBatchRecode(){
	var values="";
	$("input[name='checkOne']").each(function(){
		   if($(this).is(":checked")){
			   values += $(this).val()+",";
		   }
	})
	if(values==""){
		layer.alert("请选择所要删除的数据！");
		return false;
	}
	layer.confirm("您是否确认批量删除？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./delBatchWarehouseGoods.do",
				data: {'values':values},
				dataType:'json',
				success: function(data){
					refreshNowPageList(data);
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


/**
 * @returns 导出商品库位
 */
function exportWarehouseGoodsSetlist(){
	checkLogin();
	location.href = page_url + '/report/logistics/exportwarehousegoodssetlist.do?' + $("#search").serialize();
}

/**
 * @returns 导出库位列表
 */
function exportWarehouselist(){
	checkLogin();
	location.href = page_url + '/report/logistics/exportwarehouselist.do?' + $("#search").serialize();
}