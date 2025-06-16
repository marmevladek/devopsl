import deleteItem from '../public/api/deleteItem.js'; // Убедитесь, что путь правильный

global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    status: 200,
  })
);

describe('deleteItem', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  it('should call fetch with correct parameters', async () => {
    const itemId = '12345';
    await deleteItem(itemId);

    expect(fetch).toHaveBeenCalledWith(`http://localhost:30001/api/product/delete/${itemId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
      },
    });
  });

  it('should throw an error if response is not ok', async () => {
    fetch.mockImplementationOnce(() =>
      Promise.resolve({
        ok: false,
        status: 500,
      })
    );

    await expect(deleteItem('12345')).rejects.toThrow('Ошибка при удалении продукта! Статус: 500');
  });
});