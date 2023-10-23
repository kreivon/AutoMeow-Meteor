package me.kreivon.automeow.modules;

import com.mojang.authlib.GameProfile;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

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

    public static class AutoMeowChatEvent implements ClientReceiveMessageEvents.AllowChat {

        @Override
        public boolean allowReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
            if (Modules.get().get("Auto Meow").isActive()) {
                if (MinecraftClient.getInstance().player != null && message.getString().toUpperCase().contains("MEOW")) {
                    if (sender != null && sender.getId() == MinecraftClient.getInstance().player.getUuid())
                        return true;
                    if (Util.getMeasuringTimeMs() - lastMeow >= (int) Modules.get().get("Auto Meow").settings.get("Cooldown (ms)").get()) {
                        lastMeow = Util.getMeasuringTimeMs();
                        ChatUtils.sendPlayerMsg("meow :3");
                    }
                }
            }
            return true;
        }
    }

    public static class AutoMeowGameEvent implements ClientReceiveMessageEvents.ModifyGame {

        @Override
        public Text modifyReceivedGameMessage(Text message, boolean overlay) {
            if (Modules.get().get("Auto Meow").isActive()) {
                if (MinecraftClient.getInstance().player != null && message.getString().toUpperCase().contains("MEOW") && !message.contains(MinecraftClient.getInstance().player.getName())) {
                    if (Util.getMeasuringTimeMs() - lastMeow >= (int) Modules.get().get("Auto Meow").settings.get("Cooldown (ms)").get()) {
                        lastMeow = Util.getMeasuringTimeMs();
                        ChatUtils.sendPlayerMsg("meow :3");
                    }
                }
            }
            return message;
        }
    }

}
