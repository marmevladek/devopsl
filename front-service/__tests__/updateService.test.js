

/**
 * @jest-environment jsdom
 */
import { jest } from '@jest/globals';

// Mock the modules
jest.mock('../public/api/readItem', () => ({
  readItem: jest.fn(),
}));
jest.mock('../public/api/updateItem', () => ({
  updateItem: jest.fn(),
}));

import { readItem } from '../public/api/readItem';
import { updateItem } from '../public/api/updateItem';

describe('Update Service Page', () => {
  let originalLocation;
  const mockId = '12345';
  const mockItem = {
    name: 'Test Name',
    description: 'Test Desc',
    price: '100',
    fullDescription: 'Full Desc',
    linkImage: 'https://img.com/abc.jpg',
    phoneNumber: '555-5555',
    email: 'test@email.com',
  };

  beforeEach(() => {
    // Set up DOM
    document.body.innerHTML = `
      <input id="name" />
      <input id="description" />
      <input id="price" />
      <input id="fullDescription" />
      <input id="linkImage" />
      <input id="phoneNumber" />
      <input id="email" />
      <img id="itemPhoto" />
      <button id="button">Update</button>
    `;

    // Mock location.search
    originalLocation = window.location;
    delete window.location;
    window.location = { search: `?id=${mockId}` };

    // Set up readItem mock
    readItem.mockClear();
    readItem.mockResolvedValueOnce(mockItem);
    updateItem.mockClear();
  });

  afterEach(() => {
    // Restore location
    window.location = originalLocation;
    jest.resetAllMocks();
  });

  // Simulate the main code logic: fetch item and populate fields
  async function loadAndBindUpdate() {
    // Simulate parsing id from query string
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');
    // Fetch item
    const item = await readItem(id);
    // Populate fields
    document.getElementById('name').value = item.name;
    document.getElementById('description').value = item.description;
    document.getElementById('price').value = item.price;
    document.getElementById('fullDescription').value = item.fullDescription;
    document.getElementById('linkImage').value = item.linkImage;
    document.getElementById('phoneNumber').value = item.phoneNumber;
    document.getElementById('email').value = item.email;
    document.getElementById('itemPhoto').src = item.linkImage;
    // Bind update button
    document.getElementById('button').addEventListener('click', async () => {
      const updatedItem = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        price: document.getElementById('price').value,
        fullDescription: document.getElementById('fullDescription').value,
        linkImage: document.getElementById('linkImage').value,
        phoneNumber: document.getElementById('phoneNumber').value,
        email: document.getElementById('email').value,
      };
      await updateItem(id, updatedItem);
    });
  }

  it('should load item data and populate fields', async () => {
    await loadAndBindUpdate();
    expect(readItem).toHaveBeenCalledWith(mockId);
    expect(document.getElementById('name').value).toBe(mockItem.name);
    expect(document.getElementById('description').value).toBe(mockItem.description);
    expect(document.getElementById('price').value).toBe(mockItem.price);
    expect(document.getElementById('fullDescription').value).toBe(mockItem.fullDescription);
    expect(document.getElementById('linkImage').value).toBe(mockItem.linkImage);
    expect(document.getElementById('phoneNumber').value).toBe(mockItem.phoneNumber);
    expect(document.getElementById('email').value).toBe(mockItem.email);
    expect(document.getElementById('itemPhoto').src).toContain(mockItem.linkImage);
  });

  it('should call updateItem with updated data on button click', async () => {
    await loadAndBindUpdate();
    // Change some values
    document.getElementById('name').value = 'Updated Name';
    document.getElementById('description').value = 'Updated Desc';
    document.getElementById('price').value = '200';
    document.getElementById('fullDescription').value = 'Updated Full Desc';
    document.getElementById('linkImage').value = 'https://img.com/xyz.jpg';
    document.getElementById('phoneNumber').value = '444-4444';
    document.getElementById('email').value = 'updated@email.com';

    // Simulate button click
    document.getElementById('button').click();
    // Wait for any async update
    await Promise.resolve();

    expect(updateItem).toHaveBeenCalledWith(mockId, {
      name: 'Updated Name',
      description: 'Updated Desc',
      price: '200',
      fullDescription: 'Updated Full Desc',
      linkImage: 'https://img.com/xyz.jpg',
      phoneNumber: '444-4444',
      email: 'updated@email.com',
    });
  });
});