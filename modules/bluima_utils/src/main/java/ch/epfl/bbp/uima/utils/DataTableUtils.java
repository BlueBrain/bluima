package ch.epfl.bbp.uima.utils;

import ch.epfl.bbp.uima.types.DataTable;

/**
 * @author renaud.richardet@epfl.ch
 */
public class DataTableUtils {

    /** @return an html representation of a {@link DataTable} */
    public static String toHtml(DataTable t) {

	StringBuilder sb = new StringBuilder();

	sb.append("\n<div id =\"title\">Table #" + t.getTableId() + " on page "
		+ t.getPageNumber() + "</div>\n");
	sb.append("\n<div id =\"caption\">caption: " + t.getCaption()
		+ "</div>\n");

	sb.append("\n<table  style=\"border:1px solid black;border-collapse:collapse;\" id =\""
		+ t.getTableId() + "\">\n");

	try {
	    for (int i = 0; i < t.getColumnCount(); i++) {
		sb.append(" <thead style=\"border:1px solid black;\">" + t.getHeadings(i) + "</thead>\n");
	    }
	} catch (Exception e) {
	    sb.append(" <thead style=\"border:1px solid black;\">" + t.getHeadings() + "</thead>\n");
	}

	for (int j = 0; j < t.getRowCount(); j++) {
	    sb.append(" <tr>\n");
	    for (String td : t.getBody(j).split(";")) {
		sb.append("  <td style=\"border:1px solid black;\">" + td.trim() + "</td>\n");
	    }
	    sb.append(" </tr>\n");
	}

	sb.append("</table>");
	sb.append("\n<div id =\"reference_text\">reference text: "
		+ t.getReferenceText() + "</div><br/><br/><br/>\n");
	return sb.toString();
    }

}
