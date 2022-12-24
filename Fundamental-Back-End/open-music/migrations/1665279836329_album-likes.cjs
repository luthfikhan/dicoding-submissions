exports.up = (pgm) => {
  pgm.addColumns("albums", {
    coverurl: { type: "text", notNull: false, default: null },
  });

  pgm.createTable("user_album_like", {
    user_id: {
      type: "VARCHAR(50)",
      notNull: false,
      references: "users",
      onDelete: "CASCADE",
    },
    album_id: {
      type: "VARCHAR(50)",
      notNull: false,
      references: "albums",
      onDelete: "CASCADE",
    },
  });
};

exports.down = (pgm) => {
  pgm.dropTable("user_album_like");
};
