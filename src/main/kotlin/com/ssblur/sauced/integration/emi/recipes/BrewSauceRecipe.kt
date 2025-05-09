package com.ssblur.sauced.integration.emi.recipes

import com.ssblur.alchimiae.alchemy.ClientAlchemyHelper
import com.ssblur.alchimiae.data.AlchimiaeDataComponents
import com.ssblur.alchimiae.data.CustomEffect
import com.ssblur.alchimiae.data.CustomPotionEffects
import com.ssblur.alchimiae.item.AlchimiaeItems
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

open class BrewSauceRecipe(id: ResourceLocation) : SauceRecipe(id, CUSTOM_POTIONS) {
    override fun getPotionIngredient(random: Random, ingredient: EmiIngredient): EmiStack {
        val stack = ingredient.emiStacks[random.nextInt(ingredient.emiStacks.size)].itemStack
        val potion = mutableListOf<CustomEffect>()
        val allEffects = getEffects()
        val amount = 1 + random.nextInt(8)
        for (i in 0..<amount) {
            potion.add(
                CustomEffect(
                    allEffects[random.nextInt(allEffects.size)],
                    20 * 60 * (1 + random.nextInt(25)),
                    random.nextInt(4)
                )
            )
        }
        stack.set(AlchimiaeDataComponents.CUSTOM_POTION, CustomPotionEffects(potion))
        stack.set(DataComponents.ITEM_NAME, Component.translatable("item.alchimiae.potion"));
        return EmiStack.of(stack)
    }

    override fun getInputIngredient(): EmiIngredient = input
    fun getEffects() = ClientAlchemyHelper.EFFECTS.flatMap { (_, ids) -> ids ?: setOf() }.toSet().toList()

    companion object {
        val CUSTOM_POTIONS: EmiIngredient = EmiIngredient.of(
            Ingredient.of(
                AlchimiaeItems.POTION.get(), AlchimiaeItems.SPLASH_POTION.get(), AlchimiaeItems.LINGERING_POTION.get()
            )
        )
    }
}
