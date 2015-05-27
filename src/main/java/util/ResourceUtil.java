package util;

import exception.ResourceConversionException;

import org.apache.commons.lang.StringUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import model.Resource;
import model.annotations.InjectLogger;

public class ResourceUtil {

  @InjectLogger
  private static Logger LOG;

  public static String THUMBNAIL_IMAGE_FORMAT = "PNG";
  public static String THUMBNAIL_IMAGE_CONTENT_TYPE = "image/png";
  public static String THUMBNAIL_NAME_PREFIX = "thumbnail";
  public static int THUMBNAIL_HEIGHT = 100;
  public static int THUMBNAIL_WIDTH = 100;

  public static Resource createThumbnail(Resource resource) {
    byte[] thumbnailContent;

    // create thumbnail
    try {
      thumbnailContent = createThumbnail(resource.getContent());
    } catch (IOException e) {
      throw new ResourceConversionException(e);
    }

    // create resource
    Resource thumbnail = new Resource();
    thumbnail.setContent(thumbnailContent);
    thumbnail.setLastModified(new Date());
    thumbnail.setMimeType(THUMBNAIL_IMAGE_CONTENT_TYPE);
    if (StringUtils.isNotBlank(resource.getName())) {
      thumbnail.setName(THUMBNAIL_NAME_PREFIX + "_" + resource.getName());
    }

    return thumbnail;
  }

  private static byte[] createThumbnail(byte[] content) throws IOException {
    try (ByteArrayInputStream bais = new ByteArrayInputStream(content)) {
      BufferedImage image = ImageIO.read(bais);
      BufferedImage thumbnail = resize(image);

      try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        ImageIO.write(thumbnail, THUMBNAIL_IMAGE_FORMAT, baos);
        return baos.toByteArray();
      }
    }
  }

  private static BufferedImage resize(BufferedImage image) {
    BufferedImage thumbnail =
        Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, THUMBNAIL_WIDTH,
            THUMBNAIL_HEIGHT);
    image.flush();
    return thumbnail;
  }

}
