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
//ОткрытиеЗакрытие окон
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

document.getElementById('art_byArr_pointsCount').addEventListener('input', function() {
this.value = this.value.replace(/[^0-9]/g, '')});
document.getElementById('art_byByFunction_pointsCount').addEventListener('input', function() {
this.value = this.value.replace(/[^0-9]/g, '')});
document.getElementById('index').addEventListener('input', function() {
    this.value = this.value.replace(/[^0-9]/g, '');
});
document.getElementById('countStreams').addEventListener('input', function() {
    this.value = this.value.replace(/[^0-9]/g, '');
});