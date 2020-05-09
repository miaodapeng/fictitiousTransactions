
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="联系人与地址" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/view_contact.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
function edit(url){
	parent.document.getElementById('tab_frame_1').src=url;
}
</script>
<%@ include file="customer_tag.jsp"%>
<div class="content">
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
                联系人信息
            </div>
            <c:if test="${customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
            <div class="title-click nobor  pop-new-data" layerParams='{"width":"700px","height":"510px","title":"新增联系人","link":"./toAddContactPage.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"}'>
                新增
            </div>
            </c:if>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
        <input type="hidden" id="traderType" value="1"/>
        <input type="hidden" id="traderCustomerId" value="${traderCustomer.traderCustomerId }"/>
            <thead>
                <tr>
                    <th class="wid6">姓名</th>
                    <th class="wid4">性别</th>
                    <th>部门</th>
                    <th class="wid14">电话</th>
                    <th class="wid14">手机</th>
                    <th class="wid14">手机2</th>
                    <th>邮箱</th>
                    <th>QQ</th>
                    <th>微信</th>
                    <th> 备注</th>
                    <th>注册账号</th>
                    <th> 默认联系人</th>
                    <th class="wid6">在职状态</th>
					<th>是否注册</th>
					<th>贝登商城会员</th>
                    <th class="wid17">操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not empty contactList}">
					<c:forEach items="${contactList}" var="con" varStatus="status">
		                <tr>
		                    <td>
		                    <a href="javascript:void(0);">
		                    <span class="editclick" 
		                        	onclick="goUrl('${pageContext.request.contextPath}/trader/customer/getContactInfo.do?traderContactId=${con.traderContactId}&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}');">${con.name}</span>
		                    </a></td>
		                    <td>
		                    	<c:if test="${con.sex eq 0 }">女</c:if>
		                    	<c:if test="${con.sex eq 1 }">男</c:if>
		                    	<c:if test="${con.sex eq 2 }">未知</c:if>
		                    </td>
		                    <td>${con.department}</td>
		                    <td>
		                    <c:if test="${not empty con.telephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${con.telephone}',${traderCustomer.traderId},1,0,0,${con.traderContactId});"></i>
	                        </c:if>
		                    <span>${con.telephone}</span></td>
		                    <td>
		                    <c:if test="${not empty con.mobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${con.mobile}',${traderCustomer.traderId},1,0,0,${con.traderContactId});"></i>
	                        </c:if>
							<span>${con.mobile}</span></td>
							<td>
		                    <c:if test="${not empty con.mobile2}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${con.mobile2}',${traderCustomer.traderId},1,0,0,${con.traderContactId});"></i>
	                        </c:if>
							<span>${con.mobile2}</span></td>
		                    <td>${con.email}</td>
		                    <td>${con.qq}</td>
		                    <td>${con.weixin}</td>
		                    <td>${con.comments}</td>
		                    <td>${con.account}</td>
		                    <td>
		                    	<c:if test="${con.isEnable eq 1}">
		                    	<input type="radio" onclick="setDefault(${con.traderContactId},${con.isDefault},${con.traderId},0,0,0);" <c:if test="${con.isDefault eq 1 }">checked="checked"</c:if>>
		                    	</c:if>
		                    </td>
		                    <td>
		                    	<c:if test="${con.isOnJob eq 1 }">在职</c:if>
		                    	<c:if test="${con.isOnJob eq 0 }">离职</c:if>
		                    </td>
							<td>
								<c:if test="${con.isVedengMember gt -1}">已注册</c:if>
								<c:if test="${con.isVedengMember eq -1}">未注册</c:if>
							</td>
							<td>
                                <c:if test="${con.isVedengMember eq 1}">会员</c:if>
                                <c:if test="${con.isVedengMember ne 1}">非会员</c:if>
							</td>
		                    <td class="caozuo">
		                    	<c:if test="${customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
		                        <span class="caozuo-blue editclick" 
		                        	onclick="goUrl('${pageContext.request.contextPath}/trader/customer/toEditContactPage.do?traderContactId=${con.traderContactId}&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}');">编辑</span>
		                        <c:choose>
									<c:when test="${con.isEnable eq 0}">
										<span class="caozuo-blue " onclick="setDisabled(${con.traderContactId},1,0);">启用</span>
									</c:when>
									<c:when test="${con.isEnable eq 1}">
										<span class="caozuo-red " onclick="setDisabled(${con.traderContactId},0,0);">禁用</span>
									</c:when>
								</c:choose>
		                        <span class="caozuo-red pop-new-data" style="cursor: pointer;" layerParams='{"width":"850px","height":"500px","title":"转移联系人",
		                        	"link":"./toTransferContactPage.do?traderContactId=${con.traderContactId}&traderType=${con.traderType}&name=${con.name}&sex=${con.sex}&department=${con.department}&mobile=${con.mobile}&position=${con.position}&isOnJob=${con.isOnJob}&traderId=${traderCustomer.traderId}"}'>转移</span>
		                      	</c:if>
		                    </td>
		                </tr>
	                </c:forEach>
                </c:if>
                <c:if test="${empty contactList}">
                    <tr>
                        <td colspan="13">暂无记录</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
    <div class="parts">
        <div class="title-container">
            <div class="table-title ">
                地址信息
            </div>
            <c:if test="${customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
            <div class="title-click pop-new-data" layerParams='{"width":"800px","height":"300px","title":"新增地址","link":"./toAddAddressPage.do?traderId=${traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"}'>
                新增
            </div>
            </c:if>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>地区</th>
                    <th>地址</th>
                    <th>邮编</th>
                    <th>默认地址</th>
                    <th>备注</th>
                    <th class="wid15">操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not empty addressList}">
					<c:forEach items="${addressList}" var="vo" varStatus="status">
		                <tr>
		                    <td>${vo.area}</td>
		                    <td>${vo.traderAddress.address}</td>
		                    <td>
		                    	${vo.traderAddress.zipCode}
		                    </td>
		                    <td>
		                    <c:if test="${vo.traderAddress.isEnable eq 1}">
		                        <input type="radio" onclick="setDefault(${vo.traderAddress.traderAddressId},${vo.traderAddress.isDefault},${vo.traderAddress.traderId},1,${vo.traderAddress.areaId},${vo.traderAddress.areaIds});" 
		                        		<c:if test="${vo.traderAddress.isDefault eq 1}">checked="checked"</c:if>>
		                       	<input type="hidden" value="${vo.traderAddress.areaIds}">
		                       	</c:if>
		                    </td>
		                    <td>${vo.traderAddress.comments}</td>
		                    <td class="caozuo">
		                    	<c:if test="${customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
		                        <span style="cursor: pointer;" class="caozuo-blue editclick trasfer" layerParams='{"width":"800px","height":"300px","title":"操作确认",
		                        "link":"./toEditAddressPage.do?traderAddressId=${vo.traderAddress.traderAddressId}"}'>编辑</span>
		                         <c:choose>
									<c:when test="${vo.traderAddress.isEnable eq 0}">
										<span class="caozuo-blue " onclick="setDisabled(${vo.traderAddress.traderAddressId},1,1);">启用</span>
									</c:when>
									<c:when test="${vo.traderAddress.isEnable eq 1}">
										<span class="caozuo-red " onclick="setDisabled(${vo.traderAddress.traderAddressId},0,1);">禁用</span>
									</c:when>
								</c:choose>
								</c:if>
		                    </td>
		                </tr>
	                </c:forEach>
                </c:if>
                <c:if test="${empty addressList}">
                    <tr>
                        <td colspan="6">暂无记录</td>
                    </tr>
                </c:if>
         
             <table class="table table-bordered table-striped table-condensed table-centered">
					<div class="table-title nobor">客户添加的地址</div>
					<thead>
                <tr>
                    <th>收件人</th>
                    <th>手机号码</th>
                    <th>所在地区</th>
                    <th>详细地址</th>
                    <th>标签名称</th>
                    <th>默认地址</th>
                    <th>添加人</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not empty mjxlist}">
					<c:forEach items="${mjxlist}" var="vo" varStatus="status">
		                <tr>
		                    <td>${vo.deliveryName}</td>
		                    <td>${vo.telphone}</td>
		                    <td>${vo.area}</td>
		                    <td>${vo.address}</td>
		                    <td>${vo.titleName}</td>
		                    <td>${vo.defaultAddress}</td>
		                    <td>${vo.creator}</td>
		                </tr>
		                    </c:forEach>
		             </c:if>       
		        </tbody>            
             </table>
            </tbody>
        </table>
    </div>
    <!-- 
    <div class="ml10 pb50">
        友情提醒：
        <br/> 1、如果该客户同时也是供应商，则显示供应商相关数据，否则供应信息为空；采购次数和采购金额指我司向其采购订单的次数和金额。
    </div> -->
</div>
</body>


</html>
