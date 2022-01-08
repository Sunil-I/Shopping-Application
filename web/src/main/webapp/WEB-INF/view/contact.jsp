<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
// request set in controller
   request.setAttribute("selectedPage","contact");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />
<!-- Begin page content -->

<main class="container">
   <H1>Contact Form</H1>
   <%-- handle error message --%>
   <c:if test="${not empty errorMessage}">
      <div class="alert alert-danger alert-dismissible" role="alert">
         <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            ${errorMessage}
      </div>
   </c:if>
   <%-- handle message --%>

   <c:if test="${not empty message}">
      <div class="alert alert-success alert-dismissible" role="alert">
         <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            ${message}
      </div>
   </c:if>

   <form class="form-card" method="POST" id="card-form">
      <div class="form-group">
         <table class="table">
            <tbody>
               <tr>
                  <td>First name</td>
                  <td><input type="text" size="16" name="firstname" value="" required></td>
               </tr>
               <tr>
                  <td>Last Name</td>
                  <td><input type="text" size="16" name="lastname" value="" required></td>
               </tr>
               <tr>
                  <td>Reason for contact</td>
                  <td><input type="text" size="36" name="subject" value="" required></td>
               </tr>
               <tr>
                  <td>Message</td>
                  <td><textarea type="text" name="message" value="" class="form-group" style="margin: 0px 0px 15px; width: 299px; height: 57px;" required> </textarea></td>
               </tr>
            </tbody>
         </table>
         <input name="action" type="hidden" value="email">
         <button class="btn ml-2 rounded" type="submit">Send Email</button>
      </div>
   </form>
</main>


<jsp:include page="footer.jsp" />
