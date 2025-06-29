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
        
    stage('Docker Build and Push') {
        agent {
            docker {
                image 'docker:dind'
                args '--privileged --network=host -v /var/run/docker.sock:/var/run/docker.sock'
                reuseNode true
            }
        }
        environment {
            DOCKER_HUB_USERNAME = credentials('DOCKER_HUB_USERNAME')
            DOCKER_HUB_TOKEN = credentials('DOCKER_HUB_TOKEN')
        }
        steps {
            script {
                // Безопасный способ передачи credentials
                withCredentials([usernamePassword(
                    credentialsId: 'DOCKER_HUB_CREDS',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    // Настройка Buildx
                    sh '''
                    docker buildx create --use --name mybuilder || true
                    docker buildx inspect --bootstrap || true
                    '''
                    
                    // Логин в Docker Hub (безопасный способ)
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    '''
                    
                    // Сборка и пуш образа
                    dir('front-service') {
                        sh '''
                        docker buildx build \
                            --platform linux/amd64,linux/arm64 \
                            -t marmevladek/devopsl-frontend:latest \
                            --push \
                            .
                        '''
                    }
                    
                    // Выход из Docker Hub
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
