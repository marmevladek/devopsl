import readItem from "../../api/readItem.js";
import updateItem from "../../api/updateItem.js";

let updateButton = document.getElementById('button');

let params = new URLSearchParams(document.location.search);
let itemId = params.get("id"); 

let json = await readItem(itemId);

document.getElementById("name").value = json.name;
document.getElementById("description").value = json.description;
document.getElementById("price").value = json.price;
document.getElementById("fullDescription").value = json.fullDescription;
document.getElementById("itemPhoto").src = json.linkImage;
document.getElementById("linkImage").value = json.linkImage;
document.getElementById("phoneNumber").value = json.phoneNumber;
document.getElementById("email").value = json.email;


updateButton.addEventListener('click', async () => {

    let item = {
      name: document.getElementById("name").value,
      description: document.getElementById("description").value,
      fullDescription: document.getElementById("fullDescription").value,
      price: document.getElementById("price").value,
      linkImage: document.getElementById("linkImage").value,
      phoneNumber: document.getElementById("phoneNumber").value,
      email: document.getElementById("email").value,
      category_id: 1
    };
    
    console.log(item);

    updateItem(json.id, item)
  
  })