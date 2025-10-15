import { listVagas } from '../services/api.js';

export function render() {
  return `
    <section>
      <h1>Vagas</h1>
      <div id="vagasContainer">Carregando...</div>
    </section>
  `;
}

export function mount() {
  const container = document.getElementById('vagasContainer');
  listVagas()
    .then(vagas => {
      container.innerHTML = `
        <table class="table">
          <thead><tr><th>#</th><th>Tipo</th><th>Status</th><th>Placa</th></tr></thead>
          <tbody>
            ${vagas.map(v => `
              <tr>
                <td>${v.numero}</td>
                <td>${v.tipo}</td>
                <td>${v.status}</td>
                <td>${v.placaAtual || ''}</td>
              </tr>
            `).join('')}
          </tbody>
        </table>
      `;
    })
    .catch(() => {
      container.textContent = 'Erro ao carregar vagas';
    });
}
