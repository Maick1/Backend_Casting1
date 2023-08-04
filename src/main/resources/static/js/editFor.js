// Función para mostrar la alerta de confirmación de envío del formulario
    function enviarFormulario() {
    // Validar el formulario antes de enviarlo
    var formulario = document.getElementById("formulario");
    if (formulario.checkValidity()) {
    // Mostrar la alerta de confirmación
    Swal.fire({
    title: '¡Formulario enviado!',
    text: 'Tu formulario se ha enviado correctamente.',
    icon: 'success',
    confirmButtonText: 'Aceptar',
    timer: 4000 // Tiempo en milisegundos (3 segundos en este ejemplo)
}).then(function() {
    // Submit the form once the user clicks "Aceptar" on the SweetAlert2 alert
    formulario.submit();
});
}
}
