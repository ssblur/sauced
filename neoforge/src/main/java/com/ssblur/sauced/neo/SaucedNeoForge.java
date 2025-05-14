package com.ssblur.sauced.neo;

import com.ssblur.sauced.Sauced;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Sauced.MODID)
public final class SaucedNeoForge {
    public SaucedNeoForge() {
        Sauced.init();
        if (FMLEnvironment.dist == Dist.CLIENT) Sauced.clientInit();
    }
}
