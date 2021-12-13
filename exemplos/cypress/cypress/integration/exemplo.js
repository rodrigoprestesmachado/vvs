/* ==== Test Created with Cypress Studio ==== */
it('Dados', function() {
  /* ==== Generated with Cypress Studio ==== */
  cy.visit('localhost:8080');
  cy.get('#inputNome').clear();
  cy.get('#inputNome').type('Rodrigo');
  cy.get('#inputSenha').clear();
  cy.get('#inputSenha').type('vvs');
  /* ==== End Cypress Studio ==== */
  /* ==== Generated with Cypress Studio ==== */
  cy.get('#button').click();
  cy.get('#result > div').click();
  /* ==== End Cypress Studio ==== */
  /* ==== Generated with Cypress Studio ==== */
  cy.get('#result > div').should('be.visible');
  /* ==== End Cypress Studio ==== */
});