CREATE TABLE Wallet
(
    id        BINARY(16) PRIMARY KEY,
    fk_userId BINARY(16) NOT NULL,
    balance   BIGINT NOT NULL,

    FOREIGN KEY (fk_userId) REFERENCES User (id)
);