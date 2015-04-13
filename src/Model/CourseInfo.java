package Model;

public class CourseInfo {
	
	private String course;
	private String professor;
	private double grade;
	
	public CourseInfo (String course, String professor, double grade) {
		this.course = course;
		this.professor = professor;
		this.grade = grade;
	}
	
	public String getCourse() {
		return course;
	}
	
	public String getProfessor() {
		return professor;
	}
	
	public double getGrade() {
		return grade;
	}
	
}
