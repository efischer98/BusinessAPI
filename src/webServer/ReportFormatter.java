package webServer;

public class ReportFormatter {
	
	protected StringBuilder report_;
	
	public ReportFormatter() {
		header();
	}
	public void header() {
		report_ = new StringBuilder();
		report_.append("<HTML><BODY>");
	}
	public void footer() {
		report_.append("</BODY></HTML");
	}
	
	public void addLine(String line) {
		report_.append(line + "<br>");
	}
	public void addHeaderLine(String line) {
		report_.append("<H1>" + line + "</H1><br>");
	}
	
	public String toString() {
		return report_.toString();
	}

}
