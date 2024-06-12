import { Button, Input, Modal } from 'antd';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './hooks/useAuth';
import { authApi } from '@/shared/apis';
import { AxiosError } from 'axios';

export const SignUpPage = () => {
  const { user } = useAuth();
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
    try {
      await authApi.register(email, password, confirmPassword);
    } catch (e: any) {
      console.log(e);
      Modal.error({
        title: 'Error',
        content: (
          <p>
            {Object.entries(e.response.data).map(([k, v]: any) => {
              // Explicitly type 'v' as string
              return (
                <p key={k}>
                  {k}: {v}
                </p>
              );
            })}
          </p>
        ),
      });
      return;
    }
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
