import api from './api';

export const especialidadService = {
  getAll: async () => {
    const response = await api.get('/especialidades');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/especialidades/${id}`);
    return response.data;
  },

  create: async (especialidad) => {
    const response = await api.post('/especialidades', especialidad);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/especialidades/${id}`);
    return response.data;
  }
};