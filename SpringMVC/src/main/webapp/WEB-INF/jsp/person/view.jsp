<%-- 
    Document   : view
    Created on : 12-Dec-2016, 22:59:43
    Author     : Maros Staurovsky
--%>


<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Person view">
<jsp:attribute name="body">

    <c:if test="${authenticatedUser.admin == true}">
    <form method="post" action="${pageContext.request.contextPath}/person/delete/${person.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    <td><my:a href="/person/edit/${person.id}" class="btn btn-primary">Edit</my:a></td>
    </form>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>type</th>
            <th>Occurrence Start</th>
            <th>Occurrence Stop</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${person.id}</td>
                <td><c:out value="${person.name}"/></td>
                <td><c:out value="${person.type}"/></td>
                <td><fmt:formatDate value="${person.DateOfBirth}" pattern="yyyy-MM-dd"/></td>
            </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pageTemplate>