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
