$(function() {
	clickOne("twoMedicalType");
	clickOne("threeMedicalType");
	$("#file_1").change(function(){
		checkLogin();
		$("#uri_1").val($("#file_1").val());
	})
	
	$("#file_2").change(function(){
		checkLogin();
		$("#uri_2").val($("#file_2").val());
	})
	
	$("#file_3").change(function(){
		checkLogin();
		$("#uri_3").val($("#file_3").val());
	})
	
	$("#file_4").change(function(){
		checkLogin();
		$("#uri_4").val($("#file_4").val());
	})
	$("#file_5").change(function(){
		checkLogin();
		$("#uri_5").val($("#file_5").val());
	})
	
	$("#file_8").change(function(){
		checkLogin();
		$("#uri_8").val($("#file_8").val());
	})
	
	$("#one").click(function(){
		checkLogin();
			$("#name_2").attr("disabled",true);
			$("#tax label").addClass("bg-opcity");
			$("#tax .Wdate").addClass("bg-opcity");
			$("#tax .Wdate").css("cursor","not-allowed");
			$("#taxUpload").css("cursor","not-allowed");
			$("#taxUpload").prop("onclick",null).off("click");
			$("#name_3").attr("disabled",true);
			$("#org label").addClass("bg-opcity");
			$("#org .Wdate").addClass("bg-opcity");
			$("#org .Wdate").css("cursor","not-allowed");
			$("#orgUpload").css("cursor","not-allowed");
			$("#orgUpload").prop("onclick",null).off("click");
	})
	$("#zero").click(function(){
		checkLogin();
			$("#name_2").removeAttr("disabled");
			$("#tax label").removeClass("bg-opcity");
			$("#tax .Wdate").removeClass("bg-opcity");
			$("#tax .Wdate").css("cursor","");
			$("#tax .Wdate").removeAttr("disabled");
			$("#taxUpload").css("cursor","pointer");
			$('#taxUpload').attr("onclick","file_2.click()");
			$("#name_3").removeAttr("disabled");
			$("#org label").removeClass("bg-opcity");
			$("#org .Wdate").removeClass("bg-opcity");
			$("#org .Wdate").removeAttr("disabled");
			$("#org .Wdate").css("cursor","");
			$("#orgUpload").css("cursor","pointer");
			$("#orgUpload").attr("onclick","file_3.click()");

	})
	
	$("#med1").click(function(){
		checkLogin();
			$("#three_medical input").attr("disabled",true);
			$("#three_medical label").addClass("bg-opcity");
			$("#three_medical .Wdate").addClass("bg-opcity");
			$("#three_medical .Wdate").css("cursor","not-allowed");
			$("#threeUpload").css("cursor","not-allowed");
			$("#threeUpload").prop("onclick",null).off("click");
	})
	$("#med0").click(function(){
		checkLogin();
			$("#three_medical input").removeAttr("disabled");
			$("#three_medical label").removeClass("bg-opcity");
			$("#three_medical .Wdate").removeClass("bg-opcity");
			$("#three_medical .Wdate").removeAttr("disabled");
			$("#three_medical .Wdate").css("cursor","");
			$("#threeUpload").css("cursor","pointer");
			$("#threeUpload").attr("onclick","file_5.click()");
	})

	
	$("#submit").click(function(){
		checkLogin();
//		var inputFlag=true;
//		$.each($("input[type='text']"),function(i,n){
//			var val = $(this).val();
//			if(val!=""){
//				inputFlag = false;
//				return false;
//			}
//		});
			var medicalQualification=$('input:radio[name="medicalQualification"]:checked').val();
//			var medIds='';
//			var typeIds = '';
//			var flag=false;
//			$("select").each(function(index,ele){
//				var num =$("select").length;
//				if($(this).val() != ''&&$(this).val() != undefined){
//					medIds += $(this).val()+",";
//				}
//				if($(this).val()!=''&&$(this).val()!=194){
//					var val=$('input:radio[name="medicalTypLevel_'+(index+1)+'"]:checked').val();
//					if(val!=undefined){
//						typeIds += val+",";
//					}
//					flag=true;
//					return false;
//				}
//			});
//			if(inputFlag && medIds == ''&&!flag){
//				layer.alert("请至少填写/选择一项数据");
//				return false;
//			}
//			if(flag && typeIds==''){
//				layer.alert("请选择医疗资质信息的分类");
//				return false;
//			}
//			if((inputFlag || $("#name_4").val()=='')&& typeIds.indexOf("239")>=0){
//				layer.alert("资质信息含有二类医疗资质，请上传二类医疗资质");
//				return false;
//			}else if(medicalQualification ==0 &&(inputFlag || $("#name_5").val()=='') && typeIds.indexOf("240")>=0){
//				layer.alert("资质信息含有三类医疗资质，请上传三类医疗资质");
//				return false;
//			}else if(medicalQualification ==1 &&(inputFlag || $("#name_4").val()=='') && typeIds.indexOf("240")>=0){
//				layer.alert("资质信息含有二类医疗资质，请上传二类医疗资质");
//				return false;
//			}else if(medicalQualification ==0 && (inputFlag ||$("#name_4").val()=='' ||$("#name_5").val()=='') && typeIds.indexOf("241")>=0){
//				layer.alert("资质信息含有二三类医疗资质，请上传二三类医疗资质");
//				return false;
//			}else if(medicalQualification ==1 && (inputFlag ||$("#name_4").val()=='') && typeIds.indexOf("241")>=0){
//				layer.alert("资质信息含有二类医疗资质，请上传二类医疗资质");
//				return false;
//			}
		
		var threeInOne=$('input:radio[name="threeInOne"]:checked').val();
		if(threeInOne ==1){
//			if($("#name_1").val()==''&&$("#busStartTime").val()==''&&$("#busEndTime").val()==''&&$("input[name='isMedical']").prop("checked")){
//				$("#name_1").addClass("errorbor");
//				$("#name_1").siblings("div").css("display","");
//				return false;
//			}else if($("#name_1").val()==''&&($("#busStartTime").val()!=''||$("#busEndTime").val()!=''||$("input[name='isMedical']").prop("checked"))){
//				$("#name_1").addClass("errorbor");
//				$("#name_1").siblings("div").css("display","");
//				return false;
//			}else if($("#busStartTime").val()==''&&($("#name_1").val()!=''||$("#busEndTime").val()!=''||$("input[name='isMedical']").prop("checked"))){
//				$("#name_1").removeClass("errorbor");
//				$("#name_1").siblings("div").css("display","none");
//				$("#busStartTime").addClass("errorbor");
//				$("#busStartTime").parent('div').siblings("div").css("display","");
//				return false;
//			}else{
//				$("#name_1").removeClass("errorbor");
//				$("#name_1").siblings("div").css("display","none");
//				$("#busStartTime").removeClass("errorbor");
//				$("#busStartTime").parent('div').siblings("div").css("display","none");
//			}
			
			if($("#busStartTime").val()==''){
				$("#busStartTime").addClass("errorbor");
				$("#busStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#busStartTime").removeClass("errorbor");
				$("#busStartTime").parent('div').siblings("div").css("display","none");
			}
			
		}else{
//			if($("#name_1").val()==''&&($("#busStartTime").val()!=''||$("#busEndTime").val()!=''||$("input[name='isMedical']").prop("checked"))){
//				$("#name_1").addClass("errorbor");
//				$("#name_1").siblings("div").css("display","");
//				return false;
//			}else if($("#busStartTime").val()==''&&($("#name_1").val()!=''||$("#busEndTime").val()!=''||$("input[name='isMedical']").prop("checked"))){
//				$("#name_1").removeClass("errorbor");
//				$("#name_1").siblings("div").css("display","none");
//				$("#busStartTime").addClass("errorbor");
//				$("#busStartTime").parent('div').siblings("div").css("display","");
//				return false;
//			}else{
//				$("#name_1").removeClass("errorbor");
//				$("#name_1").siblings("div").css("display","none");
//				$("#busStartTime").removeClass("errorbor");
//				$("#busStartTime").parent('div').siblings("div").css("display","none");
//			}
			
			if($("#busStartTime").val()==''&&($("#name_1").val()!=''||$("#busEndTime").val()!=''||$("input[name='isMedical']").prop("checked"))){
				$("#busStartTime").addClass("errorbor");
				$("#busStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#busStartTime").removeClass("errorbor");
				$("#busStartTime").parent('div').siblings("div").css("display","none");
			}
			
//			if($("#name_2").val()==''&&($("#taxStartTime").val()!=''||$("#taxEndTime").val()!='')){
//				$("#name_2").addClass("errorbor");
//				$("#name_2").siblings("div").css("display","");
//				return false;
//			}else if($("#taxStartTime").val()==''&&($("#name_2").val()!=''||$("#taxEndTime").val()!='')){
//				$("#name_2").removeClass("errorbor");
//				$("#name_2").siblings("div").css("display","none");
//				$("#taxStartTime").addClass("errorbor");
//				$("#taxStartTime").parent('div').siblings("div").css("display","");
//				return false;
//			}else{
//				$("#name_2").removeClass("errorbor");
//				$("#name_2").siblings("div").css("display","none");
//				$("#taxStartTime").removeClass("errorbor");
//				$("#taxStartTime").parent('div').siblings("div").css("display","none");
//			}
			
			if($("#taxStartTime").val()==''&&($("#name_2").val()!=''||$("#taxEndTime").val()!='')){
				$("#taxStartTime").addClass("errorbor");
				$("#taxStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#taxStartTime").removeClass("errorbor");
				$("#taxStartTime").parent('div').siblings("div").css("display","none");
			}
			
//			if($("#name_3").val()==''&&($("#orgaStartTime").val()!=''||$("#orgaEndTime").val()!='')){
//				$("#name_3").addClass("errorbor");
//				$("#name_3").siblings("div").css("display","");
//				return false;
//			}else if($("#orgaStartTime").val()==''&&($("#name_3").val()!=''||$("#orgaEndTime").val()!='')){
//				$("#name_3").removeClass("errorbor");
//				$("#name_3").siblings("div").css("display","none");
//				$("#orgaStartTime").addClass("errorbor");
//				$("#orgaStartTime").parent('div').siblings("div").css("display","");
//				return false;
//			} else{
//				$("#name_3").removeClass("errorbor");
//				$("#name_3").siblings("div").css("display","none");
//				$("#orgaStartTime").removeClass("errorbor");
//				$("#orgaStartTime").parent('div').siblings("div").css("display","none");
//			}
			
			if($("#orgaStartTime").val()==''&&($("#name_3").val()!=''||$("#orgaEndTime").val()!='')){
				$("#orgaStartTime").addClass("errorbor");
				$("#orgaStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#orgaStartTime").removeClass("errorbor");
				$("#orgaStartTime").parent('div').siblings("div").css("display","none");
			}
			
		}
		
			var testReg =/^[0-9a-zA-Z]{1,128}$/;
			if(medicalQualification ==1){
//				if($("#name_4").val()==''&&($("#twoStartTime").val()!=''||$("#twoEndTime").val()!=''||$("#twoSn").val()!='')){
//					$("#name_4").addClass("errorbor");
//					$("#name_4").siblings("div").css("display","");
//					return false;
//				}else if($("#twoStartTime").val()==''&&($("#name_4").val()!=''||$("#twoEndTime").val()!=''||$("#twoSn").val()!='')){
//					$("#name_4").removeClass("errorbor");
//					$("#name_4").siblings("div").css("display","none");
//					$("#twoStartTime").addClass("errorbor");
//					$("#twoStartTime").parent('div').siblings("div").css("display","");
//					return false;
//				}else if($("#twoSn").val()==''&&$("#twoStartTime").val()!=''&&$("#name_4").val()!=''){
//					$("#name_4").removeClass("errorbor");
//					$("#name_4").siblings("div").css("display","none");
//					$("#twoStartTime").removeClass("errorbor");
//					$("#twoStartTime").parent('div').siblings("div").css("display","none");
//					$("#twoSn").addClass("errorbor");
//					$("#twoSn").siblings("div").css("display","").html("许可证编号不允许为空");
//					return false;
//				}else{
//					$("#name_4").removeClass("errorbor");
//					$("#name_4").siblings("div").css("display","none");
//					$("#twoStartTime").removeClass("errorbor");
//					$("#twoStartTime").parent('div').siblings("div").css("display","none");
//					$("#twoSn").removeClass("errorbor");
//					$("#twoSn").siblings("div").css("display","none");
//				}
//				if($("#twoSn").val()!=''){
//					var flag = false;
//					$.each($("input[name='twoMedicalType']"),function(i,n){
//						if($(this).prop("checked")){
//							flag = true;
//							return false;
//						}
//					});
//					if(!flag){
//						layer.alert("请选择二类医疗资质");
//						return false;
//					}
//					var flag1 = false;
//					$.each($("input[name='threeMedicalType']"),function(i,n){
//						if($(this).prop("checked")){
//							flag1 = true;
//							return false;
//						}
//					});
//					if(!flag1){
//						layer.alert("请选择三类医疗资质");
//						return false;
//					}
//				}
				
				if($("#twoStartTime").val()==''){
					$("#twoStartTime").addClass("errorbor");
					$("#twoStartTime").parent('div').siblings("div").css("display","");
					return false;
				}else{
					$("#twoStartTime").removeClass("errorbor");
					$("#twoStartTime").parent('div').siblings("div").css("display","none");
				}
				
				if($("#twoStartTime").val()!=''){
					var flag = false;
					$.each($("input[name='twoMedicalType']"),function(i,n){
						if($(this).prop("checked")){
							flag = true;
							return false;
						}
					});
					if(!flag){
						layer.alert("请选择医疗器械二类备案凭证");
						return false;
					}
					var flag1 = false;
					$.each($("input[name='threeMedicalType']"),function(i,n){
						if($(this).prop("checked")){
							flag1 = true;
							return false;
						}
					});
					if(!flag1){
						layer.alert("请选择三类医疗资质");
						return false;
					}
				}
			}else{
//				if($("#name_4").val()==''&&($("#twoStartTime").val()!=''||$("#twoEndTime").val()!=''||$("#twoSn").val()!='')){
//					$("#name_4").addClass("errorbor");
//					$("#name_4").siblings("div").css("display","");
//					return false;
//				}else if($("#twoStartTime").val()==''&&($("#name_4").val()!=''||$("#twoEndTime").val()!=''||$("#twoSn").val()!='')){
//					$("#name_4").removeClass("errorbor");
//					$("#name_4").siblings("div").css("display","none");
//					$("#twoStartTime").addClass("errorbor");
//					$("#twoStartTime").parent('div').siblings("div").css("display","");
//					return false;
//				}else if($("#twoSn").val()==''&&$("#twoStartTime").val()!=''&&$("#name_4").val()!=''){
//					$("#name_4").removeClass("errorbor");
//					$("#name_4").siblings("div").css("display","none");
//					$("#twoStartTime").removeClass("errorbor");
//					$("#twoStartTime").parent('div').siblings("div").css("display","none");
//					$("#twoSn").addClass("errorbor");
//					$("#twoSn").siblings("div").css("display","").html("许可证编号不允许为空");
//					return false;
//				}else{
//					$("#name_4").removeClass("errorbor");
//					$("#name_4").siblings("div").css("display","none");
//					$("#twoStartTime").removeClass("errorbor");
//					$("#twoStartTime").parent('div').siblings("div").css("display","none");
//					$("#twoSn").removeClass("errorbor");
//					$("#twoSn").siblings("div").css("display","none");
//				}
//				if($("#twoSn").val()!=''){
//					var flag = false;
//					$.each($("input[name='twoMedicalType']"),function(i,n){
//						if($(this).prop("checked")){
//							flag = true;
//							return false;
//						}
//					});
//					if(!flag){
//						layer.alert("请选择二类医疗资质");
//						return false;
//					}
//				}
				
				if($("#twoStartTime").val()!=''){
					var flag = false;
					$.each($("input[name='twoMedicalType']"),function(i,n){
						if($(this).prop("checked")){
							flag = true;
							return false;
						}
					});
					if(!flag){
						layer.alert("请选择医疗器械二类备案凭证");
						return false;
					}
				}
				var nameFlag = false;
				$("#name_4").parent().parent().parent().find("input[name='name_4']").each( function(){//循环判断后添加的输入框其中是否有
					if($(this).val() != ''){
						nameFlag = true;
					}
				});
				
				if($("#twoStartTime").val()=='' && (nameFlag||$("#twoEndTime").val()!=''||$("#twoSn").val()!='')){
					$("#twoStartTime").addClass("errorbor");
					$("#twoStartTime").parent('div').siblings("div").css("display","");
					return false;
				}else{
					$("#twoStartTime").removeClass("errorbor");
					$("#twoStartTime").parent('div').siblings("div").css("display","none");
				}
				
//				if($("#name_5").val()==''&&($("#threeStartTime").val()!=''||$("#threeEndTime").val()!=''||$("#threeSn").val()!='')){
//					$("#name_5").addClass("errorbor");
//					$("#name_5").siblings("div").css("display","");
//					return false;
//				}else if($("#threeStartTime").val()==''&&($("#name_5").val()!=''||$("#threeEndTime").val()!=''||$("#threeSn").val()!='')){
//					$("#name_5").removeClass("errorbor");
//					$("#name_5").siblings("div").css("display","none");
//					$("#threeStartTime").addClass("errorbor");
//					$("#threeStartTime").parent('div').siblings("div").css("display","");
//					return false;
//				}else if($("#threeSn").val()==''&&$("#threeStartTime").val()!=''&&$("#name_5").val()!=''){
//					$("#name_5").removeClass("errorbor");
//					$("#name_5").siblings("div").css("display","none");
//					$("#threeStartTime").removeClass("errorbor");
//					$("#threeStartTime").parent('div').siblings("div").css("display","none");
//					$("#threeSn").addClass("errorbor");
//					$("#threeSn").siblings("div").css("display","").html("许可证编号不允许为空");
//					return false;
//				}else{
//					$("#name_5").removeClass("errorbor");
//					$("#name_5").siblings("div").css("display","none");
//					$("#threeStartTime").removeClass("errorbor");
//					$("#threeStartTime").parent('div').siblings("div").css("display","none");
//					$("#threeSn").removeClass("errorbor");
//					$("#threeSn").siblings("div").css("display","none");
//				}
//				if($("#threeSn").val()!=''){
//					var flag = false;
//					$.each($("input[name='threeMedicalType']"),function(i,n){
//						if($(this).prop("checked")){
//							flag = true;
//							return false;
//						}
//					});
//					if(!flag){
//						layer.alert("请选择三类医疗资质");
//						return false;
//					}
//				}
				
				if($("#threeStartTime").val()==''&&($("#name_5").val()!=''||$("#threeEndTime").val()!=''||$("#threeSn").val()!='')){
					$("#name_5").removeClass("errorbor");
					$("#name_5").siblings("div").css("display","none");
					$("#threeStartTime").addClass("errorbor");
					$("#threeStartTime").parent('div').siblings("div").css("display","");
					return false;
				}else{
					$("#threeStartTime").removeClass("errorbor");
					$("#threeStartTime").parent('div').siblings("div").css("display","none");
				}
				if($("#threeStartTime").val()!=''){
					var flag = false;
					$.each($("input[name='threeMedicalType']"),function(i,n){
						if($(this).prop("checked")){
							flag = true;
							return false;
						}
					});
					if(!flag){
						layer.alert("请选择三类医疗资质");
						return false;
					}
				}
			}
		
//			if($("#name_6").val()==''&&($("#productStartTime").val()!=''||$("#productEndTime").val()!=''||$("#productSn").val()!='')){
//				$("#name_6").addClass("errorbor");
//				$("#name_6").siblings("div").css("display","");
//				return false;
//			}else if($("#productStartTime").val()==''&&($("#name_6").val()!=''||$("#productEndTime").val()!=''||$("#productSn").val()!='')){
//				$("#name_6").removeClass("errorbor");
//				$("#name_6").siblings("div").css("display","none");
//				$("#productStartTime").addClass("errorbor");
//				$("#productStartTime").parent('div').siblings("div").css("display","");
//				return false;
//			}else if($("#productSn").val()==''&&$("#productStartTime").val()!=''&&$("#name_6").val()!=''){
//				$("#name_6").removeClass("errorbor");
//				$("#name_6").siblings("div").css("display","none");
//				$("#productStartTime").removeClass("errorbor");
//				$("#productStartTime").parent('div').siblings("div").css("display","none");
//				$("#productSn").addClass("errorbor");
//				$("#productSn").siblings("div").css("display","").html("许可证编号不允许为空");
//				return false;
//			}else{
//				$("#name_6").removeClass("errorbor");
//				$("#name_6").siblings("div").css("display","none");
//				$("#productStartTime").removeClass("errorbor");
//				$("#productStartTime").parent('div').siblings("div").css("display","none");
//				$("#productSn").removeClass("errorbor");
//				$("#productSn").siblings("div").css("display","none");
//			}
			
			if($("#productStartTime").val()==''&&($("#name_6").val()!=''||$("#productEndTime").val()!=''||$("#productSn").val()!='')){
				$("#productStartTime").addClass("errorbor");
				$("#productStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#productStartTime").removeClass("errorbor");
				$("#productStartTime").parent('div').siblings("div").css("display","none");
			}
			
			if($("#operateStartTime").val()==''&&($("#name_7").val()!=''||$("#operateEndTime").val()!=''||$("#operateSn").val()!='')){
				$("#operateStartTime").addClass("errorbor");
				$("#operateStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#operateStartTime").removeClass("errorbor");
				$("#operateStartTime").parent('div').siblings("div").css("display","none");
			}
			
			// begin modify by franlin for[编辑供应商财务与资质信息，销售人员授权书应该为非必填项] at 2018-09-26
//			if($("#name_8").val() =='')
//			{
//				$("#name_8").addClass("errorbor");
//				$("#sale_auto_book").css("display","");
//				return false;
//			}
//			else
//			{
//				$("#name_8").removeClass("errorbor");
//				$("#sale_auto_book").css("display", "none");
//			}
//			if($("#saleAuthBookStartTime").val() == '')
//			{
//				$("#saleAuthBookStartTime").addClass("errorbor");
//				$("#sale_time_div").css("display","");
//				$("#sale_time_div").text("开始时间不能为空");
//				
//				return false;
//			}
//			else
//			{
//				$("#saleAuthBookStartTime").removeClass("errorbor");
//				$("#sale_time_div").css("display","none");
//			}
//			if($("#saleAuthBookEndTime").val() == '')
//			{
//				$("#saleAuthBookEndTime").addClass("errorbor");
//				$("#sale_time_div").css("margin-left","111px");
//				$("#sale_time_div").css("display","");
//				$("#sale_time_div").text("结束时间不能为空");
//				return false;
//			}
//			else
//			{
//				$("#saleAuthBookEndTime").removeClass("errorbor");
//				$("#sale_time_div").css("display","none");
//			}
			// end modify by franlin for[编辑供应商财务与资质信息，销售人员授权书应该为非必填项] at 2018-09-26
			if($("#name_9").val() != '' && $("#brandBookStartTime").val() == ''){
				$("#brandBookStartTime").addClass("errorbor");
				$("#brandBook_time_div").css("display","");
				$("#brandBook_time_div").text("开始时间不能为空");
				
				return false;
			}else{
				$("#brandBookStartTime").removeClass("errorbor");
				$("#brandBook_time_div").css("display","none");
			}
			
			if($("#name_9").val() == '' && $("#brandBookStartTime").val() != ''){
				$("#name_9").addClass("errorbor");
				$("#brand_book").css("display","");
				return false;
			}else{
				$("#name_9").removeClass("errorbor");
				$("#brand_book").css("display","none");
			}
			
			if(($("#name_9").val() != '' && $("#brandBookStartTime").val() != '' ) || ($("#name_9").val() == '' && $("#brandBookEndTime").val() == '')){
				$("#name_9").removeClass("errorbor");
				$("#brand_book").css("display","none");
			}else{
				$("#name_9").addClass("errorbor");
				$("#brand_book").css("display","");
				return false;
			}

//		$.ajax({
//			url:page_url+'/trader/supplier/saveAptitude.do',
//			data:$('#myform').serialize(),
//			type:"POST",
//			dataType : "json",
//			async: false,
//			success:function(data)
//			{
//				if(data.code==0){
//					layer.alert(data.message,{
//						closeBtn: 0,
//						btn: ['确定'] //按钮
//					}, function(){
//						if(data.code==2){
//							layer.alert(data.message);
//						}else{
//							var st=data.data.split(",");
//							var str=page_url+"/trader/supplier/getFinanceAndAptitude.do?traderId="+st[0]+"&traderSupplierId="+st[1];
//							$("#finace").attr('href',str);
//							window.parent.location.reload();
//						}
//						
//					});
//				}else{
//					layer.msg(data.message);
//				}
//			},
//			error:function(data){
//				if(data.status ==1001){
//					layer.alert("当前操作无权限")
//				}
//			}
//		});
//		return false;
	})
	
});
//删除
function delobj(obj){
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
	$(obj).parent("li").remove();
	layer.close(index);
		}, function(){
		});
}

