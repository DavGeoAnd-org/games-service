package com.davgeoand.api.exception;

public class TemtemException {
    public static class MissingException extends NullPointerException {
        public MissingException(String string) {
            super(string);
        }
    }

    public static class MissingBattleException extends MissingException {
        public MissingBattleException(String battleName) {
            super("Battle does not exist: " + battleName);
        }
    }

    public static class MissingTemtemException extends MissingException {
        public MissingTemtemException(String temtemName) {
            super("Temtem does not exist: " + temtemName);
        }
    }

    public static class MissingTechniqueException extends MissingException {
        public MissingTechniqueException(String technique) {
            super("Technique does not exist: " + technique);
        }
    }
}
