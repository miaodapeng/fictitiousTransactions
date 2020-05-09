$(function() {
	initGoodsInfo();
	$("#saveSaleInfoForm").submit(function(){
		checkLogin();
		$(".warning").remove();
		var urlReg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
		if(typeof($("input[name='goodsLevel']:checked").val()) == "undefined") {
			warnTips("goods_level_div","产品级别不允许为空");
			return false;
		}
		if($("select[name='manageCategory']").val() == 0){
			warnTips("manageCategory","管理类别不允许为空");
			return false;
		}
		if ($("select[name='manageCategory']").val() != 194 && typeof($("input[name='manageCategoryLevel']:checked").val()) == "undefined") {
			warnTips("manageCategory","管理类别级别不允许为空");
			return false;
		}
		if (($("#view_344").length > 0 || $("#domain_344").val() != '') && ($("input[name='begintimeStr']").val() == '' || $("input[name='endtimeStr']").val() == '')) {
			warnTips("register_span","注册证有效期不允许为空");
			return false;
		}		
		if ($("#purchaseRemind").val().length > 1024) {
			warnTips("purchaseRemind","采购提醒长度不允许超过1024个字符");
			return false;
		}
		if ($("#licenseNumber").val().length > 256) {
			warnTips("licenseNumber","批准文号长度不允许超过256个字符");
			return false;
		}
		if ($("#registrationNumber").val().length > 256) {
			warnTips("registrationNumber","注册证号长度不允许超过256个字符");
			return false;
		}
		if($("#authorizationCertificateUrl").val() != '' && !urlReg.test($("#authorizationCertificateUrl").val())){
			warnTips("authorizationCertificateUrl","授权证书格式不正确");
			return false;
		}
		if ($("#authorizationCertificateUrl").val().length > 256) {
			warnTips("authorizationCertificateUrl","授权证书长度不允许超过256个字符");
			return false;
		}
		if($("#otherQualificationUrl").val() != '' && !urlReg.test($("#otherQualificationUrl").val())){
			warnTips("otherQualificationUrl","其他资质格式不正确");
			return false;
		}
		if ($("#otherQualificationUrl").val().length > 256) {
			warnTips("otherQualificationUrl","其他资质长度不允许超过256个字符");
			return false;
		}
		if($("#colorPageUrl").val() != '' && !urlReg.test($("#colorPageUrl").val())){
			warnTips("colorPageUrl","彩页地址格式不正确");
			return false;
		}
		if ($("#colorPageUrl").val().length > 256) {
			warnTips("colorPageUrl","彩页地址长度不允许超过256个字符");
			return false;
		}
		if($("#technicalParameterUrl").val() != '' && !urlReg.test($("#technicalParameterUrl").val())){
			warnTips("technicalParameterUrl","技术参数地址格式不正确");
			return false;
		}
		if ($("#technicalParameterUrl").val().length > 256) {
			warnTips("technicalParameterUrl","技术参数地址长度不允许超过256个字符");
			return false;
		}
		if($("#instructionsUrl").val() != '' && !urlReg.test($("#instructionsUrl").val())){
			warnTips("instructionsUrl","产品说明书地址格式不正确");
			return false;
		}
		if ($("#instructionsUrl").val().length > 256) {
			warnTips("instructionsUrl","产品说明书地址长度不允许超过256个字符");
			return false;
		}
		if($("#biddingDataUrl").val() != '' && !urlReg.test($("#biddingDataUrl").val())){
			warnTips("biddingDataUrl","陪标资料地址格式不正确");
			return false;
		}
		if ($("#biddingDataUrl").val().length > 256) {
			warnTips("biddingDataUrl","陪标资料地址长度不允许超过256个字符");
			return false;
		}		
		if ($("#packingList").val().length > 256) {
			warnTips("packingList","包装清单长度不允许超过256个字符");
			return false;
		}
		if ($("#tos").val().length > 1024) {
			warnTips("tos","服务条款长度不允许超过1024个字符");
			return false;
		}
		return true;
	});
	
	
});

