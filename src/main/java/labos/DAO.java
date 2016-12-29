package labos;

public class DAO {

    private String json;

    public DAO() {
        // TODO: Cargamos el JSON de los horarios (y del software)
        //json = "{'lab1': [ {'9:00': 'EDA', '9:30': 'EDA', '10:00': ''}, {'9:00': 'AW', '9:30': 'AW', '10:00': 'AW'}, {'9:00': '', '9:30': '', '10:00': 'X'} ] }";
        json = "{\"lab1\":{\"1\":{\"9:00\":\"EDA\",\"9:30\":\"EDA\",\"10:00\":\"\"},\"2\":{\"9:00\":\"AW\",\"9:30\":\"AW\",\"10:00\":\"AW\"},\"3\":{\"9:00\":\"\",\"9:30\":\"\",\"10:00\":\"X\"}}}";
    }

    public String getTimetableJSON() {
        return json;
    }

}