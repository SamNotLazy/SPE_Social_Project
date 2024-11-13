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

//         stage('Create Dockerfile') {
//                     steps {
//                         echo 'Creating Dockerfile for MySQL...'
//                         script {
//                             // Create the Dockerfile with MySQL setup
//                             writeFile file: 'Dockerfile', text: '''
//                                 FROM mysql:latest
//                                 ENV MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
//                                 ENV MYSQL_DATABASE=${MYSQL_DATABASE}
//                                 ENV MYSQL_USER=${MYSQL_USER}
//                                 ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}
//                                 EXPOSE 3306
//                                 # Uncomment the following line if you have an init.sql file for custom setup
//                                 # COPY ./init.sql /docker-entrypoint-initdb.d/
//                             '''
//                         }
//                     }
//                 }

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
                            bat """
                                wsl docker exec custom-mysql-container mysql 127.0.0.1 -u ${MYSQL_USER} -p ${MYSQL_PASSWORD} -e "SHOW DATABASES;"
                            """
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
            bat "docker stop ${IMAGE_NAME} "
            bat "docker rm ${IMAGE_NAME} "
        }
        success {
            echo 'Pipeline succeeded.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
