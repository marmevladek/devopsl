pipeline {
    agent any
    
    options {
        skipDefaultCheckout true // Отключаем автоматический checkout
    }
    
    triggers {
        pollSCM('H/5 * * * *')
    }
    
    tools {
        nodejs 'node18' 
        nodejs 'node20' 
        nodejs 'node22'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        credentialsId: 'jenkins', // Укажите ваш credentials ID
                        url: 'https://github.com/maximAdmakin/devopsL.git'
                    ]]
                ])
            }
        }
        
        stage('Build and Test') {
            parallel {
                stage('Build') {
                    matrix {
                        axes {
                            axis {
                                name 'NODE_VERSION'
                                values 'node18', 'node20', 'node22'
                            }
                        }
                        stages {
                            stage('Build') {
                                steps {
                                    script {
                                        nodejs(NODE_VERSION) {
                                            dir('front-service') {
                                                sh 'npm ci'
                                                sh 'npm run build --if-present'
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                stage('Test') {
                    matrix {
                        axes {
                            axis {
                                name 'NODE_VERSION'
                                values 'node18', 'node20', 'node22'
                            }
                        }
                        stages {
                            stage('Test') {
                                steps {
                                    script {
                                        nodejs(NODE_VERSION) {
                                            dir('front-service') {
                                                sh 'npm ci'
                                                sh 'npm test'
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
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
