package me.danbeneventano.skyblockplugin.challenges

import me.danbeneventano.skyblockplugin.challenges.Requirement
import me.danbeneventano.skyblockplugin.challenges.Reward

data class Challenge (val name: String,
                      val requirements: List<Requirement>,
                      val rewards: List<Reward>,
                      val tier: Int,
                      val repeatable: Boolean)