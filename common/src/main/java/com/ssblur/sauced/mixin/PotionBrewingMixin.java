package com.ssblur.sauced.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.ssblur.sauced.data.SaucedTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.ssblur.sauced.util.BrewingLogicKt.canCreateSauce;
import static com.ssblur.sauced.util.BrewingLogicKt.cookSauce;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
  @ModifyReturnValue(method = "isIngredient", at = @At("RETURN"))
  private boolean eggCheck(boolean original, ItemStack stack) {
    return original || stack.is(SaucedTags.SAUCE_CATALYST);
  }

  @Inject(method = "mix", at = @At("HEAD"), cancellable = true)
  private void cookingSauce(ItemStack ingredient, ItemStack potion, CallbackInfoReturnable<ItemStack> info) {
    var sauce = cookSauce(potion, ingredient);
    if(sauce != null) info.setReturnValue(sauce);
  }

  @ModifyReturnValue(method = "hasMix", at = @At("RETURN"))
  private boolean sauceCheck(boolean original, ItemStack potion, ItemStack ingredient) {
    return original || canCreateSauce(potion, ingredient);
  }
}
