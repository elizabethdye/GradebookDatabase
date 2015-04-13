package Model;

public class CourseInfo {
	
	private String course;
	private String professor;
	
	public CourseInfo (String course, String professor) {
		this.course = course;
		this.professor = professor;
	}
	
	public String getCourse() {
		return course;
	}
	
	public String getProfessor() {
		return professor;
	}

}
