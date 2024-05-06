import axiosInstance from "./axios-config";

export const gitExchangeApi = {
  async getTrees() {
    return await axiosInstance.get('/gitProjectExchange/trees');
  },

  async getContent(path: string) {
    return await axiosInstance.get(`/gitProjectExchange/content`, {
      params: {
        path,
      },
    });
  }
};