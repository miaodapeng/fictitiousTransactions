$(function(){
	var checkType = function(){
		if($('.J-type:checked').val() == '1'){
			$('.J-block1').show().find('input').attr('disabled', false);
			$('.J-block2').hide().find('input').attr('disabled', true);
		}else{
			$('.J-block2').show().find('input').attr('disabled', false);
			$('.J-block1').hide().find('input').attr('disabled', true);
		}
	}
	
	checkType();
	
	$('.J-type').change(function(){
		checkType();
	})
	
	$('.form-tips-show').hide();
	$('.form-tips-show_2').hide();
})

function hanlderSelect(traderId, traderName,traderType){
	$('#trader_name_span_1').html(traderName);
	$('#trader_id_span_1').val(traderId);
	$('#trader_type_span_1').val(traderType);
}
function hanlderSelectgoods(goodsId,sku,goodsStock,goodsName){
	$('#goods_num_span_1').val(goodsStock);
	$('#goods_num_span_1').html(goodsStock);
	$('#goods_name_span_1').html(goodsName);
	$('#goods_sku_span_1').val(sku);
	$('#goods_id_span_1').val(goodsId);
	if($('#goods_name_span_1').html() !== ""){
		$('.form-tips-show').show();
	};
}
function hanlderSelectAfterSales(afterSalesNo, goodsName,afterGoodsNum,exchangeDeliverNum,goodsStockNum,goodsId){
	$('#goods_aftersalesno_span_val2').val(afterSalesNo);
	$('#goods_aftersalesno_span_2').html(afterSalesNo);
	$('#goods_name_span_2').html(goodsName);
	$('#goods_num_span_2').html(afterGoodsNum-exchangeDeliverNum);
	$('#goods_num_span_2').val(afterGoodsNum-exchangeDeliverNum);
	$('#goods_stock_span_2').val(goodsStockNum);
	$('#goods_stock_span_2').html(goodsStockNum);
	$('#goods_id_span_2').val(goodsId);
	if($('#goods_name_span_2').html() !== ""){
		$('.form-tips-show_2').show();
	};
}
function savelendout() {
	checkLogin();
	var stockNum1 = $("#goods_num_span_1").val();
	var stockNum2 = $("#goods_stock_span_2").val();
	var num2 = $("#goods_num_span_2").val();
	if(stockNum2>num2){
		stockNum2  = num2;
	}
//	var nn = $("#lendoutnum1").val();
	
//	console.log($("#lendoutnum1").val(),stockNum1);
	//console.log(nn);
	//console.log('num2'+num2);
	//console.log('stockNum2'+stockNum2);
	$("form :input").parents('li').find('.warning').remove();
	if($('.form-blanks .input-middle:checked').val() == '1'){
		
		if ($("#trader_id_span_1").val() == 0) {
			layer.alert("请选择借用企业");
			return false;
		}
		if ($("#goods_sku_span_1").val() == 0  || $('#goods_id_span_1').val()==0) {
//			warnTips("goodsIdMsg","请选择借用产品");
			layer.alert("请选择借用产品");
			return false;
		}
		if($("#lendoutnum1").val() == ''){
			warnTips("lendOutNumMsg","请选择借用数量");
			return false;

	   }else if(!(/\d+/g.test($("#lendoutnum1").val()))){
			warnTips("lendOutNumMsg","请输入正确的数字");
			return false;
		}else if($("#lendoutnum1").val() <= '0'){
			warnTips("lendOutNumMsg","请输入正确的数字");
			return false;
		}else if(Number($("#lendoutnum1").val()) > Number(stockNum1)){
			warnTips("lendOutNumMsg","外借数量不能大于库存量");
			return false;
		}
		
		
		if($("#searchEndtimeStr").val() == 0){
			warnTips("searchEndtimeStrMsg","请选择预计归还时间");
			return false;
		}
	}else{
		if ($("#goods_aftersalesno_span_val2").val() == "") {
			layer.alert("请选择借用产品");
			//warnTips("afterNoMsg","请选择借用产品");
			return false;
		}
		if($("#lendoutnum2").val() == ''){
			warnTips("lendOutNumMsg2","请选择借用数量");
			return false;

		}else if(!(/\d+/g.test($("#lendoutnum2").val()))){
			warnTips("lendOutNumMsg2","请输入正确的数字");
			return false;
		}else if($("#lendoutnum2").val() <= '0'){
			warnTips("lendOutNumMsg2","请输入正确的数字");
			return false;
		}else if(Number($("#lendoutnum2").val()) >Number(stockNum2)){
			warnTips("lendOutNumMsg2","外借数量不正确");
			return false;
		}
		if($("#searchEndtimeStr2").val() == 0){
			warnTips("searchEndtimeStrMsg2","请选择预计归还时间");
			return false;
		}
}
	
	
	var type = $('[name=test]:checked').val();
	var $block = $('.J-block' + type);
	
	$block.find('').val()
//	console.log("成功");
	$('.J-form').submit();
		 
//    $.ajax({
//        type: "POST",
//        url: "/warehouse/warehousesout/saveAddLendOutInfo.do",
//        data: $('.J-form').serialize(),
//        dataType: 'json',
//        success: function (data) {
//        	window.location.href = '';
//        },
//        error: function (data) {
//            if (data.status == 1001) {
//                layer.alert("当前操作无权限")
//            }
//        }
//    });
}
