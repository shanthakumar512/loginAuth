delete from user_roles;
delete from users;
delete from roles;

INSERT INTO roles (`id`,`name`) VALUES (1,'ROLE_USER');
INSERT INTO roles (`id`,`name`) VALUES (3,'ROLE_ADMIN');


INSERT INTO users (`id`,`email`,`password`,`username`) VALUES (3,'user1@gmail.com','$2a$10$M0MZfhaBKR.G7HNeAhOJvOeyjIIsCQD3hIIywwyyIhSbMy3nzGVri','user1');
INSERT INTO users (`id`,`email`,`password`,`username`) VALUES (4,'admin1@gmail.com','$2a$10$6chVBalRjULmuLC9wE3M.O9QnVpCYIwWQRdDpDYaIpoGXbc.kDCOW','admin1');
INSERT INTO users (`id`,`email`,`password`,`username`) VALUES (1,'user2@gmail.com','$2a$10$M0MZfhaBKR.G7HNeAhOJvOeyjIIsCQD3hIIywwyyIhSbMy3nzGVri','user2');
INSERT INTO users (`id`,`email`,`password`,`username`) VALUES (2,'admin2@gmail.com','$2a$10$6chVBalRjULmuLC9wE3M.O9QnVpCYIwWQRdDpDYaIpoGXbc.kDCOW','admin2');

INSERT INTO user_roles (`user_id`,`role_id`) VALUES (3,1);
INSERT INTO user_roles (`user_id`,`role_id`) VALUES (4,3);
INSERT INTO user_roles (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO user_roles (`user_id`,`role_id`) VALUES (2,3);