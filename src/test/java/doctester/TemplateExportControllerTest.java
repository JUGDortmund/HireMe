package doctester;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import model.ExportTemplateDefinition;
import ninja.NinjaDocTester;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

public class TemplateExportControllerTest extends NinjaDocTester {

  public static final TypeReference<List<ExportTemplateDefinition>> TEMPLATE_LIST_TYPE =
      new TypeReference<List<ExportTemplateDefinition>>() {};

  @Test
  public void getReturnsListOFTemplates() throws Exception {
    Response response = getAllTemplates();
    assertThat(response.payloadJsonAs(TEMPLATE_LIST_TYPE)).isNotNull();
  }

  @Test
  public void SizeOfTemplates() throws Exception {
    Response reponse = getAllTemplates();
    assertThat(reponse.payloadJsonAs(TEMPLATE_LIST_TYPE).size()).isEqualTo(2);
  }

  @Test
  public void successfullyexportTemplate() throws Exception {
    Response response = getAllTemplates();
    assertThat(response.httpStatus).isEqualTo(200);
  }

  private Response getAllTemplates() {
    return sayAndMakeRequest(Request.GET().url(testServerUrl().path("api/templates")));
  }

}
