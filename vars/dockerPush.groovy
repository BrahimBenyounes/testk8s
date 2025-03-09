def call(String user, String password, String imageName, String buildId) {
    sh """
        echo $password | docker login -u $user --password-stdin
        docker push $imageName:$buildId
    """
}