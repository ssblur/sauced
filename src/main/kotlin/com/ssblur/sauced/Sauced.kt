package com.ssblur.sauced

import com.ssblur.sauced.item.SauceItem
import com.ssblur.unfocused.ModInitializer
import com.ssblur.unfocused.Unfocused
import com.ssblur.unfocused.helper.ColorHelper.registerColor
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.PotionContents
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object Sauced : ModInitializer("sauced") {
    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(id)

    @JvmField
    val SAUCE_ITEM = registerItem("sauce") {
        SauceItem(Item.Properties().component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY))
    }

    fun init() {
        LOGGER.info("Loading Sauced for Burgered")
    }

    fun clientInit() {
        SAUCE_ITEM.registerColor(SauceItem::getColor)
    }

    fun hasAlchimiae() = Unfocused.isModLoaded("alchimiae")
}