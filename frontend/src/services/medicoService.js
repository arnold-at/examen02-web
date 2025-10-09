import api from './api';

export const medicoService = {
  getAll: async () => {
    const response = await api.get('/medicos');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/medicos/${id}`);
    return response.data;
  },

  create: async (medico) => {
    const response = await api.post('/medicos', medico);
    return response.data;
  },

  update: async (id, medico) => {
    const response = await api.put(`/medicos/${id}`, medico);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/medicos/${id}`);
    return response.data;
  },

  // Obtener especialidades
  getEspecialidades: async () => {
    const response = await api.get('/especialidades');
    return response.data;
  }
};