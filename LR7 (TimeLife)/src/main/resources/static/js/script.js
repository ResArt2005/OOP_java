function openModal(modalId, endpoint) {
    fetch(endpoint)
        .then(response => response.text())
        .then(html => {
            document.getElementById(modalId).querySelector('.modal-content').innerHTML = html;
            document.getElementById(modalId).style.display = 'block';
            history.pushState(null, '', endpoint);
        });
}

// Закрытие модального окна
function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
    history.back();
}
const button = document.getElementById('buttonDef');
   button.addEventListener('click', () => {
       const modalId = button.getAttribute('data-modal-id');
       const url = button.getAttribute('data-url');
       openModal(modalId, url);
   });
