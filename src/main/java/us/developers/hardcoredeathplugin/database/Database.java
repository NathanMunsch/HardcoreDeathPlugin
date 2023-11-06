package us.developers.hardcoredeathplugin.database;

import org.bukkit.entity.Player;
import java.sql.*;
import java.util.Random;

public class Database {
    private final Connection connection;

    public Database(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        createTables();
        initializeQuests();
    }

    private void createTables() {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("""
                CREATE TABLE IF NOT EXISTS players (
                uuid TEXT PRIMARY KEY,
                repayMode INTEGER NOT NULL DEFAULT 0,
                deaths INTEGRER NOT NULL DEFAULT 0)
                """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS quests (
                id INTEGER PRIMARY KEY,
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeQuests() {
        String[][] quests = new String[][] {
                {"Treasure hunt", "1"},
                {"Kill a wither", "1"},
                {"Make an exchange with an NPC", "1"},
                {"Craft an anvil", "1"},
                {"Catch 5 fish", "5"},
                {"Kill 10 zombies", "10"},
                {"Melt 10 gold bars", "10"},
                {"Craft a diamond pickaxe", "1"},
                {"Activate a nether portal", "1"},
                {"Breed two cows", "2"},
                {"Ride a minecart", "1"},
                {"Craft an enchanting table", "1"},
                {"Mine an ancient debris", "1"}
        };

        for (String[] quest : quests) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR IGNORE INTO " +
                    "quests(id, name, objective) VALUES (?, ?, ?)")) {
                preparedStatement.setInt(1, getLastIdFromQuestsTable() + 1);
                preparedStatement.setString(2, quest[0]);
                preparedStatement.setInt(3, Integer.parseInt(quest[1]));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getLastIdFromQuestsTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) AS 'lastId' FROM quests")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int lastId = resultSet.getInt("lastId");
                resultSet.close();
                return lastId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR IGNORE INTO players(uuid) VALUES (?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
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

    public void activateRepayMode(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET repayMode = 1 WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deactivateRepayMode(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET repayMode = 0 WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDeaths(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT deaths FROM players WHERE uuid = ?")) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET deaths = ? WHERE uuid = ?")) {
            preparedStatement.setInt(1, getDeaths(player) + 1);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void linkRandomQuests(Player player) {
        int count;
        int[] randomNumbers;
        do {
            Random random = new Random();
            randomNumbers = new int[5];
            for (int i = 0; i < 5; i++) {
                int min = 1;
                int max = getLastIdFromQuestsTable();
                randomNumbers[i] = random.nextInt(max - min + 1) + min;
            }
            count = 0;
            for (int number : randomNumbers) {
                for (int randomNumber : randomNumbers) {
                    if (number == randomNumber) {
                        count++;
                    }
                }
            }
        } while (count > 5);

        for (int randomNumber : randomNumbers) {
            linkQuest(randomNumber, player);
        }
    }

    private void linkQuest(int questId, Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "players_quests(player_uuid, quests_id) VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setInt(2, questId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}