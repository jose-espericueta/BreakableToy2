import React from 'react';

const Login: React.FC = () => {
  const handleLogin = () => {
    window.location.assign('http://127.0.0.1:8080/auth/spotify');
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '4rem' }}>
      <h1>Login </h1>
      <button onClick={handleLogin}>Login with Spotify</button>
    </div>
  );
};

export default Login;