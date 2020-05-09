$(function() {
	//品牌信息
	initBrand();
	//其它信息录入控制
	$("input[name^='attributedesc']").bind("change",function(){
		checkLogin();
		if($(this).val() != ''){
			$(this).parent().find("input[name='attributeId']").attr('checked','checked');
		}
	});
	
	//综合医院控制
	var p = $("input[name='attributeId'][value='5_93']").prop("checked");
	if(!p){
		$("li[alt='5']").remove();
	}
	
	//是否是临床分销
	var traderCustomerCategoryIds = "";
	$.each($("#customer_category_div").find("select"),function(i,n){
		if($(this).val() == 5){
			$("input[name='isLcfx']").val(1);
		}
		traderCustomerCategoryIds += $(this).val() + ',';
	});
	$("#traderCustomerCategoryIds").val(traderCustomerCategoryIds);
	
	//经营地区更改
	$("select[name='bussiness_province']").change(function(){
		checkLogin();
		var regionId = $(this).val();
		if(regionId > 0){
			$.ajax({
				type : "POST",
				url : page_url+"/system/region/getregion.do",
				data :{'regionId':regionId},
				dataType : 'json',
				success : function(data) {
					$option = "<option value='0'>请选择</option>";
					$.each(data.listData,function(i,n){
						$option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
					});
					$("select[name='bussiness_city'] option:gt(0)").remove();
					$("select[name='bussiness_zone'] option:gt(0)").remove();
					$("select[name='bussiness_city']").html($option);
					$("select[name='bussiness_zone']").html("<option value='0'>请选择</option>");
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}else if(regionId==0){
			$("select[name='bussiness_city'] option:gt(0)").remove();
			$("select[name='bussiness_zone'] option:gt(0)").remove();
		}
	});
	
	//经营地区更改
	$("select[name='bussiness_city']").change(function(){
		checkLogin();
		var regionId = $(this).val();
		if(regionId > 0){
			$.ajax({
				type : "POST",
				url : page_url+"/system/region/getregion.do",
				data :{'regionId':regionId},
				dataType : 'json',
				success : function(data) {
					$option = "<option value='0'>请选择</option>";
					$.each(data.listData,function(i,n){
						$option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
					});
					$("select[name='bussiness_zone'] option:gt(0)").remove();
					$("select[name='bussiness_zone']").html($option);
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	});
	
	//业务模式
	$("input[name='traderCustomer.directSelling']").change(function(){
		checkLogin();
		if($("input[name='traderCustomer.directSelling']").val()*1 !=0 && $("input[name='traderCustomer.directSelling']").val()*1<=100){
			$("input[name='traderCustomer.wholesale']").val(100-$("input[name='traderCustomer.directSelling']").val()*1);
		}else{
			$("input[name='traderCustomer.wholesale']").val("");
		}
	});
	$("input[name='traderCustomer.wholesale']").change(function(){
		checkLogin();
		if($("input[name='traderCustomer.wholesale']").val()*1 !=0 && $("input[name='traderCustomer.wholesale']").val()*1<=100){
			$("input[name='traderCustomer.directSelling']").val(100-$("input[name='traderCustomer.wholesale']").val()*1);
		}else{
			$("input[name='traderCustomer.directSelling']").val("");
		}
	});
	$("#customerForm").submit(function(){
		checkLogin();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		$(".warning").remove();
		var sb =true;
		var traderName = $("input[name='traderName']").val();
		var traderNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,\（\）\s]{2,128}$/;
		if(traderName == ""){
			warnTips("traderName","客户名称不能为空");
			return false;
		}
		if(traderName.length < 2 || traderName.length > 128){
			warnTips("traderName","客户名称长度为2-128个字符长度");
			return false;
		}
		if(!traderNameReg.test(traderName)){
			warnTips("traderName","公司名称不允许使用特殊字符");
			return false;
		}
		
		//客户是否存在
		$.ajax({
			type : "POST",
			url : page_url+"/trader/customer/gettraderbytradername.do",
			data : {traderName:traderName,'traderType':1},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.code == 0){
					//客户已经存在
					if(data.data.traderCustomer != null 
							&& data.data.traderCustomer.traderCustomerId != undefined 
							&& data.data.traderCustomer.traderCustomerId > 0
							&& data.data.traderCustomer.traderCustomerId != $("input[name='traderCustomer.traderCustomerId']").val()){
						sb = false;
						return false;
					}
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		
		if(!sb){
			warnTips("traderName","客户名称不允许重复");
			return false;
		}
		
		if($("select[name='province']").val() == 0){
			$("select[name='province']").addClass("errorbor");
			$("#zone").siblings('.warning').remove();
			$("#zone").after('<div class="warning">省份不允许为空</div>');
			return false;
		}
		if($("select[name='city']").val() == 0){
			$("select[name='city']").addClass("errorbor");
			$("#zone").siblings('.warning').remove();
			$("#zone").after('<div class="warning">地市不允许为空</div>');
			return false;
		}
		if($("select[name='zone']").val() == 0 && $("select[name='zone'] option").length > 1){
			$("select[name='zone']").addClass("errorbor");
			$("#zone").siblings('.warning').remove();
			$("#zone").after('<div class="warning">区县不允许为空</div>');
			return false;
		}
		
		var customer_category_ok = true;
		$.each($("#customer_category_div select"),function(i,n){
			if($(this).val() == 0){
				customer_category_ok = false;
			}
		});
		if(!customer_category_ok){
			$.each($("#customer_category_div select"),function(i,n){
				if($(this).val() == 0){
					customer_category_ok = false;
					$(this).addClass("errorbor");
				}
			});
			$("#customer_category_div").after('<div class="warning">客户类型不允许为空</div>');
			return false;
		}
		
		var show_fenxiao = false;
		$.each($("#customer_category_div > select"),function(i,n){
			var id = $(this).val();
			show_fenxiao = id==3||id==5?true:false;
		})
		
		if(show_fenxiao){
			if(($("input[name='traderCustomer.directSelling']").val()*1 !=0 
					|| $("input[name='traderCustomer.wholesale']").val()*1 !=0)
					&& ($("input[name='traderCustomer.directSelling']").val()*1+$("input[name='traderCustomer.wholesale']").val()*1 )!=100
					){
					warnTips("wholesale","直销比例与批发比例总和应为100%");
					return false;
				}
		}
		
		$.each($(".bt"),function(i,n){
			var tab = $(n).find('.infor_name').find('lable').html();
			var ok = false;
			$.each($(n).find('.inputradio').find("input[type='checkbox']"),function(j,m){
				if($(m).prop("checked")){
					ok = true;
				}
			});
			$.each($(n).find('.inputfloat').find("input[type='checkbox']"),function(j,m){
				if($(m).prop("checked")){
					ok = true;
				}
			});
			$.each($(n).find('.inputfloat').find("input[type='radio']"),function(o,p){
				if($(p).prop("checked")){
					ok = true;
				}
			});
			$.each($(n).find('.inputradio').find("input[type='radio']"),function(o,p){
				if($(p).prop("checked")){
					ok = true;
				}
			});
			$.each($(n).find("select"),function(k,l){
				if($(l).val() != 0){
					ok = true;
				}
			});
			if(!ok){
				sb = false;
				$.each($(n).find("select"),function(k,l){
					$(this).addClass("errorbor");
				});
				$(n).find("div:last").append('<div class="warning">'+'请选择'+tab+'</div>')
				return false;
			}
		});
		
		$.each($("input[name^='attributedesc']"),function(){
			var ok = true;
			if($(this).val().length >32){
				ok = false;
			}
			
			if(!ok){
				sb = false;
				layer.alert("其它长度不允许超过32个字符");
				return false;
			}
		});
		
		var registeredCapital = $("#registeredCapital").val();
//		var registeredCapitalReg = /^(([1-9]\d{0,9})|0)(\.\d{1,4})?$|^$/;
		
		if(registeredCapital !='' && registeredCapital.length > 64){
			warnTips("registeredCapitalSpan","注册资金长度不允许超过64个字符");
			return false;
		}
//		if(!registeredCapitalReg.test(registeredCapital)){
//			warnTips("registeredCapitalSpan","注册资金格式错误");
//			return false;
//		}
		
		return sb;
	});
	
	$("input[name='traderName']").change(function(){
		checkLogin();
		$(".warning").remove();
		$("input[name='traderName']").removeClass("errorbor");
		if($(this).val() != ''){
			$.ajax({
				type : "POST",
				url : page_url+"/trader/customer/gettraderbytradername.do",
				data : {traderName:$(this).val(),'traderType':1},
				dataType : 'json',
				async : false,
				success : function(data) {
					if(data.code == 0){
						//客户已经存在
						if(data.data.traderCustomer != null 
								&& data.data.traderCustomer.traderCustomerId != undefined 
								&& data.data.traderCustomer.traderCustomerId > 0
								&& data.data.traderCustomer.traderCustomerId != $("input[name='traderCustomer.traderCustomerId']").val()){
							warnTips("traderName","客户名称不允许重复");
							return false;
						}
						//客户已经是供应商
						if(data.data.traderSupplier !=null && data.data.traderSupplier.traderSupplierId != undefined && data.data.traderSupplier.traderSupplierId > 0){
							var regionIds = data.data.areaIds.split(",");
							if(regionIds[0] != undefined){
								$("select[name='province']").val(regionIds[0]);
								$.ajax({
									type : "POST",
									url : page_url+"/system/region/getregion.do",
									data :{'regionId':regionIds[0]},
									dataType : 'json',
									async : false,
									success : function(data) {
										$option = "<option value='0'>请选择</option>";
										$.each(data.listData,function(i,n){
											var selected = data.listData[i]['regionId'] == regionIds[1] ? "selected" : "";
											$option += "<option value='"+data.listData[i]['regionId']+"' "+selected+">"+data.listData[i]['regionName']+"</option>";
										});
										$("select[name='city'] option:gt(0)").remove();
										$("select[name='zone'] option:gt(0)").remove();
										$("select[name='city']").html($option);
										$("select[name='zone']").html("<option value='0'>请选择</option>");
									}
								});
							}
							if(regionIds[1] != undefined){
								$.ajax({
									type : "POST",
									url : page_url+"/system/region/getregion.do",
									data :{'regionId':regionIds[1]},
									dataType : 'json',
									async : false,
									success : function(data) {
										$option = "<option value='0'>请选择</option>";
										$.each(data.listData,function(i,n){
											var selected = data.listData[i]['regionId'] == regionIds[2] ? "selected" : "";
											$option += "<option value='"+data.listData[i]['regionId']+"' "+selected+">"+data.listData[i]['regionName']+"</option>";
										});
										$("select[name='zone'] option:gt(0)").remove();
										$("select[name='zone']").html($option);
									}
								});
							}
						}
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	});
	initCustomerBefore();
});


function initCustomerBefore(){
	$.each($("#customer_category_div").find("select"),function(){
		var customerNature=$(this).val();
		if(customerNature==3||customerNature==4||customerNature==5||customerNature==6){
			$("#customerNatureBefore").val(customerNature);
		}
	});
}
//员工人数\年销售额\客户来源\首次询价方式\战略合作伙伴
function initCustomerInfo(){
	checkLogin();
	$.ajax({
		type : "POST",
		url : page_url+"/trader/customer/gettradercustomeroption.do",
		dataType : 'json',
		success : function(data) {
			if(data.code == 0){
				var attr =  eval('(' + data.param + ')');
				$.each(attr,function(i,n){
					if(i == 1016){//员工人数
						var opt = "<option value='0'>请选择</option>";
						$.each(n,function(j,m){
							opt += "<option value='"+m['sysOptionDefinitionId']+"'>"+m['title']+"</option>";
							
						});
						$("select[name='traderCustomer.employees']").html(opt);
					}
					if(i == 1017){//年销售额
						var opt = "<option value='0'>请选择</option>";
						$.each(n,function(j,m){
							opt += "<option value='"+m['sysOptionDefinitionId']+"'>"+m['title']+"</option>";
							
						});
						$("select[name='traderCustomer.annualSales']").html(opt);
					}
					if(i == 1018){//销售模式
						var radio = "";
						$.each(n,function(j,m){
							radio += "<input type='radio' name='traderCustomer.salesModel' value='"+m['sysOptionDefinitionId']+"'/> <label class='mr8'>"+m['title']+"</label>";
							
						});
						$("#salesModel_div").html(radio);
					}
					if(i == 1009){//客户来源
						var radio = "";
						$.each(n,function(j,m){
							radio += "<input type='radio' name='traderCustomer.customerFrom' value='"+m['sysOptionDefinitionId']+"'/> <label class='mr8'>"+m['title']+"</label>";
							
						});
						$("#customerFrom_div").html(radio);
					}
					if(i == 1010){//首次询价方式
						var radio = "";
						$.each(n,function(j,m){
							radio += "<input type='radio' name='traderCustomer.firstEnquiryType' value='"+m['sysOptionDefinitionId']+"'/> <label class='mr8'>"+m['title']+"</label>";
							
						});
						$("#firstEnquiryType").html(radio);
					}
					if(i == 1033){//战略合作伙伴
						var li = "";
						$.each(n,function(j,m){
							li += "<li><input type='checkbox' name='attributeId' value='26_"+m['sysOptionDefinitionId']+"' /><label>"+m['title']+"</label></li>";
						});
						
						$("#zlhzhb_ul").html(li);
					}
				});
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}

//诊所单选控制
function attrCheck(obj){
	checkLogin();
	if($(obj).prop('checked')){
		$.each($(obj).parent().siblings('li').find("input[type='checkbox']"),function(i,n){
			if($(this).prop('checked')){
				$(this).prop('checked',false);
			}
		});
	}
	
}

//客户分类初始化
function initCustomerCategory(){
	checkLogin();
	$.ajax({
		type : "POST",
		url : page_url+"/trader/customer/gettradercustomercategory.do",
		data :{'parentId':0},
		dataType : 'json',
		success : function(data) {
			if(data.code == 0){
				$option = "<option value='0'>请选择</option>";
				$.each(data.listData,function(i,n){
					$option += "<option value='"+data.listData[i]['traderCustomerCategoryId']+"'>"+data.listData[i]['customerCategoryName']+"</option>";
				});
				
				$("select[name='customer_category']").html($option);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}


function saveBaseInfo() {
	var newTraderName=$("#traderName").val();
	var oldTraderName=$("#traderNameBefore").val();
	if(oldTraderName!=newTraderName){
		$("#isCheckAptitudeStatus").val(1)
	}
	let status=$("#isCheckAptitudeStatus").val();
	if(status==1){
		let traderCustomerId=$("#traderCustomerId").val();
		$.ajax({
			type : "POST",
			url : page_url+"/trader/customer/getAptitudeStatus.do",
			data :{'traderCustomerId':traderCustomerId},
			dataType : 'json',
			success : function(data) {
				if(data.code == 0){
					let status=data.data
					if(status==0||status==1){
						var index1 = layer.confirm("公司信息变更，确定后将重置资质审核状态为待审核状态。", {
							btn: ['确定', '取消'] //按钮
						}, function () {
							layer.close(index1)
							$("#save").click();
						}, function () {
							// $("#isCheckAptitudeStatus").val(0);
							// $("#save").click();
						});
					}else{
						$("#isCheckAptitudeStatus").val(0);
						$("#save").click();
					}
				}else{
					layer.alert(data.message)
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}else{
		$("#save").click();
	}
}
//客户分类更改
function changeCate(obj){
	checkLogin();
	var id = $(obj).val();
	$(obj).nextAll("select").remove();
	if(id > 0){
		$.ajax({
			type : "POST",
			url : page_url+"/trader/customer/gettradercustomercategory.do",
			data :{'parentId':id},
			dataType : 'json',
			success : function(data) {
				if(data.code == 0){
					if(data.listData.length > 0){
						var name = "customerCategory"+id
						$option = " <select class='input-middle mr6' name='"+name+"' onchange='changeCate(this);'><option value='0'>请选择</option>";
						$.each(data.listData,function(i,n){
							$option += "<option value='"+data.listData[i]['traderCustomerCategoryId']+"'>"+data.listData[i]['customerCategoryName']+"</option>";
						});
						$option += "</select>";
						$(obj).after($option);
					}
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
	var customerNatureBefore=$("#customerNatureBefore").val();
	if((id==3||id==4||id==5||id==6)){
		if(id!=customerNatureBefore){
			$("#isCheckAptitudeStatus").val(1)
		}else{
			$("#isCheckAptitudeStatus").val(0)
		}
	}
	$("#traderCustomerCategoryId").val(id);
	changeAttribute();
	//是否是临床分销
	var traderCustomerCategoryIds = "";
	$.each($(obj).parent().find("select"),function(){
		if($(this).val() == 5){
			$("input[name='isLcfx']").val(1);
		}
		
		traderCustomerCategoryIds += $(this).val() + ',';
	});
	
	$("#traderCustomerCategoryIds").val(traderCustomerCategoryIds);
	
	$("input[name='traderCustomer.wholesale']").val("");
	$("input[name='traderCustomer.directSelling']").val("");
}
//更改属性
function changeAttribute(){
	checkLogin();
	$(".customerAttribute").remove();
	
	var show_fenxiao = false;
	$.each($("#customer_category_div > select"),function(i,n){
		var id = $(this).val();
		if(id > 0){
			show_fenxiao = id==3||id==5?true:false;
			$.ajax({
				type : "POST",
				url : page_url+"/trader/customer/gettradercustomerattribute.do",
				data :{'traderCustomerCategoryId':id},
				dataType : 'json',
				success : function(data) {
					if(data.code == 0){
						if(data.listData.length > 0){
							$.each(data.listData,function(i,n){
								var c = "";
								if(data.listData[i]['isSelected'] == 1){
									c = "bt";
								}
								var li = '<li class="customerAttribute '+c+'"><div class="infor_name mt0">';
								if(data.listData[i]['isSelected'] == 1){
									li += '<span>*</span>';
								}
								li += '<lable>'+data.listData[i]['categoryName']+'</lable>';
								li += '</div>';
								console.log(data.listData[i]['inputType']);
								if(data.listData[i]['inputType'] == 0){//单选
									li += '<div class="f_left inputfloat inputradio"><ul>';
								}
								if(data.listData[i]['inputType'] == 1){//复选
									li += '<div class="f_left inputradio"><ul>';
								}
								if(data.listData[i]['inputType'] == 2){//下拉
									li += '<div class="f_left">';
									
									if(data.listData[i]['scope'] == 1013 ){ //所有制
										li += '<select class="input-middle" name="traderCustomer.ownership"><option value="0">请选择</option>';
									}
									if(data.listData[i]['scope'] == 1014 ){ //医学类型
										li += '<select class="input-middle" name="traderCustomer.medicalType"><option value="0">请选择</option>';
									}
									if(data.listData[i]['scope'] == 1015 ){ //医院等级
										li += '<select class="input-middle" name="traderCustomer.hospitalLevel"><option value="0">请选择</option>';
									}
								}
								
								$.each(data.listData[i]['sysOptionDefinitions'],function(j,m){
									if(data.listData[i]['inputType'] == 0){//单选
										li += '<li>';
										li += '<input type="checkbox" onclick="attrCheck(this);" name="attributeId" value="'+data.listData[i]['attributeCategoryId']+'_'+data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId']+'" /> <label>'+data.listData[i]['sysOptionDefinitions'][j]['title']+'</label>';
										if((data.listData[i]['scope'] == 1027 && data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId'] == 109 ) 
												|| (data.listData[i]['scope'] == 1028 && data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId'] == 115)
												|| (data.listData[i]['scope'] == 1032 && data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId'] == 302)){
											li += '<input type="text" class="input-small mt0 heit18" name="attributedesc_'+data.listData[i]['attributeCategoryId']+'_'+data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId']+'"/>';
										}
										
										li += '</li>';
									}
									
									if(data.listData[i]['inputType'] == 1){//复选
										var onclick = '';
										if(data.listData[i]['attributeCategoryId'] == 5 && data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId'] == 93){
											onclick += 'onclick="getChildAttr(this,5)"';
										}
										li += '<li>';
										li += '<input type="checkbox" name="attributeId" value="'+data.listData[i]['attributeCategoryId']+'_'+data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId']+'" '+onclick+' /> <label>'+data.listData[i]['sysOptionDefinitions'][j]['title']+'</label>';
										if((data.listData[i]['scope'] == 1027 && data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId'] == 109 ) 
												|| (data.listData[i]['scope'] == 1028 && data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId'] == 115)
												|| (data.listData[i]['scope'] == 1032 && data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId'] == 302)){
											li += '<input type="text" class="input-small mt0 heit18" name="attributedesc_'+data.listData[i]['attributeCategoryId']+'_'+data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId']+'"/>';
										}
										
										li += '</li>';
									}
									if(data.listData[i]['inputType'] == 2){//下拉
										if(data.listData[i]['scope'] == 1013
												|| data.listData[i]['scope'] == 1014
												|| data.listData[i]['scope'] == 1015
												){
											li += '<option value="'+data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId']+'">'+data.listData[i]['sysOptionDefinitions'][j]['title']+'</option>';
										}else{
											li += '<option value="'+data.listData[i]['attributeCategoryId']+'_'+data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId']+'">'+data.listData[i]['sysOptionDefinitions'][j]['title']+'</option>';
										}
									}
									
								});
								if(data.listData[i]['inputType'] == 0){//单选
									li += '</ul></div>';
								}
								if(data.listData[i]['inputType'] == 1){//复选
									li += '</ul></div>';
								}
								if(data.listData[i]['inputType'] == 2){//下拉
									li += '</selected></div>';
								}
								li += '</li>';
								
								$("#attr_brfore_li").after(li);
							});
							//其它信息录入控制
							$("input[name^='attributedesc']").bind("change",function(){
								if($(this).val() != ''){
									$(this).parent().find("input[name='attributeId']").attr('checked','checked');
								}
							});
						}
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	});
	
	if(show_fenxiao){
		$("li[alt='fenxiao']").show();
	}else{
		$("li[alt='fenxiao']").hide();
		$("#bussinessArea_show").html("");
		$("#bussinessBrand_show").html("");
		$("#agentBrand_show").html("");
		$("input[name='directSelling']").val('');
		$("input[name='wholesale']").val('');
		$.each($("input[name='salesModel']"),function(){
			if($(this).prop('checked')){
				$(this).prop('checked',false);
			}
		});
	}
}

//获取子集属性 id=parentId
function getChildAttr(obj,id){
	checkLogin();
	if($(obj).prop("checked")){
		//选择属性
		if(id > 0){
			$.ajax({
				type : "POST",
				url : page_url+"/trader/customer/gettradercustomerchildattribute.do",
				data :{'attributeCategoryId':id},
				dataType : 'json',
				success : function(data) {
					if(data.code == 0){
						if(data.listData.length > 0){
							$.each(data.listData,function(i,n){
								var c = "";
								if(data.listData[i]['isSelected'] == 1){
									c = "bt";
								}
								var li = '<li class="customerAttribute '+c+'" alt="'+id+'"><div class="infor_name mt0">';
								if(data.listData[i]['isSelected'] == 1){
									li += '<span>*</span>';
								}
								li += '<lable>'+data.listData[i]['categoryName']+'</lable>';
								li += '</div><div class="f_left inputradio"><ul>';
								$.each(data.listData[i]['sysOptionDefinitions'],function(j,m){
									li += '<li><input type="checkbox" name="attributeId" value="'+data.listData[i]['attributeCategoryId']+'_'+data.listData[i]['sysOptionDefinitions'][j]['sysOptionDefinitionId']+'" '+onclick+' /> <label>'+data.listData[i]['sysOptionDefinitions'][j]['title']+'</label>';
									li += '</li>';
								});
								li += '</ul></div></li>';
								
								$(obj).parent().parent().parent().parent().after(li);
							});
						}
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	}else{
		//取消属性
		$("li[alt="+id+"]").remove();
	}
}

//添加经营地区TAG
function addBussinessArea(){
	checkLogin();
	var province = $("select[name='bussiness_province']").val();
	var city = $("select[name='bussiness_city']").val();
	var zone = $("select[name='bussiness_zone']").val();
	if(province == 0 && city == 0 && zone == 0){
		return false;
	}
	
	var provinceText = $('select[name="bussiness_province"]').find(':selected').text();
	var cityText = $('select[name="bussiness_city"]').find(':selected').text();
	var zoneText = $('select[name="bussiness_zone"]').find(':selected').text();
	
	var area_id = '';
	var area_ids = '';
	var area_str = '';
	
	if(zone > 0){
		area_id = zone;
		area_ids = province+","+city+","+zone;
    	area_str = provinceText+" "+cityText +" "+zoneText;
	}else if(city > 0){
		area_id = city;
		area_ids = province+","+city;
    	area_str = provinceText+" "+cityText ;
	}else if(province > 0){
		area_id = province;
		area_ids = province;
    	area_str = provinceText;
	}
	
	var ok = true;
	$.each($("input[name='bussinessAreaId']"),function(i,n){
		if($(this).val() == area_id){
			ok = false;
		}
	});
	
	if(ok){
		var bussinsessLi = "<li class='bluetag'>"+area_str
		+"<input type='hidden' name='bussinessAreaId' value='"+area_id+"'><input type='hidden' name='bussinessAreaIds' value='"+area_ids+"'><i class='iconbluecha' onclick='delTag(this);'></i></li>";
		
		$("#bussinessArea_show").append(bussinsessLi);
		
		initBussinessRegion();
		
		$("#bussinessArea_show").parent(".addtags").show();
	}else{
		layer.tips("选择的经营地区已经存在","#addBussinessArea",{time:1000});
	}
}

//删除标签
function delTag(obj){
	checkLogin();
	var div = $(obj).parent().parent().parent();
	$(obj).parent().remove();
	if($(div).find("li").length == 0)
	{
		$(div).hide();
	}
}

//经营地区
function initBussinessRegion(){
	$.ajax({
		type : "POST",
		url : page_url+"/system/region/getregion.do",
		data :{'regionId':1},
		dataType : 'json',
		success : function(data) {
			$option = "<option value='0'>请选择</option>";
			$.each(data.listData,function(i,n){
				$option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
			});
			$("select[name='bussiness_city'] option:gt(0)").remove();
			$("select[name='bussiness_zone'] option:gt(0)").remove();
			$("select[name='bussiness_province']").html($option);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}

//品牌列表
function initBrand(){
	checkLogin();
	$.ajax({
		type : "POST",
		url : page_url+"/goods/brand/getallbrand.do",
		dataType : 'json',
		success : function(data) {
			$option = "<option value='0'>请选择</option>";
			$.each(data.listData,function(i,n){
				$option += "<option value='"+data.listData[i]['brandId']+"'>"+data.listData[i]['brandName']+"</option>";
			});
			$("select[name='bussinessBrands']").html($option);
			$("select[name='agentBrands']").html($option);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}

//搜索经营品牌
function searchBussinessBrand(){
	checkLogin();
	var brand = $("input[name='bussinessBrandKey']").val();
	$.ajax({
		type : "POST",
		url : page_url+"/goods/brand/getallbrand.do",
		data : {brandName:brand},
		dataType : 'json',
		success : function(data) {
			$option = "<option value='0'>请选择</option>";
			$.each(data.listData,function(i,n){
				var selected = "";
				if(brand == data.listData[i]['brandName']){
					selected = "selected";
				}
				$option += "<option value='"+data.listData[i]['brandId']+"' "+selected+">"+data.listData[i]['brandName']+"</option>";
			});
			$("select[name='bussinessBrands']").html($option);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}
//搜索代理品牌
function searchAgentBrand(){
	checkLogin();
	var brand = $("input[name='agentBrandKey']").val();
	$.ajax({
		type : "POST",
		url : page_url+"/goods/brand/getallbrand.do",
		data : {brandName:brand},
		dataType : 'json',
		success : function(data) {
			$option = "<option value='0'>请选择</option>";
			$.each(data.listData,function(i,n){
				var selected = "";
				if(brand == data.listData[i]['brandName']){
					selected = "selected";
				}
				$option += "<option value='"+data.listData[i]['brandId']+"' "+selected+">"+data.listData[i]['brandName']+"</option>";
			});
			$("select[name='agentBrands']").html($option);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}
//添加经营品牌
function addBussinessBrand(){
	checkLogin();
	var brandId = $("select[name='bussinessBrands']").val();
	if(brandId == 0){
		return false;
	}
	
	brandName = $('select[name="bussinessBrands"]').find(':selected').text();
	
	var ok = true;
	$.each($("input[name='bussinessBrandId']"),function(i,n){
		if($(this).val() == brandId){
			ok = false;
		}
	});
	
	if(ok){
		var bussinsessLi = "<li class='bluetag'>"+brandName
		+"<input type='hidden' name='bussinessBrandId' value='"+brandId+"'><i class='iconbluecha' onclick='delTag(this);'></i></li>";
		
		$("#bussinessBrand_show").append(bussinsessLi);
		$('select[name="bussinessBrands"]').val(0);
		
		$("#bussinessBrand_show").parent(".addtags").show();
	}else{
		layer.tips("选择的品牌已经存在","#addBussinessBrand",{time:1000});
	}
}

//添加代理品牌
function addAgentBrand(){
	checkLogin();
	var brandId = $("select[name='agentBrands']").val();
	if(brandId == 0){
		return false;
	}
	
	brandName = $('select[name="agentBrands"]').find(':selected').text();
	
	var ok = true;
	$.each($("input[name='agentBrandId']"),function(i,n){
		if($(this).val() == brandId){
			ok = false;
		}
	});
	if(ok){
		var agentLi = "<li class='bluetag'>"+brandName
		+"<input type='hidden' name='agentBrandId' value='"+brandId+"'><i class='iconbluecha' onclick='delTag(this);'></i></li>";
		$("#agentBrand_show").append(agentLi);
		$('select[name="agentBrands"]').val(0);
		
		$("#agentBrand_show").parent(".addtags").show();
	}else{
		layer.tips("选择的品牌已经存在","#addAgentBrand",{time:1000});
	}
}