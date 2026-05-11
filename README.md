# ☕ Java & Bean Café Website

A Java Web Application deployed using Apache Tomcat on Ubuntu Server (VirtualBox)

---

## 📌 Project Overview

The *Java & Bean Café Website* is a dynamic Java web application that simulates an online café system where users can browse menu items, view products, and interact with the platform.

This project demonstrates the deployment of a Java-based web application using *Apache Tomcat* on an *Ubuntu Server running in a VirtualBox virtual machine*. It highlights server-side application hosting, shared folder integration, and web application deployment in a controlled virtual environment.

---

## 🚀 Features

- 📋 Dynamic café menu display  
- 🛒 Product browsing interface  
- 🔐 Backend design using Java (Servlets/JSP)  
- 🌐 Hosted inside Ubuntu VM via Apache Tomcat  
- 🗄️ Database connectivity (MySQL)  
- 🧑‍💼 Optional admin functionalities  

---

## 🖥️ System Architecture

### Frontend
- HTML, CSS, JavaScript  

### Backend
- Java Servlets & JSP (deployed on Apache Tomcat)  

### Database
- MySQL   

### Environment
- Ubuntu Server (VirtualBox VM)
- Apache Tomcat (Java Web Server)  

---

## 🧰 Technologies Used

- ☕ Java (JDK 8 or higher)  
- 🐧 Ubuntu Server  
- 📦 VirtualBox  
- 🛢️ MySQL  
- 💻 VS Code   
- 🔧 Git & GitHub  

---

## 📂 Project Structure

```
java-bean-cafe/
│── src/
│── webapp/ or WebContent/
│── WEB-INF/
│   └── web.xml
│── lib/
│── css/
│── js/
│── images/
│── database/
│── README.md
```

---

## 🧪 VM Setup & Deployment Guide (Ubuntu + Shared Folder)

### 🖥️ Step 1: Install Virtual Machine

1. Download and install VirtualBox  
2. Download Ubuntu Server ISO (20.04 or 22.04 LTS recommended)

---

### 🐧 Step 2: Create Ubuntu Server VM

1. Open VirtualBox → Click New  
2. Name: Ubuntu Server  
3. Type: Linux  
4. Version: Ubuntu (64-bit)  
5. RAM: at least 2GB  
6. Storage: 20GB (VDI, dynamically allocated)  

Then:

- Go to Settings → Storage  
- Attach Ubuntu ISO  

Start the VM and install Ubuntu:

- Choose Install Ubuntu Server  
- Set username, password, hostname  
- Install OpenSSH server  

Reboot after installation.

---

### 🔧 Step 3: Update System

```bash
sudo apt update
sudo apt upgrade -y
```

---

## 🌐 Hosting the Website in Ubuntu (Shared Folder Method)

### 📂 Step 4: Setup Shared Folder

1. Power off VM  
2. Go to Settings → Shared Folders  
3. Add:
   - Folder Path: your project folder  
   - Folder Name: ver2.0  
   - ✔ Auto-mount  
   - ✔ Make Permanent  
4. Start VM  

---

### 📁 Step 5: Access Shared Folder

```bash
cd /media
ls
```

Expected output:

```
sf_ver2.0
```

---

### ⚠️ If Permission is Denied

```bash
sudo usermod -aG vboxsf $USER
sudo reboot
```

After reboot:

```bash
cd /media/sf_ver2.0
ls
```

---

### 📦 Step 6: Access Website Files

```bash
cd /media/sf_ver2.0
ls
```

---

### ▶️ Step 7: Run Website (Python Server)

```bash
python3 -m http.server 8000
```

---

### 🌍 Step 8: Open in Browser

Inside VM:

```
http://localhost:8000
```

From host machine:

```
http://<VM-IP>:8000
```

Check IP:

```bash
ip a
```
---
# ☕ Apache Tomcat 10 + Java Setup & Deployment (Ubuntu)
This guide explains how to install Java, set up Apache Tomcat 10, compile Java Servlet files, and deploy a web application using a shared folder in Ubuntu.

---
### ☕ Step 1: Install Java (Required for Tomcat)
```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version
```
---

