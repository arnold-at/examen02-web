import { useState, useEffect } from 'react';
import { Search, Plus, Edit, Trash2, X, Eye } from 'lucide-react';
import { consultaService } from '../services/consultaService';
import { pacienteService } from '../services/pacienteService';
import { medicoService } from '../services/medicoService';
import { citaService } from '../services/citaService';
import DetalleConsulta from './DetalleConsulta';

const Consultas = () => {
  const [consultas, setConsultas] = useState([]);
  const [pacientes, setPacientes] = useState([]);
  const [medicos, setMedicos] = useState([]);
  const [citas, setCitas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDetalleModal, setShowDetalleModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedConsulta, setSelectedConsulta] = useState(null);
  const [consultaDetalle, setConsultaDetalle] = useState(null);
  const [formData, setFormData] = useState({
    idPaciente: '',
    idMedico: '',
    idCita: '',
    fecha: '',
    hora: '',
    motivoConsulta: '',
    observaciones: ''
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [consultasData, pacientesData, medicosData, citasData] = await Promise.all([
        consultaService.getAll(),
        pacienteService.getAll(),
        medicoService.getAll(),
        citaService.getAll()
      ]);
      setConsultas(consultasData);
      setPacientes(pacientesData);
      setMedicos(medicosData);
      setCitas(citasData);
    } catch (error) {
      console.error('Error al cargar datos:', error);
      alert('Error al cargar los datos');
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (selectedConsulta) {
        await consultaService.update(selectedConsulta.idConsulta, formData);
        alert('Consulta actualizada exitosamente');
      } else {
        await consultaService.create(formData);
        alert('Consulta registrada exitosamente');
      }
      setShowModal(false);
      resetForm();
      loadData();
    } catch (error) {
      console.error('Error al guardar consulta:', error);
      alert('Error al guardar la consulta');
    }
  };

  const handleEdit = (consulta) => {
    setSelectedConsulta(consulta);
    setFormData({
      idPaciente: consulta.paciente?.idPaciente || '',
      idMedico: consulta.medico?.idMedico || '',
      idCita: consulta.cita?.idCita || '',
      fecha: consulta.fecha,
      hora: consulta.hora,
      motivoConsulta: consulta.motivoConsulta,
      observaciones: consulta.observaciones
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar esta consulta?')) {
      try {
        await consultaService.delete(id);
        alert('Consulta eliminada exitosamente');
        loadData();
      } catch (error) {
        console.error('Error al eliminar consulta:', error);
        alert('Error al eliminar la consulta');
      }
    }
  };

  const handleVerDetalle = (consulta) => {
    setConsultaDetalle(consulta);
    setShowDetalleModal(true);
  };

  const resetForm = () => {
    setFormData({
      idPaciente: '',
      idMedico: '',
      idCita: '',
      fecha: '',
      hora: '',
      motivoConsulta: '',
      observaciones: ''
    });
    setSelectedConsulta(null);
  };

  const openNewModal = () => {
    resetForm();
    setShowModal(true);
  };

  const filteredConsultas = consultas.filter(consulta => {
    const searchLower = searchTerm.toLowerCase();
    const pacienteNombre = `${consulta.paciente?.nombres || ''} ${consulta.paciente?.apellidos || ''}`.toLowerCase();
    const medicoNombre = `${consulta.medico?.nombres || ''} ${consulta.medico?.apellidos || ''}`.toLowerCase();
    return (
      pacienteNombre.includes(searchLower) ||
      medicoNombre.includes(searchLower) ||
      consulta.motivoConsulta?.toLowerCase().includes(searchLower) ||
      consulta.fecha?.toString().includes(searchTerm)
    );
  });

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-xl text-gray-600">Cargando consultas...</div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-800">Gestión de Consultas</h1>
        <button
          onClick={openNewModal}
          className="flex items-center gap-2 bg-gradient-to-r from-green-600 to-green-700 text-white px-6 py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all shadow-md"
        >
          <Plus className="w-5 h-5" />
          Nueva Consulta
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-md p-6">
        <div className="flex gap-4 mb-6">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
            <input
              type="text"
              placeholder="Buscar consulta..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
            />
          </div>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b-2 border-gray-200">
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Fecha</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Hora</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Paciente</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Médico</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Motivo</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filteredConsultas.map((consulta) => (
                <tr key={consulta.idConsulta} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4">{consulta.fecha}</td>
                  <td className="py-3 px-4">{consulta.hora}</td>
                  <td className="py-3 px-4">
                    {consulta.paciente?.nombres} {consulta.paciente?.apellidos}
                  </td>
                  <td className="py-3 px-4">
                    Dr. {consulta.medico?.nombres} {consulta.medico?.apellidos}
                  </td>
                  <td className="py-3 px-4">{consulta.motivoConsulta}</td>
                  <td className="py-3 px-4">
                    <div className="flex gap-2">
                      <button
                        onClick={() => handleVerDetalle(consulta)}
                        className="p-2 bg-purple-100 text-purple-700 rounded-lg hover:bg-purple-200 transition-colors"
                        title="Ver Detalles"
                      >
                        <Eye className="w-4 h-4" />
                      </button>
                      <button
                        onClick={() => handleEdit(consulta)}
                        className="p-2 bg-blue-100 text-blue-700 rounded-lg hover:bg-blue-200 transition-colors"
                      >
                        <Edit className="w-4 h-4" />
                      </button>
                      <button
                        onClick={() => handleDelete(consulta.idConsulta)}
                        className="p-2 bg-red-100 text-red-700 rounded-lg hover:bg-red-200 transition-colors"
                      >
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl p-8 w-full max-w-2xl max-h-[90vh] overflow-y-auto">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-2xl font-bold text-gray-800">
                {selectedConsulta ? 'Editar Consulta' : 'Registrar Nueva Consulta'}
              </h2>
              <button onClick={() => setShowModal(false)} className="p-2 hover:bg-gray-100 rounded-lg">
                <X className="w-5 h-5" />
              </button>
            </div>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Paciente *</label>
                  <select
                    name="idPaciente"
                    value={formData.idPaciente}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  >
                    <option value="">Seleccionar paciente</option>
                    {pacientes.map(p => (
                      <option key={p.idPaciente} value={p.idPaciente}>
                        {p.nombres} {p.apellidos} - {p.dni}
                      </option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Médico *</label>
                  <select
                    name="idMedico"
                    value={formData.idMedico}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  >
                    <option value="">Seleccionar médico</option>
                    {medicos.map(m => (
                      <option key={m.idMedico} value={m.idMedico}>
                        Dr. {m.nombres} {m.apellidos}
                      </option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Cita (opcional)</label>
                  <select
                    name="idCita"
                    value={formData.idCita}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                  >
                    <option value="">Sin cita asociada</option>
                    {citas.map(c => (
                      <option key={c.idCita} value={c.idCita}>
                        Cita {c.fecha} - {c.hora}
                      </option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Fecha *</label>
                  <input
                    type="date"
                    name="fecha"
                    value={formData.fecha}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Hora *</label>
                  <input
                    type="time"
                    name="hora"
                    value={formData.hora}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">Motivo de Consulta *</label>
                  <textarea
                    name="motivoConsulta"
                    value={formData.motivoConsulta}
                    onChange={handleInputChange}
                    rows="3"
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  ></textarea>
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">Observaciones</label>
                  <textarea
                    name="observaciones"
                    value={formData.observaciones}
                    onChange={handleInputChange}
                    rows="3"
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                  ></textarea>
                </div>
              </div>
              <div className="flex gap-4 pt-4">
                <button
                  type="submit"
                  className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium"
                >
                  {selectedConsulta ? 'Actualizar' : 'Registrar'} Consulta
                </button>
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="flex-1 bg-gray-200 text-gray-700 py-3 rounded-lg hover:bg-gray-300 transition-colors font-medium"
                >
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {showDetalleModal && (
        <DetalleConsulta
          consulta={consultaDetalle}
          onClose={() => {
            setShowDetalleModal(false);
            loadData();
          }}
        />
      )}
    </div>
  );
};

export default Consultas;