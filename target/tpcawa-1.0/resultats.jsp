<%--
  Created by IntelliJ IDEA.
  User: Baelhadj
  Date: 21-Oct-20
  Time: 21:48

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/results/css/main.css"/>
    <title>Résultats : ${keyword}</title>
</head>
<body>
<script>
    const keyword = "${keyword}";
</script>
<nav>
    <h1>Recherche ${search_type}</h1>
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
    <header>
        <h1>Résultats de recherche : <span>${keyword}</span></h1>
        <h2>Nombre des résultats : <span>${length}</span></h2>
    </header>
    <div class="header">
        <div>Titre</div>
        <div>Auteur</div>
        <div>Domaine</div>
    </div>
    <div class="table-container">
        <table>
            <c:forEach var="b" items="${books}">
                <tr>
                    <td class="title result-container"
                        data-content="${b.getTitle()}"
                        data-issn="${b.getIssn()}"
                    >
                    </td>
                    <td class="author result-container"
                        data-content="${b.getAuthorName()}"
                        data-issn="${b.getIssn()}"
                    >
                    </td>
                    <td class="domain result-container"
                        data-content="${b.getDomain()}" data-issn="${b.getIssn()}"
                    >
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
<script>
    function initData(key) {
        document.querySelectorAll(".result-container").forEach(element => {
            let data = element.dataset.content;
            if (data) {
                if (element.classList.contains("${type}")) {
                    data = data
                        .replace(
                            new RegExp(key, "ig"),
                            '<span class="red">' + key + "</span>"
                        )
                        .replace(new RegExp(/\s/gi), " ");
                }
                let issn = element.dataset.issn;
                let link = "#";
                if (issn)
                    link = "${pageContext.request.contextPath}/book-details?book_issn=" + issn;
                let anchor = document.createElement("a");
                anchor.href = link;
                anchor.innerHTML = "<p>" + data + "</p>";
                element.appendChild(anchor);
            }
        });
    }

    initData(keyword);
</script>
</body>
</html>
