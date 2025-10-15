// Main application bootstrap
import { Router } from './router.js';
import { Store } from './store.js';
import { UI } from './ui.js';
import { seedData } from './data/seed.js';

class App {
    constructor() {
        this.store = new Store();
        this.ui = new UI();
        this.router = new Router();
        this.currentUser = null;
        
        this.init();
    }

    init() {
        // Initialize app
        this.setupEventListeners();
        this.loadInitialData();
        this.setupTheme();
        this.showLogin();
    }

    setupEventListeners() {
        // Login form
        const loginForm = document.getElementById('loginForm');
        loginForm?.addEventListener('submit', (e) => this.handleLogin(e));

        // Logout button
        const logoutBtn = document.getElementById('logoutBtn');
        logoutBtn?.addEventListener('click', () => this.handleLogout());

        // Theme toggle
        const themeToggle = document.getElementById('themeToggle');
        themeToggle?.addEventListener('click', () => this.toggleTheme());

        // Sidebar navigation
        document.addEventListener('click', (e) => {
            const navItem = e.target.closest('.nav-item');
            if (navItem) {
                this.updateActiveNavItem(navItem);
            }
        });

        // Handle hash changes
        window.addEventListener('hashchange', () => {
            this.router.handleRoute();
        });
    }

    loadInitialData() {
        // Load data from localStorage or use seed data
        const saved = this.store.loadFromStorage();
        if (!saved) {
            console.log('Initializing with seed data');
            seedData(this.store);
        }
    }

    setupTheme() {
        const prefs = this.store.getPreferences();
        if (prefs.darkMode) {
            document.documentElement.classList.add('dark');
            this.updateThemeIcon('☀️');
        }
    }

    showLogin() {
        const loginModal = document.getElementById('loginModal');
        const appContainer = document.getElementById('appContainer');
        
        loginModal?.classList.remove('hidden');
        appContainer?.classList.add('hidden');
    }

    hideLogin() {
        const loginModal = document.getElementById('loginModal');
        const appContainer = document.getElementById('appContainer');
        
        loginModal?.classList.add('hidden');
        appContainer?.classList.remove('hidden');
    }

    handleLogin(e) {
        e.preventDefault();
        
        const roleSelect = document.getElementById('roleSelect');
        const selectedRole = roleSelect.value;
        
        if (!selectedRole) {
            this.ui.showToast('Por favor, selecione um perfil', 'error');
            return;
        }

        // Set current user
        this.currentUser = {
            role: selectedRole,
            name: this.getRoleName(selectedRole)
        };

        // Update store
        this.store.setAuth({ currentRole: selectedRole });
        
        // Update UI
        this.updateUserInfo();
        this.updateSidebarVisibility();
        this.hideLogin();
        
        // Navigate to dashboard
        window.location.hash = '#/dashboard';
        this.router.handleRoute();
        
        this.ui.showToast(`Bem-vindo, ${this.currentUser.name}!`, 'success');
    }

    handleLogout() {
        this.currentUser = null;
        this.store.setAuth({ currentRole: null });
        this.showLogin();
        
        // Clear form
        document.getElementById('roleSelect').value = '';
        
        this.ui.showToast('Logout realizado com sucesso', 'info');
    }

    toggleTheme() {
        const html = document.documentElement;
        const isDark = html.classList.contains('dark');
        
        if (isDark) {
            html.classList.remove('dark');
            this.updateThemeIcon('🌙');
        } else {
            html.classList.add('dark');
            this.updateThemeIcon('☀️');
        }
        
        // Save preference
        this.store.updatePreferences({ darkMode: !isDark });
    }

    updateThemeIcon(icon) {
        const themeIcon = document.querySelector('.theme-icon');
        if (themeIcon) {
            themeIcon.textContent = icon;
        }
    }

    updateUserInfo() {
        const userInfo = document.getElementById('userInfo');
        if (userInfo && this.currentUser) {
            userInfo.textContent = this.currentUser.name;
        }
    }

    updateSidebarVisibility() {
        if (!this.currentUser) return;
        
        const navItems = document.querySelectorAll('.nav-item[data-roles]');
        navItems.forEach(item => {
            const allowedRoles = item.dataset.roles?.split(',') || [];
            if (allowedRoles.includes(this.currentUser.role)) {
                item.style.display = 'flex';
            } else {
                item.style.display = 'none';
            }
        });
    }

    updateActiveNavItem(activeItem) {
        // Remove active class from all items
        document.querySelectorAll('.nav-item').forEach(item => {
            item.classList.remove('active');
        });
        
        // Add active class to clicked item
        activeItem.classList.add('active');
    }

    getRoleName(role) {
        const roleNames = {
            'ADMIN': 'Administrador',
            'ATENDENTE': 'Atendente',
            'OPERADOR': 'Operador'
        };
        return roleNames[role] || role;
    }

    getCurrentUser() {
        return this.currentUser;
    }

    getStore() {
        return this.store;
    }

    getUI() {
        return this.ui;
    }
}

// Initialize app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.app = new App();
});

export { App };