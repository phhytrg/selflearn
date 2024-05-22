import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Input, Modal } from 'antd';
import { useAuth } from './hooks/useAuth';
import { AxiosError } from 'axios';

export const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { login, user } = useAuth();

  useEffect(() => {
    if (user) {
      navigate('/', { replace: true });
    }
  }, [user, navigate]);

  const handleLogin = async (e: any) => {
    e.preventDefault();
    try {
      await login({
        username,
        password,
      });
    } catch (e: any) {
      Modal.error({ title: 'Error', content: JSON.stringify(e.response.data) });
      return;
    }
    navigate('/', { replace: true });
  };

  return (
    <div className="flex flex-col gap-4">
      <div>
        <label htmlFor="username">Username:</label>
        <Input
          id="username"
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <Input
          id="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <Button type="primary" onClick={handleLogin}>
        Log In
      </Button>
      <Button
        type="default"
        onClick={() => {
          navigate('/signup', { replace: true });
        }}
      >
        Sign Up
      </Button>
    </div>
  );
};
