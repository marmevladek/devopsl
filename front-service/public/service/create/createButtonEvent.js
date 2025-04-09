import createItem from "../../api/createItem.js";

let sendButton = document.getElementById('button');


sendButton.addEventListener('click', async () => {
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

  createItem(item);  
})