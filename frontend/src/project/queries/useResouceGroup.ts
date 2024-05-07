import { resourceGroupApi } from '@/shared/apis';
import { useQuery } from 'react-query';

const QUERY_KEY = ['resourceGroups'];

export const useGetAllResourceGroups = () => {
  return useQuery({
    queryKey: QUERY_KEY,
    queryFn: async () => {
      const response = await resourceGroupApi.getAll();
      return response.data;
    },
    refetchOnWindowFocus: false,
  });
};
