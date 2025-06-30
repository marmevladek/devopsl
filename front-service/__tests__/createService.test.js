/**
 * @jest-environment jsdom
 */
jest.mock("../public/api/createItem.js"); // 🧠 МОКАЕМ ДО импорта

let createItem; // ссылка на мок; инициализируем в beforeEach

describe("createButtonEvent", () => {
  beforeEach(() => {
    // Сбрасываем кеш модулей, чтобы обработчик подцепился к свежему DOM
    jest.resetModules();

    // Подготавливаем DOM
    document.body.innerHTML = `
      <input id="name"            value="Coffee">
      <input id="description"     value="Tasty">
      <input id="fullDescription" value="Very tasty coffee">
      <input id="price"           value="4.99">
      <input id="linkImage"       value="http://image">
      <input id="phoneNumber"     value="+1234567890">
      <input id="email"           value="test@example.com">
      <button id="button"></button>
    `;

    // Подключаем мок и сохраняем ссылку
    const mockedModule = require("../public/api/createItem.js");
    createItem = mockedModule.default || mockedModule;

    // Загружаем обработчик **после** того, как элементы уже в DOM
    require("../public/service/create/createButtonEvent.js");

    // Сбрасываем счётчики вызовов моков
    jest.clearAllMocks();
  });

  it("вызывает createItem с корректными данными после клика", async () => {
    document.getElementById("button").click();

    expect(createItem).toHaveBeenCalledTimes(1);
    expect(createItem).toHaveBeenCalledWith({
      name: "Coffee",
      description: "Tasty",
      fullDescription: "Very tasty coffee",
      price: "4.99",
      linkImage: "http://image",
      phoneNumber: "+1234567890",
      email: "test@example.com",
      category_id: 1,
    });
  });
});