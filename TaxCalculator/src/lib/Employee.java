package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private PersonalData personalData;
	private LocalDate joinDate;
	private int monthWorkingInYear;
	private boolean isForeigner;
	private boolean gender; // true = Laki-laki, false = Perempuan
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	private Spouse spouse;
	private List<String> childNames;
	private List<String> childIdNumbers;

	public Employee(String employeeId, PersonalData personalData, LocalDate joinDate, boolean isForeigner,
			boolean gender) {
		this.employeeId = employeeId;
		this.personalData = personalData;
		this.joinDate = joinDate;
		this.isForeigner = isForeigner;
		this.gender = gender;

		childNames = new LinkedList<>();
		childIdNumbers = new LinkedList<>();
	}

	public void setMonthlySalary(int grade) {
		if (grade == 1) {
			monthlySalary = 3000000;
			if (isForeigner) {
				monthlySalary = (int) (3000000 * 1.5);
			}
		} else if (grade == 2) {
			monthlySalary = 5000000;
			if (isForeigner) {
				monthlySalary = (int) (3000000 * 1.5);
			}
		} else if (grade == 3) {
			monthlySalary = 7000000;
			if (isForeigner) {
				monthlySalary = (int) (3000000 * 1.5);
			}
		}
	}

	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}

	public void setSpouse(String name, String idNumber) {
		this.spouse = new Spouse(name, idNumber);
	}

	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}

	public int getAnnualIncomeTax() {
		LocalDate currentDate = LocalDate.now();

		if (currentDate.getYear() == joinDate.getYear()) {
			monthWorkingInYear = currentDate.getMonthValue() - joinDate.getMonthValue();
		} else {
			monthWorkingInYear = 12;
		}

		boolean hasSpouse = (spouse == null || spouse.getIdNumber().equals(""));

		return TaxFunction.calculateTax(
				monthlySalary,
				otherMonthlyIncome,
				monthWorkingInYear,
				annualDeductible,
				hasSpouse,
				childIdNumbers.size());
	}

	private static class PersonalData {
		private String firstName;
		private String lastName;
		private String idNumber;
		private String address;

		public PersonalData(String firstName, String lastName, String idNumber, String address) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.idNumber = idNumber;
			this.address = address;
		}
	}

	private static class Spouse {
		private String name;
		private String idNumber;

		public Spouse(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}

		public String getIdNumber() {
			return idNumber;
		}
	}
}