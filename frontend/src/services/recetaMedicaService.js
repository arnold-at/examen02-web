import api from './api';

export const recetaMedicaService = {
  create: async (receta) => {
    const response = await api.post('/recetas', receta);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/recetas/${id}`);
    return response.data;
  }
};