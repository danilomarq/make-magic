package br.com.hogwarts.utils;

public enum PotterApiRoutes {
    KEY("$2a$10$ysdflW6CO054Zcl0VFhmXeHb/yXorWWm4NblCev.sU8NS8nEdaXz."),
    BASE_ROUTE("https://www.potterapi.com/v1/"),

    /*House routes*/
    HOUSES_ALL("/houses"),
    HOUSES_ID("/houses/{houseId}");

    private final String route;
    PotterApiRoutes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
