<%--
    Document   : list
    Created on : 15-Dec-2016, 23:04:17
    Author     : Matej Sipka
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Tires">
    <jsp:attribute name="scripts">
        <script>
            $(document).ready(function () {
                var url = window.location.href;
                var searchIndex = url.indexOf("?");
                var param = url.substring(searchIndex + 1);
                console.log(param);
                if (param === 'deleteError') {
                    document.getElementById('error_message').style.display = 'block';
                }
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="body">
        <c:if test="${not empty Admin}">
            <my:a href="/tires/create" class="btn btn-primary"> New Tire</my:a>
        </c:if>

        <div id="error_message" style="display: none; color: red;">Could not delete tire. This tire is probably included in an existing order.</div>

        <table class="table">
            <caption>Tires list</caption>
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

                        <c:if test="${not empty Admin}">
                            <td><my:a href="/tires/edit/${tire.id}" class="btn btn-primary">Edit</my:a></td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/tires/delete/${tire.id}">
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
