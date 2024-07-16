import { faker } from '@faker-js/faker';
import { DefaultBodyType, http, HttpResponse } from 'msw';

export const handlers = [
  http.get('http://localhost:8080/api/v1/subscriptions', async () => {
    return HttpResponse.json([
      {
        name: faker.animal.bird(),
        id: faker.string.uuid(),
        sha: faker.string.binary({
          length: 40,
        }),
      },
      {
        name: faker.animal.bird(),
        id: faker.string.uuid(),
        sha: faker.string.binary({
          length: 40,
        }),
      },
      {
        name: faker.animal.bird(),
        id: faker.string.uuid(),
        sha: faker.string.binary({
          length: 40,
        }),
      },
    ]);
  }),
];

type GetSubscriptionParams = {
  subscriptionId: string;
};

type GetSubscriptionResponse = {
  name: string;
  id: string;
  sha: string;
};

export const handlers2 = [
  http.get<GetSubscriptionParams, DefaultBodyType, GetSubscriptionResponse[]>(
    'http://localhost:8080/api/v1/subscriptions',
    async () => {
      return HttpResponse.json([
        {
          name: faker.animal.bird(),
          id: faker.string.uuid(),
          sha: faker.string.binary({
            length: 40,
          }),
        },
        {
          name: faker.animal.bird(),
          id: faker.string.uuid(),
          sha: faker.string.binary({
            length: 40,
          }),
        },
        {
          name: faker.animal.bird(),
          id: faker.string.uuid(),
          sha: faker.string.binary({
            length: 40,
          }),
        },
      ]);
    },
  ),
];
