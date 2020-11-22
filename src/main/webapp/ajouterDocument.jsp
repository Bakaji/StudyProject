<%--
  Created by IntelliJ IDEA.
  User: Baelhadj
  Date: 21-Oct-20
  Time: 21:48

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/add-author/css/main.css">
    <title>Ajouter Document</title>
</head>
<body>
<div class="container">

    <h1>Ajouter Document au ${authorFullName}</h1>
    <form method="post" action="${pageContext.request.contextPath}/admin/authors/add-book">
        <input type="hidden" name="authorId" value="${authorId}">
        <table>
            <tr>
                <td><label for="book-issn">ISSN</label></td>
                <td><input id="book-issn" required type="text" name="book-issn"/></td>
            </tr>
            <tr>
                <td><label for="book-title">Titre</label></td>
                <td><input id="book-title" required type="text" name="book-title"/></td>
            </tr>
            <tr class="text-field">
                <td><label for="book-resume">Résumé</label></td>
                <td class="text-field">
                    <textarea id="book-resume" aria-grabbed="false" required name="book-resume"></textarea>
                </td>
            </tr>
            <tr>
                <td><label for="book-page-nbr">Nombre des pages</label></td>
                <td><input id="book-page-nbr" required type="number" name="book-page-nbr"/></td>
            </tr>
            <tr>
                <td><label for="book-domain">Domaine</label></td>
                <td><input id="book-domain" required type="text" name="book-domain"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="btn-container">
                        <input type="submit" value="Ajouter"/>
                        <input class="danger" type="button" value="Annuler"/>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<script>
    document.querySelector("input[type='button']").addEventListener("click", function () {
        window.location.href = "${pageContext.request.contextPath}/admin/"
    })
</script>
</body>
</html>
