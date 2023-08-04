
    function previewImage(event) {
    var reader = new FileReader();
    reader.onload = function () {
    var preview = document.getElementById('preview-img');
    preview.src = reader.result;
}
    reader.readAsDataURL(event.target.files[0]);
}

    function enviarCorreo() {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¿Estás seguro de enviar este correo?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, envíalo!'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire(
                'Enviado!',
                'Tu correo ha sido enviado.',
                'success'
            )
        }
    })
}

