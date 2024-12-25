// Копирование токена в буфер обмена
const tokenText = document.getElementById('tokenText');
const copySuccessMessage = document.getElementById('copySuccessMessage');
if (tokenText) {
    tokenText.addEventListener('click', () => {
        const token = tokenText.textContent; // Получаем текст токена

        // Используем API для записи текста в буфер обмена
        navigator.clipboard.writeText(token).then(() => {
            // Показываем сообщение об успешном копировании
            copySuccessMessage.style.display = 'inline';

            // Скрываем сообщение через 2 секунды
            setTimeout(() => {
                copySuccessMessage.style.display = 'none';
            }, 2000);
        }).catch(err => {
            console.error('Ошибка при копировании текста:', err);
        });
    });
}
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
    }).catch(error => {
        Message("error", "Ошибка сети: " + error.message);
    });
});

