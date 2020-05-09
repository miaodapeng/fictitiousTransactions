
function exportPayApplyList(){
	checkLogin();
	location.href = page_url + '/report/finance/exportPayApplyList.do?' + $("#search").serialize();
}

function matchBankBill(payApplyId,bankBillId,tranFlow,taskId,bankTag,payType){
	layer.confirm('确认匹配?', {title:'操作确认'},function(index){
			var paymentType = 0;
			if(bankTag == 2){
				//南京银行
				paymentType = 641;
			}else if(bankTag == 3){
				//中国银行
				paymentType = 716;
			}
			var formToken = $("input[name='formToken']").val();
			if(payType == 517){
				var url = page_url + '/finance/buyorder/payApplyPassByBankBill.do';
			}else{
				var url = page_url + '/finance/capitalbill/saveAddAfterCapitalBillForBank.do';
			}
			$.ajax({
				async : false,
				url : url,
				data : {
					"payApplyId" : payApplyId,
					"bankBillId" : bankBillId,
					"tranFlow" : tranFlow,
					"taskId":taskId,
					"paymentType":paymentType,
					"formToken":formToken
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.code == 0){
							layer.alert(data.message, { icon : 1});
							window.location.reload();
					}else{
						layer.alert(data.message, { icon : 2});
						window.location.reload();
					}
				},error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		  layer.close(index);
		}); 
}

// add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. start
//全选
function checkall(obj,name){
	checkLogin();
	if($(obj).is(":checked")){
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop("checked",true);
	}else{
		$("input[name='"+$(obj).val()+"']").not(':disabled').prop('checked',false);
	}
}

// 批量确认
function batchConfirm(){
	// 获取所有选中的单选框
	var checkedObj = $("input[name='checkbox_one']:checked");
	if(checkedObj && checkedObj.length > 0){
		// 再次确认是否批量操作
		layer.confirm('确定同意所选数据吗?', {title:'操作确认'},function(index){
			layer.close(index);
			// 循环
			var formToken = $("input[name='formToken']").val();

			var ids = new Array();
			for (var i = 0; i < checkedObj.length; i++) {
				// 找到被选中的单选框
				if($(checkedObj[i]).is(":checked")){
					var id = $(checkedObj[i]).val();
					ids.push(id);
				}
			}
			console.log(ids);
			var formToken = $("input[name='formToken']").val();
			$.ajax({
				async : false,
				url :page_url + "/order/buyorder/batchComplementTask.do",
				data : {
					"ids" : ids,
					"formToken" : formToken
				},
				type : "POST",
				dataType : "json",
				//ajax传递数组使用
				traditional: true,
				success : function(data) {
					if(data.code == 0){
						layer.alert(data.message, { icon : 1});
						sleep(500)
						window.location.href=page_url+"/finance/payapply/getPayApplyListPage.do";
						/*window.location.reload();*/
					}else{
						layer.alert(data.message, { icon : 2});
						window.location.reload();
					}
				},error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
			layer.close(index);
			// 页面重新加载
			window.location.reload();
		});
	}
	// 如果没有选中的单选框
	else{
		layer.alert("当前无选中的按钮");
	}
	// add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能. end


}

function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while (true) {
		now = new Date();
		if (now.getTime() > exitTime)
			return;
	}
}