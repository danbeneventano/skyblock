package me.danbeneventano.skyblockplugin.database

import me.danbeneventano.skyblockplugin.islands.Island
import java.util.*

class NullDatabase : Database {
    override fun saveIsland(island: Island) {}

    override fun loadIslands(uuid: UUID) {}

    override fun loadUser(uuid: UUID) {}

    override fun saveUser(user: User) {}
}