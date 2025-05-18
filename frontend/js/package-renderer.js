class PackageRenderer {
    constructor(apiService) {
        this.apiService = apiService;
        this.packagesListElement = document.getElementById('packages-list');
        this.packageDetailsElement = document.getElementById('package-details');
        this.installationGameElement = document.getElementById('installation-game');
        this.installationSuccessElement = document.getElementById('installation-success');
        
        // Package Details-Elemente
        this.packageNameElement = document.getElementById('package-name');
        this.packageAuthorElement = document.getElementById('package-author');
        this.packageVersionElement = document.getElementById('package-version');
        this.packageCategoryElement = document.getElementById('package-category');
        this.packageCompatibilityElement = document.getElementById('package-compatibility');
        this.packageDownloadsElement = document.getElementById('package-downloads');
        this.packageDescriptionElement = document.getElementById('package-description');
        
        // Button-Event-Listener
        document.getElementById('back-to-list').addEventListener('click', () => this.showPackagesList());
        document.getElementById('install-package').addEventListener('click', () => this.startInstallation());
        document.getElementById('cancel-installation').addEventListener('click', () => this.cancelInstallation());
        document.getElementById('return-to-details').addEventListener('click', () => {
            this.installationSuccessElement.classList.add('hidden');
            this.packageDetailsElement.classList.remove('hidden');
        });
        
        // Filter-Event-Listener
        document.getElementById('compatibility-filter').addEventListener('change', (e) => {
            this.filterByCompatibility(e.target.value);
        });
        
        // Search-Event-Listener
        document.getElementById('search-button').addEventListener('click', () => {
            const query = document.getElementById('search-input').value;
            this.searchPackages(query);
        });
        
        document.getElementById('search-input').addEventListener('keyup', (e) => {
            if (e.key === 'Enter') {
                const query = e.target.value;
                this.searchPackages(query);
            }
        });
    }
    
    async loadPackages() {
        const packages = await this.apiService.getAllPackages();
        this.renderPackagesList(packages);
    }
    
    renderPackagesList(packages) {
        this.packagesListElement.innerHTML = '';
        
        if (packages.length === 0) {
            this.packagesListElement.innerHTML = '<p class="no-results">No packages found. Try a different search.</p>';
            return;
        }
        
        packages.forEach(pkg => {
            const packageCard = document.createElement('div');
            packageCard.className = 'package-card';
            packageCard.dataset.id = pkg.id;
            
            packageCard.innerHTML = `
                <h3>${pkg.name}</h3>
                <p>${pkg.description.substring(0, 80)}${pkg.description.length > 80 ? '...' : ''}</p>
                <p><strong>Author:</strong> ${pkg.author}</p>
                <p><strong>Version:</strong> ${pkg.version}</p>
                <span class="compatibility ${pkg.compatibility}">${pkg.compatibility}</span>
            `;
            
            packageCard.addEventListener('click', () => this.showPackageDetails(pkg.id));
            this.packagesListElement.appendChild(packageCard);
        });
    }
    
    async showPackageDetails(id) {
        const pkg = await this.apiService.getPackageById(id);
        if (!pkg) return;
        
        this.packageNameElement.textContent = pkg.name;
        this.packageAuthorElement.textContent = pkg.author;
        this.packageVersionElement.textContent = pkg.version;
        this.packageCategoryElement.textContent = pkg.category;
        this.packageCompatibilityElement.textContent = pkg.compatibility;
        this.packageDownloadsElement.textContent = pkg.downloads.toLocaleString();
        this.packageDescriptionElement.textContent = pkg.description;
        
        // Animation mit anime.js
        this.packagesListElement.parentElement.classList.add('hidden');
        this.packageDetailsElement.classList.remove('hidden');
        
        anime({
            targets: this.packageDetailsElement,
            opacity: [0, 1],
            translateY: [20, 0],
            easing: 'easeOutExpo',
            duration: 800
        });
        
        // Speichere aktuelle Paket-ID für die Installation
        this.currentPackageId = id;
    }
    
    showPackagesList() {
        this.packageDetailsElement.classList.add('hidden');
        this.packagesListElement.parentElement.classList.remove('hidden');
        
        anime({
            targets: this.packagesListElement.parentElement,
            opacity: [0, 1],
            translateY: [20, 0],
            easing: 'easeOutExpo',
            duration: 800
        });
    }
    
    startInstallation() {
        this.packageDetailsElement.classList.add('hidden');
        this.installationGameElement.classList.remove('hidden');
        
        // Starte das Minispiel
        if (window.installationGame) {
            window.installationGame.startGame();
        }
    }
    
    cancelInstallation() {
        if (window.installationGame) {
            window.installationGame.stopGame();
        }
        
        this.installationGameElement.classList.add('hidden');
        this.packageDetailsElement.classList.remove('hidden');
    }
    
    showInstallationSuccess(packageData) {
        this.installationGameElement.classList.add('hidden');
        this.installationSuccessElement.classList.remove('hidden');
        
        // Chart.js für die Paketstatistik
        const ctx = document.getElementById('stats-chart').getContext('2d');
        new Chart(ctx, {
            type: 'radar',
            data: {
                labels: ['Speed', 'Reliability', 'Security', 'Size', 'Compatibility'],
                datasets: [{
                    label: 'Package Stats',
                    data: [
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100),
                        Math.floor(Math.random() * 100)
                    ],
                    backgroundColor: 'rgba(76, 159, 56, 0.2)',
                    borderColor: 'rgba(76, 159, 56, 1)',
                    pointBackgroundColor: 'rgba(76, 159, 56, 1)',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(76, 159, 56, 1)'
                }]
            },
            options: {
                scales: {
                    r: {
                        angleLines: {
                            color: 'rgba(255, 255, 255, 0.2)'
                        },
                        grid: {
                            color: 'rgba(255, 255, 255, 0.2)'
                        },
                        pointLabels: {
                            color: 'rgba(255, 255, 255, 0.8)'
                        },
                        ticks: {
                            color: 'rgba(255, 255, 255, 0.8)',
                            backdropColor: 'transparent'
                        }
                    }
                },
                plugins: {
                    legend: {
                        labels: {
                            color: 'rgba(255, 255, 255, 0.8)'
                        }
                    }
                }
            }
        });
    }
    
    async searchPackages(query) {
        if (!query.trim()) {
            // Wenn die Suche leer ist, lade alle Pakete
            await this.loadPackages();
            return;
        }
        
        const packages = await this.apiService.searchPackages(query);
        this.renderPackagesList(packages);
    }
    
    async filterByCompatibility(faction) {
        if (!faction) {
            // Wenn kein Filter ausgewählt ist, lade alle Pakete
            await this.loadPackages();
            return;
        }
        
        const packages = await this.apiService.getPackagesByCompatibility(faction);
        this.renderPackagesList(packages);
    }
}