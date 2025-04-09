<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
        <link rel="stylesheet" href="static/css/index.css">
		<title>Kahoot 2 - Login page</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <form action="login" method="POST" id="login-form">
                <h2>Login to continue</h2>
                <div class="form-item">
                    <label for="email">Email: </label>
                    <input
                        type="email"
                        name="email"
                        id="email"
                        placeholder="e.g. kahoot@gmail.com"
                        required>
                    <label for="password">Password: </label>
                    <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="e.g. 12345678"
                        required>
                </div>
                <c:if test="${not empty error}">
                    <div class="error-message">
                        <c:out value="${error}"/>
                    </div>
                </c:if>
                <input type="hidden" name="web" id="web" value="true">
                <input type="submit" value="Login" class="submit-btn">
                <p>Don't have an account? <a href="/register">Register here</a></p>
            </form>
        </div>
	</jsp:body>
</t:base>