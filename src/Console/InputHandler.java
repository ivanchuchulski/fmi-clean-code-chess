package Console;

import Chess.Board.BoardCoordinate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private final int SOURCE_REGEX_GROUP = 1;
    private final int TO_REGEX_GROUP = 3;
    private final static Pattern validMoveForm = Pattern.compile("([a-hA-H][1-8])([-])([a-hA-H][1-8])", Pattern.CASE_INSENSITIVE);
    private final BoardMapper mapper;

    public InputHandler() {
        mapper = new BoardMapper();
    }

    public boolean isInputValid(String move) {
        Matcher matcher = validMoveForm.matcher(move);

        return matcher.matches();
    }

    public BoardCoordinate getFrom(String val) {
        Matcher matcher = validMoveForm.matcher(val);

        if (matcher.matches()) {
            String coords = matcher.group(SOURCE_REGEX_GROUP);
            return parse(coords);
        }

        throw new IllegalArgumentException();
    }

    public BoardCoordinate getTo(String val) {
        Matcher matcher = validMoveForm.matcher(val);

        if (matcher.matches()) {
            String coords = matcher.group(TO_REGEX_GROUP);
            return parse(coords);
        }

        throw new IllegalArgumentException();
    }

    public BoardCoordinate parse(String val) {
        int x = mapper.map(val.charAt(0));
        int y = mapper.map(Integer.parseInt(String.valueOf(val.charAt(1))));

        return new BoardCoordinate(x, y);
    }
}
