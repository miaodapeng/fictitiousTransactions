<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="新增快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class="logistics-warehousein-addWarehouseIn">
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
                        	 <input type="text" class="input-middle" name="logisticsNo" id="logisticsNo"/>
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
                             	<option value="${logistics.logisticsId}">${logistics.name}</option>
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
                        <div class="form-blanks" >
                        	<input id="deliveryTimes" class="Wdate f_left input-smaller96 mr5" type="text" name="deliveryTimes" placeholder="请选择日期" value="${now}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   />
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
                            <input type="text" class="input-small" name="amount" id="amount"/>
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
                        <input type="text" class="input-small" name="_num" id="_num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" />
                    </div>
                </div>
                </li>
                 <li>
                    <div class="form-tips">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-largest" name="logisticsComments" id="logisticsComments"/>
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
                            <input type="text" class="input-small" name="cardnumber" id="cardnumber" value="0253523464"/>
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
                            <input type="radio" name="paymentType" value="1" checked="checked" /><span>寄付月结 </span>
							<input type="radio" name="paymentType" value="1" /> <span>寄付现结 </span>
							<input type="radio" name="paymentType" value="2" /><span>到付现结 </span>
							<input type="radio" name="paymentType" value="3" /><span>第三方付 </span>
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
                            <select name="business_type" id="business_type">
                             	<option value="1" >顺丰特惠</option>
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
                        <input type="text" class="input-small" name="realWeight" id="realWeight" value="1"/>
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
                        <input type="text" class="input-small" name="amountWeight" id="amountWeight" value="1"/>
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
                        <input type="text" class="input-small" name="mailGoods" id="mailGoods" value="货物"/>
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
                        <input type="text" class="input-small" name="mailGoodsNum" id="mailGoodsNum"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>是否保价</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="radio" id="isProtectPrice1" name="isProtectPrice" value="1" /><span>是 </span>
						<input type="radio" id="isProtectPrice0" name="isProtectPrice" value="0" checked="checked"/><span>否</span>
                    </div>
                </div>
            </li>
            <li style="display: none;" id="bjje">
                <div class="form-tips">
                    <lable>保价金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="protectPrice" id="protectPrice"/>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>签回单</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="radio" name="isReceipt" value="1" /><span>是 </span>
						<input type="radio" name="isReceipt" value="0" checked="checked"/><span>否</span>
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
                        <input type="text" class="input-small" name="mailCommtents" id="mailCommtents" value="节假日正常派送，提前联系"/>
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
                        <th>快递发货数量</th>
                        <th class="wid8">单位</th>
                    </tr>
                </thead>
                <tbody>
                 <c:set var="lockedStatusFlag" value="-1"></c:set>
                <c:forEach var="saleorderGoods" items="${saleorderGoodsList}">
                	 <tr>
                	  
                        <td>
                           <c:if test="${saleorderGoods.lockedStatus == 1}">
					         <font color="red">锁</font>
					        </c:if>
					        <c:if test="${saleorderGoods.lockedStatus == 0}">
					         <c:set var="lockedStatusFlag" value="1"></c:set>
					         <c:if test="${(saleorderGoods.deliveryNum-saleorderGoods.eNum) !=0}">
                            <input type="checkbox" name="b_checknox" value="${saleorderGoods.saleorderGoodsId}"  onclick="canelAll(this,'b_checknox')"
                            <c:if test="${not empty saleorder.msaleorderNo &&  saleorderGoods.goods.goodsType eq 316}">alt="mqixie"</c:if>
                            >
                            </c:if>
					        </c:if>
                        </td>
                        <td class="text-left">
                             <div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${saleorderGoods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${saleorderGoods.goodsId}","title":"产品信息"}'>${saleorderGoods.goodsName}</a>
	                        </div>
                        </td>
                        <td>${saleorderGoods.sku}<input type="hidden" name="sku" value="${saleorderGoods.sku}"</td>
                        <td>${saleorderGoods.brandName}</td>
                        <td>${saleorderGoods.model}</td>
                         <td>${saleorderGoods.materialCode}</td>
                        <td style="display: none;">${saleorderGoods.price}<input type="hidden" name="price" value="${saleorderGoods.price}"/></td>
                       
                        <td class="jianhuozongshu">
                             <span>${saleorderGoods.num}</span>
                        </td>
                        <td><span>${saleorderGoods.deliveryNum}</span></td>
                        <c:if test="${(saleorderGoods.deliveryNum-saleorderGoods.eNum) !=0}">
                        <td class="jianhuozongshu"><input name="num" type="text" 
                        <c:choose>
                        	<c:when test="${not empty saleorder.msaleorderNo &&  saleorderGoods.goods.goodsType eq 316}">value="1" disabled="disabled"</c:when>
                        	<c:otherwise> value="${saleorderGoods.deliveryNum-saleorderGoods.eNum}"  onchange="checkNum(this,${saleorderGoods.deliveryNum-saleorderGoods.eNum},${expressNumMap[saleorderGoods.saleorderGoodsId]},0)"</c:otherwise>
                        </c:choose>
                        ></td>
                        </c:if>
                         <c:if test="${(saleorderGoods.deliveryNum-saleorderGoods.eNum) ==0}">
                        <td class="jianhuozongshu">${saleorderGoods.deliveryNum-saleorderGoods.eNum}</td>
                        </c:if>
                        <td>${saleorderGoods.unitName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${lockedStatusFlag eq 1}">
		<div class="table-style4">
			<div class="allchose">
			<input type="checkbox" name="all_b_checknox" onclick="selectall(this,'out_checkbox');" value="c_checknox"> <span>全选</span>
			</div>
		</div>
		</c:if>
            <div class="table-friend-tip">
		                                       友情提示
		         <br/>1、快递发货数量=已出库数量-已快递出库数量；
		         <br/>2、美年的器械设备只能一个一个的增加快递；
		     </div>
        </div>
            <div class="add-tijiao tcenter">
                 <input type="hidden" name="formToken" value="${formToken}"/>
                 <input type="hidden" name="beforeParams" value='${beforeParams}'>
            	<input type="hidden" name="expressId" value="0"/>
                <input type="hidden" name="Identifier" value="保存"/>
                <button type="button" class="bg-light-blue" onclick="addExpress('1',${saleorder.saleorderId})">确定</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/addExpress.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
