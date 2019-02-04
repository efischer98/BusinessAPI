package webServer;

public class ErrorReport extends ReportFormatter {

	public ErrorReport(String errorMessage) {
		super();
		addHeaderLine("Error in request");
		addLine(errorMessage);
		footer();
	}
}
