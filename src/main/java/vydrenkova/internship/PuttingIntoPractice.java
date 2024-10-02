package vydrenkova.internship;

import vydrenkova.internship.models.Trader;
import vydrenkova.internship.models.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PuttingIntoPractice {

    public static void main(String... args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //1. Найти все транзакции за 2011 год и отсортировать их по сумме (от меньшей к большей).
        List<Transaction> transactions2011Sorted = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(transactions2011Sorted);          //[{Trader:Brian in Cambridge, year: 2011, value: 300}, {Trader:Raoul in Cambridge, year: 2011, value: 400}]

        //2. Вывести список неповторяющихся городов, в которых работают трейдеры.
        Set<String> uniqueCities = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .collect(Collectors.toSet());
        System.out.println(uniqueCities);        //[Milan, Cambridge]


        //3. Найти всех трейдеров из Кембриджа и отсортировать их по именам.
        List<Trader> tradersFromCambridgeSorted = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(tradersFromCambridgeSorted);       //[Trader:Alan in Cambridge, Trader:Brian in Cambridge, Trader:Raoul in Cambridge]


        //4. Вернуть строку со всеми именами трейдеров, отсортированными в алфавитном порядке.
        String tradersNamesSorted = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println(tradersNamesSorted);       //Alan, Brian, Mario, Raoul


        //5. Выяснить, существует ли хоть один трейдер из Милана.
        boolean anytraderFromMilan = transactions.stream()
                .map(Transaction::getTrader)
                .anyMatch(t -> t.getCity().equals("Milan"));

        System.out.println(anytraderFromMilan);       //true


        //6. Вывести сумму всех транзакций трейдеров из Кембриджа.
        int valuesFromCambridgeSum = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .mapToInt(Transaction::getValue).sum();
        System.out.println(valuesFromCambridgeSum);     //2650


        //7. Какова максимальная сумма среди всех транзакций?
        int maxValue = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max()
                .orElse(-1);
        System.out.println(maxValue);      //1000


        //8. Найти транзакцию с минимальной суммой.
        Transaction transactionWithMinValue = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .orElseThrow(() -> new IllegalArgumentException("Transactions is empty!"));
        System.out.println(transactionWithMinValue);       //{Trader:Brian in Cambridge, year: 2011, value: 300}

    }
}
