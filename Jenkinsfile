pipeline {
    agent none
    
    triggers {
        pollSCM('* * * * *') // Опционально: опрашивать SCM на изменения
    }
    
    stages {
        stage('Build and Test') {
            parallel {
                stage('Build') {
                    agent {
                        docker {
                            image 'node:latest'
                            args '-u root'
                            reuseNode true
                        }
                    }
                    stages {
                        stage('Checkout') {
                            steps {
                                checkout scm
                            }
                        }
                        stage('Build Matrix') {
                            matrix {
                                axes {
                                    axis {
                                        name 'NODE_VERSION'
                                        values '18', '20', '22'
                                    }
                                }
                                agent {
                                    docker {
                                        image "node:\${NODE_VERSION}"
                                        args '-u root'
                                        reuseNode true
                                    }
                                }
                                stages {
                                    stage('Build') {
                                        steps {
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
                    agent {
                        docker {
                            image 'node:latest'
                            args '-u root'
                            reuseNode true
                        }
                    }
                    stages {
                        stage('Checkout') {
                            steps {
                                checkout scm
                            }
                        }
                        stage('Test Matrix') {
                            matrix {
                                axes {
                                    axis {
                                        name 'NODE_VERSION'
                                        values '18', '20', '22'
                                    }
                                }
                                agent {
                                    docker {
                                        image "node:\${NODE_VERSION}"
                                        args '-u root'
                                        reuseNode true
                                    }
                                }
                                stages {
                                    stage('Test') {
                                        steps {
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
