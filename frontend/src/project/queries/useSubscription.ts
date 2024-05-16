import { gitExchangeApi, subscriptionApi } from '@/shared/apis';
import { useQuery } from 'react-query';

const QUERY_KEY = 'subscriptions';

export const useGetAllSubscriptions = (fetchFromDb?: boolean) => {
  return useQuery([QUERY_KEY, fetchFromDb], {
    queryFn: async () => {
      if (!fetchFromDb) {
        return (await gitExchangeApi.getSubscriptions()).data;
      }
      return (await subscriptionApi.getAll()).data;
    },
    refetchOnWindowFocus: false,
  });
};
