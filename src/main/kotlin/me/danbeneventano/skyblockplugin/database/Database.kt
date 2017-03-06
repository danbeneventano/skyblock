package me.danbeneventano.skyblockplugin.database

import me.danbeneventano.skyblockplugin.islands.Island
import me.danbeneventano.skyblockplugin.user.User
import java.util.*

interface Database {
    fun saveIsland(island: Island)

    fun loadIslands(uuid: UUID)

    fun loadUser(uuid: UUID)

    fun saveUser(user: User)
}