package me.danbeneventano.skyblockplugin.islands

import java.util.*

data class Island (val owner: UUID, val worldName: String, val level: Int = 0, val members: MutableList<UUID> = mutableListOf()) {
    companion object {
        val islands = mutableListOf<Island>()

        fun getIslands (uuid: UUID) = islands.filter { it.members.contains(uuid) }
    }
}
