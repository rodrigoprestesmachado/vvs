const API_BASE = '/books';

async function parseErrorMessage(response) {
  const contentType = response.headers.get('content-type') || '';
  if (contentType.includes('application/json')) {
    const body = await response.json();
    if (body && typeof body.message === 'string') {
      return body.message;
    }
  }
  return response.statusText || 'Erro na requisição';
}

async function request(url, options = {}) {
  const response = await fetch(url, {
    headers: {
      Accept: 'application/json',
      ...(options.body ? { 'Content-Type': 'application/json' } : {}),
      ...options.headers,
    },
    ...options,
  });

  if (!response.ok) {
    throw new Error(await parseErrorMessage(response));
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export function loadBooks() {
  return request(API_BASE);
}

export function createBook(book) {
  return request(API_BASE, {
    method: 'POST',
    body: JSON.stringify(book),
  });
}

export function updateBook(isbn, book) {
  return request(`${API_BASE}/${encodeURIComponent(isbn)}`, {
    method: 'PUT',
    body: JSON.stringify(book),
  });
}

export function deleteBook(isbn) {
  return request(`${API_BASE}/${encodeURIComponent(isbn)}`, {
    method: 'DELETE',
  });
}
