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

<my:pageTemplate title="Select Tire">
<jsp:attribute name="body">

    <table class="table">
        <caption>Services</caption>
        <thead>
        <tr>
            <th>Type</th>
            <th>Catalog Number</th>
            <th>Manufacturer</th>
            <th>Size</th>
            <th>Profile</th>
            <th>Diameter</th>
            <th>Price</th>
            <th>Car Type</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tires}" var="tire">
            <tr>
                <td><c:out value="${tire.type}"/></td>
                <td><c:out value="${tire.catalogNumber}"/></td>
                <td><c:out value="${tire.manufacturer}"/></td>
                <td><c:out value="${tire.tireSize}"/></td>
                <td><c:out value="${tire.profile}"/></td>
                <td><c:out value="${tire.diameter}"/></td>
                <td><c:out value="${tire.price}"/></td>
                <td><c:out value="${tire.typeOfCar}"/></td>
                <td>
                    <form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/create/tires/${tire.id}">
                        <button type="submit" class="btn btn-primary">Select</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:out value="${pageContext.request.remoteUser}"/>
    </table>
     <form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/create">
         <button type="submit" class="btn btn-primary">Cancel</button>
     </form>

</jsp:attribute>
</my:pageTemplate>