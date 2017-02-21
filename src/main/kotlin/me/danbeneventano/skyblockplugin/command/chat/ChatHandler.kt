package me.danbeneventano.skyblockplugin.command.chat

import me.danbeneventano.skyblockplugin.SkyblockPlugin
import me.danbeneventano.skyblockplugin.islands.Island
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*

class ChatHandler (val plugin: SkyblockPlugin) : Listener {
    val talkChannels = mutableMapOf<UUID, Channel>()
    val watchedChannels = mutableMapOf<UUID, Set<Channel>>()

    @EventHandler fun onChat (event: AsyncPlayerChatEvent) {
        event.isCancelled = true
        val uuid = event.player.uniqueId
        val chatPrefix = talkChannels.getOrPut(uuid, {Channel.GLOBAL}).prefix
        val playerPrefix = plugin.chat?.getPlayerPrefix(event.player)
        val message = "${chatPrefix} ${ChatColor.translateAlternateColorCodes('&', playerPrefix)}${event.player.name} ${ChatColor.GRAY}: ${event.message}"

        if (talkChannels[uuid] == Channel.GLOBAL) {
            plugin.server.onlinePlayers.
                    map { it.uniqueId }.
                    filter { watchedChannels.
                            getOrPut(it, { setOf(Channel.GLOBAL, Channel.ISLAND) }).
                            contains(Channel.GLOBAL) }.
                    forEach { plugin.server.getPlayer(it)?.sendMessage(message) }
            return
        }
        if (talkChannels[uuid] == Channel.ISLAND) {
            val members = mutableSetOf<UUID>()
            Island.getIslands(uuid).forEach { members.addAll(it.members) }
            members.mapNotNull { Bukkit.getPlayer(it) }.forEach { it.sendMessage(message) }
        }
    }
}