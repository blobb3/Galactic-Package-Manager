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
        }
       
        stage('Docker Build') {
            steps {
                sh '''
                export DOCKER_HOST=tcp://host.docker.internal:2375
                docker build -t heinejan/galactic-pm:latest .
                '''
            }
        }
        
        stage('Deploy Local') {
            steps {
                sh '''
                export DOCKER_HOST=tcp://host.docker.internal:2375
                docker stop gpm-container || true
                docker rm gpm-container || true
                docker run -d -p 3001:3000 -p 8080:8080 --name gpm-container heinejan/galactic-pm:latest
                
                # Auf Container-Start warten
                echo "Warte auf Container-Start..."
                sleep 10
                '''
            }
        }
        
        stage('Docker Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'DockerHub-heinejan', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh '''
                        export DOCKER_HOST=tcp://host.docker.internal:2375
                        echo $PASSWORD | docker login -u $USERNAME --password-stdin
                        docker push heinejan/galactic-pm:latest
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