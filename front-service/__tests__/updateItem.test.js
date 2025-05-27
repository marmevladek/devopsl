import updateItem from '../public/api/updateItem.js'; // Убедитесь, что путь правильный

global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    status: 200,
  })
);

describe('updateItem', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  it('should call fetch with correct parameters', async () => {
    const itemId = '12345';
    const item = { name: 'Updated Product' };
    
    await updateItem(itemId, item);

    expect(fetch).toHaveBeenCalledWith(`http://localhost:32761/api/product/update/${itemId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;',
      },
      body: JSON.stringify(item),
    });
  });

  it('should throw an error if response is not ok', async () => {
    fetch.mockImplementationOnce(() =>
      Promise.resolve({
        ok: false,
        status: 500,
      })
    );

    await expect(updateItem('12345', { name: 'Updated Product' })).rejects.toThrow('Ошибка при обновлении продукта! Статус: 500');
  });

});