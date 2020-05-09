function search() {
	checkLogin();
	var categoryId = "";
	$("#search").find("select[name='categoryOpt']").each(function(){
//		categoryId = $(this).val()+"";
		if($(this).val()!="-1"){
			categoryId = $(this).val()+"";
		}
	});
	$("#categoryId").val(categoryId=="-1"?"":categoryId);
	var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
    $("body").prepend(div); //jq获取上一页框架的父级框架；;
	$("#search").submit();
}

function delCateAttribute(categoryAttributeId){
	checkLogin();
	if(categoryAttributeId > 0){
//		layer.confirm("删除属性可能导致商品信息丢失，是否确认删除？", {
		layer.confirm("是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./delCateAttribute.do",
					data: {'categoryAttributeId':categoryAttributeId},
					dataType:'json',
					success: function(data){
						var categoryId = "";
						$("#search").find("select[name='categoryOpt']").each(function(){
							categoryId = $(this).val()+"";
						});
						$("#categoryId").val(categoryId=="-1"?"":categoryId);
						refreshNowPageList(data);
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
			}
		);
	}
}


window.onload=function(){
	var new_parentId = $("#categoryId").val();
	if(new_parentId!=""){
		getParentCategoryList(new_parentId);
	}
}
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
					var ht = '<span id="spanId'+(level-1)+'">' +
							'<select id="categoryOpt" name="categoryOpt" style="width: 100px;">';
					ht = ht + '<option value="-1" id="'+list[0].level+'" onclick="triCategory(this)">请选择</option>';
					for(var i=0;i<list.length;i++){
						if(list[i].categoryId==categoryId){
							ht = ht + '<option value="'+list[i].categoryId+'" id="'+list[i].level+'" selected onclick="triCategory(this)">'+list[i].categoryName+'</option>';
						}else{
							ht = ht + '<option value="'+list[i].categoryId+'" id="'+list[i].level+'" onclick="triCategory(this)">'+list[i].categoryName+'</option>';
						}
						if(list[i].parentId!=0 && list[i].parentId!="0"){
							new_parentId = list[i].parentId;
						}
					}
					ht = ht + '</select></span>';
					$("#spanHtmlId").after(ht);
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

function triCategory(obj){
	checkLogin();
	$(obj).trigger('change');
	updateCategory(parent.$(obj));
}

function updateCategory(obj){
	checkLogin();
	var parentId = $(obj).val();
	var level = Number($(obj).attr("id"));
	$("span[id^='spanId']").each(function(){
		var num = $(this).attr("id").substring(6,7);
		if(num!=""){
			if(Number(num) >= level){
				$(this).remove();
			}
		}
	});
	if(parentId!="-1" && parentId!=-1){
		$("#categoryId").siblings('.warning').remove();
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
						var ht = "<span id='spanId"+level+"'>" +
								  "<select id='categoryOpt' name='categoryOpt' style='width: 100px;'>";
						ht = ht + "<option value='-1' id='"+list[0].level+"' onclick='triCategory(this)'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"' onclick='triCategory(this)'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select></span>";
						$("#spanId").append(ht);
					}else{
						$("span[id^='spanId']").each(function(){
							var num = $(this).attr("id").substring(6,7);
							if(num!=""){
								if(Number(num) >= level){
									$(this).remove();
								}
							}
						});
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
}