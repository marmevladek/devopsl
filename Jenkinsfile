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
        
        stage('docker') {
            agent any
            environment {
                DOCKER_USER = "${env.DOCKER_HUB_USERNAME}"
                DOCKER_TOKEN = "${env.DOCKER_HUB_TOKEN}"
            }
            steps {
                checkout scm
        
                // Проверка доступности Docker
                sh 'docker --version'
        
                // Настройка Buildx (с обработкой ошибок)
                sh '''
                    docker buildx create --use --name mybuilder || true
                    docker buildx inspect --bootstrap
                '''
        
                // Логин в Docker Hub
                sh '''
                    echo "$DOCKER_TOKEN" | docker login -u "$DOCKER_USER" --password-stdin
                '''
        
                // Сборка и загрузка образа
                sh '''
                    docker buildx build \
                        --push \
                        --platform linux/amd64,linux/arm64 \
                        -t marmevladek/devopsl-frontend:latest \
                        -f ./front-service/Dockerfile \
                        ./front-service
                '''
            }
            post {
                always {
                    sh 'docker logout'
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