function add(obj){
	checkLogin();
	$(obj).parent("li").append('<div class="f_left bt-bg-style bt-middle bg-light-red ml8" onclick="delobj(this);">删除</div>');
	$(obj).parent("li").children(".add").remove();
	var num =$("select").length;
	$.ajax({
		url:page_url+'/trader/customer/getMedicalTypeByAjax.do',
		data:'',
		type:"POST",
		dataType : "json",
		async: false,
		success:function(data)
		{
			var medicalTypes=data.data;
			var medicalTypLevels=data.listData;
			var li='<li><select class="input-middle f_left" name="medicalType" onchange="changeType(this);"><option value="">请选择资质类别</option>';
			for(var i=0; i<medicalTypes.length;i++){
				li+='<option value="'+medicalTypes[i].sysOptionDefinitionId+'">'+medicalTypes[i].title+'</option>';
			}
			li+='</select><div class="f_left inputfloat mt4 ml8 meddiv" style="display: none">';
			for(var i=0; i<medicalTypLevels.length;i++){
				li+='<input type="radio" name="medicalTypLevel_'+(num+1)+'" value="'
						+medicalTypLevels[i].sysOptionDefinitionId+'"><label class="mr8">'+medicalTypLevels[i].title+'</label>';
			}
			li+='</div><div class="f_left bt-bg-style bt-middle bg-light-blue ml8 add" onclick="add(this);">添加</div></li>';
			$("#medical_ul").append(li);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});

}

function changeType(obj){
	checkLogin();
	if($(obj).val()!='' && $(obj).val()!=194){
		$(obj).siblings(".meddiv").css("display","");
	}else{
		$(obj).siblings(".meddiv").css("display","none");
	}
}

function uploadFile(obj,num){
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	var domain = $("#domain").val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	// 转换为小写格式
	strExtension = strExtension.toLowerCase();
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' && strExtension != 'pdf' && strExtension != 'jpeg') {
		layer.alert("文件格式不正确");
		return;
	}
	
	  var fileSize = 0;
	  var isIE = /msie/i.test(navigator.userAgent) && !window.opera;      
	  if (isIE && !obj.files) {     
	     var filePath = obj.value;      
	     var fileSystem = new ActiveXObject("Scripting.FileSystemObject");  
	     var file = fileSystem.GetFile (filePath);        
	     fileSize = file.Size;     
	  }else { 
	     fileSize = obj.files[0].size;   
	  } 
	  fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
	  if(fileSize>5120){
		  layer.alert("上传附件不得超过5M");
	    return false;
	  }
	  $(obj).parent().parent().find("i").attr("class", "iconloading mt5").show();
		$(obj).parent().parent().find("a").hide();
		$(obj).parent().parent().find("span").hide();
		var objCopy1 = $(obj).parent();
		var objCopy2 = $(obj).parent().parent();
	$.ajaxFileUpload({
		url : page_url + '/fileUpload/ajaxFileUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {

				objCopy1.find("input[type='text']").val(data.fileName);
				objCopy1.find("input[type='hidden']").val(data.filePath+"/"+data.fileName);
				$("#domain").val(data.httpUrl);
				objCopy2.find("i").attr("class", "iconsuccesss ml7").show();
				objCopy2.find("a").attr("href", 'http://' + data.httpUrl + data.filePath+"/"+data.fileName).show();
				objCopy2.find("span").show();
				$("#brand_book").css("display","none");
				$("#name_"+num).removeClass("errorbor");
			} else {
				layer.alert(data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {

			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}else{
				layer.alert(data.responseText);
			}
			
		}
	});
	
}

