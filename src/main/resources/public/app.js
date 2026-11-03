const DEMO_USER = "admin";
const DEMO_PASS = "admin123";

document.getElementById("loginForm").addEventListener("submit", (e) => {
    e.preventDefault();
    if (document.getElementById("loginUser").value === DEMO_USER && document.getElementById("loginPass").value === DEMO_PASS) {
        document.getElementById("loginScreen").classList.add("hidden");
        document.getElementById("appMain").classList.remove("hidden");
        refreshAll();
    } else {
        document.getElementById("loginMsg").textContent = "Invalid credentials";
        document.getElementById("loginMsg").style.color = "red";
    }
});

const API = "";

async function postJSON(url, data) {
    const res = await fetch(url, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(data) });
    return { ok: res.ok, status: res.status, body: await res.json().catch(() => ({})) };
}
async function getJSON(url) {
    const res = await fetch(url);
    return { ok: res.ok, status: res.status, body: await res.json().catch(() => ({})) };
}

function refreshAll() { 
    console.log("refreshAll() called");
    loadAllReservations(); 
    loadOverdue(); 
    loadReady(); 
}

document.getElementById("customerForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const { ok, body } = await postJSON(`${API}/customers`, { name: document.getElementById("customerName").value, phone: document.getElementById("customerPhone").value });
    const msg = document.getElementById("customerMsg");
    msg.textContent = ok ? `Customer added: ID ${body.id} (Auto-fills below)` : `Error: ${body.error}`;
    msg.style.color = ok ? "green" : "red";
    if (ok) { document.getElementById("resCustomerId").value = body.id; e.target.reset(); }
});

document.getElementById("itemForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const { ok, body } = await postJSON(`${API}/items`, { description: document.getElementById("itemDescription").value, price: parseFloat(document.getElementById("itemPrice").value) });
    const msg = document.getElementById("itemMsg");
    msg.textContent = ok ? `Item added: ID ${body.id} (Auto-fills below)` : `Error: ${body.error}`;
    msg.style.color = ok ? "green" : "red";
    if (ok) { document.getElementById("resItemId").value = body.id; e.target.reset(); }
});

document.getElementById("reservationForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const { ok, body } = await postJSON(`${API}/reservations`, { 
        customerId: document.getElementById("resCustomerId").value, 
        itemId: document.getElementById("resItemId").value, 
        plan: document.getElementById("resPlan").value 
    });
    const msg = document.getElementById("resMsg");
    msg.textContent = ok ? `Reservation created: ID ${body.id}` : `Error: ${body.error}`;
    msg.style.color = ok ? "green" : "red";
    if (ok) { document.getElementById("payReservationId").value = body.id; e.target.reset(); }
});

document.getElementById("paymentForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const { ok, body } = await postJSON(`${API}/payments`, { 
        reservationId: document.getElementById("payReservationId").value, 
        amount: parseFloat(document.getElementById("payAmount").value) 
    });
    const msg = document.getElementById("payMsg");
    msg.textContent = ok ? `Payment recorded.` : `Error: ${body.error}`;
    msg.style.color = ok ? "green" : "red";
    if (ok) { e.target.reset(); }
});

async function loadOverdue() {
    console.log("loadOverdue() called");
    const { ok, body } = await getJSON(`${API}/overdue`);
    console.log("Overdue response:", ok, body);
    const tbody = document.querySelector("#overdueTable tbody");
    tbody.innerHTML = (!ok || body.length === 0) ? `<tr><td colspan="5" style="color:#888;text-align:center;">No overdue reservations</td></tr>` : "";
    if (ok && Array.isArray(body)) body.forEach(r => {
        tbody.innerHTML += `<tr><td>${r.reservationId}</td><td>${r.customer.name}</td><td>${r.item.name}</td><td>K${r.remainingBalance}</td><td>Active</td></tr>`;
    });
}

async function loadReady() {
    console.log("loadReady() called");
    const { ok, body } = await getJSON(`${API}/ready`);
    console.log("Ready response:", ok, body);
    const tbody = document.querySelector("#readyTable tbody");
    tbody.innerHTML = (!ok || body.length === 0) ? `<tr><td colspan="4" style="color:#888;text-align:center;">No items ready</td></tr>` : "";
    if (ok && Array.isArray(body)) body.forEach(r => {
        tbody.innerHTML += `<tr><td>${r.reservationId}</td><td>${r.customer.name}</td><td>${r.item.name}</td><td>READY</td></tr>`;
    });
}

async function loadAllReservations() {
    console.log("loadAllReservations() called");
    const { ok, body } = await getJSON(`${API}/reservations`);
    console.log("All reservations response:", ok, body);
    const tbody = document.querySelector("#allTable tbody");
    tbody.innerHTML = (!ok || body.length === 0) ? `<tr><td colspan="7" style="color:#888;text-align:center;">No reservations found</td></tr>` : "";
    if (ok && Array.isArray(body)) body.forEach(r => {
        tbody.innerHTML += `<tr><td>${r.reservationId}</td><td>${r.customer.name}</td><td>${r.item.name}</td><td>K${r.totalPrice}</td><td>K${r.amountPaid}</td><td>K${r.remainingBalance}</td><td>${r.status}</td></tr>`;
    });
}
