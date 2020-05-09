<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>
        <c:if test="${null ne brand.brandId }">
            编辑品牌
        </c:if>
        <c:if test="${null eq brand.brandId }">
            新增品牌
        </c:if>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/brand/add_brand.css?rnd=<%=Math.random()%>">
</head>

<body>
    <form action="./saveBrand.do" id="form_submit" class="J-form" method="POST">
        <input type="hidden" name="formToken" value="${formToken}" />
        <input type="hidden" name="brandId" value="${brand.brandId}" />
        <div class="form-wrap">
            <div class="form-container base-form form-span-8">
                <c:if test="${null ne brand.brandId }">
                    <div class="form-title">编辑品牌</div>
                </c:if>
                <c:if test="${null eq brand.brandId }">
                    <div class="form-title">新增品牌</div>
                </c:if>
                <!-- 后台报错的区域 -->
                <c:forEach var="error" items="${brand.errors}" varStatus="status">
                    <div class="vd-tip tip-red">
                        <i class="vd-tip-icon vd-icon icon-error2"></i>
                        <div class="vd-tip-cnt">${error}</div>
                    </div>
                </c:forEach>
                <!-- end -->
                <div class="form-block">
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>商品品牌：</div>
                        <div class="form-fields">
                            <div class="input-radio">
                                <label class="input-wrap">
                                    <input type="radio" name="brandNature" value="1" <c:if test="${null eq brand || brand.brandNature eq 1}">checked</c:if> >
                                    <span class="input-ctnr"></span>国产品牌
                                </label>
                                <label class="input-wrap">
                                    <input type="radio" name="brandNature" value="2" <c:if test="${brand.brandNature eq 2}">checked</c:if> >
                                    <span class="input-ctnr"></span>进口品牌
                                </label>
                            </div>
                            <div class="feedback-block" wrapfor="brandNature"></div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must J-cn-must" style="display: none;">*</span>品牌名称（中文名）：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" name="brandName" valid-max="64" placeholder="请输入品牌中文名，示例：六六" class="input-text J-brandName" value="${brand.brandName}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must J-en-must" style="display: none;">*</span>品牌名称（英文名）：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" name="brandNameEn" valid-max="64" placeholder="请输入品牌英文名，示例：Bio Tek" class="input-text J-brandName" value="${brand.brandNameEn}">
                                <div class="form-fields-tip">-英文大小写符合品牌商官方定义</div>
                                <div class="feedback-block" wrapfor="brandNameEn"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">生产企业：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <div class="J-suggest-wrap"></div>
                                <input type="hidden" class="J-factory-input" name="manufacturer" value="${brand.manufacturer}">
                                <div class="form-fields-tip">-如未找到生产企业信息，请到注册证管理处添加相关注册证信息</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">公司名称：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" placeholder="请填写公司名称" name="companyName" valid-max="256" class="input-text" value="${brand.companyName}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">品牌网址：</div>
                        <div class="form-fields">
                            <div class="form-col col-8">
                                <input type="text" autocomplete="off" placeholder="请填写品牌官网链接" name="brandWebsite" valid-max="256" class="input-text" value="${brand.brandWebsite}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>品牌Logo：</div>
                        <div class="form-fields">
                            <div class="J-upload" data-limit="1"></div>
                            <input type="hidden" class="J-upload-data" value='${logMap}'>
                            <div class="form-fields-tip">
                                -最多上传1张；<br>
                                -建议图片上传尺寸为140*70像素,或其他等比尺寸；<br>
                                -图片底纹需白色；<br>
                                -图片格式为JPG、JPEG、PNG、BMP；<br>
                                -图片大小不超过5M；
                            </div>
                            <div class="feedback-block" wrapfor="upload0"></div>
                            <div class="feedback-block J-upload-error">
                                <label class="error" for="" style="display: none;"></label>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">授权证明：</div>
                        <div class="form-fields">
                            <div class="J-upload" data-limit="5"></div>
                            <input type="hidden" class="J-upload-data" value='${proofMap}'>
                            <div class="form-fields-tip">
                                -最多上传5张；<br>
                                -图片格式为JPG、JPEG、PNG、BMP；<br>
                                -图片大小不超过5M；
                            </div>
                            <div class="feedback-block J-upload-error">
                                <label class="error" for="" style="display: none;"></label>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">品牌说明：</div>
                        <div class="form-fields">
                            <div class="form-blanks ">
                                <script id="content" name="description" type="text/plain" style="width: 80%; height: 400px;">${brand.description}</script>
                            </div>
                            <div id="descriptionError"></div>
                            <div class="form-fields-tip">
                                -请填写品牌描述信息​<br>
                                -如需上传图片，每张图片建议宽度为640像素，所有图片保持一致<br>
                                -图片格式建议为JPG、JPGE、PNG、BMP；<br>
                                -图片总数建议大于1张，但不超过30张；<br>
                                -为了更好的视觉呈现，图片上的文字建议不小于20号；
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-btn">
                    <div class="form-fields">
                        <button type="submit" class="btn btn-blue btn-large">保存</button>
                        <c:if test="${null ne brand.brandId }">
                            <a href="./viewBrandDetail.do?brandId=${brand.brandId}" class="btn btn-large">取消</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/json" class="J-factory-json">
             ${traderSupplierList}
        </script>
        <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
        <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
        <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
        <script src="${pageContext.request.contextPath}/static/new/js/common/upload.js?rnd=<%=Math.random()%>"></script>
        <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
        <!-- 以下ueditor编辑器需要引用的文件 -->
        <script src="${pageContext.request.contextPath}/static/libs/textmodify/ueditor.simple.config.js?rnd=<%=Math.random()%>"></script>
        <script src="${pageContext.request.contextPath}/static/libs/textmodify/ueditor.all.min.js?rnd=<%=Math.random()%>"> </script>
        <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
        <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
        <script src="${pageContext.request.contextPath}/static/libs/textmodify/lang/zh-cn/zh-cn.js?rnd=<%=Math.random()%>"></script>
        <!-- 以上ueditor编辑器需要引用的文件 -->
        <!-- 以下为异步上传附件要引用的文件 -->
        <script src="${pageContext.request.contextPath}/static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
        <!-- 以上为异步上传附件要引用的文件 -->
        <!-- 以下为UEditor插件要引用的文件 -->
        <script src="${pageContext.request.contextPath}/static/js/file/edit_uedit.js?rnd=<%=Math.random()%>"></script>
        <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/brand/add_brand.js?rnd=<%=Math.random()%>"></script>
    </form>
</body>