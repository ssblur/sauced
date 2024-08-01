package com.ssblur.sauced.mixin;

import com.ssblur.sauced.Sauced;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
  @Inject(method = "isIngredient", at = @At("HEAD"), cancellable = true)
  private void mashIsIngredient(ItemStack item, CallbackInfoReturnable<Boolean> info) {
    if(item.is(Items.EGG)) info.setReturnValue(true);
  }

  @Inject(method = "mix", at = @At("HEAD"), cancellable = true)
  private void mashMix(ItemStack ingredient, ItemStack potion, CallbackInfoReturnable<ItemStack> info) {
    var potionContents = potion.get(DataComponents.POTION_CONTENTS);
    if(ingredient.is(Items.EGG) && potionContents != null) {
      var out = potion.transmuteCopy(Sauced.INSTANCE.getSAUCE_ITEM());
      info.setReturnValue(out);
    }
  }

  @Inject(method = "hasMix", at = @At("HEAD"), cancellable = true)
  private void mashHasMix(ItemStack potion, ItemStack ingredient, CallbackInfoReturnable<Boolean> info) {
    if(ingredient.is(Items.EGG) && potion.is(Items.POTION))
      info.setReturnValue(true);
  }
}
