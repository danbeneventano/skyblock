package me.danbeneventano.skyblockplugin.listeners

import me.danbeneventano.skyblockplugin.SkyblockPlugin
import me.danbeneventano.skyblockplugin.database.User
import me.danbeneventano.skyblockplugin.islands.Island
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener (val plugin: SkyblockPlugin) : Listener {

    @EventHandler fun onJoin (event: PlayerJoinEvent) {
        plugin.database.loadUser(event.player.uniqueId)
        plugin.database.loadIslands(event.player.uniqueId)
    }

    @EventHandler fun onLeave (event: PlayerQuitEvent) {
        plugin.database.saveUser(User.getOnlineUser(event.player.uniqueId)!!)
        User.users.remove(event.player.uniqueId)
        Island.islands.filter { it.members.contains(event.player.uniqueId) }.forEach(plugin.database::saveIsland)
    }
}