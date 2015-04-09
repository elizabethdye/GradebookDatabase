package Model;

public enum UserTypes {
	PROFESSOR, STUDENT, INVALID, ADMIN;

    public static UserTypes fromString(String element){
    	element = element.toUpperCase();
        switch (element){
            case "PROFESSOR":
                return PROFESSOR;
            case "STUDENT":
                return STUDENT;
            case "ADMIN":
            	return ADMIN;
            case "INVALID":
                return INVALID;
        }
        return null;
    }
}
