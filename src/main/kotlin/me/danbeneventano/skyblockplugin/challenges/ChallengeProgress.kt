package me.danbeneventano.skyblockplugin.challenges

import me.danbeneventano.skyblockplugin.challenges.requirement.Requirement

data class ChallengeProgress (val challenge: Challenge, val map: MutableMap<Requirement, Int> = mutableMapOf())