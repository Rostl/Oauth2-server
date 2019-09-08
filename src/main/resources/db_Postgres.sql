SET SEARCH_PATH = oauth_server;

CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id               CHARACTER VARYING(255) PRIMARY KEY,
    client_secret           CHARACTER VARYING(12) NOT NULL,
    web_server_redirect_uri CHARACTER VARYING(2048) DEFAULT NULL,
    scope                   CHARACTER VARYING(255)  DEFAULT NULL,
    access_token_validity   INTEGER                 DEFAULT NULL,
    refresh_token_validity  INTEGER                 DEFAULT NULL,
    resource_ids            CHARACTER VARYING(1024) DEFAULT NULL,
    authorized_grant_types  CHARACTER VARYING(1024) DEFAULT NULL,
    authorities             CHARACTER VARYING(1024) DEFAULT NULL,
    additional_information  CHARACTER VARYING(4096) DEFAULT NULL,
    autoapprove             CHARACTER VARYING(255)  DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS permission
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(512) UNIQUE DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS role
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(255) UNIQUE DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS system_user
(
    id                      SERIAL PRIMARY KEY,
    username                CHARACTER VARYING(100) UNIQUE NOT NULL,
    password                CHARACTER VARYING(1024)       NOT NULL,
    email                   CHARACTER VARYING(1024)       NOT NULL,
    enable                  INTEGER                       NOT NULL,
    account_non_expired     INTEGER                       NOT NULL,
    credentials_non_expired INTEGER                       NOT NULL,
    account_non_locked      INTEGER                       NOT NULL
);

CREATE TABLE IF NOT EXISTS permission_role
(
    permission_id INTEGER DEFAULT NULL,
    role_id       INTEGER DEFAULT NULL,
    FOREIGN KEY (permission_id) REFERENCES permission (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE IF NOT EXISTS role_user
(
    role_id INTEGER DEFAULT NULL,
    user_id INTEGER DEFAULT NULL,
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (user_id) REFERENCES system_user (id)
);

CREATE TABLE IF NOT EXISTS oauth_client_token
(
    token_id          CHARACTER VARYING(256),
    token             BYTEA,
    authentication_id CHARACTER VARYING(256) PRIMARY KEY,
    user_name         CHARACTER VARYING(256),
    client_id         CHARACTER VARYING(256)
);

CREATE TABLE IF NOT EXISTS oauth_access_token
(
    token_id          CHARACTER VARYING(256),
    token             BYTEA,
    authentication_id CHARACTER VARYING(256) PRIMARY KEY,
    user_name         CHARACTER VARYING(256),
    client_id         CHARACTER VARYING(256),
    authentication    BYTEA,
    refresh_token     CHARACTER VARYING(256)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token
(
    token_id       CHARACTER VARYING(256),
    token          BYTEA,
    authentication BYTEA
);

CREATE TABLE IF NOT EXISTS oauth_code
(
    code           CHARACTER VARYING(256),
    authentication BYTEA
);

CREATE TABLE IF NOT EXISTS oauth_approvals
(
    userId         CHARACTER VARYING(256),
    clientId       CHARACTER VARYING(256),
    scope          CHARACTER VARYING(256),
    status         CHARACTER VARYING(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);
