/*$(function() {
	var $form = $("#submit");
	$form.submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./addCategorySave.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){  
				var weight = $(".weight");
				alert(wight);
				return false;
				var count=0;
				for(var i=0;i<weight.length;i++){
					count=count+$("#configVoList["+i+"].weight").val()
				}
				
				if (level>=3) {
					warnTips("category_div","分类层级不允许超过三级");//文本框ID和提示用语
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
});*/

function submitData(){
		checkLogin();
		var $form = $("#submit");
		var weight = $(".weight");
		var count=0;
		for(var i=0;i<weight.length;i++){
			if(!$("#configVoList-"+i).val()){
				$('#warn').html("各项考核项占比不可为空，请检查后提交！");
				return false;
			}
			if($("#configVoList-"+i).val().indexOf(".")>-1){
				$('#warn').html("各项考核项占比不可为小数，请检查后提交！");
				return false;
			}
			count=count+parseFloat($("#configVoList-"+i).val());
		}
		if(count!=100){
			/*warnTips("warn","各项考核项占比总和不为100%，请检查后提交！");//文本框ID和提示用语*/	
			$('#warn').html("各项考核项占比总和不为100%，请检查后提交！");
			return false;
		}else{
			$('#warn').html("");
		}
		$.ajax({
			async:false,
			url:'./saveOrUpdateConfigSetData.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
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
}
