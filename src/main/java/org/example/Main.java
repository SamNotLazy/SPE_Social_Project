package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

//pipeline {
//    agent any
//
//    environment {
//        MYSQL_ROOT_PASSWORD = 'your_root_password'  // Replace with a secure password or use Jenkins credentials
//        MYSQL_DATABASE = 'your_database_name'
//        MYSQL_USER = 'your_user'
//        MYSQL_PASSWORD = 'your_user_password'
//        MYSQL_PORT = '3306' // The default MySQL port
//    }
//
//    stages {
//        stage('Pull MySQL Docker Image') {
//            steps {
//                echo 'Pulling MySQL Docker image...'
//                script {
//                    sh 'docker pull mysql:latest'
//                }
//            }
//        }
//
//        stage('Run MySQL Container') {
//            steps {
//                echo 'Starting MySQL container...'
//                script {
//                    sh '''
//                        docker run -d --name mysql-container \
//                        -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
//                        -e MYSQL_DATABASE=${MYSQL_DATABASE} \
//                        -e MYSQL_USER=${MYSQL_USER} \
//                        -e MYSQL_PASSWORD=${MYSQL_PASSWORD} \
//                        -p ${MYSQL_PORT}:3306 \
//                        mysql:latest
//                    '''
//                }
//            }
//        }
//
//        stage('Verify MySQL Container') {
//            steps {
//                echo 'Checking MySQL container status...'
//                script {
//                    // Check if MySQL is running and accessible
//                    sh '''
//                        docker exec mysql-container mysql -u${MYSQL_USER} -p${MYSQL_PASSWORD} -e "SHOW DATABASES;"
//                    '''
//                }
//            }
//        }
//    }
//
//    post {
//        always {
//            echo 'Cleaning up...'
//            script {
//                // Stop and remove the MySQL container if it exists
//                sh 'docker stop mysql-container || true'
//                sh 'docker rm mysql-container || true'
//            }
//        }
//    }
//}