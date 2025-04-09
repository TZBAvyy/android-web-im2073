<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
        <link rel="stylesheet" href="static/css/questions.css">
		<title>Kahoot 2 - Question</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <h2>${question.question_text}</h2>
            <ol class="choices-list" type="A">
            <c:forEach items="${question.choices}" var="choice">
                <li>${choice}</li>
            </c:forEach>
            </ol>
            <a href="next_question" class="start-btn">Next Question</a>
        </div>
	</jsp:body>
</t:base>