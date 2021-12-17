package Airplane.model;

public enum EnumTest {
    fetra ("nice", "27"),
    lala ("horrible", "26");

    private final String desc;
    private final String age;

    EnumTest (String description, String ageParam)
    {
        desc = description;
        age= ageParam;
    }

    public String getDesc()
    {
        return desc;
    }
    public String getAge()
    {
        return age;
    }
}
