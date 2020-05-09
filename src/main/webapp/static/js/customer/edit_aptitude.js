$(function() {
	var parseNum = function(num){
		return num <= 9 ? '0' + num : num;
	};

	window.changeDate = function(item){
		var $parents = $(item).parents('.J-date-wrap:first');
		var $date2 = $parents.find('.J-date2');
		if($(item).val() && !$.trim($date2.val())){
			var date1Ms = new Date($.trim($(item).val())).valueOf();
			var date2Ms = date1Ms + 365 * 1000 * 60 * 60 * 24 * 5;
			var date2 = new Date(date2Ms);
			var date2Str = [date2.getFullYear(), parseNum(date2.getMonth() + 1), parseNum(date2.getDate())].join('-');
			$date2.val(date2Str);
		}
	}
	//时间设置
	// $('.J-date1').change(function () {
	// 	var _this = this;
	// 	setTimeout(function () {
	// 		changeDate(_this)
	// 	}, 10);
	// });

	clickOne("twoMedicalType");
	clickOne("threeMedicalType");
	$("#file_1").change(function(){
		checkLogin();
		$("#uri_1").val($("#file_1").val());
	})

	$('.J-layer').click(function(e){
		let traderCustomerId=$("input[name='traderCustomerId']").val();
		var _this = this;
		if(checkUpContent()){
			$.ajax({
				type: "POST",
				url: "./isCanCheckAptitude.do",
				data: {'traderCustomerId': traderCustomerId},
				dataType: 'json',
				success: function (data) {
					if (data.code == 0) {
						$('.J-' + $(_this).data('type')).click();
					} else {
						layer.alert(data.message);
					}

				},
				error: function (data) {
					if (data.status == 1001) {
						layer.alert("当前操作无权限")
					}
				}
			})

		}
	})
	
	$("#file_2").change(function(){
		checkLogin();
		$("#uri_2").val($("#file_2").val());
	})

	$("#file_3").change(function(){
		checkLogin();
		$("#uri_3").val($("#file_3").val());
	})
	var threeInOne=$("#threeInOne").val();
	console.log("114"+threeInOne)
	if(threeInOne==1){
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
	}
	$("#file_4").change(function(){
		checkLogin();
		$("#uri_4").val($("#file_4").val());
	})
	$("#file_5").change(function(){
		checkLogin();
		$("#uri_5").val($("#file_5").val());
	})
	var qualification=$("#qualification").val();
	console.log("ttt:"+qualification);
	if(qualification=="1"){
		console.log("ttt 123 "+qualification);
		$("#three_medical input").attr("disabled",true);
		$("#three_medical label").addClass("bg-opcity");
		$("#three_medical .Wdate").addClass("bg-opcity");
		$("#three_medical .Wdate").css("cursor","not-allowed");
		$("#threeUpload").css("cursor","not-allowed");
		$("#threeUpload").prop("onclick",null).off("click");


		$("#threeMeicalAdd").css("pointer-events", "none");

		 // $("#conadd5").hide();
		// $("#threeMeicalAdd").attr("clickable",false)
	}
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
		$("#threeMeicalAdd").css("pointer-events", "none");
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
		$("#threeMeicalAdd").css("pointer-events", "auto");
	})

	var cansubmit = true;
	$("#submit").click(function() {
		checkLogin();

		if(checkUpContent()){
			if (cansubmit) {
				cansubmit = false;
			} else {
			    console.log("请勿重复提交");
				return false;
			}
		}else{
			return false;
		}
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
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	var domain = $("#domain").val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg' && strExtension != 'jpeg' && strExtension != 'png' && strExtension != 'bmp'
		&& strExtension != 'JPG' && strExtension != 'JPEG' && strExtension != 'PNG' && strExtension != 'BMP') {
		layer.alert("文件格式不正确");
		return false;
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
		  layer.alert("图片不能大于5MB");
	    return false;
	  }
	  $(obj).parent().parent().find("i").attr("class", "iconloading mt5").show();
	  $(obj).parent().parent().find("a").hide();
	  $(obj).parent().parent().find("span").hide();
	  var objCopy1 = $(obj).parent();
	  var objCopy2 = $(obj).parent().parent();
	$.ajaxFileUpload({
		url : page_url + '/fileUpload/uploadFile2Oss.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {

				objCopy1.find("input[type='text']").val(data.fileName);
				objCopy1.find("input[type='hidden']").val(data.filePath);
				$("#domain").val(data.httpUrl);
				objCopy2.find("i").attr("class", "iconsuccesss ml7").show();
				objCopy2.find("a").attr("href", 'http://' + data.httpUrl + data.filePath).show();
				objCopy2.find("span").show();
			} else {
				layer.alert("error"+data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data1, status, e) {

			if(data1.status ==1001){
				layer.alert("当前操作无权限")
			}else{
				var data=JSON.parse(data1);
				if (data.code == 0) {

					objCopy1.find("input[type='text']").val(data.fileName);
					objCopy1.find("input[type='hidden']").val(data.filePath);
					$("#domain").val(data.httpUrl);
					objCopy2.find("i").attr("class", "iconsuccesss ml7").show();
					objCopy2.find("a").attr("href", 'http://' + data.httpUrl + data.filePath).show();
					objCopy2.find("span").show();
				} else {
					layer.alert(data.message);
				}
			}
			
		}
	});
	
}

