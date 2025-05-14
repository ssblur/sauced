package com.ssblur.sauced.integration.emi.recipes

import com.ssblur.alchimiae.alchemy.ClientAlchemyHelper
import com.ssblur.alchimiae.data.AlchimiaeDataComponents
import com.ssblur.alchimiae.data.CustomEffect
import com.ssblur.alchimiae.data.CustomPotionEffects
import com.ssblur.alchimiae.item.AlchimiaeItems
import com.ssblur.sauced.Sauced.SAUCE_ITEM
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.GeneratedSlotWidget
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.util.*

open class BrewSauceRecipe(id: ResourceLocation) :
    SauceRecipe(id, EmiStack.of(AlchimiaeItems.POTION.get()), EmiStack.of(SAUCE_ITEM.get())) {

    override fun getInput(x: Int, y: Int): GeneratedSlotWidget = GeneratedSlotWidget({ r ->
        val stack = getPotionIngredient(r, input).itemStack
        stack.set(DataComponents.ITEM_NAME, Component.translatable("item.alchimiae.potion"))
        EmiStack.of(stack)
    }, unique, x, y)

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
        return EmiStack.of(stack)
    }

    override fun getInputIngredient(): EmiIngredient = input
    fun getEffects() = ClientAlchemyHelper.EFFECTS.flatMap { (_, ids) -> ids ?: setOf() }.toSet().toList()
}
