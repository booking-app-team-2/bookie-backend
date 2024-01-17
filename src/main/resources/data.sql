INSERT INTO user_bookie (id, username, password, name, surname, address_of_residence, telephone,
                         last_password_reset_date, role, is_blocked, is_deleted)
VALUES (nextval('sequence_user'), 'bookie@gmail.com', '$2a$10$vudjSlPv/HU4rH9n/uwXXezmoG9zCZb1KKhTvErQTWRp9Ln5gwdQe',
        'Bookie', 'Bookie', 'Bookshelf', 'Placeholder', timestamp '2023-12-09 22:14:30', 'Admin', false, false);

INSERT INTO user_bookie (id, username, password, name, surname, address_of_residence, telephone,
                         last_password_reset_date, role, is_blocked, is_deleted)
VALUES (nextval('sequence_user'), 'eikoob@gmail.com', '$2a$10$QRX9ZJEotdh.5sph3O6PRe1oeloCgF12fjJXo9Pa5oYZ3P..Zw3US',
        'Eikoob', 'Eikoob', 'Fleshbook', 'Placeholder', timestamp '2023-12-09 22:16:19', 'Guest', false, false);

INSERT INTO user_bookie (id, username, password, name, surname, address_of_residence, telephone,
                         last_password_reset_date, role, is_blocked, is_deleted)
VALUES (nextval('sequence_user'), 'darko@gmail.com', '$2a$10$ucvB0UzXjwIvnEhz9wTDBu3En9Ig7u2Ni7TRuqCJckF8tnhXdo58S',
        'Darko', 'Svilar', 'No', 'No', timestamp '2023-12-09 22:16:30', 'Guest', false, false);

INSERT INTO user_bookie (id, username, password, name, surname, address_of_residence, telephone,
                         last_password_reset_date, role, is_blocked, is_deleted)
VALUES (nextval('sequence_user'), 'owner@gmail.com', '$2a$10$IF6wgXHgzupWmoO6BNfqL.AooesY2oLhC4Lo8MnFLfxuEx12hg6Rq',
        'Owner', 'Owner', 'Placeholder', 'Placeholder', timestamp '2023-12-09 22:16:48', 'Owner', false, false);

INSERT INTO user_bookie (id, username, password, name, surname, address_of_residence, telephone,
                         last_password_reset_date, role, is_blocked, is_deleted)
VALUES (nextval('sequence_user'), 'ownernoaccs@gmail.com',
        '$2a$10$ijDyoU05pvIjQHC4aLkzn.wjgJuBVV6zOa0xMeNJ6FB250zXUg0Lq', 'Owner', 'Owner', 'Placeholder', 'Placeholder',
        timestamp '2023-12-09 22:16:48', 'Owner', false, false);

INSERT INTO user_bookie (id, username, password, name, surname, address_of_residence, telephone,
                         last_password_reset_date, role, is_blocked, is_deleted)
VALUES (nextval('sequence_user'), 'owneraccsnores@gmail.com',
        '$2a$10$pe4FvwSCNyOGUAHdokhwrOr9lCRrba/TYzVHGGhyEIeMGyGNGtEZ.', 'Owner', 'Owner', 'Placeholder', 'Placeholder',
        timestamp '2023-12-09 22:16:48', 'Owner', false, false);

INSERT INTO user_bookie (id, username, password, name, surname, address_of_residence, telephone,
                         last_password_reset_date, role, is_blocked, is_deleted)
VALUES (nextval('sequence_user'), 'guestnoreservations@gmail.com',
        '$2a$10$xP789d3bmJNDtvBx/8mSXuEdV/ckuGPBDspgrEdqg3kKnV8t3XIBS', 'Guest', 'Guest', 'Placeholder', 'Placeholder',
        '2023-01-04', 'Guest', false, false);

INSERT INTO guest (id, receives_reservation_request_notifications)
VALUES (2, true);

INSERT INTO guest (id, receives_reservation_request_notifications)
VALUES (3, true);

