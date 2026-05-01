document.addEventListener("DOMContentLoaded", () => {

  // BUTTON EVENTS
  document.getElementById("login-btn")?.addEventListener("click", login);
  document.getElementById("register-btn")?.addEventListener("click", register);
  document.getElementById("logout-btn")?.addEventListener("click", logout);

  const currentUser = JSON.parse(localStorage.getItem("currentUser"));
  const path = window.location.pathname;

  // 🔐 PAGE PROTECTION
  if (
    path.includes("dashboard.html") ||
    path.includes("menu.html") ||
    path.includes("cart.html")
  ) {
    if (!currentUser) {
      window.location.href = "index.html";
      return;
    }

    const welcomeText = document.getElementById("welcome-text");
    if (welcomeText) {
      const hour = new Date().getHours();
      const greeting =
        hour < 12 ? "Good morning" :
        hour < 18 ? "Good afternoon" :
        "Good evening";

      welcomeText.innerText = `${greeting}, ${currentUser.username}! 👋`;
    }
  }

  updateCartBadge();
  loadMenu();
  loadCart();
});


// ================= TOAST =================
function showToast(message, emoji = "✓") {
  const oldToast = document.querySelector(".toast");
  if (oldToast) oldToast.remove();

  const toast = document.createElement("div");
  toast.className = "toast";
  toast.innerHTML = `<span>${emoji}</span> ${message}`;

  document.body.appendChild(toast);

  setTimeout(() => {
    toast.classList.add("hide");
    setTimeout(() => toast.remove(), 300);
  }, 2500);
}


// ================= CART BADGE =================
function updateCartBadge() {
  const cart = JSON.parse(localStorage.getItem("cart")) || [];

  document.querySelectorAll("#cart-count").forEach(el => {
    if (cart.length > 0) {
      el.style.display = "inline";
      el.textContent = cart.length;
    } else {
      el.style.display = "none";
    }
  });
}


// ================= REGISTER =================
function register() {
  const username = document.getElementById("reg-user").value;
  const email = document.getElementById("reg-email").value;
  const password = document.getElementById("reg-pass").value;

  fetch("RegisterServlet", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: `username=${username}&email=${email}&password=${password}`
  })
  .then(res => res.text())
  .then(data => {
    if (data === "success") {
      showToast("Account created!", "🎉");
      window.location.href = "index.html";
    } else {
      showToast("Registration failed", "⚠️");
    }
  });
}

// ================= LOGIN =================
function login() {
  const username = document.getElementById("login-user").value;
  const password = document.getElementById("login-pass").value;

  fetch("LoginServlet", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: `username=${username}&password=${password}`
  })
  .then(res => res.text())
  .then(data => {
    if (data === "success") {
      localStorage.setItem("currentUser", JSON.stringify({ username }));
      window.location.href = "dashboard.html";
    } else {
      showToast("Invalid login", "⚠️");
    }
  });
}


// ================= LOGOUT =================
function logout() {
  localStorage.removeItem("currentUser");
  window.location.href = "index.html";
}


