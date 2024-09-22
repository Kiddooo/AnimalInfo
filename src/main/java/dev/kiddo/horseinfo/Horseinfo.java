package dev.kiddo.horseinfo;

import dev.kiddo.horseinfo.client.HorseInfoCommandHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class Horseinfo implements ModInitializer {

    @Override
    public void onInitialize() {
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, commandRegistryAccess) -> HorseInfoCommandHandler.register(dispatcher)));
    }
}
