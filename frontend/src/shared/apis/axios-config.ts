import axios from 'axios';
import { HOST_API } from '../constants';

axios.defaults.baseURL = HOST_API;

const axiosInstance = axios.create({
  baseURL: `${HOST_API}/api/v1`,
  headers: {
    'Content-type': 'application/json',
  },
});

export default axiosInstance;
