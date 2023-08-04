// Add an event listener to the "Generar PDF" button
    document.getElementById("generatePDFButton").addEventListener("click", function(event) {
    event.preventDefault(); // Prevent the default click behavior of the anchor tag
    showGeneratePDFAlert(); // Show the alert before generating the PDF
});

    // Función para mostrar la alerta de generación de PDF
    function showGeneratePDFAlert() {
    Swal.fire({
        title: '¿Generar PDF?',
        text: '¿Estás seguro de que deseas generar el PDF?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Sí',
        cancelButtonText: 'No',
    }).then((result) => {
        if (result.isConfirmed) {
            // Redirect to the PDF generation URL
            window.location.href = /*[[ @{/exportarPDF/{id}(id=${formulario.id})} ]]*/ '';
        }
    });
}
