function btnSub(invoiceApplyId,status,isAdvance){
	checkLogin();
	var comments = $("#comments").val();
	if (comments.length < 1) {
		warn2Tips("comments", "审核不通过原因不允许为空");// 文本框ID和提示用语
		return false;
	}
	if (comments.length > 128) {
		warn2Tips("comments", "审核不通过原因最大允许128位字符");// 文本框ID和提示用语
		return false;
	}
	/*var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
	if (!nameReg.test(advanceValidComments)) {
		warn2Tips("advanceValidComments", "审核原因不允许使用特殊字符");
		return false;
	}*/
	if(isAdvance == 1){//提前开票列表页面审核
		$.ajax({
			async : false,
			url : './saveOpenInvoiceAudit.do',
			data : {"invoiceApplyId":invoiceApplyId,"advanceValidStatus":status,"advanceValidComments":comments,"isAdvance":isAdvance},
			type : "POST",
			dataType : "json",
			success : function(data) {
				refreshPageList(data);
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}else if(isAdvance == 0){//开票审核页面审核
		$.ajax({
			async : false,
			url : './saveOpenInvoiceAudit.do',
			data : {"invoiceApplyId":invoiceApplyId,"validStatus":status,"validComments":comments,"isAdvance":isAdvance},
			type : "POST",
			dataType : "json",
			success : function(data) {
				//refreshPageList(data);
				refreshParentPage(data);
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
}


//刷新父级页面(添加、删除)
function refreshParentPage(data){
	if(data.code == 0){
		if((parent.$("#searchSpan") != undefined) && (parent.$("#searchSpan").html() != undefined)){
			if(parent.$("#winPageNo").val()!=undefined && parent.$("#choosePageSize").val()!=undefined){
				var $obj = parent.$("body").children("div.content").children("div.searchfunc").children("form");
				$obj.append("<input type='hidden' name='pageNo' value='"+parent.$("#winPageNo").val()+"'/>"
					+"<input type='hidden' name='pageSize' value='"+parent.$("#choosePageSize").val()+"'/>");
			}

		}else if(($("#searchSpan") != undefined) && ($("#searchSpan").html() != undefined)){
			if($("#winPageNo").val()!=undefined && $("#choosePageSize").val()!=undefined){
				var $obj = $("body").children("div.content").children("div.searchfunc").children("form");
				$obj.append("<input type='hidden' name='pageNo' value='"+$("#winPageNo").val()+"'/>"+"<input type='hidden' name='pageSize' value='"+$("#choosePageSize").val()+"'/>");
			}

		}else{
			var parentUrl = (window.parent.location).toString();
			if(parentUrl.endsWith('main.do')){
				if(parent.$("#searchSpan")==undefined || parent.$("#searchSpan").html()==undefined){
					window.location.reload();
					return;
				}else{
					if(parent.$("#winPageNo").val()!=undefined && parent.$("#choosePageSize").val()!=undefined){
						var $obj = parent.$("body").children("div.content").children("div.searchfunc").children("form");
						$obj.append("<input type='hidden' name='pageNo' value='"+parent.$("#winPageNo").val()+"'/>"+"<input type='hidden' name='pageSize' value='"+parent.$("#choosePageSize").val()+"'/>");
					}
				}
			}else{
				window.parent.location.reload();
			}
		}
		if(parent.layer!=undefined){
			parent.layer.close(index);
		}
		window.parent.location.reload();
	}
	if(data.code == -1){
		if(data.listData != null){
			$(".service-return-error").remove();
			var error = "<ul>";
			var errors = data.listData;
			for(var i=0;i<errors.length;i++){
				error += "<li>"+(i+1)+"、"+errors[i]['defaultMessage']+"</li>";
			}
			error += "</ul>";
			$("form").before("<div class='service-return-error'>"
				+ error
				+"</div>");
		}else{
			var msg = data.message != '' ? data.message : '操作失败';
			layer.alert(msg,{
				btn:['确认','取消']
			},function () {
				parent.layer.close(index);
			});
		}
	}

}