package banking.entities.dao;

import banking.db.Database;
import banking.entities.dao.impl.CardDaoJDBC;

public class DaoFactory {
    public static CardDao createCardDao(String databasePath) {
        return new CardDaoJDBC(Database.getConnection(databasePath));
    }
}
