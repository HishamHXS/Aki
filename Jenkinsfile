pipeline {
    agent {
        docker {
            image 'openjdk:23-jdk'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    environment {
        source .env
        DB_USER = ${DB_USER}
        DB_PASS = ${DB_PASSWORD}
    }
    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/HishamHXS/Aki.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Deploy with Docker') {
            steps {
                sh 'docker compose down && docker compose up -d --build'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
