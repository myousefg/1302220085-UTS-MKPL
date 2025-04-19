package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private EmployeeId employeeId;
	private PersonalData personalData;
	private LocalDate joinDate;
	private int monthWorkingInYear;
	private boolean isForeigner;
	private Gender gender;

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private Spouse spouse;
	private List<String> childNames;
	private List<ChildId> childIdNumbers;

	public Employee(EmployeeId employeeId, PersonalData personalData, LocalDate joinDate, boolean isForeigner,
			Gender gender) {
		this.employeeId = employeeId;
		this.personalData = personalData;
		this.joinDate = joinDate;
		this.isForeigner = isForeigner;
		this.gender = gender;

		childNames = new LinkedList<>();
		childIdNumbers = new LinkedList<>();
	}

	public void setMonthlySalary(Grade grade) {
		int baseSalary = switch (grade) {
			case GRADE1 -> 3000000;
			case GRADE2 -> 5000000;
			case GRADE3 -> 7000000;
		};

		if (isForeigner) {
			baseSalary *= 1.5;
		}
		monthlySalary = baseSalary;
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

	public void addChild(String childName, ChildId childIdNumber) {
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

	public enum Gender {
		MALE, FEMALE
	}

	public enum Grade {
		GRADE1, GRADE2, GRADE3
	}

	public static class EmployeeId {
		private final String value;

		public EmployeeId(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static class ChildId {
		private final String value;

		public ChildId(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
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