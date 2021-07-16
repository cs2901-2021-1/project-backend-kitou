package kitou.util;

public enum Role {
    REVOKED(0),
    STANDARD(1),
    ADMIN(2);

    public final Integer value;

    private Role(Integer value){
        this.value=value;
    }
}
