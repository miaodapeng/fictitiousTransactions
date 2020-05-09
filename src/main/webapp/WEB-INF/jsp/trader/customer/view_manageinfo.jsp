<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:set var="title" value="管理信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ include file="customer_tag.jsp"%>
<div class="content">
       <div class="parts">
           <div class="title-container">
               <div class="table-title">管理信息</div>
               <c:choose>
            	<c:when test="${traderCustomer.isEnable == 1 && ((traderCustomer.verifyStatus != null && traderCustomer.verifyStatus != 0 )|| traderCustomer.verifyStatus == null)}">
              <div class="font-red f_right mr8 pop-new-data" layerParams='{"width":"600px","height":"200px","title":"禁用","link":"./initDisabledPage.do?traderCustomerId=${traderCustomer.traderCustomerId}"}'>禁用</div>
            	</c:when>
            	<c:when test="${traderCustomer.isEnable != 1 }">
            		<div class="title-click f_right mr8" onclick="setDisabled(${traderCustomer.traderCustomerId},${traderCustomer.traderId},1);">启用</div>
            	</c:when>
            </c:choose>
            <c:if test="${traderCustomer.isEnable == 1 && ((traderCustomer.verifyStatus != null && traderCustomer.verifyStatus != 0 )|| traderCustomer.verifyStatus == null)}">
            <div class="title-click">
            	<a href="${pageContext.request.contextPath}/trader/customer/editmanageinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}">编辑</a>
           	</div>
           	</c:if>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <tbody>
                <tr>
                    <td class="table-smallest">创建时间</td>
                    <td class="table-middle"><date:date value ="${traderCustomer.addTime} "/></td>
                    <td class="table-smallest">归属销售</td>
                    <td class="table-middle">${traderCustomer.ownerSale }</td>
                </tr>
                <tr>
                    <td>客户来源</td>
                    <td>${traderCustomer.customerFromStr }</td>
                    <td>首次询价方式</td>
                    <td>${traderCustomer.firstEnquiryTypeStr }</td>
                </tr>
                <tr>
                    <td>客户等级、合作等级</td>
                    <td>
                      ${traderCustomer.customerLevelStr}、${traderCustomer.traderLevelStr}
                    </td>
                    <td>客户分类</td>
                    <td>
                        <c:if test="${traderCustomer.customerCategory eq 0 }">未成交客户</c:if>
                        <c:if test="${traderCustomer.customerCategory eq 1 }">新客户</c:if>
                        <c:if test="${traderCustomer.customerCategory eq 2 }">流失客户</c:if>
                        <c:if test="${traderCustomer.customerCategory eq 3 }">留存客户</c:if>
                    </td>
                </tr>
                <tr>
                    <td>客户资料分数</td>
                    <td>${traderCustomer.customerScore}</td>
                    <td>销售评级</td>
                    <td>${traderCustomer.userEvaluateStr}</td>

                </tr>
                <tr>
                    <td>贝登VIP</td>
                    <td>
                    	<c:choose>
                     	<c:when test="${traderCustomer.isVip == 1}">
                     	是
                     	</c:when>
                     	<c:otherwise>否</c:otherwise>
                    	</c:choose>
                    </td>
                    <td>基础医疗经销商</td>
                    <td>
                        <c:choose>
                            <c:when test="${traderCustomer.basicMedicalDealer == 1}">
                                是
                            </c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>

                </tr>
                <tr>
                    <td>商机数量</td>
                    <td>${traderCustomer.bussinessChanceCount}</td>
                    <td>战略合作伙伴</td>
                    <td>
                        <c:forEach items="${traderCustomer.traderCustomerAttributes }" var="attr">
                            ${attr.sysOptionDefinition.title }、
                        </c:forEach>
                    </td>

                </tr>
                <tr>
                    <td>最后交易时间</td>
                    <td><date:date value ="${traderCustomer.lastBussinessTime}"/></td>
                    <td>报价数量</td>
                    <td>${traderCustomer.quoteCount}</td>

                </tr>
                <tr>
                    <td>交易次数</td>
                    <td>${traderCustomer.buyCount}</td>

                    <td>交易金额</td>
                    <td>${traderCustomer.buyMoney}</td>

                </tr>
                <tr>
                    <td>均单价</td>
                    <td>${traderCustomer.averagePrice}</td>

                    <td>交易频次</td>
                    <td>${traderCustomer.transactionFrequency}</td>

                </tr>
                <tr>
                    <td>沟通次数</td>
                    <td>${traderCustomer.communcateCount}</td>

                    <td>上次沟通时间</td>
                    <td><date:date value ="${traderCustomer.lastCommuncateTime} "/></td>

                </tr>
                <tr>
                    <td>禁用状态 </td>
                    <td>
                        <c:choose>
                            <c:when test="${traderCustomer.isEnable == 1}">
                                未禁用
                            </c:when>
                            <c:otherwise><span class="font-red">已禁用</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>禁用时间</td>
                    <td><date:date value ="${traderCustomer.disableTime} "/></td>
                </tr>
                <tr>
                    <td>禁用原因</td>
                    <td colspan="3" class="text-left">${traderCustomer.disableReason}</td>
                </tr>
                <tr>
                    <td>客户标签</td>
                    <td colspan="3" class="text-left">
                    	<c:if test="${not empty traderCustomer.tag }">
                     <ul class="communicatecontent ml0">
                     	<c:forEach items="${traderCustomer.tag }" var="tag">
                          <li class="bluetag">${tag.tagName}</li>
                     	</c:forEach>
                     </ul>
                     </c:if>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3" class="text-left">${traderCustomer.comments}</td>
                </tr>
                <tr>
                    <td>物流信息备注</td>
                    <td colspan="3" class="text-left">${traderCustomer.logisticsComments}</td>
                </tr>

                <tr>
                    <td>财务信息备注</td>
                    <td colspan="3" class="text-left">${traderCustomer.financeComments}</td>
                </tr>
                <tr>
                    <td>科室</td>
                    <td colspan="3" class="text-left" id="orderGoodsOffice"></td>
                </tr>
                <tr>
                    <td>订单覆盖品类</td>
                    <td colspan="3" class="text-left" id="orderCoverCategory"></td>
                </tr>
                <tr>
                    <td>订单覆盖品牌</td>
                    <td colspan="3" class="text-left" id="orderCoverBrand"></td>
                </tr>
                <tr>
                    <td>订单覆盖区域</td>
                    <td colspan="3" class="text-left" id="orderCoverArea"></td>
                </tr>
                <tr>
                    <td>简介</td>
                    <td colspan="3" class="text-left">${traderCustomer.brief}</td>
                </tr>
                <tr>
                    <td>历史背景</td>
                    <td colspan="3" class="text-left">${traderCustomer.history}</td>
                </tr>
                <tr>
                    <td>现在生意情况</td>
                    <td colspan="3" class="text-left">${traderCustomer.businessCondition}</td>
                </tr>
                <tr>
                    <td>公司发展计划</td>
                    <td colspan="3" class="text-left">${traderCustomer.businessPlan}</td>
                </tr>
                <tr>
                    <td>公司优势</td>
                    <td colspan="3" class="text-left">${traderCustomer.advantage}</td>
                </tr>
                <tr>
                    <td>面临主要问题</td>
                    <td colspan="3" class="text-left">${traderCustomer.primalProblem}</td>
                </tr>
                
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">

	//订单覆盖品类，订单覆盖品牌，订单覆盖区域
	$(function(){
		
		var traderId = '${traderCustomer.traderId}';
		
		$.ajax({
			async:true,
			url:page_url + "/trader/customer/ordercoverinfo.do",
			data:{"traderId":traderId , "traderType":1},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data){
					var orderType = "";
					var orderBrand = "";
					var orderArea = "";
					//订单覆盖区域
					if(data.areaList != null && data.areaList.length > 0){
						for (var i=0; i<data.areaList.length; i++) {
							if(data.areaList[i] != null){
								if(i == data.areaList.length-1){
									orderArea += data.areaList[i];
								}else{
									orderArea += data.areaList[i] + "、";
								}
							}
						}
					}
					
					//订单覆盖品牌
					if(data.brandNameList != null && data.brandNameList.length > 0){
						for (var i=0; i<data.brandNameList.length; i++) {
							if(data.brandNameList[i] != null){
								if(i == data.brandNameList.length-1){
									orderBrand += data.brandNameList[i];
								}else{
									orderBrand += data.brandNameList[i] + "、";
								}
							}
						}
					}
					
					//订单覆盖品类
					if(data.categoryNameList != null && data.categoryNameList.length > 0){
						for (var i=0; i<data.categoryNameList.length; i++) {
							if(data.categoryNameList[i] != null){
								if(i == data.categoryNameList.length-1){
									orderType += data.categoryNameList[i];
								}else{
									orderType += data.categoryNameList[i] + "、";
								}
							}
						}
					}
					$("#orderCoverCategory").html(orderType);
					$("#orderCoverBrand").html(orderBrand);
					$("#orderCoverArea").html(orderArea);
					$("#orderGoodsOffice").html(data.officeStr);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限");
				}
			}
		})
	});
	
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/view_manageinfo.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
