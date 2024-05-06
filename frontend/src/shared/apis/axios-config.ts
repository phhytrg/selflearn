import axios from 'axios';
import { HOST_API } from '../constants';

axios.defaults.baseURL = HOST_API;

const axiosInstance = axios.create({
  baseURL: `${HOST_API}/api/v1`,
  headers: {
    'Content-type': 'application/json',
  },
});

axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  config.headers.Authorization = token ? `Bearer ${token}` : undefined;
  return config;
});

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      const refreshToken = localStorage.getItem('refreshToken');
      if (!refreshToken) {
        return Promise.reject(error);
      }
      return axios
        .post(`${HOST_API}/api/v1/auth/refresh`, {
          refresh: refreshToken,
        })
        .then((response) => {
          localStorage.setItem('accessToken', response.data.access);
          error.config.headers['Authorization'] =
            `Bearer ${response.data.access}`;
          return axios(error.response.config);
        })
        .catch(() => {
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
        });
    }
    return Promise.reject(error);
  },
);

export default axiosInstance;
