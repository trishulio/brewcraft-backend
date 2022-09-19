pipeline {
    agent {
        label 'docker'
    }

    options {
        disableConcurrentBuilds()
        quietPeriod(3 * 60) // 3 minutes
    }

    environment {
        SONARQUBE = credentials('brewcraft_sonarqube')
        HOST_WORKSPACE = env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)
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
                    def config = [
                        // Production
                        "release": [
                            "awsCredsId": "AWS_CREDS_PRODUCTION",
                            "awsAccountId": "850645470889",
                            "awsRegion": "ca-central-1",
                        ],
                        // Staging
                        "master": [
                            "awsCredsId": "AWS_CREDS_STAGING",
                            "awsAccountId": "346608161962",
                            "awsRegion": "ca-central-1",
                        ],
                        // Default
                        "develop": [
                            "awsCredsId": "NA", // Not applicable for non-deployment builds
                            "awsAccountId": "NA",
                            "awsRegion": "NA",
                        ]
                    ];

                    def configKey = ['master', 'release'].contains(env.BRANCH_NAME) ? env.BRANCH_NAME : 'develop';

                    AWS_CREDS_ID = config[configKey]['awsCredsId'];
                    AWS_ACCOUNT_ID = config[configKey]['awsAccountId'];
                    AWS_REGION = config[configKey]['awsRegion'];

                    def commitId = sh(script: 'git rev-parse HEAD', returnStdout: true);
                    IMAGE_TAG = "${env.BRANCH_NAME}_${commitId}".trim();

                    SONARQUBE_URL="https://sonarqube.cloudville.me";
                    SONARQUBE_REPORTING = ['master', 'release'].contains(env.BRANCH_NAME) ? 'true' : 'false';
                }
            }
        }

        stage ('Install') {
            steps {
                // Hack: The sibling container mounts on the host and therefore the mount path needs to be relative to the host, not the parent container.
                sh """
                    export MUTATION_COVERAGE=false
                    export CODE_COVERAGE=true
                    export SONARQUBE=${SONARQUBE_REPORTING}
                    export SONARQUBE_HOST_URL=${SONARQUBE_URL}
                    export SONARQUBE_PROJECT_KEY=$SONARQUBE_USR
                    export SONARQUBE_LOGIN=$SONARQUBE_PSW

                    make install PWD='${HOST_WORKSPACE}'
                """
            }
        }

        stage ('Containerize') {
            steps {
                sh "make containerize AWS_ACCOUNT_ID=$AWS_ACCOUNT_ID AWS_REGION=$AWS_REGION VERSION=$IMAGE_TAG"
            }
        }

        stage ('Post Build') {
            when {
                anyOf {
                    branch 'master';
                    // production is not released by default
                }
            }

            stages {
                stage ('Publish') {
                    environment {
                        AWS_CREDS = credentials("${AWS_CREDS_ID}")
                    }

                    steps {
                        sh """
                            export AWS_ACCESS_KEY_ID=$AWS_CREDS_USR
                            export AWS_SECRET_ACCESS_KEY=$AWS_CREDS_PSW

                            aws ecr get-login-password --region ${AWS_REGION} | docker login -u AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/brewcraft-backend

                            make publish AWS_ACCOUNT_ID=$AWS_ACCOUNT_ID AWS_REGION=$AWS_REGION VERSION=$IMAGE_TAG
                        """
                    }
                }

                stage ('Deploy') {
                    steps {
                        build job: '../Backend-Deploy', parameters: [
                            string(name: 'initialDelay', value: '0'),
                            string(name: 'version', value: IMAGE_TAG),
                            string(name: 'environment', value: 'staging'),
                            booleanParam(name: 'rollingUpdate', value: true),
                        ]
                    }
                }
            }
        }
    }
}