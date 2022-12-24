exports.up = (pgm) => {
  pgm.createTable("playlists", {
    id: { type: "VARCHAR(50)", primaryKey: true },
    name: { type: "text", notNull: true },
    owner: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "users",
      onDelete: "CASCADE",
    },
  });

  pgm.createTable("playlist_song", {
    playlist_id: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "playlists",
      onDelete: "CASCADE",
    },
    song_id: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "songs",
      onDelete: "CASCADE",
    },
  });

  pgm.createTable("playlist_song_activities", {
    playlist_id: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "playlists",
      onDelete: "CASCADE",
    },
    song_id: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "songs",
      onDelete: "CASCADE",
    },
    user_id: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "users",
      onDelete: "CASCADE",
    },
    action: {
      type: "text",
      notNull: true,
    },
    time: {
      type: "text",
      notNull: true,
    },
  });

  pgm.createTable("playlist_collaborations", {
    id: { type: "VARCHAR(50)", primaryKey: true },
    playlist_id: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "playlists",
      onDelete: "CASCADE",
    },
    user_id: {
      type: "VARCHAR(50)",
      notNull: true,
      references: "users",
      onDelete: "CASCADE",
    },
  });
};

exports.down = (pgm) => {
  pgm.dropTable("playlist_song");
  pgm.dropTable("playlist_song_activities");
  pgm.dropTable("playlist_collaborations");
  pgm.dropTable("playlists");
};
