pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'GitHub-blobb3-Token', url: 'https://github.com/blobb3/Galactic-Package-Manager']])
            }
        }
        
        stage('Backend Build & Test') {
            steps {
                dir('backend') {
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean test build'
                }
                junit stdioRetention: '', testResults: '**/test-results/test/*.xml'
                jacoco()
            }
        }
        
        stage('Frontend Build') {
            steps {
                dir('frontend') {
                    sh '''
                        export DOCKER_HOST=tcp://host.docker.internal:2375
                        echo "Building frontend Docker image..."
                        docker build -t heinejan/galactic-pm-frontend:latest .
                    '''
                }
            }
        }

        stage('Docker Push Frontend') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerHub-heinejan-DevOps', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh '''
                        export DOCKER_HOST=tcp://host.docker.internal:2375
                        
                        # Bei Docker Hub anmelden
                        echo "Melde bei Docker Hub an..."
                        echo $PASSWORD | docker login -u $USERNAME --password-stdin
                        
                        # Frontend-Image pushen
                        echo "Pushe Frontend-Image zu Docker Hub..."
                        docker push heinejan/galactic-pm-frontend:latest
                    '''
                }
            }
        }
        
        stage('Local Deploy for Testing') {
            steps {
                sh '''
                    export DOCKER_HOST=tcp://host.docker.internal:2375
                    
                    # Bestehende Container stoppen und entfernen
                    docker stop gpm-backend gpm-frontend || true
                    docker rm gpm-backend gpm-frontend || true
                    
                    # Backend für lokales Testing starten (nur in Jenkins)
                    docker run -d -p 8080:8080 --name gpm-backend heinejan/galactic-pm:latest
                    
                    # Frontend starten (das ist, was auf Render deployt wird)
                    docker run -d -p 3000:3000 --name gpm-frontend heinejan/galactic-pm-frontend:latest
                    
                    # Warten auf Container-Start
                    echo "Warte auf Container-Start..."
                    sleep 10
                '''
            }
        }
        
        stage('Trigger Render Deployment') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'RenderDeployKey2', variable: 'KEY')]) {
                        sh '''
                            echo "Starte Frontend-Deployment auf Render.com..."
                            curl -X GET "https://api.render.com/deploy/${KEY}"
                            echo "Render.com Deployment wurde ausgelöst."
                        '''
                    }
                }
            }
        }
    }
    
    post {
        always {
            sh 'export DOCKER_HOST=tcp://host.docker.internal:2375; docker logout'
        }
        success {
            echo 'Die Macht ist stark in diesem Build!'
        }
        failure {
            echo 'Ich spüre eine Störung in der Macht...'
        }
    }
}