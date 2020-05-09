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
        link += "?manageCategories="+ids;
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