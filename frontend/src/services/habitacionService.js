import api from './api';

export const habitacionService = {
  getAll: async () => {
    const response = await api.get('/habitaciones');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/habitaciones/${id}`);
    return response.data;
  },

  create: async (habitacion) => {
    const response = await api.post('/habitaciones', habitacion);
    return response.data;
  },

  update: async (id, habitacion) => {
    const response = await api.put(`/habitaciones/${id}`, habitacion);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/habitaciones/${id}`);
    return response.data;
  }
};