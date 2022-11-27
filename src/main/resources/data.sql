INSERT INTO role (name) VALUES ("USER"), ("MODERATOR"), ("ADMIN");

INSERT INTO authority (permission) VALUES ("READ"),("CREATE"), ("UPDATE"), ("DELETE");

INSERT INTO role_authority(role_id, authority_id) VALUES (1, 1), (2, 1), (2, 2), (2, 3), (3, 1), (3, 2), (3, 3), (3, 4);

INSERT INTO user (email, enabled, first_name, last_name, password, username)
VALUES ("jkob.matras@gmail.com", 1, "Jakub", "Matras", "$2a$12$85bXLekdSW3iFURCDii8xuTEovRJweTBnTQladoYPqJQ/IW08vSLq", "pozerinhooo");