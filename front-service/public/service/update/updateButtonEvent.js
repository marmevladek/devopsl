import readItem from "../../api/readItem.js";
import updateItem from "../../api/updateItem.js";

let updateButton = document.getElementById('updateButton');

let params = new URLSearchParams(document.location.search);
let itemId = params.get("id"); 

let json = await readItem(itemId);



document.getElementById("name").value = json.name;
document.getElementById("description").value = json.description;
document.getElementById("price").value = json.price;
//document.getElementById("caterogy").value = json.category_id;

updateButton.addEventListener('click', async () => {

    let item = {
      name: document.getElementById("name").value,
      description: document.getElementById("description").value,
      price: document.getElementById("price").value,
      category_id: 1
    };
        
    updateItem(json.id, item)
  
  })