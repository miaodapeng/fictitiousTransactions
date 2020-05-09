<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加产品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/saleorder/bh_add_saleorder_goods.js?rnd=<%=Math.random()%>'></script>
	<div class="formpublic formpublic1">
		<div>
			<!-- ------------产品数据列表--------start------- -->
			<div class="controlled" id="goodsListDiv">
				<!-- 搜索表格出来 -->
				<ul class="searchTable">
					<li>
						<form method="post" id="search" action="<%=basePath%>order/saleorder/addBhSaleorderGoods.do?saleorderId=${saleorderId}">
							<div class="infor_name ">
								<span>*</span> <label>产品名称</label>
							</div>
							<div class="f_left table-larger">
								<div class="mb10">
									<input type="text" class="input-larger mr5" placeholder="请输入产品名称/订货号/品牌/型号等关键词" id="searchContent" name="searchContent" value="${searchContent}">
									<span class="bt-bg-style bt-small bg-light-blue" onclick="search();" id="errorMes">搜索</span>
								</div>
							</div>
						</form>
						<div>
							<table
								class="table table-bordered table-striped table-condensed table-centered mb10">
								<thead>
									<th class="table-smallest8">订货号</th>
									<th>产品名称</th>
									<th class="table-smallest">品牌</th>
									<th class="table-smallest8">型号</th>
									<th class="table-smallest8">单位</th>
									<th class="table-smallest12">产品级别</th>
									<th class="table-smallest5">库存</th>
									<th class="table-smallest8">审核状态</th>
									<th class="table-smallest6">选择</th>
								</thead>
								<tbody>
									<c:forEach var="list" items="${goodsList}" varStatus="status">
										<tr>
											<td>${list.sku}</td>
											<td><a class="addtitle1" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'><c:if test="${list.source == 1}"><span style="color: red">【医械购】</span></c:if>${list.goodsName}</a></td>
											<td>${list.brandName}</td>
											<td>${list.model}</td>
											<td>${list.unitName}</td>
											<td>${list.goodsLevelName}</td>
											<td>${list.stockNum}</td>
											<td>
												<c:if test="${list.verifyStatus eq 0}">待完善</c:if>
												<c:if test="${list.verifyStatus eq 1}">审核中</c:if>
												<c:if test="${list.verifyStatus eq 2}">审核不通过</c:if>
												<c:if test="${list.verifyStatus eq 3}">审核通过</c:if>
											</td>
											<td>
												<!-- oldVerifyStatus 代表老商品流审核通过-->
												<c:if test="${list.verifyStatus eq 3  }">
													<a href="javascript:void(0);"
														onclick="selectGoods('${list.goodsId}','${list.sku}','<c:out value='${list.goodsName}' escapeXml="true"></c:out>','${list.brandName}','${list.model}','${list.unitName}','${list.goodsLevelName}','${list.proUserName}','${list.verifyStatus}');">选择</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
									<c:if test="${empty goodsList}">
										<tr><td colspan="9">查询无结果！请尝试使用其他搜索条件。</td></tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</li>
					<tags:page page="${page}" optpage="n" />
					<div class="clear"></div>
				</ul>
			</div>
			<!-- ------------产品数据列表--------end------- -->
				
				
			<!-- ------------选择列表产品后操作--------start------- -->
			<div class="controlled none" id="confirmGoodsDiv">
				<!-- 搜索最后结果lastResult -->
				<form id="confirmForm">
					<input type="hidden" name="goodsId" id="goodsId"/>
					<input type="hidden" name="sku" id="sku"/>
					<input type="hidden" name="goodsName" id="goodsName"/>
					<input type="hidden" name="brandName" id="brandName"/>
					<input type="hidden" name="model" id="model"/>
					<input type="hidden" name="unitName" id="unitName"/>
					<ul class="lastResult">
						<li>
							<div class="infor_name ">
								<span>*</span> <label>产品名称</label>
							</div>
							<div class="f_left table-largest content1">
								<div class="">
									<a href="javascript:void(0);" class="font-blue mr10 productname2 addtitle2" id="confirmGoodsName" tabTitle=''></a>
									<span class="bt-bg-style bt-small bg-light-blue searchAgain" onclick="againSearch();">重新搜索</span>
								</div>
							</div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>品牌/型号</label>
							</div>
							<div class="f_left" id="confirmGoodsBrandNameModel"></div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>产品信息</label>
							</div>
							<div class="f_left" id="confirmGoodsContent"></div>
						</li>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>备货价</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-middle mr5" name="price" id="price" onkeyup="confirmTotalMoney('price');"> 
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>数量</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-middle" name="num" id="num" onkeyup="confirmTotalMoney('num');">
							</div>
						</li>
						<li>
							<div class="infor_name mt0">
								<span>*</span> <label>单位</label>
							</div>
							<div class="f_left" id="confirmUnitName"></div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>总额</label>
							</div>
							<div class="f_left" id="confirmTotalMoney"></div>
						</li>
						<li>
							<div class="infor_name ">
								<label>供应商</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-largest mr5" placeholder="" name="supplierName" id="supplierName">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>内部备注</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-largest mr5" placeholder="内部备注，适用于向采购部转达特殊要求" name="insideComments" id="insideComments">
							</div>
						</li>
					</ul>
					<input type="hidden" name="saleorderId" id="saleorderId" value="${saleorderId}"/>
					<div class="add-tijiao  tcenter">
						<button class="bt-bg-style bg-deep-green" type="button" onclick="confirmSubmit();">提交</button>
						<button id="close-layer" type="button" class="dele">取消</button>
					</div>
				</form>
			</div>
			<!-- ------------选择列表产品后操作--------end------- -->
			
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
