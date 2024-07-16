import { Button, Input, Modal, Spin } from 'antd';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './hooks/useAuth';
import { authApi } from '@/shared/apis';

export const SignUpPage = () => {
  const { user } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (user) {
      navigate('/', { replace: true });
    }
  }, [user, navigate]);

  const handleSignUp = async (e: any) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const res = await authApi.register(email, password, confirmPassword);
    } catch (e: any) {
      Modal.error({
        title: 'Error',
        content: (
          <p data-testid="error-modal">
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
    setIsLoading(false);
    navigate('/login', { replace: true });
  };

  return (
    <div className="flex flex-col gap-4">
      <h1>Sign Up</h1>
      <Input
        type="text"
        placeholder="Email"
        value={email}
        aria-label="email"
        onChange={(e) => {
          setEmail(e.target.value);
        }}
      />
      <Input
        type="password"
        aria-label="password"
        placeholder="Password"
        value={password}
        onChange={(e) => {
          setPassword(e.target.value);
        }}
      />
      <Input
        type="password"
        placeholder="Confirm Password"
        aria-label="confirmPassword"
        value={confirmPassword}
        onChange={(e) => {
          setConfirmPassword(e.target.value);
        }}
      />
      <Button
        type="primary"
        onClick={handleSignUp}
        aria-label="signUp"
        disabled={isLoading}
      >
        {isLoading ? <Spin /> : `Sign Up`}
      </Button>
    </div>
  );
};
