import api from './api';

export const antecedenteMedicoService = {
  getByHistoria: async (idHistoria) => {
    const response = await api.get(`/antecedentes/historia/${idHistoria}`);
    return response.data;
  },

  create: async (antecedente) => {
    const response = await api.post('/antecedentes', antecedente);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/antecedentes/${id}`);
    return response.data;
  }
};