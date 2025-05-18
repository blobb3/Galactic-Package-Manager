pipeline {
    agent any
    
    tools {
        gradle 'Gradle'
        nodejs 'NodeJS 21.11.0' 
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Backend Build & Test') {
            steps {
                dir('backend') {
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean test build'
                }
            }
            post {
                always {
                    junit 'backend/build/test-results/test/*.xml'
                    jacoco execPattern: 'backend/build/jacoco/test.exec',
                           classPattern: 'backend/build/classes/java/main',
                           sourcePattern: 'backend/src/main/java'
                }
            }
        }
        
        stage('Frontend Build & Test') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run lint:html'
                    // Fügen Sie hier weitere Frontend-Tests hinzu
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                sh '''
                export DOCKER_HOST=tcp://host.docker.internal:2375
                
                # Backend Docker build
                docker build -t heinejan/devopsdemo:backend-${BUILD_NUMBER} .
                
                # Frontend Docker build (falls eine separate Dockerfile existiert)
                if [ -f "frontend/Dockerfile" ]; then
                    docker build -t heinejan/devopsdemo:frontend-${BUILD_NUMBER} ./frontend
                fi
                '''
            }
        }
    }
    
    post {
        always {
            echo 'Build und Test abgeschlossen'
        }
        success {
            echo 'Die Macht ist stark in diesem Build!'
        }
        failure {
            echo 'Ich spüre eine Störung in der Macht...'
        }
    }
}