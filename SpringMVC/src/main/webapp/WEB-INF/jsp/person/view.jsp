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
            <th>Id</th>
            <th>Fist name</th>
            <th>Surname</th>
            <th>Type</th>
            <th>Date of birth(dd-mm-yyyy)</th>
            <th>Login</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${person.id}</td>
                <td><c:out value="${person.firstname}"/></td>
                <td><c:out value="${person.surname}"/></td>
                <td><c:out value="${person.personType}"/></td>
                <td><fmt:formatDate value="${person.dateOfBirth}" pattern="dd-MM-yyyy"/></td>
                <td><c:out value="${person.login}"/></td>
            </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pageTemplate>