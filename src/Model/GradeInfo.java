package Model;

public class GradeInfo {
	
	private String student;
	private String assignment;
	private double grade;
	
	public GradeInfo (String student, String assignment, double grade) {
		this.student = student;
		this.assignment = assignment;
		this.grade = grade;
	}
	
	public String getStudent() {
		return student;
	}
	
	public String getAssignment() {
		return assignment;
	}
	
	public double getGrade() {
		return grade;
	}
}
