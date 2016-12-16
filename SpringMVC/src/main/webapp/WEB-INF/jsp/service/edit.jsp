<%--
  Created by IntelliJ IDEA.
  User: Peeve
  Date: 16.12.2016
  Time: 12:16
  To change this template use File | Settings | File Templates.
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
            <form:form method="post" action="${pageContext.request.contextPath}/service/edit/${serviceId}"
                       modelAttribute="serviceCreate" cssClass="form-horizontal">
                <div class="form-group ${duration_error?'has-error':''}">
                    <form:label path="duration" cssClass="col-sm-2 control-label">Duration (hours)</form:label>
                    <div class="col-sm-10">
                        <form:input path="duration" cssClass="form-control"/>
                        <form:errors path="duration" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${nameOfService_error?'has-error':''}">
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
                    <form:label path="typeOfCar" cssClass="col-sm-2 control-label">Car Type</form:label>
                    <div class="col-sm-10">
                        <form:input path="typeOfCar" cssClass="form-control"/>
                        <form:errors path="typeOfCar" cssClass="help-block"/>
                    </div>
                </div>
                <button class="btn btn-primary" type="submit">Update service</button>
            </form:form>
        </c:if>

        <c:if test="${not empty User}">
            You are not permitted to see this!
        </c:if>
    </jsp:attribute>
</my:pageTemplate>

