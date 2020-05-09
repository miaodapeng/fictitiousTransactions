<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function(){
		var goodsIdStr = $("input[name='goodsIdStr']").val();
		//补商品列表相关数据
		$.ajax({
			async:true,
			url:page_url+'/goods/goods/getgoodslistextrainfo.do',
			data:{"goodsIdStr":goodsIdStr},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					for (var i = 0; i < data.data.length; i++) {
						//alert(data.data[i].saleorderGoodsId);
						$("#orderOccupy_"+data.data[i].goodsId).html(data.data[i].orderOccupy);
						$("#adjustableNum_"+data.data[i].goodsId).html(data.data[i].adjustableNum);
						$("#stockNum_"+data.data[i].goodsId).html(data.data[i].stockNum);
						$("#onWayNum_"+data.data[i].goodsId).html(data.data[i].onWayNum);
						$("#saleNum365_"+data.data[i].goodsId).html(data.data[i].saleNum365);
					}
					for (var i = 0; i < data.param.length; i++) {
						//alert(data.data[i].saleorderGoodsId);
						$("#distributionPrice_"+data.param[i].goodsId).html(data.param[i].distributionPrice.toFixed(2));
					}
				}else{
					layer.alert(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	})
</script>
	<div class="content">
		<div class="searchfunc">
			<c:if test="${goods.source==0}">		
				<form action="${pageContext.request.contextPath}/goods/goods/index.do" method="post" id="search">
			</c:if>
			<c:if test="${goods.source==1}">
				<form action="${pageContext.request.contextPath}/goods/goods/goGoodsIndex.do" method="post" id="search">
			</c:if>	
				<ul>
	                <li>
	                    <label class="infor_name">商品名称</label>
	                    <input type="text" class="input-middle" name="goodsName" id="" value="${goods.goodsName}">
	                </li>
	                <li>
	                    <label class="infor_name">商品品牌</label>
	                    <input type="text" class="input-middle" name="brandName" id="" value="${goods.brandName}">
	                </li>
	                <li>
	                    <label class="infor_name">制造商型号</label>
	                    <input type="text" class="input-middle" name="model" id="" value="${goods.model}">
	                </li>
	                <li>
	                    <label class="infor_name">订货号</label>
	                    <input type="text" class="input-middle" name="sku" id="" value="${goods.sku}">
	                </li>
	                <c:if test="${goods.source==0}">
		                <li>
		                    <label class="infor_name">供应商型号</label>
		                    <input type="text" class="input-middle" name="supplyModel" id="" value="${goods.supplyModel}">
		                </li>
		                <li>
		                    <label class="infor_name">厂家名称</label>
		                    <input type="text" class="input-middle" name="manufacturer" id="" value="${goods.manufacturer}">
		                </li>
		                <li>
		                    <label class="infor_name">物料编码</label>
		                    <input type="text" class="input-middle" name="materialCode" id="" value="${goods.materialCode}">
		                </li>
		                <li>
		                    <label class="infor_name">商品归属</label>
		                    <select class="input-middle f_left" name="goodsUserId">
			                    <option value="-1">全部</option>
			                    <c:if test="${not empty buyerList }">
									<c:forEach items="${buyerList }" var="user">
										<option value="${user.userId }"
											<c:if test="${goods.goodsUserId eq user.userId }">selected="selected"</c:if>>${user.username }</option>
									</c:forEach>
								</c:if>
		                    </select>
	                    </li>
                     </c:if>
                    <li>
	                    <label class="infor_name">管理类别</label>
	                    <select class="input-middle f_left" name="manageCategoryLevel">
	                    	<option value="-1">全部</option>
		                    <c:forEach var="list" items="${manageCategoryLevels}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${goods.manageCategoryLevel == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
                    <c:if test="${goods.source==0}">
	                    <li>
		                    <label class="infor_name">商品级别</label>
		                    <select class="input-middle f_left" name="goodsLevel">
		                    	<option value="-1">全部</option>
			                    <c:forEach var="list" items="${goodsLevels}">
			                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${goods.goodsLevel == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
			                    </c:forEach>
		                    </select>
	                    </li>
	                </c:if>
                    <li>
	                    <label class="infor_name">商品类型</label>
	                    <select class="input-middle f_left" name="goodsType">
	                    	<option value="-1">全部</option>
		                    <c:forEach var="list" items="${goodsTypes}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${goods.goodsType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">上架状态</label>
	                    <select class="input-middle f_left" name="isOnSale">
	                    	<option value="-1">全部</option>
	                    	<option value="1" <c:if test="${goods.isOnSale == 1}">selected="selected"</c:if> >上架</option>
	                    	<option value="0" <c:if test="${goods.isOnSale == 0}">selected="selected"</c:if> >下架</option>
	                    </select>
                    </li>
                    <c:if test="${goods.source==0}">
	                    <li>
		                    <label class="infor_name">禁用状态</label>
		                    <select class="input-middle f_left" name="isDiscard">
		                    	<option value="-1">全部</option>
		                    	<option value="1" <c:if test="${goods.isDiscard == 1}">selected="selected"</c:if> >已禁用</option>
		                    	<option value="0" <c:if test="${goods.isDiscard == 0}">selected="selected"</c:if> >未禁用</option>
		                    </select>
	                    </li>
	                </c:if>
                    <li>
	                    <label class="infor_name">审核状态</label>
	                    <select class="input-middle f_left" name="verifyStatus">
	                    	<option value="">全部</option>
	                    	<option <c:if test="${goods.verifyStatus eq 3}">selected</c:if> value="3">待审核</option>
						    <option <c:if test="${goods.verifyStatus eq 0}">selected</c:if> value="0">审核中</option>
						    <option <c:if test="${goods.verifyStatus eq 1}">selected</c:if> value="1">审核通过</option>
						    <option <c:if test="${goods.verifyStatus eq 2}">selected</c:if> value="2">审核未通过</option>
	                    </select>
                    </li>
                    <c:if test="${goods.source==0}">
	                    <li>
	                        <label class="infor_name">商品运营分类</label>
	                        <span id="category_div">
		                        <select id='categoryOpt' name='categoryOpt' onchange="updateCategory(this,'search');">
									<option value="-1" id="1">请选择</option>
									<c:forEach var="list" items="${categoryList}" varStatus="status">
										<option value="${list.categoryId}" id="${list.level}">${list.categoryName}</option>
									</c:forEach>
								</select>
							</span>
							<input type="hidden" name="categoryId" id="categoryId" value="${goods.categoryId}" />
	                    </li>
	                    <li>
		                    <label class="infor_name">是否主推</label>
		                    <select class="input-smaller" name="isRecommed">
		                    	<option value="-1">全部</option>
		                    	<option <c:if test="${goods.isRecommed eq 0}">selected</c:if> value="0">否</option>
							    <option <c:if test="${goods.isRecommed eq 1}">selected</c:if> value="1">是</option>
		                    </select>
	                    </li>
	                </c:if>
                    <li>
                        <label class="infor_name">新国标分类</label>
                        <span id="standard_category_div">
	                        <select id='standardCategoryOpt' name='standardCategoryOpt' onchange="updateStandardCategory(this,'search');">
								<option value="-1" id="1">请选择</option>
								<c:forEach var="list" items="${standardCategoryList}" varStatus="status">
									<option value="${list.standardCategoryId}" id="${list.level}">${list.categoryName}</option>
								</c:forEach>
							</select>
						</span>
						<input type="hidden" name="standardCategoryId" id="standardCategoryId" value="${goods.standardCategoryId}" />
                    </li>
                    <li>
						<label class="infor_name">是否核价</label>
						<select class="input-middle" name="isChannelPrice" id="isChannelPrice">
							<option value="">全部</option>
							<option <c:if test="${goods.isChannelPrice eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${goods.isChannelPrice eq 1}">selected</c:if> value="1">是</option>
						</select>
					</li>
                    <li>
	                    <label class="infor_name mt0">
	                    	<select name="searchDateType">
	                    		<option value="1" <c:if test="${goods.searchDateType == 1}">selected="selected"</c:if> >创建时间</option>
	                    		<option value="2" <c:if test="${goods.searchDateType == 2}">selected="selected"</c:if> >更新时间</option>
	                    		<option value="3" <c:if test="${goods.searchDateType == 3}">selected="selected"</c:if> >审核时间</option>
	                    	</select>
	                    </label>
	                    <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${goods.searchBegintime}" format="yyyy-MM-dd" />'>
	                    <div class="gang">-</div>
	                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${goods.searchEndtime}" format="yyyy-MM-dd" />'>
                    </li>
					<li>
						<label class="infor_name">7天无理由</label>
						<select class="input-smaller" name="isNoReasonReturn">
							<option value="-1">全部</option>   
							<option <c:if test="${goods.isNoReasonReturn eq 0}">selected</c:if> value="0">否</option>
							<option <c:if test="${goods.isNoReasonReturn eq 1}">selected</c:if> value="1">是</option>
						</select>
					</li>
                    <%--<c:if test="${loginUser.companyId != 1}">
	                    <li>
	                        <label class="infor_name">公司</label>
	                        <span id="standard_category_div">
		                        <select id='showCompanyType' name='showCompanyType'>
									<option value="-1" <c:if test="${goods.showCompanyType == -1}">selected="selected"</c:if>>全部</option>
									<option value="1"  <c:if test="${goods.showCompanyType == 1}">selected="selected"</c:if>>总部</option>
									<option value="2" <c:if test="${goods.showCompanyType == 2 or goods.showCompanyType == null}">selected="selected"</c:if>>${loginUser.companyName}</option>
								</select>
							</span>
							<input type="hidden" name="standardCategoryId" id="standardCategoryId" value="${goods.standardCategoryId}" />
	                    </li>
                    </c:if>--%>
            	</ul>
            	<div class="tcenter">
	                <span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span>
	                <span class="bt-small bg-light-blue bt-bg-style" onclick="reset();$('#categoryId').val('');$('#standardCategoryId').val('')">重置</span>
	                <c:if test="${goods.source==0}">
		                <span class='addtitle bg-light-blue bt-bg-style bt-small' tabTitle='{"num":"addProduct","link":"./goods/goods/add.do","title":"新增商品"}'>新增商品</span>
	            		<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"500px","height":"200px","title":"批量新增商品","link":"./batchAddGoodsInit.do"}'>批量新增商品</span>
	            		<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"500px","height":"200px","title":"批量商品授权与定价","link":"./batchAuthorizationPricing.do"}'>批量商品授权与定价</span>
	            		<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"500px","height":"200px","title":"批量修改信息","link":"./batchUpdateGoodsInit.do"}'>批量修改信息</span>
	            		<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"500px","height":"200px","title":"批量物流运输","link":"./logisticsTransportation.do"}'>批量物流运输</span>
	            		<!-- <span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"500px","height":"200px","title":"上传核价信息","link":"./batchPriceGoodsInit.do"}'>上传核价信息</span> -->
	            		<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"500px","height":"200px","title":"上传税收分类编码","link":"./uplodeTaxCategoryNo.do?id=${list.goodsId}"}'>上传税收分类编码</span>
	            		<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"500px","height":"200px","title":"上传批量修改结算价","link":"./batchGoodsSettelmentInit.do"}'>上传结算信息</span>
            			<span class="bg-light-blue bt-bg-style bt-small" onclick="exportGoodsList()">导出列表</span>
            		</c:if>
            	</div>
			</form>
		</div>
		<div class="fixdiv">
        <div class="superdiv" style="width:2600px;">
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th style="width:40px">选择</th>
                        <th style="width:100px">订货号</th>
                        <th style="width:300px">商品名称</th>
                        <th style="width:80px">是否主推</th>
						<th style="width:110px">7天无理由退换货</th>
                        <th style="width:60px">参考价格</th>
                        <th style="width:100px">制造商型号</th>
                        <th style="width:40px">单位</th>
						<th>商品归属</th>
                        <th style="width: 140px">订单占用/可调剂/库存</th>
                        <th >在途</th>
                        <th>审核状态</th>
                        <th style="width:100px">品牌</th>
                        <th style="width:100px">厂家名称</th>
                        <th style="width:100px">物料编码</th>
                        <th style="width:100px">商品类型</th>
                        <th style="width:200px">商品运营分类</th>
                        <th style="width:160px">新国标分类</th>
                        <th style="width:60px">商品级别</th>
                        <th>近一年销量</th>
                        <th style="width:160px">创建时间</th>
                        <th>禁用状态</th>
                        <th style="width:160px">更新时间</th>
                        <c:if test="${goods.source==0}">
                        	<th style="width:100px">操作</th>
                        </c:if>
					</tr>
				</thead>
			
				<tbody class="goods">
				<c:set var="goodsIdStr" value="1"></c:set>
				<c:if test="${not empty goodsList}">
                	<c:forEach items="${goodsList }" var="list" varStatus="status">
	                    <tr>
	                        <td>
	                        	<c:if test="${isBuyer == 1}">
	                        		<input type="checkbox" name="checkOne" value="${list.goodsId}" autocomplete="off">
	                        	</c:if>
	                        </td>
	                        <td>
								<c:set var="shenhe" value="0"></c:set>
								<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
									<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
										<c:if test="${verifyUsernameInfo == loginUser.username}">
											<c:set var="shenhe" value="1"></c:set>
										</c:if>
									</c:forEach>
								</c:if>
	                       		 ${list.sku}${list.verifyStatus == 0 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
	                       	</td>
	                        <td>
	                        	<c:if test="${list.companyId == loginUser.companyId}">
									<c:if test="${list.source eq 1}"><span style="color: red">【医械购】</span></c:if><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo2.do?goodsId=${list.goodsId}","title":"商品信息"}'><c:out value=" ${list.goodsName} " escapeXml="true" /></a>
	                        	</c:if>
	                        	<c:if test="${list.companyId != loginUser.companyId}">
									<c:if test="${list.source eq 1}"><span style="color: red">【医械购】</span></c:if>${list.goodsName}
	                        	</c:if>
	                        </td>
	                        <td id="isRecommed_${list.goodsId}">
	                        	<c:if test="${not empty list.userList }">
	                        		<c:set var="userCount" value="0"></c:set>
	                        		<!-- 验证商品归属是否属于当前登录人 -->
									<c:forEach items="${list.userList }" var="user" varStatus="st">
										<c:if test="${loginUser.userId eq user.userId and userCount eq 0}">
				                        	<c:set var="userCount" value="1"></c:set>
										</c:if>
									</c:forEach>
								</c:if>
									<c:choose>
		                        		<c:when test="${list.isRecommed eq 1}">
		                        			<font color="red">是</font>
		                        			<c:if test="${userCount eq 1}">
			                        			&nbsp;&nbsp;
			                        			<span class="edit-user" onclick="updateIsRecommed(${list.goodsId},0);">取消主推</span>
		                        			</c:if>
		                        		</c:when>
		                        		<c:otherwise>
		                        			否
		                        			<c:if test="${userCount eq 1}">
			                        			&nbsp;&nbsp;
			                        			<span class="edit-user" onclick="updateIsRecommed(${list.goodsId},1);">设为主推</span>
		                        			</c:if>
		                        		</c:otherwise>
		                        	</c:choose>
	                        </td>
							<td>
								<c:choose>
									<c:when test="${list.isNoReasonReturn eq 1}">是</c:when>
									<c:otherwise>否</c:otherwise>
								</c:choose>
							</td>
	                        <td>${list.channelPrice}</td>
	                        
	                        <td>${list.model}</td>
	                        <td>${list.unitName}</td>
	                        <!-- 商品归属 -->
	                        <td>
	                        	<c:if test="${not empty list.userList }">
									<c:forEach items="${list.userList }" var="user" varStatus="st">
										${user.username } <c:if test="${st.count != list.userList.size() }">、</c:if>
									</c:forEach>
								</c:if>
							</td>
	                        <!-- 订单占用/可调剂/库存 -->
	                        <td>
		                        <span id="orderOccupy_${list.goodsId}">${list.orderOccupy}</span>/
		                        <span id="adjustableNum_${list.goodsId}">${list.adjustableNum}</span>/
		                        <span id="stockNum_${list.goodsId}">${list.stockNum}</span>
	                        </td>
	                        <!-- 在途 -->
	                        <td>
	                        	<span id="onWayNum_${list.goodsId}">${list.onWayNum}</span>
	                        </td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.verifyStatus eq 0}">
										审核中
									</c:when>
									<c:when test="${list.verifyStatus eq 1}">
										审核通过
									</c:when>
									<c:when test="${list.verifyStatus eq 2}">
										审核未通过
									</c:when>
									<c:otherwise>
										待审核
									</c:otherwise>
								</c:choose>
	                        </td>
	                        <td>${list.brandName}</td>
	                        <td>${list.manufacturer}</td>
	                        <td>${list.materialCode}</td>
	                        <td>
		                        <c:forEach var="li" items="${goodsTypes}">
		                        	<c:if test="${li.sysOptionDefinitionId ==  list.goodsType}">${li.title}</c:if>
			                    </c:forEach>
	                        </td>
	                        <td>${list.categoryName}</td>
	                        <td>${list.standardCategoryName}</td>
	                        <td>
	                        <c:forEach var="li" items="${goodsLevels}" >
	                        	<c:if test="${li.sysOptionDefinitionId ==  list.goodsLevel}">${li.title}</c:if>
		                    </c:forEach>
	                        </td>
	                        <td><span id="saleNum365_${list.goodsId}">${list.saleNum365}</span></td>
	                        
	                        <td><date:date value ="${list.addTime}"/></td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.isDiscard eq 1}">已禁用</c:when>
									<c:otherwise>未禁用</c:otherwise>
								</c:choose>
	                        </td>
	                        <td><date:date value ="${list.modTime}"/></td>
	                        <c:if test="${goods.source==0}">
		                        <td>
		                        	<c:if test="${list.companyId == loginUser.companyId}" >
		                        		<c:choose>
										<c:when test="${list.isDiscard eq 1}">
											<span class="edit-user" onclick="discardEnable(${list.goodsId});">启用</span>
										</c:when>
										<c:otherwise>
											<span class="forbid clcforbid pop-new-data" layerParams='{"width":"600px","height":"250px","title":"禁用","link":"./initDiscardPage.do?id=${list.goodsId}"}'>禁用</span>
										</c:otherwise>
										</c:choose>
										<c:set var="goodsIdStr" value="${goodsIdStr}_${list.goodsId}"></c:set>
		                        	</c:if>
		                        	<c:if test="${list.companyId != loginUser.companyId }" >
		                        		<span class="edit-user" onclick="copyGoods('${list.goodsName}',${list.goodsId});">复制</span>
		                        	</c:if>
		                        </td>
		                   </c:if>
		                   <c:if test="${goods.source==1}">
		                   		<c:if test="${list.companyId == loginUser.companyId}" >
		                   			<c:set var="goodsIdStr" value="${goodsIdStr}_${list.goodsId}"></c:set>
		                   		</c:if>
		                   </c:if>
	                    </tr>
                	</c:forEach>
        		</c:if>
				</tbody>
			</table>
			<c:if test="${empty goodsList}">
				<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			</div>
		</div>
		<div>
			<c:if test="${isBuyer == 1}">
			<div class="inputfloat f_left">
                <input type="checkbox" class="mt6 mr4" name="checkAll" autocomplete="off">
                <label class="mr10 mt4">全选</label>
                <select>
                    <option>批量迁移分类</option>
                </select>
               
               <span>
                <select id='categoryOpt' name='categoryOpt' onchange="updateCategory(this,'change');" autocomplete="off">
					<option value="-1" id="1">请选择</option>
					<c:forEach var="list" items="${categoryList}" varStatus="status">
						<option value="${list.categoryId}" id="${list.level}">${list.categoryName}</option>
					</c:forEach>
				</select>
				</span>
				<input type="hidden" name="changeCategoryId" id="changeCategoryId" value="0" />
                <span class="bt-bg-style bg-light-blue bt-small mr10" onclick="batchOptSubmit()">确定</span>
            </div>
            </c:if>
			<tags:page page="${page}"/>
		</div>
		<input type="hidden" name="goodsIdStr" value="${goodsIdStr}" />
	</div>
<%@ include file="../../common/footer.jsp"%>
