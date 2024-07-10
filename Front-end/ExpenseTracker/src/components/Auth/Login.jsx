import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../../axiosConfig';


function Login({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/new/login', { email, password });
      const token = response.data;
      localStorage.setItem('jwt', token);
      console.log(token);
      localStorage.setItem('jwt', response.data.body);  
      onLogin();
      navigate('/dashboard'); 
    } catch (error) {
      console.error('Login failed', error);
     
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <label >Email</label>
      <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
      <label >Password</label>
      <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
      <button type="submit">Login</button>
    </form>
  );
}

export default Login;