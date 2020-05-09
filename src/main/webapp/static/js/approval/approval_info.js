//未用到验证  暂时不需要验证
$(function() {
    /*var $form = $("#mysearch");
    $form.submit(function() {
        $.ajax({
            async:false,
            url:'./modify.do',
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
                    warnTips("lwfile","请上传广告图");//文本框ID和提示用语
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
    });*/
    heightCtlSelf('record-cont');
  
});

function checkManualShelves(num) {
    if (num == 0) {
        $("#dateTime").show()
    }else {
        $("#dateTime").hide()
    }
}

	
//'展开' 记录
function openMore(obj){
	var recordCont = $(obj).parents('li').find('.record-cont');
	recordCont.toggleClass('record-cont-height');
	$(obj).hide();
	$(obj).parents('li').find('.close').show();
}
//'关闭' 记录
function closeMore(obj){
	var recordCont = $(obj).parents('li').find('.record-cont');
	recordCont.toggleClass('record-cont-height');
	$(obj).hide();
	$(obj).parents('li').find('.open').show();
}


//编辑记录
function editRecord(obj){
	var editLi = $(obj).parents('li');
	var originText = editLi.find('.record-cont').html();
	editLi.addClass('edit-li').find('textarea').val(originText);
}

//验证form里面文字 不能超过500字
$(function(){
	$("#addApprovalForm").submit(function(){
		$(".warning").remove();
		
		if($("#organizeName").val().length > 500){
			warnTips("organizeName","机构名称不能超过500个字符");
			return  false;
		}
		if($("#organizeType").val().length > 500){
			warnTips("organizeType","机构类别不能超过500个字符");
			return  false;
		}
		if($("#organizeSubject").val().length > 500){
			warnTips("organizeSubject","机构科目不能超过500个字符");
			return  false;
		}
		
		if($("#contactAddress").val().length > 500){
			warnTips("contactAddress","联系地址不能超过500个字符");
			return  false;
		}
		if($("#contactPhone").val().length > 500){
			warnTips("contactPhone","联系电话不能超过500个字符");
			return  false;
		}
		
		if($("#recordContent").val().length > 500){
			warnTips("recordContent","跟进记录不能超过500个字符");
			return  false;
		}
		
		return true;
	});
});


//删除记录,子表直接删除(现在是逻辑删除) 
function dltRecord(recordId){
	checkLogin();
	layer.confirm("您是否确认删除？", {
		  btn: ['确定','取消']
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/approval/approval/deleterecord.do",
				data: {'recordId':recordId},
				dataType:'json',
				success: function(data){
					window.location.reload();
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

function refreshList(data){
	console.log(data)
	$(".J-wrap").empty();
	data.map(function(item){
		var html = "";
		if (item.isLimit == 1) {
			
			html = '<span class="record-edit" onclick="editRecord(this)">编辑</span><span class="record-dlt" onclick="dltRecord('+item.recordId +')">删除</span>';
		}else{
			html = '<span class="record-edit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="record-dlt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>';
		}
		$(".J-wrap").append(
				'<li class="J-item">' +
          		'<input type="hidden" value="' + item.approvalId + '" class="J-appId">' +
  				'<div class="record-tit">' +
      				'<div class="font-blue f_left record-user">' +item.createName +'</div>' +
      				'<span class="record-date" style="font-weight:700;font-size:13px;">' + item.modTime + '</span>' +
      				'<div class="f_right"> '+ html +' </div>' +
      			'</div>' +
  				'<div class="record-shows">' +
  					'<div class="record-cont">' + item.recordContent + '</div>' +
  				    '<div class="open J-open" onclick="openMore(this)"><span class="font-blue" >展开</span></div>' +
  				    '<div class="close" onclick="closeMore(this)"><span class="font-blue" >关闭</span></div>' +
  				'</div>' +
  				'<div class="edit-record">' +
  					'<textarea name="content" class="J-cnt"  rows=3 maxLength="500"></textarea>' +
					 '<div class=" employeesubmit edit-ensure">' +
						'<button  type="submit" class="edit-sure J-edit" data-record="' + item.recordId + '"  >确定</button>' +
						'<button  type="button" class="edit-dele" onclick="cancleEdit(this)">取消</button>' +
					'</div> ' +
  				'</div>' +
				'</li>')
	})
}

$(".J-wrap").on('click', '.J-edit', function(e){
	e.preventDefault();
//	var editLi = $(this).parents('li');
//	editLi.removeClass('edit-li');
	
	
	console.log($("#recordContent").val().length);
	
	/*if($("#recordContent").val().length > 500){
		warnTips("recordContent","跟进记录不能超过500个字符");
		return  false;
	}*/
	
	$.ajax({
        async:false,
        url:'./modifyrecord.do',
        data: {
        	recordContent: $(this).parents(".J-item:first").find(".J-cnt").val(),
        	approvalId: $(this).parents(".J-item:first").find(".J-appId").val(),
        	recordId: $(this).data('record')
    	},
        type:"POST",
        dataType : "json",
        success:function(data){
        	if (data.code == -1) {
        		layer.alert("跟进内容不能为空！") //此处生效
        	}
        	if (data.code == -2) {
        		layer.alert("跟进记录不能超过500个字符！") //此处生效
        	}
        	if (data.code == -3) {
        		layer.alert("请勿连续、重复输入字母！") //此处生效
        	}
        	if (data.code == 0) {
        		refreshList(data.listData);
        		heightCtlSelf('record-cont');
        	}
//            parent.layer.close(index);
//            parent.location.reload();
//        	refreshList(data.listData)
            
        	
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限！")
            }
        }
    })
})

$('#modifyApprovalForm').submit(function(e) {
	e.preventDefault();
	return false;
})

//function okEdit(e){
//	e.preventDefault();
////	传值  
//	$('#modifyApprovalForm').submit(function() {
//    $.ajax({
//        async:false,
//        url:'./modifyrecord.do',
//        data:$form.serialize(),
//        type:"POST",
//        dataType : "json",
//        success:function(data){
//            parent.layer.close(index);
//            parent.location.reload();
//        },
//        error:function(data){
//            if(data.status ==1001){
//                layer.alert("当前操作无权限")
//            }
//        }
//    })
//    return false;
//});
//}


function cancleEdit(obj){
	var editLi = $(obj).parents('li');
	editLi.removeClass('edit-li');
}
/*function heightCtl(obj){
	var _this = obj;
	var _this_li =  $(_this).parents('li');
	var h = _this_li.find('.record-cont').height();
	if(h>40){
		_this_li.find('.record-cont').css('max-height','40px');
		_this_li.find('.J-open').show();
	}else{
		_this_li.find('.J-open').hide();
	}
}*/
			



