<%--
    Document   : view
    Created on : 15-Dec-2016, 23:16:18
    Author     : Ivan Moscovic
--%>


<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Service view">
<jsp:attribute name="body">

    <c:if test="${authenticatedUser.admin == true}">
    <form method="post" action="${pageContext.request.contextPath}/service/delete/${service.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
        <td><my:a href="/service/edit/${service.id}" class="btn btn-primary">Edit</my:a></td>
    </form>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th>duration(hours)</th>
            <th>name</th>
            <th>price</th>
            <th>description</th>
            <th>type of car</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><c:out value="${service.duration}"/></td>
            <td><c:out value="${service.nameOfService}"/></td>
            <td><c:out value="${service.price}"/></td>
            <td><c:out value="${service.description}"/></td>
            <td><c:out value="${service.typeOfCar}"/></td>
        </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pageTemplate>