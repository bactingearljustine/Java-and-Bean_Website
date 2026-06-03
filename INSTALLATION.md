# INSTALLATION.md

# 🧪 Installation Guide

This guide explains how to install and configure the required software for the Java & Bean Café Website.

---

# 📋 Prerequisites

Before running the project, ensure the following software is installed:

* Oracle VirtualBox
* Ubuntu Server 20.04/22.04
* OpenJDK 17
* Apache Tomcat 10
* MariaDB/MySQL
* Git

---

# 🖥️ Step 1: Install Oracle VirtualBox

1. Download Oracle VirtualBox
2. Install the application on your host machine
3. Download the Ubuntu Server ISO file

Recommended Ubuntu Version:

* Ubuntu Server 22.04 LTS

---

# 🐧 Step 2: Create Ubuntu Server VM

Configure the virtual machine with the following settings:

| Setting | Recommended Value |
| ------- | ----------------- |
| RAM     | 2GB or higher     |
| Storage | 20GB              |
| Type    | Linux             |
| Version | Ubuntu (64-bit)   |

Attach the Ubuntu ISO file and install Ubuntu Server.

---

# 🔧 Step 3: Update Ubuntu Packages

```bash
sudo apt update
sudo apt upgrade -y
```

---

# ☕ Step 4: Install Java JDK

```bash
sudo apt install openjdk-17-jdk -y
```

Verify installation:

```bash
java -version
```

---

# 🌐 Step 5: Install Apache Tomcat 10

```bash
sudo apt install tomcat10 -y
```

Check Tomcat status:

```bash
sudo systemctl status tomcat10
```

---

# 🛢️ Step 6: Install MariaDB

```bash
sudo apt install mariadb-server -y
```

Start the database service:

```bash
sudo systemctl start mysql
sudo systemctl enable mysql
```

---

# 📦 Step 7: Install Git

```bash
sudo apt install git -y
```

Verify installation:

```bash
git --version
```

---

# 📂 Step 8: Clone Repository

```bash
git clone https://github.com/bactingearljustine/Java-and-Bean_Website.git
```

Navigate to the project folder:

```bash
cd Java-and-Bean_Website
```

---

# ✅ Installation Complete

# 🗄️ Database Setup Guide

This guide explains how to configure the MariaDB database for the Java & Bean Café Website.

---

# ▶️ Step 1: Access MariaDB

```bash
mysql -u root -p
```
---

# 🏗️ Step 2: Create Database

```bash
CREATE DATABASE cafeDB;
USE cafeDB;
```
---

# 👤 Step 3: Create Users Table

```bash
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);
```
---

# 🍽️ Step 4: Create Menu Table

```bash
CREATE TABLE menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100),
    price DOUBLE
);
```

---

# 🧾 Step 5: Create Orders Table

```bash
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total DOUBLE
);
```
---

# 📦 Step 6: Create Order Items Table

```bash
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    menu_id INT,
    quantity INT
);
```
---

# 🔗 Step 7: Configure Database Connection

```bash
Example DBConnection.java:

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("org.mariadb.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/cafeDB",
                "root",
                "YOUR_PASSWORD"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
```
---

# ⚙️ Step 8: Verify JDBC Driver

```bash
ls /usr/share/java/ | grep mariadb

Expected output:

text
mariadb-java-client.jar
```
---

# 🔄 Step 9: Restart Tomcat

```bash
sudo systemctl restart tomcat10
```

---

# ✅ Database Configuration Complete



    
