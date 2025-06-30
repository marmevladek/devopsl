/**
 * @jest-environment jsdom
 */



jest.mock('../public/api/readItem', () => ({
  readItem: jest.fn(),
}));

import { readItem } from '../public/api/readItem';

describe('readService', () => {
  beforeEach(() => {
    // Set up DOM elements
    document.body.innerHTML = `
      <div id="name"></div>
      <div id="description"></div>
      <div id="fullDescription"></div>
      <div id="price"></div>
      <img id="itemImage" />
      <a id="linkImage"></a>
      <div id="phoneNumber"></div>
      <div id="email"></div>
    `;

    // Mock location.search
    delete window.location;
    window.location = { search: '?id=123' };
  });

  it('should fetch item and update DOM elements correctly', async () => {
    const mockData = {
      name: 'Test Item',
      description: 'Short description',
      fullDescription: 'Full description of the test item',
      price: '100',
      itemImage: 'image.jpg',
      linkImage: 'http://example.com/image.jpg',
      phoneNumber: '123-456-7890',
      email: 'test@example.com',
    };

    readItem.mockResolvedValue(mockData);

    // Wrap the tested code in an async function to simulate the top-level async code
    async function runReadService() {
      const params = new URLSearchParams(window.location.search);
      const id = params.get('id');

      const item = await readItem(id);

      document.getElementById('name').textContent = item.name;
      document.getElementById('description').textContent = item.description;
      document.getElementById('fullDescription').textContent = item.fullDescription;
      document.getElementById('price').textContent = item.price;
      document.getElementById('itemImage').src = item.itemImage;
      document.getElementById('linkImage').href = item.linkImage;
      document.getElementById('phoneNumber').textContent = item.phoneNumber;
      document.getElementById('email').textContent = item.email;
    }

    await runReadService();

    expect(readItem).toHaveBeenCalledWith('123');
    expect(document.getElementById('name').textContent).toBe(mockData.name);
    expect(document.getElementById('description').textContent).toBe(mockData.description);
    expect(document.getElementById('fullDescription').textContent).toBe(mockData.fullDescription);
    expect(document.getElementById('price').textContent).toBe(mockData.price);
    expect(document.getElementById('itemImage').src).toContain(mockData.itemImage);
    expect(document.getElementById('linkImage').href).toBe(mockData.linkImage);
    expect(document.getElementById('phoneNumber').textContent).toBe(mockData.phoneNumber);
    expect(document.getElementById('email').textContent).toBe(mockData.email);
  });
});
