// Копирование токена в буфер обмена
const tokenText = document.getElementById('tokenText');
const copySuccessMessage = document.getElementById('copySuccessMessage');
//Открытие окна
function showModal(modalId){document.getElementById(modalId).style.display = "block"; }
// Закрытие модального окна
function hideModal(modalId){ document.getElementById(modalId).style.display = "none"; }
//Сообщение
function Message(status, message){
    modalPerent = "modalAvengeMessage"
    modalId = "art_modal_status_message";
    const element = document.getElementById(modalId)
    if(status === 'success'){
        element.style.color = "#81C784";
    }
    else if(status === 'error'){
        element.style.color = "#FF6B6B";
    }
    else{
        element.style.color = "#fff";
    }
    element.innerText = message;
    showModal(modalPerent);
    document.getElementById('message_ok').addEventListener('click', function() {
        hideModal(modalPerent);
    });
}
//Открытие закрытие окон
document.querySelectorAll('.close').forEach(button => {
        button.addEventListener('click', function () {
        hideModal(this.getAttribute('data-modal-id'));
    });
});
document.querySelectorAll('.openModal').forEach(button => {
        button.addEventListener('click', function () {
            showModal(this.getAttribute('data-modal-id'));
    });
});
//Ограничение на ввод, только целые положительные числа
document.querySelectorAll('art_byArr_pointsCount').forEach(input =>{
        input.addEventListener('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '')
    });
});
//----------------------------------------------------------------------\\
//Здесь Аякс запросы
//Очистить базу
document.getElementById("eraseBtn").addEventListener('click', function(){
    fetch("/main/workWithDbEraseAll",{
        method: "POST"
    }).then(response=>{
        if(response.ok){
            Message("success", "База чиста!");
            location.reload();
        }
        else{
            Message("error", "Что-то пошло не так на стороне сервера!");
        }
    }).catch(error => {
        Message("error", "Ошибка сети: " + error.message);
    });
});
//Работа с пользователями
//Кнопка, открывающая окно настроек пользователей
document.getElementById("userButton").addEventListener("click", function(){
    fetch("/main/workWithDbUsersINNT", {
        method: "POST"
    }).then(response => {
        if (response.ok) {
            return response.text();
        } else {
            Message("error", "Что-то пошло не так на стороне сервера!");
        }
    }).then(data => {
        const userContainer = document.getElementById("userContainer");
        userContainer.innerHTML = data;
        addDeleteEventListeners();
        addChangeEventListeners();
    }).catch(error => {
        Message("error", "Ошибка сети: " + error.message);
    });
});
//Слушатели событий для сгенерированных кнопок из сервера Удалить и Изменить
function addDeleteEventListeners() {
    document.querySelectorAll(".userDeleteBtn").forEach(button => {
        button.addEventListener('click', function(){
            const token = document.getElementById(this.getAttribute("data-token-id")).innerText;
            fetch("/main/UserDelete", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'token=' + encodeURIComponent(token)
            }).then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    Message("error", "Что-то пошло не так на стороне сервера!");
                }
            }).then(data => {
                const userContainer = document.getElementById("userContainer");
                userContainer.innerHTML = data;
                addDeleteEventListeners(); // Перепривязываем обработчики событий для новых элементов
            }).catch(error => {
                Message("error", "Ошибка сети: " + error.message);
            });
        });
    });
}
function addChangeEventListeners() {
    document.querySelectorAll(".userChangeBtn").forEach(button => {
        button.addEventListener('click', function(){
            const token = document.getElementById(this.getAttribute("data-token-id")).innerText;
            showModal("userChanging");
            document.getElementById("changeUser").addEventListener("click", function(){
            const login = document.getElementById("login_to_change").value;
            const password = document.getElementById("password_to_change").value;
            if(login == '' || password ==''){
                Message("error", "Введите логин и пароль пользователя!");
                return;
            }
            fetch("/main/UserChange", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'token=' + encodeURIComponent(token) +"&"+'login=' + encodeURIComponent(login)+"&"+"password="+encodeURIComponent(password)
            }).then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    Message("error", "Что-то пошло не так на стороне сервера!");
                }
            }).then(data => {
               hideModal("userChanging");
               const userContainer = document.getElementById("userContainer");
               userContainer.innerHTML = data;
               addChangeEventListeners(); // Перепривязываем обработчики событий для новых элементов
            }).catch(error => {
                Message("error", "Ошибка сети: " + error.message);
            });
        });
    });
    });
}
//Регистрация новых пользователей
document.getElementById("registerUser").addEventListener("click", function(){
    login = document.getElementById("login").value;
    password = document.getElementById("password").value;
    if(login == '' || password ==''){
        Message("error", "Введите логин и пароль пользователя!");
        return;
    }
    fetch("/main/UserCreate",{
        method: "POST",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'login=' + encodeURIComponent(login)+"&"+"password="+encodeURIComponent(password)
    }).then(response=>{
        if(response.ok){
            Message("success", "Пользователь зарегистрирован!");
            return response.text()
        }
        else{
            Message("error", "Ошибка регистрации!");
        }
    }).then(data=>{
        hideModal("userCreation")
        document.getElementById("login").value = '';
        document.getElementById("password").value = '';
        userContainer = document.getElementById("userContainer");
        userContainer.innerHTML = data;
        addChangeEventListeners();
        addDeleteEventListeners()
    }).catch(error => {
        Message("error", "Ошибка сети: " + error.message);
    });
});
//Логи
document.getElementById("watchLogs").addEventListener("click", function(){
    fetch("/main/workWithDbLogs", {
    method: "POST"
        }).then(response => {
            if (response.ok) {
                return response.text();
            } else {
                Message("error", "Что-то пошло не так на стороне сервера!");
            }
        }).then(data => {
            const logContainer = document.getElementById("logContainer");
            logContainer.innerHTML = data;
        }).catch(error => {
            Message("error", "Ошибка сети: " + error.message);
        });
})
document.getElementById("logsErase").addEventListener("click", function(){
    fetch("/main/removeLogs", {
    method: "POST"
        }).then(response => {
            if (response.ok) {
                return response.text();
            } else {
                Message("error", "Что-то пошло не так на стороне сервера!");
            }
        }).then(data => {
            Message("success", "Логи стёрты")
            const logContainer = document.getElementById("logContainer");
            logContainer.innerHTML = data;
        }).catch(error => {
            Message("error", "Ошибка сети: " + error.message);
        });
})
//Табулированная функция
document.getElementById("watchTB").addEventListener("click", function(){
    fetch("/main/workWithDbTBFunc", {
    method: "POST"
        }).then(response => {
            if (response.ok) {
                return response.text();
            } else {
                Message("error", "Что-то пошло не так на стороне сервера!");
            }
        }).then(data => {
            const logContainer = document.getElementById("TBContainer");
            logContainer.innerHTML = data;
        }).catch(error => {
            Message("error", "Ошибка сети: " + error.message);
        });
})
document.getElementById("TBErase").addEventListener("click", function(){
    fetch("/main/removeTB", {
    method: "POST"
        }).then(response => {
            if (response.ok) {
                return response.text();
            } else {
                Message("error", "Что-то пошло не так на стороне сервера!");
            }
        }).then(data => {
            Message("success", "Функции стёрты")
            const logContainer = document.getElementById("TBContainer");
            logContainer.innerHTML = data;
        }).catch(error => {
            Message("error", "Ошибка сети: " + error.message);
        });
})
//Мат функция
document.getElementById("mathFuncButton").addEventListener("click", function(){
    fetch("/main/workWithDbMathFunc", {
        method: "POST"
    }).then(response => {
        if (response.ok) {
            return response.text();
        } else {
        }
    }).then(data => {
        const MFContainer = document.getElementById("MFContainer");
        MFContainer.innerHTML = data;
        addDeleteEventListenersForMF();
    });
});
function addDeleteEventListenersForMF() {
    document.querySelectorAll(".MFDeleteBtn").forEach(button => {
        button.addEventListener('click', function(){
            const name = document.getElementById(this.getAttribute("data-name-id")).innerText;

            fetch("/main/MathFuncDelete", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'MathFuncName=' + encodeURIComponent(name)
            }).then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    Message("error", "Что-то пошло не так на стороне сервера!");
                }
            }).then(data => {
                const MFContainer = document.getElementById("MFContainer");
                MFContainer.innerHTML = data;
                addDeleteEventListenersForMF(); // Перепривязываем обработчики событий для новых элементов
            });
        });
    });
}
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
            });
        });
    });
});
const dropdown = document.querySelector('.art_dropdown');
const dropdownButton = dropdown.querySelector('.art_dropdown-button');
const dropdownItems = dropdown.querySelectorAll('.art_dropdown-item');
dropdownButton.addEventListener('click', function(event) {
    event.stopPropagation(); // Предотвращаем закрытие при клике вне списка
    dropdown.querySelector('.art_dropdown-content').style.display =
        dropdown.querySelector('.art_dropdown-content').style.display === 'block' ? 'none' : 'block';
});
dropdownItems.forEach(item => {
    item.addEventListener('click', function(event) {
        event.preventDefault();
        dropdownButton.textContent = this.textContent;
        dropdown.querySelector('.art_dropdown-content').style.display = 'none';
    });
});
document.addEventListener('click', function(event) {
    if (!dropdown.contains(event.target)) {
        dropdown.querySelector('.art_dropdown-content').style.display = 'none';
    }
});
