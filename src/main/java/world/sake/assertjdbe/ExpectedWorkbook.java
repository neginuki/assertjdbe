package world.sake.assertjdbe;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author neginuki
 */
public class ExpectedWorkbook {

    private final Path xlsx;

    private final Workbook workbook;

    public ExpectedWorkbook(Path xlsx) {
        this.xlsx = xlsx;
        try {
            this.workbook = WorkbookFactory.create(getExpectedXlsx().toFile());
        } catch (EncryptedDocumentException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    protected String getSettingsSheetName() {
        return "#";
    }

    protected String getDataSourceName() {
        String name = getExpectedXlsx().getFileName().toString();
        int beginIndex = name.indexOf("#");

        if (beginIndex < 0) {
            return "";
        }

        int endIndex = name.indexOf(".");

        return name.substring(beginIndex + 1, endIndex);
    }

    protected Path getExpectedXlsx() {
        return xlsx;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Expected Workbook:")//
                .append("\n    settings sheet name: " + getSettingsSheetName()//
                );

        return sb.toString();
    }
}
