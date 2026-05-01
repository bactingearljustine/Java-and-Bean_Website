# ☕ Java & Bean Café Website

A Java Web Application demonstrated using an Ubuntu Server Virtual Machine (VirtualBox)

---

## 📌 Project Overview

The **Java & Bean Café Website** is a dynamic web application designed to simulate a café system where users can browse menu items, view products, and interact with the system.

This project demonstrates **web deployment inside an Ubuntu Server virtual machine** using a shared folder setup.

---

## 🚀 Features

- 📋 Dynamic café menu display  
- 🛒 Product browsing interface  
- 🔐 Backend design using Java (Servlets/JSP)  
- 🌐 Hosted inside Ubuntu VM  
- 🗄️ Database connectivity (MySQL)  
- 🧑‍💼 Optional admin functionalities  

---

## 🖥️ System Architecture

### Frontend
- HTML, CSS, JavaScript  

### Backend
- Java Servlets & JSP  

### Database
- MySQL / MariaDB  

### Environment
- Ubuntu Server (VirtualBox VM)

---

## 🧰 Technologies Used

- ☕ Java (JDK 8 or higher)  
- 🐧 Ubuntu Server  
- 📦 VirtualBox  
- 🛢️ MySQL / MariaDB  
- 💻 VS Code / IntelliJ IDEA / Eclipse  
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
   - Folder Name: yokai2.0  
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
sf_yokai2.0
```

---

### ⚠️ If Permission is Denied

```bash
sudo usermod -aG vboxsf $USER
sudo reboot
```

After reboot:

```bash
cd /media/sf_yokai2.0
ls
```

---

### 📦 Step 6: Access Website Files

```bash
cd /media/sf_yokai2.0
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

## 🔐 Security Considerations

- Use strong database credentials  
- Restrict access to sensitive files  
- Avoid running services as root  
- Keep Ubuntu updated  


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
