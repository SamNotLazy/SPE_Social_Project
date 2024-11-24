pipeline {
    agent any

    environment {
        GIT_REPO_URL = 'https://github.com/SamNotLazy/SPE_Social_Project.git' // Replace with your Git repository URL
        BRANCH = 'main' // Specify the branch you want to build
        MYSQL_ROOT_PASSWORD = 'root_password'   // Replace with a secure password
                MYSQL_DATABASE = 'my_database'
                MYSQL_USER = 'my_user'
                MYSQL_PASSWORD = 'user_password'
                MYSQL_PORT = '3306'
                IMAGE_NAME = 'custom-mysql-image'
                SQL_CONTAINER_NAME = 'mysql-container'
                INTEGRITY_TEST_CONTAINER_NAME='itestapp-container'
    }

    stages {

        stage('Secure Fetch From Repository') {
                    steps {
                        script {
                            // Use withCredentials to securely set credentials and assign to environment variables
                            withCredentials([usernamePassword(credentialsId: 'gitCred', usernameVariable: 'TEMP_USERNAME', passwordVariable: 'TEMP_PASSWORD')]) {
                                // Set these as environment variables for the entire pipeline
                                env.USERNAME = env.TEMP_USERNAME
                                env.PASSWORD = env.TEMP_PASSWORD
                            }
                        }
                        git branch: "${BRANCH}", url: "${GIT_REPO_URL}"
                    }
                }

        stage('Maven Build') {
                    steps {
                        bat "mvn test clean package"
                    }
                }


                stage('Docker Image testings') {
                    steps {

                        script {
                            // Build the Docker image
                            bat "docker-compose up --build -d"
                        }
                    }
                }
        stage('Verify MySQL Docker Container') {
            steps {
                echo 'Verifying MySQL container...'
                script {
                def status = bat(script: "wsl docker exec custom-mysql-container mysql 127.0.0.1 -u ${MYSQL_USER} -p ${MYSQL_PASSWORD} -e 'SHOW DATABASES;'",
                                 returnStatus: true)
                                     if (status == 0) {
                                     echo 'MySQL container is running and accessible.'
                                     } else {
                                     echo 'Warning: MySQL connection returned a non-zero status.'
                                     }
                                 }
            }
        }


                stage('Build and Push Docker Images') {
                            steps {
                                script {
                                    withCredentials([usernamePassword(credentialsId: 'dockCred', usernameVariable: 'TEMP_USERNAME', passwordVariable: 'TEMP_PASSWORD')]) {
                                                                    // Set these as environment variables for the entire pipeline
                                                                    env.USERNAME = env.TEMP_USERNAME
                                                                    env.PASSWORD = env.TEMP_PASSWORD
                                                                }
                                    bat '''
                                    docker-compose config | grep 'image:' | awk '{print $2}' | while read image; do
                                        echo "Pushing image: $image"
                                        docker push $image
                                    done
                                    '''
                                }
            }
        }

        stage('Test') {
            steps {
                // Test commands (e.g., for a Java project you might use 'mvn test')
                echo 'Running tests...'
                // sh 'your-test-command-here'
            }
        }

        stage('Deploy') {
            steps {
                // Deployment steps (if any)
                echo 'Deploying application...'
                // sh 'your-deploy-command-here'
            }
        }
    }

    post {
        always {
            // Actions to run at the end of the pipeline, regardless of success/failure
            echo 'Pipeline finished.'
            bat "docker stop ${SQL_CONTAINER_NAME} "
            bat "docker rm ${SQL_CONTAINER_NAME} "
            bat "docker stop ${INTEGRITY_TEST_CONTAINER_NAME} "
            bat "docker rm ${INTEGRITY_TEST_CONTAINER_NAME} "
        }
        success {
            echo 'Pipeline succeeded.'

        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
