$(function() {
	$("input[type='checkbox']").click(function(){
		checkLogin();
		var num = 0;
		var amount = 0.00;
		$.each($("input[name='checkOne']:checked"),function(i,n){
			var goodsId = $(this).val();
			num += $("td[alt='"+goodsId+"']").html() * 1;
			amount += $("td[alt2='"+goodsId+"']").html() *1;
		});
//		$(window.frames["statIframe"].document).find("#selectNum").html(num);
//		$(window.frames["statIframe"].document).find("#selectAmount").html(amount);
	});
});


//生成备货单
function addBHOrder(obj){
	checkLogin();
	if($("input[name='checkOne']:checked").length == 0){
		layer.alert("产品不能为空");
		return false;
	}
	
	var ids = "";
	$.each($("input[name='checkOne']:checked"),function(i,n){
		ids += $(this).val()+",";
	});
	
	if(ids != ""){
		//var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
        var newPageId;
        var tabTitle = $(obj).attr('tabTitle');
        if (typeof(tabTitle) == 'undefined') {
            alert('参数错误');
        } else {
            tabTitle = $.parseJSON(tabTitle);
        }
        var id = tabTitle.num;
        //var id = 'index' + Date.parse(new Date()) + Math.floor(Math.random()*1000);
        var name = tabTitle.title;
        var uri = tabTitle.link+"?goodsIds="+ids;
        var closable = 1;
        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
        self.parent.closableTab.addTab(item);
        self.parent.closableTab.resizeMove();
        //$(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
	}
}

function exportList(){
	checkLogin();
	location.href = page_url + '/report/buy/exportbhmanagelist.do?' + $("#search").serialize();
}