<%--
  Created by IntelliJ IDEA.
  User: Baelhadj
  Date: 21-Oct-20
  Time: 21:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/add-author/css/main.css">
    <title>Ajouter Auteur</title>
</head>
<body>
<div class="container">

    <h1>Ajouter auteur</h1>
    <form method="post" action="${pageContext.request.contextPath}/admin/add-author/">
        <table>
            <tr>
                <td><label for="author-f_name">Nom</label></td>
                <td><input id="author-f_name" required type="text" name="author-first-name"/></td>
            </tr>
            <tr>
                <td><label for="author-l_name">Prénom</label></td>
                <td><input id="author-l_name" required type="text" name="author-last-name"/></td>
            </tr>
            <tr>
                <td><label for="author-b_day">Date de naissance</label></td>
                <td><input id="author-b_day" required type="date" value="1997-11-05" name="author-birth-day"/></td>
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
    document.querySelector("input[type='button']").addEventListener("click",function (){
        window.location.href="${pageContext.request.contextPath}/admin/"
    })
</script>
</body>
</html>
