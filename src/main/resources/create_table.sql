create table fly_booking
(
	booking_id int
		constraint fly_booking_pk
			primary key,
	client_name text,
	flight_number int,
	from_place text,
	to_place text,
	flight_date date
);



create table hotel_booking
(
	hotel_id int
		constraint hotel_booking_pk
			primary key,
	client_name text,
	hotel_name text,
	arrival_date date,
	departure_date date
);

