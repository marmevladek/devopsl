async function updateItem(itemId, item) {
    try {
        const response = await fetch(`http://localhost:30001/api/product/update/${itemId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;'
            },
            body: JSON.stringify(item)
        });

        if (!response.ok) {
            throw new Error(`Ошибка при обновлении продукта! Статус: ${response.status}`);
        }
        
        console.log('Продукт успешно обвнолен:');
    } catch (error) {
        console.error('Ошибка при выполнении запроса:');
        throw error;
    }
}

export default updateItem;