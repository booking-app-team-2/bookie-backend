INSERT INTO user_bookie (id, email, password, name, surname, address_of_residence, telephone, role, is_blocked,
                         is_deleted)
VALUES (nextval('sequence_user'), 'bookie@gmail.com', 'bookie123', 'Bookie', 'Bookie', 'Bookshelf', 'Placeholder',
        'Admin', false, false);

INSERT INTO user_bookie (id, email, password, name, surname, address_of_residence, telephone, role, is_blocked,
                         is_deleted)
VALUES (nextval('sequence_user'), 'eikoob@gmail.com', 'eikoob123', 'Eikoob', 'Eikoob', 'Fleshbook', 'Placeholder',
        'Guest', false, false);

INSERT INTO user_bookie (id, email, password, name, surname, address_of_residence, telephone, role, is_blocked,
                         is_deleted)
VALUES (nextval('sequence_user'), 'darko@gmail.com', 'darko123', 'Darko', 'Svilar', 'No', 'No',
        'Guest', false, false);

INSERT INTO user_bookie (id, email, password, name, surname, address_of_residence, telephone, role, is_blocked,
                         is_deleted)
VALUES (nextval('sequence_user'), 'owner@gmail.com', 'owner123', 'Owner', 'Owner', 'Placeholder', 'Placeholder',
        'Owner', false, false);

INSERT INTO user_bookie (id, email, password, name, surname, address_of_residence, telephone, role, is_blocked,
                         is_deleted)
VALUES (nextval('sequence_user'), 'ownernoaccs@gmail.com', 'owner123', 'Owner', 'Owner', 'Placeholder', 'Placeholder',
        'Owner', false, false);

INSERT INTO user_bookie (id, email, password, name, surname, address_of_residence, telephone, role, is_blocked,
                         is_deleted)
VALUES (nextval('sequence_user'), 'owneraccsnores@gmail.com', 'owner123', 'Owner', 'Owner', 'Placeholder',
        'Placeholder', 'Owner', false, false);

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

INSERT INTO account_verificator (id, date_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), 1702156470, true, 1, false);

INSERT INTO account_verificator (id, date_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), 1702156579, false, 2, false);

INSERT INTO account_verificator (id, date_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), 1702156590, true, 3, false);

INSERT INTO account_verificator (id, date_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), 1702156608, true, 4, false);

INSERT INTO account_verificator (id, date_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), 1702156608, true, 5, false);

INSERT INTO account_verificator (id, date_of_registration, is_verified, user_id, is_deleted)
VALUES (nextval('sequence_account_verificator'), 1702156608, true, 6, false);

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
VALUES (nextval('sequence_availability_period'), 15, 1702252800, 1702684800, false);

INSERT INTO availability_period (id, price, start_date, end_date, is_deleted)
VALUES (nextval('sequence_availability_period'), 20, 1702684800, 1702857600, false);

INSERT INTO availability_period (id, price, start_date, end_date, is_deleted)
VALUES (nextval('sequence_availability_period'), 30, 1702944000, 1703462400, false);

INSERT INTO availability_period (id, price, start_date, end_date, is_deleted)
VALUES (nextval('sequence_availability_period'), 10, 1702944000, 1703462400, false);

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
VALUES (nextval('sequence_reservation'), 4, 'Accepted', 1, 3, 1702857600, 1703030400, 160, false);

INSERT INTO reservation (id, number_of_guests, status, accommodation_id, reservee_id, start_date, end_date, price,
                         is_deleted)
VALUES (nextval('sequence_reservation'), 2, 'Accepted', 1, 3, 1703030400, 1703376000, 160, false);

INSERT INTO reservation (id, number_of_guests, status, accommodation_id, reservee_id, start_date, end_date, price,
                         is_deleted)
VALUES (nextval('sequence_reservation'), 1, 'Waiting', 2, 3, 1703289600, 1703462400, 60, false);

-- TODO: Insert images and connect them to accommodations
