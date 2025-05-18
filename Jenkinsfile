pipeline {
    agent any
    
    tools {
        jdk 'JDK 21'
        gradle 'Gradle 8.5'
        nodejs 'Node 20.x'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Backend Build') {
            steps {
                dir('backend/galactic-pm') {
                    script {
                        if (isUnix()) {
                            sh './gradlew clean build -x test'
                        } else {
                            bat 'gradlew.bat clean build -x test'
                        }
                    }
                }
            }
        }
        
        stage('Backend Tests') {
            steps {
                dir('backend/galactic-pm') {
                    script {
                        if (isUnix()) {
                            sh './gradlew test jacocoTestReport'
                        } else {
                            bat 'gradlew.bat test jacocoTestReport'
                        }
                    }
                }
            }
            post {
                always {
                    junit 'backend/galactic-pm/build/test-results/test/*.xml'
                    jacoco(
                        execPattern: 'backend/galactic-pm/build/jacoco/test.exec',
                        classPattern: 'backend/galactic-pm/build/classes',
                        sourcePattern: 'backend/galactic-pm/src/main/java',
                        exclusionPattern: 'backend/galactic-pm/src/test/*'
                    )
                }
            }
        }
        
        stage('Frontend Setup') {
            steps {
                dir('frontend') {
                    script {
                        if (isUnix()) {
                            sh 'npm install'
                            sh 'npm ci'
                        } else {
                            bat 'npm install'
                            bat 'npm ci'
                        }
                    }
                }
            }
        }
        
        stage('Start Applications') {
            steps {
                parallel(
                    backend: {
                        dir('backend/galactic-pm') {
                            script {
                                if (isUnix()) {
                                    sh './gradlew bootRun &'
                                    sh 'sleep 30' // Warten auf den Start des Backends
                                } else {
                                    bat 'start /b gradlew.bat bootRun'
                                    bat 'timeout /t 30' // Warten auf den Start des Backends
                                }
                            }
                        }
                    },
                    frontend: {
                        dir('frontend') {
                            script {
                                if (isUnix()) {
                                    sh 'npx serve -p 3000 &'
                                    sh 'sleep 10' // Warten auf den Start des Frontends
                                } else {
                                    bat 'start /b npx serve -p 3000'
                                    bat 'timeout /t 10' // Warten auf den Start des Frontends
                                }
                            }
                        }
                    }
                )
            }
        }
        
        stage('UI Tests') {
            steps {
                parallel(
                    seleniumChrome: {
                        dir('frontend') {
                            script {
                                if (isUnix()) {
                                    sh 'npm run selenium:chrome'
                                } else {
                                    bat 'npm run selenium:chrome'
                                }
                            }
                        }
                    },
                    seleniumFirefox: {
                        dir('frontend') {
                            script {
                                if (isUnix()) {
                                    sh 'npm run selenium:firefox'
                                } else {
                                    bat 'npm run selenium:firefox'
                                }
                            }
                        }
                    },
                    cypress: {
                        dir('frontend') {
                            script {
                                if (isUnix()) {
                                    sh 'npx cypress run'
                                } else {
                                    bat 'npx cypress run'
                                }
                            }
                        }
                    }
                )
            }
            post {
                always {
                    archiveArtifacts artifacts: 'frontend/test/screenshot-*.png', allowEmptyArchive: true
                    archiveArtifacts artifacts: 'frontend/cypress/screenshots/**/*.png', allowEmptyArchive: true
                    archiveArtifacts artifacts: 'frontend/cypress/videos/**/*.mp4', allowEmptyArchive: true
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                dir('backend/galactic-pm') {
                    script {
                        if (isUnix()) {
                            sh './gradlew sonar -Dsonar.projectKey=galactic-pm -Dsonar.host.url=http://localhost:9000 -Dsonar.token=${SONAR_TOKEN}'
                        } else {
                            bat 'gradlew.bat sonar -Dsonar.projectKey=galactic-pm -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%SONAR_TOKEN%'
                        }
                    }
                }
            }
        }
        
        stage('Package Application') {
            steps {
                dir('backend/galactic-pm') {
                    script {
                        if (isUnix()) {
                            sh './gradlew bootJar'
                        } else {
                            bat 'gradlew.bat bootJar'
                        }
                    }
                }
                
                dir('frontend') {
                    script {
                        if (isUnix()) {
                            sh 'tar -czf frontend-dist.tar.gz css js *.html package.json'
                        } else {
                            bat 'tar -czf frontend-dist.tar.gz css js *.html package.json'
                        }
                    }
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: 'backend/galactic-pm/build/libs/*.jar'
                    archiveArtifacts artifacts: 'frontend/frontend-dist.tar.gz'
                }
            }
        }
    }
    
    post {
        always {
            // Stoppt alle laufenden Prozesse
            script {
                if (isUnix()) {
                    sh 'pkill -f "gradlew bootRun" || true'
                    sh 'pkill -f "npx serve" || true'
                } else {
                    bat 'taskkill /F /IM java.exe /FI "WINDOWTITLE eq gradlew*" > nul 2>&1 || exit /b 0'
                    bat 'taskkill /F /IM node.exe /FI "WINDOWTITLE eq serve*" > nul 2>&1 || exit /b 0'
                }
            }
        }
        success {
            echo 'Der Galactic Package Manager wurde erfolgreich gebaut und getestet! Möge die Macht mit dir sein!'
        }
        failure {
            echo 'Der Build ist gescheitert wie ein TIE-Fighter gegen den Todesstern!'
        }
    }
}