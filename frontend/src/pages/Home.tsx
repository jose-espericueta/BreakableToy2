import { useEffect, useContext } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const Home = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { setToken } = useContext(AuthContext);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const token = params.get('access_token');
    const error = params.get('error');

    if (error) {
      alert('Login failed: ' + error);
      return;
    }

    if (token) {
      setToken(token);
      navigate('/dashboard');
    }
  }, [location, setToken, navigate]);

  return (
    <div>
      <h2>Home (Login)</h2>
      <p>Waiting for login...</p>
    </div>
  );
};

export default Home;