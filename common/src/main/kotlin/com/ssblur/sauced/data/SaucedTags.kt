package com.ssblur.sauced.data

import com.ssblur.sauced.Sauced.location
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object SaucedTags {
    @JvmField
    val SAUCE_CATALYST = key("sauce_catalyst")
    fun key(name: String): TagKey<Item> = TagKey.create(Registries.ITEM, location(name))
}