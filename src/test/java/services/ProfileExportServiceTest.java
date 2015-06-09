package services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import conf.ConfigurationProvider;
import model.Profile;
import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ProfileExportServiceTest {

  private ProfileExportService profileExportService;
  private Profile profile;

  @Before
  public void setUp() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    profileExportService = new ProfileExportService(mapper, new ConfigurationProvider().get());
    profile = new Profile();

    profile.setFirstname("Klaus");
    profile.setLastname("Mustermann");
    profile.setCareerLevel(Lists.newArrayList("Manager", "Consultant"));
  }

  @Test
  public void exportReturnsPdfWithProfileData() throws Exception {
    assertThat(generatePDFAndGetText(profile)).contains("Klaus");
  }

  @Test(expected = NullPointerException.class)
  public void exportWithNullProfileThrowsException() throws Exception {
    profileExportService.exportToPdf(null);
    fail("No expected exception was thrown");
  }
  
  @Test
  public void listOfTagsAreCommaSeparated() throws Exception {
    assertThat(generatePDFAndGetText(profile)).contains("Manager, Consultant");
  }
  
  @Test
  public void imagesAreReplacedWithDataUrls() throws Exception {
    final String expectedDataUrl = Base64.encodeBase64String(Files.toByteArray(new File(getClass().getResource("/exportTemplates/maredit-logo.png").toURI())));
    assertThat(generatePDFAndGetText(profile)).contains(expectedDataUrl);
  }
  
  @Test
  public void loadTemplates() throws Exception {
    assertThat(profileExportService.getTemplates()).hasSize(1);
  }

  @Test
  public void getNameFromTemplate() throws Exception {
    assertThat(profileExportService.getTemplates().get(0).getName()).isEqualTo("TestTemplate");
    assertThat(profileExportService.getTemplates()
      .get(0)
      .getTemplatePath()).isEqualTo("template.ftl.xhtml");
  }

  private String generatePDFAndGetText(final @NotNull Profile profile) throws IOException {
    Preconditions.checkNotNull(profile);
    PDFParser
      parser =
      new PDFParser(new ByteArrayInputStream(profileExportService.exportToPdf(profile)));
    parser.parse();
    PDFTextStripper stripper = new PDFTextStripper();
    return stripper.getText(parser.getPDDocument());
  }
}