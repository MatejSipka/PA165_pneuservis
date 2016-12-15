<%--
    Document   : list
    Created on : 15-Dec-2016, 21:04:17
    Author     : Ivan Moscovic
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Services">
<jsp:attribute name="body">
    <c:if test="${not empty Admin}">
    <my:a href="/services/create" class="btn btn-primary"> New service</my:a>
    </c:if>

    <table class="table">
        <caption>Service</caption>
        <thead>
        <tr>
            <th>duration(hours)</th>
            <th>own parts</th>
            <th>name</th>
            <th>price</th>
            <th>description</th>
            <th>type of car</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${services}" var="service">
            <tr>
                <td><c:out value="${service.duration}"/></td>
                <td>
                    <%--<c:if test="${service.ownParts == TRUE}">--%>
                        <!--TRUE-->
                    <%--</c:if>--%>
                    <%--<c:out value="${service.ownParts}"/>--%>
                    <%--<c:if test="${service.ownParts == FALSE}">--%>
                        <!--FALSE-->
                    <%--</c:if>--%>
                </td>
                <td><c:out value="${service.nameOfService}"/></td>
                <td><c:out value="${service.price}"/></td>
                <td><c:out value="${service.description}"/></td>
                <td><c:out value="${service.typeOfCar}"/></td>
                <td><my:a href="/services/view/${service.id}" class="btn btn-primary">View</my:a></td>
                <c:if test="${not empty Admin}">
                    <td><my:a href="/services/edit/${service.id}" class="btn btn-primary">Edit</my:a></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/services/delete/${service.id}">
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