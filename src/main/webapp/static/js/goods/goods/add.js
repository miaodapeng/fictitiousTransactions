$(function() {
	initGoodsInfo();
	$("#addGoodsForm").submit(function(){
		checkLogin();
		//alert($("input[name='goodsType']:checked").val())
		$(".warning").remove();
		//$("#brandIdMsg").html("");
		var goodsName = $("input[name='goodsName']").val().trim();
		if(goodsName == ""){
			warnTips("goodsName","商品名称不允许为空");
			return false;
		}
		// 商品名称 1个汉字2个字符
		if(strlen(goodsName) > 80)
		{
			warnTips("goodsName","商品名称不允许为空 / 商品名称不允许重复 / 商品名称最多不允许超过80个字符,一个汉字2个字符。");
			return false;
		}

        var fdStart = goodsName.indexOf("*");
        if( fdStart == 0 ){
            warnTips("goodsName","产品名称第一位不允许为特殊字符*");
            return false;
        }


		var sb =true;
		//商品名称是否存在
		$.ajax({
			type : "POST",
			url : page_url+"/goods/goods/validgoodsname.do",
			data : {goodsName:goodsName},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.code == 1){
					sb = false;
					return false;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		if(!sb){
			warnTips("goodsName","商品名称不允许重复");
			return false;
		}
		if($("#aliasName").val() != '' && $("#aliasName").val().length > 128){
			warnTips("aliasName","别名不允许超过128个字符");
			return false;
		}
		// modify by franlin.wu for[创建Sku时，不选择品牌信息可以提交成功（必填项品牌字段缺少必填校验）] at 2018-11-06 begin 
		var brandId = $("select[name='brandId']").val();
		if(undefined == brandId || '' == brandId || brandId == 0){
			
			warnTips("brandIdMsg","品牌名称不允许为空");
			return false;
		}
		// modify by franlin.wu for[创建Sku时，不选择品牌信息可以提交成功（必填项品牌字段缺少必填校验）] at 2018-11-06 begin 
		
		var model = $("#model").val();
		if(model.trim() == "")
		{
			warnTips("model","制造商型号不允许为空");
			return false;
		}
		
		if(strlen(model) > 40)
		{
			warnTips("model","制造商型号长度不允许超过40个字符,一个汉字2个字符。");
			return false;
		}
		
		if($("#manufacturer").val().trim() == ""){
			warnTips("manufacturer","厂家名称不允许为空");
			return false;
		}
		if($("#manufacturer").val().length > 256){
			warnTips("manufacturer","厂家名称长度不允许超过256个字符");
			return false;
		}
		
		if($("#supplyModel").val().length > 64){
			warnTips("supplyModel","供应商型号长度不允许超过64个字符");
			return false;
		}
		if($("#materialCode").val().length > 64){
			warnTips("materialCode","物料编码长度不允许超过64个字符");
			return false;
		}

		if($("#wikihref").val().length > 256){
			warnTips("wikihref","wiki链接长度不允许超过256个字符");
			return false;
		}
		
		// 商品类型 316:器械设备 ;327:耗材;318:试剂;653:高值耗材;319:其他
		var goodsType = $("input[name='goodsType']:checked").val();
		// 新国标分类  1388为非医疗器械
		var new_standardCategoryId = $("#standardCategoryId").val();
		
		// 管理类别  1类:339 ; 2类:340; 3类:341
		var manageLevel = $("input[name='manageCategoryLevel']:checked").val();
		// 旧国标分类
		var old_standardCategoryId = $("select[name='manageCategory']").val();
		if(typeof(goodsType) == "undefined")
		{
			warnTips("goods_type_div","商品类型不允许为空");
			return false;
		}
		// 商品类型不为试剂时,存在 新国标分类
		else if(goodsType != 318)
		{
			if(typeof(new_standardCategoryId) == "undefined" || -1 == new_standardCategoryId || 0 == new_standardCategoryId)
			{			
				warnTips("standardCategoryId","新国标分类不允许为空且请选择到最小分类");
				return false;
			}
			// 新国标与旧国标一致.  194为旧国标 非医疗器械
			if((new_standardCategoryId == 1388 && old_standardCategoryId != 194) || (new_standardCategoryId!= 1388 && old_standardCategoryId == 194) ) 
			{
				warnTips("manageCategory","新国标分类和旧国标分类类型不一致");
				return false;
			}
			// 当新国标分类不为非医疗器械时 ,管理类别不可为空
			if (new_standardCategoryId != 1388 && typeof(manageLevel) == "undefined") 
			{
				warnTips("manage_category_level_div","管理类别不允许为空");
				return false;
			}
		}
		// 试剂
		else
		{		
			// 当新国标分类不为非医疗器械时 ,管理类别不可为空
			if (new_standardCategoryId != 1388 && typeof(manageLevel) == "undefined") 
			{
				warnTips("manage_category_level_div","管理类别不允许为空");
				return false;
			}
		}
		
		if(old_standardCategoryId == 0)
		{
			warnTips("manageCategory","旧国标分类不允许为空");
			return false;
		}
		if($("#categoryId").val() == 0 || $("#categoryId").val() == '')
		{
			warnTips("categoryId","商品运营分类不允许为空且请选择到最小分类");
			return false;
		}
		var unitId = $("select[name='unitId']").val();
		if(undefined == unitId || '' == unitId || unitId == 0){
			warnTips("unitId","单位不允许为空");
			return false;
		}
		
		var numberReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		var goodsLength = $("input[name='goodsLength']").val();
		var goodsWidth = $("input[name='goodsWidth']").val();
		var goodsHeight = $("input[name='goodsHeight']").val();
		var netWeight = $("input[name='netWeight']").val();
		var packageLength = $("input[name='packageLength']").val();
		var packageWidth = $("input[name='packageWidth']").val();
		var packageHeight = $("input[name='packageHeight']").val();
		var grossWeight = $("input[name='grossWeight']").val();
		if ((goodsLength != '' && !numberReg.test(goodsLength))	|| (goodsWidth != '' && !numberReg.test(goodsWidth)) || (goodsHeight != '' && !numberReg.test(goodsHeight))	|| (netWeight != '' && !numberReg.test(netWeight))) {
			warnTips("size_span","格式不正确");
			return false;
		}
		if (goodsLength.length > 10 || goodsWidth.length > 10 || goodsHeight.length > 10 || netWeight.length > 10) {
			warnTips("size_span","长度不允许超过10个字符");
			return false;
		}
		
		if ((packageLength != '' && !numberReg.test(packageLength)) || (packageWidth != '' && !numberReg.test(packageWidth)) || (packageHeight != '' && !numberReg.test(packageHeight)) | (grossWeight != '' && !numberReg.test(grossWeight))) {
			warnTips("package_span","格式不正确");
			return false;
		}
		if (packageLength.length > 10 || packageWidth.length > 10 || packageHeight.length > 10 || grossWeight.length > 10) {
			warnTips("package_span","长度不允许超过10个字符");
			return false;
		}
		
		// 分类属性
		// 隐藏则不校验!
		if(!$('#attribute_list_div').is('.none'))
		{	
			var require_attr_id_str = $("#require_attr_id_str").val();
			var attr_arr = require_attr_id_str.split('_');
			for (var i = 0; i < attr_arr.length; i++) {
				if (attr_arr[i] == '') continue;
				if (typeof($('.category_attribute_'+attr_arr[i]+':checked').val()) == "undefined") {
					warnTips("category_attribute_"+attr_arr[i], $("#category_attribute_name_"+attr_arr[i]).html() + "不允许为空");
					return false;
				}
			}
		}
		
		if($("#technicalParameter").val().trim() == ""){
			warnTips("technicalParameter","技术参数不允许为空");
			return false;
		}
		if($("#technicalParameter").val().length > 2000){
			warnTips("technicalParameter","技术参数长度不允许超过2000个字符");
			return false;
		}
		
		if($("#performanceParameter").val().length > 2000){
			warnTips("performanceParameter","性能参数长度不允许超过2000个字符");
			return false;
		}
		
		if($("#specParameter").val().length > 2000){
			warnTips("specParameter","规格参数长度不允许超过2000个字符");
			return false;
		}
		
		// 当新国标分类补位非医疗器械时 , 管理类别:2,3级时
		if (new_standardCategoryId != 1388 && manageLevel != 339) 
		{
			var regN = $("#registrationNumber").val();
			if(undefined == regN || regN.trim() == '')
			{
				warnTips("registrationNumber","注册证号不允许为空");
				return false;
			}

			if (regN.length > 256) {
				warnTips("registrationNumber","注册证号长度不允许超过256个字符");
				return false;
			}
			
//			var regNName = $("#registrationNumberName").val();
//			if(undefined == regNName || regNName.trim() == '')
//			{
//				warnTips("registrationNumberName","注册证名称不允许为空");
//				return false;
//			}
//			if (regNName.length > 128) {
//				warnTips("registrationNumberName","注册证名称长度不允许超过128个字符");
//				return false;
//			}
			
			// 校验注册证，只要有一个不为空就跳过校验
			var uri_344 = $(".uri_344");
			// 0 注册证附件为空， 1 表示不为空
			var null_mark = 0;
			// 注册证input框不为空
			if(uri_344 && $(uri_344).length > 0){
				// 循环注册证上传input框
				for (var Zczlenth = 0; Zczlenth < $(uri_344).length; Zczlenth++) {
					// 如果有一个不为空，直接跳出校验
					if($(uri_344)[Zczlenth].value.trim() != ''){
						null_mark = 1;
						break;
					}
				}
			}
			// 0 注册证附件为空 
			if(null_mark == 0){
				warnTips("uri_344_div","注册证不允许为空");
				return false;
			}
			
			
			if (($("input[name='begintimeStr']").val().trim() == '' || $("input[name='endtimeStr']").val() == '')) 
			{
				warnTips("register_span","注册证有效期不允许为空");
				return false;
			}
			// 清空 批准文号 备案文件 备案编号
			$("#licenseNumber").val("");
			$("#uri_680").val("");
			$("#recordNumber").val("");
		}
		// 当新国标分类补位非医疗器械时 , 管理类别:1级时
		if (new_standardCategoryId != 1388 && manageLevel == 339) 
		{
			// 备案编号$("#recordNumber").val().length > 256
			var reN = $("#recordNumber").val().trim();
			if(reN == '')
			{
				warnTips("recordNumber","备案编号不允许为空");
				return false;
			}
			if(reN.length > 256)
			{
				warnTips("recordNumber","备案编号长度不允许超过256个字符");
				return false;
			}
			
			// 备案文件
			if ($("#uri_680").val().trim() == '') 
			{
				warnTips("uri_680_div","备案文件不允许为空");
				return false;
			}
			
			// 清空注册证名称 注册证号 注册证有效期 注册证
//			$("#registrationNumberName").val("");
			$("#registrationNumber").val("");
			$("input[name='begintimeStr']").val("");
			$("input[name='endtimeStr']").val("");
			
			// 将注册证图片信息置为空
			var uri_344_t = $(".uri_344");
			// 注册证input框不为空
			if(uri_344_t && $(uri_344_t).length > 0){
				// 循环注册证上传input框
				for (var Zczlenth = 0; Zczlenth < $(uri_344_t).length; Zczlenth++) {
					// 将所有的注册证附件输入框置为空
					$(uri_344_t[Zczlenth]).val('');
				}
			}
			
		}
		
		if ($("#packingList").val().length > 256) {
			warnTips("packingList","包装清单长度不允许超过256个字符");
			return false;
		}
		if ($("#spec").val().length > 64) {
			warnTips("spec","商品规格长度不允许超过64个字符");
			return false;
		}
		if ($("#productAddress").val().length > 256) {
			warnTips("productAddress","商品产地长度不允许超过256个字符");
			return false;
		}
		if(typeof($("input[name='goodsLevel']:checked").val()) == "undefined") {
			warnTips("goods_level_div","商品级别不允许为空");
			return false;
		}
		if ($("#purchaseRemind").val().length > 1024) {
			warnTips("purchaseRemind","采购提醒长度不允许超过1024个字符");
			return false;
		}
		if ($("#tos").val().length > 1024) {
			warnTips("tos","服务条款长度不允许超过1024个字符");
			return false;
		}
		return true;
	});
});

//商品类型 
function changeGoodsType(type)
{
	// 为试剂
	if(type == 318)
	{
		// 将新国标设置空
		$("#standardCategoryId").val("0");
		$("#new_stand_div_show_1_div").addClass("none");
		$("#new_stand_div_show_2_div").remove();
		$("#new_stand_div_show_3_div").remove();
		$("#new_stand_div_show_1_div_search").remove();
		$("#stand_span_id").addClass("none");
		$("#new_stand_div_show_1_div").removeClass("none");
		// 清除选中状态
		$("select[name='standardCategory_Opt']").val("");
		// 隐藏 新国标
		$("#new_national_standard").hide();
	}
	else
	{
		$("#new_national_standard").show();
	}
	// 设置值
	$("#goodType_id").val(type);
}

function managerCategoryLevel(v) 
{
	$("input[name='manageCategoryLevel']").prop("checked", false);
	// 当v == 1388即 选择 非医疗机械 隐藏管理级别
	if (v == 1388) 
	{
		// 管理级别 
		$(".manager_category_level").addClass('none');
		// 隐藏管理级别下所有的字段
		// 注册证号
		$("#registrationNumber_li").hide();
//		// 注册证名称
//		$("#registrationNumberName_li").hide();
		// 注册证
		$(".atta_344").addClass('none');
		// 注册证有效期
		$(".zcz_div").addClass('none');
		// 备案文件
		$(".bawj_class").addClass('none');
		// 备案编号
		$(".recordNumber").addClass('none');
		// 清空 
		$("#licenseNumber").val("");
		// 批准文号
		$(".licenseNumber").addClass('none');
	} 
	else 
	{
		$(".manager_category_level").removeClass('none');
	}
}
// 管理类别
function changeManagerCategoryLevel(level) 
{
	// 1级
	if (level == 339) 
	{
		// 备案编号
		$(".recordNumber").removeClass('none');
		// 备案文件
		$(".bawj_class").removeClass('none');
//		// 注册证名称
//		$("#registrationNumberName_li").hide();
		// 注册证号
		$("#registrationNumber_li").hide();
		// 注册证
		$(".atta_344").addClass('none');
		// 注册证有效期
		$(".zcz_div").addClass('none');
		// 批准文号
		$(".licenseNumber").addClass('none');
	}
	// 2级/3级
	else {
		// 注册证号
		$("#registrationNumber_li").show();
//		// 注册证名称
//		$("#registrationNumberName_li").show();
		// 注册证
		$(".atta_344").removeClass('none');
		// 注册证有效期
		$(".zcz_div").removeClass('none');
		// 批准文号
		$(".licenseNumber").addClass('none');
		// 备案文件
		$(".bawj_class").addClass('none');
		// 备案编号
		$(".recordNumber").addClass('none');
	}
}


//应用科室
function initGoodsInfo(){
	checkLogin();
	$.ajax({
		type : "POST",
		url : page_url+"/goods/goods/getgoodsoption.do",
		dataType : 'json',
		success : function(data) {
			if(data.code == 0){
				var attr =  eval('(' + data.param + ')');
				$.each(attr,function(i,n){
					if(i == 1035){//商品类型
						var radio = "";
						$.each(n,function(j,m){
							radio += "<li><input type='radio' onclick='changeGoodsType(this.value)' name='goodsType' value='"+m['sysOptionDefinitionId']+"'/> <label>"+m['title']+"</label></li>";
						});
						$("#goods_type_div").html(radio);
					}
					if(i == 1037){//商品级别
						var radio = "";
						$.each(n,function(j,m){
							radio += "<li><input type='radio' name='goodsLevel' value='"+m['sysOptionDefinitionId']+"'/> <label>"+m['title']+"</label></li>";
						});
						$("#goods_level_div").html(radio);
					}
					if(i == 1020){//管理类别
						var opt = "<option value='0'>请选择</option>";
						$.each(n,function(j,m){
							opt += "<option value='"+m['sysOptionDefinitionId']+"'>"+m['title']+"</option>";
							
						});
						$("select[name='manageCategory']").html(opt);
					}
					if(i == 1038){//管理类别级别
						var radio = "";
						var radioStr = "";
						$.each(n,function(j,m){
							radio += "<li class='manager_category_level none'><input type='radio' name='manageCategoryLevel' value='"+m['sysOptionDefinitionId']+"'/> <label>"+m['title']+"</label></li>";
							radioStr += "<li class='manager_category_level '><input onclick='changeManagerCategoryLevel(this.value)' type='radio' name='manageCategoryLevel' value='"+m['sysOptionDefinitionId']+"'/> <label>"+m['title']+"</label></li>";;
						});
						//$("select[name='manageCategory']").parent().after(radio);
						$("#manage_category_level_div").html(radioStr);
					}
					if(i == 1036){//应用科室
						var li = "";
						$.each(n,function(j,m){
							li += "<li class='f_left' style='margin: 0px 10px;'><input type='checkbox' name='attributeId' value='"+m['parentId']+"_"+m['sysOptionDefinitionId']+"' /><label>"+m['title']+"</label></li>";
						});
						
						$("#used_department_div").html(li);
					}
				});
				$("input[name='goodsType']").click(function(){
					if($(this).val()==318){
						$("#new_national_standard").hide();
						$(".manager_category_level").removeClass('none');
					}else{
						$("#new_national_standard").show();
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

//搜索品牌
function searchBrand(){
	checkLogin();
	var brand = $("input[name='brandKey']").val();
	$.ajax({
		type : "POST",
		url : page_url+"/goods/brand/getallbrand.do",
		data : {brandName:brand},
		dataType : 'json',
		success : function(data) {
			var option = "<option value='0'>请选择</option>";
			$.each(data.listData,function(i,n){
				var selected = "";
				if(brand == data.listData[i]['brandName']){
					selected = "selected";
				}
				option += "<option value='"+data.listData[i]['brandId']+"' "+selected+">"+data.listData[i]['brandName']+"</option>";
			});
			// modify by franlin.wu for[搜索品牌，不追加问题]  begin 
//			$("select[name='brandId']").html(option);
			$("#brandId").html(option);
			// modify by franlin.wu for[搜索品牌，不追加问题]  end 
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}

//搜索单位
function searchUnit(){
	checkLogin();
	var unit = $("input[name='unitKey']").val();
	$.ajax({
		type : "POST",
		url : page_url+"/goods/unit/getallunit.do",
		data : {unitName:unit},
		dataType : 'json',
		success : function(data) {
			var option = "<option value='0'>请选择</option>";
			$.each(data.listData,function(i,n){
				var selected = "";
				if(unit == data.listData[i]['unitName']){
					selected = "selected";
				}
				option += "<option value='"+data.listData[i]['unitId']+"' "+selected+">"+data.listData[i]['unitName']+"</option>";
			});
			$("select[name='unitId']").html(option);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}



function updateCategory(obj){
	checkLogin();
	var parentId = $(obj).val();
	var level = Number($(obj).find("option:checked").attr("id"));
	if(parentId!="-1" && parentId!=-1){
		$("#categoryId").siblings('.warning').remove();
		$.ajax({
			async:false,
			url:page_url + '/goods/category/getCategoryList.do',
			data:{"parentId":parentId},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var list = data.listData;
					if(list!=null && list.length>0){
						$("#categoryId").val(0);
						var ht = "<select id='categoryOpt' name='categoryOpt' style='width: 100px;' onchange='updateCategory(this);'>";
						ht = ht + "<option value='-1' id='"+list[0].level+"'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].categoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select>";
						
						$("#category_div").find("select:gt("+$(obj).index()+")").remove();
						$("#category_div").append(ht);
					}else{
						$("#categoryId").val(parentId);
						$("#category_div").find("select:gt("+$(obj).index()+")").remove();
						
						//获取最小分类对应的属性列表数据
						$.ajax({
							async:false,
							url:page_url + '/goods/categoryattribute/getAttributeByCategoryId.do',
							data:{"categoryId":parentId},
							type:"POST",
							dataType : "json",
							success:function(attr_data){
								if(attr_data.code==0){
									var attr_list = attr_data.listData;
									if(attr_list!=null && attr_list.length>0){
										var attribute_list_html = '';
										var require_attr_id_str = '';
										var attr_id_str = '';
										for (var attr_i = 0; attr_i < attr_list.length; attr_i++) {
											attribute_list_html += '<div class="infor_name mt0">';
											if (attr_list[attr_i].isSelected == 1) {
												attribute_list_html += '<span>*</span> ';
												require_attr_id_str += attr_list[attr_i].categoryAttributeId + '_';
											}
											attr_id_str += attr_list[attr_i].categoryAttributeId + '_';
											attribute_list_html += '<label id="category_attribute_name_' + attr_list[attr_i].categoryAttributeId + '">'+attr_list[attr_i].categoryAttributeName+'</label></div>';
											if (attr_list[attr_i].categoryAttributeValue!=null && attr_list[attr_i].categoryAttributeValue.length>0) {
												attribute_list_html += '<div class="f_left inputfloat"><ul>';
												for (var attr_v_i = 0; attr_v_i < attr_list[attr_i].categoryAttributeValue.length; attr_v_i++) {
													attribute_list_html += '<li>';
													if (attr_list[attr_i].inputType == 0) {//固定值单选
														attribute_list_html += '<input type="radio" name="categoryAttributeId_'+attr_list[attr_i].categoryAttributeId+'" class="category_attribute_'+attr_list[attr_i].categoryAttributeId+'" value="'+attr_list[attr_i].categoryAttributeId+'_'+attr_list[attr_i].categoryAttributeValue[attr_v_i].categoryAttributeValueId+'" />';
													} else if (attr_list[attr_i].inputType == 1) {//固定值复选
														attribute_list_html += '<input type="checkbox" name="categoryAttributeId" class="category_attribute_'+attr_list[attr_i].categoryAttributeId+'" value="'+attr_list[attr_i].categoryAttributeId+'_'+attr_list[attr_i].categoryAttributeValue[attr_v_i].categoryAttributeValueId+'" />';
													}
				                                	attribute_list_html += '<label>'+attr_list[attr_i].categoryAttributeValue[attr_v_i].attrValue+'</label></li>';
												}
												attribute_list_html += '<li id="category_attribute_'+attr_list[attr_i].categoryAttributeId+'"></li></ul></div>';
											}
											attribute_list_html += '<div class="clear"></div>';
										}
										$("#require_attr_id_str").val(require_attr_id_str);
										$("#attr_id_str").val(attr_id_str);
										$("#attribute_list_div").html(attribute_list_html);
										$("#attribute_list_div").removeClass("none");
										$("#attribute_list_div").addClass("line");
									} else {
										$("#attribute_list_div").html('');
										$("#require_attr_id_str").val('');
										$("#attr_id_str").val('');
										$("#attribute_list_div").removeClass("line");
										$("#attribute_list_div").addClass("none");
									}
								}else{
									layer.alert("获取对应分类属性信息失败，请稍后重试或联系管理员！");
									return false;
								}
							}
						})
					}
				}else{
					layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
					return false;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	} else {
		$("#categoryId").val(0);
		$("#category_div").find("select:gt("+$(obj).index()+")").remove();
		$("#attribute_list_div").html('');
		$("#attribute_list_div").removeClass("line");
		$("#attribute_list_div").addClass("none");
	}
}

function updateStandardCategory(obj){
	checkLogin();
	var parentId = $(obj).val();
	managerCategoryLevel(parentId);//判断是否显示管理类别
	var level = Number($(obj).find("option:checked").attr("id"));
	if(parentId!="-1" && parentId!=-1){
		$("#standardCategoryId").siblings('.warning').remove();
		$.ajax({
			async:false,
			url:page_url + '/goods/category/getStandardCategoryList.do',
			data:{"parentId":parentId},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var list = data.listData;
					if(list!=null && list.length>0){
						$("#standardCategoryId").val(0);
						var ht = "<select id='standardCategoryOpt' name='standardCategoryOpt' style='width: 100px;' onchange='updateStandardCategory(this);'>";
						ht = ht + "<option value='-1' id='"+list[0].level+"'>请选择</option>";
						for(var i=0;i<list.length;i++){
							ht = ht + "<option value='"+list[i].standardCategoryId+"' id='"+list[i].level+"'>"+list[i].categoryName+"</option>";
						}
						ht = ht + "</select>";
						
						$("#standard_category_div").find("select:gt("+$(obj).index()+")").remove();
						$("#standard_category_div").append(ht);
					}else{
						$("#standardCategoryId").val(parentId);
						$("#standard_category_div").find("select:gt("+$(obj).index()+")").remove();
					}
				}else{
					layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
					return false;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	} else {
		$("#standardCategoryId").val(0);
		$("#standard_category_div").find("select:gt("+$(obj).index()+")").remove();
	}
}

function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	// 转换为小写格式
	strExtension = strExtension.toLowerCase();
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' && strExtension != 'pdf' && strExtension != 'jpeg') 
	{
		// 清空url
		$(obj).val("");
		layer.alert("请选择图片文件");
		return;
	}
	// 字节
	if($(obj)[0].files[0].size > 5*1024*1024)
	{   
		// 清空url
		$(obj).val("");
		layer.alert("图片文件大小应为5MB以内");
		return;
	}
	/*$("#img_icon_" + num).attr("class", "iconloading mt5").show();
	$("#img_view_" + num).hide();
	$("#img_del_" + num).hide();*/

	$(obj).parent().parent().find("i").attr("class", "iconloading mt5").show();
	$(obj).parent().parent().find("a").hide();
	$(obj).parent().parent().find("span").hide();
	var objCopy1 = $(obj).parent();
	var objCopy2 = $(obj).parent().parent();
	$.ajaxFileUpload({
		url : page_url + '/goods/goods/goodsAttachmentUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			layer.closeAll();
			if (data.code == 0) {
				/*$("#uri_" + num).val(data.filePath+"/"+data.fileName);
				$("#domain_" + num).val(data.httpUrl);
				$("#img_view_" + num).attr("href", 'http://' + data.httpUrl + data.filePath+"/"+data.fileName).show();
				$("#img_icon_" + num).attr("class", "iconsuccesss mt5").show();
				$("#img_del_" + num).show();*/
				
				objCopy1.find("input[type='text']").val(data.filePath+"/"+data.fileName);
				$("#domain").val(data.httpUrl);
				objCopy2.find("i").attr("class", "iconsuccesss mt5").show();
				objCopy2.find("a").attr("href", 'http://' + data.httpUrl + data.filePath+"/"+data.fileName).show();
				objCopy2.find("span").show();
				//layer.msg('上传成功');
			} else {
				layer.alert(data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {
			layer.closeAll();
			layer.alert(data.responseText);
		}
	})
}

function delProductImg(num) {
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$("#img_icon_" + num).hide();
			$("#img_view_" + num).hide();
			$("#img_del_" + num).hide();
			$("#uri_" + num).val('');
			$("#domain_" + num).val('');
			layer.close(index);
		}, function(){
		});
}

function con_add(id)
{
	var desc = '请上传附件';
	if(id == 658)
	{
		desc = '请上传商品的测试报告';
	}
	else if(id == 343)
	{
		desc = '请上传商品图片';
	}
	else if(id == 659)
	{
		desc = '请上传商品的专利文件';
	}
	else if(id == 344){
		desc = '请上传注册证';
		var uploadCount = $("#conadd344").siblings(".upload_count");
		if(uploadCount && uploadCount.length >= 10){
			warnTips("uri_344_div","最多只能上传10张注册证");
			return false;
		}
	}
	
	var html = '<div class="clear mt8 upload_count">'+
					'<div class="pos_rel f_left mt8">'+
						'<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_'+id+'_'+RndNum(8)+'" onchange="uploadFile(this, '+id+')">'+
					    '<input type="text" class="input-middle mr5 uri_'+id+'" id="uri_'+id+'" placeholder="'+desc+'" name="uri_'+id+'">'+
					    '<label class="bt-bg-style bt-middle bg-light-blue ml4" type="file" >浏览</label>'+
					'</div>'+
					'<div class="f_left mt8">'+
					'<i class="iconsuccesss mt5 none" id="img_icon_'+id+'"></i>'+
						'<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_'+id+'">查看</a>'+
						'<span class="font-red cursor-pointer mt4" onclick="delAttachment(this)" id="img_del_'+id+'">删除</span>'+
					'</div>'+
				'</div>';
	$("#conadd"+id).before(html);
}

function RndNum(n){
    var rnd="";
    for(var i=0;i<n;i++)
        rnd+=Math.floor(Math.random()*10);
    return rnd;
}

function delAttachment(obj) {
	var uri = $(obj).parent().find("a").attr("href");
	if (uri == '') {
		$(obj).parent().parent().remove();
	} else {
		index = layer.confirm("您是否确认该操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				var length = $(obj).parent().parent().parent().find("input[type='file']").length;
				if (length == 1) {
					$(obj).parent().find("i").hide();
					$(obj).parent().find("a").hide();
					$(obj).parent().find("span").hide();
					$(obj).parent().parent().parent().find("input[type='text']").val('');
				} else {
					$(obj).parent().parent().remove();
				}
				layer.close(index);
			}, function(){
			});
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