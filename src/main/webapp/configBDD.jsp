<%-- Created by IntelliJ IDEA. User: Baelhadj Date: 21-Oct-20 Time: 21:48 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta
            name="viewport"
            content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
    />
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/assets/add-author/css/main.css"
    />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.0/axios.min.js"></script>
    <title>Configuration Base des donnnées</title>
</head>
<body>

<div class="container">
    <h1>Base des données</h1>
    <form>
        <table>
            <tr>
                <td><label for="db-url">URL</label></td>
                <td>
                    <input
                            id="db-url"
                            required
                            type="text"
                            name="db-url"
                            value="localhost"
                    />
                </td>
            </tr>
            <tr>
                <td><label for="db-port">Port</label></td>
                <td>
                    <input
                            id="db-port"
                            required
                            type="number"
                            value="3306"
                            name="db-port"
                    />
                </td>
            </tr>
            <tr>
                <td><label for="db-user">Nom d'utilisateur</label></td>
                <td>
                    <input
                            id="db-user"
                            required
                            type="text"
                            value="root"
                            name="db-user"
                    />
                </td>
            </tr>
            <tr>
                <td><label for="db-pass">Mot de passe</label></td>
                <td>
                    <input id="db-pass" type="password" name="db-pass"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="btn-container">
                        <input id="submitter" type="button" value="Ajouter"/>
                        <input id="cancel" class="danger" type="button" value="Annuler"/>
                    </div>
                </td>
            </tr>
            <tr id="errorSection" style="display: none;background-color: rgba(264 ,0, 0 , 0.64);align-items: center;width: 100%;">
                <td><label for="db-user">Erreur</label></td>
                <td>
                    <p></p>
                </td>
            </tr>
        </table>
    </form>
</div>
<script>
    document
        .getElementById('cancel')
        .addEventListener("click", function () {
            window.location.href = "${pageContext.request.contextPath}/";
        });
    document.getElementById('submitter').addEventListener("click", function tryConnect(e) {
        e.preventDefault();
        let errorSection = document.getElementById('errorSection');
        let errorContainer = document.querySelector('#errorSection td p');
        try {
            let data = {
                url: document.getElementById('db-url').value,
                port: document.getElementById('db-port').value,
                username: document.getElementById('db-user').value,
                password: document.getElementById('db-pass').value,
            }
            console.log(data);
            axios.post("${pageContext.request.contextPath}/config/database/", data).then(resp => {
                if (resp.status === 200) {
                    errorSection.style.display = "none";
                    errorContainer.innerHTML = ""
                    window.location.replace("${pageContext.request.contextPath}/");
                }
            }).catch(() => {
                errorSection.style.display = "table-row";
                errorContainer.innerHTML = "les identifients du connexion sont erronés"
            })
        } catch {
        }
        return false;
    })
</script>
</body>
</html>
