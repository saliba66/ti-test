// Hash-based SPA router
class Router {
    constructor() {
        this.routes = new Map();
        this.currentPage = null;
        this.setupRoutes();
    }

    setupRoutes() {
        // Dynamically import pages when needed
        this.routes.set('dashboard', () => import('./pages/dashboard.js'));
        this.routes.set('mapa', () => import('./pages/mapa.js'));
        this.routes.set('cancela', () => import('./pages/cancela.js'));
        this.routes.set('visitantes', () => import('./pages/visitantes.js'));
        this.routes.set('usuarios', () => import('./pages/usuarios.js'));
        this.routes.set('vagas', () => import('./pages/vagas.js'));
        this.routes.set('movimentacao', () => import('./pages/movimentacao.js'));
        this.routes.set('relatorios', () => import('./pages/relatorios.js'));
        this.routes.set('configuracoes', () => import('./pages/configuracoes.js'));
        
        // NOVO: Adiciona o mapeamento para a página de Monitoramento IA Gustavo
        this.routes.set('ia-monitoramento', () => import('./pages/ia-monitoramento.js'));
        this.routes.set('cadastro-admin', () => import('./pages/cadastro-admin.js')); 
        
        this.routes.set('404', () => import('./pages/notfound.js'));
    }

    async handleRoute() {
        const hash = window.location.hash;
        const path = hash.replace('#/', '') || 'dashboard';

        console.log(`Navigating to: ${path}`);

        try {
            // Check if route exists
            const routeLoader = this.routes.get(path) || this.routes.get('404');

            // Load the page module
            const pageModule = await routeLoader();

            // Get the page content
            const pageContent = pageModule.render();

            // Update the DOM
            const appContainer = document.getElementById('app');
            if (appContainer) {
                appContainer.innerHTML = pageContent;

                // Call mount function if it exists (for event listeners)
                if (pageModule.mount) {
                    pageModule.mount();
                }
            }

            this.currentPage = path;

        } catch (error) {
            console.error('Error loading page:', error);
            this.navigateTo404();
        }
    }

    navigateTo(path) {
        window.location.hash = `#/${path}`;
    }

    navigateTo404() {
        window.location.hash = '#/404';
    }

    getCurrentPage() {
        return this.currentPage;
    }
}

export { Router };