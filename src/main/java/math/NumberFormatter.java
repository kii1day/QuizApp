package math;
import java.text.DecimalFormat;

public class NumberFormatter {
	public String formatNumber(double accuracy) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.00%");
		return decimalFormat.format(accuracy);
	}
}