INSERT INTO owner (id, receives_reservation_request_notifications, receives_reservation_cancellation_notifications,
                   receives_review_notifications, receives_accommodation_review_notifications)
VALUES (4, true, true, true, true);

INSERT INTO owner (id, receives_reservation_request_notifications, receives_reservation_cancellation_notifications,
                   receives_review_notifications, receives_accommodation_review_notifications)
VALUES (5, true, true, true, true);

INSERT INTO owner (id, receives_reservation_request_notifications, receives_reservation_cancellation_notifications,
                   receives_review_notifications, receives_accommodation_review_notifications)
VALUES (6, true, true, true, true);

INSERT INTO guest (id, receives_reservation_request_notifications)
VALUES (7, true);

INSERT INTO account_verificator (id, timestamp_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), timestamp '2023-12-09 22:14:30', true, 1, false);

INSERT INTO account_verificator (id, timestamp_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), timestamp '2023-12-09 22:16:19', false, 2, false);

INSERT INTO account_verificator (id, timestamp_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), timestamp '2023-12-09 22:16:30', true, 3, false);

INSERT INTO account_verificator (id, timestamp_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), timestamp '2023-12-09 22:16:48', true, 4, false);

INSERT INTO account_verificator (id, timestamp_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), timestamp '2023-12-09 22:16:48', true, 5, false);

INSERT INTO account_verificator (id, timestamp_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), timestamp '2023-12-09 22:16:48', true, 6, false);

INSERT INTO account_verificator (id, timestamp_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), timestamp '2024-01-04 16:29:00', true, 7, false);

INSERT INTO accommodation (id, name, description, latitude, longitude, minimum_guests, maximum_guests,
                           reservation_cancellation_deadline, type, is_priced_per_guest, is_approved,
                           is_reservation_auto_accepted, is_deleted)
VALUES (nextval('sequence_accommodation'), 'Accommodation1', 'Accommodation1', '45.24611466641891',
        '19.851694566067337', 2, 4, 7, 'Room', true, true, true, false);

INSERT INTO accommodation (id, name, description, latitude, longitude, minimum_guests, maximum_guests,
                           reservation_cancellation_deadline, type, is_priced_per_guest, is_approved,
                           is_reservation_auto_accepted, is_deleted)
VALUES (nextval('sequence_accommodation'), 'Accommodation2', 'Accommodation2', '45.24611466641891',
        '19.851694566067337', 1, 2, 4, 'Studio', false, true, false, false);

INSERT INTO accommodation (id, name, description, latitude, longitude, minimum_guests, maximum_guests,
                           reservation_cancellation_deadline, type, is_priced_per_guest, is_approved,
                           is_reservation_auto_accepted, is_deleted)
VALUES (nextval('sequence_accommodation'), 'Accommodation3', 'Accommodation3', '45.24493015677446',
        '19.84771549825743', 1, 2, 5, 'Room', false, false, false, false);

INSERT INTO accommodation (id, name, description, latitude, longitude, minimum_guests, maximum_guests,
                           reservation_cancellation_deadline, type, is_priced_per_guest, is_approved,
                           is_reservation_auto_accepted, is_deleted)
VALUES (nextval('sequence_accommodation'), 'Accommodation4', 'Accommodation4', '45.24493015677446',
        '19.84771549825743', 10, 15, 14, 'Studio', false, true, false, false);

INSERT INTO accommodation (id, name, description, latitude, longitude, minimum_guests, maximum_guests,
                           reservation_cancellation_deadline, type, is_priced_per_guest, is_approved,
                           is_reservation_auto_accepted, is_deleted)
VALUES (nextval('sequence_accommodation'), 'Accommodation5', 'Accommodation5', '45.24611466641891',
        '19.84771549825743', 1, 1, 1, 'Room', true, true, false, false);

INSERT INTO owner_accommodations (accommodation_id, owner_id)
VALUES (1, 4);

INSERT INTO owner_accommodations (accommodation_id, owner_id)
VALUES (2, 4);

INSERT INTO owner_accommodations (accommodation_id, owner_id)
VALUES (3, 4);

