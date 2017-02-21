package me.danbeneventano.skyblockplugin.command

import me.danbeneventano.skyblockplugin.SkyblockPlugin
import me.danbeneventano.skyblockplugin.command.chat.Channel
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChatCommand (val plugin: SkyblockPlugin) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}This command is disabled for the console.")
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(arrayOf(
                    "/chat island",
                    "/chat global"
            ))
            return true
        }
        val channel = args[0].toLowerCase()
        if (channel.startsWith("i")) {
            plugin.chatHandler.talkChannels[sender.uniqueId] = Channel.ISLAND
            sender.sendMessage("${ChatColor.BLUE}You are now speaking to your island members.")
        } else if (channel.startsWith("g")) {
            plugin.chatHandler.talkChannels[sender.uniqueId] = Channel.GLOBAL
            sender.sendMessage("${ChatColor.BLUE}You are now speaking to everyone.")
        }

        return true
    }
}