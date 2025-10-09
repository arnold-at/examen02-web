import api from './api';

export const historiaClinicaService = {
  getById: async (id) => {
    const response = await api.get(`/historias/${id}`);
    return response.data;
  },

  create: async (historia) => {
    const response = await api.post('/historias', historia);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/historias/${id}`);
    return response.data;
  }
};