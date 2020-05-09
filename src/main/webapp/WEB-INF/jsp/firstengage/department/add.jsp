<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>
        <c:if test="${null ne departmentsHospital.departmentId }">
            编辑科室
        </c:if>
        <c:if test="${null eq departmentsHospital.departmentId }">
            新增科室
        </c:if>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/department/add.css?rnd=<%=Math.random()%>">
</head>

<body>
    <form action="./adddepartmentinfo.do" id="form_submit" class="J-form" method="POST">
        <input type="hidden" name="formToken" value="${formToken}" />
        <input type="hidden" name="departmentId" value="${departmentsHospital.departmentId}">

        <div class="form-wrap">
            <div class="form-container base-form form-span-8">
                <c:if test="${null ne departmentsHospital.departmentId }">
                    <div class="form-title">编辑科室</div>
                </c:if>
                <c:if test="${null eq departmentsHospital.departmentId }">
                    <div class="form-title">新增科室</div>
                </c:if>

                <!-- 后台报错的区域 -->
                <div class="vd-tip tip-red" <c:if test="${null eq error}">style="display: none;"</c:if> >
                    <i class="vd-tip-icon vd-icon icon-error2"></i>
                    <div class="vd-tip-cnt">${error}</div>
                </div>
                <!-- end -->
                <div class="form-block">
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>科室名称：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" name="departmentName" valid-max="50" class="input-text" value="${departmentsHospital.departmentName}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>收费项目：</div>
                        <div class="form-fields">
                            <div class="linkage-list J-select-list">

                                <c:if test="${empty departmentsHospital.departmentFeeItems}">
                                    <div class="linkage-wrap J-select-wrap">
                                        <div class="linkage-item">
                                            <select name="feeOne" cz-value="" class="J-select-lv1"></select>
                                        </div>
                                        <div class="linkage-item J-lv2-wrap" style="display: none;">
                                            <select name="feeTwo" cz-value="" class="J-select-lv2"></select>
                                        </div>
                                        <div class="linkage-item J-lv3-wrap" style="display: none;">
                                            <input name="feeThree" type="hidden" cz-value="" class="J-select-lv3">
                                        </div>
                                        <div class="del-wrap J-select-del" style="display: none;">
                                            <i class="vd-icon icon-recycle"></i>
                                        </div>
                                    </div>
                                </c:if>


                                <c:if test="${not empty departmentsHospital.departmentFeeItems}">
                                    <c:forEach var="feeItem" items="${departmentsHospital.departmentFeeItems}">

                                        <c:if test="${empty feeItem.departmentFeeItemsList}">
                                            <div class="linkage-wrap J-select-wrap">
                                                <div class="linkage-item">
                                                    <select name="feeOne" cz-value="${feeItem.departmentFeeItemsId}" class="J-select-lv1"></select>
                                                </div>
                                                <div class="linkage-item J-lv2-wrap" style="display: none;">
                                                    <select name="feeTwo" cz-value="" class="J-select-lv2"></select>
                                                </div>
                                                <div class="linkage-item J-lv3-wrap" style="display: none;">
                                                    <input name="feeThree" type="hidden" cz-value="" class="J-select-lv3">
                                                </div>
                                                <div class="del-wrap J-select-del" style="display: none;">
                                                    <i class="vd-icon icon-recycle"></i>
                                                </div>
                                            </div>
                                        </c:if>

                                        <%-- 二级收费项目不为空 --%>
                                        <c:if test="${not empty feeItem.departmentFeeItemsList}">
                                            <c:forEach var="two" items="${feeItem.departmentFeeItemsList}">
                                                <div class="linkage-wrap J-select-wrap">
                                                    <div class="linkage-item">
                                                        <select name="feeOne" cz-value="${feeItem.departmentFeeItemsId}" class="J-select-lv1"></select>
                                                    </div>
                                                    <div class="linkage-item J-lv2-wrap" style="display: none;">
                                                        <select name="feeTwo" cz-value="${two.departmentFeeItemsId}" class="J-select-lv2"></select>
                                                    </div>
                                                    <div class="linkage-item J-lv3-wrap" style="display: none;">
                                                        <input name="feeThree" type="hidden" <c:if test="${null eq two.feeItemsNameThree}">value=""</c:if><c:if test="${null ne two.feeItemsNameThree}">cz-value="${two.feeItemsNameThree}"</c:if> class="J-select-lv3">
                                                    </div>
                                                    <div class="del-wrap J-select-del" style="display: none;">
                                                        <i class="vd-icon icon-recycle"></i>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:if>





                                    </c:forEach>
                                </c:if>



                            </div>
                            <div class="linkage-add J-select-add">
                                <i class="vd-icon icon-add"></i>
                                <span class="add-txt">新增收费项目</span>
                            </div>
                            <div class="feedback-block" wrapfor="feeOne"></div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">科室说明：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <textarea name="description" valid-max="500" class="input-textarea">${departmentsHospital.description}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-btn">
                    <div class="form-fields">
                        <button type="submit" class="btn btn-blue btn-large">保存</button>
                        <c:if test="${null ne departmentsHospital.departmentId }">
                            <a class="btn btn-large" href="./getDepartmentsHospitalInfo.do?departmentsHospitalId=${departmentsHospital.departmentId}">取消</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <script type="text/json" class="J-fee-json">
        ${feeItems}
    </script>
    <script type="text/tmpl" class="J-select-tmpl">
        <div class="linkage-wrap J-select-wrap">
            <div class="linkage-item">
            <select name="feeOne" value="" class="J-select-lv1"></select>
            </div>
            <div class="linkage-item J-lv2-wrap" style="display: none;">
            <select name="feeTwo" value="" class="J-select-lv2"></select>
            </div>
            <div class="linkage-item J-lv3-wrap" style="display: none;">
            <input type="hidden" name="feeThree" value="" class="J-select-lv3">
            </div>
            <div class="del-wrap J-select-del" style="display: none;">
            <i class="vd-icon icon-recycle"></i>
            </div>
        </div>
    </script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/department/add.js?rnd=<%=Math.random()%>"></script>
</body>