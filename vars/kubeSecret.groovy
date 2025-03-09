def call(String secret_type, String secret_name, String cert_password) {
    // Debug: Print the values of the variables
    echo "secret_type: ${secret_type}"
    echo "secret_name: ${secret_name}"
    echo "cert_password: ${cert_password}"
    
    // Check if the secret already exists
    def secretCheck = sh(script: "kubectl get secret ${secret_name} --ignore-not-found", returnStdout: true).trim()
    echo "Secret check output: '${secretCheck}'"
    
    def secretExists = secretCheck ? true : false
    echo "secretExists: ${secretExists}"
    
    if (!secretExists) {
        // If the secret does not exist, create it
        sh(script: """
            kubectl create secret ${secret_type} ${secret_name} --from-literal=password=${cert_password}
        """)
        echo "Secret ${secret_name} created."
    } else {
        echo "Secret ${secret_name} already exists. Skipping creation."
    }
}
