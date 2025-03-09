def call(String imageName, String appName) {
    sh """
       sed -i '' 's|<docker-image>|$imageName:${env.BUILD_ID}|g' deployment.yml 
       sed -i '' 's|my-registry-secret|$appName|g' deployment.yml
       sed -i '' 's|sample-app|$appName|g' deployment.yml
       sed -i '' 's|sample-app|$appName|g' service.yml
       """

    // Apply deployment and service files
       sh 'kubectl apply -f deployment.yml'
       sh 'kubectl apply -f service.yml'
}