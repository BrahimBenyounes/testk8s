def call(String imageName, String buildArgs = "") {
        def dockerImage = docker.build("${imageName}:${env.BUILD_ID}", buildArgs + " .")
        return dockerImage
    }

