import { test, expect } from '@playwright/test';

test('has title', async ({ page }) => {
  await page.goto('https://bareun.life/');

  await expect(page).toHaveTitle(/바른/);
  await expect(page).toHaveTitle(/생활/);
});

// test('get started link', async ({ page }) => {
//   await page.goto('https://playwright.dev/');

//   // Click the get started link.
//   await page.getByRole('link', { name: 'Get started' }).click();

//   // Expects page to have a heading with the name of Installation.
//   await expect(
//     page.getByRole('heading', { name: 'Installation' }),
//   ).toBeVisible();
// });
