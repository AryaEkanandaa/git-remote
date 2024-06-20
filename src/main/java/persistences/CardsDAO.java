package persistences;

import ardit.com.Cards;

import java.sql.*;
import java.util.ArrayList;

public class CardsDAO {

    // Database connection URL
    private static final String DB_URL = "jdbc:sqlite:subscription.db";

    // SELECT ALL CARDS BASED ON CUSTOMER'S ID
    public ArrayList<Cards> selectCardsByCustomer(int idCustomer) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Cards> listCards = new ArrayList<>();

        try {
            // Establish connection to SQLite database
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database");

            // Prepare and execute query
            statement = connection.prepareStatement("SELECT * FROM cards WHERE customer = ?");
            statement.setInt(1, idCustomer);
            result = statement.executeQuery();

            while (result.next()) {
                Cards card = new Cards();
                card.setId(result.getInt("id"));
                card.setCustomer(result.getInt("customer"));
                card.setCard_type(result.getString("card_type"));
                card.setMasked_number(result.getString("masked_number"));
                card.setExpiry_month(result.getInt("expiry_month"));
                card.setExpiry_year(result.getInt("expiry_year"));
                card.setStatus(result.getString("status"));
                card.setIs_primary(result.getInt("is_primary"));
                listCards.add(card);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving cards: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return listCards;
    }

    // INSERT NEW CARD
    public String addNewCard(Cards card) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establish connection to SQLite database
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database");

            // Prepare and execute insert statement
            statement = connection.prepareStatement("INSERT INTO cards (customer, card_type, masked_number, expiry_month, expiry_year, status, is_primary) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, card.getCustomer());
            statement.setString(2, card.getCard_type().toString()); // Use toString() for enum
            statement.setString(3, card.getMasked_number());
            statement.setInt(4, card.getExpiry_month());
            statement.setInt(5, card.getExpiry_year());
            statement.setString(6, card.getStatus().toString()); // Use toString() for enum
            statement.setInt(7, card.getIs_primary());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected + " row(s) inserted.";
        } catch (SQLException e) {
            System.err.println("Error inserting card: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    // UPDATE CARD BASED ON ID
    public String updateCard(Cards card, int idCard) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establish connection to SQLite database
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database");

            // Prepare and execute update statement
            statement = connection.prepareStatement("UPDATE cards SET card_type = ?, masked_number = ?, expiry_month = ?, " +
                    "expiry_year = ?, status = ?, is_primary = ? WHERE id = ?");
            statement.setString(1, card.getCard_type().toString()); // Use toString() for enum
            statement.setString(2, card.getMasked_number());
            statement.setInt(3, card.getExpiry_month());
            statement.setInt(4, card.getExpiry_year());
            statement.setString(5, card.getStatus().toString()); // Use toString() for enum
            statement.setInt(6, card.getIs_primary());
            statement.setInt(7, idCard);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected + " row(s) updated.";
        } catch (SQLException e) {
            System.err.println("Error updating card: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    // DELETE CARD
    public String deleteCard(int idCard) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establish connection to SQLite database
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database");

            // Prepare and execute delete statement
            statement = connection.prepareStatement("DELETE FROM cards WHERE id = ?");
            statement.setInt(1, idCard);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected + " row(s) deleted.";
        } catch (SQLException e) {
            System.err.println("Error deleting card: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
