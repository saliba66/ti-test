const API_BASE = (window.API_BASE_URL || 'http://localhost:8080');

export async function getHealth() {
  const res = await fetch(`${API_BASE}/api/health`);
  if (!res.ok) throw new Error('Health check failed');
  return res.json();
}

export async function listVagas() {
  const res = await fetch(`${API_BASE}/api/v1/vagas`);
  if (!res.ok) throw new Error('Falha ao carregar vagas');
  return res.json();
}
