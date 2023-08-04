
    function confirmSubmit() {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡Vas a guardar un nuevo casting!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, ¡guardarlo!'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById('castingForm').submit();
        }
    })
}
