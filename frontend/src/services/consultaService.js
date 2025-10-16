import api from './api';

export const consultaService = {
  getAll: async () => {
    const response = await api.get('/consultas');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/consultas/${id}`);
    return response.data;
  },

  create: async (data) => {
    const payload = {
      paciente: { idPaciente: data.idPaciente },
      medico: { idMedico: data.idMedico },
      cita: data.idCita ? { idCita: data.idCita } : null,
      fecha: data.fecha,
      hora: data.hora,
      motivoConsulta: data.motivoConsulta,
      observaciones: data.observaciones,
      diagnosticos: [] 
    };
    const response = await api.post('/consultas', payload);
    return response.data;
  },

  update: async (id, data) => {
    const payload = {
      fecha: data.fecha,
      hora: data.hora,
      motivoConsulta: data.motivoConsulta,
      observaciones: data.observaciones
    };
    const response = await api.put(`/consultas/${id}`, payload);
    return response.data;
  },

  delete: async (id) => {
    await api.delete(`/consultas/${id}`);
  },

  getByPaciente: async (idPaciente) => {
    const response = await api.get(`/consultas/paciente/${idPaciente}`);
    return response.data;
  },

  getByMedico: async (idMedico) => {
    const response = await api.get(`/consultas/medico/${idMedico}`);
    return response.data;
  },

  getByCita: async (idCita) => {
    const response = await api.get(`/consultas/cita/${idCita}`);
    return response.data;
  },

  agregarDiagnostico: async (idConsulta, diagnostico) => {
    const response = await api.post(`/consultas/${idConsulta}/diagnosticos`, diagnostico);
    return response.data;
  },

  eliminarDiagnostico: async (idConsulta, indice) => {
    const response = await api.delete(`/consultas/${idConsulta}/diagnosticos/${indice}`);
    return response.data;
  },

  getDiagnosticos: async (idConsulta) => {
    const response = await api.get(`/consultas/${idConsulta}/diagnosticos`);
    return response.data;
  }
};