//点击继续添加按钮
function con_add(id,desc){
	var customerNature=$("#customerNature").val();
	var rndNum = RndNum(8);
	var kind=0;
	if(customerNature=='466'&&id==4){
		kind=0;
	}else{
		kind=id;
	}
	if($(".c_"+id)!=null) {
		var length = $(".c_" + id).length
		if (length > 2) {
			layer.alert("该种资质最多可上传3张，无法继续添加");
			return;
		}
	}
	var html = '<div class="c_'+kind+'">'+
					'<div class="pos_rel f_left mb8 ">'+
						'<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_'+id+'_'+rndNum+'" onchange="uploadFile(this, '+id+')">'+
						'<input type="text" class="input-middle" style="margin-right:10px;" id="name_'+id+'_'+rndNum+'" readonly="readonly" placeholder="'+desc+'" name="name_'+id+'" onclick="lwfile_'+id+'_'+rndNum+'.click();" value ="">'+
					    '<input type="hidden" class="input-middle mr5" id="uri_'+id+'_'+rndNum+'" name="uri_'+id+'" value="" >'+
					    '<label class="bt-bg-style bt-middle bg-light-blue ml4" type="file" >浏览</label>'+
					'</div>'+
					'<div class="f_left ">'+
						'<i class="iconsuccesss mt5 none" id="img_icon_'+id+'"></i>'+
						'<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_'+id+'" style="margin:0px 8px 0 13px;">查看</a>'+
						'<span class="font-red cursor-pointer mt4" onclick="delAttachment(this)" id="img_del_'+id+'">删除</span>'+
					'</div>'+
				'<div class="clear"></div></div>';
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
			}else if(threeInOne ==0){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				$("#img_del_" + num).hide();
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
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
			var customerProperty = $("#customerProperty").val();
			if(medicalQualification !=undefined && medicalQualification ==1 && num==4){
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
			}else if(medicalQualification ==undefined || medicalQualification ==0){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				$("#img_del_" + num).hide();
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
				if(customerProperty == 3 || customerProperty == 5){
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
			}
			if((customerProperty == 4 || customerProperty == 6)&& num ==4){
				$("#img_icon_" + num).hide();
				$("#img_view_" + num).hide();
				$("#img_del_" + num).hide();
				$("#name_"+ num).val("");
				$("#uri_"+ num).val("");
				$("#practiceStartTime").val("");
				$("#practiceEndTime").val("");
				$("#practiceSn").val("");
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
	$("#threeMeicalAdd").attr("disabled","disabled")
//	$("input[name='threeMedicalType']").attr("disabled","disabled");
//	$.each($("input[name='threeMedicalType']"),function(i,n){
//		if($(this).prop("checked")){
//			$(this).prop("checked", false);
//		}
//	});
}
function medZero(){
	checkLogin();
	$("#img_del_5").addClass("cursor-pointer");
	$("#img_del_5").attr("onclick","del(5)");
	$("#threeStartTime").removeAttr("disabled");  
	$("#threeEndTime").removeAttr("disabled"); 
	$("input[name='threeMedicalType']").removeAttr("disabled");
	
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

function closeTab(){
	$('#myform').submit();
	// pagesContrlpages(true, null, true);
}

function checkUpContent() {
	if($("#customerNature").val() == 465 ){
		var medicalQualification=$('input:radio[name="medicalQualification"]:checked').val();
		if(medicalQualification=="1"&&($("#name_4").val()==undefined||$("#name_4").val()=="")){
			$("#name_4").addClass("errorbor")
			$("#name_4").siblings("div").css("display","");
			return false;
		}else{
			$("#name_4").removeClass("errorbor")
			$("#name_4").siblings("div").css("display","none");
		}
		if($("#name_1").val()==undefined||$("#name_1").val()==""){
			$("#name_1").addClass("errorbor")
			$("#name_1").siblings("div").css("display","");
			// layer.alert("请选择营业执照")
			return false;
		}else{
			$("#name_1").removeClass("errorbor")
			$("#name_1").siblings("div").css("display","none");
		}

	}else{
		var isProfit=$('input:radio[name="isProfit"]:checked').val();
		if(isProfit==0&&($("#name_1").val()==undefined||$("#name_1").val()=="")){
			$("#name_1").addClass("errorbor")
			$("#name_1").siblings("div").css("display","");
			// layer.alert("请选择营业执照")
			return false;
		}else{
			$("#name_1").removeClass("errorbor")
			$("#name_1").siblings("div").css("display","none");
		}

	}

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
	if($("#customerNature").val()== 465 ||$("#customerNature").val()==undefined){
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

			// if($("#twoStartTime").val()!=''){
			// 	var flag = false;
			// 	$.each($("input[name='twoMedicalType']"),function(i,n){
			// 		if($(this).prop("checked")){
			// 			flag = true;
			// 			return false;
			// 		}
			// 	});
			// 	if(!flag){
			// 		layer.alert("请选择二类医疗资质");
			// 		return false;
			// 	}
			// 	var flag1 = false;
			// 	$.each($("input[name='threeMedicalType']"),function(i,n){
			// 		if($(this).prop("checked")){
			// 			flag1 = true;
			// 			return false;
			// 		}
			// 	});
			// 	if(!flag1){
			// 		layer.alert("请选择三类医疗资质");
			// 		return false;
			// 	}
			// }
			//
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

			if($("#twoStartTime").val()==''&&($("#name_4").val()!=''||$("#twoEndTime").val()!=''||$("#twoSn").val()!='')){
				$("#twoStartTime").addClass("errorbor");
				$("#twoStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#twoStartTime").removeClass("errorbor");
				$("#twoStartTime").parent('div').siblings("div").css("display","none");
			}
			// if($("#twoStartTime").val()!=''){
			// 	var flag = false;
			// 	$.each($("input[name='twoMedicalType']"),function(i,n){
			// 		if($(this).prop("checked")){
			// 			flag = true;
			// 			return false;
			// 		}
			// 	});
			// 	if(!flag){
			// 		layer.alert("请选择二类医疗资质");
			// 		return false;
			// 	}
			// }

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
			console.log($("#threeStartTime").val()+"+"+$("#threeSn").val()+"+"+$("#name_5").val()+"+"+$("#threeEndTime").val())
			if(($("#threeStartTime").val()==undefined||$("#threeStartTime").val()=='')&&($("#name_5").val()!=''||$("#threeEndTime").val()!=''||($("#threeSn").val()!=undefined&&$("#threeSn").val()!=''))){

				$("#threeStartTime").addClass("errorbor");
				$("#threeStartTime").parent('div').siblings("div").css("display","");
				return false;
			}else{
				$("#threeStartTime").removeClass("errorbor");
				$("#threeStartTime").parent('div').siblings("div").css("display","none");
			}
			// if($("#threeStartTime").val()!=''){
			// 	var flag = false;
			// 	$.each($("input[name='threeMedicalType']"),function(i,n){
			// 		if($(this).prop("checked")){
			// 			flag = true;
			// 			return false;
			// 		}
			// 	});
			// 	if(!flag){
			// 		layer.alert("请选择三类医疗资质");
			// 		return false;
			// 	}
			// }

		}
	}else if($("#customerNature").val()==466){
//			if($("#name_4").val()==''&&($("#practiceStartTime").val()!=''||$("#practiceEndTime").val()!=''||$("#practiceSn").val()!='')){
//				$("#name_4").addClass("errorbor");
//				$("#name_4").siblings("div").css("display","");
//				return false;
//			}else if($("#practiceStartTime").val()==''&&($("#name_4").val()!=''||$("#practiceEndTime").val()!=''||$("#practiceSn").val()!='')){
//				$("#name_4").removeClass("errorbor");
//				$("#name_4").siblings("div").css("display","none");
//				$("#practiceStartTime").addClass("errorbor");
//				$("#practiceStartTime").parent('div').siblings("div").css("display","");
//				return false;
//			}else if($("#practiceSn").val()==''&&$("#practiceStartTime").val()!=''&&$("#name_4").val()!=''){
//				$("#name_4").removeClass("errorbor");
//				$("#name_4").siblings("div").css("display","none");
//				$("#practiceStartTime").removeClass("errorbor");
//				$("#practiceStartTime").parent('div').siblings("div").css("display","none");
//				$("#practiceSn").addClass("errorbor");
//				$("#practiceSn").siblings("div").css("display","").html("许可证编号不允许为空");
//				return false;
//			}else{
//				$("#name_4").removeClass("errorbor");
//				$("#name_4").siblings("div").css("display","none");
//				$("#practiceStartTime").removeClass("errorbor");
//				$("#practiceStartTime").parent('div').siblings("div").css("display","none");
//				$("#practiceSn").removeClass("errorbor");
//				$("#practiceSn").siblings("div").css("display","none");
//			}

		if($("#practiceStartTime").val()==''&&($("#name_4").val()!=''||$("#practiceEndTime").val()!=''||$("#practiceSn").val()!='')){
			$("#practiceStartTime").addClass("errorbor");
			$("#practiceStartTime").parent('div').siblings("div").css("display","");
			return false;
		}else{
			$("#practiceStartTime").removeClass("errorbor");
			$("#practiceStartTime").parent('div').siblings("div").css("display","none");
		}
	}
	return true;
}

