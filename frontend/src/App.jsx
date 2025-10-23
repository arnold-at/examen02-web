import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './pages/Login';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import Pacientes from './pages/Pacientes';
import Medicos from './pages/Medicos';
import Citas from './pages/Citas';
import Consultas from './pages/Consultas';
import Hospitalizacion from './pages/Hospitalizacion';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={
            <ProtectedRoute>
              <Layout />
            </ProtectedRoute>
          }>
            <Route index element={<Navigate to="/dashboard" replace />} />
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="pacientes" element={<Pacientes />} />
            <Route path="medicos" element={<Medicos />} />
            <Route path="citas" element={<Citas />} />
            <Route path="consultas" element={<Consultas />} />
            <Route path="hospitalizacion" element={<Hospitalizacion />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;