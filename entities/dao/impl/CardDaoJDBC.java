package banking.entities.dao.impl;

import banking.db.Database;
import banking.entities.Card;
import banking.entities.dao.CardDao;

import java.sql.*;

public class CardDaoJDBC implements CardDao {
    private final Connection conn;

    public CardDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public void createCardTable() {
        // create table
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(CREATE_TABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public void updateBalance(Card card) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(UPDATE_BALANCE);
            st.setInt(1, card.getBalance());
            st.setString(2, card.toString());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error trying to add balance");
        } finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public boolean transferMoney(Card card, Card cardToTransfer) {
        try {
            conn.setAutoCommit(false);

            updateBalance(card);
            updateBalance(cardToTransfer);

            conn.commit();
            conn.setAutoCommit(true);

            return true;
        } catch (SQLException ex) {
            System.out.println("Error while transferring");
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex2) {
                System.out.println("Error while rolling back");
            }
        }
        return false;
    }

    @Override
    public Card findByNumber(String number) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(FIND_BY_NUMBER);
            st.setString(1, number);

            rs = st.executeQuery();

            if (rs.next()) {
                int balance = rs.getInt("balance");
                String PIN = rs.getString("pin");
                return new Card(number, PIN, balance);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
        return null;
    }

    @Override
    public Card findByNumberAndPIN(String number, String PIN) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(FIND_BY_NUMBER_AND_PIN);
            st.setString(1, number);
            st.setString(2, PIN);

            rs = st.executeQuery();

            if (rs.next()) {
                int balance = rs.getInt("balance");
                return new Card(number, PIN, balance);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
        return null;
    }

    @Override
    public void insert(Card card) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(INSERT);
            st.setString(1, card.toString());
            st.setString(2, card.getPIN());

            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public void delete(Card card) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(DELETE);
            st.setString(1, card.toString());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Delete failed");
        } finally {
            Database.closeStatement(st);
        }
    }
}
