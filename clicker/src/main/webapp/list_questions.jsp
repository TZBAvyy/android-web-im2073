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
                    <h1>Questions for Room: ${room_code}</h1>
                </div>
            </div>
            <c:choose>
                <c:when test="${empty questions}">
                    <div class="row">
                        <div class="col-md-12">
                            <h2>No questions available</h2>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${questions}" var="question">
                        <div class="row">
                            <div class="col-md-12">
                                <a href="display?question_id=${question.id}">
                                    <h2>${question.question_text}</h2>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <div>
            
            </div>
        </div>
	</jsp:body>
</t:base>