package McGillLibraryDB.handler;

import McGillLibraryDB.dao.*;
import McGillLibraryDB.utils.UserInputHelper;

public class GenerateReportHandler {
    private final ReportDAO reportDAO;

    public GenerateReportHandler(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    public void generateReports() {
        int intInput = UserInputHelper.getIntInput();

        switch (intInput) {
            case 1:
                System.out.println("========== Popular Books Report ===========");
                this.reportDAO.getPopularBooks();
                break;
            case 2:
                System.out.println("========== Overdue Books & Fines Report ==========");
                this.reportDAO.getOverdueLoans();
                break;
            case 3:
                System.out.println("========== Library Branch Performance Report ==========");
                this.reportDAO.getLibraryPerformance();
                break;
            case 4:
                break;
            default:
                System.out.println("==> Invalid input");
                generateReports();
                break;
        }
    }



}
