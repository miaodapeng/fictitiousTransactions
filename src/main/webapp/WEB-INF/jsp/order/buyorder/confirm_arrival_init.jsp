<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认收货" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content mt10 ">
		<form method="post" id="addBhSaleorderForm" action="./confirmArrival.do">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">产品信息</div>
            </div>
            <c:set var="num1" value="0"></c:set>
			<c:set var="id_str" value=""></c:set>
            <c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
		 
	            <table class="table table-style7">
	                <thead>
	                    <tr>
	                    	<th class="wid4">序号</th>
	                        <th class="wid19">产品名称</th>
							<th class="wid8">品牌</th>
							<th class="wid8">型号</th>
							<th class="wid9">物料编码</th>
							<th class="wid8">采购数量</th>
							<th class="wid6">单位</th>
							<th class="wid10">单价</th>
							<th class="wid8">总额</th>
							<th class="wid20">采购备注</th>
							<th class="wid10">到货状态</th>
	                    </tr>
	                </thead>
	                <tbody>
						<c:if test="${bgv.isDelete eq 0}">
						<c:set var="num1" value="${num1+1}"></c:set>
                		<c:set var="id_str" value="${id_str}_${bgv.buyorderGoodsId}"></c:set>
						
	                    <tr>
	                    	<td>${num1}<input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}"/></td>
	                    	<td class="text-left">
			                    <div class="customername pos_rel">
		                    		<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${bgv.goodsName}
		                    				&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span> 
                    				<div class="pos_abs customernameshow">
									物料编码： ${bgv.materialCode}<br> 
									注册证号： ${bgv.registrationNumber}<br>
									管理类别：  ${bgv.manageCategoryName}<br>
									产品归属：<c:if test="${not empty bgv.userList }">
												<c:forEach items="${bgv.userList }" var="user"
													varStatus="st">
											${user.username } <c:if
														test="${st.count != bgv.userList.size() }">、</c:if>
												</c:forEach>
											</c:if>  <br> 
									库存：${bgv.goodsStock}<br> 
									可用库存：${bgv.canUseGoodsStock > 0 ? bgv.canUseGoodsStock : 0}<br> 
									订单占用：${bgv.orderOccupy}<br>
									</div>
									<div>${bgv.sku}</div>
								</div>
	                        </td>
	                        <td>${bgv.brandName}</td>
	                        <td>${bgv.model}</td>
	                        <td>${bgv.materialCode}</td>
	                        <td><span alt="${bgv.goodsId}">${bgv.num-bgv.afterSaleUpLimitNum}</span>
	                        	<input type="hidden" name="buySum" alt="${bgv.buyorderGoodsId}" value="${bgv.buyorderGoodsId}|${bgv.num-bgv.afterSaleUpLimitNum}"/>
	                        </td>
	                        <td>${bgv.unitName}</td>
	                        <td>${bgv.price}</td>
	                        <td>
	                        	<span class="oneAllMoney" alt="${bgv.buyorderGoodsId}">${bgv.oneBuyorderGoodsAmount}</span>
	                        </td>
	                        <td>${bgv.insideComments}</td>
	                        <td>
	                        	<select style="width:100%;" name="arrivalStatus_${bgv.buyorderGoodsId}">
									<option <c:if test="${bgv.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
									<option <c:if test="${bgv.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
								</select>
	                        </td>
	                    </tr>
	                    
	                    </c:if>
	                </tbody>
	            </table>
	        </c:forEach>
        </div>
        
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="buyorderId" id="buyorderId" value="${buyorderVo.buyorderId}">
        	<input type="hidden" name="id_str" value="${id_str}">
            <button type="submit" id="apply_payment_submit">提交</button>
        </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>