<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="url_2" value="/styles/cart_products_style.css"/>
    <link rel="stylesheet" href="${url_2}">
    <c:url var="url_3" value="/styles/table_style.css"/>
    <link rel="stylesheet" href="${url_3}">
    <c:url var="image_url" value="/images/shopping_cart.png"/>
    <link rel="icon" href="${image_url}" type="images/x-icon">
    <title><c:out value="Cart products"/></title>
</head>
<body>
<main>
    <div id="button_back">
        <form action="pharmacy" method="post">
            <input type="hidden" name="action" value="cart_back">
            <button class="btn btn-info" title=""><c:out value="Back"/></button>
        </form>
    </div>
    <div id="button_pay">
        <form action="pharmacy" method="post">
            <input type="hidden" name="action" value="cart_pay">
            <button class="btn btn-info" title="Pay order"><c:out value="Pay"/></button>
        </form>
    </div>
    <div id="label_cash">
        <p>Order price: ${order.getPrice()} Your cash: ${user.getCash()}</p>
    </div>
    <div id="basic_content">
        <table class="table_blur">
            <thead>
            <tr>
                <th><c:out value="Name"/></th>
                <th><c:out value="Form of issue"/></th>
                <th><c:out value="Count"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="orders" items="${order.getOrderItems()}">
                <tr>
                    <td><c:out value="${orders.getDrug().getDrugName()}"/></td>
                    <td><c:out value="${orders.getDrug().getType()} ${orders.getDrug().getDosage()}Mg"/></td>
                    <td><c:out value="${orders.getCount()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
<c:url var="script_url_1" value="https://unpkg.com/sweetalert/dist/sweetalert.min.js"/>
<script src="${script_url_1}"></script>
<c:url var="javascriptURL" value="/js/main.js"/>
<script type="text/javascript" src="${javascriptURL}"></script>
<c:if test="${errorMessage != null}">
    <script>
        showMessage("Attention", "${errorMessage}");
    </script>
</c:if>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<c:url var="script_url_2" value="https://code.jquery.com/jquery-3.3.1.slim.min.js"/>
<script src="${script_url_2}"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<c:url var="script_url_3" value="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"/>
<script src="${script_url_3}"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<c:url var="script_url_4" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"/>
<script src="${script_url_4}"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>