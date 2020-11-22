let message = document.createElement("p");
let msgContainer = document.createElement("div");
let container = document.createElement("div");
let containerWrapper = document.createElement("div");
let yes = document.createElement("button");
let no = document.createElement("button");
let actContainer = document.createElement("div");

containerWrapper.style.height = "100vh";
containerWrapper.style.width = "100vw";
containerWrapper.style.position = "fixed";
containerWrapper.style.top = "0";
containerWrapper.style.left = "0";
containerWrapper.style.backgroundColor = "#00000099";
containerWrapper.style.display = "none";

container.style.height = "200px";
container.style.width = "90%";
container.style.backgroundColor = "white";
container.style.position = "fixed";
container.style.top = "50%";
container.style.left = "50%";
container.style.transform = "translate(-50%,-50%)";
container.style.flexDirection = "column";
container.style.display = "flex";


msgContainer.style.flex = "1";
msgContainer.style.display = "flex";
msgContainer.style.justifyContent = "center";
msgContainer.style.alignItems = "center";
msgContainer.style.borderBottom = "1px solid #fafafa";


actContainer.style.flexBasis = "70px";
actContainer.style.display = "flex";
actContainer.style.justifyContent = "space-around";
actContainer.style.alignItems = "center";

yes.innerText = "Oui";
no.innerText = "Non";

yes.style.backgroundColor = "red";

yes.style.height = no.style.height = "40px"
yes.style.width = no.style.width = "150px"

msgContainer.appendChild(message);
actContainer.appendChild(yes);
actContainer.appendChild(no);

container.appendChild(msgContainer);
container.appendChild(actContainer);
containerWrapper.appendChild(container);
document.body.appendChild(containerWrapper);

let eventCallback = () => {
}

function hideContainer() {
    yes.removeEventListener("click", eventCallback);
    message.innerText = "";
    containerWrapper.style.display = "none";
}

no.addEventListener("click", hideContainer);


export function showDialog(confirmationMessage, callback) {
    containerWrapper.style.display = "flex";
    message.innerText = confirmationMessage;
    eventCallback = () => {
        callback();
        hideContainer();
    }
    yes.addEventListener("click", eventCallback);
}
