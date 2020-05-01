# Zadanie "Spotkajmy się"

Program ma za zadanie znaleźć możliwe przedziały czasowe, w których dwie
osoby mogą zoorganizować spotkanie o określonej długości.

## Budowanie

Projekt można zbudować z pomocą maven.

```
mvn assembly\:assembly
```

Można też użyć skryptu
```
./mvnw assembly\:assembly
```

W folderze target powinien poswtać orangeunit-jar-with-dependencies.jar z wszsytkimi zależnościami.

## Uruchamianie

Zakładając, że jesteśmy w głównym folderze
``
java -jar target/orangeunit-jar-with-dependencies.jar kalendarz1.json kalendarz2.json 00:30
``
Pierwsze dwa parametry to pliki z kalendarzami. Trzeci parametr to długość spotkania w formacie
"hh:mm".

## Algorytm

1. Jeżeli któryś z kalendarzy jest nullem zwróć pustą listę
2. Znajdź przedział czasowy w którym obydwie osoby pracują
3. Jeżli taki przedział nie istnieje zwróć pustą listę
4. Utwórz kopiec z wszystkimi spotkaniami osób(im spotkanie zaczyna się wcześniej tym wyżej na stosie, kiedy dwa zaczynają się w tym
samym momencie, to, które jest dłuższe jest wyżej na stosie)
5. Utwórz stos możliwych spotkań
6. Dopóki stos i kopiec nie są puste:
    1. Zdejmij element ze stosu i oznacz go jako ***a***
    2. Zdejmij element  kopca i oznacz go jako ***b***
    3. Znajdź różnicę przedziałów ***a*** i ***b***
    4. Dodaj do stosu znalezione przedziały(wcześniejszy dodaj pierwszy)
7. Przefiltruj stos tak, żeby przedziały miały minimalną długość taką jaka jest
długość spotkania
8. Zwróć przefiltrowany stos
