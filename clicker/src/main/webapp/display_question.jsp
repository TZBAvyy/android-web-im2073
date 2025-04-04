<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %> <%-- To use <t: > (.tag files in WEB-INF/tags) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- To use <c: > tags from core JSTL library  --%>

<t:base>
	<jsp:attribute name="head">
		<title>Kahoot 2 - Question ${question_no}</title>	
    </jsp:attribute>

	<jsp:body>
		<div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h2>${question.question_text}</h2>
                    <ol>
                    <c:forEach items="${question.choices}" var="choice">
                        <li>${choice}</li>
                    </c:forEach>
                    </ol>
                </div>
            </div>
        </div>
	</jsp:body>
</t:base>