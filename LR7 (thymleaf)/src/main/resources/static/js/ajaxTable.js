//id таблицы
tableId = "";
//Кнопка ли это для создания графика
checkGraph = false
graphButtonId = ""
//Создание таблицы (передача id)
document.querySelectorAll('.createTable').forEach(button => {
        button.addEventListener('click', function () {
            tableId = this.getAttribute("data-modal-id");
    });
});
//Создание графика (передача id)
document.querySelectorAll('.buildFunction').forEach(button => {
        button.addEventListener('click', function () {
        checkGraph = true;
        graphButtonId = button.getAttribute("id");
    });
});
//По массивам
document.getElementById("art_byArr_createTableBtn").addEventListener('click', function () {
    const pointsCount = document.getElementById('art_byArr_pointsCount').value;
    if (!pointsCount) {
        return;
    }
    const responseContainer = document.getElementById('art_byArr_tableContainer');
    fetch('/createFunctionByArrays', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'art_byArr_pointsCount=' + encodeURIComponent(pointsCount)
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        responseContainer.innerHTML = data;
        document.getElementById('art_submitFunctionForm').addEventListener('click', function () {
        const formData = getDataFormWithoutSubmit('submitFunctionForm');
        for(let i = 0; i < formData.xValues.length; i++){
            if(!formData.xValues[i] || !formData.yValues[i])
            {
                Message("error", "Все поля должны быть заполнены!");
                return;
            }
        }
        for(let i = 0; i < formData.xValues.length - 1; i++){
            if(formData.xValues[i] >= formData.xValues[i + 1])
            {
                Message("error", "Значения X должны располагаться в порядке возрастания");
                return;
            }
        }
        hideModal(this.getAttribute('data-modal-id'));
        hideModal("createTableByArrayModal");
        fetch('/submitFunctionByArr', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Network response was not ok.');
        }).then(data => {
            Message("success", "Функция успешно создана!");
            const tableContainer = document.getElementById(tableId);
            tableContainer.innerHTML = data;
            if (checkGraph) {
                buildFunction(document.getElementById(graphButtonId));
                checkGraph = false;
                graphButtonId="";
            }
        });
        });
    })
});
//Событие вывода результата элементарных операций
document.querySelectorAll('.resultOpsFunction').forEach(button => {
    button.addEventListener("click", function(){
        const operationName = this.getAttribute('name');
        const url = this.getAttribute('data-url-id');
        const formData_1 = new FormData(document.getElementById('art_form_body_1'));
        const xValues_1 = formData_1.getAll('xValues').map(Number);
        const yValues_1 = formData_1.getAll('yValues').map(Number);
        const formData_2 = new FormData(document.getElementById('art_form_body_2'));
        const xValues_2 = formData_2.getAll('xValues').map(Number);
        const yValues_2 = formData_2.getAll('yValues').map(Number);
        if(xValues_1.length === 0 || xValues_2.length === 0){
            Message("error", "Создайте функции!");
            return;
        }
        if(xValues_1.length !== xValues_2.length){
            Message("error", "Размеры функций должны совпадать!");
            return;
        }
        for(let i = 0; i < xValues_1.length; i++){
            if(xValues_1[i] != xValues_2[i]){
                Message("error", "Значения по X должны совпадать!");
                return;
            }
        }
        const data = { operationName, xValues_1, yValues_1, xValues_2, yValues_2 };
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            Message("success", "Успешная операция!");
            const tableContainer = document.getElementById('art_table_body_3');
            tableContainer.innerHTML = data;
        })
        .catch(error => {
            Message("error", "Произошла ошибка: " + error.message);
        });
    });
});
//Событие вывода результата дифференциальных операций
document.querySelectorAll('.resultDefOpsFunction').forEach(button => {
    button.addEventListener("click", function(){
        const operationName = this.getAttribute('name');//Важно при создании других таблиц
        const url = this.getAttribute('data-url-id');//Важно при создании других таблиц
        const formData = new FormData(document.getElementById('art_form_body_def_1'));//Важно при создании других таблиц
        const xValues = formData.getAll('xValues').map(Number);
        const yValues = formData.getAll('yValues').map(Number);
        if(xValues.length === 0 || yValues.length === 0){
            Message("error", "Создайте функции!");
            return;
        }
        const data = { xValues, yValues };
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            Message("success", "Успешная операция!");
            const tableContainer = document.getElementById('art_table_body_def_2');//Важно при создании других таблиц
            tableContainer.innerHTML = data;
        })
        .catch(error => {
            Message("error", "Произошла ошибка: " + error.message);
        });
    });
});
// Событие сохранения функции путём сериализации
document.querySelectorAll('.saveFunction').forEach(button => {
    button.addEventListener('click', function () {
        const formId = this.getAttribute('data-form-id');
        const values = getDataFormWithoutSubmit(formId);

        if(values.xValues.length === 0 || values.yValues.length === 0){
            Message("error", "Создайте функции!");
            return;
        }
        // Проверка: заполнены ли xValues и yValues
        if (values.xValues === null || values.yValues === null) {
            Message("error", "Создайте функции!");
            return;
        }

        // Проверка: все ли поля заполнены
        for (let i = 0; i < values.xValues.length; i++) {
            if (values.xValues[i] === null || values.yValues[i] === null) {
                Message("error", "Заполните все поля");
                return;
            }
        }

        // Создание диалогового окна для выбора формата и имени файла
        const dialog = document.createElement('div');
        dialog.style.position = 'fixed';
        dialog.style.top = '50%';
        dialog.style.left = '50%';
        dialog.style.transform = 'translate(-50%, -50%)';
        dialog.style.padding = '20px';
        dialog.style.backgroundColor = '#fff';
        dialog.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
        dialog.style.zIndex = '9999';

        // Поле для ввода имени файла
        const fileNameInput = document.createElement('input');
        fileNameInput.type = 'text';
        fileNameInput.placeholder = 'Введите имя файла';
        fileNameInput.style.marginBottom = '10px';

        // Выпадающий список для выбора формата
        const formatSelect = document.createElement('select');
        const formats = ['bin', 'json', 'xml'];
        formats.forEach(format => {
            const option = document.createElement('option');
            option.value = format;
            option.textContent = format.toUpperCase();
            formatSelect.appendChild(option);
        });

        // Кнопка для подтверждения сохранения
        const saveButton = document.createElement('button');
        saveButton.textContent = 'Сохранить';
        saveButton.style.marginRight = '10px';

        // Кнопка для отмены
        const cancelButton = document.createElement('button');
        cancelButton.textContent = 'Отмена';

        // Добавляем элементы в диалог
        dialog.appendChild(fileNameInput);
        dialog.appendChild(formatSelect);
        dialog.appendChild(saveButton);
        dialog.appendChild(cancelButton);
        document.body.appendChild(dialog);

        // Событие для кнопки "Отмена"
        cancelButton.addEventListener('click', function () {
            document.body.removeChild(dialog);
        });

        // Событие для кнопки "Сохранить"
        saveButton.addEventListener('click', function () {
            const fileName = fileNameInput.value;
            const fileFormat = formatSelect.value;

            if (!fileName) {
                Message('error', 'Введите имя файла.');
                return;
            }

            // Отправка данных на сервер для сериализации
            fetch('/serialize', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ ...values, filePath: `${fileName}.${fileFormat}` })
            })
            .then(response => {
                if (response.ok) {
                    return response.blob();
                }
                throw new Error('Ошибка сети.');
            })
            .then(blob => {
                // Скачиваем файл через Blob
                const downloadUrl = URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = downloadUrl;
                a.download = `${fileName}.${fileFormat}`;  // Предложим пользователю имя файла
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
                URL.revokeObjectURL(downloadUrl);  // Освобождаем память
                Message('success', 'Функция успешно сохранена!');
            })
            .catch(error => {
                Message("error", error.message);
            })
            .finally(() => {
                // Удаляем диалог после завершения процесса
                document.body.removeChild(dialog);
            });
        });
    });
});
// Событие загрузки функции путём десериализации
document.querySelectorAll('.loadFunction').forEach(button => {
    button.addEventListener('click', function() {
        const tableId = this.getAttribute("data-table-id");

        // Создание диалогового окна для выбора файла загрузки
        const fileInput = document.createElement('input');
        fileInput.type = 'file';
        fileInput.accept = '.json, .xml, .bin';

        fileInput.onchange = () => {
            const file = fileInput.files[0];
            if (!file) {
                Message("error", "Файл не выбран.");
                return;
            }
            const formData = new FormData();
            formData.append('file', file);

            fetch('/deserialize', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error('Network response was not ok.');
            })
            .then(data => {
                const tableContainer = document.getElementById(tableId);
                tableContainer.innerHTML = data;
                if(!document.getElementById("art_stateUniqueErrorId")){
                    Message("success", "Функция успешно воспроизведена из файла!");
                }
                if (checkGraph) {
                buildFunction(document.getElementById(graphButtonId));
                checkGraph = false;
                graphButtonId="";
            }
            })
            .catch(error => {
                Message("error", error.message);
            });
        };
        fileInput.click();
    });
});
//Получение данных формы без перезагрузки страницы
function getDataFormWithoutSubmit(formId){
    const form = document.getElementById(formId);
    const formData = new FormData(form);

    const xValues = formData.getAll('xValues').map(Number);
    const yValues = formData.getAll('yValues').map(Number);

    return { xValues, yValues };
}
//По функции
document.getElementById("createTableByFunctionTableBtn").addEventListener('click', function () {
    const funcName = document.getElementById('art_choose_one').innerText;
    const leftBound = document.getElementById('art_byTableByFunction_left_bound').value;
    const rightBound = document.getElementById('art_byTableByFunction_right_bound').value;
    const pointCount = document.getElementById('art_byByFunction_pointsCount').value;
    if(!funcName || !leftBound || !rightBound || !pointCount){
        Message("error", "Заполните все поля");
        return;
    }
    if(funcName === "Выберите функцию"){
        Message("error", "Да выберите функцию нормально!");
        return;
    }
    if(parseFloat(leftBound) >= parseFloat(rightBound)){
        Message("error", "Левая граница должна быть меньше правой!");
        return;
    }
    if(parseInt(pointCount) < 2){
        Message("error", "Количество точек должно быть не меньше 2!");
        return;
    }
    fetch('/submitFunctionByFunction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'funcName=' + encodeURIComponent(funcName) +'&'+'leftBound=' + encodeURIComponent(leftBound)+'&'+'rightBound=' + encodeURIComponent(rightBound)+'&'+'pointCount=' + encodeURIComponent(pointCount)
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    }).then(data => {
        Message("success", "Функция успешно создана!");
        hideModal(this.getAttribute('data-modal-id'));
        const tableContainer = document.getElementById(tableId);
        tableContainer.innerHTML = data;
        if (checkGraph) {
            buildFunction(document.getElementById(graphButtonId));
            checkGraph = false;
            graphButtonId="";
        }
    });
});
//Выбор фабрики
document.getElementById("art_radio_accept").addEventListener('click', function (){
  const arrayRadio = document.getElementById("art_arrayTabulatedFunctionFactory");
  const linkedListRadio = document.getElementById("art_linkedListTabulatedFunctionFactory");

  let selectedFactory;

  if (arrayRadio.checked) {
    selectedFactory = "arrayFactory";
  } else if (linkedListRadio.checked) {
    selectedFactory = "linkedListFactory";
  } else {
    selectedFactory = null; // или какое-то значение по умолчанию
  }
  fetch('/submitFactory', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'TypeFactory=' + encodeURIComponent(selectedFactory)
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    });
});
//Построение графика
let chartInstance = null;
function buildFunction(obj) {
    const url = obj.getAttribute('data-url-id');
    const graphId = obj.getAttribute('data-graph-id');
    const formData = new FormData(document.getElementById('art_form_graph'));
    const xValues = formData.getAll('xValues').map(Number);
    const yValues = formData.getAll('yValues').map(Number);
    if (xValues.length === 0 || yValues.length === 0) {
        Message("error", "Создайте функции!");
        return;
    }

    fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ xValues, yValues })
    })
    .then(response => response.json())
    .then(data => {
        const trace = {
            x: data.xValues,
            y: data.yValues,
            mode: 'lines',
            name: 'Табулированная функция',
            line: { color: 'rgba(75, 192, 192, 1)', width: 2 }
        };

        const layout = {
            title: 'График Табулированной Функции',
            xaxis: {
                title: 'X',
                showgrid: true,
                zeroline: false
            },
            yaxis: {
                title: 'Y',
                showgrid: true,
                zeroline: false
            }
        };

        Plotly.newPlot(graphId, [trace], layout);
    })
    .catch(error => {
        Message("error", error.message);
    });
}
//Вычисления значения в точке
document.getElementById("applyButton").addEventListener('click', function() {
    const X = document.getElementById("xValue").value;
    const url = this.getAttribute('data-url-id');
    const containerId = this.getAttribute('data-container-id');
    const formData = new FormData(document.getElementById('art_form_graph'));
    const xValues = formData.getAll('xValues').map(Number);
    const yValues = formData.getAll('yValues').map(Number);
    if(xValues.length === 0 || yValues.length === 0){
        Message("error", "Создайте функции!");
        return;
    }
    // Проверка: заполнены ли xValues и yValues
    if (xValues === null || yValues === null) {
        Message("error", "Создайте функции!");
        return;
    }
    if(X === null){
        Message("error", "Введите значение точки по X!");
        return;
    }
    fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({X, xValues, yValues})
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    }).then(data => {
        const containerIdContainer = document.getElementById(containerId);
        containerIdContainer.innerHTML = data;
    })
    .catch(error => {
        Message("error", error.message);
    });
});
//Вставка точки
tableInsertId = "";
tableInsertData = "";
document.querySelectorAll('.insertPoint').forEach(button =>{
        button.addEventListener('click', function(){
        tableInsertId = button.getAttribute("data-table-id");
        tableInsertData = getDataFormWithoutSubmit(button.getAttribute("data-form-id"));
    })
});
document.getElementById("insertButton").addEventListener("click", function(){
    X = document.getElementById("insertX").value;
    Y = document.getElementById("insertY").value;
    container = document.getElementById(tableInsertId);
    if(tableInsertData.xValues.length === 0 || tableInsertData.yValues.length === 0){
        Message("error", "Создайте функции!");
        return;
    }
    // Проверка: заполнены ли xValues и yValues
    if (tableInsertData.xValues === null || tableInsertData.yValues === null) {
        Message("error", "Создайте функции!");
        return;
    }
    if(X === null || Y === null || X == "" || Y == ""){
        Message("error", "Заполните X и Y!");
        return;
    }
    xValues = tableInsertData.xValues;
    yValues = tableInsertData.yValues;
    data = {xValues, yValues, X, Y};
    fetch("/insert", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        container.innerHTML = data;
        if (checkGraph) {
            buildFunction(document.getElementById(graphButtonId));
            checkGraph = false;
            graphButtonId="";
        }
    })
    .catch(error => {
        Message("error", "Произошла ошибка: " + error.message);
    });
});
//Удаление точки
document.querySelectorAll('.removePoint').forEach(button =>{
    button.addEventListener('click', function(){
    tableInsertId = button.getAttribute("data-table-id");
    tableInsertData = getDataFormWithoutSubmit(button.getAttribute("data-form-id"));
    })
});
document.getElementById("removeButton").addEventListener("click", function(){
    index = document.getElementById("index").value;
    container = document.getElementById(tableInsertId);
    if(tableInsertData.xValues.length === 0 || tableInsertData.yValues.length === 0){
        Message("error", "Создайте функции!");
        return;
    }
    // Проверка: заполнены ли xValues и yValues
    if (tableInsertData.xValues === null || tableInsertData.yValues === null) {
        Message("error", "Создайте функции!");
        return;
    }
    if(tableInsertData.xValues.length === 2 || tableInsertData.yValues.length === 2){
        Message("error", "Достигнут предел удаления!");
        return;
    }
    if(index === null || index == ""){
        Message("error", "Укажите индекс удаляемого значения!");
        return;
    }
    if(index >= tableInsertData.xValues.length){
        Message("error", "Индекс должен быть меньше максимальной длины функции!");
        return;
    }
    xValues = tableInsertData.xValues;
    yValues = tableInsertData.yValues;
    data = {xValues, yValues, index};
    fetch("/remove", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        container.innerHTML = data;
        if (checkGraph) {
            buildFunction(document.getElementById(graphButtonId));
            checkGraph = false;
            graphButtonId="";
        }
    })
    .catch(error => {
        Message("error", "Произошла ошибка: " + error.message);
    });
})
//Вычисление интеграла
document.querySelectorAll('.resultIntegral').forEach(button => {
    button.addEventListener("click", function(){
        const operationName = this.getAttribute('name');//Важно при создании других таблиц
        const url = this.getAttribute('data-url-id');//Важно при создании других таблиц
        const formData = new FormData(document.getElementById('art_form_integral'));//Важно при создании других таблиц
        const xValues = formData.getAll('xValues').map(Number);
        const yValues = formData.getAll('yValues').map(Number);
        const countStreams = document.getElementById("countStreams").value;
        if(xValues.length === 0 || yValues.length === 0){
            Message("error", "Создайте функции!");
            return;
        }
        if (countStreams < 1){
            Message("error", "Должен быть указан хотя бы 1 поток!");
            return;
        }
        const data = { countStreams, xValues, yValues };
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            Message("success", "Успешная операция!");
            const tableContainer = document.getElementById('integralContainerResult');
            tableContainer.innerHTML = data;
        })
        .catch(error => {
            Message("error", "Произошла ошибка: " + error.message);
        });
    });
});
//Создание сложной математической функции
document.getElementById("MathFuncCreateListBtn").addEventListener("click", function(){
    pointCount = document.getElementById("MathFuncPointsCount").value;
    newName = document.getElementById("MathFuncName").value;
    if(pointCount < 2){
        Message("error", "Количество функций должно быть не меньше 2!");
        return;
    }
    if(newName === "Выберите функцию"){
        Message("error", "Такое имя функции неприемлемо!");
        return;
    }
    fetch('/hardFunction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'newName=' + encodeURIComponent(newName) +'&'+'pointCount=' + encodeURIComponent(pointCount)
    }).then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    }).then(data => {
        const MathFuncContainer = document.getElementById("MathFuncContainer");
        MathFuncContainer.innerHTML = data;
        const dropdowns = document.querySelectorAll('.art_dropdown');
        dropdowns.forEach(dropdown => {
            const dropdownButton = dropdown.querySelector('.art_dropdown-button');
            const dropdownItems = dropdown.querySelectorAll('.art_dropdown-item');

            dropdownButton.addEventListener('click', function(event) {
                event.stopPropagation(); // Предотвращаем закрытие при клике вне списка
                const content = dropdown.querySelector('.art_dropdown-content');
                content.style.display = content.style.display === 'block' ? 'none' : 'block';
            });

            dropdownItems.forEach(item => {
                item.addEventListener('click', function(event) {
                    event.preventDefault();
                    dropdownButton.textContent = this.textContent;
                    dropdown.querySelector('.art_dropdown-content').style.display = 'none';
                });
            });
        });

        // Закрытие всех выпадающих списков при клике вне
        document.addEventListener('click', function(event) {
            dropdowns.forEach(dropdown => {
                if (!dropdown.contains(event.target)) {
                    dropdown.querySelector('.art_dropdown-content').style.display = 'none';
                }
            });
        });
        document.getElementById("submitCreationMethFunction").addEventListener("click", function(){
            const elements = document.querySelectorAll('.createFunc'); // Выбор всех элементов с классом className
            const valuesList = Array.from(elements).map(element => element.textContent); // Извлечение текстового содержимого
            for(let i = 0; i < valuesList.length; i++){
                if(valuesList[i] === "Выберите функцию"){
                    Message("error", "Укажите все функции!");
                    return;
                }
            }
            fetch('/hardFunctionSubmit',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(valuesList)
            })
            .then(response => response.json())
            .then(data => {
                if (data.message === "success") {
                    location.reload(); // Перезагрузка страницы
                }
            })
            .catch(error => console.error('Ошибка:', error));
        });
    });
});