import api from './api';

export const pacienteService = {
  getAll: async () => {
    const response = await api.get('/pacientes');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/pacientes/${id}`);
    return response.data;
  },

  create: async (paciente) => {
    console.log('Datos a enviar:', paciente);
    console.log('Datos como JSON:', JSON.stringify(paciente));
    const response = await api.post('/pacientes', paciente);
    return response.data;
  },

  update: async (id, paciente) => {
    const response = await api.put(`/pacientes/${id}`, paciente);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/pacientes/${id}`);
    return response.data;
  }
};