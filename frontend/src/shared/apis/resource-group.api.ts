import axiosInstance from "./axios-config";

export const resourceGroupApi = {
  getAll: async () => {
    return await axiosInstance.get('/resource-groups');
  },
};