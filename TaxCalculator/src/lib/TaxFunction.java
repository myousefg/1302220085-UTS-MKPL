package lib;

public class TaxFunction {

	private static final int BASIC_NON_TAXABLE_INCOME = 54000000;
	private static final int MARRIED_TAX_ALLOWANCE = 4500000;
	private static final int CHILD_TAX_ALLOWANCE = 1500000;
	private static final int MAX_CHILDREN_COUNTED = 3;
	private static final double TAX_RATE = 0.05;

	public static int calculateTax(EmployeeInfo info) {
		validateMonthsWorked(info.numberOfMonthWorking);

		int grossIncome = calculateGrossIncome(info);
		int nonTaxableIncome = calculateNonTaxableIncome(info);

		int taxableIncome = grossIncome - info.deductible - nonTaxableIncome;
		int tax = (int) Math.round(TAX_RATE * taxableIncome);

		return Math.max(tax, 0);
	}

	private static void validateMonthsWorked(int monthsWorked) {
		if (monthsWorked > 12) {
			System.err.println("More than 12 month working per year");
		}
	}

	private static int calculateGrossIncome(EmployeeInfo info) {
		return (info.monthlySalary + info.otherMonthlyIncome) * info.numberOfMonthWorking;
	}

	private static int calculateNonTaxableIncome(EmployeeInfo info) {
		int nonTaxableIncome = BASIC_NON_TAXABLE_INCOME;

		if (info.isMarried) {
			nonTaxableIncome += MARRIED_TAX_ALLOWANCE;
		}

		int validChildCount = Math.min(info.numberOfChildren, MAX_CHILDREN_COUNTED);
		nonTaxableIncome += validChildCount * CHILD_TAX_ALLOWANCE;

		return nonTaxableIncome;
	}

	public static class EmployeeInfo {
		public int monthlySalary;
		public int otherMonthlyIncome;
		public int numberOfMonthWorking;
		public int deductible;
		public boolean isMarried;
		public int numberOfChildren;

		public EmployeeInfo(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking,
				int deductible, boolean isMarried, int numberOfChildren) {
			this.monthlySalary = monthlySalary;
			this.otherMonthlyIncome = otherMonthlyIncome;
			this.numberOfMonthWorking = numberOfMonthWorking;
			this.deductible = deductible;
			this.isMarried = isMarried;
			this.numberOfChildren = numberOfChildren;
		}
	}
}