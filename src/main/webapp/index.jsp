<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html class="no-js" lang="">
<head>
    <title></title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/home/css/main.css" type="text/css">
    <script src="${pageContext.request.contextPath}/assets/home/js/code.js"></script>
</head>

<body>

<main class="container">
    <div class="header-holder">
        <h1>Bienvenue</h1>
    </div>
    <section class="redirect">
        <h2>Rechercher livres</h2>
        <div class="section-content">
            <div class="redirect-buttons">
                <a href="${pageContext.request.contextPath}/rechercher">
                    <button>Rechercher</button>
                </a>
            </div>
        </div>
    </section>
    <section class="separator-holder">
        <h2>OU</h2>
        <div class="separator"></div>
    </section>
    <section class="admin-login">
        <h2>S'authentifier comme administrateur</h2>
        <div class="section-content">
            <form class="login-form" action="${pageContext.request.contextPath}/admin"
                  id="loginForm" method="POST">
                <input name="username" type="text" placeholder="User name" required/>
                <input name="password" type="password" placeholder="Password" required/>
                <input type="submit" value="Submit"/>
            </form>
        </div>
    </section>
</main>
</body>
</html>
