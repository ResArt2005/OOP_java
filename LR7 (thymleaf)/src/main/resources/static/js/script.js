function showModal(modalId, endpoint){
    document.getElementById(modalId).style.display = "block";
    history.pushState(null, '', endpoint);
}
// Закрытие модального окна
function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
    history.back();
}
function hideModal(modalId){
    document.getElementById(modalId).style.display = "none";
}
document.querySelector('.close').addEventListener('click', function () {
    const modalId = this.getAttribute('data-modal-id');
    closeModal(modalId);
});
document.getElementById("testButton").addEventListener('click', function (){
    const modalId = this.getAttribute('data-modal-id');
    const url = this.getAttribute('data-url');
    showModal(modalId, url);
})