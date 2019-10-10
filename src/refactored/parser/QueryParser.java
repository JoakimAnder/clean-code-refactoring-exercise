package refactored.parser;


import refactored.actions.Action;

public class QueryParser extends Parser {
    private static final String ON_EMPTY_MESSAGE = "[empty]\nCommands: q=quit c=clear + - * / number";

    @Override
    public Action parse(String query) {
        query = query.trim();
        if(query.isEmpty())
            return () -> getOnEmpty().execute();
        final String fixedQuery = query;
        char command = query.charAt(0);
        if (Character.isDigit(command)){
            return () -> getOnDigit().accept(Double.parseDouble(fixedQuery));
        } else if(command == '+') {
            return () -> getOnBinaryOperator().execute(xy -> xy[0] + xy[1]);
        } else if(command == '-') {
            return () -> getOnBinaryOperator().execute(xy -> xy[0] - xy[1]);
        } else if(command == '*') {
            return () -> getOnBinaryOperator().execute(xy -> xy[0] * xy[1]);
        } else if(command == '/') {
            return () -> getOnBinaryOperator().execute(xy -> xy[1] / xy[0]);
        } else if(command == 'c') {
            return () -> getOnClear().execute();
        } else if(command == 'q') {
            return () -> getOnExit().execute();
        }else {
            return () -> getOnInvalid().accept(fixedQuery);
        }
    }

    @Override
    public String getDefaultMessage() {
        return ON_EMPTY_MESSAGE;
    }
}
