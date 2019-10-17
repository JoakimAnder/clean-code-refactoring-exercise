package refactored.parser;


import refactored.actions.Action;
import refactored.actions.Operation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QueryParser implements Parser<Double> {
    private static final String ON_EMPTY_MESSAGE = "[empty]\nCommands: q=quit c=clear + - * / number";
    private List<Parse> parses;
    Consumer<String> onError;
    Action onQuit;

    // region predicates
    private static final Predicate<String> isClear = input -> input.equalsIgnoreCase("c") || input.equalsIgnoreCase("clear");
    private static final Predicate<String> isQuit = input -> input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit");
    private static final Predicate<String> isPlus = input -> input.equals("+");
    private static final Predicate<String> isSubtract = input -> input.equals("-");
    private static final Predicate<String> isMultiply = input -> input.equals("*");
    private static final Predicate<String> isDivide = input -> input.equals("/");
    private static final Predicate<String> isNumber = input -> {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };
    // endregion

    // region functions
    private static final Function<Double[], Double[]> add = i -> Collections.singletonList(i[0] + i[1]).toArray(new Double[1]);
    private static final Function<Double[], Double[]> sub = i -> Collections.singletonList(i[0] - i[1]).toArray(new Double[1]);
    private static final Function<Double[], Double[]> mul = i -> Collections.singletonList(i[0] * i[1]).toArray(new Double[1]);
    private static final Function<Double[], Double[]> div = i -> Collections.singletonList(i[0] / i[1]).toArray(new Double[1]);
    private static final Function<Double[], Double[]> clear = i -> new Double[0];
    private final Function<Double[], Double[]> quit = i -> {onQuit.execute(); return new Double[0];};

    // endregion

    public QueryParser() {
        onError = s -> {throw new RuntimeException(s);};
        onQuit = () -> onError.accept("Quitting");

        parses = Arrays.asList(
                new Parse(isClear, new Operation<>(Integer.MAX_VALUE, clear)),
                new Parse(isPlus, new Operation<>(2, add)),
                new Parse(isSubtract, new Operation<>(2, sub)),
                new Parse(isMultiply, new Operation<>(2, mul)),
                new Parse(isDivide, new Operation<>(2, div)),
                new Parse(isQuit, new Operation<>(2, quit))
        );
    }



    @Override
    public Operation<Double> toOperation(String query) {
        query = query.trim();
        if(query.isEmpty())
            return new Operation<>(0, i -> {onError.accept("No Input");return new Double[0];});

        final String finalQuery = query;

        if(isNumber.test(query)) {
            return new Operation<>(0, i -> {
                Double[] arr = new Double[1];
                arr[0] = Double.parseDouble(finalQuery);
                return arr;
            });
        }

        List<Parse> potentialParses = parses.stream()
                .filter(p -> p.test(finalQuery))
                .collect(Collectors.toList());

        if(potentialParses.isEmpty())
            return new Operation<>(0, i -> {onError.accept("No Operation called: "+finalQuery);return new Double[0];});

        return potentialParses.get(0).getOperation();



//        final String fixedQuery = query;
//        char command = query.charAt(0);
//        if (Character.isDigit(command)){
//            return () -> getOnDigit().accept(Double.parseDouble(fixedQuery));
//        } else if(command == '+') {
//            return () -> getOnBinaryOperator().execute(xy -> xy[0] + xy[1]);
//        } else if(command == '-') {
//            return () -> getOnBinaryOperator().execute(xy -> xy[0] - xy[1]);
//        } else if(command == '*') {
//            return () -> getOnBinaryOperator().execute(xy -> xy[0] * xy[1]);
//        } else if(command == '/') {
//            return () -> getOnBinaryOperator().execute(xy -> xy[0] / xy[1]);
//        } else if(command == 'c') {
//            return () -> getOnClear().execute();
//        } else if(command == 'q') {
//            return () -> getOnExit().execute();
//        }else {
//            return () -> getOnInvalid().accept(fixedQuery);
//        }
    }

    @Override
    public String getDefaultMessage() {
        return ON_EMPTY_MESSAGE;
    }

    @Override
    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    @Override
    public void setOnQuit(Action onQuit) {
        this.onQuit = onQuit;
    }

    private static class Parse {
        private Predicate<String> predicate;
        private Operation<Double> operation;

        public Parse(Predicate<String> predicate, Operation<Double> operation) {
            this.predicate = predicate;
            this.operation = operation;
        }

        public boolean test(String s) {
            return predicate.test(s);
        }

        public Operation<Double> getOperation() {
            return operation;
        }
    }
}
