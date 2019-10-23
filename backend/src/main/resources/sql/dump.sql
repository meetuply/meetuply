INSERT INTO role (name, uid) VALUES ('admin', 5091998);
INSERT INTO role (name, uid) VALUES ('user', 3061979);

INSERT INTO state (name, uid) VALUES ('Booked', 11);
INSERT INTO state (name, uid) VALUES ('Canceled', 21);
INSERT INTO state (name, uid) VALUES ('In progress', 31);
INSERT INTO state (name, uid) VALUES ('Passed', 51);
INSERT INTO state (name, uid) VALUES ('Scheduled', 1);
INSERT INTO state (name, uid) VALUES ('Terminated', 41);

INSERT INTO user (uid, email, password, firstname, surname, registration_confirmed, is_deactivated, allow_notifications, role_id, photo) VALUES (56, '111@gmail.com', '$2a$10$F3vsfcPxDenhA5.rb1CGf.882Nln1SV62wdniRzem8iUr0e/Rs2BG', '111', '111', 1, 0, 1, 3061979, null);
INSERT INTO user (uid, email, password, firstname, surname, registration_confirmed, is_deactivated, allow_notifications, role_id, photo) VALUES (57, '222@gmail.com', '$2a$10$F60GkxL/u95n0tSlwFfmeeuYJ4m2sQvny2QVRpyt/e5faJRW7nI0O', '222', '222', 1, 0, 1, 3061979, null);
INSERT INTO user (uid, email, password, firstname, surname, registration_confirmed, is_deactivated, allow_notifications, role_id, photo) VALUES (58, '333@gmail.com', '$2a$10$3gOV4.Za8Q4X8qdXlS7O6.wb2qP9GPliw.rjup5d72TPxRc2iFzH2', '333', '333', 1, 0, 1, 3061979, null);
INSERT INTO user (uid, email, password, firstname, surname, registration_confirmed, is_deactivated, allow_notifications, role_id, photo) VALUES (59, '444@gmail.com', '$2a$10$yaJ80oSP5PquPh2uLODLkuH8tX07SjqMog6BGp2phlQvEYRbIZEZC', '444', '444', 1, 0, 1, 3061979, null);
INSERT INTO user (uid, email, password, firstname, surname, registration_confirmed, is_deactivated, allow_notifications, role_id, photo) VALUES (60, '555@gmail.com', '$2a$10$F4ljdh8CEyVgT1ZE6ndo3e6qDg7chOipUaNaV7MBPnZRBa5mndVCC', '555', '555', 1, 0, 1, 3061979, null);

INSERT INTO meetup (title, uid, start_date_time, finish_date_time, place, registered_attendees, min_attendees, max_attendees, state_id, speaker_id, description) VALUES ('Test #1', 2, '2019-10-21 11:20:09', '2019-10-21 12:20:12', 'Office #1', 0, 1, 2, 1, 56, 'Very exciting');
