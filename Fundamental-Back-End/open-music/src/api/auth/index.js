import routes from "./routes.js";
import Handler from "./handler.js";

export default {
  name: "auth",
  version: "1.0.0",
  register: async (
    server,
    { usersPostgres, authenticationsPostgresService }
  ) => {
    const handler = new Handler(usersPostgres, authenticationsPostgresService);
    server.route(routes(handler));
  },
};
