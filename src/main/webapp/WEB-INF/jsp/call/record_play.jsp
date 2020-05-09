<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>录音播放</title>
</head>
<body>
<c:choose>
	<c:when test="${type == 2 }">
		<EMBED height=45 type=audio/mpeg width=300 src="${url }" volume="0" autostart="false"></EMBED>
	</c:when>
	<c:otherwise>
		<audio controls="controls" height="45" width="300" >
		    <source src="${url }" type="audio/mp3" />
		    <source src="${url }" type="audio/ogg" />
			<embed height="45" width="100" src="${url }" />
		</audio>
	</c:otherwise>
</c:choose>
</body>
</html>