<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品出库" scope="application" /> 
<%@ include file="../../common/common.jsp"%>
<%--<style>
	.second select {
		width:169px;
		height:106px;
		margin:0px;
		margin-top:60px;
		outline:none;
		border:1px solid #999;
		margin-top:31px;
	}
	.second input {
		width:167px;
		top:6px;
		outline:none;
		border:0pt;
		position:absolute;
		line-height:30px;
		left:8px;
		height:30px;
		border:1px solid #999;
	}
	.second ul {
		position:absolute;
		top:27px;
		border:1px solid #999;
		left:8px;
		width:125px;
		line-height:16px;
	}
	.ul li {
		list-style:none;
		width:161px;
		/* left:15px;
        */
		margin-left:-40px;
		font-family:微软雅黑;
		padding-left:4px;
	}
	.blue {
		background:#1e91ff;
	}
</style>
<script>
    var TempArr = []; //存储option
    $(function() {
        /*先将数据存入数组*/
        $("#orgId option").each(function(index, el) {
            TempArr[index] = $(this).text();
        });
        $(document).bind('click',
            function(e) {
                var e = e || window.event; //浏览器兼容性
                var elem = e.target || e.srcElement;
                while (elem) { //循环判断至跟节点，防止点击的是div子元素
                    if (elem.id && (elem.id == 'orgId' || elem.id == "makeupCo")) {
                        return;
                    }
                    elem = elem.parentNode;
                }
                $('#orgId').css('display', 'none'); //点击的不是div或其子元素
            });
    })

    function changeF(this_) {
        $(this_).prev("input").val($(this_).find("option:selected").text());
        $("#orgId").css({
            "display": "none"
        });
    }
    function setfocus(this_) {
        $("#orgId").css({
            "display": ""
        });
        var select = $("#orgId");
        for (i = 0; i < TempArr.length; i++) {
            var option = $("<option></option>").text(TempArr[i]);
            select.append(option);
        }
    }

    function setinput(this_) {
        var select = $("#orgId");
        select.html("");
        for (i = 0; i < TempArr.length; i++) {
            //若找到以txt的内容开头的，添option
            if (TempArr[i].substring(0, this_.value.length).indexOf(this_.value) == 0) {
                var option = $("<option></option>").text(TempArr[i]);
                select.append(option);
            }
        }
    }

</script>--%>

<div class="customer">
    <ul>
        <li>
            <a href="/warehouse/warehousesout/index.do" class="customer-color">销售出库</a>
        </li>
        <li>
            <a href="/warehouse/businessWarehouseOut/changeIndex.do">采购售后出库</a>
        </li>
        <li>
            <a href="/warehouse/businessWarehouseOut/index.do">销售售后出库</a>
        </li>
        <li>
            <a href="/warehouse/warehousesout/lendOutIndex.do">商品外借出库</a>
        </li>
    </ul>
