package dev.kiddo.horseinfo.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class HorseInfoCommandHandler {

    public static void register(CommandDispatcher<Object> dispatcher, CommandRegistryAccess commandRegistryAccess) {
        dispatcher.register(LiteralArgumentBuilder.literal("horseinfo").executes(context -> {
                    Entity entity = context.getArgument("entity", Entity.class);

                    if (!(entity instanceof HorseEntity)) {
                        sendInvalidEntityMessage((FabricClientCommandSource) context.getSource());
                        return 0;
                    }

                    HorseEntity horse = (HorseEntity) entity;

                    String speed = "Speed: " + horse.speed;
                    String jumpHeight = "Jump Height: " + horse.getHeight();
                    String health = "Health: " + horse.getHealth();
                    String patternVariant = "Pattern Variant: " + horse.getMarking().toString();
                    String color = "Color: " + horse.getVariant().asString();
                    String owner = "Owner: " + Objects.requireNonNull(horse.getOwner());

                    String info = speed + "\n" + jumpHeight + "\n" + health + "\n" + patternVariant + "\n" + color + "\n" + owner;

                    sendHorseInfoMessage((FabricClientCommandSource) context.getSource(), info);
                    return 1;
                }
        ));
    }

    private static void sendInvalidEntityMessage(FabricClientCommandSource source) {
        MutableText message = Text.literal("Selected entity is not a horse").styled(style ->
                style.withColor(TextColor.fromRgb(0xFF0000))); // Red color
        source.sendFeedback(message);
    }

    private static void sendHorseInfoMessage(FabricClientCommandSource source, String info) {
        MutableText baseText = Text.literal("Horse Information: ").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));

        MutableText shopsText = Text.literal(info).styled(style -> style.withColor(Formatting.WHITE));

        MutableText message = baseText.append(shopsText);

        source.sendFeedback(message);
    }
}
