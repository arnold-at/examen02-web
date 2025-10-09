import api from './api';

export const diagnosticoService = {
  getByConsulta: async (idConsulta) => {
    const response = await api.get(`/diagnosticos/consulta/${idConsulta}`);
    return response.data;
  },

  create: async (diagnostico) => {
    const response = await api.post('/diagnosticos', diagnostico);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/diagnosticos/${id}`);
    return response.data;
  }
};