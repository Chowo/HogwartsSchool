CREATE TABLE human (
    id INTEGER PRIMARY KEY,
    name VARCHAR,
    age INTEGER,
    driver_license BOOLEAN,
    car_id INTEGER
);

CREATE TABLE car (
    car_id INTEGER PRIMARY KEY,
    brand VARCHAR,
    model VARCHAR,
    price MONEY
);
