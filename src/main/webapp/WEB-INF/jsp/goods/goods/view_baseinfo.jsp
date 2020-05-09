<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品基本信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/view_baseinfo.js?rnd=<%=Math.random()%>'></script>
	<div class="customer">
        <ul>
            <li>
                <a class="customer-color" href="${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}">商品信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}">销售信息</a>
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
	                <div class="table-title nobor">基本信息</div>
	                <c:if test="${(null==taskInfo and null==taskInfo.getProcessInstanceId())or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id]) }">
	                   <c:if test="${ goods.verifyStatus != 1}">
	                      <div class="title-click nobor" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/editbaseinfo.do?goodsId=${goods.goodsId}');">编辑</div>
                       </c:if>
                    </c:if>
            </div>
            
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smallest">商品名称</td>
                        <td><a href="http://www.vedeng.com/goods-${goods.goodsId}.html" target="_blank"><c:out value=" ${goods.goodsName} " escapeXml="true" /></a></td>
                        <td class="table-smallest">订货号</td>
                        <td>${goods.sku}</td>
                    </tr>
                    <tr>
                        <td>厂家名称</td>
                        <td><c:out value=" ${goods.manufacturer} " escapeXml="true" /></td>
                        <td>品牌名称</td>
                        <td>${goods.brandName}</td>
                    </tr>
                    <tr>
                        <td>制造商型号</td>
                        <td>${goods.model}</td>
                        <td>供应商型号</td>
                        <td>${goods.supplyModel}</td>
                    </tr>
                    <tr>
                    	<td>物料编码</td>
                        <td>${goods.materialCode}</td>
                        <c:if test="${goods.goodsType != 318 }">
	                        <td>新国标分类</td>
	                        <td>${goods.standardCategoryName}</td>
                        </c:if>
                        <c:if test="${goods.goodsType == 318 }">
                        	<td></td>
	                        <td></td>
                        </c:if>
                    </tr>
                    <tr>
                    	<c:if test="${goods.standardCategoryId != 1388 }">
                    		<td>管理类别</td>
	                        <td>
	                        	<c:forEach var="li" items="${manageCategorylevels}">
		                        	<c:if test="${li.sysOptionDefinitionId ==  goods.manageCategoryLevel}">${li.title}</c:if>
			                    </c:forEach>
	                        </td>
                    	</c:if>
                        <c:if test="${goods.standardCategoryId == 1388 }">
                        	<td></td>
	                        <td>
	                        	
	                        </td>
                        </c:if>
                        <td>旧国标分类</td>
                        <td>
	                        <c:forEach var="li" items="${manageCategorys}">
	                        	<c:if test="${li.sysOptionDefinitionId ==  goods.manageCategory}">${li.title}</c:if>
		                    </c:forEach>
	                    </td>
                    </tr>
                    <tr>
                        <td>商品运营分类</td>
                        <td>${goods.categoryName}</td>
                        <td>单位</td>
                        <td>${goods.unitName}</td>
                    </tr>
                    <tr>
                        <td>商品类型</td>
                        <td>
                        <c:forEach var="li" items="${goodsTypes}">
                        	<c:if test="${li.sysOptionDefinitionId ==  goods.goodsType}">${li.title}</c:if>
	                    </c:forEach>
                        </td>
                        <td>商品图片</td>
                        <td>
                        <c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 343}">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">查看</a>&nbsp;&nbsp;
	                    	</c:if>
	                    </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>商品体积</td>
                        <td>长度 ${goods.goodsLength} cm   宽度 ${goods.goodsWidth} cm   高度 ${goods.goodsHeight} cm   净重 ${goods.netWeight} kg</td>
                        <td>运输/包装体积</td>
                        <td>长度 ${goods.packageLength} cm   宽度 ${goods.packageWidth} cm   高度 ${goods.packageHeight} cm   毛重 ${goods.grossWeight} kg</td>
                    </tr>
                    <tr>
                        <td>商品负责人</td>
                        <td>
                        <c:if test="${not empty goods.userList }">
							<c:forEach items="${goods.userList }" var="user" varStatus="st">
								${user.username } <c:if	test="${st.count != goods.userList.size() }">、</c:if>
							</c:forEach>
						</c:if>
                        </td>
                        <td>Wiki链接</td>
	                    <td>
	                    	<c:if test="${not empty goods.href }">
								<a href="${goods.href}" target="_blank">${goods.href}</a>
							</c:if>
	                    	<c:if test="${empty goods.href }">
								--
							</c:if>
	                    </td>
                       <!--  ${goods.manageCategoryLevel}
                       		<td>批准文号</td>
                        	<td>${goods.licenseNumber}</td>
                        -->
                        <%-- <c:if test="${goods.manageCategoryLevel == 340 || goods.manageCategoryLevel == 341}">
	                        <td>注册证名称</td>
	                        <td>${goods.registrationNumberName}</td>
                        </c:if>
                        <c:if test="${!(goods.manageCategoryLevel == 340 || goods.manageCategoryLevel == 341)}">
	                        <td></td>
	                        <td></td>
                        </c:if> --%>
