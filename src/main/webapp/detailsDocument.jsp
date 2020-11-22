<%--suppress ALL --%>
<%--
  Created by IntelliJ IDEA.
  User: Baelhadj
  Date: 21-Oct-20
  Time: 21:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Détails livre :${book.getTitle()}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/details/css/main.css"/>
</head>
<body>
<nav>
    <h1>Détails de livre</h1>
    <a href="${pageContext.request.contextPath}/rechercher">
        <button>autre recherche</button>
    </a>
    <form
            class="login-form"
            action="${pageContext.request.contextPath}/admin"
            id="loginForm"
            method="POST"
    >
        <input name="username" type="text" placeholder="User name" required/>
        <input
                name="password"
                type="password"
                placeholder="Password"
                required
        />
        <input type="submit" value="Submit"/>
    </form>
</nav>
<main>
    <div class="header">
        <h1>Details de livre : <span>${book.getTitle()}</span></h1>
        <a id="anchor" href="#">
            <button>Go back</button>
        </a>
    </div>
    <div class="details-container">
        <h2>ISSN</h2>
        <p>${book.getIssn()}</p>
        <h2>Résumé</h2>
        <p>
            ${book.getResume()}
        </p>
        <h2>Crée par</h2>
        <p>${book.getAuthorName()}</p>
        <h2>Domaine</h2>
        <p>${book.getDomain()}</p>
        <h2>Nombres des pages</h2>
        <p>${book.getPagesNumber()} pages</p>
    </div>
</main>
</body>
<script>
    const doc = document.documentElement;
    const navHeader = document.querySelector('nav h1');
    window.onscroll = function () {
        const currentScrollPos = window.pageYOffset;
        if (currentScrollPos > 50) {
            doc.style.setProperty("--background-scss-color", "white");
            doc.style.setProperty("--foreground-scss-color", "#2287d5");
            navHeader.innerHTML = "Details de livre : ${book.getTitle()}"
        } else {
            doc.style.setProperty("--background-scss-color", "#2287d5");
            doc.style.setProperty("--foreground-scss-color", "white");
            navHeader.innerHTML = "Details de livre"
        }
        console.log(doc.style.getPropertyValue("--background-scss-color"));
    };
    const anchor = document.getElementById('anchor');
    anchor.addEventListener("click", function (e) {
        e.preventDefault();
        window.history.back();
    })
</script>
</html>
