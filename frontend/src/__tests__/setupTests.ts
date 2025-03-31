import '@testing-library/jest-dom';
import { afterAll, afterEach, beforeAll } from 'vitest';
import { server } from './msw/worker';
import { cleanup } from '@testing-library/react';

// declare module 'vitest' {
//   interface Assertion<T = any>
//     extends jest.Matchers<void, T>,
//       TestingLibraryMatchers<T, void> {}
// }

// expect.extend(matchers);

// Start worker before all tests
const { getComputedStyle } = window;
window.getComputedStyle = (elt) => getComputedStyle(elt);

beforeAll(() => {
  server.listen();
});

//  Close worker after all tests
afterAll(() => {
  server.close();
});

// Reset handlers after each test `important for test isolation`
afterEach(() => {
  server.resetHandlers();
  cleanup();
});
