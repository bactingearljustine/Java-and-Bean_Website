# ☕ Java & Bean Café Website

A Java Web Application deployed using Apache Tomcat on Ubuntu Server (VirtualBox)

---

## 📌 Project Overview

The *Java & Bean Café Website* is a dynamic Java web application that simulates an online café system where users can browse menu items, view products, and interact with the platform.

This project demonstrates the deployment of a Java-based web application using *Apache Tomcat* on an *Ubuntu Server running in a VirtualBox virtual machine*. It highlights server-side application hosting, shared folder integration, and web application deployment in a controlled virtual environment.

---

## 📌 Features

- User login
- Registration
- Café menu
- Cart system
- MariaDB integration

---

## 🧰 Technologies Used

- Java
- Tomcat
- MariaDB
- Ubuntu Server

## 🖥️ System Architecture

### Frontend
- HTML, CSS, JavaScript  

### Backend
- Java Servlets & JSP (deployed on Apache Tomcat)  

### Database
- MySQL   

- Passwords hashed with SHA-256 and a random salt via `PasswordUtil.java` — never stored as plain text
- All database queries use `PreparedStatement` — no SQL injection possible
- Server-side `HttpSession` authentication on all protected servlet endpoints
- Specific DB constraint violation handling with full transaction rollback on order failure
- Input validation on both client (email regex, password strength meter) and server (Java)
- All 4 servlets mapped in `web.xml` with `HttpOnly` session cookies and a 60-minute timeout

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

---

## 🖥️ System Architecture

### Frontend
- HTML, CSS, JavaScript
### Backend
- Java Servlets & JSP (deployed on Apache Tomcat)
### Database
- MariaDB — `cafeDB` with 4 tables: `users`, `menu`, `orders`, `order_items`
### Environment
- Ubuntu Server (VirtualBox VM)
- Apache Tomcat 10 (Java Web Server)
- 
---


## 📂 Project Structure

```

java-bean-cafe/
├── index.html               ← Login page
├── register.html            ← Registration page
├── dashboard.html           ← Home (after login)
├── menu.html                ← Browse menu items
├── cart.html                ← Cart & checkout
├── style.css                ← All styles
├── script.js                ← All frontend logic
├── WEB-INF/
│   ├── web.xml              ← Servlet mappings, session config
│   ├── lib/                 ← Place mariadb-java-client.jar here
│   └── classes/             ← Compiled .class files go here
├── scr/
│   ├── DBConnection.java
│   ├── PasswordUtil.java
│   ├── RegisterServlet.java
│   ├── LoginServlet.java
│   ├── MenuServlet.java
│   └── OrderServlet.java
└── sql/
    └── cafeDB_setup.sql     ← Database schema + sample menu data

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
### 🗄️ Sample Database Output
![Cafe Database](images/databasee.png)

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
