pipeline {
    agent none
    
    triggers {
        pollSCM('H/5 * * * *') // Проверять изменения каждые 5 минут
    }
    
    stages {
        stage('Build and Test') {
            parallel {
                stage('Build') {
                    stages {
                        stage('Build Node 18') {
                            agent {
                                docker {
                                    image 'node:18'
                                    args '-u root'
                                    reuseNode true
                                }
                            }
                            steps {
                                checkout scm
                                dir('front-service') {
                                    sh 'npm ci'
                                    sh 'npm run build --if-present'
                                }
                            }
                        }
                        stage('Build Node 20') {
                            agent {
                                docker {
                                    image 'node:20'
                                    args '-u root'
                                    reuseNode true
                                }
                            }
                            steps {
                                checkout scm
                                dir('front-service') {
                                    sh 'npm ci'
                                    sh 'npm run build --if-present'
                                }
                            }
                        }
                        stage('Build Node 22') {
                            agent {
                                docker {
                                    image 'node:22'
                                    args '-u root'
                                    reuseNode true
                                }
                            }
                            steps {
                                checkout scm
                                dir('front-service') {
                                    sh 'npm ci'
                                    sh 'npm run build --if-present'
                                }
                            }
                        }
                    }
                }
                
                stage('Test') {
                    stages {
                        stage('Test Node 18') {
                            agent {
                                docker {
                                    image 'node:18'
                                    args '-u root'
                                    reuseNode true
                                }
                            }
                            steps {
                                checkout scm
                                dir('front-service') {
                                    sh 'npm ci'
                                    sh 'npm test'
                                }
                            }
                        }
                        stage('Test Node 20') {
                            agent {
                                docker {
                                    image 'node:20'
                                    args '-u root'
                                    reuseNode true
                                }
                            }
                            steps {
                                checkout scm
                                dir('front-service') {
                                    sh 'npm ci'
                                    sh 'npm test'
                                }
                            }
                        }
                        stage('Test Node 22') {
                            agent {
                                docker {
                                    image 'node:22'
                                    args '-u root'
                                    reuseNode true
                                }
                            }
                            steps {
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
    
    post {
        always {
            cleanWs()
        }
    }
}
