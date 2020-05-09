
function delBrand(brandId){
	checkLogin();
	if(brandId > 0){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./delBrand.do",
					data: {'brandId':brandId},
					dataType:'json',
					success: function(data){
						//refreshNowPageList(data);
						if(data.code == 0){
							self.location.reload();
						}else{
							layer.alert(data.message);
						}
						
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
			});
	}
}

function exportBrand(){
	checkLogin();
	location.href = page_url + '/report/supply/exportBrandList.do?' + $("#search").serialize();
}