// Получаем элементы из DOM
const sendDataBtn = document.getElementById('sendDataBtn');
const inputField = document.getElementById('inputField');
const responseContainer = document.getElementById('responseContainer');

// Добавляем обработчик события на кнопку
sendDataBtn.addEventListener('click', function () {
    const inputValue = inputField.value; // Получаем значение из поля ввода

    // Создаем объект XMLHttpRequest
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/path', true); // Указываем метод и путь к контроллеру

    // Устанавливаем заголовок для отправки данных в формате URL-кодирования
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Обработка ответа от сервера
    xhr.onload = function () {
        if (xhr.status === 200) {
            // Обновляем HTML-разметку динамически
            responseContainer.textContent = xhr.responseText;
        } else {
            responseContainer.textContent = 'Ошибка: ' + xhr.status;
        }
    };

    // Обработка ошибок сети
    xhr.onerror = function () {
        responseContainer.textContent = 'Произошла ошибка при отправке запроса.';
    };

    // Отправляем данные на сервер
    xhr.send('data=' + encodeURIComponent(inputValue));
});
