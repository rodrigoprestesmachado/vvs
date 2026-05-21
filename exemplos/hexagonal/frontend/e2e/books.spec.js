import { test, expect } from '@playwright/test';

test('cadastra dois livros com ISBN-10 válido terminando em X', async ({ page }) => {
  await page.goto('/');

  await page.getByRole('button', { name: 'Novo livro' }).click();
  await page.getByRole('textbox', { name: 'ISBN-' }).fill('156881111X');
  await page.getByRole('textbox', { name: 'Título' }).fill(
    'Erdős on Graphs: His Legacy of Unsolved Problems',
  );
  await page.getByRole('textbox', { name: 'Autor' }).fill('Fan Chung, Ronald L. Graham');
  await page.getByRole('spinbutton', { name: 'Ano de publicação' }).fill('1999');
  await page.getByRole('button', { name: 'Salvar' }).click();

  await page.getByRole('button', { name: 'Novo livro' }).click();
  await page.getByRole('textbox', { name: 'ISBN-' }).fill('0-201-89551-X');
  await page.getByRole('textbox', { name: 'Título' }).fill(
    'Object-Oriented Analysis and Design with Applications',
  );
  await page.getByRole('textbox', { name: 'Autor' }).fill(
    'Grady Booch, James Rumbaugh, Ivar Jacobson',
  );
  await page.getByRole('spinbutton', { name: 'Ano de publicação' }).fill('2007');
  await page.getByRole('button', { name: 'Salvar' }).click();

  await expect(
    page.getByRole('row').filter({ hasText: '156881111X' }),
  ).toBeVisible();
  await expect(
    page.getByRole('row').filter({ hasText: '020189551X' }),
  ).toBeVisible();
});
