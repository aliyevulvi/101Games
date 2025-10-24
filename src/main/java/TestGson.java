import com.google.gson.Gson;

public class TestGson {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String json = "{\"name\":\"Ülvi\",\"age\":22}";
        Person p = gson.fromJson(json, Person.class);
        System.out.println(p.name + " - " + p.age);
    }

    static class Person {
        String name;
        int age;
    }
}
