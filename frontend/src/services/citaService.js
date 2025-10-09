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
  const response = await api.post('/citas', JSON.stringify(cita));
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

  reprogramar: async (id, nuevaFechaHora) => {
    const response = await api.put(`/citas/${id}/reprogramar`, nuevaFechaHora);
    return response.data;
  },

  cancelar: async (id) => {
    const response = await api.put(`/citas/${id}/cancelar`);
    return response.data;
  }
};