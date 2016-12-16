<%--
  Created by IntelliJ IDEA.
  User: vit.holasek
  Date: 16.12.2016
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <title>Order Billing</title>
</head>
<body>
<h1>Order no. ${order.id} Billing</h1>
<table class="table">
    <caption><strong>Order</strong></caption>
    <thead>
    <tr>
        <th>Order Number</th>
        <th>Note</th>
        <th>Client</th>
        <th>Price without VAT</th>
        <th>Price wit VAT</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td><c:out value="${order.id}"/></td>
            <td><c:out value="${order.note}"/></td>
            <td><c:out value="${person.firstname} ${person.surname}"/></td>
            <td><c:out value="${billing.price}"/></td>
            <td><c:out value="${billing.priceWithVAT}"/></td>
        </tr>
    </tbody>
</table>
<table class="table">
    <caption><strong>Order Items</strong></caption>
    <thead>
    <tr>
        <th>Description</th>
        <th>VAT</th>
        <th>Price without VAT</th>
        <th>Price with VAT</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${billing.billingItems}" var="item">
        <tr>
            <td><c:out value="${item.description}"/></td>
            <td><c:out value="${item.VAT}%"/></td>
            <td><c:out value="${item.price}"/></td>
            <td><c:out value="${item.priceWithVAT}"/></td>
        </tr>
    </c:forEach>
    <c:out value="${pageContext.request.remoteUser}"/>
    </tbody>
</table>
</body>
</html>
