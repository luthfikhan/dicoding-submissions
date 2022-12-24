import Joi from 'joi';
import {
  addBookHandler,
  deleteBookByIdHandler,
  editBookHandler,
  getBookByIdHandler,
  getBookHandler,
} from '../handlers/bookHandlers.js';

const baseurl = '/books';

const validate = (message) => {
  return {
    failAction: (request, h, err) => {
      const { name, readPage, pageCount } = request.payload;

      if (!name) {
        err.output.payload = {
          status: 'fail',
          message: `${message}. Mohon isi nama buku`,
        };
      }
      if (readPage > pageCount) {
        err.output.payload = {
          status: 'fail',
          message: `${message}. readPage tidak boleh lebih besar dari pageCount`,
        };
      }

      return err;
    },
    payload: Joi.object({
      name: Joi.string().required(),
      pageCount: Joi.number()
        .greater(Joi.ref('readPage'))
        .allow(Joi.ref('readPage')),
    })
      .required()
      .unknown(),
  };
};

export default [
  {
    method: 'POST',
    path: `${baseurl}`,
    handler: addBookHandler,
    options: {
      validate: validate('Gagal menambahkan buku'),
    },
  },
  {
    method: 'GET',
    path: `${baseurl}`,
    handler: getBookHandler,
  },
  {
    method: 'GET',
    path: `${baseurl}/{id}`,
    handler: getBookByIdHandler,
  },
  {
    method: 'PUT',
    path: `${baseurl}/{id}`,
    handler: editBookHandler,
    options: {
      validate: validate('Gagal memperbarui buku'),
    },
  },
  {
    method: 'DELETE',
    path: `${baseurl}/{id}`,
    handler: deleteBookByIdHandler,
  },
];
