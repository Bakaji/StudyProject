function show(type, message) {
    let body = document.querySelector("#app");
    let windowMessage = document.createElement("div");
    windowMessage.style.height = "40px";
    windowMessage.style.width = "100%";
    windowMessage.style.display = "flex";
    windowMessage.style.justifyContent = "center";
    windowMessage.style.backgroundColor = type === "error" ? "rgba(255,0,0,0.7)" : "rgba(6,165,0,0.7)";
    windowMessage.style.color = "white";
    windowMessage.style.alignItems = "center";
    windowMessage.innerText = message;
    body.insertBefore(windowMessage, body.firstChild.nextSibling);
    setTimeout(function () {
        body.removeChild(windowMessage)
    }, 4000)
}

function showMessage(message) {
    show("message", message)
}

function showError(message) {
    show("error", message)
}
