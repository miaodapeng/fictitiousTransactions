var num = 999;
function addCateValue(){
	var ht = '<li>'+
				'<div id="liId'+num+'">'+
					'<input type="hidden" id="dataType" name="dataType" value="insert"/>'+
					'<input type="hidden" id="categoryAttributeValueId" name="categoryAttributeValueId">'+
					'<span class="font-red mr4">*</span>'+
					'<label class="mr4">值</label>'+
					'<input type="text"  class="input-middle mr10" name="attrValue" id="'+num+'">'+
					'<label class="mr4">排序</label>'+
					'<input type="text"  class="input-smaller mr3" value="100" name="sort" id="'+num+'">'+
					'<span class="deleattribute" onclick="delUl(this)" style="margin-left:7px;">删除</span>'+
				'</div>'+
			'</li>';
	$('.zhi').append(ht);
	num ++;
}
function delUl(obj){
	checkLogin();
	if($(obj).parent().find("#categoryAttributeValueId")!=undefined && $(obj).parent().find("#categoryAttributeValueId").val()!=undefined && $(obj).parent().find("#categoryAttributeValueId").val()!=""){
		$(obj).parent().find("#dataType").val("del");
		$(obj).parent().parent().hide();
	}else{
		$(obj).parent().parent().remove();
	}
}

$(function() {
	var $form = $("#editCateAttributeValueForm");

	$form.submit(function() {
		checkLogin();
		var flag = true;
		var attrValueArr = [];var attrValue = "";var value_id = "";
		$form.find("input[name='attrValue']").each(function(i){
			attrValue = $(this).val().trim(); value_id = $(this).attr("id");
			if (attrValue.length==0) {
				warnTips("liId"+value_id,"属性值名称不允许为空");//文本框ID和提示用语
				flag = false;
				return flag;
			}
			if (attrValue.length<1 || attrValue.length >100) {
				warnTips("liId"+value_id,"属性值名称长度应该在1-100个字符之间");//文本框ID和提示用语
				flag = false;
				return flag;
			}
/*			var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
			if(!nameReg.test(attrValue)){
				warnTips("liId"+value_id,"属性值名称不允许使用特殊字符");
				flag = false;
				return flag;
			}*/
			if($.inArray(attrValue, attrValueArr)!=-1){
				warnTips("liId"+value_id,"属性值名称不允许重复");
				flag = false;
				return flag;
			}
			attrValueArr.push(attrValue);
		});
		if(!flag){
			return false;
		}
		var sortArr = [];var sortValue = "";var sort_id = "";
		$form.find("input[name='sort']").each(function(i){
			sortValue = $(this).val().trim();sort_id = $(this).attr("id");
			if (sortValue.length==0) {
				warnTips("liId"+sort_id,"排序号不允许为空");//文本框ID和提示用语
				flag = false;
				return flag;
			}else{
				var re = /^[0-9]{1,4}$/;
				if(!re.test(sortValue)){
					warnTips("liId"+sort_id,"排序编号必须是0-9999之间的正整数");//文本框ID和提示用语
					flag = false;
					return flag;
				}
			}
			sortArr.push(sortValue);
		});
		if(!flag){
			return false;
		}
		var dateTypeArr = [];
		$form.find("input[name='dataType']").each(function(i){
			dateTypeArr.push($(this).val());
		});
		
		var categoryAttributeValueIdArr = [];
		$form.find("input[name='categoryAttributeValueId']").each(function(i){
			categoryAttributeValueIdArr.push($(this).val());
		});
		$.ajax({
			async:false,
			url:'./updateCateAttributeValue.do',
			data:{"categoryAttributeId":$("#categoryAttributeId").val(),"attrValueArr":JSON.stringify(attrValueArr),"sortArr":JSON.stringify(sortArr),"dateTypeArr":JSON.stringify(dateTypeArr),"categoryAttributeValueIdArr":JSON.stringify(categoryAttributeValueIdArr),"beforeParams":$("input[name='beforeParams']").val()},
			type:"POST",
			dataType : "json",
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
});