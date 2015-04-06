package Model;

public enum DatabaseCommand {
	CREATE_TABLES, ADD_COURSE, ADD_STUDENT, ADD_GRADE, 
	RETRIEVE_GRADE, RETRIEVE_OVERALL_GRADE, GET_COURSES,
	GET_ASSIGNMENTS, GET_STUDENTS, GET_GRADE_INFO, GET_USER_TYPE;
	
	public static DatabaseCommand fromString(String command){
    	command = command.toUpperCase();
        switch (command){
            case "CREATE_TABLES":
                return CREATE_TABLES;
            case "ADD_COURSE":
                return ADD_COURSE;
            case "ADD_STUDENT":
                return ADD_STUDENT;
            case "ADD_GRADE":
            	return ADD_GRADE;
            case "RETRIEVE_GRADE":
            	return RETRIEVE_GRADE;
            case "RETRIEVE_OVERALL_GRADE":
            	return RETRIEVE_OVERALL_GRADE;
            case "GET_COURSES":
            	return GET_COURSES;
            case "GET_ASSIGNMENTS":
            	return GET_ASSIGNMENTS;
            case "GET_STUDENTS":
            	return GET_STUDENTS;
            case "GET_GRADE_INFO":
            	return GET_GRADE_INFO;
            case "GET_USER_TYPE":
            	return GET_USER_TYPE;
        }
        return null;
    }

}
