<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%request.setAttribute("selectedPage", "cart");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>

<!-- Begin page content -->
<main role="main" class="container">

    <H1>Checkout</H1>
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

    <c:if test="${sessionUser.userRole !='ANONYMOUS'}">
        <div class="col-xs-6 col-md-6">
            <form action="./checkout" method="POST">
                <table class="table">
                    <tbody>
                    <tr>
                        <td>Holder Name</td>
                        <td><input type="text" size="36" name="cc_holder_name" value="${sessionUser.firstName} ${sessionUser.secondName}" required></td>
                    </tr>
                    <tr>
                        <td>Card Number</td>
                        <td><input type="text" size="36" name="cc_number" value="${sessionUser.cardNumber}" required></td>
                    </tr>
                    <tr>
                        <td>Issue Number</td>
                        <td><input type="text" size="36" name="cc_issueNum" value="01" required></td>
                    </tr>
                    <tr>
                        <td>Expiry Date</td>
                        <td><input type="text" size="36" name="cc_expiry" value="${sessionUser.cardExpiry}" required></td>
                    </tr>
                    <tr>
                        <td>Cvv Code</td>
                        <td><input type="text" size="36" name="cc_cvv" value="" class="form-group col-md-2" required></td>
                    </tr>
                    </tbody>
                </table>
                <button class="btn ml-2 rounded" type="submit">Pay Now</button>
            </form>
        </div>

        <div class="col-xs-6 col-md-6">
            <table class="table">
                <tr>
                    <th>Item Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>

                <c:forEach var="item" items="${shoppingCartItems}">

                    <tr>
                        <td>${item.name}</td>
                        <td>${item.price}</td>
                        <td>${item.quantity}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>TOTAL</td>
                    <td>${shoppingcartTotal}</td>
                </tr>
            </table>
        </div>
    </c:if>
</main>
<jsp:include page="footer.jsp"/>
