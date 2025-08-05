// ui/src/pages/LoginPage.js

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  const [loginUsername, setLoginUsername] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      credentials: 'include', // This enables cookies/session
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({
        username: loginUsername,
        password: loginPassword,
      }),
    });

    if (response.ok) {
      // Redirect after successful login
      navigate('/dashboard'); // update to your actual dashboard route
    } else {
      setError('Invalid username or password.');
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Login</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          type="text"
          placeholder="Username"
          value={loginUsername}
          onChange={(e) => setLoginUsername(e.target.value)}
          required
          style={styles.input}
        />
        <input
          type="password"
          placeholder="Password"
          value={loginPassword}
          onChange={(e) => setLoginPassword(e.target.value)}
          required
          style={styles.input}
        />
        <button type="submit" style={styles.button}>Login</button>
        {error && <p style={styles.error}>{error}</p>}
      </form>
    </div>
  );
}

const styles = {
  container: {
    maxWidth: '400px',
    margin: 'auto',
    marginTop: '100px',
    padding: '20px',
    borderRadius: '10px',
    backgroundColor: '#f5f5f5',
    boxShadow: '0 0 10px rgba(0,0,0,0.1)'
  },
  title: {
    textAlign: 'center',
    marginBottom: '20px'
  },
  form: {
    display: 'flex',
    flexDirection: 'column'
  },
  input: {
    marginBottom: '15px',
    padding: '10px',
    fontSize: '16px'
  },
  button: {
    padding: '10px',
    fontSize: '16px',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    cursor: 'pointer'
  },
  error: {
    marginTop: '10px',
    color: 'red',
    textAlign: 'center'
  }
};

export default LoginPage;
