package com.tecsup.examen2.reportes.service;

import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.citas.model.Cita;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReporteExcelService {

    public byte[] generarReportePacientes(List<Paciente> pacientes) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pacientes");

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle bodyStyle = createBodyStyle(workbook);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"DNI", "Nombres", "Apellidos", "Fecha Nacimiento", "Sexo", "Teléfono", "Correo", "Estado"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Paciente p : pacientes) {
            Row row = sheet.createRow(rowNum++);

            createCell(row, 0, p.getDni(), bodyStyle);
            createCell(row, 1, p.getNombres(), bodyStyle);
            createCell(row, 2, p.getApellidos(), bodyStyle);
            createCell(row, 3, p.getFechaNacimiento() != null ? p.getFechaNacimiento().toString() : "", bodyStyle);
            createCell(row, 4, p.getSexo(), bodyStyle);
            createCell(row, 5, p.getTelefono(), bodyStyle);
            createCell(row, 6, p.getCorreo(), bodyStyle);
            createCell(row, 7, p.getEstado(), bodyStyle);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }

    public byte[] generarReporteCitas(List<Cita> citas) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Citas");

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle bodyStyle = createBodyStyle(workbook);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Fecha", "Hora", "Paciente", "Médico", "Motivo", "Estado"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Cita c : citas) {
            Row row = sheet.createRow(rowNum++);

            createCell(row, 0, c.getFecha().toString(), bodyStyle);
            createCell(row, 1, c.getHora().toString(), bodyStyle);
            createCell(row, 2, c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos(), bodyStyle);
            createCell(row, 3, "Dr. " + c.getMedico().getNombres() + " " + c.getMedico().getApellidos(), bodyStyle);
            createCell(row, 4, c.getMotivo(), bodyStyle);
            createCell(row, 5, c.getEstado(), bodyStyle);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }

    private void createCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value : "");
        cell.setCellStyle(style);
    }
}