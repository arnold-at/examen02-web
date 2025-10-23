import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { 
  Users, Calendar, Stethoscope, BedDouble, FileText, Hospital, 
  LogOut, User, Download, FileSpreadsheet 
} from 'lucide-react';
import { useState } from 'react';

const Layout = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [showReports, setShowReports] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const downloadReport = (type, format) => {
    const url = `http://localhost:9090/api/reportes/${type}/${format}`;
    window.open(url, '_blank');
  };

  const menuItems = [
    { path: '/dashboard', icon: Hospital, label: 'Dashboard' },
    { path: '/pacientes', icon: Users, label: 'Pacientes' },
    { path: '/medicos', icon: Stethoscope, label: 'Médicos' },
    { path: '/citas', icon: Calendar, label: 'Citas' },
    { path: '/consultas', icon: FileText, label: 'Consultas' },
    { path: '/hospitalizacion', icon: BedDouble, label: 'Hospitalización' },
  ];

  return (
    <div className="min-h-screen bg-gray-100 flex">
      <aside className="w-64 bg-gradient-to-b from-blue-900 to-blue-800 text-white flex flex-col">
        <div className="p-6 border-b border-blue-700">
          <div className="flex items-center gap-3">
            <Hospital className="w-8 h-8" />
            <div>
              <h1 className="text-xl font-bold">Hospital</h1>
              <p className="text-xs text-blue-300">Sistema de Gestión</p>
            </div>
          </div>
        </div>

        <nav className="flex-1 p-4">
          {menuItems.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className="flex items-center gap-3 px-4 py-3 rounded-lg hover:bg-blue-700 transition-colors mb-2"
            >
              <item.icon className="w-5 h-5" />
              <span>{item.label}</span>
            </Link>
          ))}

          <div className="mt-4 pt-4 border-t border-blue-700">
            <button
              onClick={() => setShowReports(!showReports)}
              className="w-full flex items-center gap-3 px-4 py-3 rounded-lg hover:bg-blue-700 transition-colors mb-2"
            >
              <Download className="w-5 h-5" />
              <span>Reportes</span>
            </button>
            
            {showReports && (
              <div className="ml-4 space-y-1">
                <button
                  onClick={() => downloadReport('pacientes', 'pdf')}
                  className="w-full flex items-center gap-2 px-4 py-2 text-sm rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <FileText className="w-4 h-4" />
                  Pacientes PDF
                </button>
                <button
                  onClick={() => downloadReport('pacientes', 'excel')}
                  className="w-full flex items-center gap-2 px-4 py-2 text-sm rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <FileSpreadsheet className="w-4 h-4" />
                  Pacientes Excel
                </button>
                <button
                  onClick={() => downloadReport('citas', 'pdf')}
                  className="w-full flex items-center gap-2 px-4 py-2 text-sm rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <FileText className="w-4 h-4" />
                  Citas PDF
                </button>
                <button
                  onClick={() => downloadReport('citas', 'excel')}
                  className="w-full flex items-center gap-2 px-4 py-2 text-sm rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <FileSpreadsheet className="w-4 h-4" />
                  Citas Excel
                </button>
              </div>
            )}
          </div>
        </nav>

        <div className="p-4 border-t border-blue-700">
          <div className="flex items-center gap-3 px-4 py-3 bg-blue-700 rounded-lg mb-2">
            <User className="w-5 h-5" />
            <div className="flex-1 min-w-0">
              <p className="text-sm font-medium truncate">{user?.nombres}</p>
              <p className="text-xs text-blue-300 truncate">{user?.rol}</p>
            </div>
          </div>
          <button
            onClick={handleLogout}
            className="w-full flex items-center gap-3 px-4 py-3 rounded-lg hover:bg-red-600 transition-colors"
          >
            <LogOut className="w-5 h-5" />
            <span>Cerrar Sesión</span>
          </button>
        </div>
      </aside>

      <main className="flex-1 p-8 overflow-auto">
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;