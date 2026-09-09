CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    name     VARCHAR(100),
    enabled  BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,

    CONSTRAINT fk_authorities_users
        FOREIGN KEY (username)
            REFERENCES users (username)
            ON DELETE CASCADE,

    CONSTRAINT uq_authorities
        UNIQUE (username, authority)
);