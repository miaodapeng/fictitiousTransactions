<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="table  table-bordered table-striped table-condensed table-centered">
    <thead>
    <tr>
        <th class="wid4">序号</th>
        <th class="wid15">产品名称</th>
        <th >品牌</th>
        <th class="wid8">型号</th>
        <th class="wid8">单价</th>
        <th class="wid8">原单价</th>
        <c:if test="${referenceCostPrice eq 1 }">
            <th class="wid8">参考成本</th>
        </c:if>
        <th class="wid4">数量</th>
        <th class="wid4">单位</th>
        <th >总额</th>
        <th >货期</th>
        <th class="wid5">直发</th>
        <th  class="wid10">核价参考</th>
        <th >占用/库存</th>
        <th >含安调</th>
        <th>类别管制</th>
        <th>产品备注</th>
        <th>内部备注</th>
        <th>锁定状态</th>
        <th>采购状态</th>
        <th>到库状态</th>
        <th>发货状态</th>
        <th>收货状态</th>
        <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start -->
        <th class="wid10">已开票/开票中数量</th>
        <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end -->
    </tr>
    </thead>
    <tbody>
    <c:set var="num" value="0"></c:set>
    <!-- 原订单总金额 -->
    <c:set var="frTotleMoney" value="0.00"></c:set>
    <c:set var="totleMoney" value="0.00"></c:set>
    <c:set var="isNotDelPriceZero" value="0"></c:set>
    <c:set var="isUrgent" value="0"></c:set>
    <c:set var="isCold" value="0"></c:set>
    <c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
        <c:if test="${list.isDelete eq 0}">
            <c:set var="num" value="${num + list.num}"></c:set>
            <c:set var="totleMoney" value="${totleMoney + list.maxSkuRefundAmount}"></c:set>
            <c:set var="frTotleMoney" value="${frTotleMoney + (list.realPrice * list.num)}"></c:set>
            <c:if test="${list.price == '0.00'}">
                <c:set var="isNotDelPriceZero" value="1"></c:set>
            </c:if>
        </c:if>
        <c:if test="${list.goodsId == '251526'}">
            <c:set var="isUrgent" value="1"></c:set>
        </c:if>
        <c:if test="${list.goodsId == '256675'}">
            <c:set var="isCold" value="1"></c:set>
        </c:if>
        <!-- 判断该商品是不是归属于当前登陆人 -->
        <c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
            <c:set var="shenhe" value="0"></c:set>
            <c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
                <c:if test="${verifyUsernameInfo == curr_user.username}">
                    <c:set var="shenhe" value="1"></c:set>
                </c:if>
            </c:forEach>
            <c:if test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name == '产品线归属人审核'}">
                <c:choose>
                    <c:when test="${fn:contains(list.goodsUserIdStr, loginUserId)}">
                        <c:set var="goodsCategoryUser" value="y"></c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="goodsCategoryUser" value="n"></c:set>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:if>
        <c:if test="${saleorder.verifyStatus != null and curr_user.positType == 311 and isCrossMonth == 0}">
            <c:choose>
                <c:when test="${fn:contains(list.goodsUserIdStr, loginUserId)}">
                    <c:set var="goodsCategoryUser" value="y"></c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="goodsCategoryUser" value="n"></c:set>
                </c:otherwise>
            </c:choose>
        </c:if>
        <tr class="<c:if test="${list.isDelete eq 1 or goodsCategoryUser eq 'n'}">caozuo-grey</c:if> J-skuInfo-tr">
            <td>${staut.count}</td>
            <td class="text-left">
                <div class="customername pos_rel">
                    <c:choose>
                        <c:when test="${list.isDelete eq 1}">
                            <span class="hc_order_detail_goodsName_br">${newSkuInfosMap[list.sku].SHOW_NAME} <%--${list.goodsName}<br/>--%></span>
                            <span class="hc_order_detail_skucode_br_materialcode">
                                ${newSkuInfosMap[list.sku].SKU_NO}<br>${newSkuInfosMap[list.sku].MATERIAL_CODE}
                                <%--${list.sku}<br>${list.goods.materialCode}--%>
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span class="font-blue">
                                <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>
                                    <c:if test="${list.isActionGoods eq 1}"><span style="color:red;">【活动】</span></c:if>
                                        <%--${list.goodsName}--%>
                                      ${newSkuInfosMap[list.sku].SHOW_NAME}
                                </a>&nbsp;
                                <i class="iconbluemouth contorlIcon"></i><br/>
                            </span>
                            <span class="hc_order_detail_skucode_br_materialcode">
                                    <%--${list.sku} <br>${list.goods.materialCode}--%>
                                    ${newSkuInfosMap[list.sku].SKU_NO}<br>${newSkuInfosMap[list.sku].MATERIAL_CODE}
                            </span>
                            <div class="pos_abs customernameshow">
                                物料编码：<%--${list.goods.materialCode}--%> ${newSkuInfosMap[list.sku].MATERIAL_CODE} <br>
                                注册证号： <%--${list.goods.registrationNumber}--%> ${newSkuInfosMap[list.sku].REGISTRATION_NUMBER}<br>
                                管理类别：<%--${list.manageCategoryName}--%> ${newSkuInfosMap[list.sku].MANAGE_CATEGORY_LEVEL}<br>
                                产品负责人：
                                    <%--<c:if test="${not empty list.goods.userList }">
                                        <c:forEach items="${list.goods.userList }" var="user" varStatus="st">
                                            ${user.username }
                                            <c:if test="${st.count != list.goods.userList.size() }">、</c:if>
                                        </c:forEach>
                                    </c:if>--%>
                                    ${newSkuInfosMap[list.sku].PRODUCTMANAGER}<br>

                                <%--采购提醒：${list.goods.purchaseRemind} <br>--%>
                                包装清单：<%--${list.goods.packingList}--%> ${newSkuInfosMap[list.sku].PACKING_LIST}<br>
                                <%--服务条款：${list.goods.tos} <br>--%>
                                质保年限：${newSkuInfosMap[list.sku].QA_YEARS} <br>
                                <%--库存：<span id="kc_${list.saleorderGoodsId}">${list.goods.stockNum}</span> <br>
                                可用库存：<span id="kykc_${list.saleorderGoodsId}">${(list.goods.stockNum-list.goods.orderOccupy) < 0 ? 0 : (list.goods.stockNum-list.goods.orderOccupy)}</span><br>
                                订单占用：<span id="dzzy_${list.saleorderGoodsId}">${list.goods.orderOccupy}</span><br>--%>

                                库存：<span>${newSkuInfosMap[list.sku].STOCKNUM}</span> <br>
                                可用库存：<span>${newSkuInfosMap[list.sku].AVAILABLESTOCKNUM}</span><br>
                                订单占用：<span>${newSkuInfosMap[list.sku].OCCUPYNUM}</span><br>
                                可调剂：<span id="ktj_${list.saleorderGoodsId}">${list.goods.adjustableNum}</span> <br>
                                审核状态： ${newSkuInfosMap[list.sku].CHECK_STATUS}
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </td>
            <td>
                    <%--${list.brandName}--%>
                    ${newSkuInfosMap[list.sku].BRAND_NAME}
            </td>
            <td>
                    <%--${list.model}--%>
                    ${newSkuInfosMap[list.sku].MODEL}
            </td>
            <!-- 优惠单价-->
            <td>${list.price}</td>
            <!-- 实际单价 -->
            <td>
                <fmt:formatNumber type="number" value="${null == list.realPrice ? 0 : list.realPrice}" pattern="0.00" maxFractionDigits="2" />
            </td>
            <c:if test="${referenceCostPrice eq 1 }">
                <!-- 如果是采购部并且非待审核的 -->
                <c:choose>
                    <c:when test="${saleorder.validStatus != null and curr_user.positType == 311 and isCrossMonth == 0}">
                        <td>
                            <input type="text" name="referenceCostPrice_${staut.count}" value="${list.referenceCostPrice == null || list.referenceCostPrice == '0.00' ?(list.costPrice == null ?'0.00':list.costPrice):list.referenceCostPrice}" alt="${list.referenceCostPrice == null || list.referenceCostPrice == '0.00'?'0.00':list.referenceCostPrice}" goodsCategoryUser="${goodsCategoryUser}">
                            <input type="hidden" name="saleorderGoodsId" value="${list.saleorderGoodsId}" />
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td>${list.referenceCostPrice}</td>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <td>
                <c:choose>
                    <c:when test="${((list.afterReturnNum==''||list.afterReturnNum==null) ? 0 : list.afterReturnNum) eq 0}">
                        ${list.num}
                    </c:when>
                    <c:otherwise>
                        <div class="customername pos_rel">
				                        	<span>
				                        		${list.num - list.afterReturnNum}
				                        		<i class="iconredsigh ml4 contorlIcon"></i>
				                        	</span>
                            <div class="pos_abs customernameshow">原值：${list.num}</div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    <%--${list.unitName}--%>
                    ${newSkuInfosMap[list.sku].UNIT_NAME}
            </td>
            <!-- 订单实际金额 -->
            <td>
                <fmt:formatNumber type="number" value="${list.maxSkuRefundAmount - list.afterReturnAmount}" pattern="0.00" maxFractionDigits="2" />
            </td>
            <td>${list.deliveryCycle}</td>
            <td>
                <div class="customername pos_rel">
	                                <span>
	                                <c:choose>
										<c:when test="${list.deliveryDirect eq 0}">否</c:when>
										<c:otherwise>
										是
										<i class="iconbluesigh ml4 contorlIcon"></i></span>
                    <div class="pos_abs customernameshow">直发原因：${list.deliveryDirectComments}</div>
                    </c:otherwise>
                    </c:choose>
                </div>
            </td>
            <td>
                <div class="customername pos_rel">
                    核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
                    参考价格：${list.referencePrice} <br/>
                    参考货期：${list.referenceDeliveryCycle} <br/>
                    <c:if test="${settlementPrice eq 1 }">
                        结算价：${list.settlePrice} <br/>
                    </c:if>
                    <c:choose>
                        <c:when test="${list.reportStatus eq 2}">
                            报备成功<i class="iconbluesigh ml4"></i>
                        </c:when>
                        <c:when test="${list.reportStatus eq 3}">
                            报备失败<i class="iconredsigh ml4"></i>
                        </c:when>
                        <c:otherwise>
                            <i class="iconbluesigh ml4"></i>
                        </c:otherwise>
                    </c:choose>
                    <div class="pos_abs customernameshow">
                        <c:set var="goodsUserNm" value=""/>
                        <c:forEach var="user" items="${userList}">
                            <c:if test="${user.userId eq list.lastReferenceUser}"><c:set var="goodsUserNm" value="${user.username}"/></c:if>
                        </c:forEach>
                        核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
                        客户上次购买价格：<fmt:formatNumber type="number" value="${list.lastOrderPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
                        参考价格（${goodsUserNm}）：${list.referencePrice} <br/>
                        期货交货期：${list.channelDeliveryCycle} <br>
                        现货交货期：${list.delivery} <br>
                        参考货期（${goodsUserNm}）：${list.referenceDeliveryCycle} <br/>
                        报备结果：${list.reportStatus}
                        <c:if test="${list.reportStatus eq 2}">
                            成功 <br/>
                            报备失败原因：<%-- ${list.reportComments} --%>
                        </c:if>
                        <c:if test="${list.reportStatus eq 3}">
                            失败 <br/>
                            报备失败原因：${list.reportComments}
                        </c:if>
                    </div>
                </div>
            </td>
            <td><span id="orderOccupy_stockNum_${list.saleorderGoodsId}">${list.goods.orderOccupy}/${list.goods.stockNum}</span></td>
            <td>
                <c:choose>
                    <c:when test="${list.haveInstallation eq 0}">否</c:when>
                    <c:otherwise>是</c:otherwise>
                </c:choose>
            </td>
            <td>---</td>
            <td>${list.goodsComments}</td>
            <td>${list.insideComments}</td>

            <td>
                <c:choose>
                    <c:when test="${list.lockedStatus eq 0}"><span>-</span></c:when>
                    <c:otherwise><span style="color: red;">锁</span></c:otherwise>
                </c:choose>
            </td>

            <td><span id="cgztStr_${list.saleorderGoodsId}"></span></td>
            <td><span id="dkztStr_${list.saleorderGoodsId}"></span></td>
            <td>
                <c:choose>
                    <c:when test="${list.deliveryStatus eq 0}">未发货</c:when>
                    <c:when test="${list.deliveryStatus eq 1}"><span style="color:green;">部分发货</span></c:when>
                    <c:when test="${list.deliveryStatus eq 2}"><span style="color:green;">已发货</span></c:when>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${list.arrivalStatus eq 0}">未收货</c:when>
                    <c:when test="${list.arrivalStatus eq 1}"><span style="color:green;">部分收货</span></c:when>
                    <c:when test="${list.arrivalStatus eq 2}"><span style="color:green;">已收货</span></c:when>
                </c:choose>
            </td>
            <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start -->
            <td>${list.invoicedNum}/${list.appliedNum}</td>
            <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end -->
        </tr>
    </c:forEach>
    <tr style="background: #eaf2fd;">
        <c:choose>
        <c:when test="${referenceCostPrice eq 1}">
        <td colspan="24" class="text-left">
            </c:when>
            <c:otherwise>
        <td colspan="23" class="text-left">
            </c:otherwise>
            </c:choose>
            <input type="hidden" value="${isNotDelPriceZero}" id="isNotDelPriceZero">
            总件数 <span class="font-red"> ${num}</span>，订单原金额
            <span class="font-red">
                               <fmt:formatNumber type="number" value="${saleorder.totalAmount}" pattern="0.00" maxFractionDigits="2" />
                            </span> ，订单实际金额
            <span class="font-red"><fmt:formatNumber type="number" value="${saleorderDataInfo['realAmount']}" pattern="0.00" maxFractionDigits="2" /></span>
            <!-- 优惠金额 -->
            ，优惠金额 <span class="font-red"><fmt:formatNumber type="number" value="${null == saleorder.couponAmount ? 0 : saleorder.couponAmount}" pattern="0.00" maxFractionDigits="2" /></span>
            <shiro:hasPermission name="/order/saleorder/showCostAmountAndRate.do">
                ，（五行）手填总成本 <span class="font-red"><fmt:formatNumber type="number" value="${totalReferenceCostPrice}" pattern="0.00" maxFractionDigits="2" /></span>
                ，（五行）毛利率 <span class="font-red">
                        	<c:choose>
                                <c:when test="${totleMoney*1 <=0  }">-100%</c:when>
                                <c:otherwise>
                                    <fmt:formatNumber type="number" value="${(totleMoney - totalReferenceCostPrice ) / totleMoney * 100}" pattern="0.00" maxFractionDigits="2" />%
                                </c:otherwise>
                            </c:choose>
                        	</span>
            </shiro:hasPermission>
        </td>
    </tr>
    </tbody>
</table>