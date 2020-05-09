<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售订单打印" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
	<link type="text/css" rel="stylesheet"
	href='<%=basePath%>static/css/admin/order_print/branch_company_print.css?rnd=<%=Math.random()%>'
	id="skinlayercss">
    <body>
    	<div id="printArea" class="content">
    		<h2 class="list-title">广东贝海医疗供应链管理有限公司<br/> 销售订单</h2>
            <div class="branch-company-table">
                <!--公司信息，操作人信息，日期开始 -->
                <table class="mb15 table-td-center">
                    <tbody>
                        <tr>
                            <td style="width: 190px;"><b>门诊名称：</b></td>
                            <td style="width: 237px;">${saleorderVo.traderName}</td>
                            <td style="width: 100px;"><b>业务员：</b></td>
                            <td style="width: 121px;">${username}</td>
                            <td style="width: 90px;"><b>签单日期：</b></td>
                            <td style="width: 110px;"><date:date value ="${saleorderVo.satisfyDeliveryTime }" format="yyyy-MM-dd"/></td>
                        </tr>
                         <tr>
                            <td><b>出货单是否填价格：</b></td>
                            <td></td>
                            <td><b>联系人：</b></td>
                            <td>${saleorderVo.traderContactName }</td>
                            <td><b>电话：</b></td>
                            <td>${saleorderVo.traderContactMobile }</td>
                        </tr>                         
                        <tr>
                            <td><b>付款方式：</b></td>
                            <td>
                            <c:forEach var="list" items="${paymentTermList}">
		                    	<c:if test="${saleorderVo.paymentType == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
                            </td>
                            <td><b>发货日期：</b></td>
                            <td></td>
                            <td><b>发货方式：</b></td>
                            <td><c:forEach var="list" items="${deliveryTypes}">
		                    	<c:if test="${saleorderVo.deliveryType == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
		                    <c:forEach var="list" items="${freightDescriptions}">
		                    	<c:if test="${saleorderVo.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
		                    </td>
                        </tr>   
                        <tr>
                            <td><b>经销商名称及联系方式：</b></td>
                            <td colspan="5">
                            ${saleorderVo.traderName} ${saleorderVo.traderContactName} ${saleorderVo.traderContactMobile}
                            </td>
                        </tr> 
                         <tr>
                            <td><b>详细地址联系人电话：</b></td>
                            <td colspan="5">${saleorderVo.takeTraderAddress} ${saleorderVo.takeTraderContactName} ${saleorderVo.takeTraderContactMobile}</td>
                        </tr>   
                    </tbody>
                </table>
                <!--公司信息，操作人信息，日期 结束-->
                <!-- 产品规格展示区 -->
                <table class="table-td-padding">
                        <thead>          
                            <tr>
                                <th  style="width:40px;">序号</th>
                                <th  style="width: 100px;">名称</th>
                                <th  style="width: 207px;">规格</th>
                                <th  style="width: 40px;">数量</th>
                                <th  style="width: 60px;">单位</th>
                                <th  style="width: 80px;">单价</th>
                                <th  style="width: 120px;">金额</th>
                                <th  >备注</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- 产品开始 -->
                            <c:set var="count" value="1"></c:set>
                            <c:forEach var="list" items="${saleorderGoodsList}" varStatus="num">
											<c:if test="${list.isDelete eq 0}">
                            <tr>
                                <td class="wid80  text-center"> ${count}</td>
								<c:set var="count" value="${count+1}"></c:set>
                                <td class="text-center">${ list.goodsName}</td>
                                <td class="text-center">${list.model }</td>
                                <td class="text-center">${list.num }</td>
                                <td class="text-center">${list.unitName }</td>
                                <td class="text-center">${list.price }</td>
                                <td class="text-center">${list.num* list.price}</td>
                                <td></td>
                            </tr>
                            </c:if>
											</c:forEach>
                            <!-- 产品结束 -->
                            <tr>
                                <td colspan="2" class="text-center"><b>总金额</b></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td  class="text-center"><b>${saleorderVo.totalAmount }</b></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td colspan="8"><b>其他要求及说明：</b><span class="ml10"></span></td>
                            </tr>
                            <tr>
                                <td colspan="8"><b>开票资料：</b><span class="ml10"></span></td>
                            </tr>
                             <tr>
                                <td colspan="4">出货确认（请确认各零配件是否备齐）   </td>
                                <td colspan="4"></td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <b>安装问题？</b>
                                </td>
                                <td class="text-center">运费
                                </td>
                                <td colspan="2">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <b>折扣：</b>
                                </td>
                                <td class="text-center" colspan="2">完成情况
                                </td>
                                <td colspan="3">
                                </td>
                            </tr>
                            <tr>
                                <td>成本</td>
                                <td colspan="2"></td>
                                <td colspan="2" class="text-center">利润</td>
                                <td colspan="3"></td>
                            </tr>
                             <tr>
                                <td colspan="2"><b>收款金额 </b><span></span></td>
                                <td><b>收款日期</b></td>
                                <td colspan="2" class="text-center"></td>
                                <td colspan="" class="text-center">
                                    <b>录单人</b>
                                </td>
                                 <td colspan="2">
                                   ${detail.realName }
                                </td>
                            </tr>
                       </tbody>
                </table>  
            </div>
            <div class='tcenter mb15' style="margin-top: 20px">
		<span class=" bt-small bt-bg-style bg-light-blue"  id="btnPrint">打印</span>
    	</div>   
    	</div>
    	  <script>
              $("#btnPrint").click(function() {
                  $("#btnPrint").hide();
                  if (window.ActiveXObject || "ActiveXObject" in window){
                      $("#print-contract").printArea({
                          mode:'popup'
                      });
                  }else{
                      $("#print-contract").printArea();
                  }
                  $("#btnPrint").show();
              });
</script>
    </body>
  <script type="text/javascript"
	src='<%=basePath%>static/js/order/saleorder/order_print.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>