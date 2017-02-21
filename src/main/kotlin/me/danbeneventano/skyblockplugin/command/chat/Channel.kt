package me.danbeneventano.skyblockplugin.command.chat

import net.md_5.bungee.api.ChatColor

enum class Channel (val prefix: String) {
    GLOBAL ("${ChatColor.YELLOW}${ChatColor.BOLD}[G]"),
    ISLAND ("${ChatColor.GREEN}${ChatColor.BOLD}[I]")
}