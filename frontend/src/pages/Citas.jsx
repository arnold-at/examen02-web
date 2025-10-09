import { useState, useEffect } from 'react';
import { Search, Plus, Edit, Trash2, X } from 'lucide-react';
import { citaService } from '../services/citaService';
import { pacienteService } from '../services/pacienteService';
import { medicoService } from '../services/medicoService';

const Citas = () => {
  const [citas, setCitas] = useState([]);
  const [pacientes, setPacientes] = useState([]);
  const [medicos, setMedicos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCita, setSelectedCita] = useState(null);
  const [formData, setFormData] = useState({
    idPaciente: '',
    idMedico: '',
    fecha: '',
    hora: '',
    motivo: '',
    estado: 'programada'
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [citasData, pacientesData, medicosData] = await Promise.all([
        citaService.getAll(),
        pacienteService.getAll(),
        medicoService.getAll()
      ]);
      setCitas(citasData);
      setPacientes(pacientesData);
      setMedicos(medicosData);
    } catch (error) {
      console.error('Error al cargar datos:', error);
      alert('Error al cargar los datos');
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const cita = {
      paciente: { idPaciente: parseInt(formData.idPaciente) },
      medico: { idMedico: parseInt(formData.idMedico) },
      fecha: formData.fecha,
      hora: formData.hora,
      motivo: formData.motivo,
      estado: formData.estado
    };

    try {
      if (selectedCita) {
        await citaService.update(selectedCita.idCita, cita);
        alert('Cita actualizada exitosamente');
      } else {
        await citaService.create(cita);
        alert('Cita registrada exitosamente');
      }

      setShowModal(false);
      resetForm();
      loadData();
    } catch (error) {
      console.error('Error al guardar cita:', error);
      alert('Error al guardar la cita');
    }
  };

  const handleEdit = (cita) => {
    setSelectedCita(cita);
    setFormData({
      idPaciente: cita.paciente?.idPaciente || '',
      idMedico: cita.medico?.idMedico || '',
      fecha: cita.fecha,
      hora: cita.hora,
      motivo: cita.motivo,
      estado: cita.estado
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar esta cita?')) {
      try {
        await citaService.delete(id);
        alert('Cita eliminada exitosamente');
        loadData();
      } catch (error) {
        console.error('Error al eliminar cita:', error);
        alert('Error al eliminar la cita');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      idPaciente: '',
      idMedico: '',
      fecha: '',
      hora: '',
      motivo: '',
      estado: 'programada'
    });
    setSelectedCita(null);
  };

  const openNewModal = () => {
    resetForm();
    setShowModal(true);
  };

  const getEstadoColor = (estado) => {
    const colors = {
      programada: 'bg-blue-100 text-blue-700',
      atendida: 'bg-green-100 text-green-700',
      cancelada: 'bg-red-100 text-red-700'
    };
    return colors[estado] || 'bg-gray-100 text-gray-700';
  };

  const filteredCitas = citas.filter(cita => {
    const searchLower = searchTerm.toLowerCase();
    const pacienteNombre = `${cita.paciente?.nombres || ''} ${cita.paciente?.apellidos || ''}`.toLowerCase();
    const medicoNombre = `${cita.medico?.nombres || ''} ${cita.medico?.apellidos || ''}`.toLowerCase();
    return (
      pacienteNombre.includes(searchLower) ||
      medicoNombre.includes(searchLower) ||
      cita.motivo?.toLowerCase().includes(searchLower) ||
      cita.fecha?.toString().includes(searchTerm)
    );
  });

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-xl text-gray-600">Cargando citas...</div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-800">Gestión de Citas Médicas</h1>
        <button
          onClick={openNewModal}
          className="flex items-center gap-2 bg-gradient-to-r from-green-600 to-green-700 text-white px-6 py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all shadow-md"
        >
          <Plus className="w-5 h-5" />
          Nueva Cita
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-md p-6">
        <div className="flex gap-4 mb-6">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
            <input
              type="text"
              placeholder="Buscar cita..."
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
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Estado</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filteredCitas.map((cita) => (
                <tr key={cita.idCita} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4">{cita.fecha}</td>
                  <td className="py-3 px-4">{cita.hora}</td>
                  <td className="py-3 px-4">
                    {cita.paciente?.nombres} {cita.paciente?.apellidos}
                  </td>
                  <td className="py-3 px-4">
                    Dr. {cita.medico?.nombres} {cita.medico?.apellidos}
                  </td>
                  <td className="py-3 px-4">{cita.motivo}</td>
                  <td className="py-3 px-4">
                    <span className={`px-3 py-1 rounded-full text-xs font-medium ${getEstadoColor(cita.estado)}`}>
                      {cita.estado}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <div className="flex gap-2">
                      <button
                        onClick={() => handleEdit(cita)}
                        className="p-2 bg-blue-100 text-blue-700 rounded-lg hover:bg-blue-200 transition-colors"
                      >
                        <Edit className="w-4 h-4" />
                      </button>
                      <button
                        onClick={() => handleDelete(cita.idCita)}
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
                {selectedCita ? 'Editar Cita' : 'Registrar Nueva Cita'}
              </h2>
              <button
                onClick={() => setShowModal(false)}
                className="p-2 hover:bg-gray-100 rounded-lg"
              >
                <X className="w-5 h-5" />
              </button>
            </div>
            
            <div className="space-y-4">
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
                  <label className="block text-sm font-medium text-gray-700 mb-2">Motivo *</label>
                  <textarea
                    name="motivo"
                    value={formData.motivo}
                    onChange={handleInputChange}
                    rows="3"
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  ></textarea>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Estado</label>
                  <select
                    name="estado"
                    value={formData.estado}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                  >
                    <option value="programada">Programada</option>
                    <option value="atendida">Atendida</option>
                    <option value="cancelada">Cancelada</option>
                  </select>
                </div>
              </div>
              <div className="flex gap-4 pt-4">
                <button
                  onClick={handleSubmit}
                  className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium"
                >
                  {selectedCita ? 'Actualizar' : 'Registrar'} Cita
                </button>
                <button
                  onClick={() => setShowModal(false)}
                  className="flex-1 bg-gray-200 text-gray-700 py-3 rounded-lg hover:bg-gray-300 transition-colors font-medium"
                >
                  Cancelar
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Citas;