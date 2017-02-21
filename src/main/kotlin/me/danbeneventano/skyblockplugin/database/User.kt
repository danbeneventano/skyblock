package me.danbeneventano.skyblockplugin.database

import me.danbeneventano.skyblockplugin.challenges.ChallengeProgress
import java.util.*
import java.util.concurrent.ConcurrentHashMap

data class User (val uniqueId: UUID, var islandCount: Int = 0, var exp: Int = 0, val challengeProgress: MutableList<ChallengeProgress> = mutableListOf()) {
    init {
        users[uniqueId] = this
    }

    companion object {
        val users = ConcurrentHashMap<UUID, User>()

        fun getOnlineUser(uuid: UUID) = users[uuid]
    }
}