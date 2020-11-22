<%--
  Created by IntelliJ IDEA.
  User: Baelhadj
  Date: 21-Oct-20
  Time: 21:48

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>admin</title>
    <link href="${pageContext.request.contextPath}/assets/admin/css/app.css" rel="stylesheet">
</head>
<body>
<script>
    localStorage.setItem("base_url", "${pageContext.request.contextPath}");
</script>
<noscript><strong>We're sorry but admin doesn't work properly without JavaScript enabled. Please enable it to
    continue.</strong></noscript>
<div id="app"></div>
<script src="${pageContext.request.contextPath}/assets/message_handler.js"></script>
<script src="${pageContext.request.contextPath}/assets/admin/js/chunk-vendors.js"></script>
<script src="${pageContext.request.contextPath}/assets/admin/js/app.js"></script>
</body>
</html>
