editCardForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(editCardForm);
    const cardIdToEdit = formData.get('cardIdToEdit');

    const cardData = {
        title: formData.get('title'),
        description: formData.get('description'),
        color: formData.get('color'),
        dueDate: formData.get('dueDate'),
        workers: formData.get('workers').split(',').map(worker => worker.trim())
    };

    try {
        const response = await fetch(`/api/board/${boardId}/column_list/${columnListId}/card/${cardIdToEdit}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(cardData)
        });

        if (response.ok) {
            alert('Card updated successfully!');
            // 여기에 페이지 이동 또는 추가 작업을 수행할 수 있습니다.
        } else {
            alert('Error updating card.');
        }
    } catch (error) {
        console.error('Error:', error);
    }
});