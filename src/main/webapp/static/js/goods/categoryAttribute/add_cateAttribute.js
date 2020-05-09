$(function() {
	var $form = $("#addCateAttributeForm");
	$form.submit(function() {
		checkLogin();
		var categoryId = "";
		$form.find("select[name='categoryOpt']").each(function(){
			categoryId = $(this).val();
		});
		$("#categoryId").val(categoryId);
		
		$("#isSelected").val($("input[name='isSelectedRad']:checked").val());
		$("#isFilter").val($("input[name='isFilterRad']:checked").val());
		
		$.ajax({
			async:false,
			url:'./saveCateAttribute.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){  
				var categoryAttributeName = $("#categoryAttributeName").val();
				if (categoryAttributeName.length==0) {
					warnTips("categoryAttributeName","属性名称不能为空");//文本框ID和提示用语
					return false;
				}
				if (categoryAttributeName.length<1 || categoryAttributeName.length >32) {
					warnTips("categoryAttributeName","属性名称长度应该在1-32个字符之间");//文本框ID和提示用语
					return false;
				}
				var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
				if(!nameReg.test(categoryAttributeName)){
					warnTips("categoryAttributeName","属性名称不允许使用特殊字符");
					return false;
				}
				if(categoryId == 0 || categoryId=="0" || categoryId==-1 || categoryId=="-1"){
					warnTips("categoryId","请选择产品分类");
					return false;
				}
				
				var inputType = $("#inputType").val();
				if(inputType==-1 || inputType=="-1"){
					warnTips("inputType","请选择数据类型");
					return false;
				}
			},
			success:function(data){
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
	});
});

function updateCategory(obj){
	checkLogin();
	var parentId = $(obj).val();
	var level = Number($(obj).find("option:checked").attr("id"));
//	if(parentId=="-1" || parentId==-1){
		for(var i=level;i<10;i++){
			$("#spanId"+i).remove();
		}
//	}
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
						var ht = "<span id='spanId"+level+"' class='mr10'>" +
								  "<select id='categoryOpt' name='categoryOpt' style='width: 100px;' onchange='updateCategory(this);'>";
						ht = ht + "<option value='-1' id='"+list[0].level+"'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select></span>";
						$("#spanId").append(ht);
					}else{
						for(var i=level;i<5;i++){
							$("#spanId"+i).remove();
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
}