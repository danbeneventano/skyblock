package me.danbeneventano.skyblockplugin.challenges

import me.danbeneventano.skyblockplugin.challenges.requirement.Requirement
import me.danbeneventano.skyblockplugin.challenges.rewards.Reward

abstract class Challenge (val name: String, val requirements: List<Requirement>, val rewards: List<Reward>, val repeatable: Boolean = false)