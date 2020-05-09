$(document).ready(function(){
	$('#myform').submit(function() {
		checkLogin();
		jQuery.ajax({
			url:'./saveadd.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				var title = $("#title").val();
				var sort = $("#sort").val();
				var sortReg = /^[1-9][0-9]{0,9}$/;
				var comments = $("#comments").val();
				var titleReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,\/\%]{1,128}$/;
			
				if(title == ""){
					warnTips("title","名称不允许为空");
					return false;
				}
				if(title.length < 1 || title.length > 128){
					warnTips("title","名称长度应该在2-128个字符之间");
					return false;
				}
				// if(!titleReg.test(title)){
				// 	warnTips("title","名称不允许使用特殊字符");
				// 	return false;
				// }
				
				if(sort == ""){
					warnTips("sort","排序不允许为空");
					return false;
				}
				if(sort.length > 10){
					warnTips("sort","排序长度不允许超过10个字符");
					return false;
				}
				if(!sortReg.test(sort)){
					warnTips("sort","排序不允许使用特殊字符");
					return false;
				}
				if(comments.length > 128){
					warnTips("comments","备注长度不允许超过128个字符");
					return false;
				}
			},
			success:function(data)
			{
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
			
		});
		return false;
	});
});
