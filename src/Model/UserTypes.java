package Model;

public enum UserTypes {
	PROFESSOR, STUDENT, INVALID;

    public static UserTypes fromString(String element){
        switch (element){
            case "PROFESSOR":
                return PROFESSOR;
            case "STUDENT":
                return STUDENT;
            case "INVALID":
                return INVALID;
        }
        return null;
    }
}
