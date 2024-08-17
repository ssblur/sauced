package com.ssblur.sauced

import com.ssblur.sauced.item.SauceItem
import dev.architectury.platform.Platform
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import org.slf4j.LoggerFactory
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.world.item.alchemy.PotionContents


object Sauced : ModInitializer {
    val MOD_ID = "sauced"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

    val SAUCE_ITEM = SauceItem(Item.Properties().component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY))

	override fun onInitialize() {
        LOGGER.info("Loading Sauced for Burgered")

        Registry.register(BuiltInRegistries.ITEM, location("sauce"), SAUCE_ITEM)

        if(Platform.getEnv() == EnvType.CLIENT) {
            ColorProviderRegistry.ITEM.register({ stack, layer ->
                stack.get(DataComponents.POTION_CONTENTS)?.color ?: 0xFFFFFF
            }, SAUCE_ITEM)
        }
	}

    fun location(name: String) : ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
}