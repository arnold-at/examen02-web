import api from './api';

export const hospitalizacionService = {
  getAll: async () => {
    const response = await api.get('/hospitalizaciones');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/hospitalizaciones/${id}`);
    return response.data;
  },

  create: async (hospitalizacion) => {
    const payload = {
      paciente: { idPaciente: hospitalizacion.idPaciente },
      habitacion: { idHabitacion: hospitalizacion.idHabitacion },
      fechaIngreso: hospitalizacion.fechaIngreso,
      diagnosticoIngreso: hospitalizacion.diagnosticoIngreso,
      estado: 'EN_CURSO'
    };
    const response = await api.post('/hospitalizaciones', payload);
    return response.data;
  },

  update: async (id, hospitalizacion) => {
    const response = await api.put(`/hospitalizaciones/${id}`, hospitalizacion);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/hospitalizaciones/${id}`);
    return response.data;
  },

  getByPaciente: async (idPaciente) => {
    const response = await api.get(`/hospitalizaciones/paciente/${idPaciente}`);
    return response.data;
  },

  getByEstado: async (estado) => {
    const response = await api.get(`/hospitalizaciones/estado/${estado}`);
    return response.data;
  },

  getActivaByPaciente: async (idPaciente) => {
    const response = await api.get(`/hospitalizaciones/paciente/${idPaciente}/activa`);
    return response.data;
  },

  darDeAlta: async (id, fechaAlta) => {
    const response = await api.post(`/hospitalizaciones/${id}/alta`, null, {
      params: { fechaAlta }
    });
    return response.data;
  }
};