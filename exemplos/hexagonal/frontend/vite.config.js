import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { resolve } from 'path';
import { fileURLToPath } from 'url';

const __dirname = fileURLToPath(new URL('.', import.meta.url));

export default defineConfig({
  plugins: [vue()],
  base: '/',
  build: {
    outDir: resolve(__dirname, '../src/main/resources/META-INF/resources'),
    emptyOutDir: true,
  },
  server: {
    port: 5173,
    proxy: {
      '/books': 'http://localhost:8080',
    },
  },
});
