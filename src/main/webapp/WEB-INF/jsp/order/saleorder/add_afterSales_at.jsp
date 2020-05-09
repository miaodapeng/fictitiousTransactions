<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后-安调" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_th.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips5">
    <form method="post" action="<%= basePath %>/order/saleorder/saveAddAfterSales.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>售后类型</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales">
                        	<c:if test="${saleorder.thk eq 0 }">
                        		<li>
	                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=th&&saleorderId=${saleorder.saleorderId}">
	                                    <input type="radio" name="type" value="539">
	                                    <label>退货</label>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=hh&&saleorderId=${saleorder.saleorderId}">
	                                    <input type="radio" name="type" value="540">
	                                    <label>换货</label>
	                                </a>
	                            </li>
                        	</c:if>
                        	<!-- 耗材订单不展示 安调与维修 -->
                            <c:if test="${saleorder.orderType ne 5}">
	                            <li>
	                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=at&&saleorderId=${saleorder.saleorderId}">
	                                    <input type="radio" checked="true" name="type" value="541">
	                                    <label>安调</label>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=wx&&saleorderId=${saleorder.saleorderId}">
	                                    <input type="radio" name="type" value="584">
	                                    <label>维修</label>
	                                </a>
	                            </li>
                            </c:if>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=tp&&saleorderId=${saleorder.saleorderId}" >
                                    <input type="radio" name="type" value="542">
                                    <label>退票</label>
                                </a>
                            </li>
                            <c:if test="${saleorder.thk eq 0 }">
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=tk&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="543">
                                    <label>退款</label>
                                </a>
                            </li>
                            </c:if>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=jz&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="544">
                                    <label>技术咨询</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=qt&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="545">
                                    <label>其他</label>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <input type="hidden" id="" name="orderId" value="${saleorder.saleorderId}" />
            <input type="hidden" id="" name="orderNo" value="${saleorder.saleorderNo}" />
            <input type="hidden" id="" name="traderId" value="${saleorder.traderId}" />
            <input type="hidden" id="" name="thGoodsId" value="251462" />
            <input type="hidden" id="shtype" value="aw" />
            <input type="hidden" id="shtype-aw" value="at" />
            <input type="hidden" name="formToken" value="${formToken}"/>
  <!--         <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>售后原因</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul>
                        	<c:forEach items="${sysList}" var="sys" >
                        		<c:if test="${sys.relatedField eq 541 }">
                        			<li>
		                                <input type="radio" name="reason" value="${sys.sysOptionDefinitionId}">
		                                <label>${sys.title}</label>
		                            </li>
                        		</c:if>
                        	</c:forEach>
                        </ul>
                    </div>
                    <div id="reasonError" class="font-red " style="display: none">售后原因不允许为空</div>
                </div>
            </li> -->  
            <li>
                <div class="parts">
                    <div class="title-container">
                        <div class="table-title nobor">
                            选择商品
                        </div>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th class="wid5">选择</th>
                                <th >产品名称</th>
                                <th >品牌</th>
                                <th >型号</th>
                                <th class="wid12">物料编码</th>
                                <th >销售价</th>
                                <th>数量</th>
                                <th class="wid6">单位</th>
                                <th class="wid12">售后数量</th>
                                <th class="wid10">已发货数量</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${saleorder.sgvList}" var="sgv">
                        		<c:if test="${sgv.goodsId ne 140633 && sgv.goodsId ne 253620 && sgv.goodsId ne 251462 && sgv.goodsId ne 127063 && sgv.goodsId ne 251526 && sgv.goodsId ne 256675}">
	                        		 <tr>
		                                <td>
		                                	<c:choose>
		                                		<c:when test="${sgv.lockedStatus eq 0}">
		                                			<input type="checkbox" name="oneSelect" alt="${sgv.saleorderGoodsId}" checked="checked" > 
		                                		</c:when>
		                                		<c:otherwise>
		                                			<span style="color: red">锁</span>
		                                		</c:otherwise>
		                                	</c:choose>
		                                	
		                                	
		                                	<input type="hidden" name="afterSaleNums" >
		                                	<input type="hidden" name="goodsIds" value="${sgv.goodsId}">
                                            <input type="hidden" name="isActionGoodses" value="${sgv.isActionGoods}">
		                                	<!-- 退货数量与已发货数量比较
		                                	<input type="hidden" name="deliveryNums" id="deliveryNums_${sgv.saleorderGoodsId}" value="${sgv.num}">
		                                	暂时使用下面的-->
		                                	<input type="hidden" name="deliveryNums" id="deliveryNums_${sgv.saleorderGoodsId}" value="${sgv.afterSaleUpLimitNum}"> 
		                                </td>
		                                 <td class="text-left">
                                             <c:if test="${sgv.isActionGoods==1}"><span style="color:red;">【活动】</span></c:if>
		                                    <span class="font-blue cursor-pointer addtitle" style="display: inline; "
		                                    	tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
			                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${sgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[sgv.sku].SHOW_NAME}</span>
		                                    <div>${sgv.sku}</div>
		                                </td>
                                         <td>${newSkuInfosMap[sgv.sku].BRAND_NAME}</td>
                                         <td>${newSkuInfosMap[sgv.sku].MODEL}</td>
                                         <td>${newSkuInfosMap[sgv.sku].MATERIAL_CODE}</td>
		                                <td><fmt:formatNumber type="number" value="${sgv.price}" pattern="0.00" maxFractionDigits="2" /></td>
		                                <td>${sgv.num}</td>
		                                <td>${sgv.unitName}</td>
		                                <td><input type="text" style="width:50px;" alt1="${sgv.saleorderGoodsId}" value="${sgv.afterSaleUpLimitNum }"></td>
		                                <td>
		                                <!-- 暂时使用下面的
		                                	${sgv.num}
		                                	 --> ${sgv.afterSaleUpLimitNum }
		                                </td>
		                            </tr>
	                            </c:if>
                        	</c:forEach>
                        	<tr>
                        		<td colspan="10" style="text-align:left;padding-left:17px;overflow:hidden;">
                        			<input type="checkbox" id="allSelect"  checked="checked" style="float:left;"/><span style="float:left;">全选</span>
                        		</td>
                        	</tr>
                        </tbody>
                    </table>
                </div>
            </li>
            <li style="margin-top:-13px;">
                    <div class="form-tips">
                        <span>*</span>
                        <lable>详情说明</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                            <textarea name="comments" id="comments" placeholder="请详述客户需求及安调维修要求以便售后同事联系" rows="5" class="wid90"></textarea>
                        </div>
                        <div id="commentsError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>联系人</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select class="input-largest" name="traderContactId" id="traderContactId">
	                            <c:forEach items="${saleorder.tcList}" var="tc">
	                            	<option value="${tc.traderContactId}" 
	                            		<c:if test="${saleorder.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
	                            </c:forEach> 
                            </select>
                        </div>
                        <div id="traderContactIdError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>地区</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <ul> 
								<li>
									<select class="wid9" name="province" id="province">
										<option value="0">全部</option>
										<c:if test="${not empty provinceList }">
											<c:forEach items="${provinceList }" var="prov">
												<option value="${prov.regionId }"
													<c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
											</c:forEach>
										</c:if>
									</select>
								</li>
								<li>
									<select class="wid9" name="city" id="city">
										<option value="0">全部</option>
										<c:if test="${not empty cityList }">
											<c:forEach items="${cityList }" var="cy">
												<option value="${cy.regionId }"
													<c:if test="${city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
											</c:forEach>
										</c:if>
									</select> 
								</li>
								<li>	
									<select class="wid9" name="zone" id="zone">
										<option value="0">全部</option>
										<c:if test="${not empty zoneList }">
											<c:forEach items="${zoneList }" var="zo">
												<option value="${zo.regionId }"
													<c:if test="${zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
											</c:forEach>
										</c:if>
									</select>
								</li>
							</ul>
                        </div>
                        <div id="areaError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>详细地址</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                             <input type="text" class="input-largest" id="address" name="address" value="${saleorder.takeTraderAddress}"> 
                        </div>
                        <div id="addressError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>上传附件</lable>
                    </div>
                    <input type="hidden" id="domain" name="domain" value="${domain}">
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<div class="pos_rel f_left">
	                            <input type="file" class="uploadErp" id='file_1' name="lwfile" onchange="uploadFile(this,1);">
	                            <input type="text" class="input-largest" id="name_1" readonly="readonly"
	                            	placeholder="请上传附件" name="fileName" onclick="file_1.click();" > 
	                            <input type="hidden" id="uri_1" name="fileUri" >
			    			</div>
                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_1').click();">浏览</label>
                            <!-- 上传成功出现 -->
                            <div class="f_left">
	                            <i class="iconsuccesss mt3 none" id="img_icon_1"></i>
	                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_1">查看</a>
		                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(1)" id="img_del_1">删除</span>
	                    	</div>
	                    	<div class='clear'></div>
                        </div>
                        <div class="mt8" id="conadd">
                            <span class="bt-border-style bt-small border-blue" onclick="conadd();">继续添加</span>
                        </div>
                        <div class="pop-friend-tips mt6">
                        	安调须知：
							<br/>1、安调区域与销售区域不一致时，需运营部审核；
                            <div class="add-tijiao text-left mt8">
                                <button type="submit" id="submit">提交</button>
                            </div>
                        </div>
                    </div>
                </li>
        </ul>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>