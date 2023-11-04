package us.developers.hardcoredeathplugin.database;

import org.bukkit.entity.Player;

import java.sql.*;

public class Database {
    private final Connection connection;

    public Database(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
            CREATE TABLE IF NOT EXISTS players (
            uuid TEXT PRIMARY KEY,
            username TEXT NOT NULL,
            repayMode INTEGER NOT NULL,
            deathsNumber INTEGRER NOT NULL DEFAULT 0)
            """);
        statement.execute("""
            CREATE TABLE IF NOT EXISTS quests (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT UNIQUE NOT NULL,
            objective INTEGER NOT NULL)
            """);
        statement.execute("""
            CREATE TABLE IF NOT EXISTS players_quests (
            player_uuid TEXT,
            quests_id INTEGER,
            PRIMARY KEY (player_uuid, quests_id),
            FOREIGN KEY (player_uuid) REFERENCES players(uuid),
            FOREIGN KEY (quests_id) REFERENCES quests(id))
            """);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPlayer(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players(uuid, username, repayMode) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.getDisplayName());
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInRepayMode(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT repayMode FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int repayMode = resultSet.getInt("repayMode");
                resultSet.close();
                return repayMode == 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void deactivateRepayMode(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET repayMode = 0 WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumberOfDeath(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT deathsNumber FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int deathNumber = resultSet.getInt("deathNumber");
                resultSet.close();
                return deathNumber;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void addDeath(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET deathsNumber = ? WHERE uuid = ?")) {
            preparedStatement.setInt(1, getNumberOfDeath(player) + 1);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}