import React from 'react';
import { QueryClient } from 'react-query';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

export const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