### 📥 Step 2: Install Apache Tomcat 10
```bash
sudo apt install tomcat10 -y
```
---

### 📦 Step 3: Move Project to Tomcat Directory
```bash
sudo cp -r /media/sf_ver2.0 /var/lib/tomcat10/webapps/myproject
```
---

### 📂 Step 4: Go to Project Source Folder (Inside Tomcat)
```bash
cd /var/lib/tomcat10/webapps/myproject/src
```
---

### 🧱 Step 5: Compile Java Servlet Files
```bash
javac -cp .:/usr/share/java/jakarta-servlet-api.jar -d ../WEB-INF/classes *.java
```
---

### 📁 Step 6: Ensure Proper Directory Structure
```
mkdir -p ../WEB-INF/classes
```
---

### ▶️ Step 7: Start and Enable Tomcat
```bash
sudo systemctl start tomcat10
sudo systemctl enable tomcat10
sudo systemctl status tomcat10
```
---

### 🔄 Step 8: Restart Tomcat
```bash
sudo systemctl restart tomcat10
```
### 🌐 Step 9: Access the Web Application
```bash
http://<VM-IP>:8080/myproject
```
or
```bash
http://localhost:8080/myproject
```
---
# 🗄️ Database Setup and Connection

### 📌 1. Installing MariaDB/MySQL

The database management system used for the project was MariaDB, which served as the backend database for storing user accounts, menu information, and order records.

### 💻 Installation

```bash id="h1"
sudo apt update
sudo apt install mariadb-server -y
```

---

### ▶️ 2. Starting the Database Service

After installation, the MariaDB service was started to enable database operations.

```bash id="h2"
sudo systemctl start mysql
```

To verify whether the service was running successfully:

```bash id="h3"
sudo systemctl status mysql
```

---

### 🔍 3. Verifying the MariaDB JDBC Driver

The MariaDB JDBC driver was required to establish communication between the Java Servlets and the MariaDB database.
To verify the presence of the JDBC driver:

```bash id="h4"
ls /usr/share/java/ | grep mariadb
```

### ✅ Expected Output

```text id="h5"
mariadb-java-client.jar
```
---
# 🔐 4. Accessing the Database

The MariaDB server was accessed using the root account.

```bash id="h6"
mysql -u root -p
```
---

# 🏗️ 5. Creating the Project Database

A dedicated database named cafeDB was created for the web application.

```sql id="h7"
CREATE DATABASE cafeDB;
```
To select the database for use:

```sql id="h8"
USE cafeDB;
```
---

# 📋 6. Creating the Required Database Tables

## 👤 Users Table

The users table stored account information for registered users.

```sql id="h9"
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);
```
---

## 🍽️ Menu Table

The menu table stored available food and beverage items.
```
sql id="h10"
CREATE TABLE menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100),
    price DOUBLE
);
```
---

## 🧾 Orders Table

The orders table stored customer order records.
```
sql id="h11"
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total DOUBLE
);
```
---

## 📦 Order Items Table

The order_items table stored individual menu items included in each order.

```sql id="h12"
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    menu_id INT,
    quantity INT
);
```
# 🔗 7. Establishing Database Connectivity

## 📄 DBConnection.java

The `DBConnection.java` file was responsible for creating a connection between the Java web application and the MariaDB database.

