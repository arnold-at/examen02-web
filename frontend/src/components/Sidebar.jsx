import { Link, useLocation } from 'react-router-dom';
import { Home, Users, Calendar, Stethoscope, FileText, BedDouble, Activity } from 'lucide-react';

const Sidebar = ({ isOpen }) => {
  const location = useLocation();

  const menuItems = [
    { path: '/', label: 'Dashboard', icon: Home },
    { path: '/pacientes', label: 'Pacientes', icon: Users },
    { path: '/citas', label: 'Citas Médicas', icon: Calendar },
    { path: '/medicos', label: 'Médicos', icon: Stethoscope },
    { path: '/consultas', label: 'Consultas', icon: FileText },
    { path: '/hospitalizacion', label: 'Hospitalización', icon: BedDouble },
  ];

  return (
    <aside
      className={`${
        isOpen ? 'w-64' : 'w-0'
      } bg-gradient-to-b from-blue-600 to-blue-800 text-white transition-all duration-300 overflow-hidden`}
    >
      <div className="p-6">
        <div className="flex items-center gap-3 mb-8">
          <Activity className="w-8 h-8" />
          <h1 className="text-xl font-bold">Hospital System</h1>
        </div>

        <nav className="space-y-2">
          {menuItems.map((item) => {
            const Icon = item.icon;
            const isActive = location.pathname === item.path;
            
            return (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-all ${
                  isActive
                    ? 'bg-white text-blue-600 shadow-lg'
                    : 'hover:bg-blue-700 text-blue-100'
                }`}
              >
                <Icon className="w-5 h-5" />
                <span className="font-medium">{item.label}</span>
              </Link>
            );
          })}
        </nav>
      </div>
    </aside>
  );
};

export default Sidebar;