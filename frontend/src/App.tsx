import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
// import Dashboard from './pages/Dashboard';
import Home from './pages/Home';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        {/*<Route path="/dashboard" element={<Dashboard />} />*/}
      </Routes>
    </Router>
  );
}

export default App;