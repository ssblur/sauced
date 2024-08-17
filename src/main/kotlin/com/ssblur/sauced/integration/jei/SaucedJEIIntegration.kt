package com.ssblur.sauced.integration.jei

import com.ssblur.sauced.Sauced
import com.ssblur.sauced.Sauced.location
import com.ssblur.sauced.mixin.BrewingOutputAccessor
import mezz.jei.api.IModPlugin
import mezz.jei.api.constants.RecipeTypes
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionBrewing
import net.minecraft.world.item.alchemy.PotionContents.createItemStack

class SaucedJEIIntegration : IModPlugin {
    override fun getPluginUid(): ResourceLocation = location("sauced")

    override fun registerRecipes(registration: IRecipeRegistration) {
        val brewingRegistry =
            (Minecraft.getInstance().level?.potionBrewing() ?: PotionBrewing.EMPTY) as BrewingOutputAccessor
        if (brewingRegistry.potionRecipes.isEmpty()) return
        registration.addRecipes(
            RecipeTypes.BREWING,
            brewingRegistry.potionRecipes.map { it.to }.toSet().map { potion ->
                registration.vanillaRecipeFactory.createBrewingRecipe(
                    listOf(Items.EGG.defaultInstance),
                    createItemStack(Items.POTION, potion),
                    createItemStack(Sauced.SAUCE_ITEM, potion),
                    location("brewing/sauce/${potion.registeredName.replace(":", "/")}")
                )
            }
        )
    }

}