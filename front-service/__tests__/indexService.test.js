/**
 * @jest-environment jsdom
 */

import buildItemsList from "../public/service/index/itemsListBuilder.js";
import deleteItem from "../public/api/deleteItem.js";

jest.mock("../public/api/deleteItem.js", () => jest.fn());

describe("buildItemsList", () => {
  let itemsList;

  beforeEach(() => {
    document.body.innerHTML = '<div id="itemsList"></div>';
    itemsList = document.getElementById("itemsList");
    deleteItem.mockClear();
  });

  it("clears the itemsList container before adding new elements", () => {
    itemsList.innerHTML = "<p>Old content</p>";
    buildItemsList([{ id: 1, name: "Item 1" }]);
    expect(itemsList.innerHTML).not.toContain("Old content");
  });

  it("creates DOM elements with correct structure and content", () => {
    const items = [
      {
        id: 1,
        name: "Item 1",
        description: "desc1",
        price: "100",
        linkImage: "img1.jpg",
        category: "cat1",
        createdAt: "2023-01-01",
      },
      {
        id: 2,
        name: "Item 2",
        description: "desc2",
        price: "200",
        linkImage: "img2.jpg",
        category: "cat2",
        createdAt: "2023-01-02",
      },
    ];
    buildItemsList(items);

    const children = itemsList.children;
    expect(children.length).toBe(items.length);

    for (let i = 0; i < items.length; i++) {
      const itemDiv = children[i];
      expect(itemDiv.tagName).toBe("DIV");
      expect(itemDiv.className).toBe("itemContainer");

      const img = itemDiv.querySelector("img");
      expect(img).not.toBeNull();
      expect(img.src).toContain(items[i].linkImage);
      expect(img.style.height).toBe("100px");

      const nameLink = itemDiv.querySelector("a[href='/readItem?id=" + items[i].id + "']");
      expect(nameLink).not.toBeNull();
      expect(nameLink.textContent).toBe(items[i].name);

      const description = itemDiv.querySelector("a:nth-of-type(2)");
      expect(description).not.toBeNull();
      expect(description.textContent).toBe(items[i].description);

      const price = itemDiv.querySelector("a:nth-of-type(3)");
      expect(price).not.toBeNull();
      expect(price.textContent).toBe(items[i].price + " р.");

      const category = itemDiv.querySelector("a:nth-of-type(4)");
      expect(category).not.toBeNull();
      expect(category.textContent).toBe(items[i].category);

      const createdAt = itemDiv.querySelector("a:nth-of-type(5)");
      expect(createdAt).not.toBeNull();
      expect(createdAt.textContent).toBe(items[i].createdAt);

      const modifyLink = itemDiv.querySelector("a:nth-of-type(6)");
      expect(modifyLink).not.toBeNull();
      expect(modifyLink.textContent).toBe("Изменить");

      const deleteBtn = itemDiv.querySelector("a#link");
      expect(deleteBtn).not.toBeNull();
      expect(deleteBtn.textContent).toBe("Удалить");
    }
  });

  it("calls deleteItem with correct id when delete button is clicked", () => {
    const items = [
      {
        id: 42,
        name: "Delete Me",
        description: "",
        price: "",
        linkImage: "",
        category: "",
        createdAt: "",
      },
    ];
    buildItemsList(items);

    const deleteBtn = itemsList.querySelector("a#link");
    expect(deleteBtn).not.toBeNull();

    deleteBtn.click();

    expect(deleteItem).toHaveBeenCalledTimes(1);
    expect(deleteItem).toHaveBeenCalledWith(42);
  });
});
