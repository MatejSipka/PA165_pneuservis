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

<my:pageTemplate title="New Order">

<jsp:attribute name="body">
    <form:form method="post" action="${pageContext.request.contextPath}/order/create/submit"
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
            <form:label path="clientId" cssClass="col-sm-2 control-label">Payment Type</form:label>
            <div class="col-sm-10">
                <form:select path="paymentType" cssClass="form-control">
                    <c:forEach items="${paymentTypeValues}" var="value">
                        <form:option value="${value}"></form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="paymentType" cssClass="help-block"/>
            </div>
        </div>
        </c:if>
        <button class="btn btn-primary" type="submit">Confirm</button>
    </form:form>
    <br/>
    <form method="post" action="${pageContext.request.contextPath}/order/create/services">
        <button type="submit" class="btn btn-primary">Add Service</button>
    </form>
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
         <c:forEach items="${order.listOfServices}" var="service">
            <tr>
                <td><c:out value="${service.nameOfService}"/></td>
                <td><c:out value="${service.duration}"/></td>
                <td><c:out value="${service.price}"/></td>
                <td><form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/create/services/${service.id}/remove">
                    <button type="submit" class="btn btn-primary">Remove</button>
                </form></td>
            </tr>
        </c:forEach>

         </tbody>
     </table>

    <form method="post" action="${pageContext.request.contextPath}/order/create/tires">
        <button type="submit" class="btn btn-primary">Add Tire</button>
    </form>
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
        <c:forEach items="${order.listOfTires}" var="tire">
            <tr>
                <td><c:out value="${tire.type}"/></td>
                <td><c:out value="${tire.catalogNumber}"/></td>
                <td><c:out value="${tire.price}"/></td>
                <td><form style="display:inline-block" method="post" action="${pageContext.request.contextPath}/order/create/tires/${tire.id}/remove">
                    <button type="submit" class="btn btn-primary">Remove</button>
                </form></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pageTemplate>
