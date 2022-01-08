<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%request.setAttribute("selectedPage", "login");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Login</H1>
    <%-- handle error message --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
                ${errorMessage}
        </div>
    </c:if>
    <%-- handle message --%>
    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
                ${message}
        </div>
    </c:if>
    <form action="./login" method="post">
        <input type="hidden" name="action" value="login">
        <p>Username <input type="text" name="username" required></input></p><BR>
        <p>Password <input type="password" name="password" required></input></p>
        <p>
            <button type="submit">Log In</button>
        </p>
    </form>

    <a href="./register">Create a new account</a>
</main>


<jsp:include page="footer.jsp"/>
