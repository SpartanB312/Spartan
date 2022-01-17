import net.spartanb312.render.launch.Logger;
import net.spartanb312.render.launch.ResourceCenter;
import net.spartanb312.render.launch.mod.Extension;
import net.spartanb312.render.launch.mod.Mod;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mod.Extension(
        name = "IconSetter",
        fatherId = "spartan",
        version = "1.0",
        description = "nmsl"
)
public class IconSetter implements Extension {

    @Override
    public void init() {
        Logger.INSTANCE.fatal("Setting icon");
        try {
            setIcon();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setIcon() throws IOException {
        Display.setIcon(new ByteBuffer[]{
                readImageToBuffer(ResourceCenter.getResourceAsStream("/assets/spartan/icon/logo32.png")),
                readImageToBuffer(ResourceCenter.getResourceAsStream("/assets/spartan/icon/logo16.png"))});
    }

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {

        BufferedImage bufferedimage = ImageIO.read(imageStream);

        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint) {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }

}
