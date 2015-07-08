package services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;

import model.ExportTemplateDefinition;
import model.Profile;
import model.Project;
import model.ProjectAssociation;
import model.Resource;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import conf.FreemarkerConfigurationProvider;

public class ProfileExportServiceTest {

  public static final String PDF_PROFILE_IMAGE = "/exportTemplates/pdf-profileImage.png";
  public static final String PDF_MAREDIT_LOGO = "/exportTemplates/pdf-maredit-logo.png";
  private ProfileExportService profileExportService;
  private TemplateExportService templateExportService;
  private Profile profile;
  private ExportTemplateDefinition template;

  @Before
  public void setUp() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    profileExportService =
        new ProfileExportService(mapper, new FreemarkerConfigurationProvider().get());
    templateExportService = new TemplateExportService(mapper);
    template = templateExportService.getTemplate("Standard");
    profile = new Profile();
    profile.setFirstname("Max");
    profile.setLastname("Mustermann");
    profile.setCareerLevel(Lists.newArrayList("Manager", "Consultant"));
    profile.setWorkExperience(new Date());
    profile.setDegrees(Lists.newArrayList("Dipl.Ing"));
    profile.setIndustries(Lists.newArrayList("Versandhandel", "E-Commerce"));

    Project project = new Project();
    project.setStart(new Date());
    project.setEnd(new Date());
    project.setTitle("Test Project");

    ProjectAssociation projectAssociation = new ProjectAssociation();
    projectAssociation.setPositions(Lists.newArrayList("Developer", "Project Manager"));
    projectAssociation.setProject(project);
    projectAssociation.setStart(new Date());
    projectAssociation.setEnd(new Date());

    profile.setProjectAssociations(Lists.newArrayList(projectAssociation));
  }

  @Test
  public void exportReturnsPdfWithProfileData() throws Exception {
    assertThat(generatePDFAndGetText()).contains("Max", "Mustermann");
  }

  @Test
  public void exportWithSpecialCharacters() throws Exception {
    profile.setFirstname("<>&\"'");
    assertThat(generatePDFAndGetText()).contains("<>&\"'");
  }

  @Test(expected = NullPointerException.class)
  public void exportWithNullProfileThrowsException() throws Exception {
    profileExportService.exportToPdf(null, null);
    fail("No expected exception was thrown");
  }

  @Test
  public void listOfTagsAreCommaSeparated() throws Exception {
    assertThat(generatePDFAndGetText()).contains("Manager, Consultant");
  }

  @Test
  public void documentContainsFooterLogo() throws Exception {
    final byte[] expected =
        Files.toByteArray(new File(getClass().getResource(PDF_MAREDIT_LOGO).toURI()));

    assertThat(getImages()).contains(expected);
  }

  @Test
  public void containsProfileImage() throws Exception {
    Resource resource = new Resource();
    resource.setId(new ObjectId());
    resource.setContent(Files.toByteArray(new File(getClass().getResource(
        "/exportTemplates/maredit-logo.png").toURI())));
    profile.setImage(resource);

    final byte[] logo =
        Files.toByteArray(new File(getClass().getResource(PDF_MAREDIT_LOGO).toURI()));

    final byte[] profileImage =
        Files.toByteArray(new File(getClass().getResource(PDF_PROFILE_IMAGE).toURI()));

    assertThat(getImages()).contains(logo).doesNotContain(profileImage);
  }

  @Test
  public void useDefaultProfileImageIfNoImageWasSet() throws Exception {
    final byte[] profileImage =
        Files.toByteArray(new File(getClass().getResource(PDF_PROFILE_IMAGE).toURI()));

    assertThat(getImages()).contains(profileImage);
  }


  @Test
  public void templateContainsProjectAssociations() throws Exception {
    assertThat(generatePDFAndGetText()).contains("Test Project");
  }

  @Test
  public void excludeTagsFromStringConcatenations() throws Exception {
    assertThat(generatePDFAndGetText()).contains("Developer", "Project Manager").doesNotContain(
        "Developer, Project Manager");
  }

  @Test
  public void useGermanDateFormat() throws Exception {
    assertThat(generatePDFAndGetText()).contains(
        new SimpleDateFormat("MMMM yyyy", Locale.GERMAN).format(new Date()));
  }


  private String generatePDFAndGetText() throws IOException {
    Preconditions.checkNotNull(profile);
    PDFParser parser =
        new PDFParser(new ByteArrayInputStream(profileExportService.exportToPdf(profile, template)));
    parser.parse();
    PDFTextStripper stripper = new PDFTextStripper();
    return stripper.getText(parser.getPDDocument());
  }

  @SuppressWarnings("unchecked")
  private List<byte[]> getImages() throws IOException {
    return getPDFImages().stream().map(x -> x.content).collect(Collectors.toList());
  }

  @SuppressWarnings("unchecked")
  private List<PDFImage> getPDFImages() throws IOException {
    PDFParser parser =
        new PDFParser(new ByteArrayInputStream(profileExportService.exportToPdf(profile, template)));
    parser.parse();


    Function<Object, Stream<PDFImage>> function =
        o -> {
          final Map<String, PDXObject> xObjects = ((PDPage) o).getResources().getXObjects();
          return xObjects
              .keySet()
              .stream()
              .filter(x -> xObjects.get(x) instanceof PDXObjectImage)
              .map(
                  ele -> {
                    try {
                      final PDXObjectImage image = ((PDXObjectImage) xObjects.get(ele));
                      try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        ImageIO.write(image.getRGBImage(), image.getSuffix(), outputStream);
                        return new PDFImage(Arrays.hashCode(outputStream.toByteArray()) + "."
                            + image.getSuffix(), outputStream.toByteArray());
                      }
                    } catch (final IOException e) {
                      throw new RuntimeException(e);
                    }
                  });
        };

    return (List<PDFImage>) parser.getPDDocument().getDocumentCatalog().getAllPages().stream()
        .flatMap(function).collect(Collectors.toList());
  }

  private static class PDFImage {
    final String name;
    final byte[] content;

    public PDFImage(@NotNull final String name, final @NotNull byte[] content) {
      this.name = name;
      this.content = content;
    }
  }
}