function managerCategoryLevel(v) {
	if (v != 0 && v != 194) {
		$(".manager_category_level").removeClass('none');
	} else {
		$("input[name='manageCategoryLevel']").prop("checked", false);
		$(".manager_category_level").addClass('none');
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
					if(i == 1037){//产品级别
						var goodsLevelId = $("#goodsLevelId").val();
						var radio = "";
						$.each(n,function(j,m){
							var checked_str = m['sysOptionDefinitionId'] == goodsLevelId ? 'checked="checked"' : '';
							radio += "<li><input type='radio' " + checked_str + "name='goodsLevel' value='"+m['sysOptionDefinitionId']+"'/> <label>"+m['title']+"</label></li>";
						});
						$("#goods_level_div").html(radio);
					}
					if(i == 1020){//管理类别
						var manageCategoryId = $("#manageCategoryId").val();
						var opt = "<option value='0'>请选择</option>";
						$.each(n,function(j,m){
							var selected_str = m['sysOptionDefinitionId'] == manageCategoryId ? 'selected="selected"' : '';
							opt += "<option " + selected_str + "value='"+m['sysOptionDefinitionId']+"'>"+m['title']+"</option>";
							
						});
						$("select[name='manageCategory']").html(opt);
					}
					if(i == 1038){//管理类别级别
						var manageCategoryLevelId = $("#manageCategoryLevelId").val();
						var radio = "";
						$.each(n,function(j,m){
							var checked_str = m['sysOptionDefinitionId'] == manageCategoryLevelId ? 'checked="checked"' : '';
							radio += "<li class='manager_category_level none'><input type='radio' " + checked_str + "name='manageCategoryLevel' value='"+m['sysOptionDefinitionId']+"'/> <label>"+m['title']+"</label></li>";
						});
						$("select[name='manageCategory']").parent().after(radio);
						
						var manageCategoryId = $("#manageCategoryId").val();
						managerCategoryLevel(manageCategoryId);
					}
					if(i == 1036){//应用科室
						var goodsSysOptionAttributeIds = $("#goodsSysOptionAttributeIds").val();
						goodsSysOptionAttributeIdsArr = goodsSysOptionAttributeIds.split(',');
						
						var li = "";
						$.each(n,function(j,m){
							var checked_str = '';
							for (var x in goodsSysOptionAttributeIdsArr) {
								if (goodsSysOptionAttributeIdsArr[x] == m['sysOptionDefinitionId']) {
									checked_str = "checked='checked'"; 
								}
							}
							li += "<li><input type='checkbox' " + checked_str + " name='attributeId' value='"+m['parentId']+"_"+m['sysOptionDefinitionId']+"' /><label>"+m['title']+"</label></li>";
						});
						
						$("#used_department_div").html(li);
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
			$("select[name='brandId']").html(option);
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
										for (var attr_i = 0; attr_i < attr_list.length; attr_i++) {
											attribute_list_html += '<div class="infor_name mt0">';
											if (attr_list[attr_i].isSelected == 1) {
												attribute_list_html += '<span>*</span> ';
											}
											attribute_list_html += '<label>'+attr_list[attr_i].categoryAttributeName+'</label></div>';
											if (attr_list[attr_i].categoryAttributeValue!=null && attr_list[attr_i].categoryAttributeValue.length>0) {
												attribute_list_html += '<div class="f_left inputfloat"><ul>';
												for (var attr_v_i = 0; attr_v_i < attr_list[attr_i].categoryAttributeValue.length; attr_v_i++) {
													attribute_list_html += '<li>';
													if (attr_list[attr_i].inputType == 0) {//固定值单选
														attribute_list_html += '<input type="radio" name="attr_radio_'+attr_list[attr_i].categoryAttributeId+'" value="'+attr_list[attr_i].categoryAttributeValue[attr_v_i].categoryAttributeValueId+'" />';
													} else if (attr_list[attr_i].inputType == 1) {//固定值复选
														attribute_list_html += '<input type="checkbox" name="attr_checkbox_'+attr_list[attr_i].categoryAttributeId+'[]" value="'+attr_list[attr_i].categoryAttributeValue[attr_v_i].categoryAttributeValueId+'" />';
													}
													
				                                	attribute_list_html += '<label>'+attr_list[attr_i].categoryAttributeValue[attr_v_i].attrValue+'</label></li>';
												}
												attribute_list_html += '</ul></div>';
											}
											attribute_list_html += '<div class="clear"></div>';
										}
										$("#attribute_list_div").html(attribute_list_html);
									} else {
										$("#attribute_list_div").html('');
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
	}
}

function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1).toLowerCase();
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' && strExtension != 'pdf') {
		alert("请选择图片文件");
		return;
	}
	if($(obj)[0].files[0].size > 2*1024*1024){//字节
		layer.alert("图片文件大小应为2MB以内",{ icon: 2 });
		return;
	}
	$("#img_icon_" + num).attr("class", "iconloading mt5").show();
	$("#img_view_" + num).hide();
	$("#img_del_" + num).hide();
	$.ajaxFileUpload({
		url : page_url + '/goods/goods/goodsAttachmentUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {
				$("#uri_" + num).val(data.filePath+"/"+data.fileName);
				$("#domain_" + num).val(data.httpUrl);
				$("#img_view_" + num).attr("href", 'http://' + data.httpUrl + data.filePath+"/"+data.fileName).show();
				$("#img_icon_" + num).attr("class", "iconsuccesss mt5").show();
				$("#img_del_" + num).show();
				//layer.msg('上传成功');
			} else {
				layer.alert(data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {
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
