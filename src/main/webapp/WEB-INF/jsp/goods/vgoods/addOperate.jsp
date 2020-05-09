<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>编辑运营信息</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/vgoods/addOperate.css?rnd=<%=Math.random()%>">
</head>

<body>
  <form action="./saveOperate.do" id="form_submit" class="J-form" method="POST">
    <input type="hidden" name="formToken" value="${formToken}" />
    <input type="hidden" name="goodsName" value="${coreOperateInfoGenerateVo.goodsName}" />
    <input type="hidden" name="skuId" value="${coreOperateInfoGenerateVo.skuId}" />
    <input type="hidden" name="spuId" value="${coreOperateInfoGenerateVo.spuId}" />
    <input type="hidden" name="operateInfoType" value="${coreOperateInfoGenerateVo.operateInfoType}" />
    <input type="hidden" name="operateInfoId" value="${coreOperateInfoGenerateVo.operateInfoId}" />
    <div class="form-wrap">
      <div class="form-container base-form form-span-7">
        <div class="form-title">
          添加运营信息
        </div>
        <div class="tab-nav">
          <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 1}">
            <a class="tab-item" href="${pageContext.request.contextPath}/goods/vgoods/viewSpu.do?spuId=${coreOperateInfoGenerateVo.spuId}">基本信息</a>
            <a class="tab-item current" href="./openOperate.do?spuId=${coreOperateInfoGenerateVo.spuId}">运营信息</a>
          </c:if>
          <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 2}">
            <a class="tab-item" href="${pageContext.request.contextPath}/goods/vgoods/viewSku.do?skuId=${coreOperateInfoGenerateVo.skuId}&spuId=${coreOperateInfoGenerateVo.upSpuId}">基本信息</a>
            <a class="tab-item current" href="./openOperate.do?skuId=${coreOperateInfoGenerateVo.skuId}">运营信息</a>
          </c:if>
        </div>
        <!-- 后台报错的区域 -->
        <c:if test="${not empty error}">
          <div class="vd-tip tip-red">
            <i class="vd-tip-icon vd-icon icon-error2"></i>
            <div class="vd-tip-cnt">${error}</div>
          </div>
        </c:if>
        <!-- end -->
        <div class="form-block">
          <div class="form-block-title">运营图文信息</div>
          <div class="form-item">
            <div class="form-label">商品标题：</div>
            <div class="form-fields">
              <div class="form-fields-txt">
                ${coreOperateInfoGenerateVo.goodsName}
              </div>
            </div>
          </div>
          <div class="form-item">
            <div class="form-label"><span class="must">*</span>商品图片：</div>
            <div class="form-fields">
              <div class="J-upload"></div>
              <input type="hidden" class="J-upload-data" value='${goodsImgJson}'>
              <div class="form-fields-tip">
                - 请上传1-5张商品图片；<br>
                - 图片格式为JPG、JPEG、PNG、BMP；<br>
                - 图片大小不超过5M；<br>
                - 建议尺寸800*800像素；<br>
                - 可拖拽图片进行排序；
              </div>
              <div class="feedback-block" wrapfor="upload"></div>
              <div class="feedback-block J-upload-error">
                <label for="" class="error" style="display: none;"></label>
              </div>
            </div>
          </div>
          <div class="form-item">
            <div class="form-label"><span class="must">*</span>商品详情：</div>
            <div class="form-fields">
              <div class="form-blanks ">
                <script id="content" name="oprateInfoHtml" type="text/plain" style="width: 80%; height: 400px;">${coreOperateInfoGenerateVo.oprateInfoHtml}</script>
              </div>
              <div id="descriptionError"></div>
              <a href="/vgoods/operate/previewOperate.do" target="_blank" class="rich-txt-btn J-rich-preview">预览图文信息</a>
              <input type="hidden" name="richTxt">
            </div>
          </div>
        </div>

        <%--<div class="form-block">
          <div class="form-block-title">SEO信息</div>
          <div class="form-item">
            <div class="form-label"><span class="must">*</span>SEO关键词：</div>
            <div class="form-fields">
              <div class="keyword-list J-keyword-list">
                <c:if test="${empty coreOperateInfoGenerateVo.seoKeyWordsArray}">
                  <div class="keyword-item cf J-keyword-item">
                    <div class="col-5">
                      <input type="input" name="seoKeyWordsArray" class="input-text" value="">
                    </div>
                    <div class="col-1">
                      <i class="vd-icon icon-recycle J-keyword-del"></i>
                    </div>
                  </div>
                </c:if>
                <c:if test="${not empty coreOperateInfoGenerateVo.seoKeyWordsArray}">
                  <c:forEach items="${coreOperateInfoGenerateVo.seoKeyWordsArray}" var="seoKeyWords" varStatus="status">
                    <div class="keyword-item cf J-keyword-item">
                      <div class="col-5">
                        <input type="input" name="seoKeyWordsArray" class="input-text" value="${seoKeyWords}">
                      </div>
                      <div class="col-1">
                        <i class="vd-icon icon-recycle J-keyword-del"></i>
                      </div>
                    </div>
                  </c:forEach>
                </c:if>
              </div>
              <div class="add-wrap">
                <input type="hidden" name="keywordNeedValue">
                <a href="javascript: void(0);" class="add-btn J-keyword-add"><i class="vd-icon icon-add"></i>新增关键词</a>
              </div>
            </div>
          </div>
          <div class="form-item">
            <div class="form-label">SEO标题：</div>
            <div class="form-fields">
              <div class="form-col col-10">
                <input type="input" name="seoTitle" class="input-text" value="${coreOperateInfoGenerateVo.seoTitle}">
              </div>
            </div>
          </div>
          <div class="form-item">
            <div class="form-label">SEO描述：</div>
            <div class="form-fields">
              <div class="form-col col-10">
                <textarea name="seoDescript" class="input-textarea" cols="30" rows="10">${coreOperateInfoGenerateVo.seoDescript}</textarea>
              </div>
            </div>
          </div>
        </div>--%>

        <!-- <div class="form-block">
          <div class="form-block-title">平台推送</div>
          <div class="form-item">
            <div class="form-label">平台推送：</div>
            <div class="form-fields">
              <div class="input-checkbox J-attr-list">
                <label class="input-wrap">
                  <input type="checkbox" name="baseAttributeIds" value="1">
                  <span class="input-ctnr"></span>贝登
                </label>
                <label class="input-wrap">
                  <input type="checkbox" name="baseAttributeIds" value="2">
                  <span class="input-ctnr"></span>医械购
                </label>
              </div>
            </div>
          </div>
        </div> -->

        <div class="form-btn">
          <div class="form-fields">
            <button type="submit" class="btn btn-blue btn-large">提交</button>
          </div>
        </div>
      </div>
    </div>
  </form>
  <script class="J-keyword-tmpl" type="text/tmpl">
    <div class="keyword-item cf J-keyword-item">
      <div class="col-5">
        <input type="input" name="seoKeyWordsArray" class="input-text" value="">
      </div>
      <div class="col-1">
        <i class="vd-icon icon-recycle J-keyword-del"></i>
      </div>
    </div>
  </script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/upload.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/sortable.min.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/?rnd=<%=Math.random()%>"></script>
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
  <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/vgoods/addOperate.js?rnd=<%=Math.random()%>"></script>
</body>

</html>