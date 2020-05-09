function addOwner(obj){
	$(obj).parent().parent().parent("li").clone().appendTo($(obj).parent().parent().parent().parent("ul"));
	$("select[name='userId']:last").val(0);
	$(obj).replaceWith('<div class="f_left bt-bg-style bt-middle bg-light-red" onclick="delOwner(this);">删除</div>');
}
function delOwner(obj){
	$(obj).parent().parent().parent("li").remove();
}

$(function(){
	$('#myform').submit(function() {
		checkLogin();
		jQuery.ajax({
			url:'./saveeditcategoryowner.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				checkLogin();
				$(".warning").remove();
				$("select").removeClass("errorbor");
				
				var sb = true;
				var userId = "";
				$.each($("select"),function(i,n){
					if($(this).val() == 0){
						$(this).addClass("errorbor");
						$(this).parent().after('<div class="warning">归属不允许为空</div>');
						sb = false;
					}else{
						userId += $(this).val()+",";
					}
				});
				
				var ary = userId.split(","); //字符分割 
				var nary=ary.sort();

				for(var i=0;i<ary.length;i++){

					if (nary[i]==nary[i+1]){

						layer.alert("归属不允许重复");
	
						return false;
					}
				}
				return sb;
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