function del(num){
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			var threeInOne=$('input:radio[name="threeInOne"]:checked').val();
			var medicalQualification=$('input:radio[name="medicalQualification"]:checked').val();
			
			if(threeInOne ==1 && num ==1){
				$("#img_icon_" + 1).hide();
				$("#img_view_" + 1).hide();
				$("#img_del_" + 1).hide();
				$("#name_"+ 1).val("");
				$("#uri_"+ 1).val("");
				$("#busStartTime").val("");
				$("#busEndTime").val("");
				$("input[name='isMedical']").removeAttr("checked");
				$("#img_icon_" + 2).hide();
				$("#img_view_" + 2).hide();
				$("#img_del_" + 2).hide();
				$("#name_"+ 2).val("");
				$("#uri_"+ 2).val("");
				$("#taxStartTime").val("");
				$("#taxEndTime").val("");
				$("#img_icon_" + 3).hide();
				$("#img_view_" + 3).hide();
				$("#img_del_" + 3).hide();
				$("#name_"+ 3).val("");
				$("#uri_"+ 3).val("");
				$("#orgaStartTime").val("");
				$("#orgaEndTime").val("");
				$("#file_"+ num).val("");
			}else if(threeInOne ==0){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				$("#img_del_" + num).hide();
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
				$("#file_"+ num).val("");
				if(num==1){
					$("#busStartTime").val("");
					$("#busEndTime").val("");
					$("input[name='isMedical']").removeAttr("checked");
				}else if(num==2){
					$("#taxStartTime").val("");
					$("#taxEndTime").val("");
				}else if(num==3){
					$("#orgaStartTime").val("");
					$("#orgaEndTime").val("");
				}
				
				
			}
			if(medicalQualification !=undefined && medicalQualification ==1 && num ==4){
				$("#img_icon_" + 4).hide();
				$("#img_view_" + 4).hide();
				$("#img_del_" + 4).hide();
				$("#name_"+ 4).val("");
				$("#uri_"+ 4).val("");
				$("#twoStartTime").val("");
				$("#twoEndTime").val("");
				$("#twoSn").val("");
				$("#img_icon_" + 5).hide();
				$("#img_view_" + 5).hide();
				$("#img_del_" + 5).hide();
				$("#name_"+ 5).val("");
				$("#uri_"+ 5).val("");
				$("#threeStartTime").val("");
				$("#threeEndTime").val("");
				$("#threeSn").val("");
				$("#file_"+ num).val("");
			}else if(medicalQualification ==0){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				$("#img_del_" + num).hide();
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
				$("#file_"+ num).val("");
				if(num==4){
					$("#twoStartTime").val("");
					$("#twoEndTime").val("");
					$("#twoSn").val("");
				}else if(num==5){
					$("#threeStartTime").val("");
					$("#threeEndTime").val("");
					$("#threeSn").val("");
				}
			}
			if(num==6){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				$("#img_del_" + num).hide();
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
				$("#productStartTime").val("");
				$("#productEndTime").val("");
				$("#productSn").val("");
				$("#file_"+ num).val("");
			}else if(num==7){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				$("#img_del_" + num).hide();
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
				$("#operateStartTime").val("");
				$("#operateEndTime").val("");
				$("#operateSn").val("");
				$("#file_"+ num).val("");
			}
			layer.close(index);
		}, function(){
		});
}

