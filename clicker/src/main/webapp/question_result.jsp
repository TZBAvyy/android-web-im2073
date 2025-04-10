<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
        <link rel="stylesheet" href="static/css/questions.css">
        <link rel="stylesheet" href="static/css/result.css">
		<title>Kahoot 2 - Result</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <h2>${question.question_text}</h2>
            <ol class="choices-list" type="A">
            <c:forEach items="${question.choices}" var="choice">
                <li>${choice}</li>
            </c:forEach>
            </ol>
            <h3>Results</h3>
            <c:forEach items="${responses}" var="result">
                <p>${result.getPlayer_name()} - ${result.choice}</p>
            </c:forEach>
            <form action="display" method="post">
                <input type="submit" class="start-btn" value="Next Question">
            </form>
        </div>
	</jsp:body>
</t:base>