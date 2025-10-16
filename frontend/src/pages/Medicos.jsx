import { useState, useEffect } from 'react';
import { Search, Plus, Edit, Trash2, X, Award, Loader2 } from 'lucide-react';
import { medicoService } from '../services/medicoService';
import { especialidadService } from '../services/especialidadService';

const Medicos = () => {
  const [medicos, setMedicos] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [showEspecialidadModal, setShowEspecialidadModal] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedMedico, setSelectedMedico] = useState(null);
  const [selectedEspecialidad, setSelectedEspecialidad] = useState('');
  const [formData, setFormData] = useState({
    nombres: '',
    apellidos: '',
    colegiatura: '',
    telefono: '',
    correo: '',
    estado: 'ACTIVO'
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [medicosData, especialidadesData] = await Promise.all([
        medicoService.getAll(),
        especialidadService.getAll()
      ]);
      setMedicos(medicosData);
      setEspecialidades(especialidadesData);
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
    
    try {
      if (selectedMedico) {
        await medicoService.update(selectedMedico.idMedico, formData);
        alert('Médico actualizado exitosamente');
      } else {
        await medicoService.create(formData);
        alert('Médico registrado exitosamente');
      }
      setShowModal(false);
      resetForm();
      loadData();
    } catch (error) {
      console.error('Error al guardar médico:', error);
      alert('Error al guardar el médico');
    } finally {
      setSaving(false);
    }
  };

  const handleEdit = (medico) => {
    setSelectedMedico(medico);
    setFormData({
      nombres: medico.nombres,
      apellidos: medico.apellidos,
      colegiatura: medico.colegiatura,
      telefono: medico.telefono,
      correo: medico.correo,
      estado: medico.estado
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar este médico?')) {
      try {
        await medicoService.delete(id);
        alert('Médico eliminado exitosamente');
        loadData();
      } catch (error) {
        console.error('Error al eliminar médico:', error);
        alert('Error al eliminar el médico');
      }
    }
  };

  const handleAsignarEspecialidad = (medico) => {
    setSelectedMedico(medico);
    setSelectedEspecialidad('');
    setShowEspecialidadModal(true);
  };

  const handleAsignar = async () => {
    if (!selectedEspecialidad) {
      alert('Seleccione una especialidad');
      return;
    }
    
    try {
      // ⭐ CAMBIO: Usar el nuevo método del service
      await medicoService.agregarEspecialidad(selectedMedico.idMedico, selectedEspecialidad);
      alert('Especialidad asignada exitosamente');
      setShowEspecialidadModal(false);
      loadData();
    } catch (error) {
      console.error('Error al asignar especialidad:', error);
      alert(error.response?.data?.message || 'Error al asignar especialidad');
    }
  };

  const handleRemoverEspecialidad = async (idMedico, idEspecialidad) => {
    if (window.confirm('¿Remover esta especialidad del médico?')) {
      try {
        // ⭐ CAMBIO: Usar el nuevo método del service
        await medicoService.eliminarEspecialidad(idMedico, idEspecialidad);
        alert('Especialidad removida exitosamente');
        loadData();
      } catch (error) {
        console.error('Error al remover especialidad:', error);
        alert('Error al remover especialidad');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      nombres: '',
      apellidos: '',
      colegiatura: '',
      telefono: '',
      correo: '',
      estado: 'ACTIVO'
    });
    setSelectedMedico(null);
  };

  const filteredMedicos = medicos.filter(m =>
    m.nombres?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    m.apellidos?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    m.colegiatura?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="flex flex-col items-center gap-3">
          <Loader2 className="w-8 h-8 animate-spin text-green-600" />
          <div className="text-xl text-gray-600">Cargando médicos...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-800">Gestión de Médicos</h1>
        <button
          onClick={() => { resetForm(); setShowModal(true); }}
          className="flex items-center gap-2 bg-gradient-to-r from-green-600 to-green-700 text-white px-6 py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all shadow-md"
        >
          <Plus className="w-5 h-5" />
          Nuevo Médico
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-md p-6">
        <div className="flex gap-4 mb-6">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
            <input
              type="text"
              placeholder="Buscar médico..."
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
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Nombres</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Apellidos</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Colegiatura</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Especialidades</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Teléfono</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Estado</th>
                <th className="text-left py-3 px-4 font-semibold text-gray-700">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filteredMedicos.map((medico) => (
                <tr key={medico.idMedico} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4">{medico.nombres}</td>
                  <td className="py-3 px-4">{medico.apellidos}</td>
                  <td className="py-3 px-4">{medico.colegiatura}</td>
                  <td className="py-3 px-4">
                    <div className="flex flex-wrap gap-1">
                      {medico.especialidades?.map((me, index) => (
                        <span
                          key={index}
                          className="inline-flex items-center gap-1 px-2 py-1 bg-blue-100 text-blue-700 rounded text-xs"
                        >
                          {me.especialidad?.nombre}
                          <button
                            onClick={() => handleRemoverEspecialidad(medico.idMedico, me.especialidad?.idEspecialidad)}
                            className="hover:text-red-600"
                          >
                            <X size={12} />
                          </button>
                        </span>
                      ))}
                    </div>
                  </td>
                  <td className="py-3 px-4">{medico.telefono}</td>
                  <td className="py-3 px-4">
                    <span className={`px-3 py-1 rounded-full text-xs font-medium ${
                      medico.estado === 'ACTIVO' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
                    }`}>
                      {medico.estado}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <div className="flex gap-2">
                      <button
                        onClick={() => handleAsignarEspecialidad(medico)}
                        className="p-2 bg-purple-100 text-purple-700 rounded-lg hover:bg-purple-200 transition-colors"
                        title="Asignar Especialidad"
                      >
                        <Award className="w-4 h-4" />
                      </button>
                      <button
                        onClick={() => handleEdit(medico)}
                        className="p-2 bg-blue-100 text-blue-700 rounded-lg hover:bg-blue-200 transition-colors"
                      >
                        <Edit className="w-4 h-4" />
                      </button>
                      <button
                        onClick={() => handleDelete(medico.idMedico)}
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

      {/* MODAL MÉDICO */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl p-8 w-full max-w-2xl">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-2xl font-bold text-gray-800">
                {selectedMedico ? 'Editar Médico' : 'Registrar Nuevo Médico'}
              </h2>
              <button 
                onClick={() => setShowModal(false)} 
                className="p-2 hover:bg-gray-100 rounded-lg"
                disabled={saving}
              >
                <X className="w-5 h-5" />
              </button>
            </div>
            
            <div className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Nombres *</label>
                  <input
                    type="text"
                    name="nombres"
                    value={formData.nombres}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                    disabled={saving}
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Apellidos *</label>
                  <input
                    type="text"
                    name="apellidos"
                    value={formData.apellidos}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                    disabled={saving}
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Colegiatura *</label>
                  <input
                    type="text"
                    name="colegiatura"
                    value={formData.colegiatura}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                    disabled={saving}
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Teléfono</label>
                  <input
                    type="text"
                    name="telefono"
                    value={formData.telefono}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    disabled={saving}
                  />
                </div>
                <div className="col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">Correo</label>
                  <input
                    type="email"
                    name="correo"
                    value={formData.correo}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    disabled={saving}
                  />
                </div>
              </div>
              <div className="flex gap-4 pt-4">
                <button
                  onClick={handleSubmit}
                  disabled={saving}
                  className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center gap-2"
                >
                  {saving && <Loader2 className="w-5 h-5 animate-spin" />}
                  {saving ? 'Guardando...' : (selectedMedico ? 'Actualizar' : 'Registrar')}
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

      {/* MODAL ESPECIALIDAD */}
      {showEspecialidadModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl p-8 w-full max-w-md">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-2xl font-bold text-gray-800">Asignar Especialidad</h2>
              <button onClick={() => setShowEspecialidadModal(false)} className="p-2 hover:bg-gray-100 rounded-lg">
                <X className="w-5 h-5" />
              </button>
            </div>
            <div className="space-y-4">
              <p className="text-gray-600">
                Médico: <span className="font-semibold">{selectedMedico?.nombres} {selectedMedico?.apellidos}</span>
              </p>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Especialidad *</label>
                <select
                  value={selectedEspecialidad}
                  onChange={(e) => setSelectedEspecialidad(e.target.value)}
                  className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                >
                  <option value="">Seleccionar especialidad</option>
                  {especialidades.map(e => (
                    <option key={e.idEspecialidad} value={e.idEspecialidad}>
                      {e.nombre}
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex gap-4 pt-4">
                <button
                  onClick={handleAsignar}
                  className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium"
                >
                  Asignar
                </button>
                <button
                  onClick={() => setShowEspecialidadModal(false)}
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

export default Medicos;