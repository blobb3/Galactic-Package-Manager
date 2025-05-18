// cypress/e2e/galactic-pm.cy.js
describe('Galactic Package Manager Tests', () => {
    beforeEach(() => {
      // Vor jedem Test die Anwendung öffnen
      cy.visit('/');
      // Kurz warten, damit die Seite vollständig geladen ist
      cy.wait(2000);
    });
  
    it('sollte den richtigen Seitentitel haben', () => {
      cy.title().should('eq', 'Galactic Package Manager');
    });
  
    it('sollte den Particles-Hintergrund anzeigen', () => {
      cy.get('#particles-js').should('be.visible');
    });
  
    it('sollte die Suchfunktion anzeigen', () => {
      cy.get('#search-input').should('be.visible');
      cy.get('#search-button').should('be.visible');
    });
  
    it('sollte auf die Suchschaltfläche klicken können', () => {
      cy.get('#search-button').click();
      // Screenshot für visuelle Überprüfung
      cy.screenshot('after-search-click');
    });
  
    it('sollte ein Paket auswählen, Details anzeigen und zur Liste zurückkehren können', () => {
      // Warte auf das Laden der Pakete
      cy.get('.package-card').first().should('be.visible');
      
      // Screenshot vor der Paketauswahl
      cy.screenshot('before-package-selection');
      
      // Auf das erste Paket klicken
      cy.get('.package-card').first().click();
      
      // Warte auf die Detailansicht
      cy.get('#package-details').should('be.visible');
      
      // Screenshot der Detailansicht
      cy.screenshot('package-details-view');
      
      // Auf "Back to List" klicken
      cy.get('#back-to-list').click();
      
      // Sicherstellen, dass die Paketliste wieder angezeigt wird
      cy.get('#packages-list').should('be.visible');
      
      // Screenshot nach Rückkehr zur Liste
      cy.screenshot('back-to-list-view');
    });
  
    it('sollte das Installations-Mini-Game starten und abbrechen können', () => {
      // Auf das erste Paket klicken
      cy.get('.package-card').first().click();
      
      // Auf "Install Package" klicken
      cy.get('#install-package').click();
      
      // Warte auf das Spielfenster
      cy.get('#installation-game').should('be.visible');
      
      // Screenshot des Spiels
      cy.screenshot('installation-game');
      
      // Auf "Cancel" klicken
      cy.get('#cancel-installation').click();
      
      // Sicherstellen, dass wir zurück auf der Detailseite sind
      cy.get('#package-details').should('be.visible');
      
      // Screenshot nach Abbruch
      cy.screenshot('after-game-cancel');
    });
  });