<%--
    Document   : create
    Created on : 15-Dec-2016, 23:58:12
    Author     : Ivan Moscovic
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="New service">

<jsp:attribute name="body">
    <c:if test="${not empty Admin}">
        <c:if test="${not empty alert_failure}">
            <div class="alert alert-danger" role="alert"><c:out value="${alert_failure}"/></div>
        </c:if>
        <form:form method="post" action="${pageContext.request.contextPath}/service/create"
               modelAttribute="serviceCreate" cssClass="form-horizontal">


        <div class="form-group ${duration_error?'has-error':''}">
            <form:label path="duration" cssClass="col-sm-2 control-label">Duration (hours)</form:label>
            <div class="col-sm-10">
                <form:input path="duration" cssClass="form-control"/>
                <form:errors path="duration" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="nameOfService" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="nameOfService" cssClass="form-control"/>
                <form:errors path="nameOfService" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${price_error?'has-error':''}">
            <form:label path="price" cssClass="col-sm-2 control-label">Price</form:label>
            <div class="col-sm-10">
                <form:input path="price" cssClass="form-control"/>
                <form:errors path="price" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
            <div class="col-sm-10">
                <form:input path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
        </div>


        <div class="form-group ${typeOfCar_error?'has-error':''}">
            <form:label path="typeOfCar" cssClass="col-sm-2 control-label">Car type</form:label>
            <div class="col-sm-10">
                <form:input path="typeOfCar" cssClass="form-control"/>
                <form:errors path="typeOfCar" cssClass="help-block"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create service</button>
    </form:form>
</c:if>

</jsp:attribute>
</my:pageTemplate>
