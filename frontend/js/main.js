document.addEventListener('DOMContentLoaded', () => {
    // API-Service initialisieren
    const apiService = new ApiService();
    
    // Package-Renderer initialisieren
    const packageRenderer = new PackageRenderer(apiService);
    
    // Installationsspiel initialisieren
    window.installationGame = new InstallationGame(packageRenderer);
    
    // Pakete laden
    packageRenderer.loadPackages();
    
    // Willkommensanimation mit anime.js
    anime({
        targets: '.logo-container',
        translateY: ['-30px', '0px'],
        opacity: [0, 1],
        easing: 'easeOutExpo',
        duration: 1500,
        delay: 500
    });
    
    anime({
        targets: '.search-container, .filter-container',
        translateY: ['20px', '0px'],
        opacity: [0, 1],
        easing: 'easeOutExpo',
        duration: 1200,
        delay: anime.stagger(200, {start: 1000})
    });
    
    anime({
        targets: '.packages-container',
        translateY: ['50px', '0px'],
        opacity: [0, 1],
        easing: 'easeOutExpo',
        duration: 1800,
        delay: 1500
    });
});