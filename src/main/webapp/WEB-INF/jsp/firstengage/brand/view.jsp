<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>品牌详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/first/view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <div class="detail-title">品牌详情：
            <c:if test="${brand.brandNature eq 1}">${brand.brandName}</c:if>
            <c:if test="${brand.brandNature eq 2}">${brand.brandNameEn}</c:if>
        </div>
        <div class="detail-option-wrap">
            <div class="option-btns">
                <a class="btn btn-small btn-blue" href="./addBrand.do?brandId=${brand.brandId}">
                    编辑
                </a>
                <a class="btn btn-small J-one-del" data-id="${brand.brandId}">删除</a>
            </div>
        </div>
        <div class="detail-block">
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">商品品牌：</div>
                    <div class="table-td">
                        <c:if test="${brand.brandNature eq 1}">国产品牌</c:if>
                        <c:if test="${brand.brandNature eq 2}">进口品牌</c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">品牌名称（中文名）：</div>
                    <div class="table-td">
                        ${brand.brandName}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">品牌名称（英文名）：</div>
                    <div class="table-td">
                        ${brand.brandNameEn}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">生产企业：</div>
                    <div class="table-td">
                        <c:if test="${empty manufacturer}">- -</c:if>
                        <c:if test="${not empty manufacturer}">
                            <c:forEach items="${manufacturer}" var="list">
                                ${list.label}；
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
<div class="table-item">
<div class="table-th">公司名称：</div>
<div class="table-td">
${brand.companyName}
</div>
</div>
                <div class="table-item">
                    <div class="table-th">品牌网址：</div>
                    <div class="table-td">
                        ${brand.brandWebsite}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">品牌Logo：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <div class="info-pic-item J-show-big" data-src="http://${brand.logoDomain}${brand.logoUri}">
                                <img src="http://${brand.logoDomain}${brand.logoUri}" alt="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">授权证明：</div>
                    <div class="table-td">
                        <c:if test="${empty brand.proof}">
                            - -
                        </c:if>
                        <c:if test="${not empty brand.proof}">
                            <div class="info-pic">
                                <c:forEach var="proof" items="${brand.proof}">
                                    <div class="info-pic-item J-show-big" data-src="http://${proof.domain}${proof.uri}">
                                        <img src="http://${proof.domain}${proof.uri}">
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">品牌说明：</div>
                    <div class="table-td">${brand.description}</div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/tmpl" class="J-del-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i>确认删除该品牌？
            </div>
            <form class="J-del-form">
                <div class="del-cnt base-form">
                    <textarea name="content" id="" cols="30" rows="10" class="input-textarea" placeholder="必填：请填写删除理由，最少10个字，最多300个字"></textarea>
                </div>
            </form>
        </div>
    </script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/brand/view.js?rnd=<%=Math.random()%>"></script>
</body>