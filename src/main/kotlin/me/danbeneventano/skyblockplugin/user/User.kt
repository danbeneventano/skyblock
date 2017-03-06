package me.danbeneventano.skyblockplugin.user

import java.util.*
import java.util.concurrent.ConcurrentHashMap

data class User (val uniqueId: UUID, var islandCount: Int = 0, var exp: Int = 0, val userStats: UserStats = UserStats()) {
    init {
        users[uniqueId] = this
    }

    companion object {
        val users = ConcurrentHashMap<UUID, User>()

        fun getOnlineUser(uuid: UUID) = users[uuid]
    }
}