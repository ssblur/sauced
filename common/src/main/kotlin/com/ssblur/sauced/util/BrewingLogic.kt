package com.ssblur.sauced.util

import com.ssblur.alchimiae.data.AlchimiaeDataComponents
import com.ssblur.alchimiae.item.AlchimiaeItems
import com.ssblur.sauced.Sauced
import com.ssblur.sauced.Sauced.hasAlchimiae
import com.ssblur.sauced.data.SaucedTags
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

fun canCreateSauce(potion: ItemStack, ingredient: ItemStack): Boolean {
  if(potion.isEmpty || ingredient.isEmpty) return false
  if(!ingredient.`is`(SaucedTags.SAUCE_CATALYST)) return false
  if(potion.`is`(Items.POTION)) return true

  if(hasAlchimiae() && potion.`is`(AlchimiaeItems.POTION.get())) return true
  return false
}

fun cookSauce(potion: ItemStack, ingredient: ItemStack): ItemStack? {
  if(potion.isEmpty || ingredient.isEmpty) return null
  if(!ingredient.`is`(SaucedTags.SAUCE_CATALYST)) return null
  if(potion.`is`(Items.POTION)) {
    potion.get(DataComponents.POTION_CONTENTS)?.let {
     return potion.transmuteCopy(Sauced.SAUCE_ITEM.get())
    }
  }

  if(hasAlchimiae() && potion.`is`(AlchimiaeItems.POTION.get())) {
    potion.get(AlchimiaeDataComponents.CUSTOM_POTION)?.let {
      return potion.transmuteCopy(Sauced.SAUCE_ITEM.get())
    }
  }
  return null
}