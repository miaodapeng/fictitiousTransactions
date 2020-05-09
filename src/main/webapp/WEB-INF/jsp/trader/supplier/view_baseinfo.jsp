<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="基本信息" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="supplier_tag.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/supplier/view_baseinfo.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var	url = page_url + '/trader/supplier/baseinfo.do?traderId='+$("#traderId").val()+'&traderSupplierId='+$("#traderSupplierId").val();
		if($(window.frameElement).attr('src').indexOf("baseinfo")<0){
			$(window.frameElement).attr('data-url',url);
		}
	});
</script>
<div class="content">
	<div class="parts">
		<div class="title-container">
			<div class="table-title">基本信息</div>
			<c:if test="${traderSupplier.isEnable == 1 &&  traderSupplier.verifyStatus != 0 }">
                <div class="title-click addtitle"
                     tabTitle='{"num":"supplier_edit","link":"./trader/supplier/editbaseinfo.do?traderSupplierId=${traderSupplier.traderSupplierId}","title":"编辑信息"}'>编辑</div>
            </c:if>
		</div>
		<table class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smallest">供应商名称</td>
					<td class="table-middle">${traderSupplier.trader.traderName }</td>
					<td class="table-smallest">供应商id</td>
					<td class="table-middle">${traderSupplier.trader.traderId }</td>
					<td class="table-smallest">地区</td>
					<td class="table-middle">${region }</td>
				</tr>
				<input id="traderId" name="traderId" type="hidden" value="${traderSupplier.trader.traderId }"/>
				<input id="traderSupplierId" name="traderSupplierId" type="hidden" value="${traderSupplier.traderSupplierId}"/>
				<tr>
					<td>供应商品</td>
					<td colspan="5" class="text-left">${traderSupplier.supplyProduct }</td>
				</tr>
				<tr>
					<td>热线电话</td>
					<td colspan="5" class="text-left">${traderSupplier.hotTelephone }</td>
				</tr>
				<tr>
					<td>售后电话</td>
					<td colspan="5" class="text-left">${traderSupplier.serviceTelephone }</td>
				</tr>
				<tr>
					<td>承运商名称</td>
					<td colspan="5" class="text-left">${traderSupplier.logisticsName }</td>
				</tr>
				<tr>
					<td>企业宣传片</td>
					<td colspan="5" class="text-left">
						<c:forEach items="${traderSupplier.companyUriList}" var="cmu">
							<a href="${cmu.uri}" target="_blank">${cmu.uri}</a>&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>官方网址</td>
					<td colspan="5" class="text-left">
						<a href="${traderSupplier.website}" target="_blank">${traderSupplier.website}</a>
					</td>
				</tr>
				<tr>
					<td>供应品牌</td>
					<td colspan="5" class="text-left">
						<c:if test="${not empty traderSupplier.traderSupplierSupplyBrands }">
							<c:forEach items="${traderSupplier.traderSupplierSupplyBrands }" var="brand">
								${brand.brand.brandName }、
							</c:forEach>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="5" class="text-left">
						<c:if test="${traderSupplier.comments != null}">
							${traderSupplier.comments}
						</c:if>
					</td>
				</tr>
				<tr>
					<td>简介</td>
					<td colspan="5" class="text-left">
						<c:if test="${traderSupplier.brief != null}">
							${traderSupplier.brief}
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title">资质信息</div>
			<c:if test="${traderSupplier.isEnable == 1 &&  traderSupplier.verifyStatus != 0 }">
				<!-- <div class="title-click  pop-new-data" layerParams='{"width":"1200px","height":"500px","title":"编辑","link":"./editAptitude.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"}'>编辑</div> -->
				<div class="title-click addtitle"
					 tabTitle='{"num":"supplier_edit","link":"./trader/supplier/editbaseinfo.do?traderSupplierId=${traderSupplier.traderSupplierId}","title":"编辑信息"}'>编辑</div>
			</c:if>
		</div>
		<% Date date=new Date();long now = date.getTime(); request.setAttribute("now", now); %>
		<table class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
			<tr>
				<td class="table-smallest">营业执照</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${business ne null && business.uri ne null}">
							<a href="http://${business.domain}${business.uri}" target="_blank">营业执照</a>
						</c:when>
						<c:otherwise>
							营业执照
						</c:otherwise>
					</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
					有效期： <date:date value ="${business.begintime}" format="yyyy-MM-dd"/>
					<c:if test="${business ne null && business.endtime eq null}">-无限期</c:if>
					<c:if test="${business.endtime ne null}">-<date:date value ="${business.endtime}" format="yyyy-MM-dd"/></c:if>&nbsp;&nbsp;&nbsp;&nbsp;
					<c:if test="${not empty business.endtime && business.endtime ne 0 && business.endtime lt now }"><span style="color: red">（已过期）</span></c:if>
					<c:if test="${business.isMedical eq 1}">含有医疗器械</c:if>
				</td>
			</tr>
			<tr>
				<td class="table-smallest">税务登记证</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${tax ne null && tax.uri ne null}">
							<a href="http://${tax.domain}${tax.uri}" target="_blank">税务登记证</a>
						</c:when>
						<c:otherwise>
							税务登记证
						</c:otherwise>
					</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
					有效期：  <date:date value ="${tax.begintime}" format="yyyy-MM-dd"/>
					<c:if test="${tax ne null && tax.endtime eq null}">-无限期</c:if>
					<c:if test="${tax.endtime ne null}">-<date:date value ="${tax.endtime}" format="yyyy-MM-dd"/></c:if>
					<c:if test="${tax.endtime ne null && tax.endtime ne 0 && tax.endtime lt now }"><span style="color: red">（已过期）</span></c:if>
				</td>
			</tr>
			<tr>
				<td class="table-smallest">组织机构代码证</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${orga ne null && orga.uri ne null}">
							<a href="http://${orga.domain}${orga.uri}" target="_blank">组织机构代码证</a>
						</c:when>
						<c:otherwise>
							组织机构代码证
						</c:otherwise>
					</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
					有效期：<date:date value ="${orga.begintime}" format="yyyy-MM-dd"/>
					<c:if test="${orga ne null && orga.endtime eq null}">-无限期</c:if>
					<c:if test="${orga.endtime ne null}">-<date:date value ="${orga.endtime}" format="yyyy-MM-dd"/></c:if>
					<c:if test="${orga.endtime ne null && orga.endtime ne 0 && orga.endtime lt now }"><span style="color: red">（已过期）</span></c:if>

				</td>
			</tr>
			<tr>
				<td class="table-smallest">医疗器械二类备案凭证</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${twoMedicalList ne null }">
							<c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">
								<c:if test="${st.index == 0}">
									<c:set var="beginTime" value="${twoMedical.begintime}"></c:set>
									<c:set var="endTime" value="${twoMedical.endtime}"></c:set>
									<c:set var="sn" value="${twoMedical.sn}"></c:set>
								</c:if>
								<c:if test="${twoMedical.uri ne null && not empty twoMedical.uri}">
									<a href="http://${twoMedical.domain}${twoMedical.uri}" target="_blank">医疗器械二类备案凭证 - ${st.index + 1}</a>&nbsp;&nbsp;
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							医疗器械二类备案凭证&nbsp;
						</c:otherwise>
					</c:choose>&nbsp;&nbsp;&nbsp;
					有效期：<date:date value ="${beginTime} " format="yyyy-MM-dd"/>
					<c:if test="${twoMedicalList ne null and endTime eq null && not empty twoMedicalList}">-无限期</c:if>
					<c:if test="${endTime ne null}">
						-<date:date value ="${endTime} " format="yyyy-MM-dd"/>
					</c:if>&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn}
					<c:if test="${endTime ne null && endTime ne 0 && endTime lt now }"><span style="color: red">（已过期）</span></c:if>
				</td>
			</tr>
			<tr>
				<td class="table-smallest">医疗器械二类备案凭证详情</td>
				<td style="text-align: left;">
					<c:if test="${not empty two }">
						<c:forEach items="${two }" var="mc">
							${mc.title}&nbsp;&nbsp;
						</c:forEach>
					</c:if>
				</td>

			</tr>
			<tr>
				<td class="table-smallest">三类医疗资质</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${threeMedical ne null && threeMedical.uri ne null}">
							<a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank">三类医疗资质</a>
						</c:when>
						<c:otherwise>
							三类医疗资质
						</c:otherwise>
					</c:choose>&nbsp;&nbsp;&nbsp;&nbsp;
					有效期：<date:date value ="${threeMedical.begintime} " format="yyyy-MM-dd"/>
					<c:if test="${threeMedical ne null && threeMedical.endtime eq null}">-无限期</c:if>
					<c:if test="${threeMedical.endtime ne null}">
						-<date:date value ="${threeMedical.endtime} " format="yyyy-MM-dd"/>
					</c:if>&nbsp;&nbsp;&nbsp;&nbsp;许可证编号：${threeMedical.sn}
					<c:if test="${threeMedical.endtime ne null && threeMedical.endtime ne 0 && threeMedical.endtime lt now }"><span style="color: red">（已过期）</span></c:if>
				</td>
			</tr>
			<tr>
				<td class="table-smallest">三类医疗资质详情</td>
				<td style="text-align: left;">
					<c:if test="${not empty three }">
						<c:forEach items="${three }" var="mc">
							${mc.title}&nbsp;&nbsp;
						</c:forEach>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="table-smallest">医疗器械生产许可证</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${product ne null && product.uri ne null}">
							<a href="http://${product.domain}${product.uri}" target="_blank">医疗器械生产许可证</a>
						</c:when>
						<c:otherwise>
							医疗器械生产许可证
						</c:otherwise>
					</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
					有效期：<date:date value ="${product.begintime}" format="yyyy-MM-dd"/>
					<c:if test="${product ne null && product.endtime eq null}">-无限期</c:if>
					<c:if test="${product.endtime ne null}">-<date:date value ="${product.endtime}" format="yyyy-MM-dd"/></c:if>
					<c:if test="${product.endtime ne null && product.endtime ne 0 && product.endtime lt now }"><span style="color: red">（已过期）</span></c:if>

				</td>
			<tr>
				<td class="table-smallest">医疗器械经营许可证</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${operate ne null && operate.uri ne null}">
							<a href="http://${operate.domain}${operate.uri}" target="_blank">医疗器械经营许可证</a>
						</c:when>
						<c:otherwise>
							医疗器械经营许可证
						</c:otherwise>
					</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
					有效期：<date:date value ="${operate.begintime}" format="yyyy-MM-dd"/>
					<c:if test="${operate ne null && operate.endtime eq null}">-无限期</c:if>
					<c:if test="${operate.endtime ne null}">-<date:date value ="${operate.endtime}" format="yyyy-MM-dd"/></c:if>
					<c:if test="${operate.endtime ne null && operate.endtime ne 0 && operate.endtime lt now }"><span style="color: red">（已过期）</span></c:if>

				</td>
			</tr>
			<!-- begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 -->
			<tr>
				<td class="table-smallest">销售人员授权书</td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${saleAuth ne null && saleAuth.uri ne null}">
							<a href="http://${saleAuth.domain}${saleAuth.uri}" target="_blank">销售人员授权书</a>
						</c:when>
						<c:otherwise>
							销售人员授权书
						</c:otherwise>
					</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;有效期： <date:date value ="${saleAuth.begintime}" format="yyyy-MM-dd"/>-<date:date value ="${saleAuth.endtime}" format="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="table-smallest">授权销售人信息</td>
				<td style="text-align: left;">
					职位:&nbsp;<span>${saleAuth.authPost}</span>&nbsp;&nbsp;, 姓名:&nbsp;<span>${saleAuth.authUserName}</span>&nbsp;&nbsp;, 联系方式:&nbsp;<span>${saleAuth.authContactInfo}</span>
				</td>
			</tr>
			<!-- end by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 -->

			<tr>
				<td class="table-smallest">品牌授权书 </td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${brandBookList ne null }">
							<c:forEach items="${brandBookList }" var="brandBook" varStatus="st">
								<c:if test="${st.index == 0}">
									<c:set var="brandBeginTime" value="${brandBook.begintime}"></c:set>
									<c:set var="brandEndTime" value="${brandBook.endtime}"></c:set>
								</c:if>
								<c:if test="${brandBook.uri ne null && not empty brandBook.uri}">
									<a href="http://${brandBook.domain}${brandBook.uri}" target="_blank">品牌授权书 - ${st.index + 1}</a>&nbsp;&nbsp;
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							品牌授权书&nbsp;
						</c:otherwise>
					</c:choose>&nbsp;&nbsp;&nbsp;
					有效期：<date:date value ="${brandBeginTime} " format="yyyy-MM-dd"/>
					<c:if test="${brandBookList ne null and brandEndTime eq null and not empty brandBookList}">-无限期</c:if>
					<c:if test="${brandEndTime ne null}">
						-<date:date value ="${brandEndTime} " format="yyyy-MM-dd"/>
					</c:if>&nbsp;
					<c:if test="${brandEndTime ne null && brandEndTime ne 0 && brandEndTime lt now }"><span style="color: red">（已过期）</span></c:if>
				</td>
			</tr>

			<tr>
				<td class="table-smallest">其他 </td>
				<td style="text-align: left;">
					<c:choose>
						<c:when test="${otherList ne null }">
							<c:forEach items="${otherList }" var="other" varStatus="st">
								<c:if test="${other.uri ne null && not empty other.uri}">
									<a href="http://${other.domain}${other.uri}" target="_blank">其他资格证书 - ${st.index + 1}</a>&nbsp;&nbsp;
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							其他资格证书&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
			</tr>

			</tbody>
		</table>
	</div>
	<iframe class="j-iframe"  src="/trader/supplier/getContactsAddress.do?traderId=${traderSupplier.trader.traderId }&traderSupplierId=${traderSupplier.traderSupplierId}" frameborder="0" style="width: 100%;border: 0; " onload="setIframeHeight(this)" scrolling="no">
	</iframe>
    <div class="tcenter mb15 mt-5">
        <input type="hidden" name="formToken" value="${formToken}"/>
        <c:choose>
            <c:when test="${traderSupplier.verifyStatus == null || traderSupplier.verifyStatus != 1}">
                <c:if test="${belongSupplyOrg and ((null==taskInfo and null==taskInfo.getProcessInstanceId())or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id]))}">
                    <button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="applyValidSupplier(${traderSupplier.traderSupplierId},${taskInfo.id == null ?0: taskInfo.id})">申请审核</button>
                </c:if>
                <c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
                    <c:choose>
                        <c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
                            <button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
                            <button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:when>
        </c:choose>
    </div>

     <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <td>操作人</td>
                        <td>操作时间</td>
                        <td>操作事项</td>
                        <td>备注</td>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${null!=historicActivityInstance}">
                    <c:forEach var="hi" items="${historicActivityInstance}" varStatus="status">
                    <c:if test="${not empty  hi.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hi.activityType == 'startEvent'}">
							${startUser}
							</c:when>
							<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstance.size() == status.count}">
									${verifyUsers}
								</c:if>
								<c:if test="${historicActivityInstance.size() != status.count}">
									${hi.assignee}
								</c:if>
							</c:otherwise>
						</c:choose>


                    	</td>
                        <td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hi.activityType == 'startEvent'}">
							开始
							</c:when>
									<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
							结束
							</c:when>
							<c:otherwise>
							${hi.activityName}
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMap[hi.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                    <c:if test="${empty historicActivityInstance}">
		       			<!-- 查询无结果弹出 -->
		       			<tr>
		       				<td colspan="4">暂无审核记录。</td>
		       			</tr>
		        	</c:if>
                </tbody>
            </table>

        	<div class="clear"></div>
        </div>

	
</div>
<%@ include file="../../common/footer.jsp"%>
