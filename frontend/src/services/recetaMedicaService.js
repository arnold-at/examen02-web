import api from './api';

export const recetaMedicaService = {
  getAll: async () => {
    const response = await api.get('/recetas');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/recetas/${id}`);
    return response.data;
  },

  create: async (receta) => {
    const payload = {
      consulta: { idConsulta: receta.idConsulta },
      indicaciones: receta.indicaciones,
      detalles: receta.detalles || []
    };
    const response = await api.post('/recetas', payload);
    return response.data;
  },

  update: async (id, receta) => {
    const response = await api.put(`/recetas/${id}`, receta);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/recetas/${id}`);
    return response.data;
  },

  getByConsulta: async (idConsulta) => {
    const response = await api.get(`/recetas/consulta/${idConsulta}`);
    return response.data;
  },

  agregarDetalle: async (idReceta, detalle) => {
    const response = await api.post(`/recetas/${idReceta}/detalles`, detalle);
    return response.data;
  },

  eliminarDetalle: async (idReceta, indice) => {
    const response = await api.delete(`/recetas/${idReceta}/detalles/${indice}`);
    return response.data;
  },

  getDetalles: async (idReceta) => {
    const response = await api.get(`/recetas/${idReceta}/detalles`);
    return response.data;
  }
};