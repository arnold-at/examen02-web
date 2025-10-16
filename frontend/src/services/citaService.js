import api from './api';

export const citaService = {
  getAll: async () => {
    const response = await api.get('/citas');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/citas/${id}`);
    return response.data;
  },

  create: async (cita) => {
    const payload = {
      paciente: { idPaciente: cita.idPaciente },
      medico: { idMedico: cita.idMedico },
      fecha: cita.fecha,
      hora: cita.hora,
      motivo: cita.motivo,
      estado: cita.estado || 'PENDIENTE'
    };
    const response = await api.post('/citas', payload);
    return response.data;
  },

  update: async (id, cita) => {
    const response = await api.put(`/citas/${id}`, cita);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/citas/${id}`);
    return response.data;
  },

  getByPaciente: async (idPaciente) => {
    const response = await api.get(`/citas/paciente/${idPaciente}`);
    return response.data;
  },

  getByMedico: async (idMedico) => {
    const response = await api.get(`/citas/medico/${idMedico}`);
    return response.data;
  },

  getByFecha: async (fecha) => {
    const response = await api.get(`/citas/fecha/${fecha}`);
    return response.data;
  },

  getByEstado: async (estado) => {
    const response = await api.get(`/citas/estado/${estado}`);
    return response.data;
  },

  getPendientesByPaciente: async (idPaciente) => {
    const response = await api.get(`/citas/paciente/${idPaciente}/pendientes`);
    return response.data;
  }
};