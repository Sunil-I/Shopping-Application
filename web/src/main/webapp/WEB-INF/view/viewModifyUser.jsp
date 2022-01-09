<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="sh.sunil.cart.model.dto.User" %>
<%@page import="sh.sunil.cart.model.dto.UserRole" %>
<c:set var="selectedPage" value="users" scope="request"/>
<jsp:include page="header.jsp"/>

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <H1>User Details ${modifyUser.username} </H1>
        <%-- handle error message --%>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                    ${errorMessage}
            </div>
        </c:if>
        <%-- handle message --%>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                    ${message}
            </div>
        </c:if>


        <form action="./viewModifyUser" method="POST">
            <table class="table">
                <thead>
                </thead>

                <tbody>
                <tr>
                    <td>User ID</td>
                    <td>${modifyUser.id}</td>
                </tr>
                <tr>
                    <td>username</td>
                    <td>${modifyUser.username}</td>
                </tr>
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstName" value="${modifyUser.firstName}"/></td>
                </tr>
                <tr>
                    <td>Second Name</td>
                    <td><input type="text" name="secondName" value="${modifyUser.secondName}"/></td>
                </tr>
                <tr>
                    <td>House Number</td>
                    <td><input type="text" name="houseNumber" value="${modifyUser.address.houseNumber}"/></td>
                </tr>
                <tr>
                    <td>Address Line 1</td>
                    <td><input type="text" name="addressLine1" value="${modifyUser.address.addressLine1}"/></td>
                </tr>
                <tr>
                    <td>Address Line 2</td>
                    <td><input type="text" name="addressLine2" value="${modifyUser.address.addressLine2}"/></td>
                </tr>
                <tr>
                    <td>city</td>
                    <td><input type="text" name="county" value="${modifyUser.address.city}"/></td>
                </tr>
                <tr>
                    <td>county</td>
                    <td><input type="text" name="county" value="${modifyUser.address.county}"/></td>
                </tr>
                <tr>
                    <td>country</td>
                    <td><input type="text" name="country" value="${modifyUser.address.country}"/></td>
                </tr>
                <tr>
                    <td>postcode</td>
                    <td><input type="text" name="postcode" value="${modifyUser.address.postcode}"/></td>
                </tr>
                <tr>
                    <td>telephone</td>
                    <td><input type="text" name="telephone" value="${modifyUser.address.telephone}"/></td>
                </tr>
                <tr>
                    <td>mobile</td>
                    <td><input type="text" name="mobile" value="${modifyUser.address.mobile}"/></td>
                </tr>
                <tr>
                    <td>Credit Card Number</td>
                    <td><input type="text" name="cc_number" value="${modifyUser.cardNumber}"/></td>
                </tr>
                <tr>
                    <td>Credit Card Expiry</td>
                    <td><input type="text" name="cc_number" value="${modifyUser.cardExpiry}"/></td>
                </tr>
                <tr>
                    <td>Credit Card Sort Code</td>
                    <td><input type="text" name="cc_number" value="${modifyUser.cardSortCode}"/></td>
                </tr>
                </tbody>

            </table>



            <c:if test="${sessionUser.userRole !='ADMINISTRATOR'}">
                <h1>User Status and role </h1>
                <table class="table">
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Role</td>
                        <td>${modifyUser.userRole}</td>
                    </tr>
                    <tr>
                        <td>enabled</td>
                        <td><c:if test="${modifyUser.enabled}">ENABLED</c:if><c:if
                                test="${!modifyUser.enabled}">DISABLED</c:if></td>
                    </tr>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
                <h1>Manage User Status and role </h1>
                <table class="table">
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Role</td>
                        <td>
                            <select class="form-control" name="userRole">
                                <c:forEach var="value" items="${UserRole.values()}">
                                    <option value="${value}" <c:if
                                            test="${modifyUser.userRole == value}"> selected </c:if>>${value}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>enabled</td>
                        <td>
                            <select class="form-control" name="userEnabled">
                                <option value="true" <c:if test="${modifyUser.enabled}"> selected </c:if> >ENABLED
                                </option>
                                <option value="false" <c:if test="${!modifyUser.enabled}"> selected </c:if> >DISABLED
                                </option>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:if>

            <input type="hidden" name="username" value="${modifyUser.username}"/>
            <button class="btn" type="submit">Update User ${modifyUser.username}</button>
        </form>
        <h1>Update Password</h1>
        <form action="./viewModifyUser" method="post">
        <table class="table">
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>Password</td>
                <td> <input type="password" name="password"></input></td>
            </tr>
            <tr>
                <td>Re Enter Password</td>
                <td><input type="password" name="password2"></input></td>
            </tr>
            </tbody>
        </table>
            <input type="hidden" name="username" value="${modifyUser.username}"/>
            <input type="hidden" name="action" value="updatePassword"/>
            <button class="btn" type="submit">Update ${modifyUser.username} Password</button>
        </form>
        <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
            <BR>
            <form action="./users">
                <button class="btn" type="submit">Return To Users</button>
            </form>
        </c:if>

    </div>

</main>

<jsp:include page="footer.jsp"/>
