package com.ssblur.sauced.integration.emi

import com.ssblur.sauced.Sauced
import com.ssblur.sauced.Sauced.location
import com.ssblur.sauced.mixin.BrewingOutputAccessor
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionBrewing
import net.minecraft.world.item.alchemy.PotionContents.createItemStack
import net.minecraft.world.item.crafting.Ingredient

class SaucedEMIIntegration : EmiPlugin {
    override fun register(registry: EmiRegistry) {
        val brewingRegistry =
            (Minecraft.getInstance().level?.potionBrewing() ?: PotionBrewing.EMPTY) as BrewingOutputAccessor
        if (brewingRegistry.potionRecipes.isEmpty()) return
        brewingRegistry.potionRecipes.map { it.to }.toSet().forEach { potion ->
            registry.addRecipe(
                SauceBrewingRecipe(
                    EmiStack.of(createItemStack(Items.POTION, potion)),
                    EmiIngredient.of(Ingredient.of(Items.EGG)),
                    EmiStack.of(createItemStack(Sauced.SAUCE_ITEM, potion)),
                    location("brewing/sauce/${potion.registeredName.replace(":", "/")}")
                )
            )
        }
    }

    //(ender) this is here because emi doest not have a common brewing recipe
    // and because I wanted to have a single recipes that just loops through all potions in a single recipe
    // instead of having to make a recipe for each one
    // I didn't do the second thing cuz it would take a lot more work, ill do it later
    class SauceBrewingRecipe(
        private val input: EmiStack, private val ingredient: EmiIngredient,
        private val output: EmiStack, private val id: ResourceLocation
    ) : EmiRecipe {
        private val input3: EmiStack = input.copy().setAmount(3)
        private val output3: EmiStack = output.copy().setAmount(3)

        override fun getCategory(): EmiRecipeCategory = VanillaEmiRecipeCategories.BREWING
        override fun getId(): ResourceLocation = id

        override fun getInputs(): List<EmiIngredient> = listOf(input3, ingredient)
        override fun getOutputs(): List<EmiStack> = listOf(output3)

        override fun getDisplayWidth(): Int = 120
        override fun getDisplayHeight(): Int = 61

        override fun addWidgets(widgets: WidgetHolder) {
            widgets.addTexture(BACKGROUND, 0, 0, 103, 61, 16, 14)
            widgets.addAnimatedTexture(BACKGROUND, 81, 2, 9, 28, 176, 0, 1000 * 20, false, false, false)
                .tooltip { _, _ ->
                    listOf(
                        ClientTooltipComponent.create(Component.translatable("emi.cooking.time", 20).visualOrderText)
                    )
                }
            widgets.addAnimatedTexture(BACKGROUND, 47, 0, 12, 29, 185, 0, 700, false, true, false)
            widgets.addTexture(BACKGROUND, 44, 30, 18, 4, 176, 29)
            widgets.addSlot(BLAZE_POWDER, 0, 2).drawBack(false)
            widgets.addSlot(input, 39, 36).drawBack(false)
            widgets.addSlot(ingredient, 62, 2).drawBack(false)
            widgets.addSlot(output, 85, 36).drawBack(false).recipeContext(this)
        }

        companion object {
            private val BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/container/brewing_stand.png")
            private val BLAZE_POWDER = EmiStack.of(Items.BLAZE_POWDER)
        }
    }
}