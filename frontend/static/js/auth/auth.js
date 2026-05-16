const API_BASE_URL = "/api";

function togglePassword(inputId, btn) {
    const input = document.getElementById(inputId);
    const isHidden = input.type === 'password';
    input.type = isHidden ? 'text' : 'password';
    btn.querySelector('.icon-eye').style.display = isHidden ? 'none' : '';
    btn.querySelector('.icon-eye-off').style.display = isHidden ? '' : 'none';
}

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
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    if (!email || !password) {
        showToast("Preencha todos os campos.");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/users/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            const message = await response.text();
            showToast(message);
            return;
        }

        const data = await response.json();
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("userEmail", data.email);
        redirectDashboard();
    } catch (error) {
        showToast("Erro ao conectar com o servidor.");
    }
}

async function register() {
    const name = document.getElementById("name").value.trim();
    const age = parseInt(document.getElementById("age").value);
    const gender = document.getElementById("gender").value;
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (!name || !age || !gender || !email || !password || !confirmPassword) {
        showToast("Preencha todos os campos.");
        return;
    }

    if (password !== confirmPassword) {
        showToast("As senhas não coincidem.");
        return;
    }

    const strength = getPasswordStrength(password);
    if (strength.label === 'Muito fraca' || strength.label === 'Fraca') {
        showToast("A senha é muito fraca. Use ao menos 8 caracteres com letras maiúsculas, números ou símbolos.");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/users/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, age, gender, email, password, confirmPassword })
        });

        if (!response.ok) {
            const message = await response.text();
            showToast(message);
            return;
        }

        const data = await response.json();
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("userEmail", data.email);
        redirectDashboard();
    } catch (error) {
        showToast("Erro ao conectar com o servidor.");
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
