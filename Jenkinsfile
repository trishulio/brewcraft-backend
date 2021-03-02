pipeline {
    agent {
        label 'docker'
    }

    stages {
        stage ('Configure') {
            steps {
                script {
                    def commitId = sh(script: 'git rev-parse HEAD', returnStdout: true)
                    IMAGE_TAG = "${env.BRANCH_NAME}_${commitId}"
                }
            }
        }

        stage ('Checkout') {
            steps {
                checkout scm
            }
        }

        stage ('Install') {
            steps {
                // Hack: The sibling container mounts on the host and therefore the mount path needs to be relative to the host, not the parent container.
                sh "make install PWD=${env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)}"
            }
        }

        stage('Distribute') {
            when {
                branch 'master'
            }

            stages {
                stage ('Containerize') {
                    steps {
                        sh "make containerize VERSION=${IMAGE_TAG}"
                    }
                }

                stage ('Pack') {
                    steps {
                        sh "make pack VERSION=${IMAGE_TAG}"
                    }
                }

                stage ('Deploy') {
                    environment {
                        SSH_CREDS = credentials('jaas_user')
                    }

                    stages {
                        stage ('Setup SSH') {
                            steps {
                                sh """
                                    mkdir -p ~/.ssh/
                                    ssh-keyscan -H ${env.APPSERVER_IP} > ~/.ssh/known_hosts
                                """
                            }
                        }

                        stage ('Upload') {
                            steps {
                                sh """
                                    make upload ID_KEY=${SSH_CREDS} USERNAME=${SSH_CREDS_USR} HOST=${env.APPSERVER_IP}
                                """
                            }
                        }

                        stage ('Start') {
                            steps {
                                sh """
                                    make deploy ID_KEY=${SSH_CREDS} USERNAME=${SSH_CREDS_USR} HOST=${env.APPSERVER_IP} VERSION=${IMAGE_TAG}
                                """
                            }
                        }
                    }
                }
            }
        }
    }
}