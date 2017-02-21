package me.danbeneventano.skyblockplugin

import me.danbeneventano.skyblockplugin.command.ChallengeCommand
import me.danbeneventano.skyblockplugin.command.ChatCommand
import me.danbeneventano.skyblockplugin.command.IslandCommand
import me.danbeneventano.skyblockplugin.command.chat.ChatHandler
import me.danbeneventano.skyblockplugin.database.*
import me.danbeneventano.skyblockplugin.islands.Island
import me.danbeneventano.skyblockplugin.listeners.PlayerListener
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin

class SkyblockPlugin : JavaPlugin() {
    var database: Database = NullDatabase()
    var chatHandler = ChatHandler(this)
    var chat: Chat? = null

    override fun onEnable() {
        Bukkit.createWorld(WorldCreator("classic"))
        saveDefaultConfig()
        initDatabase()
        setupChat()
        getCommand("island").executor = IslandCommand(this)
        getCommand("chat").executor = ChatCommand(this)
        getCommand("challenges").executor = ChallengeCommand(this)
        server.pluginManager.registerEvents(PlayerListener(this), this)
        server.pluginManager.registerEvents(chatHandler, this)
        server.onlinePlayers.map { it.uniqueId }.forEach(database::loadUser)
        server.onlinePlayers.map { it.uniqueId }.forEach(database::loadIslands)
    }

    override fun onDisable() {
        User.users.clear()
        Island.islands.clear()
    }

    private fun initDatabase() {
        val section = config.getConfigurationSection("database")
        val type = section.getString("type")
        val user = section.getString("user")
        val password = section.getString("password")
        val host = section.getString("host")
        val port = section.getInt("port")
        val database = section.getString("authDatabase")
        when (type) {
            "mongodb" -> {
                val db = MongoDatabase(user, password, host, port, database, this)
                this.database = db
            }
            "mysql" -> {
                val db = MySqlDatabase(user, password, host, port, database, this)
                this.database = db
            }
        }
    }

    private fun setupChat(): Boolean {
        val rsp = server.servicesManager.getRegistration(Chat::class.java)
        chat = rsp.provider
        return chat != null
    }
}