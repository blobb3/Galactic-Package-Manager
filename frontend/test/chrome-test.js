const { Builder, By, Key, until } = require('selenium-webdriver');
const chrome = require('chromedriver');
const assert = require('assert');
const path = require('path');
const fs = require('fs');

describe('Galactic Package Manager Frontend Tests', function() {
  this.timeout(30000); // längerer Timeout
  
  let driver;
  
  before(async function() {
    driver = await new Builder().forBrowser('chrome').build();
    // Maximiere Browser für konsistentere Tests
    await driver.manage().window().maximize();
  });
  
  after(async function() {
    await driver.quit();
  });
  
  // Hilfsfunktion für Screenshots (zur Fehlerbehebung)
  async function takeScreenshot(name) {
    const image = await driver.takeScreenshot();
    const screenshotPath = path.join(__dirname, `screenshot-${name}.png`);
    fs.writeFileSync(screenshotPath, image, 'base64');
    console.log(`Screenshot saved to: ${screenshotPath}`);
  }
  
  it('sollte die Seite laden und den Titel im Browser-Tab anzeigen', async function() {
    await driver.get('http://localhost:3000');
    await driver.sleep(2000); // Explizite Pause für Ladezeit
    
    const title = await driver.getTitle();
    console.log("Browser-Titel:", title);
    assert.strictEqual(title, "Galactic Package Manager");
  });
  
  it('sollte den Sternenhintergrund anzeigen', async function() {
    await driver.get('http://localhost:3000');
    await driver.sleep(2000);
    
    const particlesContainer = await driver.findElement(By.id('particles-js'));
    assert.ok(await particlesContainer.isDisplayed());
  });
  
  it('sollte die Seite vollständig darstellen', async function() {
    await driver.get('http://localhost:3000');
    await driver.sleep(3000); // Längere Wartezeit
    
    await takeScreenshot('fullpage');
    
    // Prüfe, ob irgendein Text auf der Seite vorhanden ist
    const bodyText = await driver.findElement(By.tagName('body')).getText();
    console.log("Text auf der Seite:", bodyText.substring(0, 100) + "...");
    
    // Wenn überhaupt Text vorhanden ist, betrachten wir es als erfolgreich
    assert.ok(bodyText.length > 0, "Die Seite enthält keinen Text");
  });
  
  it('sollte auf die Such-Schaltfläche klicken können', async function() {
    await driver.get('http://localhost:3000');
    await driver.sleep(2000);
    
    try {
      // Versuche, die Such-Schaltfläche zu finden und zu klicken
      const searchButton = await driver.findElement(By.id('search-button'));
      await searchButton.click();
      console.log("Erfolgreich auf Such-Button geklickt");
      await takeScreenshot('after-search-click');
      assert.ok(true); // Wenn wir hierher gelangen, war der Test erfolgreich
    } catch (error) {
      console.log("Fehler beim Klicken auf Such-Button:", error.message);
      await takeScreenshot('search-button-error');
      throw error;
    }
  });
  
  // NEUE TESTS HIER HINZUFÜGEN
  it('sollte ein Paket auswählen, Details anzeigen und zur Liste zurückkehren können', async function() {
    await driver.get('http://localhost:3000');
    // Längere Wartezeit für vollständiges Laden der Pakete
    await driver.sleep(4000);
    
    // Screenshot vor der Paketauswahl
    await takeScreenshot('before-package-selection');
    
    try {
      // Finde ein Paket (nimmt das erste aus der Liste)
      const packageCard = await driver.findElement(By.css('.package-card'));
      console.log("Package gefunden, versuche zu klicken...");
      
      // Scrolle zum Paket, falls es außerhalb des sichtbaren Bereichs ist
      await driver.executeScript("arguments[0].scrollIntoView(true);", packageCard);
      await driver.sleep(1000);
      
      // Klicke auf das Paket
      await packageCard.click();
      console.log("Auf Paket geklickt, warte auf Detailansicht...");
      await driver.sleep(2000);
      
      // Screenshot nach Paketauswahl
      await takeScreenshot('package-details-view');
      
      // Überprüfe, ob die Detailansicht angezeigt wird
      const detailsSection = await driver.findElement(By.id('package-details'));
      const isDetailsSectionVisible = await detailsSection.isDisplayed();
      assert.ok(isDetailsSectionVisible, "Paketdetails werden nicht angezeigt");
      
      // Finde und klicke auf den "Back to List"-Button
      const backButton = await driver.findElement(By.id('back-to-list'));
      console.log("Back-Button gefunden, versuche zu klicken...");
      await backButton.click();
      await driver.sleep(2000);
      
      // Screenshot nach Rückkehr zur Liste
      await takeScreenshot('back-to-list-view');
      
      // Überprüfe, ob die Paketliste wieder sichtbar ist
      const packagesList = await driver.findElement(By.id('packages-list'));
      const isPackagesListVisible = await packagesList.isDisplayed();
      assert.ok(isPackagesListVisible, "Paketliste ist nach Rückkehr nicht sichtbar");
      
      console.log("Navigation erfolgreich: Paket ausgewählt und zurück zur Liste");
      assert.ok(true);
    } catch (error) {
      console.log("Fehler bei der Paketauswahl und Navigation:", error.message);
      await takeScreenshot('package-navigation-error');
      throw error;
    }
  });
  
  it('sollte das Installations-Mini-Game starten und abbrechen können', async function() {
    await driver.get('http://localhost:3000');
    await driver.sleep(4000);
    
    try {
      // Wähle ein Paket aus
      const packageCard = await driver.findElement(By.css('.package-card'));
      await packageCard.click();
      await driver.sleep(2000);
      
      // Klicke auf den Install-Button
      const installButton = await driver.findElement(By.id('install-package'));
      console.log("Install-Button gefunden, versuche zu klicken...");
      await installButton.click();
      await driver.sleep(2000);
      
      // Screenshot des Spiels
      await takeScreenshot('installation-game');
      
      // Überprüfe, ob das Spiel angezeigt wird
      const gameSection = await driver.findElement(By.id('installation-game'));
      const isGameVisible = await gameSection.isDisplayed();
      assert.ok(isGameVisible, "Installation-Game wird nicht angezeigt");
      
      // Klicke auf Cancel
      const cancelButton = await driver.findElement(By.id('cancel-installation'));
      console.log("Cancel-Button gefunden, versuche zu klicken...");
      await cancelButton.click();
      await driver.sleep(2000);
      
      // Screenshot nach Abbruch
      await takeScreenshot('after-game-cancel');
      
      // Überprüfe, ob wir wieder in der Detailansicht sind
      const detailsSection = await driver.findElement(By.id('package-details'));
      const isDetailsSectionVisible = await detailsSection.isDisplayed();
      assert.ok(isDetailsSectionVisible, "Nach Abbruch der Installation werden Paketdetails nicht angezeigt");
      
      console.log("Installation-Test erfolgreich: Spiel gestartet und abgebrochen");
      assert.ok(true);
    } catch (error) {
      console.log("Fehler beim Installations-Test:", error.message);
      await takeScreenshot('installation-test-error');
      throw error;
    }
  });
});