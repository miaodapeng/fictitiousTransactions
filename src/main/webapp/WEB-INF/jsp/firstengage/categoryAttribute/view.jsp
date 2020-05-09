<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看属性</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/categoryAttribute/view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <div class="detail-title">属性：长度</div>
        <div class="detail-block">
            <div class="block-title">基本信息</div>
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">属性名称：</div>
                    <div class="table-td">长度</div>
                </div>
                <div class="table-item">
                    <div class="table-th">单位：</div>
                    <div class="table-td">设置单位</div>
                </div>
                <div class="table-item item-col">
                    <div class="table-th">排序 属性值：</div>
                    <div class="table-td">
                        <div>2cm</div>
                        <div>55ml</div>
                        <div>200张</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="detail-block">
            <div class="block-title">已引用商品分类</div>
            <div class="cate-list">
                <table class="table">
                    <colgroup>
                        <col>
                        <col>
                    </colgroup>
                    <tbody class="J-cnt"> 
                        <tr>
                            <th>序号</th>
                            <th>已引用商品分类</th>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>临床诊断-医用成像器械</td>
                        </tr>
                    </tbody>
                </table>
                <div class="pager J-pager"></div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/pager.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/categoryAttribute/view.js?rnd=<%=Math.random()%>"></script>
</body>