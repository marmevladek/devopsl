pipeline {
    agent none
    stages {
        stage('Build and Test Matrix') {
            matrix {
                axes {
                    axis {
                        name 'NODE_VERSION'
                        values '18', '20', '22'
                    }
                }
                agent {
                    docker {
                        image "node:${NODE_VERSION}"
                        label 'docker' // если нужно, чтобы запускался на узле с докером
                        args '-u root:root' // если нужны права root
                    }
                }
                stages {
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
