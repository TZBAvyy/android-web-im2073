<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
		<title>Kahoot 2 - List Questions</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1>Welcome to Kahoot 2</h1>
                    <p>Click on the button below to start the game</p>
                    <a href="game" class="btn btn-primary">Start Game</a>
                </div>
            </div>
            <c:forEach items="${questions}" var="question" varStatus="counter">
                <div class="row">
                    <div class="col-md-12">
                        <a href="display?room_id=${room_id}&question_no=${counter.index}">
                            <h2>${question.question_text}</h2>
                        </a>
                    </div>
                </div>
            </c:forEach>
            <div>
            
            </div>
        </div>
	</jsp:body>
</t:base>