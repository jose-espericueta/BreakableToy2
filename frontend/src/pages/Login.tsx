import React from 'react';

const Login: React.FC = () => {
  const handleLogin = () => {
    window.location.assign('http://127.0.0.1:8080/auth/spotify');
  };

  return (
    <div className="login-container">
      <h1 className="login-title">Login</h1>
      <button className="login-button" onClick={handleLogin}>
        Login with Spotify
      </button>
    </div>
  );
};

export default Login;