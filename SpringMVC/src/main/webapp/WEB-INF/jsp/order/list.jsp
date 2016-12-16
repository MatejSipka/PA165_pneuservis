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

<my:pageTemplate title="Order">
<jsp:attribute name="body">
    <my:a href="/order/create" class="btn btn-primary">New Order</my:a>
    <table class="table">
        <caption>Orders</caption>
        <thead>
        <tr>
            <th>Order Number</th>
            <th>Note</th>
            <th>Payment Confirmed</th>
            <th>Shipped</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><c:out value="${order.id}"/></td>
                <td><c:out value="${order.note}"/></td>
                <c:choose>
                    <c:when test="${order.paymentConfirmed}">
                        <td><c:out value="Yes"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="No"/></td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${order.shipped}">
                        <td><c:out value="Yes"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="No"/></td>
                    </c:otherwise>
                </c:choose>
                <td><my:a href="/order/${order.id}/view" class="btn btn-primary">View</my:a><span>
                <my:a href="/order/${order.id}/billing/${order.id}" class="btn btn-primary">Billing</my:a>
                <c:if test="${not empty Admin}">
                    <my:a href="/order/${order.id}/edit/${order.id}" class="btn btn-primary">Edit</my:a>

                        <form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/${order.id}/delete">
                            <button type="submit" class="btn btn-primary">Delete</button></form>
                </c:if>
                </span></td>
            </tr>
        </c:forEach>
        <c:out value="${pageContext.request.remoteUser}"/>
        </tbody>
    </table>

</jsp:attribute>
</my:pageTemplate>