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
                CONTAINER_NAME = 'custom-mysql-container'
    }

    stages {

        stage('Setup Environment') {
                    steps {
                        script {
                            // Use withCredentials to securely set credentials and assign to environment variables
                            withCredentials([usernamePassword(credentialsId: 'gitCred', usernameVariable: 'TEMP_USERNAME', passwordVariable: 'TEMP_PASSWORD')]) {
                                // Set these as environment variables for the entire pipeline
                                env.USERNAME = env.TEMP_USERNAME
                                env.PASSWORD = env.TEMP_PASSWORD
                            }
                        }
                    }
                }
        stage('Clone Repository') {
            steps {
                // Checkout the code from the Git repository
                git branch: "${BRANCH}", url: "${GIT_REPO_URL}"
            }
        }


                stage('Build Docker Image') {
                    steps {
                        echo 'Building Docker image for MySQL...'
                        script {
                            // Build the Docker image
                            bat "docker build -t ${IMAGE_NAME} ."
                        }
                    }
                }

                stage('Run MySQL Container') {
                    steps {
                        echo 'Starting MySQL container...'
                        script {
                            // Run the Docker container with MySQL
                            bat """
                                docker run -d --name ${CONTAINER_NAME} \
                                -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
                                -e MYSQL_DATABASE=${MYSQL_DATABASE} \
                                -e MYSQL_USER=${MYSQL_USER} \
                                -e MYSQL_PASSWORD=${MYSQL_PASSWORD} \
                                -p ${MYSQL_PORT}:3306 \
                                ${IMAGE_NAME}
                            """
                        }
                    }
                }

                stage('Verify MySQL Container') {
                    steps {
                        echo 'Verifying MySQL container...'
                        script {
                            // Check if MySQL is running and accessible
//                             bat """
//                                 wsl docker exec custom-mysql-container mysql 127.0.0.1 -u ${MYSQL_USER} -p ${MYSQL_PASSWORD} -e "SHOW DATABASES;"
//                             """
                            def status = bat(
                                            script: "wsl docker exec custom-mysql-container mysql 127.0.0.1 -u ${MYSQL_USER} -p ${MYSQL_PASSWORD} -e 'SHOW DATABASES;'",
                                            returnStatus: true
                                        )
                                        if (status == 0) {
                                            echo 'MySQL container is running and accessible.'
                                        } else {
                                            echo 'Warning: MySQL connection returned a non-zero status.'
                                        }



                        }

                    }
                }





        stage('Build') {
            steps {
                // Build commands (e.g., for a Java project you might use 'mvn clean install')
                echo 'Building project...'
                // sh 'your-build-command-here'
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
            bat "docker stop ${CONTAINER_NAME} "
            bat "docker rm ${CONTAINER_NAME} "

        }
        success {
            echo 'Pipeline succeeded.'

        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