INSERT INTO owner_accommodations (accommodation_id, owner_id)
VALUES (4, 6);

INSERT INTO owner_accommodations (accommodation_id, owner_id)
VALUES (5, 6);

INSERT INTO accommodation_amenities (accommodation_id, amenity)
VALUES (1, 'Parking');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
VALUES (2, 'WiFi');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
VALUES (2, 'Kitchen');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
VALUES (2, 'AC');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
VALUES (2, 'Parking');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
VALUES (3, 'WiFi');

INSERT INTO accommodation_amenities (accommodation_id, amenity)
VALUES (3, 'Kitchen');

INSERT INTO availability_period (id, price, start_date, end_date, is_deleted)
VALUES (nextval('sequence_availability_period'), 15, current_date - 2, current_date + 4, false);

INSERT INTO availability_period (id, price, start_date, end_date, is_deleted)
VALUES (nextval('sequence_availability_period'), 20, current_date + 10, current_date + 12, false);

INSERT INTO availability_period (id, price, start_date, end_date, is_deleted)
VALUES (nextval('sequence_availability_period'), 30, current_date + 1, current_date + 8, false);

INSERT INTO availability_period (id, price, start_date, end_date, is_deleted)
VALUES (nextval('sequence_availability_period'), 10, current_date - 5, current_date - 1, false);

INSERT INTO accommodation_availability_periods (accommodation_id, availability_period_id)
VALUES (1, 1);

INSERT INTO accommodation_availability_periods (accommodation_id, availability_period_id)
VALUES (1, 2);

INSERT INTO accommodation_availability_periods (accommodation_id, availability_period_id)
VALUES (2, 3);

INSERT INTO accommodation_availability_periods (accommodation_id, availability_period_id)
VALUES (3, 4);

INSERT INTO reservation (id, number_of_guests, status, accommodation_id, reservee_id, start_date, end_date, price,
                         is_deleted)
VALUES (nextval('sequence_reservation'), 4, 'Accepted', 1, 3, current_date + 5, current_date + 9, 300, false);

INSERT INTO reservation (id, number_of_guests, status, accommodation_id, reservee_id, start_date, end_date, price,
                         is_deleted)
VALUES (nextval('sequence_reservation'), 2, 'Accepted', 1, 3, current_date + 13, current_date + 15, 120, false);

INSERT INTO reservation (id, number_of_guests, status, accommodation_id, reservee_id, start_date, end_date, price,
                         is_deleted)
VALUES (nextval('sequence_reservation'), 1, 'Waiting', 2, 3, current_date + 5, current_date + 5, 30, false);

INSERT INTO reservation (id, number_of_guests, status, accommodation_id, reservee_id, start_date, end_date, price,
                         is_deleted)
VALUES (nextval('sequence_reservation'), 4, 'Accepted', 1, 3, current_date - 10, current_date -5, 300, false);

INSERT INTO image(id,name,path,type,is_deleted)
VALUES (nextval('sequence_image'),'slika11','src/main/resources/images','jpg',false);

INSERT INTO accommodation_images(accommodation_id, image_id)
VALUES (1,1);

INSERT INTO image(id,name,path,type,is_deleted)
VALUES (nextval('sequence_image'),'slika12','src/main/resources/images','jpg',false);

INSERT INTO accommodation_images(accommodation_id, image_id)
VALUES (1,2);

INSERT INTO review(id,grade,comment,timestamp_of_creation,is_approved,is_deleted,is_reported,reviewer_id)
VALUES (nextval('sequence_review'),4,'not bad dude','2024-01-11 12:30:45.123456',true,false,false,2);

INSERT INTO accommodation_review(accommodation_id, id)
VALUES (1,1);

INSERT INTO review(id,grade,comment,timestamp_of_creation,is_approved,is_deleted,is_reported,reviewer_id)
VALUES (nextval('sequence_review'),3,'meh dude','2024-01-12 12:30:45.123456',false,false,false,2);

INSERT INTO accommodation_review(accommodation_id, id)
VALUES (1,2);



-- TODO: Insert images and connect them to accommodations
