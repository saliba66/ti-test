// Global state management with localStorage persistence
class Store {
    constructor() {
        this.state = {
            auth: { currentRole: null },
            usuarios: [],
            visitantes: [],
            vagas: [],
            eventos: [],
            prefs: { 
                darkMode: false, 
                persist: true 
            }
        };
        
        this.listeners = new Map();
        this.loadFromStorage();
    }

    // State getters
    getAuth() {
        return this.state.auth;
    }

    getUsuarios() {
        return this.state.usuarios;
    }

    getVisitantes() {
        return this.state.visitantes;
    }

    getVagas() {
        return this.state.vagas;
    }

    getEventos() {
        return this.state.eventos;
    }

    getPreferences() {
        return this.state.prefs;
    }

    getState() {
        return { ...this.state };
    }

    // State setters
    setAuth(auth) {
        this.state.auth = { ...this.state.auth, ...auth };
        this.persistIfEnabled();
        this.notify('auth', this.state.auth);
    }

    setUsuarios(usuarios) {
        this.state.usuarios = usuarios;
        this.persistIfEnabled();
        this.notify('usuarios', usuarios);
    }

    setVisitantes(visitantes) {
        this.state.visitantes = visitantes;
        this.persistIfEnabled();
        this.notify('visitantes', visitantes);
    }

    setVagas(vagas) {
        this.state.vagas = vagas;
        this.persistIfEnabled();
        this.notify('vagas', vagas);
    }

    setEventos(eventos) {
        this.state.eventos = eventos;
        this.persistIfEnabled();
        this.notify('eventos', eventos);
    }

    updatePreferences(prefs) {
        this.state.prefs = { ...this.state.prefs, ...prefs };
        this.persistIfEnabled();
        this.notify('prefs', this.state.prefs);
    }

    // CRUD operations for usuarios
    addUsuario(usuario) {
        usuario.id = this.generateId();
        this.state.usuarios.push(usuario);
        this.persistIfEnabled();
        this.notify('usuarios', this.state.usuarios);
        return usuario;
    }

    updateUsuario(id, updates) {
        const index = this.state.usuarios.findIndex(u => u.id === id);
        if (index !== -1) {
            this.state.usuarios[index] = { ...this.state.usuarios[index], ...updates };
            this.persistIfEnabled();
            this.notify('usuarios', this.state.usuarios);
            return this.state.usuarios[index];
        }
        return null;
    }

    deleteUsuario(id) {
        const index = this.state.usuarios.findIndex(u => u.id === id);
        if (index !== -1) {
            const deleted = this.state.usuarios.splice(index, 1)[0];
            this.persistIfEnabled();
            this.notify('usuarios', this.state.usuarios);
            return deleted;
        }
        return null;
    }

    // CRUD operations for visitantes
    addVisitante(visitante) {
        visitante.id = this.generateId();
        this.state.visitantes.push(visitante);
        this.persistIfEnabled();
        this.notify('visitantes', this.state.visitantes);
        return visitante;
    }

    updateVisitante(id, updates) {
        const index = this.state.visitantes.findIndex(v => v.id === id);
        if (index !== -1) {
            this.state.visitantes[index] = { ...this.state.visitantes[index], ...updates };
            this.persistIfEnabled();
            this.notify('visitantes', this.state.visitantes);
            return this.state.visitantes[index];
        }
        return null;
    }

    deleteVisitante(id) {
        const index = this.state.visitantes.findIndex(v => v.id === id);
        if (index !== -1) {
            const deleted = this.state.visitantes.splice(index, 1)[0];
            this.persistIfEnabled();
            this.notify('visitantes', this.state.visitantes);
            return deleted;
        }
        return null;
    }

    // CRUD operations for vagas
    addVaga(vaga) {
        vaga.id = this.generateId();
        this.state.vagas.push(vaga);
        this.persistIfEnabled();
        this.notify('vagas', this.state.vagas);
        return vaga;
    }

    updateVaga(id, updates) {
        const index = this.state.vagas.findIndex(v => v.id === id);
        if (index !== -1) {
            this.state.vagas[index] = { ...this.state.vagas[index], ...updates };
            this.persistIfEnabled();
            this.notify('vagas', this.state.vagas);
            return this.state.vagas[index];
        }
        return null;
    }

    deleteVaga(id) {
        const index = this.state.vagas.findIndex(v => v.id === id);
        if (index !== -1) {
            const deleted = this.state.vagas.splice(index, 1)[0];
            this.persistIfEnabled();
            this.notify('vagas', this.state.vagas);
            return deleted;
        }
        return null;
    }

    // Event operations
    addEvento(evento) {
        evento.id = this.generateId();
        evento.timestamp = evento.timestamp || Date.now();
        this.state.eventos.push(evento);
        this.persistIfEnabled();
        this.notify('eventos', this.state.eventos);
        return evento;
    }

