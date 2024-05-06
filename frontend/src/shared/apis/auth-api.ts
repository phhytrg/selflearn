import axios from 'axios';

export const authApi = {
  login: async (email: string, password: string) => {
    return await axios.post('http://localhost:8080/auth/login', {
      email,
      password,
    });
  },
  register: async (
    email: string,
    password: string,
    confirmPassword: string,
  ) => {
    return await axios.post('http://localhost:8080/auth/register', {
      email,
      password,
      confirmPassword,
    });
  },
};
