package com.ssblur.sauced.integration.jei

import com.ssblur.alchimiae.item.AlchimiaeItems
import com.ssblur.sauced.Sauced
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.item.alchemy.Potions

fun brew(registration: IRecipeRegistration): IJeiBrewingRecipe {
    return registration.vanillaRecipeFactory.createBrewingRecipe(
        listOf(Items.EGG.defaultInstance),
        PotionContents.createItemStack(AlchimiaeItems.POTION.get(), Potions.HEALING),
        PotionContents.createItemStack(Sauced.SAUCE_ITEM.get(), Potions.HEALING),
        Sauced.location("/brewing/sauce_from_brew")
    )
}