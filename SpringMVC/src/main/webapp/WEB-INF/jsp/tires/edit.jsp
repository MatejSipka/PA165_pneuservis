<%--
    Document   : list
    Created on : 15-Dec-2016, 23:56:19
    Author     : Matej Sipka
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="New tire">
    <jsp:attribute name="body">
        <c:if test="${not empty Admin}">   
            <form:form method="post" action="${pageContext.request.contextPath}/tires/edit/${tireId}"
                       modelAttribute="tireCreate" cssClass="form-horizontal">
                <div class="form-group ${manufacturer_error?'has-error':''}">
                    <form:label path="manufacturer" cssClass="col-sm-2 control-label">Manufacturer</form:label>
                        <div class="col-sm-10">
                        <form:select path="manufacturer" cssClass="form-control">
                            <form:options items="${manufacturers}"/>
                        </form:select>
                        <form:errors path="manufacturer" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${type_error?'has-error':''}">
                    <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
                        <div class="col-sm-10">
                        <form:select path="type" cssClass="form-control">
                            <form:options items="${seasons}"/>
                        </form:select>
                        <form:errors path="type" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${price_error?'has-error':''}">
                    <form:label path="price" cssClass="col-sm-2 control-label">Price</form:label>
                        <div class="col-sm-10">
                        <form:input path="price" cssClass="form-control"/>
                        <form:errors path="price" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${catalogNumber_error?'has-error':''}">
                    <form:label path="catalogNumber" cssClass="col-sm-2 control-label">Catalog Number</form:label>
                        <div class="col-sm-10">
                        <form:input path="catalogNumber" cssClass="form-control"/>
                        <form:errors path="catalogNumber" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${tireSize_error?'has-error':''}">
                    <form:label path="tireSize" cssClass="col-sm-2 control-label">Size</form:label>
                        <div class="col-sm-10">
                        <form:input path="tireSize" cssClass="form-control"/>
                        <form:errors path="tireSize" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${profile_error?'has-error':''}">
                    <form:label path="profile" cssClass="col-sm-2 control-label">Profile</form:label>
                        <div class="col-sm-10">
                        <form:input path="profile" cssClass="form-control"/>
                        <form:errors path="profile" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${diameter_error?'has-error':''}">
                    <form:label path="diameter" cssClass="col-sm-2 control-label">Diameter</form:label>
                        <div class="col-sm-10">
                        <form:input path="diameter" cssClass="form-control"/>
                        <form:errors path="diameter" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${typeOfCar_error?'has-error':''}">
                    <form:label path="typeOfCar" cssClass="col-sm-2 control-label">Car Type</form:label>
                        <div class="col-sm-10">
                        <form:input path="typeOfCar" cssClass="form-control"/>
                        <form:errors path="typeOfCar" cssClass="help-block"/>
                    </div>
                </div>
                <button class="btn btn-primary" type="submit">Update tire</button>
            </form:form>
        </c:if>

        <c:if test="${not empty User}">   
            You are not permitted to see this!
        </c:if>
    </jsp:attribute>
</my:pageTemplate>
