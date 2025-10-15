import { getHealth, listVagas } from '../services/api.js';

let state = { status: null, vagasCount: 0 };

export function render() {
  return `
    <section>
      <h1>Dashboard</h1>
      <div class="cards">
        <div class="card"><div class="card-title">Backend</div><div id="healthStatus">Verificando...</div></div>
        <div class="card"><div class="card-title">Vagas</div><div id="vagasCount">...</div></div>
      </div>
    </section>
  `;
}

export function mount() {
  const healthEl = document.getElementById('healthStatus');
  const vagasEl = document.getElementById('vagasCount');

  getHealth()
    .then(json => {
      state.status = json.status;
      healthEl.textContent = `Status: ${json.status}`;
    })
    .catch(() => {
      healthEl.textContent = 'Indisponível';
    });

  listVagas()
    .then(vagas => {
      state.vagasCount = vagas.length;
      vagasEl.textContent = `${vagas.length} vagas`;
    })
    .catch(() => {
      vagasEl.textContent = 'Erro ao carregar';
    });
}
