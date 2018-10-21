<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.onlinepharmacy.main.UserRole" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="url_2" value="/styles/users_style.css"/>
    <link rel="stylesheet" href="${url_2}">
    <c:url var="url_3" value="/styles/table_style.css"/>
    <link rel="stylesheet" href="${url_3}">
    <c:url var="image_url" value="/images/users.png"/>
    <link rel="icon" href="${image_url}" type="images/x-icon">
    <title><c:out value="Users"/></title>
</head>
<body>
<div id="wrap">
    <header>
        <div class="title"><c:out value="Online-pharmacy"/></div>
        <div id="button_back">
            <form method="post" action="pharmacy">
                <input type="hidden" name="action" value="back">
                <button class="btn btn-info"><c:out value="Back"/></button>
            </form>
        </div>
    </header>
</div>
<main>
    <div id="basic_content">
        <table class="table_blur">
            <thead>
            <tr>
                <th><c:out value="Name"/></th>
                <th><c:out value="Last name"/></th>
                <th><c:out value="E-mail"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="element" items="${users}">
                <tr>
                    <td><c:out value="${element.getUserName()}"/></td>
                    <td><c:out value="${element.getUserLastName()}"/></td>
                    <td><c:out value="${element.getEmail()}"/></td>
                    <c:if test="${element.getRole() == UserRole.CLIENT}">
                        <td>
                            <form action="pharmacy" method="post">
                                <input type="hidden" name="action" value="delete_client">
                                <input type="hidden" name="client_id" value="${element.getId()}">
                                <button class="btn btn-info"><c:out value="Delete"/></button>
                            </form>
                        </td>
                        <td>
                            <form action="pharmacy" method="post">
                                <input type="hidden" name="action" value="show_orders">
                                <input type="hidden" name="client_id" value="${element.getId()}">
                                <button class="btn btn-info"><c:out value="Orders"/></button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<c:url var="script_1" value="https://code.jquery.com/jquery-3.3.1.slim.min.js"/>
<script src="${script_1}"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<c:url var="script_2" value="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"/>
<script src="${script_2}"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<c:url var="script_3" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"/>
<script src="${script_3}"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>