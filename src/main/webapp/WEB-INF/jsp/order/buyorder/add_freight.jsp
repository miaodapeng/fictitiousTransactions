<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加运费" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" >
	function changePrice(){
		checkLogin();
		var price = $("#price").val();
		var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		if(price !='' && ( price.length > 10 || !priceReg.test(price) )){
			$("#price").addClass("errorbor");
			layer.alert("价格输入错误！仅允许使用数字，最多精确到小数点后两位");
			return false;
		}
		$("#ze").html(price);
	}
	$(function() {
		$("#sub").click(function(){
			checkLogin();
			var flag = false;
			$.each($("input[name='saleorderGoodsNum']",window.parent.document),function(i,n){
				var goodsId = $(this).attr("alt1");
				if(goodsId == 127063){
					flag = true;
					return;
				}
			});
			if(flag){
				layer.alert("当前订单中已含有运费，不允许重复添加！");
				return false;
			}
			
			$(".warning").remove();
			$("input").removeClass("errorbor");
			var price = $("#price").val();
			var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			if(price == ''){
				$("#price").addClass("errorbor");
				layer.alert("价格不允许为空");
				return false;
			}
			if(price.length > 10 || !priceReg.test(price)){
				$("#price").addClass("errorbor");
				layer.alert("价格输入错误！仅允许使用数字，最多精确到小数点后两位");
				return false;
			}
			$.ajax({
				url:page_url+'/order/buyorder/saveBuyorderFreight.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						//window.parent.location.reload();
						
						var len = $(".table-style7",window.parent.document).length;
						if($("#oldprice").val() == ''){
							len = len+1;
						}
						var table = '<table class="table table-style7" id="yf">'+
						                '<thead>'+
						                    '<tr>'+
						                    	'<th class="wid4">序号</th>'+
						                    	'<th class="wid8">订货号</th>'+
						                        '<th class="wid10">产品名称</th>'+
												'<th class="wid8">品牌</th>'+
												'<th class="wid8">型号</th>'+
												'<th class="wid9">物料编码</th>'+
												'<th class="wid6">单位</th>'+
												'<th class="wid11">可用库存 /库存量</th>'+
												'<th class="wid8">采购数量</th>'+
												'<th class="wid10">单价</th>'+
												'<th class="wid8">总额</th>'+
												'<th class="wid25">采购备注</th>'+
						                    '</tr>'+
						                '</thead>'+
						                '<tbody>'+
						                    '<tr>'+
						                    	'<td>'+len+'</td>'+
						                    	'<td>'+$("#sku").val()+'</td>'+
						                    	'<td class="text-left"><div class="customername pos_rel">'+
						                    	$("#goodsName").val()+			
						                        '</div></td>'+
						                        '<td>'+$("#brandName").val()+'</td>'+
						                        '<td>'+$("#model").val()+'</td>'+
						                        '<td></td>'+
						                        '<td>次</td>'+
						                        '<td></td>'+
						                        '<td>1<input type="hidden" name="buySum" alt="'+data.data+'" value="'+data.data+'|1"/></td>'+
						                        '<td><input type="hidden" name="price" alt="127063" value="'+data.data+'|'+$("#price").val()+'">'+
							                       $("#price").val()+
						                        '</td>'+
						                        '<td><span class="oneAllMoney">'+
						                        $("#price").val()+
						                        '</span></td>'+
						                        '<td><textarea alt="'+data.data+'" class="wid25" onblur="changComments(this,'+data.data+');">'+$("#insideComments").val()+'</textarea><input type="hidden" name="insideComments" value="'+data.data+'|'+$("#insideComments").val()+'"></td>'+
						                    '</tr>'+
						                '</tbody>'+
						            '</table>';
						var winTab = $("#yf",window.parent.document).remove();
						$(".tablelastline",window.parent.document).before(table);
						
						//计算总金额
						var allMoney = Number($("#zMoney",window.parent.document).html())+Number($("#price").val())-Number($("#oldprice").val());
						
						$("#zMoney",window.parent.document).html(allMoney.toFixed(2));
						var pay = $("#paymentType",window.parent.document).val();
						if(pay == 419){
							$("#prepaidAmount",window.parent.document).val(Number(allMoney).toFixed(2));
						}else if(pay == 420){
							var money = (Number(allMoney)*Number(0.8)).toFixed(2);
							$("#prepaidAmount",window.parent.document).val(money);
							$("#accountPeriodAmount",window.parent.document).val((Number(allMoney)-Number(money)).toFixed(2));
						}else if(pay == 421){
							var money = (Number(allMoney)*Number(0.5)).toFixed(2);
							$("#prepaidAmount",window.parent.document).val(money);
							$("#accountPeriodAmount",window.parent.document).val((Number(allMoney)-Number(money)).toFixed(2));
						}else if(pay == 422){
							var money = (Number(allMoney)*Number(0.3)).toFixed(2);
							$("#prepaidAmount",window.parent.document).val(money);
							$("#accountPeriodAmount",window.parent.document).val((Number(allMoney)-Number(money)).toFixed(2));
						}else if(pay == 423){
							$("#prepaidAmount",window.parent.document).val(0);
							$("#accountPeriodAmount",window.parent.document).val((Number(allMoney)).toFixed(2));
						}
						
					}else{
						layer.alert(data.message);
					}
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		    parent.layer.close(index);
			return false;
		});
		
	});
	
	
</script>
<div class="main-container">
	  <div class="form-list  form-tips5">
        <form method="post" action="" id="myform">
            <ul>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>产品名称</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">运费</div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>品牌/型号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<c:if test="${bgv.brandName != null}">
                            	${bgv.brandName}/${bgv.model}
                            </c:if>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>产品信息</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<c:if test="${bgv.sku != null}">
                        		${bgv.sku}/${bgv.manageCategoryName}
                        	</c:if>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>价格</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-small" name="price" id="price" onchange="changePrice();" value="${bgv.price}"/>
                            <input type="hidden" id="oldprice" onchange="changePrice();" value="${bgv.price}"/>
                        </div>
                    </div>
                </li>
                 <li>
                    <div class="form-tips">
                    	<span>*</span>
                        <lable>数量</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            1
                            <input type="hidden"  name="num"  value="1"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                    	<span>*</span>
                        <lable>单位</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                           		 次
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>总额</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks" id="ze">
                           		 ${bgv.price}
                        </div>
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter">
            	<input type="hidden" name="beforeParams" value="${beforeParams}"/>
            	<input type="hidden" name="formToken" value="${formToken}"/>
            	<input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}"/>
            	<input type="hidden" name="buyorderId" value="${bgv.buyorderId}"/>
            	<input type="hidden" name="goodsId" value="127063"/>
            	<input type="hidden" name="sku" id="sku" value="${bgv.sku}"/>
            	<input type="hidden" name="goodsName" id="goodsName" value="${bgv.goodsName}"/>
            	<input type="hidden" name="brandName" id="brandName" value="${bgv.brandName}"/>
            	<input type="hidden" name="insideComments" id="insideComments" value="${bgv.insideComments}"/>
            	<input type="hidden" name="model" id="model" value="${bgv.model}"/>
                <button type="button" class="bg-light-green" id="sub">提交</button>
                <button class="dele" id="close-layer" type="button" >取消</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>

