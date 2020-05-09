<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>首营信息详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/first/view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <div class="detail-title">首营信息：${firstEngage.registration.registrationNumber }</div>
        <div class="detail-option-wrap">
            <div class="option-btns">
                <c:if test="${firstEngage.status ne 1}">
                    <a href="./add.do?firstEngageId=${firstEngage.firstEngageId }" class="btn btn-blue btn-small">编辑</a>
                    <a class="btn btn-small J-del" data-id="${firstEngage.firstEngageId }">删除</a>

                    <c:if test="${firstEngage.registration.dealStatus eq 1}">
                        <a class="btn btn-small J-overday" data-id="${firstEngage.registrationNumberId }">过期处理</a>
                    </c:if>
                </c:if>

                <c:if test="${firstEngage.status eq 1}">
                    <c:if test="${firstEngage.checkAgain eq 0}">
                        <a class="btn btn-small J-audit" data-type="3" data-id="${firstEngage.firstEngageId }">审核通过</a>
                        <a class="btn btn-small J-audit" data-type="2" data-id="${firstEngage.firstEngageId }">审核不通过</a>
                    </c:if>
                    <c:if test="${firstEngage.checkAgain eq 1}">
                        <a class="btn btn-small J-audit" data-type="3" data-id="${firstEngage.firstEngageId }">重审通过</a>
                        <a class="btn btn-small J-audit" data-type="2" data-id="${firstEngage.firstEngageId }">重审不通过</a>
                    </c:if>
                </c:if>

                <c:if test="${firstEngage.status eq 3}">
                    <c:if test="${firstEngage.checkAgain eq 0}">
                        <a class="btn btn-small J-audit" data-type="2" data-id="${firstEngage.firstEngageId }">审核不通过</a>
                    </c:if>
                    <c:if test="${firstEngage.checkAgain eq 1}">
                        <a class="btn btn-small J-audit" data-type="2" data-id="${firstEngage.firstEngageId }">重审不通过</a>
                    </c:if>
                </c:if>

                <c:if test="${firstEngage.status eq 2}">
                    <c:if test="${firstEngage.checkAgain eq 0}">
                        <a class="btn btn-small J-audit" data-type="3" data-id="${firstEngage.firstEngageId }">审核通过</a>
                    </c:if>
                    <c:if test="${firstEngage.checkAgain eq 1}">
                        <a class="btn btn-small J-audit" data-type="3" data-id="${firstEngage.firstEngageId }">重审通过</a>
                    </c:if>
                </c:if>

            </div>
        </div>

        <c:if test="${not empty logCheckGenerateList}">
            <div class="detail-block">
                <div class="block-title">审核记录</div>
                <div class="status-title">
                <div class="status-item">更新时间</div>
                <div class="status-item">审核人</div>
                <div class="status-item">审核状态</div>
                <div class="status-item">原因</div>
            </div>

            <div class="status-list">
                <c:forEach items="${logCheckGenerateList}" var="log" varStatus="index">
                    <c:if test="${index.count==5}">
                        <div class="status-more J-optional-more">
                    </c:if>
                        <div class="status-cnt">
                            <div class="status-item">
                                <fmt:formatDate value="${log.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </div>
                            <div class="status-item">${log.creatorName}</div>
                            <div class="status-item">${log.logStatusName}</div>
                            <div class="status-item">${log.logMessage}</div>
                        </div>
                    <c:if test="${logCheckGenerateList.size()== index.count && index.count>=5 }">
                        </div>
                    </c:if>
                </c:forEach>

            <c:if test="${fn:length(logCheckGenerateList) gt 4}">
                </div>
                    <div class="detail-optional J-toggle-show">
                        <span class="toggle-txt J-more">展开更多<i class="vd-icon icon-down"></i></span>
                        <span class="toggle-txt J-less" style="display: none;">收起<i class="vd-icon icon-up"></i></span>
                    </div>
                </div>
            </c:if>

        </c:if>







        <div class="detail-block">
            <div class="block-title">注册证信息</div>
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">注册证号/备案凭证号：</div>
                    <div class="table-td">${firstEngage.registration.registrationNumber }</div>
                </div>
                <div class="table-item">
                    <div class="table-th">管理类别：</div>
                    <div class="table-td">
                        <c:choose>
                            <c:when test="${firstEngage.registration.manageCategoryLevel eq 968 }">
                                一类医疗器械
                            </c:when>
                            <c:when test="${firstEngage.registration.manageCategoryLevel eq 969 }">
                                二类医疗器械
                            </c:when>
                            <c:when test="${firstEngage.registration.manageCategoryLevel eq 970 }">
                                三类医疗器械
                            </c:when>
                            <c:otherwise>
                                - -
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">生产企业（中文注册人名称）：</div>
                    <div class="table-td">
                        ${firstEngage.registration.productCompany.productCompanyChineseName }
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">批准日期：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.issuingDateStr or '' eq firstEngage.registration.issuingDateStr}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.issuingDateStr and '' ne firstEngage.registration.issuingDateStr}">
                            ${firstEngage.registration.issuingDateStr }
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">生产企业（英文注册人名称）：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.productCompany.productCompanyEnglishName or '' eq firstEngage.registration.productCompany.productCompanyEnglishName}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.productCompany.productCompanyEnglishName and '' ne firstEngage.registration.productCompany.productCompanyEnglishName}">
                            ${firstEngage.registration.productCompany.productCompanyEnglishName }
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">有效期至：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.effectiveDateStr or '' eq firstEngage.registration.effectiveDateStr}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.effectiveDateStr and '' ne firstEngage.registration.effectiveDateStr}">
                            ${firstEngage.registration.effectiveDateStr}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">生产地址：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.productionAddress or '' eq firstEngage.registration.productionAddress}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.productionAddress and '' ne firstEngage.registration.productionAddress}">
                            ${firstEngage.registration.productionAddress}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">企业地址（注册人住所）：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.productCompany.productCompanyAddress or '' eq firstEngage.registration.productCompany.productCompanyAddress}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.productCompany.productCompanyAddress and '' ne firstEngage.registration.productCompany.productCompanyAddress}">
                            ${firstEngage.registration.productCompany.productCompanyAddress}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">产品名称（中文）：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.productChineseName or '' eq firstEngage.registration.productChineseName}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.productChineseName and '' ne firstEngage.registration.productChineseName}">
                            ${firstEngage.registration.productChineseName}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">产品名称（英文）：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.productEnglishName or '' eq firstEngage.registration.productEnglishName}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.productEnglishName and '' ne firstEngage.registration.productEnglishName}">
                            ${firstEngage.registration.productEnglishName}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">审批部门：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.approvalDepartment or '' eq firstEngage.registration.approvalDepartment}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.approvalDepartment and '' ne firstEngage.registration.approvalDepartment}">
                            ${firstEngage.registration.approvalDepartment}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">规格、型号：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.model or '' eq firstEngage.registration.model}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.model and '' ne firstEngage.registration.model}">
                            ${firstEngage.registration.model}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">代理人名称：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.registeredAgent or '' eq firstEngage.registration.registeredAgent}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.registeredAgent and '' ne firstEngage.registration.registeredAgent}">
                            ${firstEngage.registration.registeredAgent}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">代理人住所：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.registeredAgentAddress or '' eq firstEngage.registration.registeredAgentAddress}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.registeredAgentAddress and '' ne firstEngage.registration.registeredAgentAddress}">
                            ${firstEngage.registration.registeredAgentAddress}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">注册商标：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.trademark or '' eq firstEngage.registration.trademark}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.trademark and '' ne firstEngage.registration.trademark}">
                            ${firstEngage.registration.trademark}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">邮编：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.zipCode or '' eq firstEngage.registration.zipCode}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.zipCode and '' ne firstEngage.registration.zipCode}">
                            ${firstEngage.registration.zipCode}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">结构与组成：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.proPerfStruAndComp or '' eq firstEngage.registration.proPerfStruAndComp}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.proPerfStruAndComp and '' ne firstEngage.registration.proPerfStruAndComp}">
                            ${firstEngage.registration.proPerfStruAndComp}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">适用范围：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.productUseRange or '' eq firstEngage.registration.productUseRange}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.productUseRange and '' ne firstEngage.registration.productUseRange}">
                            ${firstEngage.registration.productUseRange}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">其他内容：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.otherContents or '' eq firstEngage.registration.otherContents}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.otherContents and '' ne firstEngage.registration.otherContents}">
                            ${firstEngage.registration.otherContents}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">备注：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.remarks or '' eq firstEngage.registration.remarks}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.remarks and '' ne firstEngage.registration.remarks}">
                            ${firstEngage.registration.remarks}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">产品标准：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.productStandards or '' eq firstEngage.registration.productStandards}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.productStandards and '' ne firstEngage.registration.productStandards}">
                            ${firstEngage.registration.productStandards}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">预期用途（体外诊断试剂）：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.expectedUsage or '' eq firstEngage.registration.expectedUsage}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.expectedUsage and '' ne firstEngage.registration.expectedUsage}">
                            ${firstEngage.registration.expectedUsage}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">主要组成成分（体外诊断试剂）：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.mainProPerfStruAndComp or '' eq firstEngage.registration.mainProPerfStruAndComp}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.mainProPerfStruAndComp and '' ne firstEngage.registration.mainProPerfStruAndComp}">
                            ${firstEngage.registration.mainProPerfStruAndComp}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">变更日期：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.changeDateStr or '' eq firstEngage.registration.changeDateStr}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.changeDateStr and '' ne firstEngage.registration.changeDateStr}">
                            ${firstEngage.registration.changeDateStr}
                        </c:if>
                    </div>
                </div>
                <div class="table-item item-col">
                    <div class="table-th">变更情况：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.registration.changeContents or '' eq firstEngage.registration.changeContents}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.registration.changeContents and '' ne firstEngage.registration.changeContents}">
                            ${firstEngage.registration.changeContents}
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="detail-block">
            <div class="block-title">首营信息</div>
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">商品类型：</div>
                    <div class="table-td">
                        <c:if test="${316 eq firstEngage.goodsType}">
                            器械设备
                        </c:if>
                        <c:if test="${317 eq firstEngage.goodsType}">
                            耗材
                        </c:if>
                        <c:if test="${318 eq firstEngage.goodsType}">
                            试剂
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">产品有效期限：</div>
                    <div class="table-td">
                        <c:if test="${316 eq firstEngage.goodsType}">
                            - -
                        </c:if>
                        <c:if test="${316 ne firstEngage.goodsType}">
                            <c:if test="${null eq firstEngage.effectiveDays or '' eq firstEngage.effectiveDays}">
                                0
                            </c:if>
                            <c:if test="${null ne firstEngage.effectiveDays and '' ne firstEngage.effectiveDays}">
                                ${firstEngage.effectiveDays}
                            </c:if>

                            <c:choose>
                                <c:when test="${null ne firstEngage.effectiveDayUnit and 2 eq firstEngage.effectiveDayUnit}">
                                    月
                                </c:when>
                                <c:when test="${null ne firstEngage.effectiveDayUnit and 3 eq firstEngage.effectiveDayUnit}">
                                    年
                                </c:when>
                                <c:otherwise>
                                    天
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">商品品牌：</div>
                    <div class="table-td">
                        <c:if test="${1 eq firstEngage.brandType}">
                            国产品牌
                        </c:if>
                        <c:if test="${2 eq firstEngage.brandType}">
                            进口品牌
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">国标类型：</div>
                    <div class="table-td">
                        <c:if test="${1 eq firstEngage.standardCategoryType}">
                            新国标
                        </c:if>
                        <c:if test="${2 eq firstEngage.standardCategoryType}">
                            旧国标
                        </c:if>
                        <c:if test="${3 eq firstEngage.standardCategoryType}">
                            二者都是
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">新国标分类：</div>
                    <div class="table-td">
                        <c:if test="${null eq firstEngage.newStandardCategoryName or '' eq firstEngage.newStandardCategoryName}">
                            - -
                        </c:if>
                        <c:if test="${null ne firstEngage.newStandardCategoryName and '' ne firstEngage.newStandardCategoryName}">
                            ${firstEngage.newStandardCategoryName}
                        </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">旧国标分类：</div>
                    <div class="table-td">
                        <c:choose>
                            <c:when test="${null ne firstEngage.oldStandardCategoryName and '' ne firstEngage.oldStandardCategoryName}">
                                ${firstEngage.oldStandardCategoryName}
                            </c:when>
                            <c:otherwise>
                                - -
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">存储条件1：</div>
                    <div class="table-td">
                        <c:if test="${firstEngage.conditionOne eq 983}">常温</c:if>
                        <c:if test="${firstEngage.conditionOne eq 984}">冷藏</c:if>
                        <c:if test="${firstEngage.conditionOne eq 985}">冷冻</c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">存储条件2：</div>
                    <div class="table-td">
                        <c:if test="${not empty firstEngage.storageCondition }">
                            <c:forEach var="storage" items="${firstEngage.storageCondition }">
                                ${storage.name }
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty firstEngage.storageCondition }">
                            - -
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="detail-block">
            <div class="block-title">其他信息</div>
            <div class="detail-table">

                <div class="table-item">
                    <div class="table-th">注册证附件/备案凭证附件：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.zczAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.zczAttachments }">
                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img style="width:100%;height:100%" src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>
                                    <a class="printAtta" href="javascript:;">打印</a>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>


                <div class="table-item">
                    <div class="table-th">营业执照：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.yzAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.yzAttachments }">
                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img style="width:100%;height:100%" src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>
                                    <a class="printAtta" href="javascript:;">打印</a>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="table-item">
                    <div class="table-th">说明书：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.smsAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.smsAttachments }">
                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty firstEngage.registration.smsAttachments }">
                                - -
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">生产企业卫生许可证：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.wsAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.wsAttachments }">
                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty firstEngage.registration.wsAttachments }">
                                - -
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">生产企业生产许可证：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.scAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.scAttachments }">
                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty firstEngage.registration.scAttachments }">
                                - -
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">商标注册证：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.sbAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.sbAttachments }">
                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty firstEngage.registration.sbAttachments }">
                                - -
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">注册登记表附件：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.djbAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.djbAttachments }">

                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>

                                </c:forEach>
                            </c:if>
                            <c:if test="${empty firstEngage.registration.djbAttachments }">
                                - -
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="table-item item-col">
                    <div class="table-th">产品图片（单包装/大包装）：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:if test="${not empty firstEngage.registration.cpAttachments }">
                                <c:forEach var="attachments" items="${firstEngage.registration.cpAttachments }">

                                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                                        <img src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                                    </div>

                                </c:forEach>
                            </c:if>
                            <c:if test="${empty firstEngage.registration.cpAttachments }">
                                - -
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/tmpl" class="J-del-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i>确认删除该首营品种资质？
            </div>
            <form class="J-del-form">
                <div class="del-cnt base-form">
                    <textarea name="content" id="" cols="30" rows="10" class="input-textarea" placeholder="必填：请填写删除原因，最少10个字，最多300个字"></textarea>
                </div>
            </form>
        </div>
    </script>
    <script type="text/tmpl" class="J-audit-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i>确定{{=auditTip}}么？
            </div>
            {{ if(type != 3){ }}
            <form class="J-audit-form">
                <div class="del-cnt base-form">
                    <textarea name="content" id="" cols="30" rows="10" class="input-textarea" placeholder="必填：请填写{{=auditTip}}原因，最多64个字"></textarea>
                </div>
            </form>
            {{ } }}
        </div>
    </script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/first/view.js?rnd=<%=Math.random()%>"></script>


<script type="text/javascript"
src='${pageContext.request.contextPath}/static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script>
$(function(){
$(".printAtta").each(function(){
$(this).click(function(){
$(this).hide();
$(this).prev().printArea();
$(this).show()
})
})
})
</script>

</body>

</html>