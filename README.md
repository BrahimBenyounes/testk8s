# Build and Deploy Sample Application with Jenkins Pipeline to Minikube Kubernetes Cluster

## Prerequisites

Ensure you have Homebrew installed on your Mac. If not, you can install it using:

````sh
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
````

## 1. Install Java (MAC)

1. Install Java using Homebrew:

   ```sh
   brew install java11
   ```

   This will install OpenJDK 11.

2. Set JAVA_HOME Environment Variable: (Optional)

   After installation, you need to set the `JAVA_HOME` environment variable. The lines will be populated in the logs of java installation.
   The lines might look like the below examples.

   ```sh
   export PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH" >> ~/.zshrc
   export JAVA_HOME=$(/usr/libexec/java_home -v 11)
   export PATH=$JAVA_HOME/bin:$PATH
   ```


## 2. Install Jenkins LTS (MAC)

1. Install Jenkins LTS using Homebrew:

   ```sh
   brew install jenkins-lts
   ```

2. Start Jenkins:

   ```sh
   brew services start jenkins-lts
   ```

   Jenkins will start automatically and run as a background service.

3. Access Jenkins:

   Open your web browser and navigate to:

   ```
   http://localhost:8080
   ```

4. Unlock Jenkins:

   You’ll need to unlock Jenkins using the initial admin password. Find the password with:

   ```sh
   cat /Users/$(whoami)/.jenkins/secrets/initialAdminPassword
   ```

   Copy the password and paste it into the Jenkins unlock page.


## 4. Install Minikube (MAC)

1. To install the latest minikube stable release on x86-64 macOS using binary download::

   ```sh
   curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-darwin-amd64
   sudo install minikube-darwin-amd64 /usr/local/bin/minikube
   ```

2. Start Minikube:

   ```sh
   minikube start
   ```

   This will start Minikube with default settings.

3. Verify Minikube Status:

   ```sh
   minikube status
   ```


# Once you are done with you setup, Below are the steps for clean up.

```sh
minikube stop

brew services stop jenkins-lts

brew uninstall jenkins-lts

brew uninstall java11
```

# Installation on Ubuntu

## Docker
1. Update the apt package index and install packages
```
sudo apt-get update
sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
```
2. Add Docker’s official GPG key
```
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
```

3. Set up the stable repository
```
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```
4. Update the apt package index again and install Docker Engine
```
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io
```
5. Add your user to the docker group
```
sudo usermod -aG docker $USER
```
6. Apply the new group membership 
```
newgrp docker
```

## Jenkins Installation on Ubuntu
1. Update the package index
```
sudo apt update
```
2. Install OpenJDK 11
```
sudo apt install openjdk-11-jdk -y
```
3. Verify the Java installation
```
java -version
```
4. Add the Jenkins Debian repository and import the GPG key
```
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
```
5. Update the package index
```
sudo apt update
```
6. Install Jenkins LTS
```
sudo apt install jenkins -y
```
7. Start and enable the Jenkins service
```
sudo systemctl start jenkins
sudo systemctl enable jenkins
```
8. Add the Jenkins user to the Docker group
```
sudo usermod -aG docker jenkins
```
9. Restart the Jenkins service to apply the changes
```
sudo systemctl restart jenkins
```


## Conclusion

You have successfully installed Java, Jenkins LTS, and Minikube on your Mac using Homebrew. Ensure to check each tool's documentation for advanced configurations and troubleshooting.