    // Parking business logic
    processarEntrada(placa, categoria) {
        placa = this.normalizarPlaca(placa);
        
        // Validate plate
        if (!this.validarPlaca(placa)) {
            const evento = this.addEvento({
                placa,
                decisao: 'NEGADO',
                motivo: 'Placa inválida',
                vagaId: null
            });
            return { success: false, evento, motivo: 'Placa inválida' };
        }

        // Check if already inside
        const vagaOcupada = this.state.vagas.find(v => v.placaAtual === placa);
        if (vagaOcupada) {
            const evento = this.addEvento({
                placa,
                decisao: 'NEGADO',
                motivo: 'Veículo já está no estacionamento',
                vagaId: vagaOcupada.id
            });
            return { success: false, evento, motivo: 'Veículo já está no estacionamento' };
        }

        // Find compatible available spot
        const vagaDisponivel = this.encontrarVagaCompativel(categoria);
        
        if (!vagaDisponivel) {
            const evento = this.addEvento({
                placa,
                decisao: 'NEGADO',
                motivo: 'Não há vagas compatíveis disponíveis',
                vagaId: null
            });
            return { success: false, evento, motivo: 'Não há vagas compatíveis disponíveis' };
        }

        // Occupy the spot
        this.updateVaga(vagaDisponivel.id, {
            status: 'OCUPADA',
            placaAtual: placa,
            desde: Date.now()
        });

        const evento = this.addEvento({
            placa,
            decisao: 'AUTORIZADO',
            motivo: `Vaga ${vagaDisponivel.numero} atribuída`,
            vagaId: vagaDisponivel.id
        });

        return { 
            success: true, 
            evento, 
            vaga: this.state.vagas.find(v => v.id === vagaDisponivel.id),
            motivo: `Autorizado - Vaga ${vagaDisponivel.numero}`
        };
    }

    liberarVaga(vagaId) {
        const vaga = this.state.vagas.find(v => v.id === vagaId);
        if (!vaga || vaga.status !== 'OCUPADA') {
            return { success: false, motivo: 'Vaga não está ocupada' };
        }

        const placa = vaga.placaAtual;
        
        this.updateVaga(vagaId, {
            status: 'LIVRE',
            placaAtual: null,
            desde: null
        });

        const evento = this.addEvento({
            placa,
            decisao: 'MANUAL',
            motivo: `Vaga ${vaga.numero} liberada manualmente`,
            vagaId
        });

        return { success: true, evento, vaga };
    }

    encontrarVagaCompativel(categoria) {
        const vagasLivres = this.state.vagas.filter(v => v.status === 'LIVRE');
        
        // Priority rules
        const prioridades = {
            'FUNCIONARIO': ['FUNCIONARIO', 'COMUM'],
            'PCD': ['PCD', 'COMUM'],
            'VISITANTE_ESPECIAL': ['VISITANTE_ESPECIAL', 'COMUM'],
            'ALUNO': ['COMUM'],
            'VISITANTE_COMUM': ['COMUM']
        };

        const tiposPermitidos = prioridades[categoria] || ['COMUM'];
        
        for (const tipo of tiposPermitidos) {
            const vaga = vagasLivres.find(v => v.tipo === tipo);
            if (vaga) {
                return vaga;
            }
        }

        return null;
    }

    // Utilities
    normalizarPlaca(placa) {
        return (placa || '').toUpperCase().replace(/[^A-Z0-9]/g, '');
    }

    validarPlaca(placa) {
        const normalizada = this.normalizarPlaca(placa);
        return /^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$/.test(normalizada) || 
               /^[A-Z]{3}[0-9]{4}$/.test(normalizada);
    }

    generateId() {
        return Date.now().toString(36) + Math.random().toString(36).substr(2);
    }

    // Persistence
    persistIfEnabled() {
        if (this.state.prefs.persist) {
            try {
                localStorage.setItem('unipark_data', JSON.stringify(this.state));
            } catch (error) {
                console.error('Failed to persist data:', error);
            }
        }
    }

    loadFromStorage() {
        if (typeof localStorage === 'undefined') return false;
        
        try {
            const saved = localStorage.getItem('unipark_data');
            if (saved) {
                const data = JSON.parse(saved);
                this.state = { ...this.state, ...data };
                return true;
            }
        } catch (error) {
            console.error('Failed to load data from storage:', error);
        }
        return false;
    }

    clearStorage() {
        try {
            localStorage.removeItem('unipark_data');
        } catch (error) {
            console.error('Failed to clear storage:', error);
        }
    }

    // Event system
    subscribe(event, callback) {
        if (!this.listeners.has(event)) {
            this.listeners.set(event, []);
        }
        this.listeners.get(event).push(callback);
        
        // Return unsubscribe function
        return () => {
            const callbacks = this.listeners.get(event);
            if (callbacks) {
                const index = callbacks.indexOf(callback);
                if (index > -1) {
                    callbacks.splice(index, 1);
                }
            }
        };
    }

    notify(event, data) {
        const callbacks = this.listeners.get(event);
        if (callbacks) {
            callbacks.forEach(callback => {
                try {
                    callback(data);
                } catch (error) {
                    console.error('Error in event callback:', error);
                }
            });
        }
    }
}

export { Store };