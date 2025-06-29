pipeline {
    agent any

    stages {
        stage('Build and Test Matrix') {
            matrix {
                axes {
                    axis {
                        name 'NODE_VERSION'
                        values '18', '20', '22'
                    }
                }
                stages {
                    stage('Setup Node') {
                        steps {
                            echo "Using Node.js version ${NODE_VERSION}"
                            // Пример с nvm (нужно, чтобы nvm был установлен на агенте)
                            sh """
                                source ~/.nvm/nvm.sh
                                nvm install ${NODE_VERSION}
                                nvm use ${NODE_VERSION}
                                node -v
                            """
                        }
                    }
                    stage('Install Dependencies') {
                        steps {
                            dir('front-service') {
                                sh 'npm ci'
                            }
                        }
                    }
                    stage('Build') {
                        steps {
                            dir('front-service') {
                                sh 'npm run build --if-present'
                            }
                        }
                    }
                    stage('Test') {
                        steps {
                            dir('front-service') {
                                sh 'npm test'
                            }
                        }
                    }
                }
            }
        }
    }
}
