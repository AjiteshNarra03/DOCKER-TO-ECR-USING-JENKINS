Launching an EC2 Instance and Installing Jenkins

Launching an EC2 Instance

Create an IAM role with the AmazonEC2ContainerRegistryFullAccess policy attached. This will allow the EC2 instance to access ECR.

Launch an EC2 instance using the Amazon Linux 2 AMI. Make sure to:

Attach the IAM role created in step 1

Add port 8080 to the security group

Attach a key pair or create a new one

Take note of the public DNS name

Connect to the instance using SSH and the key pair.

ssh -i /path/my-key-pair.pem ec2-user@ec2-198-51-100-1.compute-1.amazonaws.com

Installing Jenkins

Update the packages on the instance:

sudo yum update –y

Add the Jenkins repo:

sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo

Import the key file and upgrade:

sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key sudo yum upgrade

Install Java and Jenkins:

sudo dnf install java-17-amazon-corretto -y sudo yum install jenkins -y

Enable and start the Jenkins service:

sudo systemctl enable jenkins sudo systemctl start jenkins

Access the Jenkins web UI at http://<public_DNS>:8080 and follow the prompts to unlock Jenkins and create an admin user.

Install plugins like Amazon EC2 plugin.

Configure Jenkins to connect to EC2 by adding a new EC2 cloud. Provide the AWS credentials, key pair, and other details.

Jenkins is now ready to use EC2 agents for builds.
