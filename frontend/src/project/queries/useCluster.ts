import { clusterApi, gitExchangeApi } from '@/shared/apis';
import { useQuery } from 'react-query';

const QUERY_KEY = 'clusters';

export const useGetClusters = (
  params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  },
  fetchFromDb?: boolean,
) => {
  return useQuery<{ id?: string; name: string }[], Error>(
    [QUERY_KEY, { params, fetchFromDb }],
    {
      queryFn: async () => {
        if (!fetchFromDb) {
          return (await gitExchangeApi.getClusters(params)).data;
        }
        return (await clusterApi.getClusters(params)).data;
      },
      refetchOnWindowFocus: false,
    },
  );
};
