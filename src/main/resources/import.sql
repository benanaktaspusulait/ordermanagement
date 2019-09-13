INSERT INTO "roles"("id","name") VALUES(1,'ROLE_USER');
INSERT INTO "roles"("id","name") VALUES(2,'ROLE_ADMIN');

INSERT INTO "users" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version", "email", "password") VALUES (1, '2019-09-01 12:43:51.292527', 1, 1,  null, null, 1,  'demo1@demo.com', '$2a$10$QMjpq46vUCS95F4ans7VtuRRHjA.utq8gzkVpQFVyKPpNBLDVq/fG');
INSERT INTO "users" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version","email", "password") VALUES (2, '2019-09-01 12:43:51.292527', 1, 1, null,  null, 1,   'demo2@demo.com', '$2a$10$QMjpq46vUCS95F4ans7VtuRRHjA.utq8gzkVpQFVyKPpNBLDVq/fG');
INSERT INTO "users" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version","email", "password") VALUES (3, '2019-09-01 12:43:51.292527', 1, 1, null,  null, 1,   'demo3@demo.com', '$2a$10$QMjpq46vUCS95F4ans7VtuRRHjA.utq8gzkVpQFVyKPpNBLDVq/fG');

INSERT INTO "user_role" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version", "role_id", "user_id") VALUES (1, '2019-09-01 17:06:03.857000', 1, 1, null,  null, 0, 1, 1);
INSERT INTO "user_role" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version", "role_id", "user_id") VALUES (2, '2019-09-01 17:06:03.857000', 1, 1, null,  null, 0, 2, 1);

INSERT INTO "user_role" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version", "role_id", "user_id") VALUES (3, '2019-09-01 17:06:03.857000', 1, 1, null,  null, 0, 1, 2);
INSERT INTO "user_role" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version", "role_id", "user_id") VALUES (4, '2019-09-01 17:06:03.857000', 1, 1, null,  null, 0, 2, 2);

INSERT INTO "user_role" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version", "role_id", "user_id") VALUES (5, '2019-09-01 17:06:03.857000', 1, 1, null,  null, 0, 1, 3);
INSERT INTO "user_role" ("id", "created_at", "create_user", "data_status",  "update_user", "updated_at", "version", "role_id", "user_id") VALUES (6, '2019-09-01 17:06:03.857000', 1, 1, null,  null, 0, 2, 3);

INSERT INTO oauth_client_details(client_id, client_secret, scope, authorized_grant_types,web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information) VALUES ('sampleClientId', 'secret', 'read,write,foo,bar',    'password,authorization_code,refresh_token', null, null, 36000, 36000, null);
