import api from './api';

export const medicoEspecialidadService = {
  asignar: async (idMedico, idEspecialidad) => {
    const response = await api.post('/medico-especialidades/asignar', {
      idMedico,
      idEspecialidad
    });
    return response.data;
  },

  eliminar: async (id) => {
    const response = await api.delete(`/medico-especialidades/${id}`);
    return response.data;
  }
};