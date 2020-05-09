$(function() {
	$("#search").click(function(){
		checkLogin();
		var name=$("#supplierName").val();
		if(name == ''){
			$("#supplierName").addClass("errorbor")
			$("#none").removeClass("none");
			return  false;
		}else{
			$("#supplierName").removeClass("errorbor")
			$("#none").addClass("none");
		}
		$("#myform1").submit();
	});

});

function unableChoose(){
	layer.alert("请先审核通过该供应商，再选择");
}
function addSelectSupplier(traderSupplierName,traderSupplierId,traderId,supplierComments,periodBalance,periodDay){
	checkLogin();
	$("#searchTraderName",window.parent.document).hide();
	$("#traderName",window.parent.document).val(traderSupplierName);
	$("#traderSupplierId",window.parent.document).val(traderSupplierId);
	$("#traderId",window.parent.document).val(traderId);
	$("#periodBalance",window.parent.document).val(periodBalance);
	$("#periodAmount",window.parent.document).html(periodBalance);
	$("#periodDay",window.parent.document).html(periodDay);
	$("#name",window.parent.document).html(traderSupplierName);
	$("#name",window.parent.document).removeClass("none");
	$("#errorMes",window.parent.document).addClass("none");
	$("#research",window.parent.document).removeClass("none");
	var direct = $("#deliveryDirect",window.parent.document).val();
	
		$.ajax({
			url:page_url+'/order/buyorder/getContactsAddress.do',
			data:{"traderId":traderId,"traderType":2},
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code ==0){
					$("#contact",window.parent.document).removeClass("none");
					var contactSelect = $("select[name='traderContactStr']",window.parent.document);
					contactSelect.empty();
					var contact = "<option value=''>请选择</option>";
					for(var i=0; i<data.data.length;i++){
						if(data.data[i].isDefault==1){
							contact += "<option selected='selected' value='"+data.data[i].traderContactId+"|"
										+data.data[i].name+"|"+data.data[i].mobile+"|"+data.data[i].telephone+"'>"+data.data[i].name+"/"+data.data[i].mobile+"/"+data.data[i].telephone+"</option>";
						}else{
							contact += "<option value='"+data.data[i].traderContactId+"|"
										+data.data[i].name+"|"+data.data[i].mobile+"|"+data.data[i].telephone+"'>"+data.data[i].name+"/"+data.data[i].mobile+"/"+data.data[i].telephone+"</option>";
						}
						
					}
					contactSelect.append(contact);
					
					$("#address",window.parent.document).removeClass("none");
					var addressSelect = $("select[name='traderAddressStr']",window.parent.document);
					addressSelect.empty();
					var address = "<option value=''>请选择</option>";
					for(var i=0; i<data.listData.length;i++){
						if(data.listData[i].traderAddress.isDefault==1){
							address += "<option selected='selected' value='"+data.listData[i].traderAddress.traderAddressId
										+"|"+data.listData[i].area+"|"+data.listData[i].traderAddress.address+"'>"
										+data.listData[i].area+"  "+data.listData[i].traderAddress.address+"</option>";
						}else{
							address += "<option value='"+data.listData[i].traderAddress.traderAddressId
										+"|"+data.listData[i].area+"|"+data.listData[i].traderAddress.address+"'>"
										+data.listData[i].area+"  "+data.listData[i].traderAddress.address+"</option>";
						}
						
					}
					addressSelect.append(address);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		$("#comment",window.parent.document).removeClass("none");
		$("#supplierComments",window.parent.document).html(supplierComments);
		$("#traderComments",window.parent.document).val(supplierComments);
	
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    parent.layer.close(index);

}

