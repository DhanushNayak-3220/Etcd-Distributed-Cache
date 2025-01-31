# **ETCD as a Distributed Key-Value Store**

## **Overview**  
This project demonstrates how to use **ETCD** as a distributed key-value store in a **Spring Boot** application. ETCD ensures strong consistency, making it ideal for service discovery, configuration management, and distributed locking.

---

## **Prerequisites**  
Ensure you have the following installed before proceeding:  
✅ **Java 17**  
✅ **Spring Boot 3.0.x**  
✅ **Maven** or **Gradle**  
✅ **Docker** (for running the ETCD server)  

---

## **Running the ETCD Server with Docker**  

To quickly set up an ETCD server, run the following command:  

```sh
docker run -d --name etcd-server \
  -p 2379:2379 -p 2380:2380 \
  -e ALLOW_NONE_AUTHENTICATION=yes \
  -e ETCD_ADVERTISE_CLIENT_URLS=http://0.0.0.0:2379 \
  bitnami/etcd:latest
```

This will start an ETCD server container that listens on **port 2379** for client requests.

---

## **⚡ Important Caching Reminder**  
When performing **ADD, DELETE, or UPDATE** operations, always **clear your cache** to prevent stale data from being returned. Cached values may not reflect the latest changes immediately, leading to inconsistencies.

---

## **🔗 Resources**  
🔹 **Official ETCD Docs**: [etcd.io](https://etcd.io/)  
🔹 **Spring Boot + ETCD Guide** *(Coming Soon!)*  

🚀 **Get started now and build scalable distributed applications with ETCD!**
