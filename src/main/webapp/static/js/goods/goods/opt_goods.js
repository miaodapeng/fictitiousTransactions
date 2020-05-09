function search() {
	$("#search").submit();
}

function saveOpt(){
	checkLogin();
	var num = 0;var goodsIdArr = [];
	$("input[name='checkOne']").each(function(){
		if($(this).is(':checked')){
			goodsIdArr.push($(this).val());
			num++;
		}
	});
	if(num==0){
		layer.alert("请选择新增的配件");
		return false;
	}
	var optType = $("#optType").val();
	if(optType=="pj"){//配件
		var isStandard = $("input[name='isStandard']:checked").val();
		if(isStandard==undefined || isStandard == null || isStandard==""){
			layer.alert("请选择配件种类");
			return false;
		}
		layer.confirm("您是否确认操作？", 
				{ btn: ['确定','取消']}, 
				function(){
					$.ajax({
						async:false,
						url:'./saveGoodsPackage.do',
						data:{"isStandard":isStandard,"parentGoodsId":$("#goodsId").val(),"goodsIdArr":JSON.stringify(goodsIdArr)},
						type:"POST",
						dataType : "json",
						success:function(data){
							if(data.code==0){
								layer.alert(data.message, 
										{ icon: 1 },
										function () {
											$('#cancle').click();
											/*if(parent.layer!=undefined){
												parent.layer.close(index);
											}*/
											parent.location.reload();
										}
								);
							}else{
								layer.alert(data.message);
							}
						},
						error:function(data){
							if(data.status ==1001){
								layer.alert("当前操作无权限")
							}
						}
					})
				});
	}else if(optType=="gl"){
		layer.confirm("您是否确认操作？", 
				{ btn: ['确定','取消']}, 
				function(){
					$.ajax({
						async:false,
						url:'./saveGoodsRecommend.do',
						data:{"parentGoodsId":$("#goodsId").val(),"goodsIdArr":JSON.stringify(goodsIdArr)},
						type:"POST",
						dataType : "json",
						success:function(data){
							if(data.code==0){
								layer.alert(data.message, 
										{ icon: 1 },
										function () {
											$('#cancle').click();
											parent.location.reload();
										}
								);
							}else{
								layer.alert(data.message);
							}
						},
						error:function(data){
							if(data.status ==1001){
								layer.alert("当前操作无权限")
							}
						}
					})
				});
	}
}