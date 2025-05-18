// cypress.config.js
module.exports = {
    e2e: {
      setupNodeEvents(on, config) {
        // Ereignisse einrichten
      },
      baseUrl: 'http://localhost:3000',
      viewportWidth: 1280,
      viewportHeight: 720,
      defaultCommandTimeout: 10000,
      watchForFileChanges: false
    },
  };