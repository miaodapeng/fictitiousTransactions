function search() {
	checkLogin();
	var parentId = "";
	$("#search").find("select[name='categoryOpt']").each(function(){
		if($(this).val()!="-1"){
			parentId = $(this).val()+"";
		}
	});
	$("#parentId").val(parentId=="-1"?"":parentId);
	var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
    $("body").prepend(div); //jq获取上一页框架的父级框架；;
	$("#search").submit();
}

function resetCate(){
	checkLogin();
	$("form").find("input[type='text']").val('');
	$("form select[name='categoryOpt']:first").val("-1");
	$("form select[name='categoryOpt']:first option:selected").each(function(){
		triCategory(this);
	});
	$("select[name='userId']").val(0);
/*	$.each($("form select"),function(i,n){
		$(this).children("option:first").prop("selected",true);
	});*/
}

function delCategory(categoryId){
	checkLogin();
	if(categoryId > 0){
		layer.confirm("删除分类也会同时删除对应的属性，您是否确认该操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./delCategoryById.do",
					data: {'categoryId':categoryId},
					dataType:'json',
					success: function(data){
						//防止后台parentId默认为0，只查询顶级目录下的菜单
						var parentId = "";
						$("#search").find("select[name='categoryOpt']").each(function(){
							parentId = $(this).val()+"";
						});
						$("#parentId").val(parentId=="-1"?"":parentId);
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

window.onload=function(){
	var new_parentId = $("#parentId").val();
	if(new_parentId!="" && new_parentId!=0){
		getParentCategoryList(new_parentId);
	}
}
function getParentCategoryList(categoryId){
	checkLogin();
	$.ajax({
		async:false,
		url:page_url + '/firstengage/category/getParentCateList.do',
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
							'<select id="categoryOpt" name="categoryOpt" style="width: 100px;" >';
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
//	if(parentId=="-1" || parentId==-1){
/*		for(var i=level;i<5;i++){
			$("#spanId"+i).remove();
		}*/
		$("span[id^='spanId']").each(function(){
			var num = $(this).attr("id").substring(6,7);
			if(num!=""){
				if(Number(num) >= level){
					$(this).remove();
				}
			}
		});
//	}
	if(parentId!="-1" && parentId!=-1){
		$("#categoryId").siblings('.warning').remove();
		$.ajax({
			async:false,
			url:page_url + '/firstengage/category/getCategoryList.do',
			data:{"parentId":parentId},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var list = data.listData;
					if(list!=null && list.length>0){
						if(list[0].level <3){
						var ht = "<span id='spanId"+level+"'>" +
								  "<select id='categoryOpt' name='categoryOpt' style='width: 100px;'>";
						ht = ht + "<option value='-1' id='"+list[0].level+"' onclick='triCategory(this)'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"' onclick='triCategory(this)'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select></span>";
						$("#spanId").append(ht);
						}
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


//更改归属
function editAll(obj){
	checkLogin();
	if($("input[name='checkOne']:checked").length == 0){
		layer.alert("分类不能为空");
		return false;
	}
	
	var ids = "";
	$.each($("input[name='checkOne']:checked"),function(i,n){
		ids += $(this).val()+",";
	});
	
	if(ids != ""){
		layer.config({
            extend: 'vedeng.com/style.css', //加载您的扩展样式
            skin: 'vedeng.com'
        });
        var layerParams = $(obj).attr('layerParams');
        if (typeof(layerParams) == 'undefined') {
            alert('参数错误');
        } else {
            layerParams = $.parseJSON(layerParams);
        }
        var link = layerParams.link;
        link += "?categoryIds="+ids;
        if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 == link.length)) {
            link += "pop=pop";
        } else if (link.indexOf("?") < 0) {
            link += "?pop=pop";
        } else if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 != link.length)) {
            link += "&pop=pop";
        }
        var index = layer.open({
            type: 2,
            shadeClose: false, //点击遮罩关闭
            //area: 'auto',
            area: [layerParams.width, layerParams.height],
            title: layerParams.title,
            content: encodeURI(encodeURI(link)),
            success: function(layero, index) {
                //layer.iframeAuto(index);
            }
        });
	}
}

function exportCategory(){
	checkLogin();
	location.href = page_url + '/report/supply/exportCategoryList.do?' + $("#search").serialize();
}