package me.danbeneventano.skyblockplugin.command

import me.danbeneventano.skyblockplugin.SkyblockPlugin
import me.danbeneventano.skyblockplugin.database.User
import me.danbeneventano.skyblockplugin.islands.Island
import net.md_5.bungee.api.ChatColor
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File
import java.util.*

class IslandCommand(val plugin: SkyblockPlugin) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            //help
            sender.sendMessage(arrayOf(
                    "/island create",
                    "/island go"
            ))
            return true
        }

        val sub = args[0]
        val args = args.drop(1)

        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}This command is disabled for the console.")
            return true
        }

        if (sub.equals("emptyworld", true) && sender.isOp) {
            if (args.isEmpty()) {
                sender.sendMessage("${ChatColor.RED}/createemptyworld <name>")
                return true
            }
            val name = args[0]
            val world = Bukkit.getServer().createWorld(WorldCreator(name).type(WorldType.FLAT).generatorSettings("3;minecraft:air;4").generateStructures(false))
            world.setSpawnLocation(0, 60, 0)
            world.getBlockAt(0, 60, 0).type = Material.GLASS
            sender.sendMessage("${ChatColor.BLUE}Created world $name")
            if (sender is Player) {
                sender.teleport(world.spawnLocation)
                sender.isFlying = true
                sender.allowFlight = true
                sender.gameMode = GameMode.CREATIVE
            }
            return true
        }

        if (sub.equals("create", true)) {
            if (sender !is Player) return true
            val user = User.getOnlineUser(sender.uniqueId)!!
            if (user.islandCount > 0) {
                sender.sendMessage("${ChatColor.RED}Donate to create more islands.")
                return true
            }
            val world = createIsland(sender.uniqueId)
            val island = Island(owner = sender.uniqueId, worldName = world.name)
            Island.islands.add(island)
            island.members.add(sender.uniqueId)
            sender.teleport(world.spawnLocation)
            sender.sendMessage("${ChatColor.BLUE}Welcome to the island!")
            return true
        }

        if (sub.equals("go", true)) {
            if (sender !is Player) return true
            val user = User.getOnlineUser(sender.uniqueId)!!
            if (user.islandCount == 0) {
                sender.sendMessage("${ChatColor.RED}You don't have an island. Use /island create to make one.")
                return true
            }
            val worldName = "${sender.uniqueId.toString().replace("-", "")}_1"
            val world = plugin.server.createWorld(WorldCreator(worldName))
            sender.teleport(world.spawnLocation)
            sender.sendMessage("${ChatColor.BLUE}Welcome to the island!")
        }

        return true
    }

    private fun createIsland (uuid: UUID): World {
        val user = User.getOnlineUser(uuid)!!
        user.islandCount++
        val worldName = "${uuid.toString().replace("-", "")}_${user.islandCount}"
        val island = Island(owner = uuid, worldName = worldName)
        island.members.add(uuid)
        val source = Bukkit.getWorld("classic").worldFolder
        val target = File(Bukkit.getWorldContainer(), worldName)
        WorldUtils.copyWorld(source, target)
        val world = plugin.server.createWorld(WorldCreator(worldName))
        world.setSpawnLocation(6, 56, 3)
        return world
    }
}