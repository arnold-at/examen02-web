import { Users, Calendar, Stethoscope, BedDouble, UserPlus, FileText } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const navigate = useNavigate();

  const stats = [
    { label: 'Pacientes Activos', value: '1,234', icon: Users, color: 'blue' },
    { label: 'Citas Hoy', value: '48', icon: Calendar, color: 'green' },
    { label: 'Médicos Disponibles', value: '25', icon: Stethoscope, color: 'purple' },
    { label: 'Hospitalizados', value: '89', icon: BedDouble, color: 'red' },
  ];

  const colorClasses = {
    blue: 'from-blue-500 to-blue-600',
    green: 'from-green-500 to-green-600',
    purple: 'from-purple-500 to-purple-600',
    red: 'from-red-500 to-red-600',
  };

  return (
    <div className="space-y-6">
      {/* Banner de Bienvenida */}
      <div className="bg-gradient-to-r from-blue-600 to-green-500 rounded-2xl p-8 text-white shadow-xl">
        <h1 className="text-4xl font-bold mb-2">
          Bienvenido al Sistema de Gestión Hospitalaria
        </h1>
        <p className="text-blue-50 text-lg">
          Administra todos los procesos de tu hospital de manera integrada y eficiente
        </p>
      </div>

      {/* Estadísticas */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat, idx) => {
          const Icon = stat.icon;
          return (
            <div
              key={idx}
              className="bg-white rounded-xl p-6 shadow-md hover:shadow-xl transition-shadow cursor-pointer"
            >
              <div
                className={`w-12 h-12 bg-gradient-to-br ${colorClasses[stat.color]} rounded-lg flex items-center justify-center mb-4`}
              >
                <Icon className="w-6 h-6 text-white" />
              </div>
              <h3 className="text-gray-600 text-sm font-medium mb-1">{stat.label}</h3>
              <p className="text-3xl font-bold text-gray-800">{stat.value}</p>
            </div>
          );
        })}
      </div>

      {/* Accesos Rápidos y Actividad */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Accesos Rápidos */}
        <div className="bg-white rounded-xl p-6 shadow-md">
          <h3 className="text-lg font-semibold text-gray-800 mb-4">Accesos Rápidos</h3>
          <div className="space-y-3">
            <button
              onClick={() => navigate('/pacientes')}
              className="w-full flex items-center gap-3 p-3 rounded-lg border-2 border-gray-200 hover:border-blue-500 hover:bg-blue-50 transition-all"
            >
              <UserPlus className="w-5 h-5 text-blue-600" />
              <span className="font-medium text-gray-700">Registrar Nuevo Paciente</span>
            </button>
            <button
              onClick={() => navigate('/citas')}
              className="w-full flex items-center gap-3 p-3 rounded-lg border-2 border-gray-200 hover:border-blue-500 hover:bg-blue-50 transition-all"
            >
              <Calendar className="w-5 h-5 text-blue-600" />
              <span className="font-medium text-gray-700">Agendar Cita</span>
            </button>
            <button
              onClick={() => navigate('/consultas')}
              className="w-full flex items-center gap-3 p-3 rounded-lg border-2 border-gray-200 hover:border-blue-500 hover:bg-blue-50 transition-all"
            >
              <FileText className="w-5 h-5 text-blue-600" />
              <span className="font-medium text-gray-700">Ver Consultas del Día</span>
            </button>
          </div>
        </div>

        {/* Actividad Reciente */}
        <div className="bg-white rounded-xl p-6 shadow-md">
          <h3 className="text-lg font-semibold text-gray-800 mb-4">Actividad Reciente</h3>
          <div className="space-y-3">
            {[
              'Paciente Juan Pérez registrado',
              'Cita programada con Dr. García',
              'Alta médica de María López',
              'Nueva consulta de emergencia',
            ].map((activity, idx) => (
              <div key={idx} className="flex items-start gap-3 p-3 rounded-lg bg-gray-50">
                <div className="w-2 h-2 bg-green-500 rounded-full mt-2"></div>
                <p className="text-sm text-gray-700">{activity}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;