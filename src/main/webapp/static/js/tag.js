//删除标签
function delTag(obj){
	var div = $(obj).parent().parent().parent();
	$(obj).parent().remove();
	if($(div).find("li").length == 0)
	{
		$(div).hide();
	}
}

/**
 * 标签更换
 */
function changeTag(totalPage,pageSize,obj,tagType){
	var left = $("#leftNum").html()*1;
	var pageNo = $("#pageNo").val()*1+1;
	if(left == 1){
		pageNo = 1;
	}
	if(pageNo != '' && totalPage != ''){
		$.ajax({
			type : "POST",
			url : page_url + '/system/tag/gettag.do',
			data : {pageNo:pageNo,totalPage:totalPage,pageSize:pageSize,isRecommend:1,tagType:tagType},
			dataType : 'json',
			success : function(data) {
				if(data.code == 0){
					$(obj).prevAll("span").remove();
					var param =  eval('(' + data.param + ')');
					var list = param.list;
					$.each(list,function(i,n){
						$(obj).before("<span onclick=addTag('"+list[i]['tagId']+"','"+list[i]['tagName']+"',this)>"+list[i]['tagName']+"</span>");
					});
					if(left == 1){
						$("#leftNum").text(totalPage);
					}else{
						$("#leftNum").text(left-1);
					}
					$("#pageNo").val(pageNo);
				}else{
					layer.msg(data.message)
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
}
/**
 * 标签库标签添加
 */
function addTag(tagId,tagName,obj){
	$(".warning").remove();
	var ok = true;
	$.each($("input[name='tagId']"),function(i,n){
		if($(this).val() == tagId){
			ok = false;
		}
	});
	
	if(ok){
		
		var tagLi = "<li class='bluetag'>"+tagName
		+"<input type='hidden' name='tagId' alt='"+tagName+"' value='"+tagId+"'><i class='iconbluecha' onclick='delTag(this);'></i></li>";
		
		$("#tag_show_ul").append(tagLi);
		
		$("#tag_show_ul").parent(".addtags").show();
	}else{
		layer.tips("选择的标签已经存在",obj,{time:1000});
	}
	
}

/**
 * 自定义标签添加
 */
function addDefineTag(obj){
	var tagName = $("#defineTag").val();
	if(tagName.length > 0 && tagName.length > 32){
		layer.tips("标签长度不允许超过32个字符",obj,{time:1000});
		return false;
	}
	var ok = true;
	$.each($("input[name='tagName']"),function(i,n){
		if($(this).val() == tagName){
			ok = false;
		}
	});
	$.each($("input[name='tagId']"),function(i,m){
		if($(this).attr("alt") == tagName){
			ok = false;
		}
	})
	if(ok){
		if(tagName != ''){
			var tagLi = "<li class='greentag'>"+tagName
			+"<input type='hidden' name='tagName' value='"+tagName+"'><i class='icongreencha' onclick='delTag(this);'></i></li>";
			
			$("#tag_show_ul").append(tagLi);
			
			$("#defineTag").val("");
			
			$("#tag_show_ul").parent(".addtags").show();
		}
	}else{
		layer.tips("选择的标签已经存在",obj,{time:1000});
	}
}