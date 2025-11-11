pipeline {
    agent any
    
    tools {
        maven 'maven 3.8.4'
        jdk 'JDK-17'
    }
    
    environment {
        APP_NAME = 'employee-management-system'
        APP_VERSION = '1.0-SNAPSHOT'
        STAGING_SERVER = 'localhost'
        STAGING_PORT = '8081'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '=============================='
                echo '📥 Checking out source code...'
                echo '=============================='
                checkout scm
                sh 'git --version'
                sh 'git log -1 --oneline'
            }
        }
        
        stage('Build') {
            steps {
                echo '=============================='
                echo '🔨 Building application...'
                echo '=============================='
                sh 'mvn clean compile'
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo '=============================='
                echo '🧪 Running unit tests...'
                echo '=============================='
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    echo '✅ Test reports published'
                }
            }
        }
        
        stage('Code Coverage') {
            steps {
                echo '=============================='
                echo '📊 Generating coverage report...'
                echo '=============================='
                sh 'mvn jacoco:report'
            }
            post {
                always {
                    echo '✅ Coverage report generated'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '=============================='
                echo '📦 Packaging application...'
                echo '=============================='
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar',
                                   allowEmptyArchive: false,
                                   fingerprint: true,
                                   onlyIfSuccessful: true
                    echo '✅ Artifact archived successfully'
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'main'
            }
            steps {
                echo '=============================='
                echo '🚀 Deploying to Staging...'
                echo '=============================='
                script {
                    sh '''
                        echo "Stopping any existing instance..."
                        pkill -f employee-management-system || true
                        sleep 2
                        
                        echo "Starting application on staging..."
                        nohup java -jar target/employee-management-system.jar > staging.log 2>&1 &
                        
                        echo "Waiting for application to start..."
                        sleep 10
                        
                        echo "Verifying deployment..."
                        curl -f http://localhost:8081/api/health || exit 1
                        
                        echo "✅ Deployment successful!"
                    '''
                }
            }
        }
        
        stage('Staging Tests') {
            when {
                branch 'main'
            }
            steps {
                echo '=============================='
                echo '🔍 Running staging tests...'
                echo '=============================='
                sh '''
                    # Health check
                    curl -f http://localhost:8081/api/health
                    
                    # Verify employees endpoint
                    curl -f http://localhost:8081/api/employees
                    
                    echo "✅ All staging tests passed!"
                '''
            }
        }
        
        stage('Approval for Production') {
            when {
                branch 'production'
            }
            steps {
                echo '=============================='
                echo '⏸️  Waiting for approval...'
                echo '=============================='
                input message: 'Deploy to Production?',
                      ok: 'Deploy',
                      submitter: 'admin,manager'
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'production'
            }
            steps {
                echo '=============================='
                echo '🚀 Deploying to Production...'
                echo '=============================='
                script {
                    sh '''
                        echo "🔴 PRODUCTION DEPLOYMENT"
                        echo "Application: ${APP_NAME}"
                        echo "Version: ${APP_VERSION}"
                        echo "Build: ${BUILD_NUMBER}"
                        
                        # In real scenario, deploy to production servers
                        echo "✅ Production deployment successful!"
                    '''
                }
            }
        }
    }
    
    post {
        always {
            echo '=============================='
            echo '📊 Build Summary'
            echo '=============================='
            echo "Status: ${currentBuild.currentResult}"
            echo "Duration: ${currentBuild.durationString}"
            echo "Build Number: ${BUILD_NUMBER}"
        }
        
        success {
            echo '✅ Pipeline completed successfully!'
        }
        
        failure {
            echo '❌ Pipeline failed!'
            echo 'Check logs for details.'
        }
        
        unstable {
            echo '⚠️  Pipeline unstable!'
        }
    }
}
