CREATE TABLE Buyer (
                       cpf VARCHAR(11) PRIMARY KEY,
                       fk_user BINARY,
                       FOREIGN KEY (fk_user) REFERENCES User(id)
);
CREATE TABLE PurchaseOrder (
                               id INTEGER PRIMARY KEY AUTO_INCREMENT,
                               date DATE,
                               orderStatus VARCHAR(15),
                               fk_buyer VARCHAR(11),
                               FOREIGN KEY (fk_buyer) REFERENCES Buyer(cpf)
);
CREATE TABLE ProductOrder (
                              fk_purchaseOrder INTEGER,
                              fk_product INTEGER,
                              FOREIGN KEY (fk_purchaseOrder) REFERENCES PurchaseOrder(id),
                              FOREIGN KEY (fk_product) REFERENCES Product(id)
);