<%--                         <c:if test="${goods.manageCategoryLevel == 340 || goods.manageCategoryLevel == 341}">
	                        <td>注册证号</td>
	                        <td>${goods.registrationNumber}</td>
                        </c:if>
                        <c:if test="${!(goods.manageCategoryLevel == 340 || goods.manageCategoryLevel == 341)}">
	                        <td></td>
	                        <td></td>
                        </c:if> --%>
                    </tr>
                    
                   <c:if test="${goods.manageCategoryLevel == 340 || goods.manageCategoryLevel == 341}">
                    	<tr>
	                        <td>注册证号</td>
	                        <td>${goods.registrationNumber}</td>
	                        <td>--</td>
	                        <td>--</td>
                    	</tr>
                    </c:if>
                    <c:if test="${goods.manageCategoryLevel == 339}">
	                    <tr>
	                        <td>备案编号</td>
	                        <td>${goods.recordNumber}</td>
	                         <td>备案文件</td>
	                        <td>
	                       	<c:forEach items="${attachmentList}" var="list" varStatus="status">
		                    	<c:if test="${list.attachmentType == 680}">
		                    	<a href="http://${list.domain}${list.uri}" target="_blank">查看</a>
		                    	</c:if>
	                    	</c:forEach>
	                        </td>
	                    </tr>
                    </c:if>
                    <c:if test="${goods.manageCategoryLevel == 340 || goods.manageCategoryLevel == 341}">
	                    <tr>
	                        <td>注册证</td>
	                        <td>
	                       	<c:forEach items="${attachmentList}" var="list" varStatus="status">
		                    	<c:if test="${list.attachmentType == 344}">
		                    	<a href="http://${list.domain}${list.uri}" target="_blank">查看</a>
		                    	</c:if>
	                    	</c:forEach>
	                        </td>
	                        <td>有效期</td>
	                        <td>
	                        <date:date value ="${goods.begintime}" format="yyyy-MM-dd"/> ~ <date:date value ="${goods.endtime}" format="yyyy-MM-dd"/>
	                        <c:if test="${goods.endtime != 0 and currentTime > goods.endtime}"><span class="font-red">（已过期）</span> </c:if>
	                        </td>
	                    </tr>
                    </c:if>
                    <tr>
                        <td>包装清单</td>
                        <td>${goods.packingList}</td>
                        <td>商品规格</td>
                        <td>${goods.spec}</td>
                    </tr>
                    <tr>
                        <td>商品产地</td>
                        <td>${goods.productAddress}</td>
                        <td>商品级别</td>
                        <td>
                        <c:forEach var="li" items="${goodsLevels}">
                        	<c:if test="${li.sysOptionDefinitionId ==  goods.goodsLevel}">${li.title}</c:if>
	                    </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>检测报告</td>
                        <td>
                        <c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 658}">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">查看</a>&nbsp;&nbsp;
	                    	</c:if>
	                    </c:forEach>
                        </td>
                        <td>专利文件</td>
                        <td>
                        <c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 659}">
	                    	<a href="http://${list.domain}${list.uri}" target="_blank">查看</a>&nbsp;&nbsp;
	                    	</c:if>
	                    </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>采购提醒</td>
                        <td>${goods.purchaseRemind}</td>
                        <td>服务条款</td>
                        <td>${goods.tos}</td>
                    </tr>
                    <tr>
                        <td>应用科室</td>
                        <td>
                        	<c:forEach items="${goodsSysOptionAttributeList}" var="list" varStatus="status">
                        	<c:if test="${list.attributeType  == 320}">
	                        ${list.title}&nbsp;
	                        </c:if>
	                        </c:forEach>
                        </td>
                        <td>7天无理由退换货</td>
                        <td>
							<c:choose>
								<c:when test="${goods.isNoReasonReturn eq 1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
                    </tr>
                    <tr>
                        <td>技术参数</td>
                        <td colspan="3" class="text-left">${goods.technicalParameter}</td>
                    </tr>
                    <tr>
                        <td>性能参数</td>
                        <td colspan="3" class="text-left">${goods.performanceParameter}</td>
                    </tr>
                    <tr>
                        <td>规格参数</td>
                        <td colspan="3" class="text-left">${goods.specParameter}</td>
                    </tr>
                    <tr>
                        <td>商品属性</td>
                        <td colspan="3" class="text-left">
                        <c:forEach items="${goodsAttributeMap}" var="map">
                        	${map.key} ：
                        	<c:forEach items="${map.value}" var="list">
                        		${list}&nbsp;
                        	</c:forEach>
                        	<br>
                        </c:forEach>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="table-buttons">
        <input type="hidden" name="formToken" value="${formToken}"/>
        	<c:choose>
				<c:when test="${goods.verifyStatus == null || goods.verifyStatus != 1}">
				<c:if test="${(null==taskInfo and null==taskInfo.getProcessInstanceId())or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}">
					<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="applyValidGoods(${goods.goodsId},${taskInfo.id == null ?0: taskInfo.id})">申请审核</button>
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
				<c:when test="${goods.verifyStatus != null && goods.verifyStatus == 1}">
					<button type="button" class="confSearch bt-small bt-bg-style bg-light-blue" onclick="restVerify(${goods.goodsId});">重置为待审核</button>
				</c:when>
			</c:choose>
     </div>
     <div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
		                审核记录
		            </div>
		        </div>
		        <table class="table table-bordered table-striped table-condensed table-centered">
		            <tbody>
		                <tr>
		                    <td>操作人</td>
                        	<td>操作时间</td>
                        	<td>操作事项</td>
                       	    <td>备注</td>
		                </tr>
		                <c:if test="${not empty historicActivityInstance}">
		             
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
			            	<tr>
			                	<td colspan="4">暂无审核记录!</td>
			                </tr>
	        			</c:if>
                </tbody>
            </table>
            
        	<div class="clear"></div>
		 </div>
        
      <!--<div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
                操作日志
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>时间</th>
                    <th>操作人</th>
                    <th>IP地址</th>
                    <th>操作动作</th>
                    <th>操作详情</th>
                    <th>操作结果</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>李月月</td>
                    <td>女</td>
                    <td>尔尕尔啊如果让他</td>
                    <td>404840217123@qq.com</td>
                    <td>404840217123</td>
                    <td>娃儿噶儿童诗阿尔噶而过 </td>
                    <td>结果</td>
                </tr>
            </tbody>
        </table>
                审核记录
            </div>
            <div class="title-click nobor  pop-new-data" layerParams='{"width":"78%","height":"50%","title":"管理信息","link":"editpages/addcontacts.html"}'>
                申请审核
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>申请人</th>
                    <th>申请时间</th>
                    <th>审核结果</th>
                    <th>审核人</th>
                    <th>审核时间</th>
                    <th>备注</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>李月月</td>
                    <td>女</td>
                    <td>尔尕尔啊如果让他</td>
                    <td>404840217123@qq.com</td>
                    <td>404840211117123</td>
                    <td>娃儿噶儿童诗阿尔噶而过 </td>
                </tr>
            </tbody>
        </table>
    </div>
    </div> -->
     
    </div>
<%@ include file="../../common/footer.jsp"%>