// ================= FULL MENU (UPDATED) =================
const menu = [
  // ☕ COFFEE
  { id: 1, name: "Espresso", price: 120, icon: "☕", desc: "Strong and bold shot." },
  { id: 2, name: "Americano", price: 130, icon: "🥤", desc: "Espresso with hot water." },
  { id: 3, name: "Latte", price: 150, icon: "🥛", desc: "Creamy milk coffee." },
  { id: 4, name: "Cappuccino", price: 160, icon: "🫧", desc: "Foamy and rich." },
  { id: 5, name: "Mocha", price: 170, icon: "🍫", desc: "Chocolate espresso blend." },
  { id: 6, name: "Caramel Macchiato", price: 180, icon: "🍯", desc: "Sweet caramel layers." },

  // 🧊 ICED DRINKS
  { id: 7, name: "Iced Latte", price: 160, icon: "🧊", desc: "Chilled creamy coffee." },
  { id: 8, name: "Iced Mocha", price: 175, icon: "🧋", desc: "Cold chocolate coffee." },
  { id: 9, name: "Cold Brew", price: 165, icon: "🥶", desc: "Smooth slow-brewed coffee." },

  // 🍹 NON-COFFEE
  { id: 10, name: "Matcha Latte", price: 170, icon: "🍵", desc: "Green tea goodness." },
  { id: 11, name: "Hot Chocolate", price: 150, icon: "🍫", desc: "Warm chocolate drink." },
  { id: 12, name: "Milkshake", price: 180, icon: "🍦", desc: "Sweet creamy treat." },

  // 🥐 SNACKS
  { id: 13, name: "Croissant", price: 90, icon: "🥐", desc: "Buttery flaky pastry." },
  { id: 14, name: "Chocolate Cake", price: 120, icon: "🍰", desc: "Rich chocolate slice." },
  { id: 15, name: "Blueberry Muffin", price: 100, icon: "🧁", desc: "Soft fruity muffin." },
  { id: 16, name: "Donut", price: 80, icon: "🍩", desc: "Classic sweet donut." },
  { id: 17, name: "Sandwich", price: 140, icon: "🥪", desc: "Savory light meal." }
];


// ================= LOAD MENU =================
function loadMenu() {
  const container = document.getElementById("menu-list");
  if (!container) return;

  container.innerHTML = "";

  menu.forEach((item, index) => {
    const div = document.createElement("div");
    div.className = "menu-item";

    div.innerHTML = `
      <span class="item-icon">${item.icon}</span>
      <h3>${item.name}</h3>
      <span class="item-price">₱${item.price}</span>
      <p class="item-desc">${item.desc}</p>
      <button class="btn-add" onclick="addToCart(${item.id})">+ Add to Cart</button>
    `;

    container.appendChild(div);
  });
}


// ================= ADD TO CART =================
function addToCart(id) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];

  const item = menu.find(m => m.id === id);
  cart.push(item);

  localStorage.setItem("cart", JSON.stringify(cart));

  updateCartBadge();
  showToast(`${item.name} added!`, item.icon);
}


// ================= LOAD CART =================
function loadCart() {
  const cartDiv = document.getElementById("cart-items");
  const totalDiv = document.getElementById("total-price");
  const summaryDiv = document.getElementById("summary-rows");

  if (!cartDiv || !totalDiv) return;

  let cart = JSON.parse(localStorage.getItem("cart")) || [];

  if (cart.length === 0) {
    cartDiv.innerHTML = `<p>Your cart is empty 🛒</p>`;
    totalDiv.textContent = "₱0";
    if (summaryDiv) summaryDiv.innerHTML = "";
    return;
  }

  let total = 0;
  let grouped = {};

  cart.forEach(item => {
    total += item.price;

    if (!grouped[item.name]) {
      grouped[item.name] = { ...item, qty: 0 };
    }
    grouped[item.name].qty++;
  });

  cartDiv.innerHTML = "";

  Object.values(grouped).forEach(item => {
    cartDiv.innerHTML += `
      <div class="cart-item-row">
        <div class="cart-item-icon">${item.icon}</div>
        <span class="cart-item-name">${item.name} × ${item.qty}</span>
        <span class="cart-item-price">₱${item.price * item.qty}</span>
      </div>
    `;
  });

  if (summaryDiv) {
    summaryDiv.innerHTML = Object.values(grouped).map(item => `
      <div class="summary-row">
        <span>${item.name} × ${item.qty}</span>
        <span>₱${item.price * item.qty}</span>
      </div>
    `).join("");
  }

  totalDiv.textContent = "₱" + total;
}


// ================= CHECKOUT =================
function checkout() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];

  if (cart.length === 0) {
    showToast("Cart is empty!", "⚠️");
    return;
  }

  let orders = JSON.parse(localStorage.getItem("orders")) || [];

  orders.push({
    items: cart,
    date: new Date().toLocaleString()
  });

  localStorage.setItem("orders", JSON.stringify(orders));
  localStorage.removeItem("cart");

  showToast("Order placed! ☕", "🎉");

  setTimeout(() => {
    window.location.reload();
  }, 1500);
}