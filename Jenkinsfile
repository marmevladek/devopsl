pipeline {
    agent any
    
    triggers {
        pollSCM('H/5 * * * *')
    }
    
    tools {
        nodejs 'node18'
        nodejs 'node20'
        nodejs 'node22'
    }
    
    stages {
        stage('Build and Test') {
            parallel {
                stage('Build') {
                    parallel {
                        stage('Build Node 18') {
                            agent { label 'any' }
                            options { skipDefaultCheckout() }
                            steps {
                                ws('devopsL_main_node18') {
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
                            agent { label 'any' }
                            options { skipDefaultCheckout() }
                            steps {
                                ws('devopsL_main_node20') {
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
                            agent { label 'any' }
                            options { skipDefaultCheckout() }
                            steps {
                                ws('devopsL_main_node22') {
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
                    parallel {
                        stage('Test Node 18') {
                            agent { label 'any' }
                            options { skipDefaultCheckout() }
                            steps {
                                ws('devopsL_main_node18') {
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
                            agent { label 'any' }
                            options { skipDefaultCheckout() }
                            steps {
                                ws('devopsL_main_node20') {
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
                            agent { label 'any' }
                            options { skipDefaultCheckout() }
                            steps {
                                ws('devopsL_main_node22') {
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
