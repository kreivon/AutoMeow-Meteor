package me.kreivon.automeow.modules;

import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Util;

public class AutoMeow extends Module {

    public static long lastMeow;
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> cooldown = sgGeneral.add(new IntSetting.Builder()
        .name("Cooldown (ms)")
        .description("Cooldown in-between messages in milliseconds")
        .defaultValue(10000)
        .min(0)
        .sliderMin(0)
        .sliderMax(20000)
        .build()
    );

    public AutoMeow() {
        super(Categories.Misc, "Auto Meow", "Automatically meows when somebody else meows in chat.");
    }

    @Override
    public void onActivate() {
        super.onActivate();
        lastMeow = Util.getMeasuringTimeMs() + cooldown.get();
    }

    @EventHandler
    private void onMessageReceive(ReceiveMessageEvent event) {
        if (mc.player != null && event.getMessage().getString().toUpperCase().contains("MEOW") && !event.getMessage().contains(mc.player.getName())) {
            if (Util.getMeasuringTimeMs() - lastMeow >= (int) Modules.get().get("Auto Meow").settings.get("Cooldown (ms)").get()) {
                lastMeow = Util.getMeasuringTimeMs();
                ChatUtils.sendPlayerMsg("meow :3");
            }
        }
    }

}
