pipeline {
    agent any
    
    options {
        skipDefaultCheckout true
    }
    
    triggers {
        pollSCM('H/5 * * * *')
    }
    
    tools {
        nodejs 'node20'
        nodejs 'node22'
    }
    
    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        credentialsId: 'jenkins',
                        url: 'https://github.com/maximAdmakin/devopsL.git'
                    ]]
                ])
            }
        }
        
        stage('Build and Test') {
            matrix {
                axes {
                    axis {
                        name 'NODE_VERSION'
                        values 'node20', 'node22'
                    }
                }
                stages {
                    stage('Setup') {
                        steps {
                            nodejs(NODE_VERSION) {
                                script {
                                    sh 'rm -rf node_modules package-lock.json || true'
                                }
                            }
                        }
                    }
                    
                    stage('Install') {
                        steps {
                            nodejs(NODE_VERSION) {
                                dir('front-service') {
                                    sh 'npm install --no-audit --no-fund'
                                }
                            }
                        }
                    }
                    
                    stage('Build') {
                        steps {
                            nodejs(NODE_VERSION) {
                                dir('front-service') {
                                    sh 'npm run build --if-present'
                                }
                            }
                        }
                    }
                    
                    stage('Test') {
                        steps {
                            nodejs(NODE_VERSION) {
                                dir('front-service') {
                                    sh 'npm test'
                                }
                            }
                        }
                    }
                }
            }
        }
        
        stage('Build and Push Docker Image') {
            environment {
                DOCKER_HUB_USERNAME = credentials('DOCKER_HUB_USERNAME')
                DOCKER_HUB_TOKEN = credentials('DOCKER_HUB_TOKEN')
                IMAGE_NAME = 'marmevladek/devopsl-frontend'
            }
            steps {
                script {
                    def platforms = ['linux/amd64', 'linux/arm64']
                    def buildCommand = "docker buildx build " +
                                       "--platform ${platforms.join(',')} " +
                                       "--file ./front-service/Dockerfile " +
                                       "--tag ${IMAGE_NAME}:latest " +
                                       "--push ./front-service"
        
                    // Логин в Docker Hub
                    sh "echo ${DOCKER_HUB_TOKEN} | docker login -u ${DOCKER_HUB_USERNAME} --password-stdin"
        
                    // Настройка Buildx (если buildx не настроен)
                    sh '''
                        docker buildx create --use --name mybuilder || true
                        docker buildx inspect mybuilder --bootstrap
                    '''
        
                    // Сборка и пуш образа
                    sh buildCommand
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}
