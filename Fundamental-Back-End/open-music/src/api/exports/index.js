import routes from "./routes.js";
import Handler from "./handler.js";

export default {
  name: "exports",
  version: "1.0.0",
  register: async (server, { rabbitmqService, playlistPostgres }) => {
    const handler = new Handler(rabbitmqService, playlistPostgres);

    server.route(routes(handler));
  },
};
