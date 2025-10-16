import { useState, useEffect } from 'react';
import { Search, Plus, X, CheckCircle, Loader2 } from 'lucide-react';
import { hospitalizacionService } from '../services/hospitalizacionService';
import { pacienteService } from '../services/pacienteService';
import { habitacionService } from '../services/habitacionService';

const Hospitalizacion = () => {
  const [hospitalizaciones, setHospitalizaciones] = useState([]);
  const [pacientes, setPacientes] = useState([]);
  const [habitaciones, setHabitaciones] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [formData, setFormData] = useState({
    idPaciente: '',
    idHabitacion: '',
    diagnosticoIngreso: ''
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [hospData, pacientesData, habitacionesData] = await Promise.all([
        hospitalizacionService.getAll(),
        pacienteService.getAll(),
        habitacionService.getAll()
      ]);
      setHospitalizaciones(hospData);
      setPacientes(pacientesData);
      setHabitaciones(habitacionesData);
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
    setSaving(true);

    const hospitalizacion = {
      // ⚠️ CAMBIO: NO usar parseInt(), dejar IDs como string
      idPaciente: formData.idPaciente,
      idHabitacion: formData.idHabitacion,
      fechaIngreso: new Date().toISOString().split('T')[0],
      diagnosticoIngreso: formData.diagnosticoIngreso,
      estado: 'EN_CURSO'
    };

    try {
      await hospitalizacionService.create(hospitalizacion);
      alert('Paciente internado exitosamente');
      setShowModal(false);
      resetForm();
      loadData();
    } catch (error) {
      console.error('Error al internar paciente:', error);
      alert('Error al internar el paciente');
    } finally {
      setSaving(false);
    }
  };

  const handleAlta = async (id) => {
    if (window.confirm('¿Estás seguro de dar de alta a este paciente?')) {
      try {
        const fechaAlta = new Date().toISOString().split('T')[0];
        await hospitalizacionService.darDeAlta(id, fechaAlta);
        alert('Alta registrada exitosamente');
        loadData();
      } catch (error) {
        console.error('Error al dar alta:', error);
        alert('Error al dar de alta');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      idPaciente: '',
      idHabitacion: '',
      diagnosticoIngreso: ''
    });
  };

  const getEstadoColor = (estado) => {
    const colors = {
      'EN_CURSO': 'bg-blue-100 text-blue-700',
      'FINALIZADA': 'bg-green-100 text-green-700'
    };
    return colors[estado] || 'bg-gray-100 text-gray-700';
  };

  const filteredHospitalizaciones = hospitalizaciones.filter(hosp => {
    const searchLower = searchTerm.toLowerCase();
    const pacienteNombre = `${hosp.paciente?.nombres || ''} ${hosp.paciente?.apellidos || ''}`.toLowerCase();
    const habitacionNumero = hosp.habitacion?.numero?.toLowerCase() || '';
    return (
      pacienteNombre.includes(searchLower) ||
      habitacionNumero.includes(searchLower) ||
      hosp.diagnosticoIngreso?.toLowerCase().includes(searchLower) ||
      hosp.fechaIngreso?.toString().includes(searchTerm)
    );
  });

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="flex flex-col items-center gap-3">
          <Loader2 className="w-8 h-8 animate-spin text-green-600" />
          <div className="text-xl text-gray-600">Cargando hospitalizaciones...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-800">Gestión de Hospitalización</h1>
        <button
          onClick={() => { resetForm(); setShowModal(true); }}
          className="flex items-center gap-2 bg-gradient-to-r from-green-600 to-green-700 text-white px-6 py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all shadow-md"
        >
          <Plus className="w-5 h-5" />
          Internar Paciente
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-md p-6">
        <div className="flex gap-4 mb-6">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
            <input
              type="text"
              placeholder="Buscar hospitalización..."
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
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Paciente</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Habitación</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Fecha Ingreso</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Fecha Alta</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Diagnóstico</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Estado</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filteredHospitalizaciones.map((hosp) => (
                <tr key={hosp.idHosp} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4">
                    {hosp.paciente?.nombres} {hosp.paciente?.apellidos}
                  </td>
                  <td className="py-3 px-4">
                    Hab. {hosp.habitacion?.numero} - {hosp.habitacion?.tipo}
                  </td>
                  <td className="py-3 px-4">{hosp.fechaIngreso}</td>
                  <td className="py-3 px-4">{hosp.fechaAlta || '-'}</td>
                  <td className="py-3 px-4">{hosp.diagnosticoIngreso}</td>
                  <td className="py-3 px-4">
                    <span className={`px-3 py-1 rounded-full text-xs font-medium ${getEstadoColor(hosp.estado)}`}>
                      {hosp.estado}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <div className="flex gap-2">
                      {hosp.estado === 'EN_CURSO' && (
                        <button
                          onClick={() => handleAlta(hosp.idHosp)}
                          className="p-2 bg-green-100 text-green-700 rounded-lg hover:bg-green-200 transition-colors"
                          title="Dar de Alta"
                        >
                          <CheckCircle className="w-4 h-4" />
                        </button>
                      )}
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
              <h2 className="text-2xl font-bold text-gray-800">Internar Paciente</h2>
              <button
                onClick={() => setShowModal(false)}
                className="p-2 hover:bg-gray-100 rounded-lg"
                disabled={saving}
              >
                <X className="w-5 h-5" />
              </button>
            </div>

            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Paciente *</label>
                <select
                  name="idPaciente"
                  value={formData.idPaciente}
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                  required
                  disabled={saving}
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
                <label className="block text-sm font-medium text-gray-700 mb-2">Habitación *</label>
                <select
                  name="idHabitacion"
                  value={formData.idHabitacion}
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                  required
                  disabled={saving}
                >
                  <option value="">Seleccionar habitación</option>
                  {habitaciones.filter(h => h.estado === 'DISPONIBLE').map(h => (
                    <option key={h.idHabitacion} value={h.idHabitacion}>
                      Hab. {h.numero} - {h.tipo}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Diagnóstico de Ingreso *</label>
                <textarea
                  name="diagnosticoIngreso"
                  value={formData.diagnosticoIngreso}
                  onChange={handleInputChange}
                  rows="4"
                  className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                  required
                  disabled={saving}
                ></textarea>
              </div>
              <div className="flex gap-4 pt-4">
                <button
                  onClick={handleSubmit}
                  disabled={saving}
                  className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center gap-2"
                >
                  {saving && <Loader2 className="w-5 h-5 animate-spin" />}
                  {saving ? 'Internando...' : 'Internar Paciente'}
                </button>
                <button
                  onClick={() => setShowModal(false)}
                  disabled={saving}
                  className="flex-1 bg-gray-200 text-gray-700 py-3 rounded-lg hover:bg-gray-300 transition-colors font-medium disabled:opacity-50 disabled:cursor-not-allowed"
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

export default Hospitalizacion;