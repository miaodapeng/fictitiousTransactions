<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="编辑快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class=" logistics-warehousein-addWarehouseIn">
	  <div class="form-list  form-tips8">
        <form method="post" action="" id="">
            <ul>
                <li>
                    <div class="form-tips">
                        <span id="lno">*</span>
                        <lable>快递单号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	 <input type="text" class="input-middle" name="logisticsNo" id="logisticsNo" value="${expressList.logisticsNo}"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>快递公司</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select name="logisticsId" id="logisticsId">
                            <c:forEach var="logistics" items="${logisticsList}">
                             	<option  value="${logistics.logisticsId}" <c:if test="${logistics.logisticsId == expressList.logisticsId}">selected</c:if>>${logistics.name}</option>
                            </c:forEach>
                            </select>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>发货时间</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<input id="deliveryTimes" class="Wdate f_left input-smaller96 mr5" type="text" name="deliveryTimes" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="<date:date value ="${expressList.deliveryTime}" format="yyyy-MM-dd"/>"  format="yyyy-MM-dd"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>运费</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<c:set var="amount" value="0.00"></c:set>
                        	<c:forEach var="expressDetails" items="${expressList.expressDetail}">
                        		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
                        	</c:forEach>
                            <input type="text" class="input-small" name="amount" id="amount" value="${amount}"/>
                        </div>
                    </div>
                </li>
                <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>件数</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="_num" id="_num" value="${expressList.j_num}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
                    </div>
                </div>
            </li>
                 <li>
                    <div class="form-tips">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-largest" name="logisticsComments" id="logisticsComments" value="${expressList.logisticsComments}"/>
                        </div>
                    </div>
                </li>
                <div id="sfkd" style="display: none;">
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>月结卡号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-small" name="cardnumber" id="cardnumber" value="${expressList.cardnumber}"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>付款方式</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="hidden" id="_paymentType" value="${expressList.paymentType}"/>
                            <input type="radio" id="paymentType1" name="paymentType" value="1" /><span>寄付月结 </span>
							<input type="radio" id="paymentType2" name="paymentType" value="2" /> <span>寄付现结 </span>
							<input type="radio" id="paymentType3" name="paymentType" value="3" /><span>到付现结 </span>
							<input type="radio" id="paymentType4" name="paymentType" value="4" /><span>第三方付 </span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>业务类型</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="hidden" id="_business_type" value="${expressList.business_Type}"/>
                            <select name="business_type" id="business_type">
                             	<option value="1">顺丰特惠</option>
                             	<option value="2">物流普运</option>
                             	<option value="3">顺丰标快</option>
                            </select>
                        </div>
                    </div>
                </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>实际重量</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="realWeight" id="realWeight" value="${expressList.realWeight}"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>计费重量</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="amountWeight" id="amountWeight" value="${expressList.amountWeight}" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>托寄物</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="mailGoods" id="mailGoods" value="${expressList.mailGoods}"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>托寄物数量</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="mailGoodsNum" id="mailGoodsNum" value="${expressList.mailGoodsNum}"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>是否保价</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="hidden" id ="_isProtectPrice" value="${expressList.isProtectPrice}">
                        <input type="radio" id="isProtectPrice1" name="isProtectPrice" value="1" /><span>是 </span>
						<input type="radio" id="isProtectPrice0" name="isProtectPrice" value="0" /><span>否</span>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>保价金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="protectPrice" id="protectPrice" value="${expressList.protectPrice}"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>签回单</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="hidden" id="_isReceipt" value="${expressList.isReceipt}"/>
                        <input type="radio" id="isReceipt1" name="isReceipt" value="1" /><span>是 </span>
						<input type="radio" id="isReceipt0" name="isReceipt" value="0" /><span>否</span>
                    </div>
                </div>
            </li>
             <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>寄方备注</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="mailCommtents" id="mailCommtents" value="${expressList.mailCommtents}"/>
                    </div>
                </div>
            </li>
            </div>
            </ul>
            <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	选择出库商品
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">选择</th>
                         <th>产品名称</th>
                        <th class="wid15">订货号</th>
                        <th>品牌</th>
                        <th>型号</th>
                         <th>物料编码</th>
                        <th>总数</th>
                        <th>已发</th>
                        <th>快递还可发货数量</th>
                        <th class="wid8">单位</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="goods" items="${lendout.goodslist}">
                	 <tr>
                	  
                        <td>
                            <input type="checkbox" name="b_checknox" value="${lendout.lendOutId}" 
                            <c:if test="${expressNumMap[lendout.lendOutId]==0 && (lendout.lendOutNum == allExpressNumMap[lendout.lendOutId])}">disabled</c:if> <c:if test="${expressNumMap[lendout.lendOutId]>0}">checked</c:if> onclick="canelAll(this,'b_checknox')">
                        </td>
                        <td class="text-left">
                             <div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${goods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}","title":"产品信息"}'>${goods.goodsName}</a>
	                        </div>
                        </td>
                        <td>${goods.sku}</td>
                        <td>${goods.brandName}</td>
                        <td>${goods.model}</td>
                         <td>${goods.materialCode}</td>
                        <td style="display: none;">${goods.price}<input type="hidden" name="price" value="${goods.price}"/></td>
                       
                        <td class="jianhuozongshu">
                             <span>${lendout.lendOutNum}</span>
                        </td>
                        <td class="jianhuozongshu"><span>
                        <input name="num" type="text" value="${expressNumMap[lendout.lendOutId]}" onchange="checkNum(this,${goods.deliveryNum-allExpressNumMap[lendout.lendOutId]+expressNumMap[lendout.lendOutId]},${expressNumMap[lendout.lendOutId]}, ${expressNumMap[lendout.lendOutId]})">
                        </span></td>
                        <td class="jianhuozongshu">${goods.deliveryNum-goods.eNum}</td>
                        <td>${goods.unitName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
		<div class="table-style4">
			<div class="allchose">
			<input type="checkbox" name="all_b_checknox" onclick="selectall(this,'out_checkbox');" value="c_checknox"> <span>全选</span>
			</div>
			<%-- <div class="times">
				<span class="mr10">请选择批次</span>
				<c:forEach var="wtlist" items="${timeArrayWl}" varStatus="num">
                 <input type="checkbox" name="${wtlist}" id="out_checkbox" onclick="checkbarcode('${saleorder.saleorderId}');">
                 <span class="mr20">${wtlist}</span>
                </c:forEach>
			</div> --%>
		</div>
            <div class="table-friend-tip">
		                                       友情提示
		         <br/>1、快递发货数量=已出库数量-已快递出库数量；
		     </div>
        </div>
            <div class="add-tijiao tcenter">
                <input type="hidden" name="formToken" value="${formToken}"/>
                <input type="hidden" name="isInvoicing" value="${expressList.isInvoicing}">
                <input type="hidden" name="beforeParams" value='${beforeParams}'>
            	<input type="hidden" name="expressId" value="${expressList.expressId}"/>
                <button type="button" class="bg-light-blue" onclick="addExpress('3',${lendout.lendOutId})">确定</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/add_lendout_Express.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
