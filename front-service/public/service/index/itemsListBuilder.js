import deleteItem from "../../api/deleteItem.js";

function buildItemsList (item) {
    let itemsList = document.getElementById('itemsList')

    while (itemsList.firstChild) {
        itemsList.removeChild(itemsList.firstChild);
    }

    for (let i = 0; i < item.length; i++) {

        let itemName = document.createElement('a');
        itemName.innerHTML = item[i].name;
        itemName.href = "/readItem?id=" + item[i].id;

        let itemDescription = document.createElement('a');
        itemDescription.innerHTML = item[i].description;

        let itemPrice = document.createElement('a');
        itemPrice.innerHTML = item[i].price;

        let itemCategory = document.createElement('a');
        itemCategory.innerHTML = item[i].category;

        let itemDate = document.createElement('a');
        itemDate.innerHTML = item[i].createdAt;

        let modifyButton = document.createElement('a');
        modifyButton.href = "updateItem?id=" + item[i].id;
        modifyButton.innerHTML = 'Изменить';

        

        let deleteButton = document.createElement('a');
        deleteButton.id = "link";
        deleteButton.innerHTML = 'Удалить';

        deleteButton.addEventListener('click', async () => {
            deleteItem(item[i].id);
        });



        let itemContainer = document.createElement('div')
        itemContainer.className = 'itemContainer';

        itemContainer.appendChild(itemName);
        itemContainer.appendChild(itemDescription);
        itemContainer.appendChild(itemPrice);
        itemContainer.appendChild(itemCategory);
        itemContainer.appendChild(itemDate);
        itemContainer.appendChild(modifyButton);
        itemContainer.appendChild(deleteButton);



        itemsList.appendChild(itemContainer);
    }
}

export default buildItemsList;