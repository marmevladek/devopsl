async function deleteItem(itemId) {
    try {
        const response = await fetch(`http://localhost:1000/api/product/delete/${itemId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        });

        if (!response.ok) {
            throw new Error(`Ошибка при удалении продукта! Статус: ${response.status}`);
        }
        
        console.log('Продукт успешно удалён');
    } catch (error) {
        console.error('Ошибка при выполнении запроса');
        throw error;
    }
}

export default deleteItem;