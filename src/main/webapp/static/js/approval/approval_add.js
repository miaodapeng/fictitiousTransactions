$(function() {
    var $form = $("#addSalesForm");
    $form.submit(function() {
        $.ajax({
            async:false,
            url:'./add.do',
            data:$form.serialize(),
            type:"POST",
            dataType : "json",
            beforeSend:function(){
                var salesAuditName = $("#salesAuditName").val();
                if (salesAuditName.length==0) {
                    warnTips("salesAuditName","销售审单名称不能为空");//文本框ID和提示用语
                    return false;
                }

                var uri = $("#uri").val().trim();
                if (uri.length==0) {
                    warnTips("lwfile","请上传销售审单图片");//文本框ID和提示用语
                    return false;
                }
            },
            success:function(data){
                parent.layer.close(index);
                parent.location.reload();
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

function uploadFile(obj){
    checkLogin();
    var imgPath = $(obj).val();
    if(imgPath == '' || imgPath == undefined){
        return false;
    }
    $("#lwfile").siblings('.warning').remove();
    var uri = $("#uri").val();
    fileSize = $(obj)[0].files[0].size;

    //判断上传文件的后缀名
    strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
    if (strExtension != 'jpg' && strExtension != 'jpeg' && strExtension != 'png' && strExtension != 'JPG' && strExtension != 'JPEG' && strExtension != 'PNG') {
        layer.alert("图片格式不正确");
        if(uri == ""){
            delImgInfo();
        }

        return false;
    }
    var status = $("#terminalDiv input[name = 'terminalType']:checked").val();
    if (status == 0){
        if($(obj)[0].files[0].size > 3*1024*1024){//字节
            layer.alert("图片文件大小应为3MB以内",{ icon: 2 });
            if(uri == ""){
                delImgInfo();
            }
            return false;
        }
    } else {
        if($(obj)[0].files[0].size > 2*1024*1024){//字节
            layer.alert("图片文件大小应为2MB以内",{ icon: 2 });
            if(uri == ""){
                delImgInfo();
            }
            return false;
        }
    }

    $("#img_icon").attr("class", "iconloading mt5").show();
    $("#img_view").hide();
    $("#img_del").hide();
    $.ajaxFileUpload({
        url : page_url + '/sales/sales/salesUpload.do', //用于文件上传的服务器端请求地址
        secureuri : false, //一般设置为false
        fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
        dataType : 'json',//返回值类型 一般设置为json
        complete : function() {//只要完成即执行，最后执行
        },
        //服务器成功响应处理函数
        success : function(data) {
            if (data.code == 0) {
                $("#uri").val(data.filePath+"/"+data.fileName);
                $("#domain").val(data.httpUrl);
                $("#file_name").val(data.fileName);
                $("#img_view").attr("href", 'http://' + data.httpUrl + data.filePath+"/"+data.fileName).show();
                $("#img_icon").attr("class", "iconsuccesss mt5").show();
                $("#img_del").show();
                $("#lwfile").val("");
                //layer.msg('上传成功');
            } else {
                layer.alert(data.message);
            }
        },
        //服务器响应失败处理函数
        error : function(data, status, e) {
            layer.alert(data.responseText);
        }
    })
}

function delImgInfo(){
    $("#uri").val('');
    $("#lwfile").val('');
    $("#domain").val('');
    $("#file_name").val('');
    $("#img_view").attr("href", '').hide();
    $("#img_icon").attr("class", "iconsuccesss mt5").hide();
    $("#img_del").hide();
}

function delProductImg() {
    checkLogin();
    confimMsg = layer.confirm("您是否确认该操作？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        $("#img_icon").hide();
        $("#img_view").hide();
        $("#img_del").hide();
        $("#uri").val('');
        $("#domain").val('');
        $("#file_name").val('');
        layer.close(confimMsg);
    }, function(){
        layer.close(confimMsg);
    });
}

function checkManualShelves(num) {
    if (num == 0) {
        $("#dateTime").show()
    }else {
        $("#dateTime").hide()
    }
}

function addSalesValue(){
	var ht ='<li class="sales">'+
	'<div class="infor_name">'+
	'</div>'+'<div class="f_left">'+
	'<input type="text" class="input-largest" name="salesAuditList"  placeholder = "请输入销售审单名称">'+
	'<span class="delSales" onclick="del(this)" style="margin-left:7px;color:red">删除</span>'+
	'</div>'+
	'<div class="clear"></div>'+
	'</li>';
	$('#addValue').before(ht);
	//属性值大于1个
	if($(".sales").length>1){
		$.each($(".delSales"), function() {
			$(this).show();
			
		})
	}
}

function del(obj){
	checkLogin();
	//属性值大于1个
	if($(".sales").length==2){
		$.each($(".delSales"), function() {
			$(this).hide();
		})
	}
	$(obj).parent().parent().remove();
}
