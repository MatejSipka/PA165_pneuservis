<%--
    Document   : view
    Author     : vit.holasek
--%>


<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Order View">
<jsp:attribute name="body">

    <a href="/order/${order.id}/billing" target="_blank" class="btn btn-primary">Billing</a>
    <c:if test="${not empty Admin}"><span>
    <form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/${order.id}/delete">
        <button type="submit" class="btn btn-primary">Delete</button>
        <td><my:a href="/order/${order.id}/edit" class="btn btn-primary">Edit</my:a></td>
    </form></span>
    </c:if>
    </br>
    <table class="table">
        <tbody>
        <tr>
            <td><strong>Order Number:</strong></td>
            <td><c:out value="${order.id}"/></td>
        </tr>
        <tr>
            <td><strong>Note:</strong></td>
            <td><c:out value="${order.note}"/></td>
        </tr>
        <tr>
            <td><strong>Client:</strong></td>
            <td><c:out value="${person.firstname} ${person.surname}"/></td>
        </tr>
        <tr>
            <td><strong>Payment Type:</strong></td>
            <td><c:out value="${order.paymentType}"/></td>
        </tr>
        <tr>
            <td><strong>Payment Confirmed:</strong></td>
            <c:choose>
                    <c:when test="${order.paymentConfirmed}">
                        <td><c:out value="Yes"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="No"/></td>
                    </c:otherwise>
                </c:choose>
        </tr>
        <tr>
            <td><strong>Shipped:</strong></td>
            <c:choose>
                    <c:when test="${order.shipped}">
                        <td><c:out value="Yes"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="No"/></td>
                    </c:otherwise>
                </c:choose>
        </tr>
        </tbody>
    </table>

    <c:if test="${order.services != null}">
     <table class="table">
         <caption>Ordered Services</caption>
         <thead>
         <tr>
             <th>Name</th>
             <th>Duration (Hours)</th>
             <th>Price</th>
         </tr>
         </thead>
         <tbody>
         <c:forEach items="${order.services}" var="service">
            <tr>
                <td><c:out value="${service.nameOfService}"/></td>
                <td><c:out value="${service.duration}"/></td>
                <td><c:out value="${service.price}"/></td>
            </tr>
        </c:forEach>
         </tbody>
     </table>
    </c:if>

    <c:if test="${order.tires != null}">
    <table class="table">
        <caption>Ordered Tires</caption>
        <thead>
        <tr>
            <th>Type</th>
            <th>Catalog Number</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.tires}" var="tire">
            <tr>
                <td><c:out value="${tire.type}"/></td>
                <td><c:out value="${tire.catalogNumber}"/></td>
                <td><c:out value="${tire.price}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</jsp:attribute>
</my:pageTemplate>