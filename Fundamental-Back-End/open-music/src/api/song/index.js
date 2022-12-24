import routes from './routes.js';
import Handler from './handler.js';

export default {
  name: 'song',
  version: '1.0.0',
  register: async (server, { service }) => {
    const songHandler = new Handler(service);

    server.route(routes(songHandler))
  }
}