CREATE TABLE Wallet
(
    id      BINARY(16) PRIMARY KEY,
    fk_user BINARY(16) NOT NULL,
    balance BIGINT NOT NULL,

    FOREIGN KEY (fk_user) REFERENCES User (id)
);