import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Input, Modal, Spin } from 'antd';
import { useAuth } from './hooks/useAuth';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { login, user } = useAuth();
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (user) {
      navigate('/', { replace: true });
    }
  }, [user, navigate]);

  const handleLogin = async (e: any) => {
    e.preventDefault();
    try {
      setIsLoading(true);
      const res = await login({
        username: email,
        password,
      });
      console.log(res);
    } catch (e: any) {
      Modal.error({
        title: 'Error',
        content: (
          <div data-testid="error-modal">
            {JSON.stringify(e.response?.data) || e.message}
          </div>
        ),
      });
      return;
    } finally {
      setIsLoading(false);
    }
    navigate('/', { replace: true });
  };

  return (
    <div className="flex flex-col gap-4">
      <div>
        <label htmlFor="email">Email:</label>
        <Input
          id="email"
          type="text"
          name="email"
          aria-label="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <Input
          id="password"
          type="password"
          name="password"
          aria-label="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <Button
        type="primary"
        onClick={handleLogin}
        name="signIn"
        aria-label="signIn"
        disabled={isLoading}
      >
        {isLoading ? <Spin /> : `Log In`}
      </Button>
      <Button
        type="default"
        name="signUp"
        aria-label="signUp"
        onClick={() => {
          navigate('/signup', { replace: true });
        }}
      >
        Sign Up
      </Button>
    </div>
  );
};

export default LoginPage;
