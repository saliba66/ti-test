export function seedData(store) {
  // Seed minimal data so the UI renders before backend is ready
  const vagas = Array.from({ length: 6 }).map((_, idx) => ({
    id: `${Date.now()}_${idx}`,
    numero: String(idx + 1),
    tipo: idx % 3 === 0 ? 'COMUM' : (idx % 3 === 1 ? 'FUNCIONARIO' : 'PCD'),
    status: 'LIVRE',
    placaAtual: null,
    desde: null
  }));
  store.setVagas(vagas);
}