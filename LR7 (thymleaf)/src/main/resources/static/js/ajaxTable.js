//id таблицы
tableId = "";
//Создание таблицы (передача id)
document.querySelectorAll('.createTable').forEach(button => {
        button.addEventListener('click', function () {
            tableId = this.getAttribute("data-modal-id");
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
        });
        });
    })
});
//Функция результата
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
        if(xValues_1.length !== xValues_2.length){
            Message("error", "Размеры функций должны совпадать!");
            return;
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