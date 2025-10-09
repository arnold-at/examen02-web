import api from './api';

export const hospitalizacionService = {
  getAll: async () => {
    const response = await api.get('/hospitalizaciones');
    return response.data;
  },

  internar: async (hospitalizacion) => {
    const response = await api.post('/hospitalizaciones/internar', hospitalizacion);
    return response.data;
  },

  darAlta: async (id) => {
    const response = await api.post(`/hospitalizaciones/${id}/alta`);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/hospitalizaciones/${id}`);
    return response.data;
  }
};