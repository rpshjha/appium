pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
    }

    stages {
        stage('Clone and Build') {
            steps {
                echo 'Cloning repository and building the project'
                git 'https://github.com/rpshjha/appium.git'
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                echo 'Running Maven tests'
                sh 'mvn test'
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }
    }

    post {
        success {
            echo 'The pipeline has succeeded!'
            // You can add additional actions for success if needed
        }

        failure {
            echo 'The pipeline has failed!'
            // You can add additional actions for failure if needed
        }
    }
}