</div>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
	<div class="content logistics-warehousein-index"> 	
		<div class="searchfunc">
			<form method="post" id="search" action="<%= basePath %>/warehouse/warehousesout/index.do">
				<ul>
					<li>
						<label class="infor_name">销售单号</label>
						<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorder.saleorderNo}"/>
					</li>
					<li>
						<label class="infor_name">客户名称</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${saleorder.traderName}"/>
					</li>
					<li>
						<label class="infor_name">产品名称</label>
						<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${saleorder.goodsName}"/>
					</li>
					<li>
						<label class="infor_name">订货号</label>
						<input type="text" class="input-middle" name="sku" id="sku" value="${saleorder.sku}"/>
					</li>
					<li>
						<label class="infor_name">品牌</label>
						<input type="text" class="input-middle" name="brandName" id="brandName" value="${saleorder.brandName}"/>
					</li>
					<li>
						<label class="infor_name">型号</label>
						<input type="text" class="input-middle" name="model" id="model" value="${saleorder.model}"/>
					</li>
					<li>
						<label class="infor_name">物料编码</label>
						<input type="text" class="input-middle" name="materialCode" id="materialCode" value="${saleorder.materialCode}"/>
					</li>

					<li>
                        <label class="infor_name">销售部门</label>
                        <select name="orgId" id="orgId"  class="input-middle">
                            <option value="">全部</option>
                            <c:forEach items="${orgList}" var="org" >
                                <option value="${org.orgId}" <c:if test="${saleorder.orgId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
                            </c:forEach>
                        </select>
					</li>

                    <li>
                        <label class="infor_name">发货状态</label>
                        <select class="input-middle" name="deliveryStatus" id="deliveryStatus">
                            <option value="">全部</option>
                            <option value="0" <c:if test="${saleorder.deliveryStatus eq 0}">selected="selected"</c:if>>未发货</option>
                            <option value="1" <c:if test="${saleorder.deliveryStatus eq 1}">selected="selected"</c:if>>部分发货</option>
							<option value="2" <c:if test="${saleorder.deliveryStatus eq 2}">selected="selected"</c:if>>全部发货</option>
                        </select>
                    </li>
                    <li>
                        <label class="infor_name">可发货时间</label>
                        <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchBeginTime" value="${searchBeginTime}">
                        <div class="gang">-</div>
                        <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchEndTime" value="${searchEndTime}">
                    </li>
					<li>
						<label class="infor_name">发货方式</label>
						<select class="input-middle" name=deliveryType id="deliveryType">
							<option value="">全部</option>
							<c:forEach items="${deliveryTypes}" var="list">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.deliveryType == list.sysOptionDefinitionId}">selected="selected"</c:if>>${list.title}</option>
							</c:forEach>
						</select>
					</li>
                    <li>
                        <label class="infor_name">库存允许出库</label>
                        <select class="input-middle" name="outIsFlag" id="outIsPage">
                            <option value="">全部</option>
                            <option value="2" <c:if test="${saleorder.outIsFlag==2}">selected="selected"</c:if>>是</option>
                            <option value="1" <c:if test="${saleorder.outIsFlag==1}">selected="selected"</c:if>>否</option>
                        </select>
                    </li>
					<input type="hidden" name="flag" id="flag" value="1"/>
					<li>
						<label class="infor_name">付款时间</label>
						<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchPaymentBeginTimeStr" value="<date:date value ="${searchPaymentBeginTime}" format="yyyy-MM-dd"/>">
						<div class="gang">-</div>
						<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchPaymentEndTimeStr" value="<date:date value ="${searchPaymentEndTime}" format="yyyy-MM-dd"/>">
					</li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				</div>
			</form>
		</div>
		<input id="Ids" type="hidden" value="${Ids}"/>
		<c:if test="${saleorder.flag eq 0}">
		<div class="tablelastline" style='margin-top:0px;'>
                        当前待出库订单数：<span class="warning-color1">${page.totalRecord}</span>
        </div>
        </c:if>
          <div class="table-style5">
          <c:forEach var="list" items="${saleorderList}" varStatus="num">
            <table class="table">
                <thead>
                    <tr>
                       <th class="wid5">序号</th>
                        <th class="wid20">订单号</th>
						<th class="wid15">付款时间</th>
						<th class="wid15">可发货时间</th>
						<th class="">客户名称</th>
						<th class="">销售部门</th>
						<th class="">销售人员</th>
						<!-- <th class="">订单总额</th>
						<th class="">已付款金额</th> -->
						<th class="">发货状态</th>
                        <th class="">是否可以出库</th>
						<th class="">操作人</th>
						<th class="wid12">操作</th>
                    </tr>
                </thead>
                <tbody id="${list.saleorderId}">
                    <tr>
                        <td>${num.count}</td>
                    	<td id="${list.saleorderId}_flag"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>
                    	${list.saleorderNo}</a>
                    	<c:if test="${list.show eq 0}">
                    	</c:if>
                    	</td>
                        <td><date:date value ="${list.paymentTime}"/></td>
                        <td><date:date value ="${list.satisfyDeliveryTime}"/></td>
                        <td>
						<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
						</td>
                        <td>${list.salesDeptName}</td>
                        <td>${list.salesName}</td>
                       <%--  <td>${list.totalAmount}</td>
                        <td>${list.accountPayable+list.paymentAmount}</td> --%>
                          <c:choose>
							<c:when test="${list.deliveryStatus eq 0}">
								<td class="warning-color1">未发货</td>
							</c:when>
							<c:when test="${list.deliveryStatus eq 1}">
								<td class="warning-color1">部分发货</td>
							</c:when>
							<c:when test="${list.deliveryStatus eq 2}">
								<td>全部发货</td>
							</c:when>
							<c:otherwise>
							<td></td>
							</c:otherwise>
						  </c:choose>
                        <c:if test="${list.outIsFlag==1}">
                            <td>否</td>
                        </c:if>
                        <c:if test="${list.outIsFlag==2}">
                            <td>是</td>
                        </c:if>
                        <td>${list.optor}</td>
                         <td  class="begin-enter-lib caozuo">
                                               <span class="bt-smaller bt-border-style border-blue addtitle"
					tabTitle='{"num":"warehousesout_index_${list.saleorderId}","link":"./warehouse/warehousesout/detailJump.do?saleorderId=${list.saleorderId}","title":"出库详情"}'>查看详情</span>
                                            </td>
                    </tr>
                    <tr>
                        <td colspan="11" class="tdpadding">
                            <table class="table" >
                                    <tbody id="${list.saleorderId}_goods">
                                        <tr>
                                        <th class="wid25">产品名称</th>
                                        <th class=" ">订货号</th>
                                        <th class=" wid10">品牌</th>
                                        <th class=" wid10">型号</th>
                                        <th class=" wid15">物料编码</th>
                                        <th class=" ">单位</th>
                                        <th class=" ">总数</th>
                                        <th class=" ">未出库数量</th>
                                        <th class="">库存量</th>
                                        <th>状态</th>
                                        <th class="wid20">存储位置</th>
                                        </tr>
                                   <%--    <c:forEach var="saleorderGoods" items="${list.goodsList}">
	                                    <tr>
	                                        <td class="text-left">
						                        <div >
						                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${saleorderGoods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${saleorderGoods.goodsId}","title":"产品信息"}'>${saleorderGoods.goodsName}</a>
						                        </div>
						                        <div>${saleorderGoods.sku}</div>
						                    </td>
											<td>${saleorderGoods.brandName}</td>
											<td>${saleorderGoods.model}</td>
											<td>${saleorderGoods.materialCode}</td>
											<td>
												${saleorderGoods.unitName}
											</td>
											<td>${saleorderGoods.num}</td>
											<td>${saleorderGoods.num -saleorderGoods.deliveryNum }</td>
											   <c:choose>
												<c:when test="${saleorderGoods.deliveryStatus eq 0}">
													<td class="warning-color1">未发货</td>
												</c:when>
												<c:when test="${saleorderGoods.deliveryStatus eq 1}">
													<td class="warning-color1">部分发货</td>
												</c:when>
												<c:when test="${saleorderGoods.deliveryStatus eq 2}">
													<td>全部发货</td>
												</c:when>
												<c:otherwise>
												<td></td>
												</c:otherwise>
											  </c:choose>
											<td>
											  <c:forEach items="${saleorderGoods.whList}" var="addr" begin="0" 
												  end="${fn:length(saleorderGoods.whList)}" varStatus="stat">
												${addr}<br/>
												</c:forEach>
											</td>
	                                    </tr>
                                    </c:forEach> --%>
                                    </tbody>
                             </table>
                             
                        </td>
                    </tr>
                    <%--  <c:if test="${empty list.goodsList}">
	                    <tr>
	                        <td colspan="12">暂无商品记录</td>
	                    </tr>
                    </c:if> --%>
                </tbody>
            </table>
             </c:forEach>
             
			<c:if test="${empty saleorderList}">
				<!-- 查询无结果弹出 -->
				 <table class="table">
				 	<tbody>
						<tr>
                        	<td colspan="10">查询无结果！请尝试使用其他搜索条件。</td>
                    	</tr>
                     </tbody>
           		</table>
			  </c:if>
          <div class="mt-5">
           	<tags:page page="${page}" />
          </div>
        </div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/index_warehouseOut.js?rnd=<%=Math.random()%>'></script>


<%@ include file="../../common/footer.jsp"%>
