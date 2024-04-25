import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import path from 'path';

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
});
