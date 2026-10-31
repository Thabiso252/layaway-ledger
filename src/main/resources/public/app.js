// ---- Demo Access Gate ----
const DEMO_USER = "admin";
const DEMO_PASS = "admin123";

document.getElementById("loginForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const u = document.getElementById("loginUser").value;
    const p = document.getElementById("loginPass").value;
    const msg = document.getElementById("loginMsg");

    if (u === DEMO_USER && p === DEMO_PASS) {
        document.getElementById("loginScreen").classList.add("hidden");
        document.getElementById("appMain").classList.remove("hidden");
        refreshAll(); // Load data immediately on login
    } else {
        msg.textContent = "Invalid credentials";
        msg.style.color = "red";
    }
});

// ---- Base API URL ----
const API = "";

// ---- Helper Functions ----
async function postJSON(url, data) {
    const res = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
    return { ok: res.ok, status: res.status, body: await res.json().catch(() => ({})) };
}

async function getJSON(url) {
    const res = await fetch(url);
    return { ok: res.ok, status: res.status, body: await res.json().catch(() => ({})) };
}

function refreshAll() {
    loadAllReservations();
    loadOverdue();
    loadReady();
}

// ---- Add Customer ----
document.getElementById("customerForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("customerName").value;
    const phone = document.getElementById("customerPhone").value;
    const { ok, body } = await postJSON(`${API}/customers`, { name, phone });
    const msg = document.getElementById("customerMsg");
    if (ok) {
        msg.textContent = `Customer added: ID ${body.id ?? "?"}`;
        msg.style.color = "green";
        e.target.reset();
    } else {
        msg.textContent = `Error: ${body.error || "Unknown"}`;
        msg.style.color = "red";
    }
});

// ---- Add Item ----
document.getElementById("itemForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const description = document.getElementById("itemDescription").value;
    const price = parseFloat(document.getElementById("itemPrice").value);
    const { ok, body } = await postJSON(`${API}/items`, { description, price });
    const msg = document.getElementById("itemMsg");
    if (ok) {
        msg.textContent = `Item added: ID ${body.id ?? "?"}`;
        msg.style.color = "green";
        e.target.reset();
    } else {
        msg.textContent = `Error: ${body.error || "Unknown"}`;
        msg.style.color = "red";
    }
});

// ---- Create Reservation ----
document.getElementById("reservationForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const customerId = parseInt(document.getElementById("resCustomerId").value);
    const itemId = parseInt(document.getElementById("resItemId").value);
    const plan = document.getElementById("resPlan").value;
    const { ok, body } = await postJSON(`${API}/reservations`, { customerId, itemId, plan });
    const msg = document.getElementById("resMsg");
    if (ok) {
        msg.textContent = `Reservation created: ID ${body.id ?? "?"}`;
        msg.style.color = "green";
        e.target.reset();
    } else {
        msg.textContent = `Error: ${body.error || "Unknown"}`;
        msg.style.color = "red";
    }
});

// ---- Record Payment ----
document.getElementById("paymentForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const reservationId = parseInt(document.getElementById("payReservationId").value);
    const amount = parseFloat(document.getElementById("payAmount").value);
    const { ok, body } = await postJSON(`${API}/payments`, { reservationId, amount });
    const msg = document.getElementById("payMsg");
    if (ok) {
        msg.textContent = `Payment recorded. New balance: K${body.remainingBalance ?? "?"}`;
        msg.style.color = "green";
        e.target.reset();
    } else {
        msg.textContent = `Error: ${body.error || "Unknown"}`;
        msg.style.color = "red";
    }
});

// ---- Tables ----
async function loadOverdue() {
    const { ok, body } = await getJSON(`${API}/overdue`);
    const tbody = document.querySelector("#overdueTable tbody");
    tbody.innerHTML = "";
    if (!ok || !Array.isArray(body) || body.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" style="color:#888;text-align:center;">No overdue reservations</td></tr>`;
        return;
    }
    body.forEach(r => {
        tbody.innerHTML += `<tr>
            <td>${r.id}</td><td>${r.customerName}</td><td>${r.itemDescription}</td>
            <td>K${r.remainingBalance}</td><td>${r.nextDueDate}</td>
        </tr>`;
    });
}

async function loadReady() {
    const { ok, body } = await getJSON(`${API}/ready`);
    const tbody = document.querySelector("#readyTable tbody");
    tbody.innerHTML = "";
    if (!ok || !Array.isArray(body) || body.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" style="color:#888;text-align:center;">No items ready for collection</td></tr>`;
        return;
    }
    body.forEach(r => {
        tbody.innerHTML += `<tr>
            <td>${r.id}</td><td>${r.customerName}</td><td>${r.itemDescription}</td>
            <td>READY</td>
        </tr>`;
    });
}

async function loadAllReservations() {
    const { ok, body } = await getJSON(`${API}/reservations`);
    const tbody = document.querySelector("#allTable tbody");
    tbody.innerHTML = "";
    if (!ok || !Array.isArray(body) || body.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" style="color:#888;text-align:center;">No reservations found</td></tr>`;
        return;
    }
    body.forEach(r => {
        tbody.innerHTML += `<tr>
            <td>${r.id}</td><td>${r.customerName}</td><td>${r.itemDescription}</td>
            <td>K${r.totalPrice}</td><td>K${r.amountPaid}</td><td>K${r.remainingBalance}</td>
            <td>${r.complete ? "Complete" : "Active"}</td>
        </tr>`;
    });
}
