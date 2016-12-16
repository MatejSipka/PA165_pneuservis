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

<my:pageTemplate title="Select Service">
<jsp:attribute name="body">

    <table class="table">
        <caption>Services</caption>
        <thead>
        <tr>
            <th>Duration (Hours)</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Type of car</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${services}" var="service">
            <tr>
                <td><c:out value="${service.duration}"/></td>
                <td><c:out value="${service.nameOfService}"/></td>
                <td><c:out value="${service.price}"/></td>
                <td><c:out value="${service.description}"/></td>
                <td><c:out value="${service.typeOfCar}"/></td>
                <td>
                    <form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/create/services/${service.id}">
                        <button type="submit" class="btn btn-primary">Select</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:out value="${pageContext.request.remoteUser}"/>
        </tbody>
    </table>
     <form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/create">
         <button type="submit" class="btn btn-primary">Cancel</button>
     </form>

</jsp:attribute>
</my:pageTemplate>