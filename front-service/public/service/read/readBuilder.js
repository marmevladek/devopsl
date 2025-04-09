import readItem from "../../api/readItem.js";

let params = new URLSearchParams(document.location.search);
let itemId = params.get("id"); 

let json = await readItem(itemId);

document.getElementById("name").innerHTML = json.name;
document.getElementById("description").innerHTML = json.description;
document.getElementById("fullDescription").innerHTML = json.fullDescription;
document.getElementById("price").innerHTML = json.price;
document.getElementById("itemImage").src = json.linkImage;
document.getElementById("linkImage").innerHTML = json.linkImage;
document.getElementById("phoneNumber").innerHTML = json.phoneNumber;
document.getElementById("email").innerHTML = json.email;