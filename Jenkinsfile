pipeline {
    agent any
    
    triggers {
        pollSCM('H/5 * * * *') // Проверять изменения каждые 5 минут
    }
    
    tools {
        nodejs 'node18' // Предварительно нужно настроить NodeJS в Global Tool Configuration
        nodejs 'node20' // Можно добавить несколько версий Node.js
        nodejs 'node22'
    }
    
    stages {
        stage('Build and Test') {
            parallel {
                stage('Build') {
                    stages {
                        stage('Build Node 18') {
                            steps {
                                script {
                                    nodejs('node18') {
                                        checkout scm
                                        dir('front-service') {
                                            sh 'npm ci'
                                            sh 'npm run build --if-present'
                                        }
                                    }
                                }
                            }
                        }
                        stage('Build Node 20') {
                            steps {
                                script {
                                    nodejs('node20') {
                                        checkout scm
                                        dir('front-service') {
                                            sh 'npm ci'
                                            sh 'npm run build --if-present'
                                        }
                                    }
                                }
                            }
                        }
                        stage('Build Node 22') {
                            steps {
                                script {
                                    nodejs('node22') {
                                        checkout scm
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
                
                stage('Test') {
                    stages {
                        stage('Test Node 18') {
                            steps {
                                script {
                                    nodejs('node18') {
                                        checkout scm
                                        dir('front-service') {
                                            sh 'npm ci'
                                            sh 'npm test'
                                        }
                                    }
                                }
                            }
                        }
                        stage('Test Node 20') {
                            steps {
                                script {
                                    nodejs('node20') {
                                        checkout scm
                                        dir('front-service') {
                                            sh 'npm ci'
                                            sh 'npm test'
                                        }
                                    }
                                }
                            }
                        }
                        stage('Test Node 22') {
                            steps {
                                script {
                                    nodejs('node22') {
                                        checkout scm
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
    
    post {
        always {
            cleanWs()
        }
    }
}
