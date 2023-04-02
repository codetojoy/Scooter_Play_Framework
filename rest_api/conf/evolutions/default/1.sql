-- Attendee table

-- !Ups

CREATE TABLE attendee(
id SERIAL,
name VARCHAR(256) NOT NULL,
constraint pk_attendee primary key (id)
);

INSERT into attendee (name) VALUES ('Johann Sebastian Bach');
INSERT into attendee (name) VALUES ('Ludwig van Beethoven');
INSERT into attendee (name) VALUES ('Wolfgang Amadeus Mozart');
INSERT into attendee (name) VALUES ('Franz Schubert');
INSERT into attendee (name) VALUES ('Richard Wagner');
INSERT into attendee (name) VALUES ('Antonio Vivaldi');
INSERT into attendee (name) VALUES ('Johannes Brahms');
INSERT into attendee (name) VALUES ('Giuseppe Verdi');
INSERT into attendee (name) VALUES ('Robert Schumann');
INSERT into attendee (name) VALUES ('Giacomo Puccini');
INSERT into attendee (name) VALUES ('Antonín Dvorák');
INSERT into attendee (name) VALUES ('George Handel');
INSERT into attendee (name) VALUES ('Franz Liszt');
INSERT into attendee (name) VALUES ('Joseph Haydn');
INSERT into attendee (name) VALUES ('Frédéric Chopin');
INSERT into attendee (name) VALUES ('Igor Stravinsky');
INSERT into attendee (name) VALUES ('Gustav Mahler');
INSERT into attendee (name) VALUES ('Richard Strauss');
INSERT into attendee (name) VALUES ('Dmitri Shostakovich');
INSERT into attendee (name) VALUES ('Hector Berlioz');

-- !Downs

DROP TABLE attendee;

