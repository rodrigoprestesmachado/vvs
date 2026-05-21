<script setup>
import { onMounted, ref } from 'vue';
import {
  createBook,
  deleteBook,
  loadBooks,
  updateBook,
} from './api/books.js';

const books = ref([]);
const loading = ref(false);
const saving = ref(false);
const errorMessage = ref('');
const successMessage = ref('');
const modalOpen = ref(false);
const editingIsbn = ref(null);
const modalErrorMessage = ref('');

const emptyForm = () => ({
  isbn: '',
  title: '',
  author: '',
  publicationYear: new Date().getFullYear(),
  copiesAvailable: 1,
});

const form = ref(emptyForm());

function clearMessages() {
  errorMessage.value = '';
  successMessage.value = '';
}

function showError(message) {
  successMessage.value = '';
  errorMessage.value = message;
}

function showSuccess(message) {
  errorMessage.value = '';
  successMessage.value = message;
}

async function refreshBooks() {
  loading.value = true;
  clearMessages();
  try {
    books.value = await loadBooks();
  } catch (err) {
    showError(err.message);
  } finally {
    loading.value = false;
  }
}

function openCreateModal() {
  editingIsbn.value = null;
  form.value = emptyForm();
  modalOpen.value = true;
  modalErrorMessage.value = '';
  clearMessages();
}

function openEditModal(book) {
  editingIsbn.value = book.isbn;
  form.value = {
    isbn: book.isbn,
    title: book.title,
    author: book.author,
    publicationYear: book.publicationYear,
    copiesAvailable: book.copiesAvailable,
  };
  modalOpen.value = true;
  modalErrorMessage.value = '';
  clearMessages();
}

function closeModal() {
  modalOpen.value = false;
  editingIsbn.value = null;
  form.value = emptyForm();
  modalErrorMessage.value = '';
}

function normalizeIsbn(value) {
  return value.trim().replace(/[\s-]/g, '');
}

function buildPayload() {
  return {
    isbn: normalizeIsbn(form.value.isbn),
    title: form.value.title.trim(),
    author: form.value.author.trim(),
    publicationYear: Number(form.value.publicationYear),
    copiesAvailable: Number(form.value.copiesAvailable),
  };
}

async function saveBook() {
  saving.value = true;
  modalErrorMessage.value = '';
  const payload = buildPayload();

  try {
    if (editingIsbn.value) {
      await updateBook(editingIsbn.value, payload);
      showSuccess('Livro atualizado com sucesso.');
    } else {
      await createBook(payload);
      showSuccess('Livro criado com sucesso.');
    }
    closeModal();
    await refreshBooks();
  } catch (err) {
    modalErrorMessage.value = err.message;
  } finally {
    saving.value = false;
  }
}

async function removeBook(isbn) {
  if (!window.confirm(`Remover o livro ${isbn}?`)) {
    return;
  }

  clearMessages();
  try {
    await deleteBook(isbn);
    showSuccess('Livro removido com sucesso.');
    await refreshBooks();
  } catch (err) {
    showError(err.message);
  }
}

onMounted(refreshBooks);
</script>

<template>
  <section class="section">
    <div class="container">
      <h1 class="title">Cadastro de Livros</h1>
      <p class="subtitle">CRUD sobre a API REST <code>/books</code></p>

      <div
        v-if="errorMessage"
        class="notification is-danger is-light"
        role="alert"
      >
        {{ errorMessage }}
      </div>
      <div
        v-if="successMessage"
        class="notification is-success is-light"
        role="status"
      >
        {{ successMessage }}
      </div>

      <div class="buttons mb-4">
        <button
          type="button"
          class="button is-primary"
          @click="openCreateModal"
        >
          Novo livro
        </button>
        <button
          type="button"
          class="button is-light"
          :class="{ 'is-loading': loading }"
          :disabled="loading"
          @click="refreshBooks"
        >
          Atualizar lista
        </button>
      </div>

      <div v-if="loading" class="has-text-centered py-5">
        <p>Carregando livros...</p>
      </div>

      <div v-else-if="books.length === 0" class="notification is-info is-light">
        Nenhum livro cadastrado. Clique em &quot;Novo livro&quot; para começar.
      </div>

      <div v-else class="table-container">
        <table class="table is-fullwidth is-striped is-hoverable">
          <thead>
            <tr>
              <th>ISBN</th>
              <th>Título</th>
              <th>Autor</th>
              <th>Ano</th>
              <th>Exemplares</th>
              <th class="has-text-right">Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="book in books" :key="book.isbn">
              <td><code>{{ book.isbn }}</code></td>
              <td>{{ book.title }}</td>
              <td>{{ book.author }}</td>
              <td>{{ book.publicationYear }}</td>
              <td>{{ book.copiesAvailable }}</td>
              <td class="has-text-right">
                <div class="buttons are-small is-right">
                  <button
                    type="button"
                    class="button is-info is-light"
                    @click="openEditModal(book)"
                  >
                    Editar
                  </button>
                  <button
                    type="button"
                    class="button is-danger is-light"
                    @click="removeBook(book.isbn)"
                  >
                    Excluir
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>

  <div class="modal" :class="{ 'is-active': modalOpen }">
    <div class="modal-background" @click="closeModal"></div>
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">
          {{ editingIsbn ? 'Editar livro' : 'Novo livro' }}
        </p>
        <button
          type="button"
          class="delete"
          aria-label="Fechar"
          @click="closeModal"
        ></button>
      </header>
      <section class="modal-card-body">
        <div
          v-if="modalErrorMessage"
          class="notification is-danger is-light mb-4"
          role="alert"
        >
          {{ modalErrorMessage }}
        </div>
        <form @submit.prevent="saveBook">
          <div class="field">
            <label class="label" for="isbn">ISBN-10</label>
            <div class="control">
              <input
                id="isbn"
                v-model="form.isbn"
                class="input"
                type="text"
                required
                maxlength="15"
                :disabled="!!editingIsbn"
                placeholder="Ex.: 1-56881-111-X ou 156881111X"
              />
            </div>
            <p v-if="editingIsbn" class="help">
              O ISBN não pode ser alterado após o cadastro.
            </p>
          </div>

          <div class="field">
            <label class="label" for="title">Título</label>
            <div class="control">
              <input
                id="title"
                v-model="form.title"
                class="input"
                type="text"
                required
              />
            </div>
          </div>

          <div class="field">
            <label class="label" for="author">Autor</label>
            <div class="control">
              <input
                id="author"
                v-model="form.author"
                class="input"
                type="text"
                required
              />
            </div>
          </div>

          <div class="field">
            <label class="label" for="publicationYear">Ano de publicação</label>
            <div class="control">
              <input
                id="publicationYear"
                v-model.number="form.publicationYear"
                class="input"
                type="number"
                required
                min="1000"
                max="9999"
              />
            </div>
          </div>

          <div class="field">
            <label class="label" for="copiesAvailable">Exemplares</label>
            <div class="control">
              <input
                id="copiesAvailable"
                v-model.number="form.copiesAvailable"
                class="input"
                type="number"
                required
                min="0"
              />
            </div>
          </div>
        </form>
      </section>
      <footer class="modal-card-foot">
        <button
          type="button"
          class="button is-primary"
          :class="{ 'is-loading': saving }"
          :disabled="saving"
          @click="saveBook"
        >
          Salvar
        </button>
        <button type="button" class="button" @click="closeModal">
          Cancelar
        </button>
      </footer>
    </div>
  </div>
</template>
