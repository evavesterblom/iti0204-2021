package ee.ttu.algoritmid.trampoline;

public interface Trampoline {

    enum Type {
        NORMAL,
        WITH_FINE,
        WALL
    }

    int getJumpForce();

    Type getType();

}