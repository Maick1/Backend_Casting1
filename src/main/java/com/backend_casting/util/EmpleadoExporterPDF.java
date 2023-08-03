package com.backend_casting.util;

import com.backend_casting.entity.Formulario;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmpleadoExporterPDF {
    private List<Formulario> formularios;

    public EmpleadoExporterPDF(List<Formulario> formularios) {
        this.formularios = formularios;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(new com.itextpdf.text.BaseColor(Color.BLUE.getRGB()));
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new com.itextpdf.text.BaseColor(Color.WHITE.getRGB()));

        celda.setPhrase(new com.itextpdf.text.Phrase("Campo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new com.itextpdf.text.Phrase("Valor", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Formulario formulario : formularios) {

            tabla.addCell("Fecha de Ingreso: ");
            if (formulario.getFechaIngreso() != null) {
                tabla.addCell(formulario.getFechaIngreso());
            } else {
                tabla.addCell("N/A");
            }

            tabla.addCell("Nombre: ");
            tabla.addCell(formulario.getNombre());

            tabla.addCell("Apellido: ");
            tabla.addCell(formulario.getApellido());

            tabla.addCell("Cedula: ");
            tabla.addCell(formulario.getCedula());

            tabla.addCell("Correo: ");
            tabla.addCell(formulario.getCorreo());

            tabla.addCell("Celular: ");
            tabla.addCell(formulario.getCeluar());

            tabla.addCell("Sexo: ");
            tabla.addCell(formulario.getSexo());

            tabla.addCell("Fecha de nacimiento: ");
            tabla.addCell(formulario.getF_nacimiento());

            tabla.addCell("Estatura: ");
            tabla.addCell(formulario.getEstatura());

            tabla.addCell("Foto: ");

            String rutaImagen = "C:/Participantes/recursos/" + formulario.getFoto();

            if (Files.exists(Paths.get(rutaImagen))) {
                try {
                    Image imagen = Image.getInstance(rutaImagen);
                    imagen.scaleToFit(100, 100);  // Ajusta la imagen a 20x20 (corrección del comentario)
                    PdfPCell celdaImagen = new PdfPCell(imagen, true);
                    tabla.addCell(celdaImagen);
                } catch (BadElementException | IOException e) {
                    tabla.addCell("Error al cargar la imagen");
                }
            } else {
                tabla.addCell("No hay foto");
            }


            tabla.addCell("Menor de Edad: ");
            tabla.addCell(formulario.getM_edad());

            tabla.addCell("Nombre del Tutor: ");
            tabla.addCell(formulario.getN_tutor());

            tabla.addCell("Celular del Tutor: ");
            tabla.addCell(formulario.getC_tutor());

            tabla.addCell("¿Manager?: ");
            tabla.addCell(formulario.getP_manager());

            tabla.addCell("Nombre del Manager: ");
            tabla.addCell(formulario.getNo_manager());

            tabla.addCell("Celular del Manager: ");
            tabla.addCell(formulario.getCel_manager());

            tabla.addCell("Talla de ropa");
            tabla.addCell(formulario.getTalla());

            tabla.addCell("Color de piel");
            tabla.addCell(formulario.getC_piel());

            tabla.addCell("Color de ojos");
            tabla.addCell(formulario.getC_ojos());

            tabla.addCell("Color de cabello");
            tabla.addCell(formulario.getC_cabello());

            tabla.addCell("Tipos de alergia");
            tabla.addCell(formulario.getAlergia());

            tabla.addCell("Comerciales realizados");
            tabla.addCell(formulario.getComerciales());

            tabla.addCell("Hobbies");
            tabla.addCell(formulario.getHobbie());

            tabla.addCell("Deportes");
            tabla.addCell(formulario.getDeporte());

            tabla.addCell("Tipo de vehículo");
            tabla.addCell(formulario.getVehiculo());

            tabla.addCell("Tipo de licencia");
            tabla.addCell(formulario.getLicencia());
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Detalles_Formulario.pdf";

        response.setHeader(cabecera, valor);

        Document documento = new Document();
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new com.itextpdf.text.BaseColor(Color.BLUE.getRGB()));

        Paragraph titulo = new Paragraph("Datos del Participante", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] { 1f, 2f });

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}
