import api from './api';

export const historiaClinicaService = {
  getAll: async () => {
    const response = await api.get('/historias');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/historias/${id}`);
    return response.data;
  },

  getByPaciente: async (idPaciente) => {
    const response = await api.get(`/historias/paciente/${idPaciente}`);
    return response.data;
  },

  update: async (id, historia) => {
    const response = await api.put(`/historias/${id}`, historia);
    return response.data;
  },

  agregarAntecedente: async (idHistoria, antecedente) => {
    const response = await api.post(`/historias/${idHistoria}/antecedentes`, antecedente);
    return response.data;
  },

  eliminarAntecedente: async (idHistoria, indice) => {
    const response = await api.delete(`/historias/${idHistoria}/antecedentes/${indice}`);
    return response.data;
  },

  getAntecedentes: async (idHistoria) => {
    const response = await api.get(`/historias/${idHistoria}/antecedentes`);
    return response.data;
  }
};