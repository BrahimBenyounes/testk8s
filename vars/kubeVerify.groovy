def call(String appName) {
    // Check deployment status
    def deployStatus = sh(script: "kubectl rollout status deployment/${appName}-deployment", returnStatus: true)
    if (deployStatus != 0) {
        echo "Deployment failed or is not completed. Triggering rollback."
        sh "kubectl rollout undo deployment/${appName}-deployment"
        error "Deployment failed. Rolled back to the previous version."
    }

    // Check if the pods are running
    def podStatus = sh(script: "kubectl get pods -l app=${appName} -o jsonpath='{.items[0].status.phase}'", returnStdout: true).trim()
    if (podStatus != 'Running') {
        echo "Pods are not in Running state. Triggering rollback."
        sh "kubectl rollout undo deployment/${appName}-deployment"
        error "Pods are not in Running state. Rolled back to the previous version."
    }

    // Check if the service is available
    def serviceStatus = sh(script: "kubectl get services ${appName}-service -o jsonpath='{.spec.ports[0].nodePort}'", returnStdout: true).trim()
    if (!serviceStatus) {
        echo "Service is not available. Triggering rollback."
        sh "kubectl rollout undo deployment/${appName}-deployment"
        error "Service is not available. Rolled back to the previous version."
    }

    echo "Deployment and service are successfully created and running."
}
