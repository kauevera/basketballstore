const API_BASE_URL = "/api";

const SVG_EYE_OPEN = `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>`;
const SVG_EYE_OFF  = `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>`;

function togglePassword(inputId) {
    const input = document.getElementById(inputId);
    const btn = input.nextElementSibling;
    const isHidden = input.type === 'password';
    input.type = isHidden ? 'text' : 'password';
    btn.innerHTML = isHidden ? SVG_EYE_OFF : SVG_EYE_OPEN;
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.toggle-password').forEach(btn => {
        btn.innerHTML = SVG_EYE_OPEN;
    });
});

function getPasswordStrength(password) {
    const checks = [
        password.length >= 8,
        password.length >= 12,
        /[a-z]/.test(password),
        /[A-Z]/.test(password),
        /[0-9]/.test(password),
        /[^a-zA-Z0-9]/.test(password)
    ];
    const score = checks.filter(Boolean).length;

    const levels = [
        { label: 'Muito fraca', color: '#e74c3c', width: '16%'  },
        { label: 'Fraca',       color: '#e67e22', width: '33%'  },
        { label: 'Média',       color: '#f1c40f', width: '55%'  },
        { label: 'Forte',       color: '#27ae60', width: '78%'  },
        { label: 'Muito forte', color: '#1e8449', width: '100%' },
    ];

    if (score <= 1) return levels[0];
    if (score === 2) return levels[1];
    if (score === 3) return levels[2];
    if (score === 4) return levels[3];
    return levels[4];
}

function updatePasswordStrength() {
    const password = document.getElementById('password').value;
    const fill  = document.getElementById('strength-fill');
    const label = document.getElementById('strength-label');
    if (!fill || !label) return;

    if (!password) {
        fill.style.width = '0';
        label.textContent = '';
        return;
    }

    const strength = getPasswordStrength(password);
    fill.style.width = strength.width;
    fill.style.backgroundColor = strength.color;
    label.textContent = strength.label;
    label.style.color = strength.color;
}

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
