/// <reference types="vite/client" />

interface ImportMetaEnv {
  VITE_APP_TITLE: string;
  VITE_BASE_URL: string;
  VITE_HOST_API: string;
}

interface ImportMeta {
  env: ImportMetaEnv;
}
