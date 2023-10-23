package me.kreivon.automeow;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import org.slf4j.Logger;

public class AutoMeow extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOG.info("Initializing AutoMeow");

        // Modules
        Modules.get().add(new me.kreivon.automeow.modules.AutoMeow());
        ClientReceiveMessageEvents.ALLOW_CHAT.register(new me.kreivon.automeow.modules.AutoMeow.AutoMeowChatEvent());
        ClientReceiveMessageEvents.MODIFY_GAME.register(new me.kreivon.automeow.modules.AutoMeow.AutoMeowGameEvent());
    }

    @Override
    public String getPackage() {
        return "me.kreivon.automeow";
    }
}
