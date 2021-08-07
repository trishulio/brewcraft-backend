pipeline {
    agent {
        label 'docker'
    }

    stages {
        stage ('Checkout') {
            steps {
                checkout scm
            }
        }

        stage ('Setup Job') {
            steps {
                script {
                    def commitId = sh(script: 'git rev-parse HEAD', returnStdout: true)
                    IMAGE_TAG = "${env.BRANCH_NAME}_${commitId}"
                }
            }
        }

        stage ('Install') {
            steps {
                // Hack: The sibling container mounts on the host and therefore the mount path needs to be relative to the host, not the parent container.
                sh "make install PWD=${env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)}"
            }
        }

        stage('Containerize') {
            steps {
                sh "make containerize VERSION=${IMAGE_TAG}"
            }
        }
    }
}