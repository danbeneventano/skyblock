package me.danbeneventano.skyblockplugin.command

import me.danbeneventano.skyblockplugin.SkyblockPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ChallengeCommand(val plugin: SkyblockPlugin) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        return true
    }
}