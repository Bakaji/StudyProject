<%--suppress HtmlFormInputWithoutLabel --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Recherche</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/search/css/main.css">
</head>
<body>
  <main>
    <nav>
        <h1>Recherche par catégorie</h1>
      
        <form
          class="login-form"
          action="./admin"
          id="loginForm"
          method="POST"
        >
          <input name="username" type="text" placeholder="User name" required />
          <input
            name="password"
            type="password"
            placeholder="Password"
            required
          />
          <input type="submit" value="Submit" />
        </form>
      </nav>
  <h1>Recherche</h1>
  <div class="forms-container">
    <div class="form-holder">
      <form action="./result" method="GET">
        <input type="hidden" name="search_type" value="title"/>
        <div class="input-holder">
          <input id="by_title" autocomplete="off" required type="text" name="keyword" />
          <label class="label-name" for="by_title">
            <span class="content-name">Par titre</span>
          </label>
        </div>
        <div class="submit-container">
          <input type="submit" value="Rechercher" />
        </div>
      </form>
    </div>
    <div class="form-holder">
      <form action="./result" method="GET">
        <input type="hidden" name="search_type" value="author"/>
        <div class="input-holder">
          <input id="by_author" autocomplete="off" required type="text" name="keyword" />
          <label class="label-name" for="by_author">
            <span class="content-name">Par auteur</span>
          </label>
        </div>
        <div class="submit-container">
          <input type="submit" value="Rechercher" />
        </div>
      </form>
    </div>
    <div class="form-holder">
      <form action="./result" method="GET">
        <input type="hidden" name="search_type" value="domain"/>
        <div class="input-holder">
          <input id="by_domain" autocomplete="off" required type="text" name="keyword" />
          <label class="label-name" for="by_domain">
            <span class="content-name">Par domaine</span>
          </label>
        </div>
        <div class="submit-container">
          <input type="submit" value="Rechercher" />
        </div>
      </form>
    </div>
  </div>
</main>
</body>
</html>
