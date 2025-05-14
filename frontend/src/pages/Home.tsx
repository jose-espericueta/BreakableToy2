import { useEffect, useContext } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const Home = () => {
  const navigate = useNavigate();
  const { setToken } = useContext(AuthContext);
  const location = useLocation();

  useEffect(() => {
    const hash = new URLSearchParams(location.search);
    const error = hash.get('error');
    const accessToken = hash.get('access_token');

    if (error) {
        alert('Login failed: ' + error);
        return;
    }

    if (accessToken) {
      setToken(accessToken);
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
