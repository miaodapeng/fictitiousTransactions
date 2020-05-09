<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./common/common.jsp"%>

<div class="front-page">
        <div class="front-page-container">
            <div class="title">综合业务管理平台<small style="font-size: 24px;padding-left: 10px;">V1.1.0</small></div>
           <div class="show-container">
                <div class="show-two">
                <ul>
                    <li>
                        <a href=""></a>
                           <img src="<%= basePath %>static/images/l1.png">
                           <p>SAAS架构，业务分离，数据共享</p>
                        
                    </li>
                    <li>
                        <a href=""></a>
                             <img src="<%= basePath %>static/images/l2.png">
                           <p>高效专业的分析工具，建立多维度分析</p>
                        
                    </li>
                </ul>
            </div>
            <div class="show-three">
                <ul>
                   <li>
                        <a href=""></a>
                           <img src="<%= basePath %>static/images/l3.png">
                           <p>针对岗位设定个人首页</p>
                        
                    </li>
                    <li>
                        <a href=""></a>
                             <img src="<%= basePath %>static/images/l4.png">
                           <p>网络协同，第三方工具接入</p>
                        
                    </li>
                     <li>
                        <a href=""></a>
                           <img src="<%= basePath %>static/images/l5.png">
                           <p>增加售后流程</p>
                        
                    </li>
                </ul>
            </div>

           </div>
        </div>
    </div>
</body>


<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/call/call.js?rnd=<%=Math.random()%>"></script>
<%@ include file="./common/footer.jsp"%>