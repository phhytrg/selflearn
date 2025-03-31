import { describe, expect, it, test, vi } from 'vitest';
import { server } from '../msw/worker';
import { handlers as subscriptionHandlers } from '../msw/mocks/subscription.handlers';
import { http, HttpResponse } from 'msw';
import axios from 'axios';

server.use(...subscriptionHandlers);

describe('Select Tabs', () => {
  test('use get subscriptions', async () => {
    server.use(
      http.get(
        'http://localhost:8080/api/v1/subscriptions',
        async ({ request }) => {
          const bearerToken = request.headers.get('Authorization');
          if (bearerToken !== 'abc') {
            return new HttpResponse(null, {
              status: 403,
              statusText: 'Forbidden',
            });
          }
        },
      ),
    );

    const axiosInstance = axios.create({
      baseURL: 'http://localhost:8080/api/v1',
      headers: {
        Authorization: 'def',
      },
    });

    axiosInstance.get('/subscriptions').catch((error) => {
      expect(error.response.status).toBe(403);
    });

    const res = await axiosInstance.get('/subscriptions', {
      transformRequest: (data, headers) => {
        headers.Authorization = 'abc';
        return data;
      },
    });

    console.log(res);

    //expect success fully here
    expect(res.status).toBe(200);
  });
});
