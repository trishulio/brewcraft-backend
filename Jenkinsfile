pipeline {
    agent {
        label 'docker'
    }

    environment {
        SONARQUBE = credentials('brewcraft_sonarqube')
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
                            "kubeConfigId": "KUBE_CONFIG_PRODUCTION",
                            "awsAccountId": "850645470889",
                            "awsRegion": "ca-central-1",
                            "namespace": "production",
                            "valuesFile": "values-production.yml"
                        ],
                        // Staging
                        "master": [
                            "awsCredsId": "AWS_CREDS_STAGING",
                            "kubeConfigId": "KUBE_CONFIG_STAGING",
                            "awsAccountId": "346608161962",
                            "awsRegion": "ca-central-1",
                            "namespace": "staging",
                            "valuesFile": "values-staging.yml"
                        ],
                        // Default
                        "develop": [
                            "awsCredsId": "144571613969", // Not applicable for non-deployment builds
                            "kubeConfigId": "NA", // Not applicable for non-deployment builds
                            "awsAccountId": "144571613969",
                            "awsRegion": "ca-central-1",
                            "namespace": "local",
                            "valuesFile": "values-development.yml"
                        ]
                    ]

                    def configKey = ['master', 'release'].contains(env.BRANCH_NAME) ? env.BRANCH_NAME : 'develop'

                    AWS_CREDS_ID = config[configKey]['awsCredsId']
                    KUBE_CREDS_ID = config[configKey]['kubeConfigId']
                    AWS_ACCOUNT_ID = config[configKey]['awsAccountId']
                    AWS_REGION = config[configKey]['awsRegion']
                    NAMESPACE = config[configKey]['namespace']
                    VALUES_FILE = config[configKey]['valuesFile']

                    def commitId = sh(script: 'git rev-parse HEAD', returnStdout: true)
                    IMAGE_TAG = "${env.BRANCH_NAME}_${commitId}"

                    SONARQUBE_URL="https://sonarqube.cloudville.me"
                    SONARQUBE_REPORTING = ['master', 'release'].contains(env.BRANCH_NAME) ? 'true' : 'false'
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
                    make install PWD='${env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)}'
                """
            }
        }

        stage ('Containerize') {
            steps {
                sh "make containerize AWS_ACCOUNT_ID=$AWS_ACCOUNT_ID AWS_REGION=$AWS_REGION VERSION=$IMAGE_TAG"
            }
        }

        stage ('Post Success') {
            when {
                anyOf {
                    branch 'master';
                    branch 'release';
                }
            }

            environment {
                AWS_CREDS = credentials("${AWS_CREDS_ID}")
                KUBE_CREDS = credentials("${KUBE_CREDS_ID}")
            }

            stages {
                stage ('Publish') {
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
                        // Hack: The sibling container mounts on the host and therefore the mount path needs to be relative to the host, not the parent container. PWD and HOME are manipulated to be relative to the host.
                        sh """
                            mkdir -p $WORKSPACE/.kube
                            cp $KUBE_CREDS $WORKSPACE/.kube/config
                            make deploy PWD='${env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)}' HOME='${env.WORKSPACE.replaceFirst(env.WORKSPACE_HOME, env.HOST_WORKSPACE_HOME)}' VALUES_FILE=${VALUES_FILE} NAMESPACE=${NAMESPACE} VERSION=${IMAGE_TAG} HELM="docker-compose -f ../docker-compose-helm.yml run --rm -T helm"
                        """
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