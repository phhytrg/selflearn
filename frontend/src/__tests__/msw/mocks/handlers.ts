import { handlers as authHandlers } from './auth.handlers';
import { handlers as subscriptionHandlers } from './subscription.handlers';

export const handlers = [...authHandlers, ...subscriptionHandlers];
