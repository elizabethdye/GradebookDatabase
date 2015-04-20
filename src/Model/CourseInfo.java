package Model;

public class CourseInfo implements java.io.Serializable {

	private static final long serialVersionUID = -4022043567991970815L;
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
	
	public Boolean isEqual(CourseInfo other) {
		return this.course.equals(other.course) && this.professor.equals(other.professor);
	}

}
