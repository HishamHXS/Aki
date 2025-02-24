pipeline {
    agent any

    environment {
        DB_USER = "${env.DB_USER}"
        DB_PASS = "${env.DB_PASS}"
    }

    stages {
        stage('Build and Test') {
            steps {
                script {
                    withEnv(["DB_USER=${env.DB_USER}", "DB_PASS=${env.DB_PASS}"]) {
                        sh '''
                        ./mvnw test
                        '''
                    }
                }
            }
        }
    }
}
