class InstallationGame {
    constructor(packageRenderer) {
        this.packageRenderer = packageRenderer;
        this.gameArea = document.getElementById('game-area');
        this.progressBar = document.getElementById('installation-progress');
        this.progressPercentage = document.getElementById('progress-percentage');
        this.progress = 0;
        this.targetCount = 0;
        this.clickedTargets = 0;
        this.gameInterval = null;
        this.targets = [];
    }
    
    startGame() {
        this.progress = 0;
        this.targetCount = 0;
        this.clickedTargets = 0;
        this.targets = [];
        this.updateProgress(0);
        
        // Lösche alle vorhandenen Ziele
        while (this.gameArea.firstChild) {
            this.gameArea.removeChild(this.gameArea.firstChild);
        }
        
        // Starte das Spiel
        this.gameInterval = setInterval(() => this.gameLoop(), 1000);
    }
    
    stopGame() {
        if (this.gameInterval) {
            clearInterval(this.gameInterval);
            this.gameInterval = null;
        }
        
        // Lösche alle vorhandenen Ziele
        this.targets.forEach(target => {
            if (target.element && target.element.parentNode) {
                target.element.parentNode.removeChild(target.element);
            }
        });
        this.targets = [];
    }
    
    gameLoop() {
        // Spawn neues Ziel
        this.spawnTarget();
        
        // Erhöhe die Installationsgeschwindigkeit mit der Zeit
        this.progress += 2;
        
        // Beende das Spiel, wenn die Installation abgeschlossen ist
        if (this.progress >= 100) {
            this.completeInstallation();
        }
        
        this.updateProgress(this.progress);
    }
    
    spawnTarget() {
        const target = document.createElement('div');
        target.className = 'target';
        
        // Zufällige Position im Spielbereich
        const maxX = this.gameArea.clientWidth - 40;
        const maxY = this.gameArea.clientHeight - 40;
        const posX = Math.floor(Math.random() * maxX);
        const posY = Math.floor(Math.random() * maxY);
        
        target.style.left = `${posX}px`;
        target.style.top = `${posY}px`;
        
        // Animation mit anime.js
        target.style.opacity = '0';
        this.gameArea.appendChild(target);
        
        anime({
            targets: target,
            opacity: [0, 1],
            scale: [0.5, 1],
            duration: 500,
            easing: 'easeOutElastic(1, .8)'
        });
        
        // Klick-Event
        target.addEventListener('click', () => this.targetClicked(target));
        
        // Timer für Verschwinden
        const targetObj = {
            element: target,
            timeout: setTimeout(() => {
                if (target.parentNode) {
                    anime({
                        targets: target,
                        opacity: 0,
                        scale: 0.5,
                        duration: 500,
                        easing: 'easeInExpo',
                        complete: () => {
                            if (target.parentNode) {
                                target.parentNode.removeChild(target);
                            }
                            this.targets = this.targets.filter(t => t.element !== target);
                        }
                    });
                }
            }, 3000)
        };
        
        this.targets.push(targetObj);
        this.targetCount++;
    }
    
    targetClicked(target) {
        if (target.clicked) return;
        target.clicked = true;
        
        // Animation beim Klicken
        anime({
            targets: target,
            opacity: 0,
            scale: 1.5,
            rotate: '1turn',
            duration: 500,
            easing: 'easeOutExpo',
            complete: () => {
                if (target.parentNode) {
                    target.parentNode.removeChild(target);
                }
                this.targets = this.targets.filter(t => t.element !== target);
            }
        });
        
        // Boost für den Installationsfortschritt
        this.progress += 5;
        this.clickedTargets++;
        this.updateProgress(this.progress);
        
        // Beende das Spiel, wenn die Installation abgeschlossen ist
        if (this.progress >= 100) {
            this.completeInstallation();
        }
    }
    
    updateProgress(value) {
        // Begrenze den Wert auf maximal 100
        value = Math.min(value, 100);
        this.progressBar.style.width = `${value}%`;
        this.progressPercentage.textContent = Math.floor(value);
    }
    
    completeInstallation() {
        this.stopGame();
        
        // Zeige Erfolgsseite mit Paketinformationen an
        this.packageRenderer.showInstallationSuccess({
            clickedTargets: this.clickedTargets,
            totalTargets: this.targetCount
        });
    }
}