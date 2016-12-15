<%-- 
    Document   : list
    Created on : 12-Dec-2016, 22:59:30
    Author     : Maros Staurovsky
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Person">
<jsp:attribute name="body">
    <c:if test="${not empty Admin}">
    <my:a href="/person/create" class="btn btn-primary"> New person</my:a>
    </c:if>
    
    <table class="table">
        <caption>Person</caption>
        <thead>
        <tr>
            <th>Id</th>
            <th>Fist name</th>
            <th>Surname</th>
            <th>Type</th>
            <th>Date of birth (dd-mm-yyyy)</th>
            <th>Login</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${persons}" var="person">
            <tr>
                <td>${person.id}</td>
                <td><c:out value="${person.firstname}"/></td>
                <td><c:out value="${person.surname}"/></td>
                <td><c:out value="${person.personType}"/></td>
                <td><fmt:formatDate value="${person.dateOfBirth}" pattern="dd-MM-yyyy"/></td>
                <td><c:out value="${person.login}"/></td>
                <td><my:a href="/person/view/${person.id}" class="btn btn-primary">View</my:a></td>
                <c:if test="${not empty Admin}">
                    <td><my:a href="/person/edit/${person.id}" class="btn btn-primary">Edit</my:a></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/person/delete/${person.id}">
                        <button type="submit" class="btn btn-primary">Delete</button></form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        <c:out value="${pageContext.request.remoteUser}"/>
        </tbody>
    </table>

</jsp:attribute>
</my:pageTemplate>