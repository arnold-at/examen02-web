package com.tecsup.examen2.reportes.controller;

import com.tecsup.examen2.Pacientes.model.Paciente;
import com.tecsup.examen2.Pacientes.repository.PacienteRepository;
import com.tecsup.examen2.citas.model.Cita;
import com.tecsup.examen2.citas.repository.CitaRepository;
import com.tecsup.examen2.reportes.service.ReportePdfService;
import com.tecsup.examen2.reportes.service.ReporteExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReportePdfService pdfService;
    private final ReporteExcelService excelService;
    private final PacienteRepository pacienteRepository;
    private final CitaRepository citaRepository;

    public ReporteController(ReportePdfService pdfService,
                             ReporteExcelService excelService,
                             PacienteRepository pacienteRepository,
                             CitaRepository citaRepository) {
        this.pdfService = pdfService;
        this.excelService = excelService;
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
    }

    @GetMapping("/pacientes/pdf")
    public ResponseEntity<byte[]> generarPdfPacientes() {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            byte[] pdfBytes = pdfService.generarReportePacientes(pacientes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "reporte_pacientes.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/pacientes/excel")
    public ResponseEntity<byte[]> generarExcelPacientes() {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            byte[] excelBytes = excelService.generarReportePacientes(pacientes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reporte_pacientes.xlsx");

            return ResponseEntity.ok().headers(headers).body(excelBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/citas/pdf")
    public ResponseEntity<byte[]> generarPdfCitas() {
        try {
            List<Cita> citas = citaRepository.findAll();
            byte[] pdfBytes = pdfService.generarReporteCitas(citas);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "reporte_citas.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/citas/excel")
    public ResponseEntity<byte[]> generarExcelCitas() {
        try {
            List<Cita> citas = citaRepository.findAll();
            byte[] excelBytes = excelService.generarReporteCitas(citas);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reporte_citas.xlsx");

            return ResponseEntity.ok().headers(headers).body(excelBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}