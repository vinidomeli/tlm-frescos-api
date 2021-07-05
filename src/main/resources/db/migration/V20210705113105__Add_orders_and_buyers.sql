CREATE TABLE Buyer (
                       cpf VARCHAR(11) PRIMARY KEY,
                       FOREIGN KEY (fk_user) REFERENCES User(id)
);

CREATE TABLE Order (
                       orderId INTEGER PRIMARY KEY,
                       date DATE,
                       orderStatus VARCHAR(15),
                       FOREIGN KEY (fk_buyer) REFERENCES Buyer(cpf)
);

CREATE TABLE product_order (
                       FOREIGN KEY (fk_order) REFERENCES Order(orderId),
                       FOREIGN KEY (fk_product) REFERENCES Product(productId)
);
