CREATE TABLE Buyer (
    cpf VARCHAR(11) PRIMARY KEY,
    fk_user BINARY(16),
    FOREIGN KEY (fk_user) REFERENCES User(id)
);

CREATE TABLE PurchaseOrder (
    id BINARY(16) PRIMARY KEY,
    date DATE,
    price DOUBLE,
    fk_user BINARY(16),
    FOREIGN KEY (fk_user) REFERENCES User(id)
);

CREATE TABLE PurchaseOrderContent (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    productName VARCHAR(255),
    productPrice DOUBLE,
    productQuantity INTEGER,
    fk_purchaseOrder BINARY(16),
    FOREIGN KEY (fk_purchaseOrder) REFERENCES PurchaseOrder(id)
);

CREATE TABLE Cart (
    id BINARY(16) PRIMARY KEY,
    price DOUBLE,
    fk_user BINARY(16),
    FOREIGN KEY (fk_user) REFERENCES User(id)
);

CREATE TABLE CartContent (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    quantity INTEGER,
    fk_cart BINARY(16),
    fk_product INTEGER,
    FOREIGN KEY (fk_cart) REFERENCES Cart(id),
    FOREIGN KEY (fk_product) REFERENCES Product(id)
);
