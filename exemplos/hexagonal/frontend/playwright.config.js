import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { defineConfig, devices } from '@playwright/test';

const frontendDir = dirname(fileURLToPath(import.meta.url));
const projectRoot = process.env.MAVEN_PROJECT_DIR || join(frontendDir, '..');
const mvnw = process.platform === 'win32' ? 'mvnw.cmd' : 'mvnw';
const mvnwPath = join(projectRoot, mvnw);

/** @type {import('@playwright/test').PlaywrightTestConfig} */
export default defineConfig({
  testDir: './e2e',
  timeout: 60_000,
  expect: { timeout: 10_000 },
  fullyParallel: false,
  workers: 1,
  reporter: [['list'], ['html', { open: 'never' }]],
  use: {
    ...devices['Desktop Chrome'],
    baseURL: process.env.BASE_URL || 'http://localhost:8080',
    headless: true,
    trace: 'on-first-retry',
  },
  webServer: process.env.BASE_URL
    ? undefined
    : {
        command: `"${mvnwPath}" quarkus:dev -DskipTests -Dskip.frontend=true -Dquarkus.banner.enabled=false`,
        cwd: projectRoot,
        url: 'http://localhost:8080',
        reuseExistingServer: !process.env.CI,
        timeout: 240_000,
      },
});
