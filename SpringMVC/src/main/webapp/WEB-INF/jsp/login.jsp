<%-- 
    Document   : login
    Created on : 15-Dec-2016, 17:12:17
    Author     : Maros Staurovsky
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:loginTemplate title="Login">
<jsp:attribute name="body">
    <div class="container">

                <form id="loginform" class="form-signin" action="<c:url value='login/check' />" method="POST">
                    <h2 class="form-signin-heading">Please sign in</h2>
                    <label for="inputLogin" class="sr-only">Login</label>
                    <input name="login" type="text" id="inputLogin" class="form-control" placeholder="Login" required autofocus>
                    <label for="inputPassword" class="sr-only">Password</label>
                    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                    <button class="btn btn-lg btn-primary btn-block" name="submit" type="submit">Sign in</button>
                    <my:a href="/createNewAccount" class="btn btn-primary">New Account</my:a>
                </form>
    </div>   

</jsp:attribute>
</my:loginTemplate>