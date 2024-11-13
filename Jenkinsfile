pipeline {
    agent any

    environment {
        GIT_REPO_URL = 'https://github.com/SamNotLazy/SPE_Social_Project.git' // Replace with your Git repository URL
        BRANCH = 'main' // Specify the branch you want to build
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
        }
        success {
            echo 'Pipeline succeeded.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
