<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="sh.sunil.cart.model.dto.User" %>
<%@page import="sh.sunil.cart.model.dto.UserRole" %>
<jsp:include page="header.jsp"/>

<!-- Begin page content -->
<main role="main" class="container">
    <%-- handle error message --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span>
            </button>
                ${errorMessage}
        </div>
    </c:if>
    <%-- handle message --%>

    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span>
            </button>
                ${message}
        </div>
    </c:if>
    <div>
        <H1>View Item: ${modifyItem.name} </H1>

        <form>
            <table class="table">
                <thead>
                </thead>
                <tbody>
                <tr>
                    <td>Item ID</td>
                    <td>${modifyItem.id}</td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="name" value="${modifyItem.name}" readonly/></td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td><input type="text" name="price" value="${modifyItem.price}" readonly/></td>
                </tr>
                <tr>
                    <td>Quantity</td>
                    <td><input type="text" name="quantity" value="${modifyItem.quantity}" readonly/></td>
                </tr>
                </tbody>

            </table>
        </form>

        <form action="./catalog">
            <button class="btn" type="submit">Return To Catalog</button>
        </form>

    </div>

</main>

<jsp:include page="footer.jsp"/>