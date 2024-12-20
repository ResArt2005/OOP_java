document.getElementById("art_byArr_createTableBtn").addEventListener('click', function () {
    const pointsCount = document.getElementById('art_byArr_pointsCount').value;
    if (!pointsCount) {
        return;
    }
    const responseContainer = document.getElementById('art_byArr_tableContainer');

    // Создаем объект XMLHttpRequest
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/main/createFunctionByArrays', true); // Указываем метод и путь к контроллеру

    // Устанавливаем заголовок для отправки данных в формате URL-кодирования
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Обработка ответа от сервера
    xhr.onload = function () {
        if (xhr.status === 200) {
            // Обновляем HTML-разметку динамически
            responseContainer.innerHTML = xhr.responseText;

            // Добавляем обработчик для отправки формы
            document.getElementById('art_submitFunctionForm').addEventListener('click', function (event) {
                    event.preventDefault();
                    const formData = getDataFormWithoutSubmit("submitFunctionForm");
                    const xhrSubmit = new XMLHttpRequest();
                    xhrSubmit.open('POST', '/main/submitFunction', true);
                    xhrSubmit.setRequestHeader('Content-Type', "application/json");
                    xhrSubmit.onload = function () {
                        if (xhrSubmit.status === 200) {
                            const responseModalContainer = document.getElementById('modalContainer');
                            responseModalContainer.innerHTML = xhrSubmit.responseText;
                            document.getElementById("art_submitFunctionForm").addEventListener('click',  function (){
                                closeModal('TEST');
                            });
                            document.getElementById('ok').addEventListener('click',  function (){
                                const InnerModalId = this.getAttribute('data-modal-id');
                                hideModal(InnerModalId);
                            });
                        } else {
                            alert('Ошибка создания функции: ' + xhrSubmit.status);
                        }
                    };
                    xhrSubmit.send(JSON.stringify(formData));
                });
            } else {
                responseContainer.textContent = 'Ошибка: ' + xhr.status;
            }
    };

    // Обработка ошибок сети
    xhr.onerror = function () {
        responseContainer.textContent = 'Произошла ошибка при отправке запроса.';
    };

    // Отправляем данные на сервер
    xhr.send('art_byArr_pointsCount=' + encodeURIComponent(pointsCount));
});

function getDataFormWithoutSubmit(formId){
    const form = document.getElementById(formId); // Получаем форму
    const formData = new FormData(form); // Создаем объект FormData

    // Собираем данные из полей с именами xValues и yValues
    const xValues = formData.getAll('xValues'); // Массив всех значений xValues
    const yValues = formData.getAll('yValues'); // Массив всех значений yValues
    const numericXValues = xValues.map(Number);
    const numericYValues = yValues.map(Number);
    // Возвращаем данные, если нужно использовать их дальше
    return { xValues: numericXValues, yValues: numericYValues };
}