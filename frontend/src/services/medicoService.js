import api from './api';

export const medicoService = {
  getAll: async () => {
    const response = await api.get('/medicos');
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/medicos/${id}`);
    return response.data;
  },

  create: async (medico) => {
    const response = await api.post('/medicos', medico);
    return response.data;
  },

  update: async (id, medico) => {
    const response = await api.put(`/medicos/${id}`, medico);
    return response.data;
  },

  delete: async (id) => {
    const response = await api.delete(`/medicos/${id}`);
    return response.data;
  },

  getEspecialidades: async () => {
    const response = await api.get('/especialidades');
    return response.data;
  },

  buscar: async (termino) => {
    const response = await api.get(`/medicos/buscar/${termino}`);
    return response.data;
  },

  getByEstado: async (estado) => {
    const response = await api.get(`/medicos/estado/${estado}`);
    return response.data;
  },

  agregarEspecialidad: async (idMedico, idEspecialidad) => {
    const response = await api.post(`/medicos/${idMedico}/especialidades/${idEspecialidad}`);
    return response.data;
  },

  eliminarEspecialidad: async (idMedico, idEspecialidad) => {
    const response = await api.delete(`/medicos/${idMedico}/especialidades/${idEspecialidad}`);
    return response.data;
  },

  getByEspecialidad: async (idEspecialidad) => {
    const response = await api.get(`/medicos/especialidad/${idEspecialidad}`);
    return response.data;
  }
};
