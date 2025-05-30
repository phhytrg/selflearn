import axiosInstance from './axios-config';

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

  getClusters: async (params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  }) => {
    return await axiosInstance.get('/gitProjectExchange/clusters', {
      params,
    });
  },

  getResourceGroups: async (subscriptionName?: string) => {
    return await axiosInstance.get('/gitProjectExchange/resource-groups', {
      params: {
        subscriptionName,
      },
    });
  },

  getNodePools: async (params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  }) => {
    return await axiosInstance.get('/gitProjectExchange/node-pools', {
      params,
    });
  },

  getSubscriptions: async () => {
    return await axiosInstance.get('/gitProjectExchange/subscriptions');
  },

  syncDatabaseWithGithub: async () => {
    return await axiosInstance.put('/gitProjectExchange/sync-db-with-git');
  },

  syncGithubWithDatabase: async () => {
    return await axiosInstance.post('/gitProjectExchange/sync-git-with-db');
  },
};
