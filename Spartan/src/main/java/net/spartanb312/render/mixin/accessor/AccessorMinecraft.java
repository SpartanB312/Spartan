package net.spartanb312.render.mixin.accessor;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public interface AccessorMinecraft {

    @Invoker(value = "readImageToBuffer")
    ByteBuffer readImageToBuffer_spartan(InputStream inputStream);

}
