import axiosInstance from "./axios-config";

export const clusterApi = {
  getAll: async () => {
    return await axiosInstance.get('/clusters');
  },
};