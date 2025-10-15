// UI utilities and components
class UI {
    constructor() {
        this.toastContainer = document.getElementById('toastContainer');
        this.modalContainer = document.getElementById('modalContainer');
        this.toastCounter = 0;
    }

    // Toast notifications
    showToast(message, type = 'info', duration = 5000) {
        const toast = this.createToast(message, type);
        this.toastContainer.appendChild(toast);
        
        // Show toast
        setTimeout(() => toast.classList.add('show'), 100);
        
        // Auto remove
        setTimeout(() => {
            this.removeToast(toast);
        }, duration);
        
        return toast;
    }

    createToast(message, type) {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.id = `toast-${++this.toastCounter}`;
        
        const icon = this.getToastIcon(type);
        
        toast.innerHTML = `
            <span class="toast-icon">${icon}</span>
            <span class="toast-message">${message}</span>
            <button class="toast-close" onclick="this.parentElement.remove()" aria-label="Fechar">×</button>
        `;
        
        return toast;
    }

    getToastIcon(type) {
        const icons = {
            success: '✅',
            error: '❌',
            warning: '⚠️',
            info: 'ℹ️'
        };
        return icons[type] || icons.info;
    }

    removeToast(toast) {
        toast.classList.remove('show');
        setTimeout(() => {
            if (toast.parentElement) {
                toast.parentElement.removeChild(toast);
            }
        }, 300);
    }

    // Modal management
    showModal(content, options = {}) {
        const modal = this.createModal(content, options);
        this.modalContainer.appendChild(modal);
        
        // Focus management
        this.trapFocus(modal);
        
        return modal;
    }

