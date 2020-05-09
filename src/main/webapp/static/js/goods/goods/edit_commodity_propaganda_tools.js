$(function() {
	$("#myform").submit(function(){
		if ($("#customerNames").val() != "" && $("#customerNames").val().length > 256) {
			warnTips("customerNames","客户名单长度不允许超过256个字符");
			return false;
		}
		if ($("#sellingWords").val() != "" && $("#sellingWords").val().length > 256) {
			warnTips("sellingWords","销售话术长度不允许超过256个字符");
			return false;
		}
		if ($("#marketStrategy").val() != "" && $("#marketStrategy").val().length > 512) {
			warnTips("marketStrategy","市场策略长度不允许超过512个字符");
			return false;
		}
		if ($("#promotionPolicy").val() !="" && $("#promotionPolicy").val().length > 256) {
			warnTips("promotionPolicy","促销政策长度不允许超过256个字符");
			return false;
		}
		//判断输入的视频地址长度不允许超过256个字符
		var flag = -1 ;
		$("input[name='fileName41']").each(function(index,item){
			var value = $(this).val(); 
			if(value!=""){
				 if(value.length>256){
					 $("form :input").parents('li').find('.warning').remove();
					 $("form :input").removeClass("errorbor");
					 $(this).siblings('.warning').remove();
					 $(this).after('<div class="warning">视频地址长度不允许超过256个字符</div>');
					 $(this).focus();
					 $(this).addClass("errorbor");
					 flag = 1;
				 }
			}
		})
		if(flag == 1){
			return false;
		}
	})
});

function con_add(i){
	var num = Number($("#conadd"+i).siblings(".form-blanks").length)+Number(1);
	var div = '<div class="form-blanks mt10">'+
					'<div class="pos_rel f_left">'+
				    '<input type="file" class="upload_file" id="file_'+i+num+'" name="lwfile" style="display: none;" onchange="uploadFile(this,'+i+num+');">'+
				    '<input type="text" class="input-largest" id="name_'+i+num+'" readonly="readonly"'+
				    	'placeholder="请上传附件" name="fileName'+i+'" onclick="file_'+i+num+'.click();" >'+
				    '<input type="hidden" id="uri_'+i+num+'" name="fileUri'+i+'" >'+
				'</div>'+
				'<label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $(\'#file_'+i+num+'\').click();">浏览</label>'+
				'<div class="f_left"><i class="iconsuccesss mt3 none" id="img_icon_'+i+num+'"></i>'+
				'<a href="" target="_blank" class="font-blue cursor-pointer mt3 none" id="img_view_'+i+num+'">查看</a>'+
				'<span class="font-red cursor-pointer mt3" onclick="del('+i+num+','+num+')" id="img_del_'+i+num+'">删除</span></div><div class="clear"></div>'+
				'</div>';
	$("#conadd"+i).before(div);
}
function con_add_vedio(){
	$("#img_del_41").show();
	var num = Number($("#conadd41").siblings(".form-blanks").length)+Number(1);
	var div = '<div class="form-blanks mt10">'+
					'<div class="pos_rel f_left">'+
				    '<input placeholder="请填写链接地址" type="text" class="input-largest" id="name_4'+num+'" '+' name="fileName41" " >'+
				    '<input type="hidden" id="uri_4'+num+'" name="fileUri41" >'+
				    '</div><div class="f_left">'+
				'<span class="font-red cursor-pointer mt3" onclick="del_vedio(4'+num+','+num+')" id="img_del_4'+num+'">删除</span></div><div class="clear"></div>'+
				'</div>';
	$("#conadd41").before(div);
}

function del_vedio(num,i){
	var sum = Number($("#conadd41").siblings(".form-blanks").length);
	var uri = $("#uri_"+num).val();
	if( sum > 1){
		$("#img_del_"+num).parent().parent(".form-blanks").remove();
	}else{
		if(i == 1){
			$("#img_del_" + num).hide();
		}
		$("#name_"+ num).val("");
		$("#uri_"+ num).val("");
		$("#file_"+ num).val("");
	}
}


function del(num,i){
	var n=0;
	if((num>200 && num<300)|| (num>20 && num<30)){
		n= 21;
	}else if(num>300 || (num >30 && num < 100) ){
		n = 31;
	}else if((num>100 && num<200) || (num>10 && num<20)){
		n = 11; 
	}
	var sum = Number($("#conadd"+n).siblings(".form-blanks").length);
	var uri = $("#uri_"+num).val();
	if(uri == '' && sum > 1){
		$("#img_del_"+num).parent().parent(".form-blanks").remove();
	}else{
		index = layer.confirm("您是否确认该操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				if(i == 1){
					$("#img_del_" + num).hide();
				}
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
				$("#file_"+ num).val("");
				layer.close(index);
			}, function(){
			});
	}
}
