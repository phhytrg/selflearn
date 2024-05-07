import { gitExchangeApi } from '@/shared/apis';
import { useQuery } from 'react-query';

export interface Cluster {
  readonly name: string,
  readonly provisioningState: string,
  readonly powerState: string,
  readonly nodeCount: number,
  readonly mode: string,
  readonly nodeImageVersion: string,
  readonly k8sVersion: string,
  readonly nodeSize: string,
  readonly os: string,
}

const QUERY_KEY = ['clusters'];

export const useGetAllClusters = () => {
  return useQuery<Cluster[], Error>({
    queryKey: QUERY_KEY,
    queryFn: async () => {
      const response = await gitExchangeApi.getAllCluster();
      return response.data;
    },
    refetchOnWindowFocus: false,
  });
};