```java id="h13"
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

# ⚙️ 8. JDBC Driver Configuration and Compilation

To enable Java Servlets to communicate with MariaDB, the JDBC driver `.jar` file needed to be included during compilation.

### 📁 Driver Location

```text id="h14"
/usr/share/java/mariadb-java-client.jar
```

### 💻 Compilation Command

```bash id="h15"
sudo javac -cp ".:/usr/share/tomcat10/lib/*:/usr/share/java/mariadb-java-client.jar" -d /var/lib/tomcat10/webapps/myproject/WEB-INF/classes *.java
```

### 🔄 Restart Tomcat

```bash id="h16"
sudo systemctl restart tomcat10
```

---

### 📸 System Output

### 🏠 Homepage
![Homepage](images/dashboard.png)

### 🔐 Login Page
![Login](images/login.png)

### 📝 Registration Page
![Registration](images/register.png)

### 📋 Menu Page
**Menu View 1**
![Menu 1](images/menu.png)

**Menu View 2**
![Menu 2](images/menu2.png)

### 🛒 Cart
![Cart](images/cart.png)

### 🧾 Cart with Orders
![Cart with Orders](images/cart2.png)

### 🌐 Application Running in Browser (Ubuntu)
![Running](images/vm_running.png)
---

## 🌍 Cross-Device Testing

The system was successfully tested across multiple devices to ensure responsiveness and accessibility:

**📱iPhone (Safari)**
- ![📱 iPhone (Safari)](images/safari.png)

**🤖 Android (Chrome)**
- ![🤖 Android (Chrome)](images/android.png)

**💻 Laptop (Browser)**
- ![💻 Laptop (Browser)](images/other_laptop.png)

All devices were able to access the system using the VM IP address: (http://192.168.100.77:8080/myproject)

This confirms proper network configuration and server deployment.
---

## 🛠️ Troubleshooting (Struggles Encountered & Solutions)

### 1. Files Not Deployed to Tomcat
❌ Problem:  Project files were still in the shared folder: /media/sf_yokai2.0

💡 Cause:  Files were not copied to Tomcat’s deployment directory.

✅ Solution:
```bash
sudo cp -r /media/sf_yokai2.0 /var/lib/tomcat10/webapps/myproject
```

---

### 2. Permission Denied (Shared Folder)
❌ Problem:
Cannot access shared folder files

💡 Cause:
User not added to VirtualBox shared folder group

✅ Solution:
```bash
sudo usermod -aG vboxsf $USER
sudo reboot

```
---

### 3. Website Not Loading (Blank / 404)

❌ Problem:
Website not loading
Blank page or 404 error

💡 Causes:
Incorrect URL
Application not deployed correctly
Tomcat service not running

✅ Solution:
```bash
✔ Use correct URLs:
http://<VM-IP>:8080/
http://<VM-IP>:8080/myproject/

✔ Check Tomcat status:
sudo systemctl status tomcat10
✔ Restart Tomcat:

sudo systemctl restart tomcat10

```

--- 

### 4. Shared Folder Not Showing in /media
❌ Problem: Shared folder (sf_ver2.0) did not appear after mounting  

🔍 Cause: VirtualBox Guest Additions not
 properly configured  

 ✅ Solution:
```bash
sudo usermod -aG vboxsf $USER
sudo reboot
```

---

### 5. “No such file or directory” Error

❌ Problem: Directory not found when navigating

🔍 Cause: Incorrect folder path or folder name

✅ Solution:
```bash
ls
cd /media/sf_ver2.0
```
---

### 6. Java Compilation Errors

❌ Problem: Errors when compiling Java servlet files

🔍 Cause:Missing servlet API in the classpath

✅ Solution:
```bash
javac -cp .:/usr/share/java/jakarta-servlet-api.jar -d ../WEB-INF/classes *.java
```
--- 
 
### 7. 404 Error – Application Not Found

❌ Problem:
HTTP Status 404 – /myapp is not available

💡 Meaning:
Tomcat is running, but the application is not properly deployed.

### 🔍 Step-by-Step Fix
### 1. Check Deployment Directory
ls /var/lib/tomcat10/webapps

Expected:
```bash
myapp.war 
myapp/
```

If missing:
sudo cp myapp.war /var/lib/tomcat10/webapps/

### 2. Restart Tomcat
```bash
sudo systemctl restart tomcat10
```

### 3. Verify Extraction
```bash
ls /var/lib/tomcat10/webapps

```
Should show:
```bash
myapp/
myapp.war

```

If only .war exists:
WAR may be invalid
Structure may be incorrect

### 4. Check Project Structure
```bash
ls /var/lib/tomcat10/webapps/myapp/WEB-INF

```
Must contain:
web.xml
classes/

### 5. Fix Common Structure Errors
```bash

Wrong:
│── myapp/
│   └── myapp/
│      └── WEB-INF/

Correct:
│── myapp/
│   └── WEB-INF/

```

### 6. Use Correct URL
```bash
http://localhost:8080/myapp

