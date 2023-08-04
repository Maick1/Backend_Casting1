
    function limpiarCampos(){
    window.location='/clasificar';
}

    // Función para manejar el clic en el ícono de corazón
    function toggleLike(event) {
    event.preventDefault();
    var heartIcon = event.target.parentElement; /* Selecciona el contenedor del ícono de corazón */
    heartIcon.classList.toggle('liked');

    // Verificar si el corazón tiene la clase 'liked'
    var isLiked = heartIcon.classList.contains('liked');

    // Si se agregó la clase 'liked', agregar la animación
    if (isLiked) {
    heartIcon.classList.add('animate__heartBeat');
    // Eliminar la animación después de cierto tiempo (1000ms = 1 segundo)
    setTimeout(function () {
    heartIcon.classList.remove('animate__heartBeat');
}, 1000);
}
}

    // Agregar evento de clic a los iconos de corazón
    var heartIcons = document.querySelectorAll('.heart-icon');
    heartIcons.forEach(function (icon) {
    icon.addEventListener('click', toggleLike);
});