function medOne(){
	checkLogin();
	$("#img_del_5").removeClass("cursor-pointer");
	$("#img_del_5").prop("onclick",null).off("click");
	$("#threeStartTime").attr("disabled","disabled");
	$("#threeEndTime").attr("disabled","disabled");
}
function medZero(){
	checkLogin();
	$("#img_del_5").addClass("cursor-pointer");
	$("#img_del_5").attr("onclick","del(5)");
	$("#threeStartTime").removeAttr("disabled");  
	$("#threeEndTime").removeAttr("disabled"); 
}
function thOne(){
	checkLogin();
	$("#img_del_2").removeClass("cursor-pointer");
	$("#img_del_2").prop("onclick",null).off("click");
	$("#taxStartTime").attr("disabled","disabled");
	$("#taxEndTime").attr("disabled","disabled");
	$("#img_del_3").removeClass("cursor-pointer");
	$("#img_del_3").prop("onclick",null).off("click");
	$("#orgaStartTime").attr("disabled","disabled");
	$("#orgaEndTime").attr("disabled","disabled");
}
function thZero(){
	checkLogin();
	$("#img_del_2").addClass("cursor-pointer");
	$("#img_del_2").attr("onclick","del(2)");
	$("#taxStartTime").removeAttr("disabled");  
	$("#taxEndTime").removeAttr("disabled"); 
	$("#img_del_3").addClass("cursor-pointer");
	$("#img_del_3").attr("onclick","del(3)");
	$("#orgaStartTime").removeAttr("disabled");  
	$("#orgaEndTime").removeAttr("disabled"); 
}

