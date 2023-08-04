
    document.getElementById("poseeManager").addEventListener("change", function () {
    var preguntasDiv = document.getElementById("preguntas");
    if (this.value === "Si") {
    preguntasDiv.style.display = "block";
} else {
    preguntasDiv.style.display = "none";
}
});

    document.getElementById("m_edad").addEventListener("change", function () {
    var tutorFieldsDiv = document.getElementById("tutorFields");
    if (this.value === "Si") {
    tutorFieldsDiv.style.display = "block";
} else {
    tutorFieldsDiv.style.display = "none";
}
});

    function enviarFormulario() {
    // Validar si todos los campos obligatorios están llenos
    var camposObligatorios = document.querySelectorAll("[required]");
    var camposVacios = Array.from(camposObligatorios).filter(campo => !campo.value);

    // Añadir validación para longitud de caracteres
    var camposDeLongitudEstricta = ["cedula", "celuar"];
    var camposConLongitudIncorrecta = camposDeLongitudEstricta.filter(id => document.getElementById(id).value.length !== 10);
    var cedula = document.getElementById("cedula").value;
    if (!validarCedula(cedula)) {
    Swal.fire({
    title: 'Error',
    text: 'La cédula ingresada no es válida. Por favor, verifica e inténtalo de nuevo.',
    icon: 'error',
    confirmButtonText: 'Aceptar'
});
    return;
}

    // Agregar clases "is-invalid" y mostrar mensajes de error en rojo
    camposObligatorios.forEach(campo => {
    if (!campo.value) {
    campo.classList.add("is-invalid");
} else {
    campo.classList.remove("is-invalid");
}
});

    camposConLongitudIncorrecta.forEach(id => {
    document.getElementById(id).classList.add("is-invalid");
});

    if (camposVacios.length > 0 || camposConLongitudIncorrecta.length > 0) {
    // Si hay campos vacíos o con longitud incorrecta, muestra una alerta de error
    Swal.fire({
    title: 'Error',
    text: 'Por favor, completa todos los campos obligatorios y asegúrate de que los campos de cédula y celular contengan exactamente 10 dígitos.',
    icon: 'error',
    confirmButtonText: 'Aceptar'
});
} else {
    // Verificar si la cédula ya existe
    $.ajax({
    url: '/checkCedula',
    data: {'cedula': cedula},
    success: function (data) {
    if (data.exists) {
    Swal.fire({
    title: 'Error',
    text: 'La cédula ya existe en el sistema.',
    icon: 'error',
    confirmButtonText: 'Aceptar'
});
} else {
    // Si la cédula no existe, enviar el formulario
    Swal.fire({
    title: '¡Formulario enviado!',
    text: 'Tu formulario se ha enviado correctamente.',
    icon: 'success',
    confirmButtonText: 'Aceptar',
    timer: 5000 // Tiempo en milisegundos (5 segundos en este ejemplo)
}).then(() => {
    // Una vez que se ha mostrado la alerta de éxito, envía el formulario
    document.querySelector("form").submit();
});
}
},
    error: function (err) {
    // Manejo de errores
    console.error(err);
}
});
}
}

    // Agrega esta función para obtener la fecha actual en formato yyyy-mm-dd
    function obtenerFechaActual() {
    const fecha = new Date();
    const año = fecha.getFullYear();
    const mes = String(fecha.getMonth() + 1).padStart(2, '0');
    const dia = String(fecha.getDate()).padStart(2, '0');
    return `${año}-${mes}-${dia}`;
}

    // Agrega esta función para asignar la fecha actual al campo de fecha de ingreso
    function asignarFechaActual() {
    const fechaActual = obtenerFechaActual();
    document.getElementById("fechaIngreso").value = fechaActual;
}

    // Función para validar la cédula
    function validarCedula() {
    const cedulaField = document.getElementById("cedula");
    const cedulaError = document.getElementById("cedula-error");
    const cedula = cedulaField.value;

    // Asegurarse de que la entrada solo contiene números
    if (!(/^\d+$/.test(cedula))) {
    cedulaError.style.display = "block";
    return false;
}

    // Comprobar que la longitud del número sea exactamente 10 dígitos
    if (cedula.length !== 10) {
    cedulaError.style.display = "block";
    return false;
}

    // Algoritmo de validación de cédula ecuatoriana
    let digits = cedula.split("");
    let thirdDigit = parseInt(digits[2], 10);
    if (thirdDigit < 0 || thirdDigit > 6) {
    cedulaError.style.display = "block";
    return false;
}

    let sum = 0;
    let digit = 0;
    for (let i = 0; i < digits.length - 1; i++) {
    digit = parseInt(digits[i], 10);
    if (i % 2 === 0) {
    digit = digit * 2;
    if (digit > 9) {
    digit = digit - 9;
}
}
    sum += digit;
}

    let lastDigit = sum % 10 === 0 ? 0 : 10 - sum % 10;
    if (lastDigit !== parseInt(digits[9], 10)) {
    cedulaError.style.display = "block";
    return false;
}

    // Si la cédula es válida, oculta el mensaje de error
    cedulaError.style.display = "none";
    return true;
}

    // Llama a la función al cargar la página para asignar la fecha actual al campo de fecha de ingreso
    window.onload = function () {
    asignarFechaActual();
};
