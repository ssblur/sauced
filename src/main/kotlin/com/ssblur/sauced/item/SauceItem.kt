package com.ssblur.sauced.item

import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.food.FoodProperties.PossibleEffect
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.wiredtomato.burgered.api.Burger
import net.wiredtomato.burgered.api.ingredient.BurgerIngredient
import net.wiredtomato.burgered.init.BurgeredDataComponents

class SauceItem(properties: Properties) : Item(properties), BurgerIngredient {
    override fun canBePutOn(stack: ItemStack, burger: Burger): Boolean = true

    override fun modelHeight(stack: ItemStack): Double = 1.0

    override fun onEat(entity: LivingEntity, world: Level, stack: ItemStack, component: FoodProperties) {
        val burger = stack.get(BurgeredDataComponents.BURGER)
        burger?.ingredients()?.forEach { pair ->
            if(pair.first.item is SauceItem)
                pair.first.get(DataComponents.POTION_CONTENTS)?.allEffects?.forEach { effect ->
                    entity.addEffect(effect)
                }
        }
    }

    override fun overSaturation(stack: ItemStack): Double = 0.0

    override fun saturation(stack: ItemStack): Int = 0

    override fun statusEffects(stack: ItemStack): List<PossibleEffect> = listOf()

    override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag)
        itemStack.get(DataComponents.POTION_CONTENTS)?.addPotionTooltip(list::add, 1.0f, tooltipContext.tickRate())
    }
}