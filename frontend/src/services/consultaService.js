import api from './api';

export const consultaService = {
  getAll: async () => (await api.get('/consultas')).data,

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
      observaciones: data.observaciones
    };
    return (await api.post('/consultas', payload)).data;
  },

  update: async (id, data) => {
    const payload = {
      paciente: { idPaciente: data.idPaciente },
      medico: { idMedico: data.idMedico },
      cita: data.idCita ? { idCita: data.idCita } : null,
      fecha: data.fecha,
      hora: data.hora,
      motivoConsulta: data.motivoConsulta,
      observaciones: data.observaciones
    };
    return (await api.put(`/consultas/${id}`, payload)).data;
  },

  delete: async (id) => await api.delete(`/consultas/${id}`)
};