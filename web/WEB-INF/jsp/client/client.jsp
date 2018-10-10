<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.onlinepharmacy.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="style_url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${style_url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="style_url_2" value="${pageContext.request.contextPath}/styles/client_style.css"/>
    <link rel="stylesheet" href="${style_url_2}">
    <c:url var="image_url" value="/images/cross.png"/>
    <link rel="icon" href="${image_url}" type="images/x-icon">
    <title><c:out value="Online-pharmacy"/></title>
</head>
<body>
<div id="modal_balance" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><c:out value="Replenishment of balance"/></h4>
                <button type="button" class="close" data-dismiss="modal">×</button>
            </div>
            <div class="modal-body">
                <form method="post" action="pharmacy">
                    <div class="form-group">
                        <label for="money"><c:out value="Amount of money(max 1000):"/></label>
                        <input type="number" class="form-control" name="money" id="money" min="1" max="1000"
                               placeholder="Enter amount of money" required>
                    </div>
                    <div>
                        <input type="hidden" name="action" value="balance">
                        <input type="submit" class="btn btn-info" value="Replenish?">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="modal_order" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><c:out value="Order"/></h4>
                <button type="button" class="close" data-dismiss="modal">×</button>
            </div>
            <div class="modal-body">
                <form method="post" action="pharmacy">
                    <div class="form-group">
                        <label for="name"><c:out value="Name:"/></label>
                        <input type="text" pattern="[A-Z]{1}[a-zA-Z-]*" class="form-control" name="name" id="name"
                               placeholder="Enter medicine name" required>
                    </div>
                    <div class="form-group">
                        <label for="dosage"><c:out value="Dosage:"/></label>
                        <input type="number" name="dosage" class="form-control" id="dosage" min="1" max="20000"
                               placeholder="Enter medicine dosage" required>
                    </div>
                    <div class="form-group">
                        <label for="count"><c:out value="Count:"/></label>
                        <input type="number" name="count" id="count" class="form-control" min="1" max="10"
                               placeholder="Enter medicine count" required>
                    </div>
                    <div>
                        <input type="hidden" name="action" value="buy_drug">
                        <input type="submit" class="btn btn-info" value="Buy">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="wrap">
    <header>
        <c:set var="user" value="${user}"/>
        <c:set var="name" value="${user.getUserName()}"/>
        <c:set var="surname" value="${user.getUserLastName()}"/>
        <c:set var="cash" value="${user.getCash()}"/>
        <div class="title"><c:out value="Online-pharmacy"/></div>
        <div class="dropdown" id="user_dropdown">
            <button class="btn btn-info dropdown-toggle" type="button" id="dropdownMenuButton1" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                <c:out value="${name} ${surname}"/>
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                <button class="dropdown-item" data-toggle="modal" data-target="#modal_balance"><c:out
                        value="Cash: ${cash} BR"/></button>
                <button class="dropdown-item"><c:out value="Cart products"/></button>
                <button data-toggle="modal" data-target="#modal_order" class="dropdown-item"><c:out
                        value="Buy"/></button>
                <form method="post" action="pharmacy">
                    <input type="hidden" name="action" value="logout">
                    <button class="dropdown-item"><c:out value="Logout"/></button>
                </form>
            </div>
        </div>
        <form method="post" action="pharmacy">
            <div class="input-form">
                <div class="input-group">
                    <input type="hidden" name="action" value="search_client">
                    <input type="text" title="Enter name of the medicine" class="form-control"
                           pattern="[A-Z]{1}[a-zA-Z-]*" name="drug_name" placeholder="Name of the medicine" required>
                    <div class="input-group-btn">
                        <button title="Search medicine" class="btn btn-info" type="submit">Search</button>
                    </div>
                </div>
                <div class="checkbox">
                    <label style="color: white"><input type="checkbox" name="checkbox" value="true"><c:out
                            value=" Including analogues"/></label>
                </div>
            </div>
        </form>
    </header>
</div>
<main>
    <div class="dropdown" id="sorted_dropdown">
        <button class="btn btn-info dropdown-toggle" type="button" title="Sorted medicine by some parameters"
                id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <c:out value="Sorted by"/>
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <form action="pharmacy" method="post">
                <input type="hidden" name="action" value="sorted_by_name_client">
                <button class="dropdown-item"><c:out value="Name"/></button>
            </form>
            <form method="post" action="pharmacy">
                <input type="hidden" name="action" value="sorted_by_price_client">
                <button class="dropdown-item"><c:out value="Price"/></button>
            </form>
        </div>
    </div>
    <div id="button_all">
        <form action="pharmacy" method="post">
            <input type="hidden" name="action" value="all_client">
            <button class="btn btn-info" title="Show all medicines"><c:out value="All"/></button>
        </form>
    </div>
    <div id="basic_content">
        <table class="table_blur">
            <thead>
            <tr>
                <th><c:out value="Name"/></th>
                <th><c:out value="Form of issue"/></th>
                <th><c:out value="Company/Country"/></th>
                <th><c:out value="Substance"/></th>
                <th><c:out value="Recipe"/></th>
                <th><c:out value="Price"/></th>
                <th><c:out value="Count"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="element" items="${drugs}">
                <tr>
                    <td><c:out value="${element.getDrugName()}"/></td>
                    <td><c:out value="${element.getType()} ${element.getDosage()}Mg"/></td>
                    <td><c:out value="${element.getCompany()}/${element.getCompanyCountry()}"/></td>
                    <td><c:out value="${element.getSubstance().getName()}"/></td>
                    <td>
                        <c:if test="${element.isRequiredRecipe() == true}">
                            <c:out value="Yes"/>
                        </c:if>
                        <c:if test="${element.isRequiredRecipe() == false}">
                            <c:out value="No"/>
                        </c:if>
                    </td>
                    <td><c:out value="${element.getPrice()}"/></td>
                    <td><c:out value="${element.getDrugsCount()}"/></td>
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
<script src="${script_url_2}" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<c:url var="script_url_3" value="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"/>
<script src="${script_url_3}" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<c:url var="script_url_4" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"/>
<script src="${script_url_4}" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>