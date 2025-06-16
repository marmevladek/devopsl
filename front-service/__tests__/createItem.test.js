import createItem from '../public/api/createItem.js'; // Убедитесь, что путь правильный

global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    status: 200,
  })
);

describe('createItem', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  it('should call fetch with correct parameters', async () => {
    const item = { name: 'Test Product', price: 100 };
    await createItem(item);

    expect(fetch).toHaveBeenCalledWith('http://backend-service:1000/api/product/create', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
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

   
  });
});