CREATE TABLE User (
                      id BINARY(16) PRIMARY KEY,
                      role VARCHAR(50),
                      name VARCHAR(100),
                      login VARCHAR(50),
                      password VARCHAR(50)
);

CREATE TABLE Seller (
                        CNPJ VARCHAR(18) PRIMARY KEY,
                        reputation VARCHAR(255),
                        fk_user BINARY(16),
                        FOREIGN KEY (fk_user) REFERENCES User(id)
);

CREATE TABLE Product (
                         id INTEGER PRIMARY KEY AUTO_INCREMENT,
                         type VARCHAR(50),
                         fk_seller VARCHAR(18),
                         FOREIGN KEY (fk_seller) REFERENCES Seller(CNPJ)
);

CREATE TABLE Supervisor (
                            registerNumber INTEGER PRIMARY KEY AUTO_INCREMENT,
                            fk_user BINARY(16),
                            FOREIGN KEY (fk_user) REFERENCES User(id)
);

CREATE TABLE Warehouse (
                           warehouseCode BINARY(16) PRIMARY KEY,
                           location VARCHAR(50),
                           fk_supervisor VARCHAR(18),
                           FOREIGN KEY (fk_supervisor) REFERENCES Supervisor(registerNumber)
);

CREATE TABLE Section (
                         sectionCode BINARY(16) PRIMARY KEY,
                         productType VARCHAR(50),
                         limitSize INTEGER,
                         temperature DOUBLE,
                         currentSize INTEGER,
                         fk_warehouse BINARY(16),
                         FOREIGN KEY (fk_warehouse) REFERENCES Warehouse(warehouseCode)
);

CREATE TABLE InboundOrder (
                              orderNumber INTEGER PRIMARY KEY AUTO_INCREMENT,
                              orderDate DATE,
                              fk_section BINARY(16),
                              FOREIGN KEY (fk_section) REFERENCES Section(sectionCode)
);

CREATE TABLE Batch (
                       batchNumber INTEGER PRIMARY KEY AUTO_INCREMENT,
                       productType VARCHAR(50),
                       initialQuantity INTEGER,
                       currentQuantity INTEGER,
                       minimumTemperature DOUBLE,
                       currentTemperature DOUBLE,
                       manufacturingDate DATE,
                       manufacturingTime TIMESTAMP,
                       dueDate DATE,
                       fk_product INTEGER,
                       fk_inboundOrder INTEGER,
                       FOREIGN KEY (fk_product) REFERENCES Product(id),
                       FOREIGN KEY (fk_inboundOrder) REFERENCES InboundOrder(orderNumber)
);