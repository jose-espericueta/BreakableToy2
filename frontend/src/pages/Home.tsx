import { useEffect, useContext } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const Home = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { token, setToken } = useContext(AuthContext);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const tokenFromUrl = params.get('access_token');
    const error = params.get('error');

    if (error) {
      alert('Login failed: ' + error);
      return;
    }

    if (tokenFromUrl) {
      setToken(tokenFromUrl);
      navigate('/dashboard');
      return;
    }

    if (token) {
      navigate('/dashboard');
    } else {
      navigate('/login');
    }
  }, [location, setToken, token, navigate]);

  return (
    <div>
      <h2>Home (Login)</h2>
      <p>Waiting for login...</p>
    </div>
  );
};

export default Home;