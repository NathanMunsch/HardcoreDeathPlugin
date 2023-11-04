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
            repayMode INTEGER NOT NULL)
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

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void addPlayer(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players(uuid, username, repayMode) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.getDisplayName());
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();
        }
    }

    public boolean isInRepayMode(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT repayMode FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt("repayMode") == 1;
            }
        }
        return false;
    }

    public void deactivateRepayMode(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET repayMode = 0 WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }
}