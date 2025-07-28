-- schema.sql
DROP TABLE if exists authorities;
DROP TABLE if exists users;
CREATE Table users (
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(50) not null,
    enabled boolean not null
);

DROP TABLE if exists authorities;
create table authorities(
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    primary key (username, authority),
    constraint fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username on authorities (username, authority);

-- data.sql
-- Users table
INSERT INTO users (username, password, enabled)
VALUES ('user', 'foouser', true);
INSERT INTO users (username, password, enabled)
VALUES ('user1', 'foouser1', true);
INSERT INTO users (username, password, enabled)
VALUES ('user2', 'foouser2', true);
INSERT INTO users (username, password, enabled)
VALUES ('admin', 'fooadmin', true);

-- Authorities table
INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority)
VALUES ('user1', 'ROLE_USER');
INSERT INTO authorities (username, authority)
VALUES ('user2', 'ROLE_USER');
INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_USER'); -- Same admin user with ROLE_USER as well.
