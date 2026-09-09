pipeline {
    agent any

    tools {
        maven 'maven-3.9.5'
        jdk 'openjdk-17'
    }

    environment {
        DOCKER_REGISTRY = 'docker-registry:5000'
        IMAGE_NAME = 'car-dealership'
        BUILD_VERSION = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo '✅ Code checked out successfully'
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building the application...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo '📦 Packaging the application...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def imageTag = "${env.DOCKER_REGISTRY}/${env.IMAGE_NAME}:${env.BUILD_VERSION}"
                    echo "🐳 Building Docker image: ${imageTag}"
                    sh """
                        docker build -t ${imageTag} .
                        docker tag ${imageTag} ${env.DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest
                    """
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def imageTag = "${env.DOCKER_REGISTRY}/${env.IMAGE_NAME}:${env.BUILD_VERSION}"
                    echo "⬆️ Pushing Docker image to registry..."
                    sh """
                        docker push ${imageTag}
                        docker push ${env.DOCKER_REGISTRY}/${env.IMAGE_NAME}:latest
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "🚀 Deploying to Kubernetes..."
                    sh """
                        kubectl set image deployment/car-dealership \
                            car-dealership=${env.DOCKER_REGISTRY}/${env.IMAGE_NAME}:${env.BUILD_VERSION} \
                            -n production
                        kubectl rollout status deployment/car-dealership -n production
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
            slackSend(
                color: 'good',
                message: "✅ Deployment successful! Version ${env.BUILD_VERSION}"
            )
        }
        failure {
            echo "❌ Pipeline failed!"
            slackSend(
                color: 'danger',
                message: "❌ Deployment failed! Build ${env.BUILD_VERSION}"
            )
        }
        always {
            cleanWs()
        }
    }
}