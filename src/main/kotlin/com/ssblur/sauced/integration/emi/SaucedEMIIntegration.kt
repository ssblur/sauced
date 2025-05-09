package com.ssblur.sauced.integration.emi

import com.ssblur.sauced.Sauced.hasAlchimiae
import com.ssblur.sauced.Sauced.location
import com.ssblur.sauced.integration.emi.recipes.BrewSauceRecipe
import com.ssblur.sauced.integration.emi.recipes.SauceRecipe
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry

@EmiEntrypoint
class SaucedEMIIntegration : EmiPlugin {
    override fun register(registry: EmiRegistry) {
        registry.addRecipe(SauceRecipe(location("/brewing/sauce")))
        if (hasAlchimiae()) registry.addRecipe(BrewSauceRecipe(location("/brewing/sauce_from_brew")))
    }
}