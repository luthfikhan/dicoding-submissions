import routes from "./routes.js";
import Handler from "./handler.js";

export default {
  name: "playlist",
  version: "1.0.0",
  register: async (
    server,
    { playlistPostgres, songPostgres, usersPostgres }
  ) => {
    const handler = new Handler(playlistPostgres, songPostgres, usersPostgres);

    server.route(routes(handler));
  },
};
