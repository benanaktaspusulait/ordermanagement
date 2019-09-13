drop sequence if exists  hibernate_sequence;
drop table if exists  oauth_client_details;
drop table if exists  oauth_client_token;
drop table if exists  oauth_access_token;
drop table if exists  oauth_code ;
drop table if exists  oauth_approvals;
drop table if exists  OAUTH_REFRESH_TOKEN;

create sequence hibernate_sequence;


create table oauth_client_details (
                                      client_id VARCHAR(256) PRIMARY KEY,
                                      resource_ids VARCHAR(256),
                                      client_secret VARCHAR(256),
                                      scope VARCHAR(256),
                                      authorized_grant_types VARCHAR(256),
                                      web_server_redirect_uri VARCHAR(256),
                                      authorities VARCHAR(256),
                                      access_token_validity INTEGER,
                                      refresh_token_validity INTEGER,
                                      additional_information VARCHAR(4096),
                                      autoapprove VARCHAR(256)
);

create table oauth_client_token (
                                    token_id VARCHAR(256),
                                    token bytea,
                                    authentication_id VARCHAR(256) PRIMARY KEY,
                                    user_name VARCHAR(256),
                                    client_id VARCHAR(256)
);

create table oauth_access_token (
                                    token_id VARCHAR(256),
                                    token bytea,
                                    authentication_id VARCHAR(256) PRIMARY KEY,
                                    user_name VARCHAR(256),
                                    client_id VARCHAR(256),
                                    authentication bytea,
                                    refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
                                     token_id VARCHAR(256),
                                     token bytea,
                                     authentication bytea
);

create table oauth_code (
                            code VARCHAR(256), authentication bytea
);

create table oauth_approvals (
                                 userId VARCHAR(256),
                                 clientId VARCHAR(256),
                                 scope VARCHAR(256),
                                 status VARCHAR(10),
                                 expiresAt TIMESTAMP,
                                 lastModifiedAt TIMESTAMP
);