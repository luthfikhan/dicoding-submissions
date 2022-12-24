exports.up = (pgm) => {
  pgm.createTable("albums", {
    id: { type: "VARCHAR(50)", primaryKey: true },
    name: { type: "text", notNull: true },
    year: { type: "integer", notNull: true },
  });

  pgm.createTable("songs", {
    id: { type: "VARCHAR(50)", primaryKey: true },
    title: { type: "text", notNull: true },
    year: { type: "integer", notNull: true },
    performer: { type: "text", notNull: true },
    genre: { type: "text", notNull: true },
    duration: { type: "integer", notNull: false },
    album_id: {
      type: "VARCHAR(50)",
      notNull: false,
      references: "albums",
      onDelete: "SET NULL",
    },
  });
};

exports.down = (pgm) => {
  pgm.dropTable("albums");
  pgm.dropTable("songs");
};
