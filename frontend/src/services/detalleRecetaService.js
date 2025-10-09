import api from './api';

export const detalleRecetaService = {
  getByReceta: async (idReceta) => {
    const response = await api.get(`/detalles/receta/${idReceta}`);
    return response.data;
  },

  create: async (detalle) => {
    const response = await api.post('/detalles', detalle);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/detalles/${id}`);
    return response.data;
  }
};