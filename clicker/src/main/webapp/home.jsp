<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
        <link rel="stylesheet" href="static/css/index.css">
		<title>Kahoot 2 - Home page</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <form action="login" method="POST" id="login-form">
                LOGGED IN
            </form>
        </div>
	</jsp:body>
</t:base>