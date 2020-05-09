$(function() {
	var $form = $("#editCategoryForm");
	$form.submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./editCategory.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){
				var categoryName = $("#categoryName").val();
				if (categoryName.length==0) {
					warnTips("categoryName","分类名称不能为空");//文本框ID和提示用语
					return false;
				}
				if (categoryName.length > 32) {
					warnTips("categoryName","分类名称不允许超过32个字符");//文本框ID和提示用语
					return false;
				}
				var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
				if(!nameReg.test(categoryName)){
					warnTips("categoryName","分类名称不允许使用特殊字符");
					return false;
				}
				
				var parentId = $("#parentId").val().trim();
				if (parentId.length==0) {
					warnTips("parentId","请选择上级分类");//文本框ID和提示用语
					return false;
				}
				var parentId_id = $("#parentId :selected").attr("id");
				if(parentId_id!=undefined && parentId_id.indexOf($("#categoryId").val())!=-1){
					warnTips("parentId","不允许选中子级分类");//文本框ID和提示用语
					return false;
				}
				var par_level = Number($("#parentId :selected").attr("alt"));//新父级层级
				if (par_level>=3) {
					warnTips("parentId","分类层级不允许超过三级");//文本框ID和提示用语
					return false;
				}else{
					var level = Number($("#level").val());
					var max_level = 0;
					if(level == 2){//自己原本是2级
						max_level = 4;//可放在2级目录或一级或顶级下
					}else if(level == 1){
						max_level = 3;//最多放到2级目录下
					}else if(level == 3){//原本自己是3级
						max_level = 5;//最多放在2级下
					}
					if((level + par_level) > max_level){//新父级与自己原有级别相加不能超过4
						warnTips("parentId","分类层级不允许超过三级");//文本框ID和提示用语
						return false;
					}
				}
				
				/*var sort = $("#sort").val().trim();
				if (sort.length==0) {
					warnTips("sort","排序编号不能为空");//文本框ID和提示用语
					return false;
				}else{
					var re = /^[0-9]+$/ ;
					if(!re.test(sort)){
						warnTips("sort","排序编号必须为正整数");//文本框ID和提示用语
						return false;
					}
				}*/
				waitWindow("No");
			},
			success:function(data){
				refreshNowPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
	});
	
	var categoryId = $("#parentId").val();
	if(categoryId!=0 && categoryId!="0"){
		getParentCategoryList(categoryId);
	}
});



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
					var ht = '<select id="categoryOpt" name="categoryOpt" style="width: 100px;" onchange="updateCategory(this);">';
					if(list[0].level == 1){
						ht = ht + '<option value="0" id="0">顶级</option>';
					}else{
						ht = ht + '<option value="-1" id="'+list[0].level+'">请选择</option>';
					}
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



function updateCategory(obj){
	checkLogin();
	var parentId = $(obj).val();
	var level = Number($(obj).find("option:checked").attr("id"));
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
					if(list!=null && list.length>0 && parentId!=0){
						if(list[0].level <3){
						var ht = "<select id='categoryOpt' name='categoryOpt' style='width: 100px;' onchange='updateCategory(this);'>";
						ht = ht + "<option value='-1' alt='"+list[0].level+"' pid='"+list[0].parentId+"'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].categoryId+"' alt='"+list[i].level+"' pid='"+list[0].parentId+"'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select>";
						$("#category_div").find("select:gt("+$(obj).index()+")").remove();
						$("#category_div").append(ht);
						}
					}else{
						$("#category_div").find("select:gt("+$(obj).index()+")").remove();
					}
						$("#parentId").val(parentId);
						var level = $(obj).find("option:checked").attr("alt");
						$("#parentId").attr("alt",level);
					
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
		$("#parentId").val($(obj).find("option:checked").attr("pid"));
		$("#parentId").attr("alt",$(obj).find("option:checked").attr("alt")-1);
		$("#category_div").find("select:gt("+$(obj).index()+")").remove();
	}
}