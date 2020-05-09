var changecareerlayer = {
    addLayer: function(obj) {
        var set_wid = obj.width;
        var set_heit = obj.height;
        var title = obj.title;
        var url = obj.link;
        var iframe = '<div class= "changecareer changecareerlayer"><div style="background:#fff;width:' + set_wid + ';height:' + set_heit + ';position:absolute;left:50%;top:50%;margin-top:-' + (set_heit / 2) + 'px;margin-left:-' + (set_wid / 2) + 'px;"><div style="background:#f3f3f3;overflow:hidden;height:30px;line-height:30px;"><span class="f_left" style="margin-left:10px;">'+title+'</span><i class="icon iconchacha f_right" close="changecareer" style="margin:7px 10px 0 0;" onclick="changecareerlayer.closeLayer(this);"></i></div><iframe name="changecareer" frameborder="0" src="' + url + '" style="width:100%; height: calc(100% - 30px);height: -webkit-calc(100% - 30px);height: -moz-calc(100% - 30px); "></iframe></div></div>'
        $('body').append(iframe);
    },
    closeLayer: function(item){
        var close=$(item).attr('close');
        $("."+close).remove();
    },
    closeParentLayer: function(){
         $(window.parent.document).find('.changecareer').remove();
    }
}
function saveNoticeUser(){
	var userId=$("#userId").val();
	var noticeId=$("#noticeId").val();
	$.ajax({
    	type: "POST",
    	url:'./system/notice/saveNoticeUser.do',
    	data:{'userId':userId,'noticeId':noticeId},
    	dataType:'json',
		success: function(data){
			if(data.data!=1){
				layer.alert("操作失败");
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限");
			}
		}
    });
}
$(function(){
    $('#changecareer-close').on('click',function(){
       changecareerlayer.closeParentLayer();
    });
    var timer = null;
    var n=5;
    timer = setInterval(function(){
         if(n>0){
            $('.have-read span').text('我已阅读('+n+'S)');
             n--;
         }else{
            $('.have-read span').text('我已阅读').css({'opacity':'1',"cursor":"pointer"});
            clearInterval(timer);
         }
    },1000);
    setTimeout(function(){
        $('.have-read span').click(function(){
            $('.version-attention').hide();
            if($("#positType").val()=='310'){
            	$(".front-page-slide").attr('style','display: block;');
            }
            saveNoticeUser();
        });
    },5000);
})