/// <reference types="vitest" />
/// <reference types="vite/client" />
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import path from 'path';
import tailwindcss from 'tailwindcss';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@components': `${path.resolve(__dirname, './src')}/shared/components`,
      '@constants': `${path.resolve(__dirname, './src')}/shared/constants`,
      '@apis': `${path.resolve(__dirname, './src')}/shared/apis`,
      '@hooks': `${path.resolve(__dirname, './src')}/shared/hooks`,
    },
  },
  css: {
    postcss: {
      plugins: [tailwindcss()],
    },
  },
  test: {
    environment: 'jsdom',
    setupFiles: './src/__tests__/setupTests.ts',
    globals: true,
    reporters: ['html', 'default'],
    coverage: {
      reporter: ['text', 'html', 'json'],
      enabled: true,
      provider: 'istanbul',
    },
  },
});
