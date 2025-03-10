

pipeline {
    agent any

    environment {
    IMAGE_NAME = "devops091/dotnet-app"
    PASSWORD = credentials('dockerhub')
    DOCKER_HUB_USERNAME = 'brahim2023'
    DOCKER_HUB_PASSWORD = 'Lifeisgoodbrahim@@'
    DOCKER_COMPOSE_FILE = 'docker-compose.yml'
     
  
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/BrahimBenyounes/testk8s.git'
            }
        }
        stage('Build') {
            steps {
                script {
                    dockerBuild("${env.IMAGE_NAME}", "--build-arg CERT_PASSWORD=${env.CERT_PASSWORD}")
                }
            }
        }
        stage('Push') {
            steps {
              script {
                dockerPush("${env.USER}", "${env.PASSWORD}", "${env.IMAGE_NAME}", "${env.BUILD_ID}")
                }
            }
        }
        stage('Cleanup') {
            steps {
                sh "docker rmi $IMAGE_NAME:$BUILD_ID"
            }
        }
        stage('Create Kubernetes Secret') {
            steps {
                 withCredentials([
                    string(credentialsId: 'kubeconfig', variable: 'KUBE_CONFIG')
                ]) {
                    script {
                       kubeSecret ("generic", "https-cert-secret", "${env.CERT_PASSWORD}")
              }
            }
          }
        }
        stage('Create Docker Registry Secret') {
            steps {
                 withCredentials([
                    string(credentialsId: 'kubeconfig', variable: 'KUBE_CONFIG')
                ]) {
                    script {

                        def secretName = "${APP_NAME}"

                // Check if the secret already exists
                def secretCheck = sh(script: "kubectl get secret ${secretName} --ignore-not-found", returnStdout: true).trim()
                echo "Secret check output: '${secretCheck}'"

                def secretExists = secretCheck ? true : false
                echo "secretExists: ${secretExists}"

                if (!secretExists) {
                     // If the secret does not exist, create it
                     sh """
                     kubectl create secret docker-registry ${secretName} \
                     --docker-server=https://index.docker.io/v1/ \
                     --docker-username=${USER} \
                     --docker-password=${PASSWORD} \
                     --docker-email=${DOCKER_HUB_EMAIL} || true
                     """
                     echo "Secret ${secretName} created."
                 } else {
                     echo "Secret ${secretName} already exists. Skipping creation."
                   }
                 }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                 withCredentials([
                    string(credentialsId: 'kubeconfig', variable: 'KUBE_CONFIG')
                ]) {
                    script {
                        kubeDeploy ("${env.IMAGE_NAME}", "${env.APP_NAME}")
                    }
                }
            }
        }
        stage('Verify Deployment') {
            steps {
                withCredentials([
                    string(credentialsId: 'kubeconfig', variable: 'KUBE_CONFIG')
                ])  {
                        script {
                         kubeVerify ("${env.APP_NAME}")
                    }
                }
            }
        }
    }
}
