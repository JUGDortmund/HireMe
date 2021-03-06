package services;

import static org.assertj.core.api.Assertions.assertThat;
import model.Profile;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TemplateExportServiceTest {

  private TemplateExportService templateExportService;
  private ProfileExportService profileExportService;
  private Profile profile;

  @Before
  public void setUp() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    templateExportService = new TemplateExportService(mapper);
  }

  @Test
  public void loadTemplates() throws Exception {
    assertThat(templateExportService.getTemplateDefinitions()).hasSize(2);
  }

  @Test
  public void getStandardTemplate() {
    assertThat(templateExportService.getTemplate("Standard").getName()).isEqualTo("Standard");
  }

  @Test
  public void getAnonymTemplate() {
    assertThat(templateExportService.getTemplate("Anonym").getName()).isEqualTo("Anonym");
  }

}
