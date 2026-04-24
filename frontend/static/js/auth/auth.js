const API_BASE_URL = "http://localhost:8080";

async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch(`${API_BASE_URL}/users/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            const message = await response.text();
            alert(message);
            return;
        }

        const data = await response.json();
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("userEmail", data.email);
        redirectDashboard();
    } catch (error) {
        alert("Erro ao conectar com o servidor.");
    }
}

async function register() {
    const name = document.getElementById("name").value;
    const age = parseInt(document.getElementById("age").value);
    const gender = document.getElementById("gender").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    try {
        const response = await fetch(`${API_BASE_URL}/users/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, age, gender, email, password, confirmPassword })
        });

        if (!response.ok) {
            const message = await response.text();
            alert(message);
            return;
        }

        const data = await response.json();
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("userEmail", data.email);
        redirectDashboard();
    } catch (error) {
        alert("Erro ao conectar com o servidor.");
    }
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("cart");
    redirectLogin();
}

function getToken() {
    return localStorage.getItem("token");
}

function getUserId() {
    return parseInt(localStorage.getItem("userId"));
}

function getUserEmail() {
    return localStorage.getItem("userEmail");
}

function isAuthenticated() {
    return !!localStorage.getItem("token");
}
