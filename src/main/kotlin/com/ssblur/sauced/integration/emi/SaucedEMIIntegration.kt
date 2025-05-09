package com.ssblur.sauced.integration.emi

import com.ssblur.sauced.Sauced.ALCHIMIAE
import com.ssblur.sauced.Sauced.location
import com.ssblur.sauced.integration.emi.recipes.BrewSauceRecipe
import com.ssblur.sauced.integration.emi.recipes.SauceRecipe
import com.ssblur.unfocused.Unfocused
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import java.util.*

@EmiEntrypoint
class SaucedEMIIntegration : EmiPlugin {
    override fun register(registry: EmiRegistry) {
        registry.addRecipe(SauceRecipe(location("/brewing/sauce")))
        if (Unfocused.isModLoaded(ALCHIMIAE))
            registry.addRecipe(BrewSauceRecipe(location("/brewing/sauce_from_brew")))
    }

    companion object {
        val RANDOM = Random()
    }
}