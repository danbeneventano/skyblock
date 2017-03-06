package me.danbeneventano.skyblockplugin.database

import me.danbeneventano.skyblockplugin.SkyblockPlugin
import me.danbeneventano.skyblockplugin.islands.Island
import me.danbeneventano.skyblockplugin.user.User
import java.util.*

class MySqlDatabase (user: String, password: String, host: String, port: Int, database: String, val plugin: SkyblockPlugin) : Database {
    private val url = "jdbc:mysql://$host:$port/$database"

    override fun saveIsland(island: Island) {

    }

    override fun loadIslands(uuid: UUID) {

    }

    override fun loadUser(uuid: UUID) {

    }

    override fun saveUser(user: User) {

    }
}