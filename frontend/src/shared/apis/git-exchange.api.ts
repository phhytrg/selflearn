import axiosInstance from "./axios-config";

export const gitExchangeApi = {
  async getTrees() {
    return await axiosInstance.get('/gitProjectExchange/trees');
  },

  async getContent(path: string) {
    return await axiosInstance.get(`/gitProjectExchange/clusters/content`, {
      params: {
        path,
      },
    });
  },

  async getAllCluster(){
    return await axiosInstance.get('/gitProjectExchange/clusters/all');
  }
};