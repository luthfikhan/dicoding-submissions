exports.up = (pgm) => {
  pgm.createTable("users", {
    id: { type: "VARCHAR(50)", primaryKey: true },
    username: { type: "text", notNull: true },
    password: { type: "text", notNull: true },
    fullname: { type: "text", notNull: true },
  });

  pgm.createTable("authentications", {
    tokenid: { type: "text", notNull: true },
  });
};

exports.down = (pgm) => {
  pgm.dropTable("users");
  pgm.dropTable("authentications");
};
