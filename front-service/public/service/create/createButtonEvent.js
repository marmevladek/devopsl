import createItem from "../../api/createItem.js";

let sendButton = document.getElementById('sendButton');


sendButton.addEventListener('click', async () => {
  let item = {
      name: document.getElementById("name").value,
      description: document.getElementById("description").value,
      price: document.getElementById("price").value,
      category_id: 1
  };

  createItem(item);  
})