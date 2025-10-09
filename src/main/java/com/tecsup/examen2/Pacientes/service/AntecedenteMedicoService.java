package com.tecsup.examen2.Pacientes.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tecsup.examen2.Pacientes.repository.AntecedenteMedicoRepository;
import com.tecsup.examen2.Pacientes.repository.HistoriaClinicaRepository;
import com.tecsup.examen2.Pacientes.model.AntecedenteMedico;
import com.tecsup.examen2.Pacientes.model.HistoriaClinica;
import java.util.List;

@Service
@Transactional
public class AntecedenteMedicoService {

    private final AntecedenteMedicoRepository repo;
    private final HistoriaClinicaRepository historiaRepo;

    public AntecedenteMedicoService(AntecedenteMedicoRepository repo,
                                    HistoriaClinicaRepository historiaRepo) {
        this.repo = repo;
        this.historiaRepo = historiaRepo;
    }

    public List<AntecedenteMedico> findByHistoria(Long idHistoria) {
        return repo.findAllByHistoriaClinica_IdHistoria(idHistoria);
    }

    public AntecedenteMedico save(AntecedenteMedico antecedente) {
        if (antecedente.getHistoriaClinica() != null
                && antecedente.getHistoriaClinica().getIdHistoria() != null) {

            Long idHistoria = antecedente.getHistoriaClinica().getIdHistoria();

            HistoriaClinica historia = historiaRepo.findById(idHistoria)
                    .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));

            antecedente.setHistoriaClinica(historia);
        } else {
            throw new RuntimeException("Se requiere una historia clínica válida para guardar el antecedente");
        }

        return repo.save(antecedente);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
