package Console;

import Chess.BoardCoordinate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private final static Pattern validMove = Pattern.compile("([a-hA-H][1-8])([-])([a-hA-H][1-8])", Pattern.CASE_INSENSITIVE);
    private final BoardMapper mapper;

    public InputHandler() {
        mapper = new BoardMapper();
    }

    public boolean isInputValid(String move) {
        Matcher matcher = validMove.matcher(move);

        return matcher.matches();
    }

    public BoardCoordinate getSource(String val) {
        Matcher matcher = validMove.matcher(val);
        matcher.matches();
        String coords = matcher.group(1);

        return parse(coords);
    }

    public BoardCoordinate getDestination(String val) {
        Matcher matcher = validMove.matcher(val);
        matcher.matches();
        String coords = matcher.group(3);

        return parse(coords);
    }

    private BoardCoordinate parse(String val) {
        int x = mapper.map(val.charAt(0));
        int y = mapper.map(Integer.parseInt(String.valueOf(val.charAt(1))));

        return new BoardCoordinate(x, y);
    }
}
