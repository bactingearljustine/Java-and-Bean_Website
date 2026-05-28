# Installation Guide
This document provides a step-by-step guide for installing and configuring the required software, tools, and dependencies needed to run the Java & Bean Café Website successfully. It includes the setup of the Ubuntu Server virtual machine, Java Development Kit (JDK), Apache Tomcat, MariaDB database server, and other essential components used throughout the project.
---

## 📌Installation


---

## 🚀 Deployment
Proceed to:

* DEPLOYMENT.md
* DATABASE.md



:::writing{variant="document" id="52716"}
# DEPLOYMENT.md

# 🌐 Deployment Guide

This guide explains how to deploy the Java & Bean Café Website using Apache Tomcat on Ubuntu Server.

---

# 📂 Step 1: Setup Shared Folder

Inside VirtualBox:

1. Open VM Settings
2. Navigate to Shared Folders
3. Add the project folder
4. Enable:
   - Auto-mount
   - Make Permanent

---

# 🔐 Step 2: Fix Shared Folder Permissions
```bash
sudo usermod -aG vboxsf $USER
sudo reboot
````

After reboot:

```bash
cd /media
ls
```

Expected output:

```text
sf_ver2.0
```

---

# 📦 Step 3: Copy Project to Tomcat Directory

```bash
sudo cp -r /media/sf_ver2.0 /var/lib/tomcat10/webapps/myproject
```

---

# 📂 Step 4: Navigate to Source Folder

```bash
cd /var/lib/tomcat10/webapps/myproject/src
```

---

# 🧱 Step 5: Compile Java Servlets

Create classes directory:

```bash
mkdir -p ../WEB-INF/classes
```

Compile servlet files:

```bash
javac -cp .:/usr/share/java/jakarta-servlet-api.jar -d ../WEB-INF/classes *.java
```

---

# ▶️ Step 6: Restart Apache Tomcat

```bash
sudo systemctl restart tomcat10
```

Check Tomcat status:

```bash
sudo systemctl status tomcat10
```

---

# 🌍 Step 7: Access the Web Application

Inside Ubuntu VM:

```text
http://localhost:8080/myproject
```

From another device:

```text
http://<VM-IP>:8080/myproject
```

Check VM IP address:

```bash
ip a
```

---

# ✅ Deployment Complete

---

## 📌Database


---
