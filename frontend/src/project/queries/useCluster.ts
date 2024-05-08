import { clusterApi, gitExchangeApi } from '@/shared/apis';
import { useQuery } from 'react-query';

export interface Cluster {
  readonly id: string;
  readonly name: string;
  readonly provisioningState: string;
  readonly powerState: string;
  readonly nodeCount: number;
  readonly mode: string;
  readonly nodeImageVersion: string;
  readonly k8sVersion: string;
  readonly nodeSize: string;
  readonly os: string;
}

const QUERY_KEY = 'clusters';

export const useGetClusters = (
  params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  },
  fetchFromDb?: boolean,
) => {
  return useQuery<Cluster[], Error>([QUERY_KEY, {params, fetchFromDb}], {
    queryFn: async () => {
      if (!fetchFromDb) {
        return (await gitExchangeApi.getAllCluster()).data;
      }
      return (await clusterApi.getClusters(params)).data;
    },
    refetchOnWindowFocus: false,
  });
};
