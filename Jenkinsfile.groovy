pipeline {
    agent any
    environment {
        AWS_ACCOUNT_ID = "000375135601"
        AWS_DEFAULT_REGION = "ap-south-1"
        IMAGE_REPO_NAME = "dokertest"
        IMAGE_TAG = "latest"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
    }
    stages {
        stage('Cloning Git') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/AjiteshNarra03/docker']]
                ])
            }
        }
        stage('Login to ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                }
            }
        }
        stage('Build Image') {
            steps {
                script {
                    dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage('Pushing to ECR') {
            steps {
                script {
                    echo "Pushing Docker image to ECR"
                    sh "docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:${IMAGE_TAG}"
                    echo "Image tagged successfully"
                    sh "docker push ${REPOSITORY_URI}:${IMAGE_TAG}"
                    echo "Docker push completed"
                }
            }
        }
    }
}