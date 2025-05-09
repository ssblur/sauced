package com.ssblur.sauced.integration.jei

import com.ssblur.alchimiae.item.AlchimiaeItems
import com.ssblur.sauced.Sauced
import com.ssblur.sauced.Sauced.location
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.RecipeTypes
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionContents.createItemStack
import net.minecraft.world.item.alchemy.Potions

@JeiPlugin
class SaucedJEIIntegration : IModPlugin {
    override fun getPluginUid(): ResourceLocation = location("sauced")

    override fun registerRecipes(registration: IRecipeRegistration) {
        registration.addRecipes(
            RecipeTypes.BREWING, listOf(
                registration.vanillaRecipeFactory.createBrewingRecipe(
                    listOf(Items.EGG.defaultInstance),
                    createItemStack(Items.POTION, Potions.HEALING),
                    createItemStack(Sauced.SAUCE_ITEM, Potions.HEALING),
                    location("/brewing/sauce")
                ),
                registration.vanillaRecipeFactory.createBrewingRecipe(
                    listOf(Items.EGG.defaultInstance),
                    createItemStack(AlchimiaeItems.POTION.get(), Potions.HEALING),
                    createItemStack(Sauced.SAUCE_ITEM, Potions.HEALING),
                    location("/brewing/sauce_from_brew")
                )
            )
        )
    }
}