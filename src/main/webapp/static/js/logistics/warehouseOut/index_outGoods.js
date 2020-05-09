$(function(){
	$("input").blur(function(){
		if($(this).val()==""){
			$(this).val("0");
		}
		var num = parseInt($("#"+$(this).attr("id")+"Num").text());
		var cnt = parseInt($(this).val());
		var pickCnt =0;
		if(cnt>num){
			layer.alert("出库数量不允许大于可出库数量！")
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
function search() {
	checkLogin();
	var pickNums ="";
    var goodsListNum = $("#goodsListNum").val();
    var allPickNum = 0;
	for(var i=1;i<=goodsListNum;i++){
		var pickNum = parseInt($("#pickNumpickCnt"+i).val());//本次拣货数量
		var pickNeedNum = parseInt($("#pickNeed"+i).val());//未拣货库存
		var needNum = parseInt($("#need"+i).val());//需拣货数
		allPickNum+=pickNum;
		if( pickNum<pickNeedNum){
			if(pickNum> needNum){
				layer.alert("拣货数量不允许超过需拣货数量！");
				return false;
			}
		}else if(pickNum>pickNeedNum){
			layer.alert("拣货数量不允许超过可用库存数量！");
			return false;
		}
    }
	if(allPickNum==0){
		layer.alert("请至少拣货一件商品！");
		return false;
	}
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
//出库提交
function searchOut() {
	checkLogin();
	var pickNums ="";
    var goodsListNum = $("#goodsListNum").val();
    var allNum = 0;
    var flag = 0;
	for(var i=1;i<=goodsListNum;i++){
		var jhNum = $("#jh"+i).val();
		var chNum = $("#pickNumpickCnt"+i).val();
		allNum+= chNum;
		if( chNum==0){
		}else{
			if(parseInt(chNum)>parseInt(jhNum)){
				layer.alert("出库数量不允许超过拣货未发数量！");
				return ;
			} else if(parseInt(chNum)<parseInt(jhNum)){
					flag = 1;
			}
		}
	}
	if(allNum==0){
		layer.alert("请至少出库一件商品！");
		return ;
	}
	if(flag==1){
		layer.confirm("存在出库数量小于拣货未发数量，是否确认出库？", {
			btn : [ '确定', '取消' ]
		}, function() {
			doShow(goodsListNum);
		});
	}else{
		doShow(goodsListNum);
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
