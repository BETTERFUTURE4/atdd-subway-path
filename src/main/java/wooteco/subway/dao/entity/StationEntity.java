package wooteco.subway.dao.entity;

public class StationEntity {

    private final long id;
    private final String name;

    public StationEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StationEntity(String name) {
        this(0, name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