```

Do NOT use:
/myapp.war

### 7. Check Logs (If Still Failing)
```bash
sudo journalctl -u tomcat10

```

### 8. Test Tomcat Server
```bash
http://localhost:8080

```

If it loads → Tomcat is working
If not → server configuration issue

---
## 🛠️ Database Troubleshooting

## ❌ Problem 1 — HTTP 404 Error

### ⚠️ Error Message

```text id="h17"
HTTP Status 404 – Not Found
The requested resource [/myproject/RegisterServlet] is not available
```

### 📌 Cause

The servlet could not be located due to one or more of the following issues:

* Missing servlet mapping in `web.xml`
* Servlet class was not compiled
* Incorrect project deployment structure

### ✅ Solution

* Added the correct servlet mapping inside `web.xml`
* Recompiled all Java Servlet files
* Restarted the Apache Tomcat service

---

# ❌ Problem 2 — HTTP 405 Method Not Allowed

### 📌 Cause

The servlet only supported HTTP POST requests, but the servlet URL was accessed directly through the browser using a GET request.

### ✅ Solution

The servlet was accessed through an HTML form configured with:

```html id="h18"
method="post"
```

instead of directly opening the servlet URL in the browser.

---

# ❌ Problem 3 — NullPointerException

### ⚠️ Error Message

```text id="h19"
java.lang.NullPointerException
at RegisterServlet.doPost(RegisterServlet.java:24)
```

### 📌 Cause

The database connection object returned `null`, indicating that the database connection failed.

### ✅ Solution

* Corrected the database credentials
* Verified that the MariaDB service was running
* Installed and verified the MariaDB JDBC driver

---

# ❌ Problem 4 — Access Denied for Root User


### ⚠️ Error Message

```text id="h20"
Access denied for user 'root'@'localhost'
```

### 📌 Cause

The root account password or authentication configuration was incorrect.

### ✅ Solution

The root password was updated using:

```sql id="h21"
ALTER USER 'root'@'localhost'
IDENTIFIED BY 'newpassword';

FLUSH PRIVILEGES;
```
---

# ❌ Problem 5 — Missing Database Table

### ⚠️ Error Message

```text id="h22"
Table 'cafedb.users' doesn't exist
```

### 📌 Cause

The required `users` table had not yet been created in the database.

### ✅ Solution

The missing table was manually created using SQL commands.

---

# ❌ Problem 6 — Login Authentication Failure

### 📌 Cause

The HTML login form did not contain the required `name` attributes, preventing the servlet from retrieving the submitted values.

### ✅ Solution

The following attributes were added to the input fields:

```html id="h23"
name="username"
name="password"
```

This enabled the servlet to retrieve form values using:

```java id="h24"
request.getParameter("username");
request.getParameter("password");
```

---

# ❌ Problem 7 — LoginServlet HTTP 404 Error

### 📌 Cause

`LoginServlet` was not properly registered in the `web.xml` deployment descriptor.

### ✅ Solution

The following servlet configuration was added to `web.xml`:

```xml id="h25"
<servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>LoginServlet</servlet-class>
</servlet>
```

along with the corresponding servlet mapping.

---

## 🔐 Security Considerations

- Use strong database credentials  
- Restrict access to sensitive files  
- Avoid running services as root  
- Keep Ubuntu updated  

---

# ❌ Problem 8 — Session Persistence Failure

### 📌 Cause

User session management was not implemented after successful authentication.

### ✅ Solution

HTTP session handling was added using:

```java id="h26"
HttpSession session = request.getSession();
session.setAttribute("username", username);
```

---

# ❌ Problem 9 — Browser Password Security Warning

### 📌 Cause

Weak passwords used during testing triggered browser security warnings from Google Password Manager.

### ✅ Solution

Stronger passwords were used during testing, and the warning was identified as a browser-side security notification rather than a system issue.
---

## 👨‍💻 Author

Developed by: 
Janella Frenzyle I. Aggay
Earl Justine S. Bacting
Rechelle C. Mercader
Kimberly A. Monserrat
Julia Mae E. Sampaga 

---

## 📄 License

This project is for educational purposes only.
