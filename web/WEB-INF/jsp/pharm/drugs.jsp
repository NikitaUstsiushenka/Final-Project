<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="url_2" value="/styles/drugs_style.css"/>
    <link rel="stylesheet" href="${url_2}">
    <c:url var="url_3" value="/styles/table_style.css"/>
    <link rel="stylesheet" href="${url_3}">
    <c:url var="image_url" value="/images/cross.png"/>
    <link rel="icon" href="${image_url}" type="images/x-icon">
    <title><c:out value="Medicines"/></title>
</head>
<body>
<div id="wrap">
    <header>
        <div class="title"><c:out value="Online-pharmacy"/></div>
        <div id="button_back">
            <form method="post" action="pharmacy">
                <input type="hidden" name="action" value="back_pharm">
                <button class="btn btn-info"><c:out value="Back"/></button>
            </form>
        </div>
    </header>
</div>
<main>
    <div class="dropdown" id="sorted_dropdown">
        <button class="btn btn-info dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <c:out value="Sorted by"/>
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <form action="pharmacy" method="post">
                <input type="hidden" name="action" value="sorted_by_name_admin">
                <button class="dropdown-item"><c:out value="Name"/></button>
            </form>
            <form method="post" action="pharmacy">
                <input type="hidden" name="action" value="sorted_by_price_admin">
                <button class="dropdown-item"><c:out value="Price"/></button>
            </form>
        </div>
    </div>
    <div id="button_all">
        <form action="pharmacy" method="post">
            <input type="hidden" name="action" value="all_pharm">
            <button class="btn btn-info"><c:out value="All"/></button>
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