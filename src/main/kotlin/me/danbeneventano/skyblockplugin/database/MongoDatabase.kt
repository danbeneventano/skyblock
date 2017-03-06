package me.danbeneventano.skyblockplugin.database

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import me.danbeneventano.skyblockplugin.SkyblockPlugin
import me.danbeneventano.skyblockplugin.islands.Island
import me.danbeneventano.skyblockplugin.user.User
import org.bson.Document
import org.bukkit.WorldCreator
import java.util.*

class MongoDatabase(user: String, password: String, host: String, port: Int, database: String, val plugin: SkyblockPlugin) : Database {
    private val uri = MongoClientURI("mongodb://$user:$password@$host:$port/$database")
    private val mongoClient = MongoClient(uri)
    private val skyblockDb = mongoClient.getDatabase("skyblock")
    private val islandsCollection: MongoCollection<Document>
    private val usersCollection: MongoCollection<Document>

    private val mapper = jacksonObjectMapper()

    init {
        if (!skyblockDb.listCollectionNames().contains("islands")) {
            skyblockDb.createCollection("islands")
        }
        islandsCollection = skyblockDb.getCollection("islands")

        if (!skyblockDb.listCollectionNames().contains("users")) {
            skyblockDb.createCollection("users")
        }
        usersCollection = skyblockDb.getCollection("users")
    }

    override fun saveIsland(island: Island) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, {
            val json = mapper.writeValueAsString(island)
            val doc = Document.parse(json)
            if (islandsCollection.count(Filters.eq("owner", island.owner.toString())) > 0) {
                islandsCollection.findOneAndReplace(Filters.eq("owner", island.owner.toString()), doc)
            } else {
                islandsCollection.insertOne(doc)
            }
        })
    }

    override fun loadIslands(uuid: UUID) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, {
            val islands = islandsCollection.find(Filters.eq("members", uuid.toString()))
            islands.forEach {
                it.remove("_id")
                it.remove("\$oid")
                val json = it.toJson()
                val island = mapper.readValue<Island>(json)
                Island.islands.add(island)
                if (Island.islands.filter { it.owner == island.owner }.isEmpty()) {
                    plugin.server.scheduler.runTask(plugin, { plugin.server.createWorld(WorldCreator(island.worldName)) })
                }
            }
        })
    }

    override fun loadUser(uuid: UUID) {
        if (!User.users.containsKey(uuid)) {
            plugin.server.scheduler.runTaskAsynchronously(plugin, {
                if (usersCollection.count(Filters.eq("uniqueId", uuid.toString())) > 0) {
                    val doc = usersCollection.find(Filters.eq("uniqueId", uuid.toString())).limit(1).first()
                    doc.remove("_id")
                    doc.remove("\$oid")
                    val json = doc.toJson()
                    val user = mapper.readValue<User>(json)
                    User.users.put(uuid, user)
                } else {
                    val user = User(uniqueId = uuid)
                }
            })
        }
    }

    override fun saveUser(user: User) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, {
            val json = mapper.writeValueAsString(user)
            val doc = Document.parse(json)
            if (usersCollection.count(Filters.eq("uniqueId", user.uniqueId.toString())) > 0) {
                usersCollection.findOneAndReplace(Filters.eq("uniqueId", user.uniqueId.toString()), doc)
            } else {
                usersCollection.insertOne(doc)
            }
            User.users.remove(user.uniqueId)
        })
    }
}