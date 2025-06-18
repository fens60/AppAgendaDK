package es.ieslosmontecillos;

import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;

/**
 * Enum class to manage all existing jasper reports in the app
 * @version 1.0
 */
public enum Reports
{
    AGENDA_REPORT;

    /**
     * API Manager to connect to the report endpoints in backend
     */
    private final APIManager apiManager = new APIManager();

    /**
     * Endpoint to ask for a report searching by name
     */
    public static final String REPORT_API_ENDPOINT_BY_NAME = "reports/byName/";

    /**
     * Endpoint to ask for a report using its ID the reports enum order must be the same both in front and backend
     */
    public static final String REPORT_API_ENDPOINT_BY_ID = "reports/byId/";

    /**
     * Sends a request to the backend to return a filled report
     * @return the jasperprint with the full report
     * @since 1.0
     */
    public JasperPrint getReportFromBackend()
    {
        try
        {
            return JRPrintXmlLoader.load(apiManager.sendRequest(REPORT_API_ENDPOINT_BY_ID + ordinal(), APIManager.METHOD.GET).getInputStream());
        }
        catch (Exception e)
        {
            new Alert(Alert.AlertType.ERROR, "Error al conseguir el reporte").show();
            e.printStackTrace(System.out);
            return null;
        }
    }

    /**
     * Returns the selected report name
     * @return the report name in a string
     * @since 1.0
     */
    public String getReportName()
    {
        switch (this) {
            case AGENDA_REPORT:
                return "agenda";
            default:
                throw new IllegalArgumentException();
        }
    }
}
