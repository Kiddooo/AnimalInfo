package dev.kiddo.horseinfo.client;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.DonkeyEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.MuleEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class HorseInfoCommandHandler {

    private static double getJumpHeight(double jumpStrength) {
        return -0.1817584952 * jumpStrength * jumpStrength * jumpStrength + 3.689713992 * jumpStrength * jumpStrength +
                2.128599134 * jumpStrength - 0.343930367;
    }


    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("horseinfo").executes(context -> {
            FabricClientCommandSource source = context.getSource();

            // Get the entity being looked at
            Entity entity = source.getClient().targetedEntity;


            switch (entity) {
                case DonkeyEntity donkey -> {
                    DecimalFormat df = new DecimalFormat("#.###");

                    MutableText comma = Text.literal(": ").styled(style -> style.withColor(Formatting.WHITE));
                    MutableText movementSpeed = Text.literal("\nSpeed").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText movementSpeedValue = Text.literal(df.format(donkey.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 42.1629629629629)).styled(style -> style.withColor(TextColor.fromRgb(0x79BAEC)));

                    MutableText jumpHeight = Text.literal("\nJump Height").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText jumpHeightValue = Text.literal(df.format(getJumpHeight(donkey.getAttributeBaseValue(EntityAttributes.GENERIC_JUMP_STRENGTH)))).styled(style -> style.withColor(TextColor.fromRgb(0x4dd676)));

                    MutableText health = Text.literal("\nHealth").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText healthValue = Text.literal(String.valueOf(donkey.getMaxHealth())).styled(style -> style.withColor(Formatting.RED));

                    MutableText message = movementSpeed.append(comma).append(movementSpeedValue).append(jumpHeight).append(comma).append(jumpHeightValue).append(health).append(comma).append(healthValue);

                    sendInfoMessage(source, "Donkey", message);
                    return 1;
                }
                case MuleEntity mule -> {
                    DecimalFormat df = new DecimalFormat("#.###");

                    MutableText comma = Text.literal(": ").styled(style -> style.withColor(Formatting.WHITE));
                    MutableText movementSpeed = Text.literal("\nSpeed").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText movementSpeedValue = Text.literal(df.format(mule.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 42.1629629629629)).styled(style -> style.withColor(TextColor.fromRgb(0x79BAEC)));

                    MutableText jumpHeight = Text.literal("\nJump Height").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText jumpHeightValue = Text.literal(df.format(getJumpHeight(mule.getAttributeBaseValue(EntityAttributes.GENERIC_JUMP_STRENGTH)))).styled(style -> style.withColor(TextColor.fromRgb(0x4dd676)));

                    MutableText health = Text.literal("\nHealth").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText healthValue = Text.literal(String.valueOf(mule.getMaxHealth())).styled(style -> style.withColor(Formatting.RED));

                    MutableText message = movementSpeed.append(comma).append(movementSpeedValue).append(jumpHeight).append(comma).append(jumpHeightValue).append(health).append(comma).append(healthValue);

                    sendInfoMessage(source, "Mule", message);
                    return 1;
                }
                case HorseEntity horse -> {
                    DecimalFormat df = new DecimalFormat("#.###");

                    MutableText comma = Text.literal(": ").styled(style -> style.withColor(Formatting.WHITE));
                    MutableText movementSpeed = Text.literal("\nSpeed").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText movementSpeedValue = Text.literal(df.format(horse.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 42.1629629629629)).styled(style -> style.withColor(TextColor.fromRgb(0x79BAEC)));

                    MutableText jumpHeight = Text.literal("\nJump Height").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText jumpHeightValue = Text.literal(df.format(getJumpHeight(horse.getAttributeBaseValue(EntityAttributes.GENERIC_JUMP_STRENGTH)))).styled(style -> style.withColor(TextColor.fromRgb(0x4dd676)));

                    MutableText health = Text.literal("\nHealth").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText healthValue = Text.literal(String.valueOf(horse.getMaxHealth())).styled(style -> style.withColor(Formatting.RED));

                    MutableText patternVariant = Text.literal("\nPattern Variant").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText patternVariantValue = Text.literal(horse.getMarking().name().replace("_", " ").toLowerCase()
                                    .replace("white dots", "White Spots")
                                    .replace("white", "White Socks")
                                    .replace("whitefield", "Whitefield")
                                    .replace("black dots", "Black Dots")
                                    .replace("none", "Plain"))
                            .styled(style -> style.withColor(Formatting.LIGHT_PURPLE));

                    MutableText horseColor = Text.literal("\nColor").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
                    MutableText horseColorValue = Text.literal(horse.getVariant().name().replace("_", " ").toLowerCase()).styled(style -> style.withColor(Formatting.BLUE));


                    MutableText message = movementSpeed.append(comma).append(movementSpeedValue)
                            .append(jumpHeight).append(comma).append(jumpHeightValue)
                            .append(health).append(comma).append(healthValue)
                            .append(patternVariant).append(comma).append(patternVariantValue)
                            .append(horseColor).append(comma).append(horseColorValue);
                    sendInfoMessage(source, "Horse", message);
                    return 1;

                }
                case null, default -> {
                    sendInvalidEntityMessage(source);
                    return 0;
                }
            }
        }));
    }

    private static void sendInvalidEntityMessage(FabricClientCommandSource source) {
        MutableText message = Text.literal("No valid entity selected or not a horse").styled(style ->
                style.withColor(TextColor.fromRgb(0xFF0000))); // Red color
        source.sendFeedback(message);
    }

    private static void sendInfoMessage(FabricClientCommandSource source, String entity, MutableText message) {
        MutableText baseText = Text.literal(entity + " Information").styled(style -> style.withColor(TextColor.fromRgb(0xffca800)));
        MutableText comma = Text.literal(": ").styled(style -> style.withColor(Formatting.WHITE));

        source.sendFeedback(baseText.append(comma).append(message));
    }
}
