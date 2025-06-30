

/**
 * @jest-environment jsdom
 */

// Мокаем builder, чтобы просто отслеживать вызовы
jest.mock("../public/service/index/itemsListBuilder.js", () => jest.fn());
const buildItemsList = require("../public/service/index/itemsListBuilder.js");

let wsInstance;

// Примитивный мок WebSocket
class WebSocketMock {
  constructor(url) {
    this.url = url;
    wsInstance = this;
    this.onopen = null;
    this.onclose = null;
    this.onmessage = null;
    this.onerror = null;
  }
}

global.WebSocket = WebSocketMock;   // подменяем глобальный WebSocket

// Импортируем скрипт после всех моков
require("../public/service/index/socket.js"); // ⚠️ проверь путь, если отличается

describe("WebSocket client handlers", () => {
  let logSpy;

  beforeEach(() => {
    logSpy = jest.spyOn(console, "log").mockImplementation(() => {});
    buildItemsList.mockClear();
  });

  afterEach(() => {
    logSpy.mockRestore();
  });

  test("onopen выводит сообщение о соединении", () => {
    wsInstance.onopen();
    expect(logSpy).toHaveBeenCalledWith("Соединение установлено.");
  });

  test("onmessage парсит JSON и вызывает buildItemsList", () => {
    const payload = { id: 1, name: "Coffee" };
    wsInstance.onmessage({ data: JSON.stringify(payload) });
    expect(buildItemsList).toHaveBeenCalledTimes(1);
    expect(buildItemsList).toHaveBeenCalledWith(payload);
  });

  test("onclose (чистое закрытие) выводит правильные сообщения", () => {
    wsInstance.onclose({ wasClean: true, code: 1000, reason: "bye" });
    expect(logSpy).toHaveBeenCalledWith("Соединение закрыто чисто");
    expect(logSpy).toHaveBeenCalledWith("Код: 1000 причина: bye");
  });

  test("onclose (обрыв) выводит сообщение об обрыве", () => {
    wsInstance.onclose({ wasClean: false, code: 1006, reason: "crash" });
    expect(logSpy).toHaveBeenCalledWith("Обрыв соединения");
    expect(logSpy).toHaveBeenCalledWith("Код: 1006 причина: crash");
  });

  test("onerror выводит сообщение об ошибке", () => {
    wsInstance.onerror({ message: "boom" });
    expect(logSpy).toHaveBeenCalledWith("Ошибка boom");
  });
});