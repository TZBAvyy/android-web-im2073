<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
		<title>Kahoot 2 - Home page</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1>Welcome to Kahoot 2</h1>
                    <p>Click on the button below to start the game</p>
                    <c:choose>
                        <c:when test="${empty username}">
                            <p>You are not logged in. Please <a href="login">log in</a> to start the game.</p>
                        </c:when>
                        <c:otherwise>
                            <p>Hello, ${username}! You are logged in.</p>
                            <p>Click on the button below to start the game.</p>
                            <a href="display?room_id=1" class="btn btn-primary">Start Game</a>
                        </c:otherwise>
                    </c:choose>
                    
                </div>
            </div>
        </div>
	</jsp:body>
</t:base>