import { nanoid } from 'nanoid';
import books from '../assets/books.js';
import {
  internalServerErrorResponse,
  notFoundResponse,
  successResponse,
} from './responseHandler.js';

export const getBookHandler = (request) => {
  let { name = '', reading, finished } = request.query;
  let booksFiltered = books.filter((book) => {
    if (reading === '1') {
      return book.reading;
    }
    if (reading === '0') {
      return !book.reading;
    }
    return true;
  });
  booksFiltered = booksFiltered.filter((book) => {
    if (finished === '1') {
      return book.finished;
    }
    if (finished === '0') {
      return !book.finished;
    }
    return true;
  });
  booksFiltered = booksFiltered.filter((book) => {
    if (name) {
      return book.name.toLowerCase().includes(name.toLowerCase());
    }
    return true;
  });

  const res = booksFiltered.map((book) => {
    return { id: book.id, name: book.name, publisher: book.publisher };
  });

  return successResponse({ books: res });
};

export const getBookByIdHandler = (request, h) => {
  const { id } = request.params;

  const res = books.find((book) => book.id === id);

  if (res) {
    return successResponse({ book: res });
  }

  return h.response(notFoundResponse('Buku tidak ditemukan')).code(404);
};

export const addBookHandler = (request, h) => {
  try {
    const { payload } = request;
    const id = nanoid();
    const now = new Date().toISOString();
    const finished = payload.readPage === payload.pageCount;

    books.push({
      ...payload,
      insertedAt: now,
      updatedAt: now,
      finished,
      id,
    });

    return h
      .response({
        ...successResponse({ bookId: id }),
        message: 'Buku berhasil ditambahkan',
      })
      .code(201);
  } catch (error) {
    return h
      .response(internalServerErrorResponse('Buku gagal ditambahkan'))
      .code(500);
  }
};

export const editBookHandler = (request, h) => {
  try {
    const {
      payload,
      params: { id },
    } = request;
    const now = new Date().toISOString();
    const finished = payload.readPage === payload.pageCount;
    const bookIndex = books.findIndex((book) => book.id === id);
    let res = books.find((book) => book.id === id);
    if (!res) {
      const response = notFoundResponse(
        'Gagal memperbarui buku. Id tidak ditemukan'
      );

      return h.response(response).code(404);
    }

    res = {
      ...res,
      ...payload,
      updatedAt: now,
      finished,
    };

    books.splice(bookIndex, 1, res);

    return h
      .response({
        ...successResponse({ bookId: id }),
        message: 'Buku berhasil diperbarui',
      })
      .code(200);
  } catch (error) {
    return h
      .response(internalServerErrorResponse('Buku gagal diperbarui'))
      .code(500);
  }
};

export const deleteBookByIdHandler = (request, h) => {
  const { id } = request.params;

  const bookIndex = books.findIndex((book) => book.id === id);

  if (bookIndex >= 0) {
    books.splice(books.indexOf(bookIndex), 1);

    return { ...successResponse(), message: 'Buku berhasil dihapus' };
  }

  return h
    .response(notFoundResponse('Buku gagal dihapus. Id tidak ditemukan'))
    .code(404);
};
