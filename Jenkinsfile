pipeline {
    agent any

    // Определяем параметры для matrix
    environment {
        FRONT_SERVICE_DIR = 'front-service'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test Matrix') {
            parallel {
                stage('Node 18') {
                    agent {
                        // Используем NodeJS tool с именем node18
                        nodejs 'node18'
                    }
                    stages {
                        stage('Install Dependencies') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm ci'
                                }
                            }
                        }
                        stage('Build') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm run build --if-present'
                                }
                            }
                        }
                        stage('Test') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm test'
                                }
                            }
                        }
                    }
                }
                stage('Node 20') {
                    agent {
                        nodejs 'node20'
                    }
                    stages {
                        stage('Install Dependencies') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm ci'
                                }
                            }
                        }
                        stage('Build') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm run build --if-present'
                                }
                            }
                        }
                        stage('Test') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm test'
                                }
                            }
                        }
                    }
                }
                stage('Node 22') {
                    agent {
                        nodejs 'node22'
                    }
                    stages {
                        stage('Install Dependencies') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm ci'
                                }
                            }
                        }
                        stage('Build') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
                                    sh 'npm run build --if-present'
                                }
                            }
                        }
                        stage('Test') {
                            steps {
                                dir(env.FRONT_SERVICE_DIR) {
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
