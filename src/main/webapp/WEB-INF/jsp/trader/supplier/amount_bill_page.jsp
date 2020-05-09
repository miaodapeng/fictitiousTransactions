<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
	function applyBill(traderId){
		var searchUrl = page_url+"/trader/supplier/accountperiodapply.do?traderId="+traderId;
		$("#popBill",window.parent.document).attr('layerParams','{"width":"500px","height":"580px","title":"申请帐期","link":"'+searchUrl+'"}');
		$("#popBill",window.parent.document).click();
	}
</script>
<div class="parts">
        <div class="title-container">
            <div class="table-title nobor">帐期记录</div>
            <c:if test="${trader.isEnable == 1 }">
	            <div class="title-click nobor " onclick="applyBill(${trader.traderId})">
	                申请帐期
	            </div>
            </c:if>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                	<th class="table-smaller">操作人</th>
                    <th class="table-smallest">操作日期</th>
                    <th class="table-smallest">操作事项</th>
                    <!-- 
                    <th class="table-smaller">操作内容</th> -->
                    <th>备注</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not empty billList }">
            		<c:forEach items="${billList }" var="bill">
                  <tr>
                  	<td>${bill.creatorUser }</td>
                      <td><date:date value ="${bill.addTime} "/></td>
                      <td>${bill.opEvent }</td>
                      <!--  
                      <td>${bill.content }</td>-->
                      <td>${bill.comments }</td>
                  </tr>
            		</c:forEach>
            	</c:if>
            </tbody>
        </table>
        <c:if test="${empty billList }">
<!-- 查询无结果弹出 -->
       		<div class="noresult">查询无结果！</div>
		</c:if>
    </div>
    <tags:page page="${page}"/>
<%@ include file="../../common/footer.jsp"%>