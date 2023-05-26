DROP TABLE IF EXISTS productos;
CREATE TABLE productos (
   clave VARCHAR(25) NOT NULL,
   nombre VARCHAR(25),
   marca VARCHAR(25),
   precio double,
   cantidad integer,
   fecha date,
   PRIMARY KEY (clave)
);