<%@ tag description="Overall Page template" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="head" fragment="true" %>

<!DOCTYPE html>
<html lang="en">
<head> 
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="static/css/general.css">
    <script src="https://kit.fontawesome.com/e617f52b14.js" crossorigin="anonymous"></script>

    <%-- EXTRA HEAD COMPONENTS FOR DIFFERENT TITLE/CSS FILES --%>
    <jsp:invoke fragment="head"/> 
</head>

<body>
    <%-- BASE HEADER COMPONENT --%>
    <header>
        <div class="header-container">
            <c:choose>
                <c:when test="${empty current_room}">
                    <h1>Kahoot 2</h1>
                </c:when>
                <c:otherwise>
                    <h1>Room Code: ${current_room.getRoomCode()}</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </header>

    <%-- CONTENT COMPONENT --%>
    <div class="content">
        <jsp:doBody/>
    </div>

    <%-- BASE FOOTER COMPONENT --%>
    <footer>
        
    </footer>
</body>
</html>