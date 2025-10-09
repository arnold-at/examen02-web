import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import Pacientes from './pages/Pacientes';
import Citas from './pages/Citas';
import Medicos from './pages/Medicos';
import Consultas from './pages/Consultas';
import Hospitalizacion from './pages/Hospitalizacion';

function App() {
  return (
    // Contenedor principal para la prueba de estilos
    <div>
      {/* BARRA DE PRUEBA: Si esta barra se ve GRANDE, ROJA y CENTRADA,
          significa que Tailwind CSS está funcionando correctamente. */}
      {/* El Router de la aplicación */}
      <Router>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<Dashboard />} />
            <Route path="pacientes" element={<Pacientes />} />
            <Route path="citas" element={<Citas />} />
            <Route path="medicos" element={<Medicos />} />
            <Route path="consultas" element={<Consultas />} />
            <Route path="hospitalizacion" element={<Hospitalizacion />} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;