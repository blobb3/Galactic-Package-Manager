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
        
        stage('Docker Build') {
            steps {
                sh '''
                    export DOCKER_HOST=tcp://host.docker.internal:2375
                    docker build -t heinejan/galactic-pm:latest .
                '''
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerHub-heinejan-DevOps', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh '''
                        # Debug-Ausgaben aktivieren
                        set -xe
                        
                        export DOCKER_HOST=tcp://host.docker.internal:2375
                        
                        # Docker-Version anzeigen
                        docker --version
                        
                        # Vorhandene Images anzeigen
                        echo "Vorhandene Images:"
                        docker images
                        
                        # Explizit neu taggen (manchmal hilft das)
                        echo "Tagge Image neu..."
                        docker tag $(docker images -q heinejan/galactic-pm:latest) heinejan/galactic-pm:latest || echo "Tagging fehlgeschlagen, fahre fort..."
                        
                        # Bei Docker Hub anmelden
                        echo "Melde bei Docker Hub an..."
                        echo $PASSWORD | docker login -u $USERNAME --password-stdin
                        
                        # Status nach Login prüfen
                        if [ $? -ne 0 ]; then
                            echo "Docker Login fehlgeschlagen!"
                            exit 1
                        fi
                        
                        # Image pushen mit ausführlichen Informationen
                        echo "Pushe Image zu Docker Hub..."
                        docker push heinejan/galactic-pm:latest
                        
                        # Status nach Push prüfen
                        if [ $? -ne 0 ]; then
                            echo "Docker Push fehlgeschlagen!"
                            exit 1
                        fi
                        
                        echo "Docker Hub Push abgeschlossen."
                    '''
                }
            }
        }
        
        stage('Local Deploy') {
            steps {
                sh '''
                    export DOCKER_HOST=tcp://host.docker.internal:2375
                    docker stop gpm-container || true
                    docker rm gpm-container || true
                    docker run -d -p 3000:8081 --name gpm-container heinejan/galactic-pm:latest
                    
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
                            echo "Starte Deployment auf Render.com..."
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