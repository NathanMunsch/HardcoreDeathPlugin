package us.developers.hardcoredeathplugin.database;

import org.bukkit.entity.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Database {
    private final Connection connection;

    public Database(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        createTables();
        initializeQuests();
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
                progression INTEGER DEFAULT 0,
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
                {"Kill a wither", "1"}, // 1
                {"Craft an anvil", "1"}, // 2
                {"Catch 5 fish", "5"}, // 3
                {"Kill 10 zombies", "10"}, // 4
                {"Craft a diamond pickaxe", "1"}, // 5
                {"Craft a painting", "1"}, // 6
                {"Craft an enchanting table", "1"}, // 7
                {"Mine an ancient debris", "1"} // 8
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

    public void addPlayer(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR IGNORE INTO players(uuid) VALUES (?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRepayMode(Player player, int value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET repayMode = ? WHERE uuid = ?")) {
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getRepayMode(Player player) {
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

    public int getDeaths(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT deaths FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int deathNumber = resultSet.getInt("deaths");
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

    public String getQuestName(int questId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM quests WHERE id = ?")) {
            preparedStatement.setInt(1, questId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                resultSet.close();
                return name;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public int getQuestObjective(int questId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT objective FROM quests WHERE id = ?")) {
            preparedStatement.setInt(1, questId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int objective = resultSet.getInt("objective");
                resultSet.close();
                return objective;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private void addQuestToPlayer(int questId, Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "players_quests(player_uuid, quests_id) VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setInt(2, questId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> getPlayerQuests(Player player) {
        ArrayList<Integer> quests = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT quests_id " +
                "FROM players_quests WHERE player_uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                quests.add(resultSet.getInt("quests_id"));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quests;
    }

    private int getNumberOfQuests(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(quests_id) " +
                "AS 'nb' FROM players_quests WHERE player_uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int nb = resultSet.getInt("nb");
                resultSet.close();
                return nb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getProgression(Player pLayer, int questId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT progression FROM " +
                "players_quests WHERE player_uuid = ? AND quests_id = ?")) {
            preparedStatement.setString(1, pLayer.getUniqueId().toString());
            preparedStatement.setInt(2, questId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int progression = resultSet.getInt("progression");
                resultSet.close();
                return progression;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void addProgression(Player player, int questId) {
        if (getPlayerQuests(player).contains(questId)) {
            if (getProgression(player, questId) < getQuestObjective(questId)) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players_quests " +
                        "SET progression = ? WHERE player_uuid = ? AND quests_id = ?")) {
                    preparedStatement.setInt(1, getProgression(player, questId) + 1);
                    preparedStatement.setString(2, player.getUniqueId().toString());
                    preparedStatement.setInt(3, questId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void linkRandomQuests(Player player) {
        int count;
        int nbOfQuestToLink = 5 - getNumberOfQuests(player);
        int[] randomNumbers = new int[nbOfQuestToLink];
        Random random = new Random();
        do {
            for (int i = 0; i < randomNumbers.length; i++) {
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
            addQuestToPlayer(randomNumber, player);
        }
    }
}