//点击继续添加按钮
function con_add(id){
	var desc = '请上传二类备案凭证';
	if(id == 9)
	{
		desc = '请上传品牌授权书';
	}
	else if(id == 10)
	{
		desc = '请上传其他资质图片';
	}
	
	var rndNum = RndNum(8);
	var html = '<div >'+
					'<div class="pos_rel f_left mb8">'+
						'<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_'+id+'_'+rndNum+'" onchange="uploadFile(this, '+id+')">'+
						'<input type="text" class="input-middle" id="name_'+id+'_'+rndNum+'" readonly="readonly" placeholder="'+desc+'" name="name_'+id+'" onclick="lwfile_'+id+'_'+rndNum+'.click();" value ="">'+
					    '<input type="hidden" class="input-middle mr5 uri" id="uri_'+id+'_'+rndNum+'" name="uri_'+id+'" value="">'+
					    '<input type="hidden" class="oss" id="oss_'+id+'_'+rndNum+'" name="oss_'+id+'" value="">'+
					    '<label class="bt-bg-style bt-middle bg-light-blue " type="file" style="margin:0 17px 0 14px;">浏览</label>'+
					'</div>'+
					'<div class="f_left ">'+
						'<i class="iconsuccesss mt5 none" id="img_icon_'+id+'"></i>'+
						'<a href="" target="_blank" class="font-blue cursor-pointer mr10  ml10 mt4 none" id="img_view_'+id+'" style="margin:0 8px 0 12px">查看</a>'+
						'<span class="font-red cursor-pointer mt4" onclick="delAttachment(this)" id="img_del_'+id+'">删除</span>'+
					'</div>'+
				'<div class="clear "></div></div>';
	$("#conadd"+id).before(html);
}

function RndNum(n) {
    var rnd = "";
    for (var i = 0; i < n; i++)
        rnd += Math.floor(Math.random() * 10);
    return rnd;
}

function delAttachment(obj) {
    var uri = $(obj).parent().find("a").attr("href");
    if (uri == '') {
        $(obj).parent().parent().remove();
    } else {
        index = layer.confirm("您是否确认该操作？", {
            btn: ['确定', '取消'] //按钮
        }, function () {
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
        }, function () {
        });
    }
}
//全选
function clickAll(type){
	//全选
	 $("input[name="+type+"All]").click(function () {
       var thisChecked = $(this).prop('checked');
       $('input[name='+type+']').prop('checked',thisChecked);
     })
     var thisChecked = $("input[name="+type+"All]").prop('checked');
	 $('input[name='+type+']').prop('checked',thisChecked);
}
//单选
function clickOne(type){
	var num = $('input[name='+type+']:checked').length;
    var sum = $('input[name='+type+']').length;
   if(num == sum){
       $('input[name='+type+'All]').prop('checked',true);
   }else{
        $('input[name='+type+'All]').prop('checked', false);
   }
}
