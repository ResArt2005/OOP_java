//По массивам
document.getElementById("art_byArr_createTableBtn").addEventListener('click', function () {
    const pointsCount = document.getElementById('art_byArr_pointsCount').value;
    if (!pointsCount) {
        return;
    }
    const responseContainer = document.getElementById('art_byArr_tableContainer');

    fetch('/{contextPath}/createFunctionByArrays', {
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
        document.getElementById('art_submitFunctionForm').addEventListener('click', function (event) {
            event.preventDefault();
            const formData = getDataFormWithoutSubmit('submitFunctionForm');

            fetch('/{contextPath}/submitFunctionByArr', {
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
            })
            .then(data => {
                const responseModalContainer = document.getElementById('modalContainer');
                responseModalContainer.innerHTML = data;
                document.getElementById("art_submitFunctionForm").addEventListener('click',  function (){
                    if(document.getElementById("art_stateIdSuccess")){
                        hideModal('createTableByArrayModal');
                    }
                });
                document.getElementById('ok').addEventListener('click',  function (){
                    const InnerModalId = this.getAttribute('data-modal-id');
                    hideModal(InnerModalId);
                    if(document.getElementById("art_stateIdSuccess")){
                        hideModal('createTableByArrayModal');
                    }
                });
            })
        });
    })
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
    fetch('/{contextPath}/submitFunctionByFunction', {
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
    });
    Message("success", "Функция успешно создана!");
    hideModal(this.getAttribute('data-modal-id'));
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
  fetch('/{contextPath}/submitFactory', {
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