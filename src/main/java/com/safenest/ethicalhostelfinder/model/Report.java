package com.safenest.ethicalhostelfinder.model;

public class Report {

    private int reportId;
    private String reportType;    // "booking", "review", "complaint"
    private String generatedDate;
    private int generatedBy;      // FK → Admin

    public Report() {}

    public Report(int reportId, String reportType,
                  String generatedDate, int generatedBy) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.generatedDate = generatedDate;
        this.generatedBy = generatedBy;
    }

    public int getReportId() { return reportId; }
    public void setReportId(int reportId) { this.reportId = reportId; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(String generatedDate) { this.generatedDate = generatedDate; }

    public int getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(int generatedBy) { this.generatedBy = generatedBy; }
}