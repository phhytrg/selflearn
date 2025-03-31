import { setupServer } from 'msw/node';
import { handlers } from './mocks/handlers';

// setupWorker is a custom function that returns a new instance of the Service Worker.
// It is used for browser environments.

export const server = setupServer(...handlers);
