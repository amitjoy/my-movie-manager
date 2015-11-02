package in.mymoviemanager.rcp;

import in.mymoviemanager.xml.convert.XMLManipulation;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.eclipse.e4.core.di.InjectorFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Singleton Activator for RCP Bundle
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class Activator extends AbstractUIPlugin implements BundleActivator {

	private static BundleContext context;
	public static final String PLUGIN_ID = "in.mymoviemanager.rcp"; //$NON-NLS-1$

	private static Map<String, Image> imageRegistry = new HashMap<String, Image>();

	static BundleContext getContext() {
		return context;
	}

	@SuppressWarnings("restriction")
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		InjectorFactory.getDefault().addBinding(IXMLManipulation.class)
				.implementedBy(XMLManipulation.class);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public static IWorkbench getDefault() {
		return PlatformUI.getWorkbench();
	}

	public static synchronized void playJingle() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(getClass()
									.getResourceAsStream("/jingle.wav")); //$NON-NLS-1$
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static java.awt.Image getAWTImage(String path) {
		return convertToAWT(getImage(path).getImageData());
	}

	public static Image getImage(String path) {
		if (!imageRegistry.containsKey(path)) {
			imageRegistry.put(path, getImageDescriptor(path).createImage());
		}
		return imageRegistry.get(path);

	}

	/**
	 * Converts ImageData to AWT Image
	 * 
	 * @param data
	 * @return
	 */
	static BufferedImage convertToAWT(ImageData data) {
		ColorModel colorModel = null;
		PaletteData palette = data.palette;
		if (palette.isDirect) {
			colorModel = new DirectColorModel(data.depth, palette.redMask,
					palette.greenMask, palette.blueMask);
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width,
							data.height), false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[3];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					RGB rgb = palette.getRGB(pixel);
					pixelArray[0] = rgb.red;
					pixelArray[1] = rgb.green;
					pixelArray[2] = rgb.blue;
					raster.setPixels(x, y, 1, 1, pixelArray);
				}
			}
			return bufferedImage;
		} else {
			RGB[] rgbs = palette.getRGBs();
			byte[] red = new byte[rgbs.length];
			byte[] green = new byte[rgbs.length];
			byte[] blue = new byte[rgbs.length];
			for (int i = 0; i < rgbs.length; i++) {
				RGB rgb = rgbs[i];
				red[i] = (byte) rgb.red;
				green[i] = (byte) rgb.green;
				blue[i] = (byte) rgb.blue;
			}
			if (data.transparentPixel != -1) {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue, data.transparentPixel);
			} else {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue);
			}
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width,
							data.height), false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					pixelArray[0] = pixel;
					raster.setPixel(x, y, pixelArray);
				}
			}
			return bufferedImage;
		}
	}
}
