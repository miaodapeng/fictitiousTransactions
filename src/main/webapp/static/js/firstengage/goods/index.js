$(function() {
	var categoryId = $("#categoryId").val();
	if(categoryId!=0 && categoryId!="0"){
		$("#category_div").html('');
		getParentCategoryList(categoryId);
	}
	
	var standardCategoryId = $("#standardCategoryId").val();
	if(standardCategoryId!=0 && standardCategoryId!="0"){
		$("#standard_category_div").html('');
		getParentStandardCategoryList(standardCategoryId);
	}
	
});

//复制产品
function copyGoods(goodsName,goodsId){
	layer.confirm("您是否确认复制该产品？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			checkLogin();
			var sb =true;
			//商品名称是否存在
			$.ajax({
				type : "POST",
				url : page_url+"/goods/goods/validgoodsname.do",
				data : {goodsName:goodsName},
				dataType : 'json',
				async : false,
				success : function(data) {
					if(data.code == 1){
						sb = false;
						return false;
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			if(!sb){
				layer.alert("商品名称不允许重复");
				return false;
			}else{
				//开始复制产品
				$.ajax({
					type : "POST",
					url : page_url+"/goods/goods/copygoodsinfo.do",
					data : {goodsId:goodsId},
					dataType : 'json',
					async : false,
					success : function(data) {
						if(data.code == 0){
							var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
					        var newPageId;
					        var id = "viewgoods"+data.data;
					        var name = '商品信息';
					        var uri = "./goods/goods/viewbaseinfo.do?goodsId="+data.data;
					        var closable = 1;
					        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
					        search();
					        self.parent.closableTab.addTab(item);
					        self.parent.closableTab.resizeMove();
					        $(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
						}else{
							layer.alert(data.msg);
						}
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
			}
		}, function(){
	});
	
}

function discardEnable(goodsId){
	checkLogin();
	if(goodsId > 0){
		layer.confirm("您是否确认启用？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./isDiscardById.do",
					data: {'goodsId':goodsId, 'isDiscard':0},
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
}

var new_parentId= "";
function getParentCategoryList(categoryId){
	checkLogin();
	$.ajax({
		async:false,
		url:page_url + '/goods/category/getParentCateList.do',
		data:{"categoryId":categoryId},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				var list = data.listData;
				if(list!=null && list.length>0){
					new_parentId = "";
					var level = Number(list[0].level);
					var ht = '<select id="categoryOpt" name="categoryOpt" style="width: 100px;" onchange="updateCategory(this,\'search\');">';
					ht = ht + '<option value="-1" id="'+list[0].level+'">请选择</option>';
					for(var i=0;i<list.length;i++){
						if(list[i].categoryId==categoryId){
							ht = ht + '<option value="'+list[i].categoryId+'" id="'+list[i].level+'" selected >'+list[i].categoryName+'</option>';
						}else{
							ht = ht + '<option value="'+list[i].categoryId+'" id="'+list[i].level+'" >'+list[i].categoryName+'</option>';
						}
						if(list[i].parentId!=0 && list[i].parentId!="0"){
							new_parentId = list[i].parentId;
						}
					}
					ht = ht + '</select>';
					$("#category_div").prepend(ht);
					if(new_parentId!=""){
						getParentCategoryList(new_parentId);
					}
				}
			}else{
				layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
				return false;
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

var standard_new_parentId= "";
function getParentStandardCategoryList(standardCategoryId){
	checkLogin();
	$.ajax({
		async:false,
		url:page_url + '/goods/category/getParentStandardCateList.do',
		data:{"standardCategoryId":standardCategoryId},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				var list = data.listData;
				if(list!=null && list.length>0){
					standard_new_parentId = "";
					var level = Number(list[0].level);
					var ht = '<select id="standardCategoryOpt" name="standardCategoryOpt" style="width: 100px;" onchange="updateStandardCategory(this,\'search\');">';
					ht = ht + '<option value="-1" id="'+list[0].level+'">请选择</option>';
					for(var i=0;i<list.length;i++){
						if(list[i].standardCategoryId==standardCategoryId){
							ht = ht + '<option value="'+list[i].standardCategoryId+'" id="'+list[i].level+'" selected >'+list[i].categoryName+'</option>';
						}else{
							ht = ht + '<option value="'+list[i].standardCategoryId+'" id="'+list[i].level+'" >'+list[i].categoryName+'</option>';
						}
						if(list[i].parentId!=0 && list[i].parentId!="0"){
							standard_new_parentId = list[i].parentId;
						}
					}
					ht = ht + '</select>';
					$("#standard_category_div").prepend(ht);
					if(standard_new_parentId!=""){
						getParentStandardCategoryList(standard_new_parentId);
					}
				}
			}else{
				layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
				return false;
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

function updateCategory(obj,type){
	checkLogin();
	var parentId = $(obj).val();
	var level = Number($(obj).find("option:checked").attr("id"));
	if(parentId!="-1" && parentId!=-1){
		$.ajax({
			async:false,
			url:page_url + '/goods/category/getCategoryList.do',
			data:{"parentId":parentId},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var list = data.listData;
					if(list!=null && list.length>0){
						$("#categoryId").val(0);
						var ht = "<select id='categoryOpt' name='categoryOpt' style='width: 100px;' onchange='updateCategory(this,\""+type+"\");'>";
						ht = ht + "<option value='-1' id='"+list[0].level+"'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select>";
						//$("#category_div").append(ht);
						$(obj).parent().find("select:gt("+$(obj).index()+")").remove();
						$(obj).parent().append(ht);
					}else{
						if (type == 'search') {
							$("#categoryId").val(parentId);
						} else if (type == 'change') {
							$("#changeCategoryId").val(parentId);
						}
						//$("#category_div").find("select:gt("+$(obj).index()+")").remove();
						$(obj).parent().find("select:gt("+$(obj).index()+")").remove();
					}
				}else{
					layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
					return false;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	} else {
		if (type == 'search') {
			$("#categoryId").val(0);
		} else if (type == 'change') {
			$("#changeCategoryId").val(0);
		}
		//$("#category_div").find("select:gt("+$(obj).index()+")").remove();
		$(obj).parent().find("select:gt("+$(obj).index()+")").remove();
	}
}

function updateStandardCategory(obj,type){
	checkLogin();
	var parentId = $(obj).val();
	var level = Number($(obj).find("option:checked").attr("id"));
	if(parentId!="-1" && parentId!=-1){
		$.ajax({
			async:false,
			url:page_url + '/goods/category/getStandardCategoryList.do',
			data:{"parentId":parentId},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var list = data.listData;
					if(list!=null && list.length>0){
						$("#standardCategoryId").val(0);
						var ht = "<select id='standardCategoryOpt' name='standardCategoryOpt' style='width: 100px;' onchange='updateStandardCategory(this,\""+type+"\");'>";
						ht = ht + "<option value='-1' id='"+list[0].level+"'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].standardCategoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select>";
						//$("#category_div").append(ht);
						$(obj).parent().find("select:gt("+$(obj).index()+")").remove();
						$(obj).parent().append(ht);
					}else{
						if (type == 'search') {
							$("#standardCategoryId").val(parentId);
						}
						//$("#category_div").find("select:gt("+$(obj).index()+")").remove();
						$(obj).parent().find("select:gt("+$(obj).index()+")").remove();
					}
				}else{
					layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
					return false;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	} else {
		$("#standardCategoryId").val("");
		if (type == 'search') {
			$("#categoryId").val(0);
		} else if (type == 'change') {
			$("#changeCategoryId").val(0);
		}
		//$("#category_div").find("select:gt("+$(obj).index()+")").remove();
		$(obj).parent().find("select:gt("+$(obj).index()+")").remove();
	}
}

function batchOptSubmit() {
	checkLogin();
	var num = 0;
	var goodsIdArr = [];
	$("input[name='checkOne']").each(function(){
		if($(this).is(':checked')){
			goodsIdArr.push($(this).val());
			num++;
		}
	});
	if(num==0){
		layer.alert("请先选择产品");
		return false;
	}
	
	var changeCategoryId = $("#changeCategoryId").val();
	if (changeCategoryId == 0) {
		layer.alert("请选择至最小分类");
		return false;
	}
	
	layer.confirm("您是否确认操作？", 
		{ btn: ['确定','取消']}, 
		function(){
			$.ajax({
				async:false,
				url:'./batchOptGoods.do',
				data:{"changeCategoryId":$("#changeCategoryId").val(),"goodsIdArr":JSON.stringify(goodsIdArr)},
				type:"POST",
				dataType : "json",
				success:function(data){
					if(data.code==0){
						layer.alert(data.message, 
							{icon:1},
							function () {
								window.location.reload();
							}
						);
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
		});
}

function exportGoodsList(){
	checkLogin();
//	location.href = page_url + '/goods/goods/exportgoodslist.do?' + $("#search").serialize();
	location.href = page_url + '/report/supply/exportGoodsList.do?' + $("#search").serialize();
}

function updateIsRecommed(goodsId,isRecommed){
	$.ajax({
		async:true,
		url:'./updateGoodsInfoById.do',
		data:{"goodsId":goodsId,"isRecommed":isRecommed},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				layer.alert(data.message, 
					{icon:1}
				);
				if(isRecommed == 0){
					$("#isRecommed_"+goodsId).html("否&nbsp;&nbsp;<span class='edit-user' onclick='updateIsRecommed("+goodsId+",1);'>设为主推</span>");
				}else{
					$("#isRecommed_"+goodsId).html("<font color='red'>是</font>&nbsp;&nbsp;<span class='edit-user' onclick='updateIsRecommed("+goodsId+",0);'>取消主推</span>");
				}
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
}