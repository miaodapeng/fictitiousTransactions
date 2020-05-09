function importSuppliers(){
	var dialog = new artDialog({
        title: '公司导入',
        content: $('.J-Import-tmpl').html(),
        init: function () {
        },
        width: 400,
        button: [{
            name: '提交',
            highlight: true,
            callback: function () {
            	if(document.getElementById("file").value == null || document.getElementById("file").value == ""){
                    alert("请选择上传文件");
                    return false;
            	}
            	$(".dlg-footer").find('.btn-main').attr('disabled','disabled');
            	$("#importForm").ajaxSubmit({
					async : false,
					url : '/order/adkorder/importSuppliers.do',
					data : $("#importForm").serialize(),
					type : "POST",
					dataType : "json",
					success : function(data) {
						$(".dlg-footer").find('.btn-main').attr('disabled',false);
						if(data.code==0){
							 alert("导入成功")
							 window.location.reload();
						}else{
							 $("#errorTit").html("导入失败："+data.message);
							 $("#errorTit").show();
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						$(".dlg-footer").find('.btn-main').attr('disabled',false)
						if(textStatus==1001){
							$("#errorTit").html("权限不足");
							$("#errorTit").show();
						}else{
							 $("#errorTit").html("导入失败，网络连接异常或登陆已过期。" );
							 $("#errorTit").show();
						}
					}
				});
              
                return false;
            }
        }, {
            name: '取消'
        }],
    })
}