    createModal(content, options = {}) {
        const modal = document.createElement('div');
        modal.className = 'modal-overlay';
        modal.setAttribute('role', 'dialog');
        modal.setAttribute('aria-modal', 'true');
        
        const { title, className = '', closable = true } = options;
        
        modal.innerHTML = `
            <div class="modal-content ${className}">
                ${title ? `<div class="modal-header">
                    <h2 class="modal-title">${title}</h2>
                    ${closable ? '<button class="modal-close" aria-label="Fechar">×</button>' : ''}
                </div>` : ''}
                <div class="modal-body">
                    ${content}
                </div>
            </div>
        `;
        
        // Event listeners
        if (closable) {
            const closeBtn = modal.querySelector('.modal-close');
            closeBtn?.addEventListener('click', () => this.closeModal(modal));
            
            modal.addEventListener('click', (e) => {
                if (e.target === modal) {
                    this.closeModal(modal);
                }
            });
        }
        
        // ESC key
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && closable) {
                this.closeModal(modal);
            }
        });
        
        return modal;
    }

    closeModal(modal) {
        if (modal.parentElement) {
            modal.parentElement.removeChild(modal);
        }
    }

    trapFocus(modal) {
        const focusableElements = modal.querySelectorAll(
            'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
        );
        
        if (focusableElements.length === 0) return;
        
        const firstElement = focusableElements[0];
        const lastElement = focusableElements[focusableElements.length - 1];
        
        firstElement.focus();
        
        modal.addEventListener('keydown', (e) => {
            if (e.key === 'Tab') {
                if (e.shiftKey) {
                    if (document.activeElement === firstElement) {
                        e.preventDefault();
                        lastElement.focus();
                    }
                } else {
                    if (document.activeElement === lastElement) {
                        e.preventDefault();
                        firstElement.focus();
                    }
                }
            }
        });
    }

    // Table utilities
    createTable(data, columns, options = {}) {
        const {
            sortable = true,
            filterable = false,
            pageable = false,
            pageSize = 10,
            className = '',
            onRowClick = null
        } = options;

        const table = document.createElement('table');
        table.className = `table ${className}`;

        // Create header
        const thead = document.createElement('thead');
        const headerRow = document.createElement('tr');

        columns.forEach(col => {
            const th = document.createElement('th');
            th.textContent = col.label;
            
            if (sortable && col.sortable !== false) {
                th.classList.add('sortable');
                th.style.cursor = 'pointer';
                th.addEventListener('click', () => {
                    this.sortTable(table, col.key);
                });
            }
            
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);
        table.appendChild(thead);

        // Create body
        const tbody = document.createElement('tbody');
        this.populateTableBody(tbody, data, columns, onRowClick);
        table.appendChild(tbody);

        return table;
    }

    populateTableBody(tbody, data, columns, onRowClick = null) {
        tbody.innerHTML = '';
        
        data.forEach(item => {
            const row = document.createElement('tr');
            
            if (onRowClick) {
                row.style.cursor = 'pointer';
                row.addEventListener('click', () => onRowClick(item));
            }
            
            columns.forEach(col => {
                const td = document.createElement('td');
                
                if (col.render) {
                    td.innerHTML = col.render(item[col.key], item);
                } else {
                    td.textContent = item[col.key] || '';
                }
                
                row.appendChild(td);
            });
            
            tbody.appendChild(row);
        });
    }

    sortTable(table, key) {
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.querySelectorAll('tr'));
        
        const isAscending = table.dataset.sortOrder !== 'asc';
        table.dataset.sortOrder = isAscending ? 'asc' : 'desc';
        
        rows.sort((a, b) => {
            const aValue = a.cells[0].textContent; // Simplified - would need column index
            const bValue = b.cells[0].textContent;
            
            if (isAscending) {
                return aValue.localeCompare(bValue);
            } else {
                return bValue.localeCompare(aValue);
            }
        });
        
        rows.forEach(row => tbody.appendChild(row));
    }

    // Form utilities
    createForm(fields, onSubmit, options = {}) {
        const form = document.createElement('form');
        form.className = options.className || '';
        
        fields.forEach(field => {
            const group = this.createFormGroup(field);
            form.appendChild(group);
        });
        
        if (onSubmit) {
            form.addEventListener('submit', (e) => {
                e.preventDefault();
                const formData = new FormData(form);
                const data = Object.fromEntries(formData.entries());
                onSubmit(data, form);
            });
        }
        
        return form;
    }

    createFormGroup(field) {
        const group = document.createElement('div');
        group.className = 'form-group';
        
        const label = document.createElement('label');
        label.className = 'form-label';
        label.textContent = field.label;
        label.setAttribute('for', field.name);
        
        let input;
        
        switch (field.type) {
            case 'select':
                input = document.createElement('select');
                input.className = 'form-select';
                
                if (field.options) {
                    if (field.placeholder) {
                        const option = document.createElement('option');
                        option.value = '';
                        option.textContent = field.placeholder;
                        input.appendChild(option);
                    }
                    
                    field.options.forEach(opt => {
                        const option = document.createElement('option');
                        option.value = opt.value;
                        option.textContent = opt.label;
                        input.appendChild(option);
                    });
                }
                break;
                
            case 'textarea':
                input = document.createElement('textarea');
                input.className = 'form-textarea';
                if (field.rows) input.rows = field.rows;
                break;
                
            default:
                input = document.createElement('input');
                input.type = field.type || 'text';
                input.className = 'form-input';
                if (field.placeholder) input.placeholder = field.placeholder;
        }
        
        input.name = field.name;
        input.id = field.name;
        
        if (field.required) {
            input.required = true;
        }
        
        if (field.value !== undefined) {
            input.value = field.value;
        }

        group.appendChild(label);
        group.appendChild(input);
        
        return group;
    }

    // CSV utilities
    downloadCSV(data, filename) {
        if (!data.length) return;
        
        const headers = Object.keys(data[0]);
        const csv = [
            headers.join(','),
            ...data.map(row => headers.map(header => {
                const value = row[header] || '';
                return `"${String(value).replace(/"/g, '""')}"`;
            }).join(','))
        ].join('\n');
        
        const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        
        if (link.download !== undefined) {
            const url = URL.createObjectURL(blob);
            link.setAttribute('href', url);
            link.setAttribute('download', filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }

    // Formatting utilities
    formatDate(timestamp, options = {}) {
        const date = new Date(timestamp);
        const defaultOptions = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        };
        
        return date.toLocaleDateString('pt-BR', { ...defaultOptions, ...options });
    }

    formatPlaca(placa) {
        if (!placa) return '';
        const clean = placa.replace(/[^A-Z0-9]/g, '');
        
        if (clean.length === 7) {
            return `${clean.slice(0, 3)}-${clean.slice(3)}`;
        } else if (clean.length === 8) {
            return `${clean.slice(0, 3)}-${clean.slice(3, 4)}${clean.slice(4, 5)}${clean.slice(5)}`;
        }
        
        return placa;
    }

    // Badge utilities
    createBadge(text, type = 'info') {
        const badge = document.createElement('span');
        badge.className = `badge badge-${type}`;
        badge.textContent = text;
        return badge;
    }

    // Loading utilities
    showLoading(container, message = 'Carregando...') {
        const loading = document.createElement('div');
        loading.className = 'loading-indicator';
        loading.innerHTML = `
            <div class="loading-spinner"></div>
            <span>${message}</span>
        `;
        
        container.innerHTML = '';
        container.appendChild(loading);
    }

    hideLoading(container) {
        const loading = container.querySelector('.loading-indicator');
        if (loading) {
            loading.remove();
        }
    }
}

export { UI };