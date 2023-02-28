package banking.entities.dao;

import banking.entities.Card;

public interface CardDao extends Dao<Card>{
    String TABLE_NAME = "card";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
            + "id INTEGER PRIMARY KEY, "
            + "number TEXT UNIQUE, "
            + "pin TEXT, "
            + "balance INTEGER DEFAULT 0); ";

    String INSERT = "INSERT INTO card (number, pin) VALUES (?, ?);";

    String FIND_BY_NUMBER = "SELECT * FROM card WHERE number = ?;";

    String FIND_BY_NUMBER_AND_PIN = "SELECT * FROM card WHERE number = ? AND pin = ?;";

    String UPDATE_BALANCE = "UPDATE " + TABLE_NAME + " SET balance = ? WHERE number = ?;";

    String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE number = ?;";

    Card findByNumber(String number);
    Card findByNumberAndPIN(String number, String PIN);

    void createCardTable();

    void updateBalance(Card card);

    boolean transferMoney(Card card, Card cardToTransfer);
}
