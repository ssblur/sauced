package com.ssblur.sauced

import com.ssblur.sauced.item.SauceItem
import net.fabricmc.api.ModInitializer
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import org.slf4j.LoggerFactory

object Sauced : ModInitializer {
    val MOD_ID = "sauced"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

    val SAUCE_ITEM = SauceItem(Item.Properties())

	override fun onInitialize() {
        LOGGER.info("Loading Sauced for Burgered")

        Registry.register(BuiltInRegistries.ITEM, location("sauce"), SAUCE_ITEM)
	}

    fun location(name: String) : ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
}