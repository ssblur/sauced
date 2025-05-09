package com.ssblur.sauced.integration.emi.recipes

import com.ssblur.sauced.Sauced
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.GeneratedSlotWidget
import net.minecraft.core.registries.BuiltInRegistries.POTION
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient
import java.util.*
import kotlin.jvm.optionals.getOrNull

open class SauceRecipe(
    id: ResourceLocation,
    val input: EmiIngredient = POTIONS,
    private val output: EmiStack = EmiStack.of(Sauced.SAUCE_ITEM)
) : CustomBrewingRecipe(id, EmiStack.of(Items.EGG)) {
    override fun getInputs(): List<EmiIngredient> = listOf(ingredient) + getInputIngredient().copy().setAmount(3)
    override fun getOutputs(): List<EmiStack> = listOf(output.copy().setAmount(3))

    override fun getInput(x: Int, y: Int): GeneratedSlotWidget =
        GeneratedSlotWidget({ r -> getPotionIngredient(r, input) }, unique, x, y)

    override fun getOutput(x: Int, y: Int): GeneratedSlotWidget =
        GeneratedSlotWidget({ r -> getPotionIngredient(r, output) }, unique, x, y)

    open fun getPotionIngredient(random: Random, ingredient: EmiIngredient): EmiStack {
        val item = ingredient.emiStacks[random.nextInt(ingredient.emiStacks.size)].itemStack.item
        val potion = POTION.getHolder(random.nextInt(POTION.size())).getOrNull() ?: Potions.WATER
        return EmiStack.of(PotionContents.createItemStack(item, potion))
    }

    open fun getInputIngredient(): EmiIngredient {
        return EmiIngredient.of(
            Ingredient.of(
                POTION.holders().flatMap { potion ->
                    input.emiStacks.stream()
                        .map { PotionContents.createItemStack(it.itemStack.item, potion) }
                })
        )
    }

    companion object {
        val POTIONS: EmiIngredient =
            EmiIngredient.of(Ingredient.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION))
    }
}
