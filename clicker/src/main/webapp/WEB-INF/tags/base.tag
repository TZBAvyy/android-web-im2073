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
        <c:if test="${not empty username}">
            <h1>Logged in as: ${username}</h1>
        </c:if>
    </header>

    <%-- CONTENT COMPONENT --%>
    <jsp:doBody/>

    <%-- BASE FOOTER COMPONENT --%>
    <footer>
        
    </footer>
</body>
</html>