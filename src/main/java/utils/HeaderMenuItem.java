package utils;

public enum HeaderMenuItem {

    HOME("//a[text()='HOME']"),
    ABOUT("//a[text()='ABOUT']"),
    LOGIN("//a[text()='LOGIN']"),
    CONTACTS("//a[text()='CONTACTS']"),
    ADD("//a[text()='ADD']"),
    SING_OUT("//button[text()='Sing Out']");


    private final String locator;

    HeaderMenuItem(String locator) {
        this.locator = locator;
    }
    public String getLocator(){
        return locator;
    }


}
