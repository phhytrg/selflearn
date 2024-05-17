import { gitExchangeApi } from '@/shared/apis';
import { useQuery } from 'react-query';
import { Tree } from '../interfaces/Tree';

const QUERY_KEY = ['tree'];

export const useGetTree = () => {
  return useQuery<Tree, Error>({
    queryKey: QUERY_KEY,
    queryFn: async () => {
      const response = await gitExchangeApi.getTrees();
      return response.data;
    },
    refetchOnWindowFocus: false,
  });
};
