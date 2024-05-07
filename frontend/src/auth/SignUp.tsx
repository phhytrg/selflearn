
import { Button, Input } from 'antd';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './hooks/useAuth';
import { authApi } from '@/shared/apis';

export const SignUpPage = () => {
  const {user} = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (user) {
      navigate('/', { replace: true });
    }
  }, [user, navigate]);

  const handleSignUp = async (e: any) => {
    e.preventDefault();
    authApi.register(email, password, confirmPassword);
    navigate('/login', { replace: true });
  };

  return (
    <div className="flex flex-col gap-4">
      <h1>Sign Up</h1>
      <Input
        type="text"
        placeholder="Email"
        value={email}
        onChange={(e) => {
          setEmail(e.target.value);
        }}
      />
      <Input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => {
          setPassword(e.target.value);
        }}
      />
      <Input
        type="password"
        placeholder="Confirm Password"
        value={confirmPassword}
        onChange={(e) => {
          setConfirmPassword(e.target.value);
        }}
      />
      <Button type="primary" onClick={handleSignUp}>
        Sign Up
      </Button>
    </div>
  );
};
