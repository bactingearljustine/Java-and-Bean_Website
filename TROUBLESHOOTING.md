## 🛠️ Troubleshooting (Struggles Encountered & Solutions)


This document serves as a support guide for identifying and resolving common issues that may occur while installing, running, or deploying the system. It includes a list of frequent errors, their possible causes, and recommended solutions. The purpose of this guide is to help users quickly diagnose problems and restore the system to proper working condition without unnecessary delays.

---

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
