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
                sh """
                    export CODE_COVERAGE=true
                    export MUTATION_COVERAGE=false
                    make install PWD='${env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)}'
                """
            }
        }

        stage ('Analyze') {
            steps {
                // Hack: The sibling container mounts on the host and therefore the mount path needs to be relative to the host, not the parent container.
                sh """
                    export CODE_COVERAGE=true
                    export MUTATION_COVERAGE=false
                    make analyze PWD='${env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)}'
                """
            }
        }

        stage ('Containerize') {
            steps {
                sh "make containerize VERSION=${IMAGE_TAG}"
            }
        }

        stage ('Post Success') {
            when { branch 'master' }

            stages {
                stage ('Prune Host') {
                    steps {
                        build job: '../Nucleus Prune', parameters: [
                            string(name: 'HOST_URL', value: 'ec2-18-222-253-162.us-east-2.compute.amazonaws.com')
                        ]
                    }
                }

                stage ('Deploy Host') {
                    steps {
                        build job: '../Brewcraft Deploy', parameters: [
                            string(name: 'HOST_URL', value: 'ec2-18-222-253-162.us-east-2.compute.amazonaws.com'),
                            string(name: 'TARGET_UPLOAD_DIR', value: '/var/server/brewcraft'),
                            string(name: 'SOURCE_UPLOAD_DIR', value: './dist')
                        ]
                    }
                }

                stage ('Report Coverage') {
                    steps {
                        build job: '../Brewcraft Report Coverage', parameters: [
                            string(name: 'HOST_URL', value: '172.17.0.1'),
                            string(name: 'JACOCO_SOURCE_HTML_DIR', value: 'target/site/jacoco'),
                            string(name: 'PITEST_SOURCE_HTML_DIR', value: 'target/pit-reports'),
                            string(name: 'JACOCO_TARGET_HTML_DIR', value: '/home/mrishab/brewcraft/code/html/jacoco'),
                            string(name: 'PITEST_TARGET_HTML_DIR', value: '/home/mrishab/brewcraft/code/html/pit-reports'),
                            booleanParam(name: 'CODE_COVERAGE', value: true),
                            booleanParam(name: 'MUTATION_COVERAGE', value: true)
                        ]
                    }
                }
            }
        }
    }
}