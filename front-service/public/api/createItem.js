async function createItem(item) {
    try {
        const response = await fetch(`http://localhost:30001/api/product/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        });

        if (!response.ok) {
            throw new Error(`Ошибка при добавлении продукта! Статус: ${response.status}`);
        }
        
        console.log('Продукт успешно удалён');
    } catch (error) {
        console.error('Ошибка при выполнении запроса');
        throw error;
    }
}

export default createItem;