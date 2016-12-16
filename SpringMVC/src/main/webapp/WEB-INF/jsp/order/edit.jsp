<%--
    Document   : create
    Author     : vit.holasek
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Edit Order">

<jsp:attribute name="body">
    <form:form method="post" action="${pageContext.request.contextPath}/order/${order.id}/edit"
               modelAttribute="order" cssClass="form-horizontal">
        <div class="form-group ${note_error?'has-error':''}">
            <form:label path="note" cssClass="col-sm-2 control-label">Note</form:label>
            <div class="col-sm-10">
                <form:input path="note" cssClass="form-control"/>
                <form:errors path="note" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${paymentType_error?'has-error':''}">
            <form:label path="paymentType" cssClass="col-sm-2 control-label">Payment Type</form:label>
            <div class="col-sm-10">
                <form:select path="paymentType" cssClass="form-control">
                    <c:forEach items="${paymentTypeValues}" var="value">
                        <form:option value="${value}"></form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="paymentType" cssClass="help-block"/>
            </div>
        </div>
        <c:if test="${not empty Admin}">
        <div class="form-group ${clientId_error?'has-error':''}">
            <form:label path="clientId" cssClass="col-sm-2 control-label">Client ID</form:label>
            <div class="col-sm-10">
                <form:input path="clientId" cssClass="form-control"/>
                <form:errors path="clientId" cssClass="help-block"/>
            </div>
        </div>
        </c:if>
        <button class="btn btn-primary" type="submit">Confirm</button>
    </form:form>
    <br/>

</jsp:attribute>
</my:pageTemplate>
