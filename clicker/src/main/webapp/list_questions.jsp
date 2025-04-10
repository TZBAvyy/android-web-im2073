<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
        <link rel="stylesheet" href="static/css/questions.css">
		<title>Kahoot 2 - List Questions</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <h1>Questions for Room: ${room_code}</h1>
            <c:choose>
                <c:when test="${empty questions}">
                    <h2>No questions available</h2>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${questions}" var="question">
                        <a href="display?question_id=${question.id}">
                            <h2>${question.question_text}</h2>
                        </a>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <form action="start" method="post">
            <input type="hidden" name="room_id" value="${room_id}">
            <input type="submit" value="Start Game" class="start-btn">
            </form>
        </div>
	</jsp:body>
</t:base>