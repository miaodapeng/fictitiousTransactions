// 新国标分类  加载
function loadStandardCategory(obj, type)
{
	checkLogin();
	$("#stand_span_id").addClass("none");
	// 清空中间值
	$("#input_center_val").val("");
	$("#new_standar_input").val("");
	var parentId = $(obj).val();
	var level = Number($(obj).find("option:checked").attr("id"));
	if(level == 1)
	{
		$("#new_stand_div_show_2_div").remove();
		$("#new_stand_div_show_3_div").remove();
	}
	else if(level == 2)
	{
		$("#new_stand_div_show_3_div").remove();
	}
	// 搜索标志
	if(type == 2)
	{
		$("#input_stand_center_name_val_one").val("");
		$("#input_stand_center_name_val_two").val("");
		$("#input_stand_center_name_val_three").val("");
	}
	var name = $(obj).find("option:checked").text();
	parentId = parentId[0];
	managerCategoryLevel(parentId);//判断是否显示管理类别

	if(parentId !="-1" && parentId != -1)
	{
		$("#standardCategoryId").siblings('.warning').remove();
		$.ajax({
			async:false,
			url: page_url + '/goods/category/getStandardCategoryList.do',
			data:{"parentId": parentId },
			type:"POST",
			dataType : "json",
			success:function(data)
			{
				if(data.code == 0)
				{
					var list = data.listData;
					if(list != null && list.length > 0)
					{
						// 先设置0
						$("#standardCategoryId").val(0);
						if(level == 1)
						{
							if('' != name && undefined != name && 'undefined' != name)
							{							
								$("#input_stand_center_name_val_one").val(name);
							}
							$("#input_stand_center_name_val_one").val(name);
							$("#input_stand_center_name_val_two").val("");
							$("#input_stand_center_name_val_three").val("");
							// 二级
							$("#new_stand_div_show_2_div").remove();
							var ht = "<div id='new_stand_div_show_2_div'> <div class='text' style='visibility: visible;'><p><span class='mb5'>二级分类</span></p></div>";
							ht += '<select style="display:block;height:108px;padding:4px 2px;" multiple="multiple" onclick="loadStandardCategory(this, 1);" >';
							for(var i=0; i<list.length; i++)
							{
								ht = ht + "<option value='"+list[i].standardCategoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
							}
							ht = ht + "</select></div>";
							$("#new_stand_div_show_2").append(ht);
						}
						else if(level == 2)
						{
							if('' != name && undefined != name && 'undefined' != name)
							{							
								$("#input_stand_center_name_val_two").val(name);
							}
							$("#input_stand_center_name_val_three").val("");
							// 三级
							$("#new_stand_div_show_3_div").remove();
							var ht = "<div id='new_stand_div_show_3_div'><div class='text' style='visibility: visible;'><p><span class='mb5'>三级分类</span></p></div>";
							ht += '<select style="display:block;height:108px;padding:4px 2px;" multiple="multiple" onclick="loadStandardCategory(this, 1);" >';
							for(var i=0; i<list.length; i++)
							{
								ht = ht + "<option value='"+list[i].standardCategoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
							}
							ht = ht + "</select></div>";
							$("#new_stand_div_show_3").append(ht);
						}
						
//						$("#standard_category_div").find("select:gt("+$(obj).index()+")").remove();
					}
					// 查询不到子级
					else
					{
						// 设置值
						$("#standardCategoryId").val(parentId);
						$("#input_center_id_val").val(parentId);
						$("#standard_category_div").find("select:gt("+$(obj).index()+")").remove();
						
						if('' != name && undefined != name && 'undefined' != name)
						{							
							$("#input_stand_center_name_val_three").val(name);
						}
						// 显示 确定按钮 
//						$("#stand_span_id").removeClass("none");
					}
				}
				else
				{
					layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
					return false;
				}
			},
			error:function(data)
			{
				if(data.status ==1001)
				{
					layer.alert("当前操作无权限")
				}
			}
		})
	} 
	else 
	{
		$("#standardCategoryId").val(0);
		$("#standard_category_div").find("select:gt("+$(obj).index()+")").remove();
	}
}
//搜索 新国标分类
function searchStandardCategory(id)
{
	// 设置值 0
	$("#standardCategoryId").val(0);
	var searchVar = $("#"+id).val();
	$("#new_stand_div_show_1_div").addClass("none");
	$("#new_stand_div_show_2_div").remove();
	$("#new_stand_div_show_3_div").remove();
	$("#new_stand_div_show_1_div_search").remove();
	$("#stand_span_id").addClass("none");
	if('' == searchVar)
	{
		$("#new_stand_div_show_1_div").removeClass("none");
		// 空搜索，置空
		// 清除选中状态
		$("select[name='standardCategory_Opt']").val("");
		return;
	}
	
	// 
	$.ajax({
		async:false,
		url: page_url + '/goods/category/getStandardCategoryList.do',
		data:{"categoryName": searchVar, interfaceType : 1 },
		type:"POST",
		dataType : "json",
		success:function(data)
		{
			if(data.code == 0)
			{
				var list = data.listData;
				if(list != null && list.length > 0)
				{					
					var ht = "<div id='new_stand_div_show_1_div_search'> <div class='text' style='visibility: visible;'><p><span class='mb5'>搜索列表</span></p></div>";
					ht += '<select style="display:block;height:108px; padding:4px 2px;" multiple="multiple" onclick="loadStandardCategory(this, 2);" >';
					for(var i=0; i<list.length; i++)
					{
						ht = ht + "<option value='"+list[i].standardCategoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
					}
					ht = ht + "</select></div>";
					$("#new_stand_div_show_1").append(ht);
				}
				else
				{
					layer.alert("搜索结果为空，请重新输入其他搜索条件", { icon: 2 });
				}
			}
			else
			{
				layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
				return false;
			}
		},
		error:function(data)
		{
			if(data.status ==1001)
			{
				layer.alert("当前操作无权限")
			}
		}
	});
	
}
// 新国标分类 确定
function sureStandOk(id)
{
	var standId = $("#input_center_id_val").val();
	$("#standardCategoryId").val(standId);
	
	var name_1 = $("#input_stand_center_name_val_one").val();
	var name_2 = $("#input_stand_center_name_val_two").val();
	var name_3 = $("#input_stand_center_name_val_three").val();
	var name = '';
	if('' != name_1)
	{
		name += name_1 + '->';
	}
	if('' != name_2)
	{
		name += name_2 + '->';
	}
	if('' != name_3)
	{
		name += name_3;
	}
	// 设置值
	$("#" + id).val(name);
	
	// 确定按钮
	$("#stand_span_id").addClass("none");
	// 隐藏
	$("#new_stand_div_show_1_div").addClass("none");
	$("#new_stand_div_show_1_div_search").remove();
	$("#new_stand_div_show_2_div").remove();
	$("#new_stand_div_show_3_div").remove();
}


