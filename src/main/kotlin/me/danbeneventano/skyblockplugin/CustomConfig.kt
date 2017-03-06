package me.danbeneventano.skyblockplugin

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.InputStreamReader
import java.io.Reader
import java.util.logging.Level.SEVERE
import java.io.IOException
import java.util.logging.Level


class CustomConfig (val fileName: String, val plugin: Plugin) {
    var file: File? = null
    var config: FileConfiguration? = null
        get() {
            if (config == null) {
                reloadConfig()
            }
            return config
        }

    fun reloadConfig() {
        if (file == null) {
            file = File(plugin.dataFolder, fileName)
        }
        config = YamlConfiguration.loadConfiguration(file)
        val defConfigStream: InputStreamReader? = InputStreamReader(plugin.getResource(fileName), "UTF8")
        if (defConfigStream != null) {
            val defConfig = YamlConfiguration.loadConfiguration(defConfigStream)
            config!!.defaults = defConfig
        }
    }

    fun saveConfig() {
        if (config == null || file == null) {
            return
        }
        try {
            config!!.save(file)
        } catch (ex: IOException) {
            plugin.logger.log(Level.SEVERE, "Could not save config to " + file, ex)
        }

    }

    fun saveDefaultConfig() {
        if (file == null) {
            file = File(plugin.dataFolder, fileName)
        }
        if (!file!!.exists()) {
            plugin.saveResource(fileName, false)
        }
    }
}