async function readItem(id) {
  try {
      const response = await fetch("http://localhost:32761/api/product/" + id);
      
      if (!response.ok) {
          throw new Error(`HTTP error! статус: ${response.status}`);
      }
      
      const data = await response.json();
      return data; 
  } catch (error) {
      console.error('Ошибка при получении данных:', error);
      throw error; 
  }
}

export default readItem;