/**
 * 
 * 商品运营分类 
 * @param obj
 * @param level
 * @returns
 */
function loadCategory(obj, type)
{
	checkLogin();
	// 按钮
	$("#category_span_id").addClass("none");
	
	$("#category_inpu_id").val("");
	var parentIds = $(obj).val();
	var pId = parentIds[0];
	// 搜索标志
	if(type == 2)
	{
		$("#input_category_center_name_val_one").val("");
		$("#input_category_center_name_val_two").val("");
		$("#input_category_center_name_val_three").val("");
	}
	var level = Number($(obj).find("option:checked").attr("id"));
	// 名称
	var name = $(obj).find("option:checked").text();
	if(level == 1)
	{
		$("#new_category_div_show_2_div").remove();
		$("#new_category_div_show_3_div").remove();
	}
	else if(level == 2)
	{
		$("#new_category_div_show_3_div").remove();
	}
	if(pId != "-1" && pId != -1 && pId != undefined && 'undefined' != pId && pId != '')
	{
		$("#categoryId").siblings('.warning').remove();
		$.ajax(
		{
			async:false,
			url:page_url + '/goods/category/getCategoryList.do',
			data:{"parentId": pId},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code == 0)
				{
					var list = data.listData;
					if(list !=null && list.length > 0)
					{
						$("#categoryId").val(0);
						if(level == 1)
						{
							if('' != name && undefined != name && 'undefined' != name)
							{							
								$("#input_category_center_name_val_one").val(name);
							}
							$("#input_categor_center_name_val_two").val("");
							$("#input_categor_center_name_val_three").val("");
							// 二级
							var ht = "<div id='new_category_div_show_2_div'> <div class='text' style='visibility: visible;'><p><span class='mb5'>二级分类</span></p></div>";
							ht += '<select id="categoryOpt" name="categoryOpt" style="display:block;height:108px;padding:4px 2px;" multiple="multiple" onclick="loadCategory(this);" >';
							for(var i=0; i<list.length; i++)
							{
								ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
							}
							ht = ht + "</select></div>";
							$("#new_category_div_show_2").append(ht);
						}
						else if(level == 2)
						{
							if('' != name && undefined != name && 'undefined' != name)
							{							
								$("#input_categor_center_name_val_two").val(name);
							}
							$("#input_categor_center_name_val_three").val("");
							// 三级
							var ht = "<div id='new_category_div_show_3_div'><div class='text' style='visibility: visible;'><p><span class='mb5'>三级分类</span></p></div>";
							ht += '<select id="categoryOpt" name="categoryOpt" style="display:block;height:108px;padding:4px 2px;" multiple="multiple" onclick="loadCategory(this, 1);" >';
							for(var i=0; i<list.length; i++)
							{
								ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
							}
							ht = ht + "</select></div>";
							$("#new_category_div_show_3").append(ht);
						}
						
						$("#category_div").find("select:gt("+$(obj).index()+")").remove();
//						$("#category_div").append(ht);
					}
					else
					{
						$("#categoryId").val(pId);
						$("#input_center_id_val").val(pId);
						$("#category_div").find("select:gt("+$(obj).index()+")").remove();
						// 展示 按钮
//						$("#category_span_id").removeClass("none");
						
						if('' != name && undefined != name && 'undefined' != name)
						{							
							$("#input_categor_center_name_val_three").val(name);
						}
						//获取最小分类对应的属性列表数据
						$.ajax({
							async:false,
							url:page_url + '/goods/categoryattribute/getAttributeByCategoryId.do',
							data:{"categoryId":pId},
							type:"POST",
							dataType : "json",
							success:function(attr_data){
								if(attr_data.code==0){
									var attr_list = attr_data.listData;
									if(attr_list!=null && attr_list.length>0){
										var attribute_list_html = '';
										var require_attr_id_str = '';
										var attr_id_str = '';
										for (var attr_i = 0; attr_i < attr_list.length; attr_i++) {
											attribute_list_html += '<div class="infor_name mt0">';
											if (attr_list[attr_i].isSelected == 1) {
												attribute_list_html += '<span>*</span> ';
												require_attr_id_str += attr_list[attr_i].categoryAttributeId + '_';
											}
											attr_id_str += attr_list[attr_i].categoryAttributeId + '_';
											attribute_list_html += '<label id="category_attribute_name_' + attr_list[attr_i].categoryAttributeId + '">'+attr_list[attr_i].categoryAttributeName+'</label></div>';
											if (attr_list[attr_i].categoryAttributeValue!=null && attr_list[attr_i].categoryAttributeValue.length>0) {
												attribute_list_html += '<div class="f_left inputfloat"><ul>';
												for (var attr_v_i = 0; attr_v_i < attr_list[attr_i].categoryAttributeValue.length; attr_v_i++) {
													attribute_list_html += '<li>';
													if (attr_list[attr_i].inputType == 0) {//固定值单选
														attribute_list_html += '<input type="radio" name="categoryAttributeId_'+attr_list[attr_i].categoryAttributeId+'" class="category_attribute_'+attr_list[attr_i].categoryAttributeId+'" value="'+attr_list[attr_i].categoryAttributeId+'_'+attr_list[attr_i].categoryAttributeValue[attr_v_i].categoryAttributeValueId+'" />';
													} else if (attr_list[attr_i].inputType == 1) {//固定值复选
														attribute_list_html += '<input type="checkbox" name="categoryAttributeId" class="category_attribute_'+attr_list[attr_i].categoryAttributeId+'" value="'+attr_list[attr_i].categoryAttributeId+'_'+attr_list[attr_i].categoryAttributeValue[attr_v_i].categoryAttributeValueId+'" />';
													}
				                                	attribute_list_html += '<label>'+attr_list[attr_i].categoryAttributeValue[attr_v_i].attrValue+'</label></li>';
												}
												attribute_list_html += '<li id="category_attribute_'+attr_list[attr_i].categoryAttributeId+'"></li></ul></div>';
											}
											attribute_list_html += '<div class="clear"></div>';
										}
										$("#require_attr_id_str").val(require_attr_id_str);
										$("#attr_id_str").val(attr_id_str);
										$("#attribute_list_div").html(attribute_list_html);
										$("#attribute_list_div").removeClass("none");
										$("#attribute_list_div").addClass("line");
									} else {
										$("#attribute_list_div").html('');
										$("#require_attr_id_str").val('');
										$("#attr_id_str").val('');
										$("#attribute_list_div").removeClass("line");
										$("#attribute_list_div").addClass("none");
									}
								}else{
									layer.alert("获取对应分类属性信息失败，请稍后重试或联系管理员！");
									return false;
								}
							}
						})
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
		$("#categoryId").val(0);
		$("#category_div").find("select:gt("+$(obj).index()+")").remove();
		$("#attribute_list_div").html('');
		$("#attribute_list_div").removeClass("line");
		$("#attribute_list_div").addClass("none");
	}
}

//搜索 商品运营分类
function searchCategoryById(id)
{
	$("#categoryId").val(0);
	var valS = $("#"+id).val();
	// 隐藏
	$("#new_category_div_show_1_div").addClass("none");
	$("#new_category_div_show_2_div").remove();
	$("#new_category_div_show_3_div").remove();
	
	$("#new_category_div_show_1_div_search").remove();
	// 按钮
	$("#category_span_id").addClass("none");
	
	if('' == valS)
	{		
		$("#new_category_div_show_1_div").removeClass("none");
		// 重置
//		var opt_text = $("select[name='category_Opt']").find("option:selected").text();
//		$("select[name='category_Opt']").find("option:selected").attr("selected", false);
		// 清除选中状态
		$("select[name='category_Opt']").val("");
//		$("select[name='category_Opt']").prop("checked", false);
		return;
	}
	// 
	$.ajax({
		async:false,
		url: page_url + '/goods/category/getCategoryList.do',
		data:{"categoryName": valS, interfaceType : 1 },
		type:"POST",
		dataType : "json",
		success:function(data)
		{
			if(data.code == 0)
			{
				var list = data.listData;
				var ht = '';
				if(list != null && list.length > 0)
				{					
					ht = "<div id='new_category_div_show_1_div_search'> <div class='text' style='visibility: visible;'><p><span class='mb5'>搜索列表</span></p></div>";
					ht += '<select style="display:block;height:108px; padding:4px 2px;" multiple="multiple" onclick="loadCategory(this, 2);" >';
					for(var i=0; i<list.length; i++)
					{
						ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
					}
					ht = ht + "</select></div>";
					$("#new_category_div_show_1").append(ht);
				}
				else
				{
					layer.alert("搜索结果为空，请重新输入其他搜索条件", { icon: 2 });	
				}
				
			}
			else
			{
				layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
				return false;
			}
		},
		error:function(data)
		{
			if(data.status ==1001)
			{
				layer.alert("当前操作无权限")
			}
		}
	});
	
}
// 商品运营分类 确认
function sureCategoryOkById(id)
{
	var categoryId = $("#input_center_id_val").val();
	$("#categoryId").val(categoryId);
	
	var name_1 = $("#input_category_center_name_val_one").val();
	var name_2 = $("#input_categor_center_name_val_two").val();
	var name_3 = $("#input_categor_center_name_val_three").val();
	var name = '';
	if('' != name_1 && 'undefined' != name_1)
	{
		name += name_1 + '->';
	}
	if('' != name_2 && 'undefined' != name_2)
	{
		name += name_2 + '->';
	}
	if('' != name_3 && 'undefined' != name_3)
	{
		name += name_3;
	}
	// 设置值
	$("#" + id).val(name);
	
	// 确定按钮
	$("#category_span_id").addClass("none");
	// 隐藏
	$("#new_category_div_show_1_div").addClass("none");
	$("#new_category_div_show_1_div_search").remove();
	$("#new_category_div_show_2_div").remove();
	$("#new_category_div_show_3_div").remove();
}

//判断商品的不可以为空也不可以以*开头
function checkProductName() {
    var goodsName = $.trim($("#goodsName").val());

    if(goodsName == ""){
        warnTips("goodsName","商品名称不允许为空");
        return false;
    }

    var fdStart = goodsName.indexOf("*");
    if( fdStart == 0 ){
        warnTips("goodsName","产品名称第一位不允许为特殊字符*");
        return false;
    }
	
    if (goodsName != "") {
		$(".warning").remove();
        $("#goodsName").removeClass('errorbor');
	}

}