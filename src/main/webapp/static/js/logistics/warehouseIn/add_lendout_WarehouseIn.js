$(function(){
	if($("#warehouses  option:selected").attr("alt")){
		$("#comments").val($("#warehouses  option:selected").attr("alt"));
		//$("input[name='comments'").val($("#warehouses  option:selected").attr("alt"));
	}else{
		$("#comments").val("");
		//$("input[name='comments'").val("");
	}
	
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

var i=2;
$(document).ready(function(e) {
	var type =1;
	if($("#ywtype").val()==2  || $("#ywtype").val()==3){
		type = 2;
	}
	if($("#ywtype").val()==4){
		type = 4;
	}
	
	$("input[name='barcode']:first").focus();
	$(":focus").val('');
	$("input[type=radio]").click(function(){
		$("input[name='barcode']:first").focus();
	});

	document.onkeydown = function(e){
       if(!e){
        e = window.event;
       }
       if((e.keyCode || e.which) == 13){
        //跳到下个输入框
           $obj = $(":focus");
           if($obj.attr('name') == 'barcode'){
               var barcode = $(":focus").val();
               var total=0;
               $.each($("input[name='barcode'"),function(i,n){
           	    if($(this).val() == barcode){
           	    	total++;
               	    }
               });
               if(total > 1){
                   layer.alert('条码重复录入！');
                   $(":focus").val('');
                   return;
               }
               
               var ywGoodsId = $("#buyorderGoodsId").val();
               var afterSalesGoodsId = ywGoodsId;
               var goodsId = $("#goodsId").val();
               $.ajax({
            	    async : false,
           			url : page_url + '/logistics/warehousein/getVerifyBarcode.do',
	           		data : {
	           			"type":type,
	           			"barcode" : barcode,
	           			"buyorderGoodsId" : ywGoodsId,
	           			"afterSalesGoodsId":ywGoodsId,
	           			"goodsId" : goodsId,
	           			"detailGoodsId":ywGoodsId
	           		},
           			type : "POST",
          			dataType:'json',
          			success: function(data){
          				if(data.code == 0){
          					$(":focus").parent().parent().find("input[name='num2']").val(1);
          					$(":focus").parent().parent().find("input[name='num']").val(1);
            				var tr = '<tr>'
                				+'<td>'+i+'</td>'
            				    +'<td><input type="text" name="barcode" onchange="checkBarcode(this)" onblur="checkCF(this)"/><input type="hidden" name="barcodeId"/></td>'
            				    +'<td><input type="text" name="barcodeFactory" onchange="checkbarcodeFactory(this)"/></td>'
            				    +'<td><input type="text" name="num2" class="num2" disabled="true"/><input type="hidden" name="num" ></td>'
            				    +'<td><input type="text" name="batchNumber" onchange="checkbatchNumber(this)"/></td>'
								+'<td><input class="Wdate  input-smaller96 " name="productDate" type="text" placeholder="请选择日期" onClick="WdatePicker({minDate:\'1970-01-01\'})"></td>'
								+'<td><input  class="Wdate  input-smaller96 " type="text" name="expirationDate" value="" onfocus="WdatePicker({minDate:&#39%y-%M-%d&#39})" /></td>'
            				  +'</tr>';
            				$(":focus").parent().parent().find("input[name='barcodeId']").val(data.data.barcodeId);
          					$("#barcode_table").append(tr);
          					$(":focus").parent().next().find("input[name='barcodeFactory']").focus();
          					i++;
          				}else{
          					layer.alert(data.message);
         					$(":focus").val('');
         					$(":focus").parent().parent().find("input[name='barcodeId']").val('');
         					$(":focus").focus();
          				}
          			},
          			error:function(data){
          				if(data.status ==1001){
          					layer.alert("当前操作无权限")
          				}
          			}
          		});

          		
           }
           if($obj.attr('name') == 'barcodeFactory' || $obj.attr('name') == 'num'){
        	   $(":focus").parent().parent().next().find("input[name='barcode']").focus();
           }
           
           if($obj.attr('name') == 'batchNumber'){
        	   $(":focus").parent().parent().next().find("input[name='barcode']").focus();
           }
           
       }
      }
});
//验证贝登条码
function checkBarcode(obj){
	checkLogin();
	var type =1;
	if($("#ywtype").val()==2 || $("#ywtype").val()==3){
		type = 2;
	}
	if($("#ywtype").val()==4){
		type = 4;
	}
	var barcode = $(obj).val();
	 var ywGoodsId = $("#buyorderGoodsId").val();
     var afterSalesGoodsId = ywGoodsId;
     console.log(ywGoodsId)
    var goodsId = $("#goodsId").val();
	 $.ajax({
 	    async : false,
			url : page_url + '/logistics/warehousein/getVerifyBarcode.do',
    		data : {
    			"type":type,
    			"barcode" : barcode,
    			"buyorderGoodsId" : ywGoodsId,
       			"afterSalesGoodsId":ywGoodsId,
    			"goodsId" : goodsId,
    			"detailGoodsId":ywGoodsId
    		},
			type : "POST",
			dataType:'json',
			success: function(data){
				if(data.code == 0){
					$(obj).parent().parent().find("input[name='barcodeId']").val(data.data.barcodeId);
				}else{
					layer.alert(data.message);
					$(obj).val('');
					$(obj).parent().parent().find("input[name='barcodeId']").val('');
					$(obj).focus();
				}
				
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
	})
}

function checkbarcodeFactory(obj){
	checkLogin();
	var barcodeFactory = $(obj).val();
	var sb = true;
	if (barcodeFactory.length > 64 ){
		layer.alert("厂商条码不允许超过64个字符");
		$(obj).val("");
		$(obj).focus();
		return false;
	}
	//验证厂商条码是否已经在入库记录中存在
	$.ajax({
		type : "POST",
		url : page_url+"/logistics/warehousein/getBarcodeFactory.do",
		data : {'barcodeFactory':barcodeFactory},
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data.code == 0){
				var list = data.data;
				if(list.length>0){
					var index = layer.confirm("厂商条码（"+barcodeFactory+"）与在库商品重复，是否继续？", {
						btn : [ '确定', '取消' ]
					}, function() {
						layer.close(index);
					},function() {
						$(obj).val("");
						$(obj).focus();
					});
				}
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}
function checkbatchNumber(obj){
	checkLogin();
	if ($(obj).val().length > 64 ){
		layer.alert("批次号不允许超过64个字符");
		$(obj).val("");
		$(obj).focus();
		return false;
	}
}
function checkNum(obj){
	checkLogin();
	 var total=0;
	 var bNum = $("#bNum").val();
	 var wNum = $("#wNum").val();
	 var re = /^[0-9]+$/;
	if(!re.test($(obj).val())){
		layer.alert("入库数量必须为正整数");
		$(obj).val('');
		$(obj).focus();
		return false;
	}
     $.each($("input[name='num'"),function(i,n){
 	    if($(this).val() != ""){
 	    	total = (total)*1+($(this).val())*1
 	    }
     });
	 if((bNum*1-wNum*1)<total){
		 layer.alert("入库数量超出上限！");
		 $(obj).val('');
		 $(obj).focus();
	 }
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

//添加一行
function addWarehouseIn(){
	checkLogin();
	var tr = '<tr>'
		+'<td>'+i+'</td>'
	    +'<td><input type="text" name="barcode" id="barcode" onchange="checkBarcode(this)" onblur="checkCF(this)"/><input type="hidden" name="barcodeId"/></td>'
	    +'<td><input type="text" name="barcodeFactory"/></td>'
	    +'<td><input type="text" name="num2" class="num2" disabled="true"/><input type="hidden" name="num"></td>'
	    +'<td><input type="text" name="batchNumber" /></td>'
		+'<td><input class="Wdate  input-smaller96 " name="productDate" type="text" placeholder="请选择日期" onClick="WdatePicker({minDate:\'1970-01-01\'})"></td>'
	    +'<td><input id="txtDate" class="Wdate  input-smaller96 " type="text" name="expirationDate" value="" onfocus="WdatePicker({minDate:&#39%y-%M-%d&#39})" /></td>'
	  +'</tr>';
	i++;
	$("#barcode_table").append(tr);
}
//单个撤销
function cancelWlog(wlogIds){
	checkLogin();
	if(wlogIds > 0){
		layer.confirm('确定撤销？', function(index){
			$.ajax({
	    		async : false,
	    		url : page_url + '/logistics/warehousein/editIsEnableWlog.do',
	    		data : {
	    			"wlogIds" : wlogIds,
	    		},
	    		type : "POST",
	    		dataType : "json",
	    		success : function(data) {
	    			if(data.code == 0){
	    				layer.alert("撤销成功", { icon : 1},function(index){
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
		})
	}
}
//批量撤销
function cancelWlogAll(){
	checkLogin();
	var wlogIds = "";
	$.each($("input[name='b_checknox']:checked"),function(){
		wlogIds += $(this).val()+"_";
	});
	if(wlogIds == ""){
		layer.alert('请选择需要撤销的入库记录');
		return false;
	}
	
	layer.confirm('确定批量撤销？', function(index){
		$.ajax({
			async : false,
			url : page_url + '/logistics/warehousein/editIsEnableWlog.do',
			data : {
				"wlogIds" : wlogIds,
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					layer.alert("撤销成功", { icon : 1},function(index){
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
//根据选择存储位置更换备注
function changeComment(obj){
	checkLogin();
	if($("#warehouses  option:selected").attr("alt")){
		$("#comments").val($("#warehouses  option:selected").attr("alt"));
		//$("input[name='comments'").val($("#warehouses  option:selected").attr("alt"));
	}else{
		$("#comments").val("");
		//$("input[name='comments'").val("");
	}
	
}
function submitWarehoseIn() {
	checkLogin();
	 var total=0;addWarehouseIn
	 var bNum = $("#bNum").val();
	 var wNum = $("#wNum").val();
	 var status = 1;
	 var warehouses = $("select[name='warehouses']").val();
     $.each($("input[name='num']"),function(i,n){
 	    if($(this).val() != "" && $(this).val() != 0){
 	    	total = (total)*1+($(this).val())*1
 	    	status = 2;
 	    }
     });
     if(warehouses == "" || warehouses == null){
    	 layer.alert("存储位置不允许为空 ");
		 return false;
     }
     //验证仓库备注
 	 var comments = $("#comments").val();
 	 if(comments.length>60){
 		warnTips("comments","仓库备注不允许超过60个字");
 		return false;
 	 }
     if(status == 1){
    	 layer.alert("入库数量必须大于0！");
		 return false;
     }
	 if((bNum*1-wNum*1)<total){
		 layer.alert("入库数量超出上限！");
		 return false;
	 }
	 layer.confirm('提交后不可撤销，是否继续提交？', function(){
		$("#addWarehouseIn").submit();
	 })
}
//验证条码是否重复
function checkCF(obj){
	checkLogin();
	var value = $(obj).val();
    if( $(obj).attr('name') == 'barcode'){
        var total=0;
        $.each($("input[name='barcode']"),function(i,n){
    	    if($(this).val() == value && $(this).val().trim() != null && $(this).val().trim() != ''){
    	    	total++;
        	    }
        });
        if(total > 1){
            layer.alert('条码重复录入！');
            $(obj).val('');
            return;
        }
    }
}