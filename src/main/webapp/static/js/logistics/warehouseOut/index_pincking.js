$(function(){
	$("input").blur(function(){
		if($(this).val()==""){
			$(this).val("0");
		}
		var num = parseInt($("#"+$(this).attr("id")+"Num").text());
		var cnt = parseInt($(this).val());
		var pickCnt =0;
		if(cnt>num){
			layer.alert("拣货数量不允许大于库存数量！")
			$(this).val(0);
			$("input[name='"+$(this).attr("name")+"']").each(function(){
			    pickCnt += parseInt($(this).val());
			  });
			$("#pickNum"+$(this).attr("name")).val(parseInt(pickCnt));
			return false;
		}
		$("input[name='"+$(this).attr("name")+"']").each(function(){
		    pickCnt += parseInt($(this).val());
		  });
		$("#pickNum"+$(this).attr("name")).val(pickCnt);
	}) 
	closeFrontPage(true);
})
//确认数量提交
function search_s() {
	//checkLogin();
	$("#searchSpan").removeAttr("onclick");
	var pickNums ="";
	var saleorderGoodsIds = "";
	var afterSalesGoodsIds = "";
	var count = 0;
    var goodsListNum = $("#goodsListNum").val();
    var allPickNum = 0;
	for(var i=1;i<=goodsListNum;i++){
	    var saleorderGoodsId = $("#saleorderGoodsIds" + i).val();
	    var afterSalesGoodsId = $("#afterSalesGoodsIds" + i).val();
	    if (count != 0){
			saleorderGoodsIds+=",";
			afterSalesGoodsIds+=",";
		};
	    count++;
	    saleorderGoodsIds+=saleorderGoodsId;
		afterSalesGoodsIds+=afterSalesGoodsId;
		var pickNum = parseInt($("#pickNumpickCnt"+i).val());//本次拣货数量
		var pickNeedNum = parseInt($("#pickNeed"+i).val());//未拣货库存
		var needNum = parseInt($("#need"+i).val());//需拣货数
		allPickNum+=pickNum;
		if( pickNum<pickNeedNum){
			if(pickNum> needNum){
				layer.alert("拣货数量不允许超过需拣货数量！");
				$("#searchSpan").attr("onclick","search_s();");
				return false;
			}
		}else if(pickNum>pickNeedNum){
			layer.alert("拣货数量不允许超过可用库存数量！");
			$("#searchSpan").attr("onclick","search_s();");
			return false;
		}
    }
	if(allPickNum==0){
		layer.alert("请至少拣货一件商品！");
		$("#searchSpan").attr("onclick","search_s();");
		return false;
	}
    for(var i=1;i<=goodsListNum;i++){
    	var vl="";
    	vl=$("#goodsId"+i).val()+"@"+$("#pickNumpickCnt"+i).val()+"_";
    	var wlNum = $("#wlistNum"+i).val();
    	for(var j=1;j<=wlNum;j++){
    		vl+= $("#pickCnt"+i+j).val();
    		var batchNumber = $("#batchNumber"+i+j).text();
    		var expirationDate = $("#expirationDate"+i+j).val();
    		var relatedId = $("#relatedId"+i+j).val();
    		var relatedType = $("#relatedType"+i+j).val();
			var warehouseGoodsOperateLogId = $("#warehouseGoodsOperateLogId"+i+j).val();
    		if(batchNumber == ""){
    			vl+= "!-1"
    		}else{
    			vl+= "!"+batchNumber;
    		}
    		if(expirationDate == ""){
    			vl+= "%0"
    		}else{
    			vl+= "%"+expirationDate;
    		}
    		if(relatedId ==""){
    			vl+= "+0";
    		}else{
    			vl+= "+"+relatedId;
    		}
    		if(relatedType==""){
    			vl+= "=0";
    		}else{
    			vl+= "="+relatedType;
    		}
    		if(warehouseGoodsOperateLogId==""){
    			vl+="*0,"
			}else{
    			vl+="*"+warehouseGoodsOperateLogId+",";
			}
    	}
    	pickNums+=vl+"#";
    }
    $("#pickNums").val(pickNums);
    $("#saleorderGoodsIds").val(saleorderGoodsIds);
    $("#afterSalesGoodsIds").val(afterSalesGoodsIds);
	$("#search").submit();
}
//出库提交
function searchOut() {
	checkLogin();
	var pickNums ="";
    var goodsListNum = $("#goodsListNum").val();
    var allNum = 0;
	for(var i=1;i<=goodsListNum;i++){
		var jhNum = $("#jh"+i).val();
		var chNum = $("#pickNumpickCnt"+i).val();
		allNum+= chNum;
		if( chNum==0){
			/*layer.alert("请至少出库一件商品！");
			return false;*/
		}else{
			if(chNum>jhNum){
				layer.alert("出库数量不允许超过拣货数量！");
				return ;
			}else{
				if(chNum<jhNum){
					layer.confirm("出库数量小于拣货数量，是否确认出库？", {
						btn : [ '确定', '取消' ]
					}, function() {
						doShow(goodsListNum);
					});
				}else{
					doShow(goodsListNum);
				}
			}
		}
    }
}
function doShow(goodsListNum){
	checkLogin();
	var pickNums ="";
	for(var i=1;i<=goodsListNum;i++){
    	var vl="";
    	vl=$("#goodsId"+i).val()+"@"+$("#pickNumpickCnt"+i).val()+"_";
    	var wlNum = $("#wlistNum"+i).val();
    	for(var j=1;j<=wlNum;j++){
    		vl+= $("#pickCnt"+i+j).val()+",";
    	}
    	pickNums+=vl+"#";
    }
    $("#pickNums").val(pickNums);
	$("#search").submit();
}
