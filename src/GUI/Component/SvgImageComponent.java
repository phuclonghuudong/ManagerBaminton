package GUI.Component;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URI;
import javax.swing.*;

/**
 *
 * @author phucp
 */
public class SvgImageComponent extends ImageIcon {

    private SVGDiagram diagram;
    private int outputWidth;
    private int outputHeight;

    public static ImageIcon loadSvgAsIcon(String fileName, int width, int height) {
        try {
            SVGUniverse universe = new SVGUniverse();

            String resourcePath = "/icon/" + fileName;
            java.net.URL svgUrl = SvgImageComponent.class.getResource(resourcePath);

            if (svgUrl == null) {
                System.err.println("Không tìm thấy file SVG: " + resourcePath);
                return null;
            }

            URI uri = universe.loadSVG(svgUrl.openStream(), fileName);
            SVGDiagram diagram = universe.getDiagram(uri);

            if (diagram == null) {
                System.err.println("Không thể load file SVG từ: " + resourcePath);
                return null;
            }

            int scaleFactor = 1;
            int highResWidth = width * scaleFactor;
            int highResHeight = height * scaleFactor;

            BufferedImage highResImage = new BufferedImage(highResWidth, highResHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = highResImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            double scaleX = highResWidth / diagram.getWidth();
            double scaleY = highResHeight / diagram.getHeight();
            double scale = Math.min(scaleX, scaleY);

            AffineTransform at = new AffineTransform();
            at.scale(scale, scale);
            g2.setTransform(at);
            diagram.render(g2);
            g2.dispose();

            // Downsample thủ công bằng Graphics2D cho kết quả sắc nét hơn
            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gFinal = finalImage.createGraphics();
            gFinal.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            gFinal.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            gFinal.drawImage(highResImage, 0, 0, width, height, null);
            gFinal.dispose();

            return new ImageIcon(finalImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
