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