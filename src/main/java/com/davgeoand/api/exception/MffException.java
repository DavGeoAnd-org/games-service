package com.davgeoand.api.exception;

public class MffException {
    public static class MissingException extends Exception {
        public MissingException(String string) {
            super(string);
        }
    }

    public static class MissingCharacterException extends MissingException {
        public MissingCharacterException(String characterId) {
            super("Character does not exist: " + characterId);
        }
    }

    public static class MissingShadowlandException extends MissingException {
        public MissingShadowlandException(long shadowlandId) {
            super("Shadowland does not exist: " + shadowlandId);
        }
    }

    public static class MismatchException extends Exception {
        public MismatchException(String string) {
            super(string);
        }
    }

    public static class IdMismatchException extends MismatchException {
        public IdMismatchException(String string) {
            super(string);
        }
    }
}
