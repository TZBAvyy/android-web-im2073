<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
        <link rel="stylesheet" href="static/css/home.css">
		<title>Kahoot 2 - Home page</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <h2>Rooms Available:</h2>
            <div class="rooms-container">
                <c:if test="${not empty rooms}">
                    <c:forEach items="${rooms}" var="room" varStatus="counter">
                        <a href="display?room_id=${room.id}">
                        Room ${counter.index+1}: [${room.getRoomCode()} | MAX PLAYERS: ${room.getMaxCapacity()}]
                        </a>
                    </c:forEach>
                </c:if>
            </div>
            <a href="create_room" class="create-room-btn">Create New Room</a>
        </div>
	</jsp:body>
</t:base>