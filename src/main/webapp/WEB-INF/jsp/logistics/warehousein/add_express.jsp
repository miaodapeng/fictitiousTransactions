<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class="main-container logistics-warehousein-addWarehouseIn">
	  <div class="form-list  form-tips8">
        <form method="post" action="" id="">
            <ul>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>快递单号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	 <input type="text" class="input-middle" name="logisticsNo" id="logisticsNo"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>快递公司</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select name="logisticsId" id="logisticsId">
                            <c:forEach var="logistics" items="${logisticsList}">
                             	<option value="${logistics.logisticsId}">${logistics.name}</option>
                            </c:forEach>
                            </select>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>发货时间</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<input class="Wdate f_left input-smaller96 mr5" type="text" name="deliveryTimes" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value ="${nowTime}" format="yyyy-MM-dd"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>运费</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-small" name="amount" id="amount"/>
                        </div>
                    </div>
                </li>
                 <li>
                    <div class="form-tips">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-largest" name="logisticsComments" id="logisticsComments"/>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	产品信息
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">选择</th>
                        <th class="wid15">订货号</th>
                        <th>产品名称</th>
                        <th>品牌</th>
                        <th>型号</th>
                        <th>单价</th>
                        <th>发货数量</th>
                        <th>已发/总数</th>
                        <th class="wid8">单位</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="buyorderGoodsVo" items="${buyorderInfo.getBuyorderGoodsVoList()}">
                	 <tr>
                        <td>
                        <c:if test="${buyorderGoodsVo.num != expressNumMap[buyorderGoodsVo.buyorderGoodsId]}">
                            <input type="checkbox" name="b_checknox" value="${buyorderGoodsVo.buyorderGoodsId}"  onclick="canelAll(this)">
                        </c:if>
                        </td>
                        <td>${buyorderGoodsVo.sku}</td>
                        <td class="text-left">
                            <div class="brand-color1 addtitle" tabTitle='{"num":"viewgoods${buyorderGoodsVo.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${buyorderGoodsVo.goodsId}","title":"产品信息"}'>${buyorderGoodsVo.goodsName}</div>
                        </td>
                        <td>${buyorderGoodsVo.brandName}</td>
                        <td>${buyorderGoodsVo.model}</td>
                        <td>${buyorderGoodsVo.price}<input type="hidden" name="price" value="${buyorderGoodsVo.price}"/></td>
                        <td class="jianhuozongshu"><input name="num" type="text" value="${buyorderGoodsVo.num-expressNumMap[buyorderGoodsVo.buyorderGoodsId]}" onchange="checkNum(this,${buyorderGoodsVo.num},${expressNumMap[buyorderGoodsVo.buyorderGoodsId]},0)"></td>
                        <td class="jianhuozongshu">
                             <span>${expressNumMap[buyorderGoodsVo.buyorderGoodsId]}</span>
                             <span>/</span>
                             <span>${buyorderGoodsVo.num}</span>
                        </td>
                        <td>${buyorderGoodsVo.unitName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="table-style4">
                <div class="allchose">
                    <input type="checkbox" name="allcheck" onclick="selectall(this)">
                    <span>全选</span>
                </div>
            </div>
        </div>
            <div class="add-tijiao tcenter">
            	<input type="hidden" name="expressId" value="0"/>
            	<input type="hidden" name="formToken" value="${formToken}"/>
            	<input type="hidden" name="buyorderId" value="${buyorderInfo.buyorderId}"/>
                <button type="button" class="bg-light-green" onclick="addExpress('0')">提交</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/addExpress.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
