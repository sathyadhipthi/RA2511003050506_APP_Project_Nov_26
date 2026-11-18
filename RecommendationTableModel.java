package skillmatch.ui;

import skillmatch.service.MatchingService.MatchResult;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RecommendationTableModel extends AbstractTableModel {
    private static final String[] COLUMNS =
            { "Internship", "Company", "Location", "Match %", "Missing skills" };
    private List<MatchResult> rows = new ArrayList<>();

    public void setResults(List<MatchResult> results) {
        this.rows = results;
        fireTableDataChanged();
    }

    public MatchResult getRow(int i) { return rows.get(i); }

    @Override public int getRowCount()    { return rows.size(); }
    @Override public int getColumnCount() { return COLUMNS.length; }
    @Override public String getColumnName(int c) { return COLUMNS[c]; }
    @Override
    public Object getValueAt(int r, int c) {
        MatchResult m = rows.get(r);
        switch (c) {
            case 0:  return m.internship().getTitle();
            case 1:  return m.internship().getCompanyName();
            case 2:  return m.internship().getLocation();
            case 3:  return Math.round(m.score()) + "%";
            default: return m.missing().isEmpty() ? "-" : String.join(", ", m.missing());
        }
    }
}
