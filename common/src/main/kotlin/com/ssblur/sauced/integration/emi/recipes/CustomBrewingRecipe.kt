package com.ssblur.sauced.integration.emi.recipes

import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.SlotWidget
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import java.util.*

abstract class CustomBrewingRecipe(private val id: ResourceLocation, val ingredient: EmiIngredient) : EmiRecipe {
    protected val unique: Int = RANDOM.nextInt()
    override fun getCategory(): EmiRecipeCategory = VanillaEmiRecipeCategories.BREWING
    override fun getId(): ResourceLocation = id
    override fun getDisplayWidth(): Int = 120
    override fun getDisplayHeight(): Int = 61

    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addTexture(BACKGROUND, 0, 0, 103, 61, 16, 14)
        widgets.addAnimatedTexture(BACKGROUND, 81, 2, 9, 28, 176, 0, 1000 * 20, false, false, false)
            .tooltip { _, _ -> listOf(COOKING_TIME) }
        widgets.addAnimatedTexture(BACKGROUND, 47, 0, 12, 29, 185, 0, 700, false, true, false)
        widgets.addTexture(BACKGROUND, 44, 30, 18, 4, 176, 29)
        widgets.addSlot(BLAZE_POWDER, 0, 2).drawBack(false)
        widgets.add(getInput(39, 36)).drawBack(false)
        widgets.add(getIngredient(62, 2)).drawBack(false)
        widgets.add(getOutput(85, 36)).drawBack(false).recipeContext(this)
    }

    abstract fun getInput(x: Int, y: Int): SlotWidget
    open fun getIngredient(x: Int, y: Int): SlotWidget = SlotWidget(ingredient, x, y)
    abstract fun getOutput(x: Int, y: Int): SlotWidget

    companion object {
        private val BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/container/brewing_stand.png")
        private val BLAZE_POWDER = EmiStack.of(Items.BLAZE_POWDER)
        private val COOKING_TIME =
            ClientTooltipComponent.create(Component.translatable("emi.cooking.time", 20).visualOrderText)
        val RANDOM = Random()
    }
}
