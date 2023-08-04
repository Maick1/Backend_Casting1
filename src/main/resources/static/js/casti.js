
    function confirmDelete(event, id) {
    event.preventDefault(); // prevent the link from being followed
    Swal.fire({
    title: '¿Estás seguro?',
    text: "No podrás revertir esto!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Sí, bórralo!'
}).then((result) => {
    if (result.isConfirmed) {
    window.location.href = "/casting/delete/" + id;
}
})
}
