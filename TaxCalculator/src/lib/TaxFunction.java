package lib;

public class TaxFunction {

	private static final int BASIC_NON_TAXABLE_INCOME = 54000000;
	private static final int MARRIED_TAX_ALLOWANCE = 4500000;
	private static final int CHILD_TAX_ALLOWANCE = 1500000;
	private static final int MAX_CHILDREN_COUNTED = 3;
	private static final double TAX_RATE = 0.05;

	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus
	 * dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan
	 * bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan)
	 * dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena
	 * pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah
	 * sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya
	 * ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */

	public static int calculateTax(EmployeeInfo info) {

		if (info.numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}

		if (info.numberOfChildren > MAX_CHILDREN_COUNTED) {
			info.numberOfChildren = MAX_CHILDREN_COUNTED;
		}

		int grossIncome = (info.monthlySalary + info.otherMonthlyIncome) * info.numberOfMonthWorking;

		int tax;
		if (info.isMarried) {
			tax = (int) Math.round(TAX_RATE * (grossIncome - info.deductible
					- (BASIC_NON_TAXABLE_INCOME + MARRIED_TAX_ALLOWANCE
							+ (info.numberOfChildren * CHILD_TAX_ALLOWANCE))));
		} else {
			tax = (int) Math.round(TAX_RATE * (grossIncome - info.deductible - BASIC_NON_TAXABLE_INCOME));
		}

		return Math.max(tax, 0);
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