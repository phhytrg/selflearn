import { subscriptionApi } from "@/shared/apis";
import { useQuery } from "react-query";

const QUERY_KEY = ['subscriptions'];

export const useGetAllSubscriptions = () => {
  return useQuery({
    queryKey: QUERY_KEY,
    queryFn: async () => {
      const response = await subscriptionApi.getAll();
      return response.data;
    },
    refetchOnWindowFocus: false,
  });
};
