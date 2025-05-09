package com.ssblur.sauced.item

import com.ssblur.alchimiae.alchemy.AlchemyHelper
import com.ssblur.alchimiae.data.AlchimiaeDataComponents
import com.ssblur.alchimiae.item.potions.CustomPotion
import com.ssblur.sauced.Sauced
import com.ssblur.sauced.Sauced.hasAlchimiae
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.wiredtomato.burgered.api.Burger
import net.wiredtomato.burgered.api.StatusEffectEntry
import net.wiredtomato.burgered.api.ingredient.BurgerIngredient
import net.wiredtomato.burgered.api.ingredient.BurgerIngredientInstance
import net.wiredtomato.burgered.api.rendering.IngredientRenderSettings
import net.wiredtomato.burgered.api.rendering.ModelId
import net.wiredtomato.burgered.init.BurgeredDataComponents
import org.joml.Vector3d

open class SauceItem(properties: Properties) : Item(properties), BurgerIngredient {
    override fun canBePutOn(ingredientStack: ItemStack, burger: Burger, burgerStack: ItemStack): Boolean = true

    override fun onEat(entity: LivingEntity, world: Level, stack: ItemStack, component: FoodProperties) {
        val burger = stack.get(BurgeredDataComponents.BURGER)
        burger?.ingredients()?.forEach { ingredient ->
            if (ingredient.ingredient is SauceItem)
                ingredient.stack().get(DataComponents.POTION_CONTENTS)?.allEffects?.forEach { effect ->
                    entity.addEffect(MobEffectInstance(effect.effect, effect.duration, effect.amplifier))
                }
            if (hasAlchimiae()) AlchemyHelper.applyEffects(ingredient.stack(), entity)
        }
    }

    override fun overSaturation(instance: BurgerIngredientInstance): Double = 0.0

    override fun renderSettings(instance: BurgerIngredientInstance): IngredientRenderSettings =
        IngredientRenderSettings.ItemModel3d.CustomModeled(
            Vector3d(1.0, 1.0, 1.0),
            Vector3d(0.0, 0.0, 0.0),
            1.0,
            ModelId(Sauced.location("custom/burger_sauce"), "")
        )

    override fun saturation(instance: BurgerIngredientInstance): Int = 0

    override fun statusEffects(instance: BurgerIngredientInstance): List<StatusEffectEntry> = listOf()

    override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag)
        itemStack.get(DataComponents.POTION_CONTENTS)?.addPotionTooltip(list::add, 1.0f, tooltipContext.tickRate())
        if (hasAlchimiae()) itemStack.get(AlchimiaeDataComponents.CUSTOM_POTION)?.decorate(list)
    }

    companion object {
        fun getColor(stack: ItemStack, layer: Int): Int {
            if (layer == 0) {
                if (hasAlchimiae() && stack.has(AlchimiaeDataComponents.CUSTOM_POTION)) {
                    return CustomPotion.getColor(stack, layer)
                }
                stack.get(DataComponents.POTION_CONTENTS)?.let { return it.color }
            }

            return 0xFFFFFFFFu.toInt()
        }
    }
}