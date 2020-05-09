$(function() {
	$(".customer a").click(function(){
		var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
	    $("body").prepend(div);
	})
	
});






