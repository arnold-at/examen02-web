import { useState, useEffect } from 'react';
import { X, Plus, Trash2, AlertCircle, Pill, FileText } from 'lucide-react';
import { diagnosticoService } from '../services/diagnosticoService';
import { recetaMedicaService } from '../services/recetaMedicaService';
import { consultaService } from '../services/consultaService';

const DetalleConsulta = ({ consulta, onClose }) => {
  const [diagnosticos, setDiagnosticos] = useState([]);
  const [recetas, setRecetas] = useState([]);
  const [loading, setLoading] = useState(false);
  const [refreshKey, setRefreshKey] = useState(0);
  const [showDiagnosticoModal, setShowDiagnosticoModal] = useState(false);
  const [showRecetaModal, setShowRecetaModal] = useState(false);
  const [formDiagnostico, setFormDiagnostico] = useState({
    tipo: 'presuntivo',
    descripcion: ''
  });
  const [formReceta, setFormReceta] = useState({
    indicaciones: '',
    detalles: [{ medicamento: '', dosis: '', frecuencia: '', duracion: '' }]
  });

  useEffect(() => {
    loadData();
  }, [consulta, refreshKey]);

  const loadData = async () => {
    try {
      setLoading(true);
      
      const [diagnosticosData, consultaActualizada] = await Promise.all([
        diagnosticoService.getByConsulta(consulta.idConsulta),
        consultaService.getById(consulta.idConsulta)
      ]);
      
      console.log('📊 Diagnósticos cargados:', diagnosticosData);
      setDiagnosticos([...diagnosticosData]);
      
      if (consultaActualizada.recetas && consultaActualizada.recetas.length > 0) {
        setRecetas([...consultaActualizada.recetas]);
      } else {
        setRecetas([]);
      }
    } catch (error) {
      console.error('Error al cargar datos:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDiagnosticoChange = (e) => {
    const { name, value } = e.target;
    setFormDiagnostico(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmitDiagnostico = async (e) => {
    e.preventDefault();
    try {
      const diagnostico = {
        consulta: { idConsulta: consulta.idConsulta },
        tipo: formDiagnostico.tipo,
        descripcion: formDiagnostico.descripcion
      };
      
      console.log('📝 Creando diagnóstico:', diagnostico);
      const resultado = await diagnosticoService.create(diagnostico);
      console.log('✅ Diagnóstico creado:', resultado);
      
      setShowDiagnosticoModal(false);
      resetFormDiagnostico();
      
      setRefreshKey(prev => prev + 1);
      
      alert('Diagnóstico registrado exitosamente');
    } catch (error) {
      console.error('Error al guardar diagnóstico:', error);
      alert('Error al guardar el diagnóstico');
    }
  };

  const handleDeleteDiagnostico = async (id) => {
    if (window.confirm('¿Eliminar este diagnóstico?')) {
      try {
        await diagnosticoService.delete(id);
        setRefreshKey(prev => prev + 1);
        alert('Diagnóstico eliminado');
      } catch (error) {
        console.error('Error al eliminar:', error);
        alert('Error al eliminar diagnóstico');
      }
    }
  };

  const handleRecetaChange = (e) => {
    const { name, value } = e.target;
    setFormReceta(prev => ({ ...prev, [name]: value }));
  };

  const handleDetalleChange = (index, field, value) => {
    const newDetalles = [...formReceta.detalles];
    newDetalles[index][field] = value;
    setFormReceta(prev => ({ ...prev, detalles: newDetalles }));
  };

  const addDetalle = () => {
    setFormReceta(prev => ({
      ...prev,
      detalles: [...prev.detalles, { medicamento: '', dosis: '', frecuencia: '', duracion: '' }]
    }));
  };

  const removeDetalle = (index) => {
    setFormReceta(prev => ({
      ...prev,
      detalles: prev.detalles.filter((_, i) => i !== index)
    }));
  };

  const handleSubmitReceta = async (e) => {
    e.preventDefault();
    try {
      const receta = {
        consulta: { idConsulta: consulta.idConsulta },
        indicaciones: formReceta.indicaciones,
        detalles: formReceta.detalles
      };
      await recetaMedicaService.create(receta);
      setShowRecetaModal(false);
      resetFormReceta();
      setRefreshKey(prev => prev + 1);
      alert('Receta médica registrada exitosamente');
    } catch (error) {
      console.error('Error al guardar receta:', error);
      alert('Error al guardar la receta');
    }
  };

  const resetFormDiagnostico = () => {
    setFormDiagnostico({ tipo: 'presuntivo', descripcion: '' });
  };

  const resetFormReceta = () => {
    setFormReceta({
      indicaciones: '',
      detalles: [{ medicamento: '', dosis: '', frecuencia: '', duracion: '' }]
    });
  };

  const getTipoColor = (tipo) => {
    return tipo === 'definitivo'
      ? 'bg-green-100 text-green-700 border-green-200'
      : 'bg-yellow-100 text-yellow-700 border-yellow-200';
  };

  if (loading) {
    return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div className="bg-white rounded-xl p-8">
          <div className="text-xl text-gray-600">Cargando detalles...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-xl w-full max-w-5xl max-h-[90vh] overflow-y-auto">
        <div className="sticky top-0 bg-white border-b border-gray-200 p-6 flex justify-between items-center">
          <div>
            <h2 className="text-2xl font-bold text-gray-800">Detalle de Consulta</h2>
            <p className="text-sm text-gray-600 mt-1">
              {consulta.paciente?.nombres} {consulta.paciente?.apellidos} - {consulta.fecha} {consulta.hora}
            </p>
          </div>
          <button onClick={onClose} className="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <X size={24} />
          </button>
        </div>

        <div className="p-6 space-y-6">
          <div className="bg-gray-50 rounded-lg p-4">
            <p className="text-sm"><span className="font-semibold">Médico:</span> Dr. {consulta.medico?.nombres} {consulta.medico?.apellidos}</p>
            <p className="text-sm mt-2"><span className="font-semibold">Motivo:</span> {consulta.motivoConsulta}</p>
            {consulta.observaciones && (
              <p className="text-sm mt-2"><span className="font-semibold">Observaciones:</span> {consulta.observaciones}</p>
            )}
          </div>

          <div>
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-xl font-bold text-gray-800 flex items-center gap-2">
                <FileText size={24} className="text-blue-600" />
                Diagnósticos ({diagnosticos.length})
              </h3>
              <button
                onClick={() => setShowDiagnosticoModal(true)}
                className="flex items-center gap-2 bg-gradient-to-r from-blue-600 to-blue-700 text-white px-4 py-2 rounded-lg hover:from-blue-700 hover:to-blue-800 transition-all"
              >
                <Plus size={20} />
                Agregar Diagnóstico
              </button>
            </div>

            {diagnosticos.length === 0 ? (
              <div className="text-center py-8 bg-gray-50 rounded-lg">
                <AlertCircle className="mx-auto text-gray-400 mb-2" size={48} />
                <p className="text-gray-500">No hay diagnósticos registrados</p>
              </div>
            ) : (
              <div className="space-y-3">
                {diagnosticos.map((diagnostico) => (
                  <div key={diagnostico.idDiagnostico} className="bg-white border-2 rounded-lg p-4 hover:shadow-md transition-shadow">
                    <div className="flex justify-between items-start">
                      <div className="flex-1">
                        <span className={`inline-block px-3 py-1 rounded-full text-xs font-semibold border mb-2 ${getTipoColor(diagnostico.tipo)}`}>
                          {diagnostico.tipo.toUpperCase()}
                        </span>
                        <p className="text-gray-700 mt-2">{diagnostico.descripcion}</p>
                      </div>
                      <button
                        onClick={() => handleDeleteDiagnostico(diagnostico.idDiagnostico)}
                        className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                      >
                        <Trash2 size={18} />
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>

          <div>
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-xl font-bold text-gray-800 flex items-center gap-2">
                <Pill size={24} className="text-green-600" />
                Recetas Médicas ({recetas.length})
              </h3>
              <button
                onClick={() => setShowRecetaModal(true)}
                className="flex items-center gap-2 bg-gradient-to-r from-green-600 to-green-700 text-white px-4 py-2 rounded-lg hover:from-green-700 hover:to-green-800 transition-all"
              >
                <Plus size={20} />
                Agregar Receta
              </button>
            </div>

            {recetas.length === 0 ? (
              <div className="text-center py-8 bg-gray-50 rounded-lg">
                <Pill className="mx-auto text-gray-400 mb-2" size={48} />
                <p className="text-gray-500">No hay recetas médicas registradas</p>
              </div>
            ) : (
              <div className="space-y-4">
                {recetas.map((receta) => (
                  <div key={receta.idReceta} className="bg-white border-2 rounded-lg p-4">
                    <p className="text-sm font-semibold text-gray-700 mb-3">Indicaciones: {receta.indicaciones}</p>
                    <div className="space-y-2">
                      {receta.detalles?.map((detalle) => (
                        <div key={detalle.idDetalleReceta} className="bg-green-50 rounded p-3">
                          <p className="font-semibold text-green-800">{detalle.medicamento}</p>
                          <p className="text-sm text-gray-600">Dosis: {detalle.dosis} | Frecuencia: {detalle.frecuencia} | Duración: {detalle.duracion}</p>
                        </div>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {showDiagnosticoModal && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white rounded-xl p-8 w-full max-w-md">
              <div className="flex justify-between items-center mb-6">
                <h3 className="text-2xl font-bold text-gray-800">Nuevo Diagnóstico</h3>
                <button onClick={() => setShowDiagnosticoModal(false)} className="p-2 hover:bg-gray-100 rounded-lg">
                  <X size={20} />
                </button>
              </div>

              <form onSubmit={handleSubmitDiagnostico} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Tipo *</label>
                  <select
                    name="tipo"
                    value={formDiagnostico.tipo}
                    onChange={handleDiagnosticoChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-blue-500 focus:outline-none"
                    required
                  >
                    <option value="presuntivo">Presuntivo</option>
                    <option value="definitivo">Definitivo</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Descripción *</label>
                  <textarea
                    name="descripcion"
                    value={formDiagnostico.descripcion}
                    onChange={handleDiagnosticoChange}
                    rows="4"
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-blue-500 focus:outline-none"
                    required
                  ></textarea>
                </div>

                <div className="flex gap-4 pt-4">
                  <button
                    type="submit"
                    className="flex-1 bg-gradient-to-r from-blue-600 to-blue-700 text-white py-3 rounded-lg hover:from-blue-700 hover:to-blue-800 transition-all font-medium"
                  >
                    Registrar
                  </button>
                  <button
                    type="button"
                    onClick={() => setShowDiagnosticoModal(false)}
                    className="flex-1 bg-gray-200 text-gray-700 py-3 rounded-lg hover:bg-gray-300 transition-colors font-medium"
                  >
                    Cancelar
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        {showRecetaModal && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
            <div className="bg-white rounded-xl p-8 w-full max-w-3xl max-h-[90vh] overflow-y-auto">
              <div className="flex justify-between items-center mb-6">
                <h3 className="text-2xl font-bold text-gray-800">Nueva Receta Médica</h3>
                <button onClick={() => setShowRecetaModal(false)} className="p-2 hover:bg-gray-100 rounded-lg">
                  <X size={20} />
                </button>
              </div>

              <form onSubmit={handleSubmitReceta} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Indicaciones Generales *</label>
                  <textarea
                    name="indicaciones"
                    value={formReceta.indicaciones}
                    onChange={handleRecetaChange}
                    rows="3"
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  ></textarea>
                </div>

                <div>
                  <div className="flex justify-between items-center mb-3">
                    <label className="block text-sm font-medium text-gray-700">Medicamentos *</label>
                    <button
                      type="button"
                      onClick={addDetalle}
                      className="flex items-center gap-1 text-green-600 hover:text-green-700 text-sm font-medium"
                    >
                      <Plus size={16} />
                      Agregar Medicamento
                    </button>
                  </div>

                  <div className="space-y-4">
                    {formReceta.detalles.map((detalle, index) => (
                      <div key={index} className="bg-gray-50 rounded-lg p-4 relative">
                        {formReceta.detalles.length > 1 && (
                          <button
                            type="button"
                            onClick={() => removeDetalle(index)}
                            className="absolute top-2 right-2 p-1 text-red-600 hover:bg-red-100 rounded"
                          >
                            <Trash2 size={16} />
                          </button>
                        )}

                        <div className="grid grid-cols-2 gap-3">
                          <div className="col-span-2">
                            <label className="block text-xs font-medium text-gray-700 mb-1">Medicamento *</label>
                            <input
                              type="text"
                              value={detalle.medicamento}
                              onChange={(e) => handleDetalleChange(index, 'medicamento', e.target.value)}
                              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:border-green-500 focus:outline-none text-sm"
                              required
                            />
                          </div>
                          <div>
                            <label className="block text-xs font-medium text-gray-700 mb-1">Dosis *</label>
                            <input
                              type="text"
                              value={detalle.dosis}
                              onChange={(e) => handleDetalleChange(index, 'dosis', e.target.value)}
                              placeholder="Ej: 500mg"
                              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:border-green-500 focus:outline-none text-sm"
                              required
                            />
                          </div>
                          <div>
                            <label className="block text-xs font-medium text-gray-700 mb-1">Frecuencia *</label>
                            <input
                              type="text"
                              value={detalle.frecuencia}
                              onChange={(e) => handleDetalleChange(index, 'frecuencia', e.target.value)}
                              placeholder="Ej: Cada 8 horas"
                              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:border-green-500 focus:outline-none text-sm"
                              required
                            />
                          </div>
                          <div className="col-span-2">
                            <label className="block text-xs font-medium text-gray-700 mb-1">Duración *</label>
                            <input
                              type="text"
                              value={detalle.duracion}
                              onChange={(e) => handleDetalleChange(index, 'duracion', e.target.value)}
                              placeholder="Ej: 7 días"
                              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:border-green-500 focus:outline-none text-sm"
                              required
                            />
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="flex gap-4 pt-4">
                  <button
                    type="submit"
                    className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium"
                  >
                    Registrar Receta
                  </button>
                  <button
                    type="button"
                    onClick={() => setShowRecetaModal(false)}
                    className="flex-1 bg-gray-200 text-gray-700 py-3 rounded-lg hover:bg-gray-300 transition-colors font-medium"
                  >
                    Cancelar
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default DetalleConsulta;