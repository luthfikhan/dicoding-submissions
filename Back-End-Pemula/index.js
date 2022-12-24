/* eslint-disable no-console */
import Hapi from '@hapi/hapi';
import routes from './routes.js';
import { notFoundResponse } from './src/handlers/responseHandler.js';

const init = async () => {
  const server = Hapi.server({
    port: 5000,
    host: 'localhost',
    routes: {
      cors: {
        origin: ['*'],
      },
      validate: {
        failAction: (request, h, err) => err,
      },
    },
  });

  server.route([
    ...routes,
    {
      method: '*',
      path: '/{any*}',
      handler: (request, h) =>
        h.response(notFoundResponse('404 not found')).code(404),
    },
  ]);

  await server.start();
  console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
  console.log(err);
  process.exit(1);
});

init();
