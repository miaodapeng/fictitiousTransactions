<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品销售信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/view_saleinfo.js?rnd=<%=Math.random()%>'></script>
	<div class="customer">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}">商品信息</a>
            </li>
            <li>
                <a class="customer-color" href="${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}">销售信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewpurchaseinfo.do?goodsId=${goods.goodsId}">采购信息</a>
            </li>
           <!--  <li>
                <a href="javascript:void(0)">运营信息</a>
            </li> -->
        </ul>
    </div>
    <div class=" content">
        <div class="parts">
	        <div class="title-container">
	                <div class="table-title nobor">
	                    管理信息
	                </div>
            </div>

            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">商品级别</td>
                        <td>
                        <c:forEach var="li" items="${goodsLevels}">
                        	<c:if test="${li.sysOptionDefinitionId ==  goods.goodsLevel}">${li.title}</c:if>
	                    </c:forEach>
                        </td>
                        <td class="table-smaller">审核状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${goods.verifyStatus eq 0}">
									审核中
								</c:when>
								<c:when test="${goods.verifyStatus eq 1}">
									审核通过
								</c:when>
								<c:when test="${goods.verifyStatus eq 2}">
									审核未通过
								</c:when>
								<c:otherwise>
									待审核
								</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>税收分类编码</td>
                        <td>${goods.taxCategoryNo}</td>
                        <td class="table-smaller">创建时间</td>
                        <td><date:date value ="${goods.addTime}"/></td> 
                    </tr>
                    <tr>
                     <td>禁用状态</td>
                        <td>
                        <c:choose>
							<c:when test="${goods.isDiscard eq 1}">已禁用</c:when>
							<c:otherwise>未禁用</c:otherwise>
						</c:choose>
                        </td>
                        <td>禁用时间</td>
                        <td><date:date value ="${goods.discardTime}"/></td>
                    </tr>
                    <tr>
                        <td>禁用原因</td>
                        <td>${goods.discardReason}</td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
            
            
        </div>
        
        <div class="parts content1" id="authorization">
        <div class="title-container">
            <div class="table-title nobor">
                商品授权与定价
            </div>
            <%-- <div class="title-click nobor" onclick="delGoodsChannelPrice(${goods.goodsId});">
                重置
            </div> --%>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th class="wid5">序号</th>
                    <th>授权地区</th>
                    <th>授权客户的类型</th>
                    <c:if test="${goodsChannelPriceList[0].minNum > 0 }">
                    <th >最小起订量</th>
                    </c:if>
                    <c:if test="${goodsChannelPriceList[0].minNum == 0 }">
                    <th >最小起订价</th>
                    </c:if>
                    <th>批量政策</th>
                    <th>VIP价</th>
                    <th>经销商指导价</th>
                    <th>非公机构终端价</th>
                    <th>公立终端价</th>
                    <th>市场指导价</th>
                    <th>是否需要终端报备</th>
                    <th>是否可出厂家授权书</th>
                    <th class="wid8">批量价</th>
                    <th>核价有效期</th>
                    <th class="wid8">更新时间</th>
                    <th>操作人</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="list" items="${goodsChannelPriceList}" varStatus="statu">
	                <tr>
	                    <td>${statu.count}</td>
	                    <td>${list.provinceName}&nbsp;${list.cityName}</td>
	                    <td>${list.customerTypeComments}</td>
	                     <c:if test="${goodsChannelPriceList[0].minNum > 0 }">
	                     <td>${list.minNum}${goods.unitName}</td>
	                     </c:if>
	                      <c:if test="${goodsChannelPriceList[0].minNum == 0 }">
	                      <td> <fmt:formatNumber type="number" value="${list.minAmount}" pattern="0.00" maxFractionDigits="2" />元</td>
	                     </c:if>
	                    <td>${list.batchPolicy}</td>
	                    <td><fmt:formatNumber type="number" value="${list.vipPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td><fmt:formatNumber type="number" value="${list.distributionPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td><fmt:formatNumber type="number" value="${list.privatePrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td><fmt:formatNumber type="number" value="${list.publicPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td><fmt:formatNumber type="number" value="${list.marketPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td>
	                    	<c:choose>
								<c:when test="${list.isReportTerminal eq 1}">
									是
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td>
	                    	<c:choose>
								<c:when test="${list.isManufacturerAuthorization eq 1}">
									是
								</c:when>
								<c:when test="${list.isManufacturerAuthorization eq 2}">
									是，需保证金
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td  class="begin-enter-lib caozuo">
                           <span class="bt-smaller  border-blue" onclick="showPriceList(${list.goodsChannelPriceId},2,'${goods.unitName}')">点击查看</span>
                        </td>
	                    <td><fmt:formatDate value="${list.periodDate}" type="date" pattern="yyyy-MM-dd"/></td>
	                    <td><date:date value ="${list.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
	                    <td>${list.username}</td>
	                    <td>
	                    	<c:if test="${not empty goods.userList }">
								<c:forEach items="${goods.userList }" var="user" varStatus="st">
									<c:if test="${user.username == curr_user.username}">
										<span class="delete"  onclick="delGoodsChannelPriceAll(${list.goodsChannelPriceId});">删除</span>
									</c:if>
								</c:forEach>
							</c:if>
	                    </td>
	                    
	                </tr>
            	</c:forEach>
           		<c:if test="${empty goodsChannelPriceList}">
	            	<tr>
	                	<td colspan="15">暂无商品授权与定价信息!</td>
	                </tr>
       			</c:if>
            </tbody>
        </table>
    </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    贝登精选定价
                </div>
            </div>

            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                <tr>
                    <td class="table-smaller">贝登精选市场价</td>
                    <td>
                        ${goods.jxMarketPrice}
                    </td>
                    <td class="table-smaller">贝登精选销售价</td>
                    <td>
                        ${goods.jxSalePrice}
                    </td>
                </tr>
                </tbody>
            </table>


        </div>
    <div class="parts">
	        <div class="title-container">
                <div class="table-title nobor">
                                                       商品宣传工具
                </div>
	            <div class="title-click nobor" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/editCommodityPropagandaTools.do?goodsId=${goods.goodsId}');">编辑</div>
            </div>
            
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">宣传彩页</td>
                        <td>
                          <c:forEach items="${goodsExtend.getBrochureList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>&nbsp;&nbsp;
	                      </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smaller">销售授权书</td>
                        <td>
                          <c:forEach items="${goodsExtend.getSalesEmpowermentList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>&nbsp;&nbsp;
	                      </c:forEach>
                        </td>
                    </tr>
                    <tr>
                     <td>宣传视频</td>
                        <td>
                           <c:forEach items="${goodsExtend.getPropagandistVideoList()}" var="list" varStatus="status">
	                    	<a href="${list.uri}" target="_blank">${list.name}</a>&nbsp;&nbsp;
	                       </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smaller">客户名单</td>
                        <td>${goodsExtend.customerNames}</td>
                    </tr>
                     <tr>
                        <td>其他资质</td>
                        <td>
                            <c:forEach items="${goodsExtend.getOtherQualificationsList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>&nbsp;&nbsp;
	                       </c:forEach>
                        </td>
                    </tr>
                     <%-- <tr>
                        <td>彩页地址</td>
                        <td>${goods.colorPageUrl }</td>
                    </tr> --%>
                    <tr>
                        <td>商品说明书</td>
                        <td>
                           <c:forEach items="${goodsExtend.getCommodityInstructionsList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>&nbsp;&nbsp;
	                       </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>技术参数</td>
                        <td>
                          <c:forEach items="${goodsExtend.getTechnicalParameterList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>&nbsp;&nbsp;
	                       </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>陪标资料</td>
                        <td>
                           <c:forEach items="${goodsExtend.getStandardDataList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>&nbsp;&nbsp;
	                       </c:forEach>
                        </td>
                    </tr>
                     <tr>
                         <td colspan="2" style="text-align: left;font-weight:bold ;">商品培训工具</td>
                    </tr>
                    <tr>
                        <td class="table-smaller">销售话术</td>
                        <td>${goodsExtend.sellingWords}</td>
                    </tr>
                    <tr>
                        <td>培训资料</td>
                        <td>
                           <c:forEach items="${goodsExtend.getTrainingMaterialsList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>
	                       </c:forEach>
                        </td>
                    </tr>
                     <tr>
                        <td>市场策略</td>
                        <td>${goodsExtend.marketStrategy}</td>
                    </tr>
                     <tr>
                        <td>竞品分析</td>
                        <td>${goodsExtend.competitiveAnalysis}</td>
                    </tr>
                    <tr>
                        <td>促销策略</td>
                        <td>${goodsExtend.promotionPolicy}</td>
                    </tr>
                    <tr>
                        <td>产品亮点</td>
                        <td>${goodsExtend.advantage}</td>
                    </tr>
                    <tr>
                        <td>选型辅助</td>
                        <td>
                        	<c:if test="${not empty goodsExtend.assistantAttachment.uri}">
                        		<a href="http://${goodsExtend.assistantAttachment.domain}${goodsExtend.assistantAttachment.uri}" target="_blank">查看</a>
                        	</c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
            
            
        </div>
    <div class="parts">
	        <div class="title-container">
                <div class="table-title nobor">
                                                      标准售后商品
                </div>
	            <div class="title-click nobor" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/editAftersaleGoods.do?goodsId=${goods.goodsId}');">编辑</div>
            </div>
            
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">售后内容</td>
                        <td>
                          <c:forEach items="${goodsExtend.getSysList()}" var="list" varStatus="status">
	                    	<c:choose>
								<c:when test="${list.attributeId eq 655}">
									包安装&nbsp;&nbsp;
								</c:when>
								<c:when test="${list.attributeId eq 656}">
									包运输&nbsp;&nbsp;
								</c:when>
								<c:when test="${list.attributeId eq 657}">
									包培训&nbsp;&nbsp;
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
	                      </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smaller">质保年限</td>
                        <td>
                            ${goodsExtend.warrantyPeriod}年
                        </td>
                    </tr>
                    <tr>
                     <td>质保规则</td>
                        <td>
                          ${goodsExtend.warrantyPeriodRule}
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smaller">保外维修</td>
                        <td>${goodsExtend.warrantyRepairFee}元</td>
                    </tr>
                     <tr>
                        <td>响应时间</td>
                        <td>${goodsExtend.responseTime}小时</td>
                    </tr>
                     <tr>
                        <td>是否有备用机</td>
                        <td>
                           <c:choose>
								<c:when test="${goodsExtend.haveStandbyMachine eq 0}">
									否
								</c:when>
								<c:when test="${goodsExtend.haveStandbyMachine eq 1}">
									是
								</c:when>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>备用机使用条件</td>
                        <td>${goodsExtend.conditions }</td>
                    </tr>
                    <tr>
                        <td>供应商延保价格</td>
                        <td>${goodsExtend.extendedWarrantyFee }元/年
                        </td>
                    </tr>
                    <tr>
                        <td>核心零部件价格</td>
                         <td>
                        <c:forEach items="${goodsExtend.getSparePartsPriceList()}" var="list" varStatus="status">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a>
	                    </c:forEach>
	                    </td>
                    </tr>
                     <tr>
                         <td colspan="2" style="text-align: left;font-weight:bold ;">非标售后商品</td>
                    </tr>
                    <tr>
                        <td class="table-smaller">退货条件</td>
                        <td>
                           <c:choose>
								<c:when test="${goodsExtend.isRefund eq 0}">
									不允许退货
								</c:when>
								<c:when test="${goodsExtend.isRefund eq 1}">
									允许退货
								</c:when>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>换货条件</td>
                        <td>
                           ${goodsExtend.exchangeConditions }
                        </td>
                    </tr>
                     <tr>
                        <td>换货方式</td>
                        <td>${goodsExtend.exchangeMode}</td>
                    </tr>
                     <tr>
                        <td>运费说明</td>
                        <td>${goodsExtend.freightDescription}</td>
                    </tr>
                </tbody>
            </table>
            
            
        </div>
        
   <div class="parts">
	        <div class="title-container">
                <div class="table-title nobor">
                                                      物流运输
                </div>
	           <%--  <div class="title-click nobor" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/editCommodityPropagandaTools.do?goodsId=${goods.goodsId}');">编辑</div> --%>
            </div>
            
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">现货交货期（工作日）</td>
                        <td>
                        ${goodsExtend.delivery}
                        <%-- <date:date value =" ${goodsExtend.delivery}" format="yyyy-MM-dd HH:mm:ss"/> --%>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smaller">期货交货期（工作日）</td>
                        <td>
                        ${goodsExtend.futuresDelivery}
                         <%-- <date:date value =" ${goodsExtend.futuresDelivery}" format="yyyy-MM-dd HH:mm:ss"/> --%>
                        </td>
                    </tr>
                    <tr>
                     <td>运输要求</td>
                        <td>
                            ${goodsExtend.transportRequirements}
                        </td>
                    </tr>
                    <tr>
                        <td class="table-smaller">运输重量（KG）</td>
                        <td>${goodsExtend.transportWeight}</td>
                    </tr>
                     <tr>
                        <td>是否包含运费</td>
                        <td>
                               <c:choose>
								<c:when test="${goodsExtend.isHvaeFreight eq 0}">
									否
								</c:when>
								<c:when test="${goodsExtend.isHvaeFreight eq 1}">
									是
								</c:when>
							</c:choose>
                        </td>
                    </tr>
                    <c:choose>
						<c:when test="${goodsExtend.isHvaeFreight eq 0}">
							 <tr>
							    <td>运费结算方式</td>
							    <td>
		                        <c:if test="${goodsExtend.freightSettlementMethod eq 669}">
								 货款同步
			                     </c:if>
								<c:if test="${goodsExtend.freightSettlementMethod eq 670}">
								 月结
			                     </c:if>
			                     <c:if test="${goodsExtend.freightSettlementMethod eq 671}">
								 到付
			                     </c:if>
			                     <c:if test="${goodsExtend.freightSettlementMethod eq 672}">
								 乙方自取
						          </c:if>
						          </td>
		                    </tr>
						</c:when>
					</c:choose>
                     <tr>
                        <td>运输完成标准</td>
                        <td>
                          <c:forEach items="${goodsExtend.getSysStandardList()}" var="list" varStatus="status">
	                    	<c:choose>
								<c:when test="${list.attributeId eq 662}">
									上楼&nbsp;&nbsp;
								</c:when>
								<c:when test="${list.attributeId eq 663}">
									不上楼&nbsp;&nbsp;
								</c:when>
								<c:when test="${list.attributeId eq 664}">
									自提&nbsp;&nbsp;
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
	                      </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>特殊运输条件</td>
                        <td>
                            <c:forEach items="${goodsExtend.getSysConditionList()}" var="list" varStatus="status">
	                    	<c:choose>
								<c:when test="${list.attributeId eq 665}">
									易碎品&nbsp;&nbsp;
								</c:when>
								<c:when test="${list.attributeId eq 666}">
									危险品&nbsp;&nbsp;
								</c:when>
								<c:when test="${list.attributeId eq 667}">
									易燃、易爆品&nbsp;&nbsp;
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
	                      </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>验收注意事项</td>
                        <td>${goodsExtend.acceptanceNotice }</td>
                    </tr>
                    <tr>
                        <td>装箱数量</td>
                        <td>${goodsExtend.packingNumber }</td>
                    </tr>
                    <tr>
                        <td class="table-smaller">包装数量</td>
                        <td>${goodsExtend.packingQuantity}</td>
                    </tr>
                </tbody>
            </table>
            
            
        </div>
         <div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
                库存信息
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>在库数量</th>
                    <th>订单占用</th>
                    <th>可调剂</th>
                    <th>在途（订单|数量|预计到达）</th>
                    <th>近期入库日期</th>
                    <th>近期出库日期</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${goods.stockNum}</td>
                    <td>${goods.orderOccupy}</td>
                    <td>${goods.adjustableNum}</td>
                    <td>${goods.onWayNum}</td>
                    <td><date:date value ="${goods.goodsLastInTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><date:date value ="${goods.goodsLastOutTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </tbody>
        </table>
    </div>
    
    <div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
                配件
            </div>
            <div class="title-click nobor  pop-new-data" layerParams='{"width":"70%","height":"80%","title":"配件","link":"../opt/index.do?goodsId=${goods.goodsId}&optType=pj"}'>
                新增
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>商品名称</th>
                    <th>品牌</th>
                    <th>型号</th>
                    <th>订货号</th>
                    <th>物料编码</th>
                    <th>单位</th>
                    <th>配件种类</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="list" items="${listPackage}" varStatus="statu">
	                <tr>
	                    <td>${statu.count}</td>
	                    <td>${list.goodsName}</td>
	                    <td>${list.brandName}</td>
	                    <td>${list.model}</td>
	                    <td>${list.sku}</td>
	                    <td>${list.materialCode}</td>
	                    <td>${list.unitName}</td>
	                    <td>
	                    	<c:choose>
								<c:when test="${list.isStandard eq 1}">
									标配
								</c:when>
								<c:otherwise>
									非标配
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td><span class="delete" onclick="delPackage(${list.keyId});">删除</span></td>
	                </tr>
            	</c:forEach>
            	<c:if test="${empty listPackage}">
	            	<tr>
	                	<td colspan="9">暂无配件信息!</td>
	                </tr>
       			</c:if>
            </tbody>
        </table>
    </div>
    
    <div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
                关联商品
            </div>
            <div class="title-click nobor  pop-new-data" layerParams='{"width":"70%","height":"80%","title":"关联商品","link":"../opt/index.do?goodsId=${goods.goodsId}&optType=gl"}'>
                新增
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>商品名称</th>
                    <th>品牌</th>
                    <th>型号</th>
                    <th>订货号</th>
                    <th>物料编码</th>
                    <th>单位</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var="list" items="${listRecommend}" varStatus="statu">
	                <tr>
	                    <td>${statu.count}</td>
	                    <td>${list.goodsName}</td>
	                    <td>${list.brandName}</td>
	                    <td>${list.model}</td>
	                    <td>${list.sku}</td>
	                    <td>${list.materialCode} </td>
	                    <td>${list.unitName}</td>
	                    <td><span class="delete" onclick="delRecommend(${list.keyId});">删除</span></td>
	                </tr>
            	</c:forEach>
            	<c:if test="${empty listRecommend}">
	            	<tr>
	                	<td colspan="8">暂无关联商品信息!</td>
	                </tr>
       			</c:if>
            </tbody>
        </table>
    </div>
    
    <div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
                商品单元
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>商品名称</th>
                    <th>订货号</th>
                    <th>创建时间</th>
                    <th>库存数量</th>
                    <th>近一年销量</th>
                    <th>是否禁用</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var="list" items="${unitList}" varStatus="staut">
	            	 <tr>
	                    <td>${staut.count}</td>
	                    <td>${list.goodsName}</td>
	                    <td>${list.sku}</td>
	                    <td><date:date value ="${list.addTime}"/></td>
	                    <td>${list.stockNum}</td>
	                    <td>${list.saleNum365}</td>
	                    <td>
							<c:choose>
								<c:when test="${list.isDiscard eq 1}">
									是
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
						</td>
	                </tr>
            	</c:forEach>
            	<c:if test="${empty unitList}">
	            	<tr>
	                	<td colspan="7">暂无商品单元信息!</td>
	                </tr>
       			</c:if>
            </tbody>
        </table>
    </div>
    </div>
<%@ include file="../../common/footer.jsp"%>
