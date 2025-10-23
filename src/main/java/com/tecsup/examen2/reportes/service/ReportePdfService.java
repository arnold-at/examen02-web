package com.tecsup.examen2.reportes.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.citas.model.Cita;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportePdfService {

    public byte[] generarReportePacientes(List<Paciente> pacientes) throws Exception {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 10);

        Paragraph title = new Paragraph("REPORTE DE PACIENTES", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 3, 3, 2, 2});

        addTableHeader(table, headerFont, "DNI", "Nombres", "Apellidos", "Teléfono", "Estado");

        for (Paciente p : pacientes) {
            addTableCell(table, bodyFont, p.getDni());
            addTableCell(table, bodyFont, p.getNombres());
            addTableCell(table, bodyFont, p.getApellidos());
            addTableCell(table, bodyFont, p.getTelefono());
            addTableCell(table, bodyFont, p.getEstado());
        }

        document.add(table);

        Paragraph footer = new Paragraph("\n\nTotal de pacientes: " + pacientes.size(), bodyFont);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);

        document.close();
        return out.toByteArray();
    }

    public byte[] generarReporteCitas(List<Cita> citas) throws Exception {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 10);

        Paragraph title = new Paragraph("REPORTE DE CITAS MÉDICAS", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 2, 3, 3, 4, 2});

        addTableHeader(table, headerFont, "Fecha", "Hora", "Paciente", "Médico", "Motivo", "Estado");

        for (Cita c : citas) {
            addTableCell(table, bodyFont, c.getFecha().toString());
            addTableCell(table, bodyFont, c.getHora().toString());
            addTableCell(table, bodyFont,
                    c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos());
            addTableCell(table, bodyFont,
                    "Dr. " + c.getMedico().getNombres() + " " + c.getMedico().getApellidos());
            addTableCell(table, bodyFont, c.getMotivo());
            addTableCell(table, bodyFont, c.getEstado());
        }

        document.add(table);

        Paragraph footer = new Paragraph("\n\nTotal de citas: " + citas.size(), bodyFont);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);

        document.close();
        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }
    }

    private void addTableCell(PdfPTable table, Font font, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "", font));
        cell.setPadding(5);
        table.addCell(cell);
    }
}