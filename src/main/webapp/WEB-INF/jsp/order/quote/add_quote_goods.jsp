<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加产品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%=basePath%>/static/js/order/quote/add_quote_goods.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
	<div class="formpublic formpublic1">
		<div>
			<ul>
				<li>
					<div class="infor_name" style='margin-top:0px;'>
						<label>产品添加方式</label>
					</div>
					<div class="f_left inputfloat inputfloatmb0">
						<ul>
							<li class="mb0 controller"><input type="radio" name="abc" checked value="select"> <label>产品库搜索</label></li>
							<li class="mb0 controller"><input type="radio" name="abc" value="input"> <label>手动填写</label></li>
							<li class="font-grey9">（手动填写的产品无法被添加到订单）</li>
						</ul>
					</div>
				</li>
			</ul>
			<!-- ------------产品数据列表--------start------- -->
			<div class="controlled" id="goodsListDiv">
				<!-- 搜索表格出来 -->
				<ul class="searchTable">
					<li>
						<form method="post" id="search" action="<%=basePath%>order/quote/addQuoteGoods.do?quoteorderId=${quoteorderId}">
							<div class="infor_name ">
								<span>*</span> <label>产品名称</label>
							</div>
							<div class="f_left table-larger">
								<div class="mb10">
									<input type="text" class="input-larger mr5" placeholder="请输入产品名称/订货号/品牌/型号等关键词" id="searchContent" name="searchContent" value="${searchContent}">
									<input type="hidden" id="salesAreaId" name="salesAreaId" value="">
									<span class="bt-bg-style bt-small bg-light-blue" onclick="search2();" id="errorMes">搜索</span>
								</div>
							</div>
						</form>
						<div>
							<table
								class="table table-bordered table-striped table-condensed table-centered mb10">
								<thead>
									<th class="table-smallest12">订货号</th>
									<th>产品名称</th>
									<th class="table-smallest15">品牌</th>
									<th class="table-smallest12">型号</th>
									<th class="table-smallest5">单位</th>
									<!-- <th>物料编码</th> -->
									<th class="table-smallest">产品级别</th>
									<th class="table-smallest5">库存</th>
									<th class="table-smallest8">审核状态</th>
									<th class="table-smallest6">选择</th>
								</thead>
								<tbody>
									<c:forEach var="list" items="${goodsList}" varStatus="status">
										<tr>
											<td>${list.sku}</td>
											<td><c:if test="${list.source == 1}"><span style="color: red">【医械购】</span></c:if>${list.goodsName}</td>
											<td>${list.brandName}</td>
											<td>${list.model}</td>
											<td>${list.unitName}</td>
											<%-- <td>${list.materialCode}</td> --%>
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
												<c:if test="${list.verifyStatus eq 3}">
												<a href="javascript:void(0);"
													onclick="selectGoods('${list.goodsId}','${list.sku}','<c:out value='${list.goodsName}' escapeXml="true"></c:out>','${list.brandName}','${list.model}','${list.unitName}','${list.goodsLevelName}','${list.proUserName}','${list.verifyStatus}','${list.channelPrice}');">选择</a>
											</c:if>
											</td>
										</tr>
									</c:forEach>
									<c:if test="${empty goodsList}">
									<!-- 查询无结果弹出 -->
									<tr>
										<td colspan='9'>
											查询无结果！请尝试使用其他搜索条件。
										</td>
									</tr>
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
					<input type="hidden" name="formToken" value="${formToken}"/>
					<input type="hidden" name="goodsId" id="goodsId"/>
					<input type="hidden" name="sku" id="sku"/>
					<input type="hidden" name="goodsName" id="goodsName"/>
					<input type="hidden" name="brandName" id="brandName"/>
					<input type="hidden" name="model" id="model"/>
					<input type="hidden" name="unitName" id="unitName"/>
					<ul class="lastResult">
						<!-- 终端客户属性和区域 -->
						<input type='hidden' name='terminalTraderName' id='terminalTraderName' value=''>
						<input type='hidden' name='terminalTraderId' id='terminalTraderId' value=''>
						<input type='hidden' name='terminalTraderType' id='terminalTraderType' value=''>
						<input type='hidden' name='salesArea' id='salesArea' value=''>
						<input type='hidden' name='salesAreaId' id='salesAreaId' value=''>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>产品名称</label>
							</div>
							<div class="f_left table-largest content1">
								<div class="">
									<a class="font-blue mr10 productname2 addtitle2" id="confirmGoodsName"></a>
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
							<div class="infor_name ">
								<label>产品信息</label>
							</div>
							<div class="f_left mr10" id="confirmGoodsContent"></div>
							<span class="font-grey9" >产品未通过审核时，不允许转化到订单中</span>
						</li>
						<li>
							<div class="infor_name ">
								<label>报价</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-middle mr5" name="price" id="price" onkeyup="confirmTotalMoney('price');"> 
						
								<span class="font-grey9" id="priceError">
								<c:if test="${quoteGoods.channelPrice==null}">
									<c:if test="${quoteGoods.avgPrice==null}">
										<span id="goodsChannelPrice">报价平均价（近12个月）：无平均价信息，请向产品部咨询</span>
									</c:if>
									<c:if test="${quoteGoods.avgPrice!=null}">
										<span id="goodsChannelPrice">报价平均价（近12个月）：<fmt:formatNumber type="number" value="${quoteGoods.avgPrice==null?0:quoteGoods.avgPrice}" pattern="0.00" maxFractionDigits="2"/></span>
										
									</c:if>
								</c:if>
								<c:if test="${quoteGoods.channelPrice!=null}"><span id="goodsChannelPrice">核价参考价格：<fmt:formatNumber type="number" value="${quoteGoods.channelPrice==null?0:quoteGoods.channelPrice}" pattern="0.00" maxFractionDigits="2"/></span></c:if>
								</span>
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>数量</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-middle" name="num" id="num" onkeyup="confirmTotalMoney('num');"><!-- 不使用onblur因为提交时有影响 -->
							</div>
						</li>
						<li>
							<div class="infor_name  mt0">
								<span>*</span> <label>单位</label>
							</div>
							<div class="f_left" id="confirmUnitName"></div>
						</li>
						<li>
							<div class="infor_name  mt0">
								<label>总额</label>
							</div>
							<div class="f_left" id="confirmTotalMoney"></div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>报价含安调</label>
							</div>
							<div class="f_left inputfloat inputfloatmb0">
								<ul>
									<li><input type="radio" name="installation" value="1" > <label>是</label></li>
									<li><input type="radio" name="installation" value="0" checked> <label>否</label></li>
								</ul>
								<input type="hidden" name="haveInstallation" id="haveInstallation"/>
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>货期</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-middle mr5" name="deliveryCycle" id="deliveryCycle">
								<!-- <span class="mt4 mr5">天</span> --> <span class="font-grey9 mt4">核价参考货期：3-5天</span>
							</div>
						</li>
						<li>
							<div class="infor_name">
								<label>是否直发</label>
							</div>
							<div class="f_left inputfloat inputfloatmb0">
								<ul>
									<li class="mt4"><input type="radio" name="deliveryDirectRad" value="0" checked> <label>否</label></li>
									<li class="mt4"><input type="radio" name="deliveryDirectRad" value="1"> <label>是</label></li>
									<li><input type="text" placeholder="请填写直发原因，含有直发商品的订单不允许提前采购" class="input-larger" name="deliveryDirectComments" id="deliveryDirectComments"></li>
								</ul>
								<input type="hidden" name="deliveryDirect" id="deliveryDirect">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>内部备注</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-largest mr5" placeholder="内部备注不对外显示" name="insideComments" id="insideComments">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>产品备注</label>
							</div>
							<div class="f_left">
								<div>
									<input type="text" class="input-largest mr5" placeholder="产品备注对外显示" name="goodsComments" id="goodsComments">
								</div>
								<div class="font-grey9 mt5">友情提示<br/>1、如果您的操作导致报价单金额发生变化，可能需要重新编辑付款计划；</div>
							</div>
						</li>
					</ul>
					<input type="hidden" name="quoteorderId" id="quoteorderId" value="${quoteorderId}"/>
					<input type="hidden" name="isTemp" id="isTemp" value="0"/><!-- 是否临时产品 -->
					<div class="add-tijiao  tcenter">
						<button class="bt-bg-style bg-deep-green" type="button" onClick="confirmSubmit();">提交</button>
						<button id="close-layer" type="button" class="dele">取消</button>
					</div>
				</form>
			</div>
			<!-- ------------选择列表产品后操作--------end------- -->
			
			<!-- ------------手动输入产品信息----------start----- -->
			<div class="controlled none" id="inputGoodsDiv">
				<form id="inputForm">
					<input type="hidden" name="formToken" value="${formToken}"/>
					<!-- 终端客户属性和区域 -->
					<input type='hidden' name='terminalTraderName' id='terminalTraderName' value=''>
					<input type='hidden' name='terminalTraderId' id='terminalTraderId' value=''>
					<input type='hidden' name='terminalTraderType' id='terminalTraderType' value=''>
					<input type='hidden' name='salesArea' id='salesArea' value=''>
					<input type='hidden' name='salesAreaId' id='salesAreaId' value=''>
					<ul>
						<li>
							<div class="infor_name">
								<span>*</span> <label>产品名称</label>
							</div>
							<div class="f_left table-largest content1">
								<div>
									<input type="text" class="input-largest" name="goodsName" id="goodsName">
								</div>
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>品牌</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-middle mr5" name="brandName" id="brandName"> 
								<!-- <span class="font-grey9">核价参考价格：12000.00 或 无核价信息，请向产品部咨询</span> -->
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>型号</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-middle" name="model" id="model">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>报价</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-smaller mr5" name="price" id="price" onkeyup="inputTotalMoney('price');">
								<!-- <span class="font-grey9">核价参考价格：12000.00 或 无核价信息，请向产品部咨询</span> -->
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>数量</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-smaller" name="num" id="num" onkeyup="inputTotalMoney('num');">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<span>*</span> <label>单位</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-smaller" name="unitName" id="unitName">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>总额</label>
							</div>
							<div class="f_left" id="inputTotalMoney"></div>
						</li>
						<li>
							<div class="infor_name mt0">
								<label>报价含安调</label>
							</div>
							<div class="f_left inputfloat inputfloatmb0">
								<ul>
									<li><input type="radio" name="installation" value="1" checked> <label>是</label></li>
									<li><input type="radio" name="installation" value="0"> <label>否</label></li>
								</ul>
								<input type="hidden" name="haveInstallation" id="haveInstallation" />
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>货期</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-smaller mr5" name="deliveryCycle" id="deliveryCycle"><!--  <span class="mt4 mr5">天</span>  -->
								<!-- <span class="font-grey9 mt4">核价参考货期：3-5天，近半年货期均值：6.5天</span> -->
							</div>
						</li>
						<li>
							<div class="infor_name">
								<label>是否直发</label>
							</div>
							<div class="f_left inputfloat inputfloatmb0">
								<ul>
									<li class="mt4"><input type="radio" name="deliveryDirectRad" value="0" checked> <label>否</label></li>
									<li class="mt4"><input type="radio" name="deliveryDirectRad" value="1"> <label>是</label></li>
									<li><input type="text" placeholder="请填写直发原因，含有直发商品的订单不允许提前采购" class="input-larger" name="deliveryDirectComments" id="deliveryDirectComments"></li>
								</ul>
								<input type="hidden" name="deliveryDirect" id="deliveryDirect">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>内部备注</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-largest mr5" placeholder="内部备注不对外显示" name="insideComments" id="insideComments">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>产品备注</label>
							</div>
							<div class="f_left">
								<input type="text" class="input-largest mr5" placeholder="产品备注对外显示" name="goodsComments" id="goodsComments">
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>产品图片</label>
							</div>
							<div class="f_left">
								<div class="">
									<input type="file" class="upload_file" style="display: none;" name="lwfile" id="lwfile" onchange="uploadImgFtp(this);">
									<input type="text" class="input-middle" id="fileUrl" onclick="lwfile.click();" readonly="readonly">
									<label class="bt-bg-style bt-middle bg-light-blue ml4" onclick="lwfile.click();">浏览</label>
									
									<!-- <i class="iconsuccesss mt5" id="iconsuccess" style="display:none;"></i> -->
									
									<i class="iconsuccesss mt5 none" id="img_icon_wait"></i>
			                        <a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_opt_look">查看</a>
			                    	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg()" id="img_opt_del">删除</span>
	                                <span class="font-grey9">使用jpg格式，2MB以内</span>
								</div>
								<!-- 附件信息表字段隐藏信息 -->
								<input type="hidden" name="name" id="imgName"/>
								<input type="hidden" name="domain" id="imgDomain"/>
								<input type="hidden" name="uri" id="imgUri"/>
								
								<div class="font-grey9 mt5">友情提示<br/>1、如果您的操作导致报价单金额发生变化，可能需要重新编辑付款计划；</div>
							</div>
						</li>
					</ul>
					<input type="hidden" name="quoteorderId" id="quoteorderId" value="${quoteorderId}"/>
					<input type="hidden" name="isTemp" id="isTemp" value="1"/><!-- 是否临时产品 -->
					<div class="add-tijiao  tcenter">
						<button class="bt-bg-style bg-deep-green" type="button" onclick="inputSubmit();">提交</button>
						<button id="cancle" type="button" class="dele">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
