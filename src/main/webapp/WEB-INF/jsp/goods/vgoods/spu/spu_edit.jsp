<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <c:if test="${command.spuId ==null or command.spuId <=0}">新增SPU</c:if>
        <c:if test="${command.spuId >0}">修改SPU</c:if>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/vgoods/spu/spu_edit.css?rnd=<%=Math.random()%>">
</head>

<body>
<c:if test="${command.spuLevel==2}">
    <form action="./saveTempSpu.do" id="form_submit" class="J-form" method="POST">
</c:if>
<c:if test="${command.spuLevel!=2}">
    <form action="./saveSpu.do" id="form_submit" class="J-form" method="POST">
</c:if>

        <input type="hidden" name="formToken" value="${formToken}">
        <input type="hidden" id="spuId" name="spuId" value="${command.spuId}">
        <input type="hidden" class="J-spu-level" value="${command.spuLevel}">
        <div class="form-wrap">
            <div class="form-container base-form form-span-7">
                <div class="form-title">
                    <c:if test="${command.spuId ==null or command.spuId <=0}">新增SPU</c:if>
                    <c:if test="${command.spuId >0}">修改SPU</c:if>
                    <div class="tip-wrap">
                        <i class="vd-icon icon-info2">
                            <div class="tip arrow-left">
                                <div class="tip-con">
                                    新增SPU需提前配置好分类、品牌、首营信息、科室&属性等信息。<br>
                                    如未能找到对应选项，或者发现问题，请前往对应管理页面进行添加/编辑，或者联系商品组同事进行配置。
                                </div>
                                <span class="arrow arrow-out">
                                    <span class="arrow arrow-in"></span>
                                </span>
                            </div>
                        </i>
                    </div>
                </div>
                <!-- 后台报错的区域 -->
                <c:forEach var="error" items="${command.errors}" varStatus="status">
                    <div class="vd-tip tip-red">
                        <i class="vd-tip-icon vd-icon icon-error2"></i>
                        <div class="vd-tip-cnt">${error}</div>
                    </div>
                </c:forEach>
                <c:forEach var="tip" items="${command.tips}" varStatus="status">
                    <div class="vd-tip tip-green">
                        <i class="vd-tip-icon vd-icon icon-yes1"></i>
                        <div class="vd-tip-cnt">${tip}</div>
                    </div>
                </c:forEach>
                <!-- end -->
                <div class="form-block">
                    <div class="form-block-title">分类信息</div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>分类：</div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <div class="select J-category-select">
                                    <div class="select-title">
                                        <div class="select-selected">
                                            <span class="J-text">请选择商品分类</span>
                                        </div>
                                        <div class="select-arrow">
                                            <i class="vd-icon icon-down"></i>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="categoryId" class="J-category-value" value="${command.categoryId}">
                            </div>
                            <div class="form-fields-tip">- 分类决定SKU商品的属性类型，请谨慎选择<br>- 如未找到对应分类，请前往<a tabTitle='{"num":"viewCategory","link":"/category/base/index.do","title":"分类管理", "random": "1"}' href="javascript:void(0);">分类管理</a>进行添加</div>
                        </div>
                    </div>
                </div>
                <div class="form-block">
                    <div class="form-block-title">基本信息</div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>商品等级：</div>
                        <div class="form-fields">
                            <div class="input-radio">
                                <c:if test="${command.spuLevel !=2}">
                                    <label class="input-wrap">
                                        <input type="radio" name="spuLevel" value="1" <c:if test="${command.spuLevel==1 }"> checked </c:if> >
                                <span class="input-ctnr"></span>核心商品
                                </label>
                                <label class="input-wrap">
                                    <input type="radio" name="spuLevel" value="0" <c:if test="${command.spuLevel==0 }"> checked </c:if>>
                                    <span class="input-ctnr"></span>其他商品
                                </label>
                                </c:if>
                                <c:if test="${command.spuLevel==2}">
                                    <label class="input-wrap">
                                        <input type="hidden" name="spuLevel" value="2">
                                        <span class="input-ctnr"></span>临时商品
                                    </label>
                                </c:if>
                            </div>
                            <div class="feedback-block" wrapfor="spuLevel"></div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>品牌：</div>
                        <div class="form-fields">
                            <div class="J-brand-wrap" data-name="${command.brandName}"></div>
                            <input type="hidden" name="brandId" class="J-brandId" value="${command.brandId}">
                            <div class="form-fields-tip">
                                - 如需<a tabTitle='{"num":"viewCategory","link":"/firstengage/brand/addBrand.do","title":"新增品牌", "random": "1"}' href="javascript:void(0);">新增品牌</a>，请前往品牌管理中添加；
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>商品类型：</div>
                        <div class="form-fields">
                            <div class="input-radio">
                                <c:forEach var="spuType" items="${command.spuTypeList}" varStatus="status">
                                    <label class="input-wrap">
                                        <input type="radio" class="J-prod-type" name="spuType" value="${spuType.sysOptionDefinitionId }" <c:if test="${command.spuType == spuType.sysOptionDefinitionId }"> checked </c:if>>
                                        <span class="input-ctnr"></span>
                                        <c:out value="${spuType.title }" />
                                    </label>
                                </c:forEach>
                            </div>
                            <div class="feedback-block" wrapfor="spuType"></div>
                        </div>
                    </div>
                    <div class="form-item J-first-block" <c:if test="${command.categoryType!=1}"> style="display: none" </c:if>  >
                        <div class="form-label"><span class="must">*</span>首营信息：</div>
                        <div class="form-fields">
                            <div class="select J-firstenage-select">
                                <div class="select-title">
                                    <div class="select-selected">
                                        <span class="J-text">请选择注册证号/备案凭证号</span>
                                    </div>
                                    <div class="select-arrow">
                                        <i class="vd-icon icon-down"></i>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" <c:if test="${command.categoryType!=1}"> disabled </c:if>   name="firstEngageId" class="J-firstenage-value" value="${command.firstEngageId}"><!-- ${command.firstEngageId} -->
                            <div class="form-fields-tip">
                                - 如需<a href="javascript:void(0);"tabTitle='{"num":"addProduct","link":"./firstengage/baseinfo/add.do","title":"新增首营信息", "random": "1"}' >新增首营信息</a>，请前往首营信息管理中添加；
                            </div>
                            <div class="first-detail-wrap form-col col-11 J-first-detail"></div>
                        </div>
                    </div>
                    <c:if test="${command.spuLevel!=2}">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>通用名：</div>
                            <div class="form-fields">
                                <div class="form-col col-6">
                                    <input type="text" class="input-text J-common-name" placeholder="请输入商品通用名" name="spuName" value="${command.spuName}">
                                </div>
                                <div class="form-fields-tip">
                                    - 请尽量使用注册证上的通用名；
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>商品名称：</div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <input type="text" class="input-text J-prod-name" placeholder="请输入商品名称" name="showName" value="${command.showName}">
                            </div>
                            <c:if test="${command.spuLevel!=2}">
                                <div class="form-fields-tip">
                                    - 对外销售的展示名，由“品牌+通用名组成”；
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${ command.spuLevel ==2 && command.spuId ==null }">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span><span class="J-prod-type-txt"></span>：</div>
                            <div class="form-fields">
                                <div class="form-col col-6">
                                    <input type="text" class="input-text" name="skuInfo" value="${command.skuInfo}">
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${command.spuLevel!=2}">
                        <div class="form-item">
                            <div class="form-label">注册商标：</div>
                            <div class="form-fields">
                                <div class="form-col col-6">
                                    <input type="text" class="input-text" name="registrationIcon" placeholder="请输入商品注册商标" value="${command.registrationIcon}">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">Wiki链接：</div>
                            <div class="form-fields">
                                <div class="form-col col-6">
                                    <input type="text" class="input-text" name="wikiHref" value="${command.wikiHref}">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">检测报告：</div>
                            <div class="form-fields">
                                <input type="hidden" class="J-upload-data" name="spuCheckFileJson" value='${command.spuCheckFileJson}'>

                                <div class="J-upload" type="spuCheckFiles"></div>
                                <div class="form-fields-tip">
                                    - 最多上传5张图片；<br>
                                    - 检测报告原件扫描件；<br>
                                    - 图片格式为JPG、JPEG、PNG、BMP；<br>
                                    - 图片大小不超过5M；
                                </div>


                                <div class="feedback-block J-upload-error">
                                    <label for="" class="error" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">专利文件：</div>
                            <div class="form-fields">
                                <input type="hidden" class="J-upload-data" name="spuPatentFilesJson" value='${command.spuPatentFilesJson}'>
                                <div class="J-upload" type="spuPatentFiles"></div>
                                <div class="form-fields-tip">
                                    - 最多上传5张图片；<br>
                                    - 专利文件原件扫描件；<br>
                                    - 图片格式为JPG、JPEG、PNG、BMP；<br>
                                    - 图片大小不超过5M；
                                </div>
                                <div class="feedback-block J-upload-error">
                                    <label for="" class="error" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
                <c:if test="${command.spuLevel!=2}">
                    <div class="form-block">
                        <div class="form-block-title">应用信息</div>
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>科室：</div>
                            <div class="form-fields">
                                <div class="input-checkbox">
                                    <c:forEach var="departmentsHospital" items="${command.departmentsHospitalList}" varStatus="status">
                                        <label class="input-wrap">
                                            <input type="checkbox" class="J-department" name="departmentIds" value="${departmentsHospital.departmentId }" <c:if test="${departmentsHospital.selected}"> checked="checked"
                </c:if>
                >
                <span class="input-ctnr"></span>${departmentsHospital.departmentName }
                </label>
                </c:forEach>
            </div>
            <div class="feedback-block" wrapfor="departmentIds"></div>

        </div>
        </div>
        <div class="form-item">
            <div class="form-label">功能：</div>
            <div class="form-fields">
                <div class="form-col col-10">
                    <div class="tag-wrap J-tag-wrap"></div>
                    <div class="tag-list J-tag-list">
                        <div class="tag-item item-add J-tag-add"><i class="vd-icon icon-add"></i>新标签</div>
                        <div class="tag-input-wrap J-tag-new" style="display: none;">
                            <input type="text" class="input-text J-tag-input" maxlength="20">
                            <a class="btn btn-small btn-blue J-tag-add-confirm">确定</a>
                            <a class="btn btn-small J-tag-add-cancel">取消</a>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="hospitalTags" class="J-tag-value" value="${command.hospitalTags}">
            </div>
        </div>
        </div>
        <div class="form-block J-attr-wrap">
            <div class="form-block-title">属性信息</div>
            <div class="form-item">
                <div class="form-label">选择带入分类属性：</div>
                <div class="form-fields">
                    <div class="input-checkbox J-attr-list">
                        <c:forEach var="baseAttribute" items="${command.baseAttributes}" varStatus="status">
                            <label class="input-wrap">
                                <input type="checkbox" name="baseAttributeIds" <c:if test="${baseAttribute.selected}"> checked </c:if> value="${baseAttribute.baseAttributeId}" >
                                <span class="input-ctnr"></span>${baseAttribute.baseAttributeName }
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        </c:if>
                <c:if test="${command.spuId ==null or command.spuId <=0}">
                <div class="form-block">
                    <div class="form-block-title">产品归属</div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>归属产品经理：</div>
                        <div class="form-fields">
                            <select class="input-middle J-select assign" style="margin-top: 6px"  name="assignmentManagerId">
                                <option value="">请选择</option>
                                <c:forEach var="list" items="${productOwnerUsers}">
                                    <option value="${list.userId}"
                                            <c:if test="${command.assignmentManagerId == list.userId}">selected="selected"</c:if> >${list.username}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>归属产品助理：</div>
                        <div class="form-fields">
                            <select class="input-middle J-select assign" style="margin-top: 6px"  name="assignmentAssistantId">
                                <option value="">请选择</option>
                                <c:forEach var="list" items="${productOwnerUsers}">
                                    <option value="${list.userId}"
                                            <c:if test="${command.assignmentAssistantId == list.userId}">selected="selected"</c:if> >${list.username}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                </c:if>
        <div class="form-btn">
            <div class="form-fields">
                <button type="submit" class="btn btn-blue btn-large">提交</button>
            </div>
        </div>
        </div>
        </div>
    </form>
    <script type="text/tmpl" class="J-first-tmpl">
        <div class="detail-item">
            <div class="detail-label">证件有效期：</div>
            <div class="detail-fields">{{=effectStartDate}} - {{=effectEndDate}}</div>
        </div>
        <div class="detail-item">
            <div class="detail-label">管理类别：</div>
            <div class="detail-fields">{{=manageCategoryLevel || '--'}}</div>
        </div>
        <div class="detail-item">
            <div class="detail-label">生产厂商：</div>
            <div class="detail-fields">{{=productCompanyChineseName || '--'}}</div>
        </div>
        <div class="detail-item">
            <div class="detail-label">旧国标分类：</div>
            <div class="detail-fields">{{=oldStandardCategoryName || '--'}}</div>
        </div>
        {{ if(newStandardCategoryName){ }}
        <div class="detail-item">
            <div class="detail-label">新国标分类：</div>
            <div class="detail-fields">{{=newStandardCategoryName || '--'}}</div>
        </div>
        {{ } }}
        <div class="detail-item">
            <div class="detail-label">审核状态：</div>
            <div class="detail-fields">{{=checkStatus || '--'}}</div>
        </div>
    </script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/upload.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/pager.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/dialogSearch.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/dialogSelect.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/vgoods/spu_edit.js?rnd=<%=Math.random()%>"></script>
</body>

</html>