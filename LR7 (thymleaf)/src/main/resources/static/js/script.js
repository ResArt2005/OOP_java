//Открытие окна
function showModal(modalId){
    document.getElementById(modalId).style.display = "block";
}
function openModal(modalId, endpoint){
    document.getElementById(modalId).style.display = "block";
    history.pushState(null, '', endpoint);
}
//Сообщение
function Message(status, message){
    modalPerent = "modalAvengeMessage"
    modalId = "art_modal_status_message";
    const element = document.getElementById(modalId)
    if(status === 'success'){
        element.classList.add("art_success");
    }
    else if(status === 'error'){
        element.classList.add("art_error");
    }
    else{
        element.classList.add("art_usual");
    }
    element.innerText = message;
    showModal(modalPerent);
    document.getElementById('message_ok').addEventListener('click', function() {
        hideModal(modalPerent);
        element.classList.remove('success');
        element.classList.remove('art_error');
        element.classList.remove('art_usual');
    });
}
// Закрытие модального окна
function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
    history.back();
}
function hideModal(modalId){
    document.getElementById(modalId).style.display = "none";
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
