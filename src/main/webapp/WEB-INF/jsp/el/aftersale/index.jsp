<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="SPD售后单意向" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
    <br>
    <div class="searchfunc">
        <form action="${pageContext.request.contextPath}/el/trader/aftersale/index.do" method="post" id="search">
            <ul>
                <li>
                    <label class="infor_name">是否处理</label>
                    <select id="status" name="status" style="width:178px">
                        <option value="">--请选择--</option>
                        <option value="0" <c:if test="${status == '0'}">selected</c:if>>未处理</option>
                        <option value="1" <c:if test="${status == '1'}">selected</c:if>>已处理</option>
                    </select>
                </li>
                <li>
                    <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
                </li>
            </ul>
            <div class="tcenter">

            </div>
        </form>
    </div>

    <div  class="normal-list-page">

        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
            <tr>
                <th class="sorts">序号</th>
                <th class="wid15">客户名称</th>
                <th class="wid10">订单号</th>
                <th class="wid10">退货明细</th>
                <th class="wid10">添加时间</th>
                <th class="wid10">更新时间</th>
                <th class="wid10">审批结果</th>
                <th style="width:5%;">操作</th>
            </tr>
            </thead>
            <tbody class="company">
            <c:if test="${not empty intentions}">
                <c:forEach items="${intentions}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>
                                ${item.traderName}
                            <input type="hidden" value="${item.elAfterSaleIntentionId}" />
                        </td>
                        <td>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${item.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${item.saleorderId}","title":"订单信息"}'>
                                    ${item.saleorderNo}
                            </a>
                        </td>
                        <td>
<%--                            <a href="javascript:void(0);"class="intention_detail" tabTitle='{"link":"./el/trader/aftersale/intention/detail?detailId=${item.elAfterSaleIntentionId}"}'>查看退货明细</a>--%>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"link":"./el/trader/aftersale/intention/detail.do?detailId=${item.elAfterSaleIntentionId}", "title":"退货明细"}'>
                                查看退货明细
                            </a>
                        </td>
                        <td>
                            <date:date value="${item.addTime} " />
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.updateTime == null}">
                                    -
                                </c:when>
                                <c:otherwise>
                                    <date:date value="${item.updateTime} " />
                                </c:otherwise>
                            </c:choose>
                        </td>
                         <td>
                             <c:choose>
                                 <c:when test="${item.status eq 0}">-</c:when>
                                 <c:when test="${item.status eq -1}">不同意</c:when>
                                 <c:when test="${item.status eq 1}">已同意</c:when>
                             </c:choose>
                         </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.status eq 0}">
                                    <span class="edit-user" onclick="update(${item.elAfterSaleIntentionId},1);">同意</span>
                                    <span class="delete" onclick="update(${item.elAfterSaleIntentionId},-1);">不同意</span>
                                </c:when>
                                <c:otherwise>
                                    已处理
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <c:if test="${empty intentions}">
            <div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        </c:if>
        <tags:page page="${page}"/>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/posit/index.js?rnd=<%=Math.random()%>"></script>
<script>

    function update(elAfterSaleIntentionId, approval){
        checkLogin();
        if(elAfterSaleIntentionId > 0){
            layer.confirm(approval === 1 ? "您是否确认同意该售后申请？" : "您是否确认驳回该售后申请？", {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type: "POST",
                    url: "./updateIntention.do",
                    data: {
                        'elAfterSaleIntentionId':elAfterSaleIntentionId,
                        'status':approval,
                    },
                    dataType:'json',
                    success: function(data){
                        refreshPageList(data);//刷新父页面列表数据
                    },
                    error:function(data){
                        if(data.status ===1001){
                            layer.alert("当前操作无权限")
                        }
                    }
                });
            }, function(){
            });
        }
    }

</script>
<%@ include file="../../common/footer.jsp"%>