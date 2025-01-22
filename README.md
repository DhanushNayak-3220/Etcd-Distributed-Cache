# Sample Project For ETCD As Distributed key-value store.

A brief description of what your project does.

## Prerequisites
- Java 17
- Spring Boot 3.0.x
- Maven or Gradle
- Any other necessary tools or frameworks

## Running the Docker Container

To deploy the application with Docker, you can run the following command to start the required etcd server container: 
- docker run -d --name etcd-server -p 2379:2379 -p 2380:2380 -e ALLOW_NONE_AUTHENTICATION=yes -e ETCD_ADVERTISE_CLIENT_URLS=http://0.0.0.0:2379 bitnami/etcd:latest

## License

- ETCD is licensed under the MIT License.

## Important Reminders

Remember to empty your cache whenever you perform an ADD, DELETE, or UPDATE operation to avoid returning stall data from the cache. This will be evident when you observe caching 

## For More Info go here https://etcd.io/