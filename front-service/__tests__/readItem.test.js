import readItem from '../public/api/readItem.js'; // Убедитесь, что путь правильный

global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    json: () => Promise.resolve({ id: 1, name: 'Test Product' }),
  })
);

describe('readItem', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  it('should call fetch with correct parameters', async () => {
    const itemId = '12345';
    await readItem(itemId);

    expect(fetch).toHaveBeenCalledWith(`http://backend-service:1000/api/product/${itemId}`);
  });

  it('should throw an error if response is not ok', async () => {
    fetch.mockImplementationOnce(() =>
      Promise.resolve({
        ok: false,
        status: 500,
      })
    );

    await expect(readItem('12345')).rejects.toThrow('HTTP error! статус: 500');
  });
});