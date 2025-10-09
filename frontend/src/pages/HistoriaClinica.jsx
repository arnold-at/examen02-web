import { useState, useEffect } from 'react';
import { Plus, Trash2, X, FileText, AlertCircle } from 'lucide-react';
import { historiaClinicaService } from '../services/historiaClinicaService';
import { antecedenteMedicoService } from '../services/antecedenteMedicoService';

const HistoriaClinica = ({ paciente, onClose }) => {
  const [historia, setHistoria] = useState(null);
  const [antecedentes, setAntecedentes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    tipo: 'alergias',
    descripcion: ''
  });

  useEffect(() => {
    loadHistoria();
  }, [paciente]);

  const loadHistoria = async () => {
    try {
      setLoading(true);
      if (paciente.historiaClinica) {
        const historiaData = await historiaClinicaService.getById(paciente.historiaClinica.idHistoria);
        setHistoria(historiaData);
        
        const antecedentesData = await antecedenteMedicoService.getByHistoria(historiaData.idHistoria);
        setAntecedentes(antecedentesData);
      } else {
        setHistoria(null);
        setAntecedentes([]);
      }
    } catch (error) {
      console.error('Error al cargar historia:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCrearHistoria = async () => {
    if (window.confirm('¿Crear historia clínica para este paciente?')) {
      try {
        const nuevaHistoria = {
          paciente: { idPaciente: paciente.idPaciente },
          fechaApertura: new Date().toISOString().split('T')[0],
          observaciones: 'Historia clínica creada'
        };
        
        const created = await historiaClinicaService.create(nuevaHistoria);
        setHistoria(created);
        alert('Historia clínica creada exitosamente');
      } catch (error) {
        console.error('Error al crear historia:', error);
        alert('Error al crear historia clínica');
      }
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      const antecedente = {
        historiaClinica: { idHistoria: historia.idHistoria },
        tipo: formData.tipo,
        descripcion: formData.descripcion
      };
      
      await antecedenteMedicoService.create(antecedente);
      alert('Antecedente registrado exitosamente');
      setShowModal(false);
      resetForm();
      loadHistoria();
    } catch (error) {
      console.error('Error al guardar antecedente:', error);
      alert('Error al guardar el antecedente');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Eliminar este antecedente?')) {
      try {
        await antecedenteMedicoService.delete(id);
        alert('Antecedente eliminado');
        loadHistoria();
      } catch (error) {
        console.error('Error al eliminar:', error);
        alert('Error al eliminar antecedente');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      tipo: 'alergias',
      descripcion: ''
    });
  };

  const getTipoColor = (tipo) => {
    const colors = {
      alergias: 'bg-red-100 text-red-700',
      'enfermedades previas': 'bg-orange-100 text-orange-700',
      cirugías: 'bg-blue-100 text-blue-700'
    };
    return colors[tipo] || 'bg-gray-100 text-gray-700';
  };

  if (loading) {
    return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div className="bg-white rounded-xl p-8">
          <div className="text-xl text-gray-600">Cargando historia clínica...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-xl w-full max-w-4xl max-h-[90vh] overflow-y-auto">
        <div className="sticky top-0 bg-white border-b border-gray-200 p-6 flex justify-between items-center">
          <div className="flex items-center gap-3">
            <div className="w-12 h-12 bg-gradient-to-br from-green-500 to-teal-600 rounded-lg flex items-center justify-center">
              <FileText className="text-white" size={24} />
            </div>
            <div>
              <h2 className="text-2xl font-bold text-gray-800">Historia Clínica</h2>
              <p className="text-sm text-gray-600">
                {paciente.nombres} {paciente.apellidos} - DNI: {paciente.dni}
              </p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
          >
            <X size={24} />
          </button>
        </div>

        <div className="p-6">
          {!historia ? (
            <div className="text-center py-12">
              <AlertCircle className="mx-auto text-yellow-500 mb-4" size={64} />
              <h3 className="text-xl font-semibold text-gray-800 mb-2">
                No hay historia clínica
              </h3>
              <p className="text-gray-600 mb-6">
                Este paciente aún no tiene una historia clínica registrada
              </p>
              <button
                onClick={handleCrearHistoria}
                className="bg-gradient-to-r from-green-600 to-green-700 text-white px-6 py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium"
              >
                Crear Historia Clínica
              </button>
            </div>
          ) : (
            <div className="space-y-6">
              <div className="bg-gray-50 rounded-lg p-4">
                <p className="text-sm text-gray-600">
                  <span className="font-semibold">Fecha de Apertura:</span> {historia.fechaApertura}
                </p>
                {historia.observaciones && (
                  <p className="text-sm text-gray-600 mt-2">
                    <span className="font-semibold">Observaciones:</span> {historia.observaciones}
                  </p>
                )}
              </div>

              <div>
                <div className="flex justify-between items-center mb-4">
                  <h3 className="text-xl font-bold text-gray-800">Antecedentes Médicos</h3>
                  <button
                    onClick={() => setShowModal(true)}
                    className="flex items-center gap-2 bg-gradient-to-r from-green-600 to-green-700 text-white px-4 py-2 rounded-lg hover:from-green-700 hover:to-green-800 transition-all"
                  >
                    <Plus size={20} />
                    Agregar Antecedente
                  </button>
                </div>

                {antecedentes.length === 0 ? (
                  <div className="text-center py-8 bg-gray-50 rounded-lg">
                    <p className="text-gray-500">No hay antecedentes registrados</p>
                  </div>
                ) : (
                  <div className="space-y-3">
                    {antecedentes.map((antecedente) => (
                      <div
                        key={antecedente.idAntecedente}
                        className="bg-white border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow"
                      >
                        <div className="flex justify-between items-start">
                          <div className="flex-1">
                            <span className={`inline-block px-3 py-1 rounded-full text-xs font-medium mb-2 ${getTipoColor(antecedente.tipo)}`}>
                              {antecedente.tipo}
                            </span>
                            <p className="text-gray-700">{antecedente.descripcion}</p>
                          </div>
                          <button
                            onClick={() => handleDelete(antecedente.idAntecedente)}
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
            </div>
          )}
        </div>

        {showModal && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white rounded-xl p-8 w-full max-w-md">
              <div className="flex justify-between items-center mb-6">
                <h3 className="text-2xl font-bold text-gray-800">Nuevo Antecedente</h3>
                <button
                  onClick={() => setShowModal(false)}
                  className="p-2 hover:bg-gray-100 rounded-lg"
                >
                  <X size={20} />
                </button>
              </div>

              <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Tipo *
                  </label>
                  <select
                    name="tipo"
                    value={formData.tipo}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  >
                    <option value="alergias">Alergias</option>
                    <option value="enfermedades previas">Enfermedades Previas</option>
                    <option value="cirugías">Cirugías</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Descripción *
                  </label>
                  <textarea
                    name="descripcion"
                    value={formData.descripcion}
                    onChange={handleInputChange}
                    rows="4"
                    className="w-full px-4 py-2 border-2 border-gray-200 rounded-lg focus:border-green-500 focus:outline-none"
                    required
                  ></textarea>
                </div>

                <div className="flex gap-4 pt-4">
                  <button
                    type="submit"
                    className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white py-3 rounded-lg hover:from-green-700 hover:to-green-800 transition-all font-medium"
                  >
                    Registrar
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
      </div>
    </div>
  );
};

export default HistoriaClinica;