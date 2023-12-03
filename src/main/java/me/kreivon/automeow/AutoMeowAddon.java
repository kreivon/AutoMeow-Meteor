package me.kreivon.automeow;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class AutoMeowAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOG.info("Initializing AutoMeow");

        // Modules
        Modules.get().add(new me.kreivon.automeow.modules.AutoMeow());
    }

    @Override
    public String getPackage() {
        return "me.kreivon.automeow";
    }
}
