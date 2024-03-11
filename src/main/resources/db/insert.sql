insert into address (country, postcode, street, street_number)
values
('Polska', '12331', 'kwiatowa', 123),
('Niemcy', '42331', 'miodowa', 17);

insert into owner (email, name, surname)
values
('dupa@gmail.com', 'Zbysek', 'Mach'),
('andriu@gmail.com', 'Andriu', 'Plot');

insert into place (phone, name, owner_id, address_id)
values
('948393', 'Byczek', 1, 1),
('148393', 'Ubojnia', 2, 2);