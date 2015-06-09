import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.output.StringBuilderWriter;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratePDF {

  public static void main(String[] args) throws Exception {
    try (OutputStream outputStream = new FileOutputStream(new File("testPDF.pdf"))) {
      Configuration cfg = new Configuration(Configuration.getVersion());
      cfg.setClassForTemplateLoading(GeneratePDF.class, "");
      Template template = cfg.getTemplate("test.ftl.html");
      Map<String, Object> data = new HashMap<>();
      List<String> countries = new ArrayList<String>();
      countries.add("India");
      countries.add("United States");
      countries.add("Germany");
      countries.add("France");

      data.put("countries", countries);

      StringBuilderWriter writer = new StringBuilderWriter();
      template.process(data, writer);

      final String[] inputs = new String[]{newPageHtml(1, "red"), newPageHtml(2, "green"),
        newPageHtml(3, "blue")};
      ITextRenderer renderer = new ITextRenderer();

      renderer.setDocumentFromString(writer.toString());
      renderer.layout();
      renderer.createPDF(outputStream, false);

      for (int i = 1; i < inputs.length; i++) {
        renderer.setDocumentFromString(inputs[i]);
        renderer.layout();
        renderer.writeNextDocument();
      }

      renderer.finishPDF();
    }
  }

  private static String newPageHtml(int pageNo, String color) {
    return "<html style='color: " + color + "'>" +
      "  Page" + pageNo +
      "</html>";
  }
}
