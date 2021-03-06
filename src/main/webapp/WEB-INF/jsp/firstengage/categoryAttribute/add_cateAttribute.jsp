<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>新增属性</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/department/add.css?rnd=<%=Math.random()%>">
</head>

<body>
    <form action="./saveBrand.do" id="form_submit" class="J-form" method="POST">
        <input type="hidden" name="formToken" value="${formToken}" />
        <div class="form-wrap">
            <div class="form-container base-form form-span-8">
                <div class="form-title">新增属性</div>
                <!-- 后台报错的区域 -->
                <div class="vd-tip tip-red">
                    <i class="vd-tip-icon vd-icon icon-error2"></i>
                    <div class="vd-tip-cnt">有错误发生</div>
                </div>
                <!-- end -->
                <div class="form-block">
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>属性名称：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" name="brandName" valid-max="100" class="input-text" value="">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">单位：</div>
                        <div class="form-fields">
                            <div class="input-checkbox">
                                <label class="input-wrap">
                                    <input type="checkbox">
                                    <span class="input-ctnr"></span>设置单位
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>排序 属性值：</div>
                        <div class="form-fields">
                            <div class="attr-wrap">
                                div.attr
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-btn">
                    <div class="form-fields">
                        <button type="submit" class="btn btn-blue btn-large">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/department/add.js?rnd=<%=Math.random()%>"></script>
</body>